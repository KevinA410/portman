package portman.view.components;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GPortafolio extends JPanel {
    private final JLabel lbl_subject = new JLabel();
    private final JButton btn_generate = new JButton("Generar");
    private final JButton btn_edit = new JButton("Editar");
    private final JButton btn_add = new JButton("Agregar");
    private final JButton btn_delete = new JButton("Borrar");

    public GPortafolio(String subject, Runnable generate, Runnable edit, Runnable add, Runnable delete) {
        super();
        lbl_subject.setText(subject + " ");

        btn_generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generate.run();
            }
        });

        btn_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edit.run();
            }
        });

        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add.run();
            }
        });

        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete.run();
            }
        });

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel buttons = new JPanel(new FlowLayout());

        add(lbl_subject, BorderLayout.CENTER);
        add(buttons, BorderLayout.EAST);
        {
            buttons.add(btn_generate);
            buttons.add(btn_edit);
            buttons.add(btn_add);
            buttons.add(btn_delete);
        }
    }
}
