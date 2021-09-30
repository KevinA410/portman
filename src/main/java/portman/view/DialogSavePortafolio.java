package portman.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import portman.controller.AuthController;
import portman.view.components.GUnit;

public class DialogSavePortafolio extends GenericDialog {
    public final JTextField txt_subject = new JTextField();;
    public final JTextField txt_professor = new JTextField();;
    public final JButton btn_add_unit = new JButton("+");
    public final JButton btn_remove_unit = new JButton("-");
    public final ArrayList<GUnit> gunits = new ArrayList<>();
    public final JPanel pn_units = new JPanel();
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public DialogSavePortafolio(JFrame parent, boolean modal) {
        super(parent, modal);
        setTitle("NUEVO PORTAFOLIO");
        pack();
        setSize(WIDTH, HEIGHT);
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
        lbl_title.setText("NUEVO PORTAFOLIO");

        JLabel lbl_subject = new JLabel("Asignatura ");
        JLabel lbl_professor = new JLabel("Profesor ");
        JLabel lbl_add_unit = new JLabel("Unidades ");

        JPanel panel_subject = new JPanel(new BorderLayout());
        JPanel panel_professor = new JPanel(new BorderLayout());
        JPanel panel_add_unit = new JPanel(new BorderLayout());
        JPanel panel_buttons_unit = new JPanel(new FlowLayout());

        pn_units.setLayout(new BoxLayout(pn_units, BoxLayout.Y_AXIS));

        panel_add_unit.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        JScrollPane scroll = new JScrollPane(pn_units);

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
        pane.add(scroll, BorderLayout.CENTER);
    }

    private void addUnit() {
        int index = gunits.size() + 1;
        GUnit u = new GUnit(index);

        u.setMaximumSize(new Dimension(WIDTH, 35));

        pn_units.add(u);
        pn_units.updateUI();
        gunits.add(u);
    }

    private void removeUnit() {
        int size = gunits.size();

        if (size < 1)
            return;

        pn_units.remove(gunits.get(size - 1));
        gunits.remove(size - 1);
        pn_units.revalidate();
        pn_units.repaint();
    }
}