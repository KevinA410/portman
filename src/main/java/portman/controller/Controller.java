package portman.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import portman.model.Portafolio;
import portman.model.Portafolios;
import portman.model.Student;
import portman.model.Unit;
import portman.view.DialogFillPortafolio;
import portman.view.DialogSavePortafolio;
import portman.view.DialogSaveStudent;
import portman.view.FrameListPortafolios;
import portman.view.components.GPortafolio;
import portman.view.components.GSelectFile;
import portman.view.components.GUnit;

public class Controller {
    private static String basePath = "path.txt";
    private static String savePath;
    private Portafolios portafolios = new Portafolios();
    private Student student = new Student();

    public Controller() {

    }

    public void run() {
        File base = new File(basePath);
        File studentInfo = new File(Student.path);
        File json = new File(Portafolios.path);

        FrameListPortafolios fls = new FrameListPortafolios();
        DialogSaveStudent dss = new DialogSaveStudent(fls, true);
        DialogSavePortafolio dsp = new DialogSavePortafolio(fls, true);

        dss.setAccept(() -> saveStudent(dss));
        fls.setRunnables(() -> editStudentInfo(dss), () -> generateAll(), () -> newPortafolio(dsp), () -> changePath());

        dsp.setAccept(() -> savePortafolios(dsp, fls));

        if (json.exists()) {
            loadPortafolios(fls, dsp);
        }

        fls.setVisible(true);

        if(!base.exists()) {
            setPath();
        }

        getPath();

        if (!studentInfo.exists()) {
            dss.setVisible(true);
        }

        student.get();
    }

    // FrameListPortafolios Functions
    private void editStudentInfo(DialogSaveStudent dss) {
        File f = new File(Student.path);

        if (f.exists()) {
            Student std = new Student();
            std.get();

            dss.txt_id.setText(std.getId());
            dss.txt_name.setText(std.getName());
            dss.cmb_degree.setSelectedItem(std.getDegree());
            ;
            dss.txt_email.setText(std.getEmail());
            dss.cmb_grade.setSelectedItem(std.getGrade());
            dss.lbl_setGroup.setText(std.getGroup().substring(0, 2));
            dss.cmb_group.setSelectedItem(std.getGroup().substring(std.getGroup().length() - 1));
        }

        dss.setVisible(true);
    }

    private void newPortafolio(DialogSavePortafolio dsp) {
        dsp.gunits.clear();
        dsp.pn_units.removeAll();
        dsp.pn_units.updateUI();

        dsp.txt_subject.setText("");
        dsp.txt_professor.setText("");

        dsp.setVisible(true);
    }

    private void changePath() {
        setPath();
        getPath();
    }

    private void generateAll() {
        portafolios.forEach((k, v) -> {
            generatePortafolio(v);
        });
    }

    // Save Functions
    private void saveStudent(DialogSaveStudent dss) {
        Student student = new Student();

        student.setId(dss.txt_id.getText());
        student.setName(dss.txt_name.getText());
        student.setDegree(String.valueOf(dss.cmb_degree.getSelectedItem()));
        student.setEmail(dss.txt_email.getText());
        student.setGrade(String.valueOf(dss.cmb_grade.getSelectedItem()));
        student.setGroup(dss.lbl_setGroup.getText() + dss.cmb_group.getSelectedItem());

        student.save();
        dss.dispose();
    }

    private void savePortafolios(DialogSavePortafolio dsp, FrameListPortafolios flp) {
        String key = dsp.txt_subject.getText();
        Portafolio p = new Portafolio();

        if(portafolios.containsKey(key)) {
            p = portafolios.get(key);
            portafolios.remove(key);
        }

        p.setSubject(key);
        p.setProfessor(dsp.txt_professor.getText());

        for (int i = 0, n = dsp.gunits.size(); i < n; i++) {
            String name = dsp.gunits.get(i).getName();
            if(i >= p.getNumberOfUnits()) {
                p.addtUnit(new Unit(i + 1, name));
            } else {
                p.getUnit(i).setName(name);
            }
        }

        portafolios.put(key, p);
        portafolios.save();

        loadPortafolios(flp, dsp);

        dsp.dispose();
    }

