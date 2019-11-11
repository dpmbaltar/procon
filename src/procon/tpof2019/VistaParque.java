package procon.tpof2019;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class VistaParque extends JFrame {

    private final JTextArea ta1;
    private final JTextArea ta2;
    private final JTextArea ta3;
    private final JTextArea ta4;
    private final JTextArea ta5;

    private final JScrollPane scroll1;
    private final JScrollPane scroll2;
    private final JScrollPane scroll3;
    private final JScrollPane scroll4;
    private final JScrollPane scroll5;

    private final JProgressBar pb;

    public VistaParque() {
        super("Vista del Parque");

        ta1 = new JTextArea();
        ta1.setEditable(false);
        scroll1 = new JScrollPane(ta1);

        ta2 = new JTextArea();
        ta2.setEditable(false);
        scroll2 = new JScrollPane(ta2);

        ta3 = new JTextArea();
        ta3.setEditable(false);
        scroll3 = new JScrollPane(ta3);

        ta4 = new JTextArea();
        ta4.setEditable(false);
        scroll4 = new JScrollPane(ta4);

        ta5 = new JTextArea();
        ta5.setEditable(false);
        scroll5 = new JScrollPane(ta5);

        scroll1.setBorder(new TitledBorder("Parque"));
        scroll2.setBorder(new TitledBorder("Tour"));
        scroll3.setBorder(new TitledBorder("Carrera de gomones"));
        scroll4.setBorder(new TitledBorder("Restaurante"));
        scroll5.setBorder(new TitledBorder("Faro-Mirador"));

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(2, 3));

        contentPane.add(scroll1);
        contentPane.add(scroll2);
        //contentPane.add(scroll3);
        contentPane.add(scroll4);
        contentPane.add(scroll5);

        JPanel jp3 = new JPanel();
        jp3.setLayout(new BoxLayout(jp3, BoxLayout.PAGE_AXIS));
        pb = new JProgressBar(0, 5);
        pb.setValue(0);
        pb.setStringPainted(true);
        pb.setBorder(new TitledBorder("Gomones ocupados"));
        pb.setString("0");

        jp3.add(scroll3);
        jp3.add(pb);
        contentPane.add(jp3);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public synchronized void mostrar(String mensaje) {
        //System.out.println(mensaje);
        ta1.append(mensaje + "\n");
    }

    public synchronized void printCarrera(String mensaje) {
        System.out.println(mensaje);
        ta3.append(mensaje + "\n");
        ta3.setCaretPosition(ta3.getDocument().getLength());
        //scroll3.getVerticalScrollBar().setValue(scroll3.getVerticalScrollBar().getMaximum());
    }

    public synchronized void agregarGomon() {
        pb.setValue(pb.getValue() + 1);
        pb.setString(String.valueOf(pb.getValue()));
    }

    public synchronized void sacarGomones() {
        pb.setValue(0);
        pb.setString(String.valueOf(0));
    }

}
