package portman.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import portman.view.components.GPortafolio;

public class FrameListPortafolios extends JFrame{
    public final JButton btn_new_portafolio = new JButton("Nuevo Portafolio");
    public final JButton btn_edit_profile = new JButton("Editar Perfil");
    public final JButton btn_change_path = new JButton("Cambiar Ruta");
    public final JButton btn_generate_all = new JButton("Generar Todos");
    public final JPanel pn_portafolios = new JPanel();
    public final ArrayList<GPortafolio> gportafolios = new ArrayList<>();

    private final JLabel lbl_title = new JLabel("ADMINISTRADOR", JLabel.CENTER);
    private final int WIDTH = 900;
    private final int HEIGHT = 700;
    private Runnable editProfile;
    private Runnable generateAll;
    private Runnable newPortafolio;
    private Runnable changePath;

    public FrameListPortafolios() {
        super();

        setTitle("Portafolios UPC");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btn_edit_profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProfile.run();
            }
        });

        btn_generate_all.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAll.run();
            }
        });

        btn_new_portafolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPortafolio.run();
            }
        });

        btn_change_path.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePath.run();
            }
        });

        initComponents();
    }

    public void setRunnables(Runnable editProfile, Runnable generateAll, Runnable newPortafolio, Runnable changePath) {
        this.editProfile = editProfile;
        this.generateAll = generateAll;
        this.newPortafolio = newPortafolio;
        this.changePath = changePath;
    }

    private void initComponents() {
        JPanel pane = (JPanel) getContentPane();
        JPanel pn_buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JScrollPane scroll = new JScrollPane(pn_portafolios);

        pane.setLayout(null);
        pn_portafolios.setLayout(new BoxLayout(pn_portafolios, BoxLayout.Y_AXIS));

        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createTitledBorder("PORTAFOLIOS"));

        lbl_title.setBounds(0,0, WIDTH, 25);
        pn_buttons.setBounds(5, 35, WIDTH-15, 35);
        scroll.setBounds(5, 80, WIDTH-15, HEIGHT-110);

        pn_buttons.add(btn_new_portafolio);
        pn_buttons.add(btn_edit_profile);
        pn_buttons.add(btn_change_path);
        pn_buttons.add(btn_generate_all);

        pane.add(lbl_title);
        pane.add(pn_buttons);
        pane.add(scroll);        
    }

    public void addGPortafolio(GPortafolio gp) {
        int width = WIDTH - 50;
        int height = 35;
        int y = 5 + (height * gportafolios.size());

        gp.setBounds(5, y, width, height);
        gp.setMaximumSize(new Dimension(width, height));

        pn_portafolios.add(gp);
        gportafolios.add(gp);
    }
}