    private void saveActivities(Portafolio p, DialogFillPortafolio dfp) {
        p.setFrame(dfp.txt_frame.getText());
        p.setDiagnostic(dfp.txt_diagnostic.getText());

        for (int i = 0; i < dfp.units.length; i++) {
            p.getUnit(i).clear();
            for (GSelectFile gsf : dfp.units[i]) {
                p.getUnit(i).setActivity(gsf.getText());
            }
        }

        portafolios.put(p.getSubject(), p);
        portafolios.save();

        dfp.dispose();
    }

    // Portafolio's functions
    private void generatePortafolio(Portafolio p) {
        File f = new File(basePath);

        if(!f.exists()) {
            if(!setPath()){
                return;
            }
            getPath();
        }

        PDFController pdfc = new PDFController(student, p, savePath);
        pdfc.createPortafolio();
    }

    private void editPortafolio(Portafolio p, DialogSavePortafolio dsp) {
        dsp.gunits.clear();
        dsp.pn_units.removeAll();
        dsp.pn_units.updateUI();

        dsp.txt_subject.setText(p.getSubject());
        dsp.txt_professor.setText(p.getProfessor());

        for(int i = 0; i < p.getNumberOfUnits(); i++) {
            Unit u = p.getUnit(i);
            GUnit gunit = new GUnit(u.getNumber());
            gunit.setName(u.getName());
            dsp.addUnit(gunit);
        }

        dsp.setVisible(true);

    }

    private void addPortafolio(Portafolio p, FrameListPortafolios flp) {
        DialogFillPortafolio dfp = new DialogFillPortafolio(flp, true, p);
        dfp.setAccept(() -> saveActivities(p, dfp));

        dfp.txt_frame.setText(p.getFrame());
        dfp.txt_diagnostic.setText(p.getDiagnostic());

        // Load Activities
        for (int i = 0; i < p.getNumberOfUnits(); i++) {
            for (int j = 0, n = p.getUnit(i).getNumberOfActivities(); j < n; j++) {
                GSelectFile gsf = new GSelectFile("Actividad " + (j + 1));
                gsf.setText(p.getUnit(i).getActivity(j));

                dfp.addGsf(gsf, i);
            }
            dfp.pn_units[i].updateUI();
        }

        dfp.setVisible(true);
    }

    private void deletePortafolio(Portafolio p, FrameListPortafolios flp, DialogSavePortafolio dsp) {
        portafolios.remove(p.getSubject());
        portafolios.save();
        loadPortafolios(flp, dsp);
    }

    // Auxiliar funcions
    private void getPath() {
        try{
            File base = new File(basePath);
            BufferedReader br = new BufferedReader(new FileReader(base));
            savePath = br.readLine() + "/portafolios";
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean setPath() {
        try {
            File base = new File(basePath);
            JFileChooser fchooser = new JFileChooser();
            fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int returnValue = fchooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (!base.exists()) {
                    base.createNewFile();
                }

                File selected = fchooser.getSelectedFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(base));

                bw.write(selected.getAbsolutePath());
                bw.close();

                return true;
            } else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadPortafolios(FrameListPortafolios flp, DialogSavePortafolio dsp) {
        portafolios.get();
        flp.pn_portafolios.removeAll();
        flp.pn_portafolios.updateUI();
        flp.gportafolios.clear();

        portafolios.forEach((k, v) -> {
            GPortafolio gp = new GPortafolio(v.getSubject(), () -> generatePortafolio(v), () -> editPortafolio(v, dsp),
                   () -> addPortafolio(v, flp), () -> deletePortafolio(v, flp, dsp));

            flp.addGPortafolio(gp);
        });

        flp.pn_portafolios.updateUI();
    }

    public static void main(String[] args) {
        Controller con = new Controller();
        con.run();
    }
}
