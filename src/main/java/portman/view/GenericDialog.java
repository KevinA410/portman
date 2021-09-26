package portman.view;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GenericDialog extends JDialog{
    protected final JLabel lbl_title = new JLabel("Default Title", JLabel.CENTER);
    protected final JPanel pane = (JPanel) this.getContentPane();
    protected final JPanel pn_main = new JPanel();
    private final JButton btn_cancel = new JButton("Cancelar");
    private final JButton btn_accept = new JButton("Aceptar");
    protected Runnable accept;

    public GenericDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        btn_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GenericDialog.this.dispose();
            }
        });

        btn_accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accept.run();
            }
        });

        initComponents();
    }

    public GenericDialog(JFrame parent, boolean modal, Runnable accept) {
        this(parent, modal);
        this.accept = accept;
    }

    public void setAccept(Runnable accept) {
        this.accept = accept;
    }

    private void initComponents() {
        JPanel pn_buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pane.setLayout(new BorderLayout());
        pn_main.setLayout(new BoxLayout(pn_main, BoxLayout.Y_AXIS));

        pane.add(pn_main, BorderLayout.NORTH);
        {
            pn_main.add(lbl_title);
            pn_main.add(new JLabel(" "));
        }
        pane.add(pn_buttons, BorderLayout.SOUTH);
        {
            pn_buttons.add(btn_cancel);
            pn_buttons.add(btn_accept);
        }
    }
}
