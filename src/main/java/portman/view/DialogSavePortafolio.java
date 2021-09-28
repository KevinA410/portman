package portman.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import portman.controller.AuthController;
import portman.view.components.GUnit;

public class DialogSavePortafolio extends GenericDialog {
    public final JTextField txt_subject = new JTextField();;
    public final JTextField txt_professor = new JTextField();;
    public final JButton btn_add_unit = new JButton("+");
    public final JButton btn_remove_unit = new JButton("-");
    public final ArrayList<GUnit> gunits = new ArrayList<>();

    public DialogSavePortafolio(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("Nuevo Portafolio");
        pack();
        setSize(800, 600);
        setLocationRelativeTo(parent);

        txt_professor.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                AuthController.validateName(txt_professor);
            }
        });

        btn_add_unit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUnit();
            }
        });

        btn_remove_unit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUnit();
            }
        });

        initComponents();
    }

    private void initComponents() {
        lbl_title.setText("Nuevo Portafolio");

        JLabel lbl_subject = new JLabel("Asignatura ");
        JLabel lbl_professor = new JLabel("Profesor ");
        JLabel lbl_add_unit = new JLabel("Unidades ");

        JPanel panel_subject = new JPanel(new BorderLayout());
        JPanel panel_professor = new JPanel(new BorderLayout());
        JPanel panel_add_unit = new JPanel(new BorderLayout());
        JPanel panel_buttons_unit = new JPanel(new FlowLayout());

        panel_add_unit.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    
        pn_main.add(panel_subject);
        {
            panel_subject.add(lbl_subject, BorderLayout.WEST);
            panel_subject.add(txt_subject, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_professor);
        {
            panel_professor.add(lbl_professor, BorderLayout.WEST);
            panel_professor.add(txt_professor, BorderLayout.CENTER);
        }
        pn_main.add(new JLabel(" "));
        pn_main.add(panel_add_unit);
        {
            panel_add_unit.add(lbl_add_unit, BorderLayout.CENTER);
            panel_add_unit.add(panel_buttons_unit, BorderLayout.EAST);
            {
                panel_buttons_unit.add(btn_add_unit);
                panel_buttons_unit.add(btn_remove_unit);
            }
        }
    }

    private void addUnit() {
        int index = gunits.size() + 1;
        GUnit u = new GUnit(index);

        gunits.add(u);
        pn_main.add(u);
        pn_main.updateUI();
    }

    private void removeUnit() {
        int size = gunits.size();

        if (size < 1) return;

        pn_main.remove(gunits.get(size - 1));
        gunits.remove(size - 1);
        pn_main.revalidate();
        pn_main.repaint();
    }
}