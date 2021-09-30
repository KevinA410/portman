package portman.view.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;

public class GUnit extends JPanel {
    private final JLabel lbl_number = new JLabel();
    private final JTextField txt_name = new JTextField();

    public GUnit(int index) {
        super();
        lbl_number.setText("Unidad " + index + " ");
        initComponents();   
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(new JLabel(" "), BorderLayout.NORTH);
        add(lbl_number, BorderLayout.WEST);
        add(txt_name, BorderLayout.CENTER);
    }

    public String getName() {
        return txt_name.getText();
    }

    public void setName(String name) {
        txt_name.setText(name);
    }
}