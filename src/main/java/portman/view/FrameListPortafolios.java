package portman.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import portman.view.components.GPortafolio;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrameListPortafolios extends JFrame{
    private final JLabel lbl_title = new JLabel("Administrador", JLabel.CENTER);
    public final JButton btn_edit_profile = new JButton("Editar Perfil");
    public final JButton btn_generate_all = new JButton("Generar Todos");
    public final JButton btn_new_portafolio = new JButton("Nuevo Portafolio");
    public final JPanel panel_portafolios = new JPanel();
    public final ArrayList<GPortafolio> gportafolios = new ArrayList<>();
    private Runnable editProfile;
    private Runnable generateAll;
    private Runnable newPortafolio;

    public FrameListPortafolios() {
        super();

        setTitle("Administrador");
        setSize(800, 600);
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

        initComponents();
    }

    public void setRunnables(Runnable editProfile, Runnable generateAll, Runnable newPortafolio) {
        this.editProfile = editProfile;
        this.generateAll = generateAll;
        this.newPortafolio = newPortafolio;
    }

    private void initComponents() {
        JPanel root = (JPanel) getContentPane();
        JPanel container = new JPanel();
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel space = new JPanel(new BorderLayout());

        root.setLayout(new BorderLayout());
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        panel_portafolios.setLayout(new BoxLayout(panel_portafolios, BoxLayout.Y_AXIS));
        panel_portafolios.setBorder(BorderFactory.createTitledBorder("Portafolios"));

        root.add(container, BorderLayout.NORTH);
        {
            container.add(lbl_title);
            container.add(new JLabel(" "));
            container.add(buttons);
            {
                buttons.add(btn_edit_profile);
                buttons.add(btn_generate_all);
                buttons.add(btn_new_portafolio);
            }
            container.add(new JLabel(" "));
            container.add(panel_portafolios);
            {
                panel_portafolios.add(space);
                {
                    space.add(new JLabel(" "), BorderLayout.CENTER);
                }
            }
        }
    }
}
