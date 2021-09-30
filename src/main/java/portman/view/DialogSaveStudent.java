package portman.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import portman.controller.AuthController;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DialogSaveStudent extends GenericDialog {
    private final String[] keys = { "M", "B", "E", "S", "A", "G" };
    private final String[] groups = { "1", "2", "3", "4" };
    private final String[] grades = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
    private final String[] degrees = { "Mecatrónica", "Biotecnología", "Electrónica y Telecomunicaciones", "Software",
            "Mecánica Automotríz", "Geofísica Petrolera" };
    public final JTextField txt_id = new JTextField();
    public final JTextField txt_name = new JTextField();
    public final JComboBox<String> cmb_degree = new JComboBox<>(degrees);
    public final JTextField txt_email = new JTextField();
    public final JComboBox<String> cmb_grade = new JComboBox<>(grades);
    public final JLabel lbl_setGroup = new JLabel(keys[0] + "-");
    public final JComboBox<String> cmb_group = new JComboBox<>(groups);

    public DialogSaveStudent(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Información Personal");
        pack();
        setSize(450, 350);
        setLocationRelativeTo(parent);
        initComponents();

        txt_id.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                AuthController.validateId(txt_id);
            }
        });

        txt_name.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                AuthController.validateName(txt_name);
            }
        });

        cmb_degree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lbl_setGroup.setText(keys[cmb_degree.getSelectedIndex()] + "-");
            }
        });
    }

    public DialogSaveStudent(JFrame parent, boolean modal, Runnable accept) {
        this(parent, modal);
        super.accept = accept;
    }

    protected void initComponents() {
        lbl_title.setText("INFORMACIÓN PERSONAL");

        JLabel lbl_id = new JLabel("Matricula ");
        JLabel lbl_name = new JLabel("Nombre ");
        JLabel lbl_degree = new JLabel("Carrera ");
        JLabel lbl_email = new JLabel("Correo ");
        JLabel lbl_grade = new JLabel("Cuatrimestre ");
        JLabel lbl_group = new JLabel("Grupo: ");

        JPanel panel_id = new JPanel(new BorderLayout());
        JPanel panel_name = new JPanel(new BorderLayout());
        JPanel panel_degree = new JPanel(new BorderLayout());
        JPanel panel_email = new JPanel(new BorderLayout());
        JPanel panel_container = new JPanel(new GridLayout(0, 2, 15, 0));
        JPanel panel_grade = new JPanel(new BorderLayout());
        JPanel panel_group = new JPanel(new BorderLayout());
        JPanel aux_group = new JPanel(new FlowLayout());

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
            panel_degree.add(cmb_degree, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_email);
        {
            panel_email.add(lbl_email, BorderLayout.WEST);
            panel_email.add(txt_email, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_container);
        panel_container.add(panel_grade);
        {
            panel_grade.add(lbl_grade, BorderLayout.WEST);
            panel_grade.add(cmb_grade, BorderLayout.CENTER);

        }
        panel_container.add(panel_group);
        {
            panel_group.add(aux_group, BorderLayout.WEST);
            {
                aux_group.add(lbl_group);
                aux_group.add(lbl_setGroup);
            }
            panel_group.add(cmb_group, BorderLayout.CENTER);
        }
    }
}
