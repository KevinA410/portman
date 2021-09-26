package portman.model;

import java.util.ArrayList;

public class Portafolio {
    private String professor;
    private String subject;
    private String frame;
    private String diagnostic;
    private ArrayList<Unit> units = new ArrayList<>();

    public Portafolio() {

    }

    public Portafolio(String professor, String subject) {
        this.professor = professor;
        this.subject = subject;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getProfessor() {
        return professor;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getFrame() {
        return frame;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void addtUnit(Unit unit) {
        units.add(unit);
    }

    public Unit getUnit(int index) {
        return units.get(index);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public int getNumberOfUnits() {
        return units.size();
    }
}
