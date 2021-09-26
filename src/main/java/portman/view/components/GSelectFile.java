package portman.view.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GSelectFile extends JPanel {
    private final JLabel lbl_tag = new JLabel();
    private final JTextField txt_file = new JTextField();
    private final JButton btn_choose = new JButton("...");

    public GSelectFile(String tag) {
        super();
        lbl_tag.setText(tag + " ");
        
        btn_choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSelectedPath();
            }
        });
        
        initComponents();
    }

    public String getText() {
        return txt_file.getText();
    }

    public void setText(String path) {
        txt_file.setText(path);
    }

    private void setSelectedPath() {
        JFileChooser fchooser = new JFileChooser();
        fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fchooser.showOpenDialog(null);

        if(returnValue != JFileChooser.APPROVE_OPTION) return;

        File selected =  fchooser.getSelectedFile();
        txt_file.setText(selected.getAbsolutePath());
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(new JLabel(" "), BorderLayout.NORTH);
        add(lbl_tag, BorderLayout.WEST);
        add(txt_file, BorderLayout.CENTER);
        add(btn_choose, BorderLayout.EAST);
    }
}
