package portman.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import portman.model.Portafolio;
import portman.model.Student;
import portman.model.Unit;
import org.apache.commons.io.FileUtils;

public class PDFController {
    private String[] months = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre",
            "octubre", "noviembre", "diciembre" };
    private Student student;
    private Portafolio portafolio;
    private String savePath;

    public PDFController(Student student, Portafolio portafolio, String savePath) {
        this.student = student;
        this.portafolio = portafolio;
        this.savePath = savePath;
    }

    private File createCover(String path) {
        InputStream cover = PDFController.class.getClassLoader().getResourceAsStream("portman/layout/cover.pdf");
        LocalDateTime d = LocalDateTime.now();

        try {
            PDDocument pDDocument = Loader.loadPDF(cover);
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();

            PDField txtStudent = pDAcroForm.getField("txt_student");
            PDField txtSubject = pDAcroForm.getField("txt_subject");
            PDField txtProfessor = pDAcroForm.getField("txt_professor");
            PDField txtGrade = pDAcroForm.getField("txt_grade");
            PDField txtDegree = pDAcroForm.getField("txt_degree");
            PDField txtGroup = pDAcroForm.getField("txt_group");
            PDField txtEmail = pDAcroForm.getField("txt_email");
            PDField txtDate = pDAcroForm.getField("txt_date");

            txtStudent.setValue(student.getId() + " - " + student.getName());
            txtSubject.setValue(portafolio.getSubject());
            txtProfessor.setValue(portafolio.getProfessor());
            txtGrade.setValue(student.getGrade());
            txtDegree.setValue(student.getDegree());
            txtGroup.setValue(student.getGroup());
            txtEmail.setValue(student.getEmail());
            txtDate.setValue(d.getDayOfMonth() + " de " + months[d.getMonthValue() - 1] + " de " + d.getYear());
            pDDocument.save(path + "/cover.pdf");
            pDDocument.close();

            return new File(path + "/cover.pdf");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File createMision(String path) {
        try {
            InputStream mision = PDFController.class.getClassLoader().getResourceAsStream("portman/layout/mision.pdf");
            PDDocument pDDocument = Loader.loadPDF(mision);

            pDDocument.save(path + "/mision.pdf");
            pDDocument.close();

            return new File(path + "/mision.pdf");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File createUnitSeparator(int number, String name, String path) {
        InputStream unitSeparator = PDFController.class.getClassLoader()
                .getResourceAsStream("portman/layout/unit_separator.pdf");
        String save = path + "/unit" + number + ".pdf";
        try {
            PDDocument pDDocument = Loader.loadPDF(unitSeparator);
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();

            PDField txtNumber = pDAcroForm.getField("txt_number");
            PDField txtName = pDAcroForm.getField("txt_name");

            txtNumber.setValue(String.valueOf(number));
            txtName.setValue(name);

            pDDocument.save(save);
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(save);
    }

    public void createPortafolio() {
        PDFMergerUtility mu = new PDFMergerUtility();
        String basePath = savePath + "/." + portafolio.getSubject();
        System.out.println(savePath);
        File portafolios = new File(savePath);
        File folder = new File(basePath);

        if (!portafolios.exists()) {
            portafolios.mkdir();
        }

        if (!folder.exists()) {
            folder.mkdir();
        }

        try {
            mu.addSource(createCover(basePath));
            mu.addSource(createMision(basePath));
            mu.addSource(new File(portafolio.getFrame()));
            mu.addSource(new File(portafolio.getDiagnostic()));

            for (int i = 0, n = portafolio.getNumberOfUnits(); i < n; i++) {
                Unit u = portafolio.getUnit(i);
                int activities = u.getNumberOfActivities();

                if (activities > 0) {
                    mu.addSource(createUnitSeparator(u.getNumber(), u.getName(), basePath));
                }

                for (int j = 0, m = u.getNumberOfActivities(); j < m; j++) {
                    mu.addSource(new File(u.getActivity(j)));
                }
            }

            mu.setDestinationFileName(savePath + "/" + generatePortafolioName() + ".pdf");
            mu.mergeDocuments(null);
            FileUtils.deleteDirectory(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generatePortafolioName() {
        LocalDateTime d = LocalDateTime.now();
        int m = d.getMonthValue();
        String period = "0" + (m <= 4 ? "1" : m <= 8 ? "2" : "3");

        return student.getId() + "-" + student.getGroup() + "-" + student.getGrade() + "-" + d.getYear() + period + "-"
                + portafolio.getSubject();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setPortafolio(Portafolio portafolio) {
        this.portafolio = portafolio;
    }

    public Portafolio getPortafolio() {
        return portafolio;
    }
}
