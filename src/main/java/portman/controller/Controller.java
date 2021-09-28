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

public class Controller {
    private static String basePath = "path.txt";
    private static String savePath;
    private Portafolios portafolios = new Portafolios();
    private Student student = new Student();
    
    public Controller() {
    
    }
    
    public void run() {
        File studentInfo = new File(Student.path);
        File json = new File(Portafolios.path);

        FrameListPortafolios fls = new FrameListPortafolios();
        DialogSaveStudent dss = new DialogSaveStudent(fls, true);
        DialogSavePortafolio dsp = new DialogSavePortafolio(fls, true);

        dss.setAccept(() -> saveStudent(dss));
        fls.setRunnables(
            () -> dss.setVisible(true),
            () -> generateAll(),
            () -> dsp.setVisible(true)
        );
        dsp.setAccept(() -> savePortafolios(dsp, fls));

        if(json.exists()){
            loadPortafolios(fls);
        }

        fls.setVisible(true);

        setSaveFolder();
        
        if(!studentInfo.exists()){
            dss.setVisible(true);
        }

        student.get();
    }

    private void setSaveFolder() {
        File base = new File(basePath);

        try {
            if(!base.exists()){
                base.createNewFile();

                JFileChooser fchooser = new JFileChooser();
                fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                
                int returnValue = fchooser.showOpenDialog(null);

                if(returnValue == JFileChooser.APPROVE_OPTION) {
                    File selected = fchooser.getSelectedFile();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(base));
        
                    bw.write(selected.getAbsolutePath());
                    bw.close();
                }
            }

                BufferedReader br = new BufferedReader(new FileReader(base));
                savePath = br.readLine() + "/portafolios";
                br.close();

                File saveFolder = new File(savePath);

                if(!saveFolder.exists()) {
                    saveFolder.mkdir();
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void generateAll() {
        System.out.println("Genereted");
    }

    private void savePortafolios(DialogSavePortafolio dsp, FrameListPortafolios flp) {
        Portafolio portafolio = new Portafolio();

        portafolio.setSubject(dsp.txt_subject.getText());
        portafolio.setProfessor(dsp.txt_professor.getText());

        for(int i = 0, n = dsp.gunits.size(); i < n; i++){
            portafolio.addtUnit(new Unit(i+1, dsp.gunits.get(i).getName()));
        }

        portafolios.put(portafolio.getSubject(), portafolio);
        portafolios.save();

        loadPortafolios(flp);

        dsp.dispose();
    }

    private void loadPortafolios(FrameListPortafolios flp) {
        portafolios.get();
        flp.pn_portafolios.removeAll();
        flp.pn_portafolios.updateUI();
        flp.gportafolios.clear();

        portafolios.forEach((k, v) -> {
            GPortafolio gp = new GPortafolio(v.getSubject(),
                () -> generatePortafolio(v),
                () -> editPortafolio(v, flp),
                () -> deletePortafolio(v, flp)
            );

            flp.addGPortafolio(gp);
        });

        flp.pn_portafolios.updateUI();
    }

    private void generatePortafolio(Portafolio p) {
        PDFController pdfc = new PDFController(student, p, savePath);
        pdfc.createPortafolio();
    }

    private void editPortafolio(Portafolio p, FrameListPortafolios flp) {
        DialogFillPortafolio dfp = new DialogFillPortafolio(flp, true, p);
        dfp.setAccept(() -> saveActivities(p, dfp));

        dfp.txt_frame.setText(p.getFrame());
        dfp.txt_diagnostic.setText(p.getDiagnostic());

        // Load Activities
        for(int i = 0; i < p.getNumberOfUnits(); i++) {
            for (int j = 0, n = p.getUnit(i).getNumberOfActivities(); j < n; j++) {
                GSelectFile gsf = new GSelectFile("Actividad " + (j+1));
                gsf.setText(p.getUnit(i).getActivity(j));

                dfp.units[i].add(gsf);
                dfp.pn_units[i].add(gsf);
            }
            dfp.pn_units[i].updateUI();
        }
        
        dfp.setVisible(true);
    }

    private void deletePortafolio(Portafolio p, FrameListPortafolios flp) {
        portafolios.remove(p.getSubject());
        portafolios.save();
        loadPortafolios(flp);
    }

    private void saveActivities(Portafolio p, DialogFillPortafolio dfp) {
        p.setFrame(dfp.txt_frame.getText());
        p.setDiagnostic(dfp.txt_diagnostic.getText());
        
        for(int i = 0; i < dfp.units.length; i++) {
            p.getUnit(i).clear();
            for (GSelectFile gsf : dfp.units[i]) {
                p.getUnit(i).setActivity(gsf.getText());
            }
        }

        portafolios.put(p.getSubject(), p);
        portafolios.save();
        
        dfp.dispose();
    }

    public static void main(String[] args) {
        Controller con = new Controller();
        con.run();    
    }
}
