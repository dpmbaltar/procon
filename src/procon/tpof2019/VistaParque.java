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

/**
 * Ventana para visualizar el estado del parque.
 */
public class VistaParque extends JFrame {

    private final JTextArea ta1;
    private final JTextArea ta2;
    private final JTextArea ta3;
    private final JTextArea ta4;
    private final JTextArea ta5;
    private final JTextArea mensajesShop;

    private final JScrollPane scroll1;
    private final JScrollPane scroll2;
    private final JScrollPane scroll3;
    private final JScrollPane scroll4;
    private final JScrollPane scroll5;

    private final JProgressBar gomones;

    private final JProgressBar[] rests = new JProgressBar[3];

    private static final VistaParque instance = new VistaParque();

    public static final VistaParque getInstance() {
        return instance;
    }

    private VistaParque() {
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
        gomones = new JProgressBar(0, 5);
        gomones.setValue(0);
        gomones.setStringPainted(true);
        gomones.setBorder(new TitledBorder("Gomones ocupados"));
        gomones.setString("0");

        jp3.add(scroll3);
        jp3.add(gomones);
        contentPane.add(jp3);

        // Restaurantes
        JPanel restaurantes = new JPanel();
        restaurantes.setBorder(new TitledBorder("Restaurantes"));
        restaurantes.setLayout(new BoxLayout(restaurantes, BoxLayout.PAGE_AXIS));
        restaurantes.add(scroll4);
        int capacidad = 10;

        for (int i = 0; i < 3; i++) {
            rests[i] = new JProgressBar(0, capacidad);
            rests[i].setValue(0);
            rests[i].setStringPainted(true);
            rests[i].setBorder(new TitledBorder("Restaurante " + (i + 1)));
            rests[i].setString("0");

            restaurantes.add(rests[i]);
            capacidad += 5;
        }

        // Shop
        JPanel panelShop = new JPanel();
        panelShop.setBorder(new TitledBorder("Shop"));
        panelShop.setLayout(new BoxLayout(panelShop, BoxLayout.PAGE_AXIS));
        mensajesShop = new JTextArea();
        mensajesShop.setEditable(false);
        JScrollPane desplShop = new JScrollPane(mensajesShop);
        panelShop.add(desplShop);
        contentPane.add(restaurantes);
        contentPane.add(panelShop);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public synchronized void printParque(String mensaje) {
        System.out.println(mensaje);
        ta1.append(mensaje + "\n");
        ta1.setCaretPosition(ta1.getDocument().getLength());
    }

    public synchronized void printTour(String mensaje) {
        System.out.println(mensaje);
        ta2.append(mensaje + "\n");
        ta2.setCaretPosition(ta2.getDocument().getLength());
    }

    public synchronized void printShop(String mensaje) {
        System.out.println(mensaje);
        mensajesShop.append(mensaje + "\n");
        mensajesShop.setCaretPosition(mensajesShop.getDocument().getLength());
    }

    public synchronized void printCarrera(String mensaje) {
        System.out.println(mensaje);
        ta3.append(mensaje + "\n");
        ta3.setCaretPosition(ta3.getDocument().getLength());
        //scroll3.getVerticalScrollBar().setValue(scroll3.getVerticalScrollBar().getMaximum());
    }

    public synchronized void printRestaurantes(String mensaje) {
        System.out.println(mensaje);
        ta4.append(mensaje + "\n");
        ta4.setCaretPosition(ta4.getDocument().getLength());
    }

    public synchronized void printFaroMirador(String mensaje) {
        System.out.println(mensaje);
        ta5.append(mensaje + "\n");
        ta5.setCaretPosition(ta5.getDocument().getLength());
    }

    public synchronized void agregarCliente(int r) {
        rests[r].setValue(rests[r].getValue() + 1);
        rests[r].setString(String.valueOf(rests[r].getValue()));
    }

    public synchronized void sacarCliente(int r) {
        rests[r].setValue(rests[r].getValue() - 1);
        rests[r].setString(String.valueOf(rests[r].getValue()));
    }

    public synchronized void agregarGomon() {
        gomones.setValue(gomones.getValue() + 1);
        gomones.setString(String.valueOf(gomones.getValue()));
    }

    public synchronized void sacarGomones() {
        gomones.setValue(0);
        gomones.setString(String.valueOf(0));
    }

}
