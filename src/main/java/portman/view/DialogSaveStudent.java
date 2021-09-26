package portman.view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogSaveStudent extends GenericDialog {
    public final JTextField txt_id = new JTextField();
    public final JTextField txt_name = new JTextField();
    public final JTextField txt_degree = new JTextField();
    public final JTextField txt_email = new JTextField();
    public final JTextField txt_grade = new JTextField();
    public final JTextField txt_group = new JTextField();

    public DialogSaveStudent(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Información Personal");
        setSize(450, 330);
        setResizable(false);
        initComponents();
    }

    public DialogSaveStudent(JFrame parent, boolean modal, Runnable accept) {
       this(parent, modal);
        super.accept = accept;
    }

    protected void initComponents() {
        lbl_title.setText("Información Personal");

        JLabel lbl_id = new JLabel("Matricula ");
        JLabel lbl_name = new JLabel("Nombre ");
        JLabel lbl_degree = new JLabel("Carrera ");
        JLabel lbl_email = new JLabel("Correo ");
        JLabel lbl_grade = new JLabel("Cuatrimestre");
        JLabel lbl_group = new JLabel("Grupo ");
        JLabel lbl_info = new JLabel("Solo se requerirá la primera vez", JLabel.CENTER);

        lbl_info.setForeground(Color.GRAY);

        JPanel panel_id = new JPanel(new BorderLayout());
        JPanel panel_name = new JPanel(new BorderLayout());
        JPanel panel_degree = new JPanel(new BorderLayout());
        JPanel panel_email = new JPanel(new BorderLayout());
        JPanel panel_grade = new JPanel(new BorderLayout());
        JPanel panel_group = new JPanel(new BorderLayout());

        pn_main.add(panel_id);
        {
            panel_id.add(lbl_id, BorderLayout.WEST);
            panel_id.add(txt_id, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_name);
        {
            panel_name.add(lbl_name, BorderLayout.WEST);
            panel_name.add(txt_name, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_degree);
        {
            panel_degree.add(lbl_degree, BorderLayout.WEST);
            panel_degree.add(txt_degree, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_email);
        {
            panel_email.add(lbl_email, BorderLayout.WEST);
            panel_email.add(txt_email, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_grade);
        {
            panel_grade.add(lbl_grade, BorderLayout.WEST);
            panel_grade.add(txt_grade, BorderLayout.CENTER);

        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_group);
        {
            panel_group.add(lbl_group, BorderLayout.WEST);
            panel_group.add(txt_group, BorderLayout.CENTER);
        }
        pane.add(lbl_info, BorderLayout.CENTER);
    }
}
