package portman.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import portman.model.Portafolio;
import portman.model.Unit;
import portman.view.components.GSelectFile;
import java.awt.Dimension;

public class DialogFillPortafolio extends GenericDialog {
    public final GSelectFile txt_frame = new GSelectFile("Encuadre");
    public final GSelectFile txt_diagnostic = new GSelectFile("Ev. Diagnostica");
    public ArrayList<GSelectFile>[] units;
    public JPanel[] pn_units;
    private Portafolio portafolio;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    @SuppressWarnings("unchecked")
    public DialogFillPortafolio(JFrame parent, boolean modal, Portafolio portafolio) {
        super(parent, modal);
        this.portafolio = portafolio;

        units = new ArrayList[portafolio.getNumberOfUnits()];
        pn_units = new JPanel[portafolio.getNumberOfUnits()];

        for (int i = 0; i < units.length; i++) {
            units[i] = new ArrayList<GSelectFile>();
        }

        setTitle("Portafolio");
        pack();
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(parent);

        initComponents();
    }

    public DialogFillPortafolio(JFrame parent, boolean modal, Portafolio portafolio, Runnable accept) {
        this(parent, modal, portafolio);
        super.accept = accept;
    }

    private void initComponents() {
        lbl_title.setText(portafolio.getSubject());

        pn_main.add(txt_frame);
        pn_main.add(txt_diagnostic);

        JPanel pn_cont_units = new JPanel();
        pn_cont_units.setLayout(new BoxLayout(pn_cont_units, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(pn_cont_units);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pn_main.add(new JLabel(" "));

        pane.add(scroll, BorderLayout.CENTER);

        for (int i = 0; i < units.length; i++) {
            Unit u = portafolio.getUnit(i);
            JLabel lbl_unit = new JLabel("Unidad " + u.getNumber());
            JButton btn_add_unit = new JButton("+");
            JButton btn_remove_unit = new JButton("-");

            JPanel pn_unit = new JPanel();
            pn_unit.setLayout(new BoxLayout(pn_unit, BoxLayout.Y_AXIS));

            pn_units[i] = pn_unit;

            JPanel pn_unit_controls = new JPanel(new BorderLayout());
            pn_unit_controls.setMaximumSize(new Dimension(WIDTH, 35));
            pn_unit_controls.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
            JPanel pn_unit_buttons = new JPanel(new FlowLayout());

            pn_cont_units.add(pn_unit);
            {
                pn_unit.add(pn_unit_controls);
                {
                    pn_unit_controls.add(lbl_unit, BorderLayout.CENTER);
                    pn_unit_controls.add(pn_unit_buttons, BorderLayout.EAST);
                    {
                        pn_unit_buttons.add(btn_add_unit);
                        pn_unit_buttons.add(btn_remove_unit);
                    }
                }
            }

            final int index = i;

            btn_add_unit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GSelectFile gsf = new GSelectFile("Actividad " + (units[index].size() + 1));
                    addGsf(gsf, index);
                }
            });

            btn_remove_unit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int size = units[index].size();

                    if (size < 1)
                        return;

                    pn_unit.remove(units[index].get(size - 1));
                    units[index].remove(size - 1);
                    pn_unit.revalidate();
                    pn_unit.repaint();
                }
            });
        }
    }

    public void addGsf(GSelectFile gsf, int index){
        gsf.setMaximumSize(new Dimension(WIDTH, 40));

        units[index].add(gsf);
        pn_units[index].add(gsf);
        pn_units[index].updateUI();
    }
}
