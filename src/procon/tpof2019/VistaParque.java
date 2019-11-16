package procon.tpof2019;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * Ventana para visualizar el estado del parque.
 */
public class VistaParque extends JFrame {

    private final JTextArea ta1;
    private final JTextArea ta2;
    private final JTextArea textoCarreraGomones;
    private final JTextArea areaTextoRestaurantes;
    private final JTextArea ta5;
    private final JTextArea mensajesShop;

    private final JScrollPane scroll1;
    private final JScrollPane scroll2;
    private final JScrollPane scroll5;

    private final JProgressBar barraTren;
    private final JProgressBar barraBicicletas;
    private final JProgressBar barraGomones;

    private final JProgressBar[] rests = new JProgressBar[3];

    private static final VistaParque instance = new VistaParque();

    public static final VistaParque getInstance() {
        return instance;
    }

    private VistaParque() {
        super("Vista del Parque");

        /*try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }*/

        Container contenedor = getContentPane();
        contenedor.setLayout(new GridLayout(2, 3, 5, 5));

        ta1 = new JTextArea();
        ta1.setEditable(false);
        scroll1 = new JScrollPane(ta1);

        ta2 = new JTextArea();
        ta2.setEditable(false);
        scroll2 = new JScrollPane(ta2);

        // Carrera de gomones
        JPanel panelCarreraGomones = new JPanel();
        panelCarreraGomones.setLayout(new BoxLayout(panelCarreraGomones, BoxLayout.Y_AXIS));
        panelCarreraGomones.setBorder(new TitledBorder("Carrera de gomones"));

        textoCarreraGomones = new JTextArea();
        textoCarreraGomones.setEditable(false);

        // Barra de carga del tren
        barraTren = new JProgressBar(0, 15);
        barraTren.setValue(0);
        barraTren.setStringPainted(true);
        barraTren.setString("Tren: 0/15");

        JRadioButton ubicacionTrenParque = new JRadioButton("Parque", true);
        JRadioButton ubicacionTrenInicio = new JRadioButton("Largada");
        JRadioButton ubicacionTrenFinal = new JRadioButton("Llegada");
        ubicacionTrenParque.setEnabled(false);
        ubicacionTrenInicio.setEnabled(false);
        ubicacionTrenFinal.setEnabled(false);
        ButtonGroup ubicacionTren = new ButtonGroup();
        ubicacionTren.add(ubicacionTrenParque);
        ubicacionTren.add(ubicacionTrenInicio);
        ubicacionTren.add(ubicacionTrenFinal);
        JPanel panelTren = new JPanel();
        panelTren.setLayout(new BoxLayout(panelTren, BoxLayout.X_AXIS));
        panelTren.add(ubicacionTrenParque);
        panelTren.add(ubicacionTrenInicio);
        panelTren.add(ubicacionTrenFinal);

        // Barra de uso de bicicletas
        barraBicicletas = new JProgressBar(0, 10);
        barraBicicletas.setValue(10);
        barraBicicletas.setStringPainted(true);
        barraBicicletas.setString("Bicicletas: 10/10");

        // Barra de uso de gomones
        barraGomones = new JProgressBar(0, 5);
        barraGomones.setValue(0);
        barraGomones.setStringPainted(true);
        barraGomones.setString("Gomones: 0/5");

        // Agregar elementos
        panelCarreraGomones.add(barraTren);
        panelCarreraGomones.add(panelTren);
        panelCarreraGomones.add(barraBicicletas);
        panelCarreraGomones.add(barraGomones);
        panelCarreraGomones.add(new JScrollPane(textoCarreraGomones));
        contenedor.add(panelCarreraGomones);



        ta5 = new JTextArea();
        ta5.setEditable(false);
        scroll5 = new JScrollPane(ta5);

        scroll1.setBorder(new TitledBorder("Parque"));
        scroll2.setBorder(new TitledBorder("Tour"));
        scroll5.setBorder(new TitledBorder("Faro-Mirador"));

        contenedor.add(scroll1);
        contenedor.add(scroll2);
        contenedor.add(scroll5);

        // Restaurantes
        JPanel panelRestaurantes = new JPanel();
        panelRestaurantes.setBorder(new TitledBorder("Restaurantes"));
        panelRestaurantes.setLayout(new BoxLayout(panelRestaurantes, BoxLayout.Y_AXIS));

        areaTextoRestaurantes = new JTextArea();
        areaTextoRestaurantes.setEditable(false);

        int capacidad = 10;

        for (int i = 0; i < 3; i++) {
            rests[i] = new JProgressBar(0, capacidad);
            rests[i].setValue(0);
            rests[i].setStringPainted(true);
            rests[i].setString("0");

            panelRestaurantes.add(new JLabel(String.format("Restaurante %s:", (i + 1))));
            panelRestaurantes.add(rests[i]);
            capacidad += 5;
        }

        panelRestaurantes.add(new JScrollPane(areaTextoRestaurantes));
        contenedor.add(panelRestaurantes);

        // Shop
        JPanel panelShop = new JPanel();
        panelShop.setBorder(new TitledBorder("Shop"));
        panelShop.setLayout(new BoxLayout(panelShop, BoxLayout.PAGE_AXIS));
        mensajesShop = new JTextArea();
        mensajesShop.setEditable(false);
        JScrollPane desplShop = new JScrollPane(mensajesShop);
        panelShop.add(desplShop);

        contenedor.add(panelShop);

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

    /**
     * Muestra un mensaje en la consola y en el area de texto de la actividad "Carrera de gomones por el río".
     *
     * @param mensaje el mensaje
     */
    public synchronized void printCarreraGomones(String mensaje) {
        System.out.println(mensaje);
        textoCarreraGomones.append(mensaje + "\n");
        textoCarreraGomones.setCaretPosition(textoCarreraGomones.getDocument().getLength());
    }

    /**
     * Agrega 1 a la barra del tren.
     */
    public synchronized void agregarPasajero() {
        barraTren.setValue(barraTren.getValue() + 1);
        barraTren.setString(String.format("Tren: %d/15", barraTren.getValue()));
    }

    /**
     * Saca 1 de la barra del tren.
     */
    public synchronized void sacarPasajero() {
        barraTren.setValue(barraTren.getValue() - 1);
        barraTren.setString(String.format("Tren: %d/15", barraTren.getValue()));
    }

    /**
     * Agrega 1 a la barra de las bicicletas.
     */
    public synchronized void agregarBicicleta() {
        barraBicicletas.setValue(barraBicicletas.getValue() + 1);
        barraBicicletas.setString(String.format("Bicicletas: %d/10", barraBicicletas.getValue()));
    }

    /**
     * Saca 1 de la barra de las bicicletas.
     */
    public synchronized void sacarBicicleta() {
        barraBicicletas.setValue(barraBicicletas.getValue() - 1);
        barraBicicletas.setString(String.format("Bicicletas: %d/10", barraBicicletas.getValue()));
    }

    /**
     * Agrega 1 a la barra de gomones.
     */
    public synchronized void agregarGomon() {
        barraGomones.setValue(barraGomones.getValue() + 1);
        barraGomones.setString(String.format("Gomones: %d/5", barraGomones.getValue()));
    }

    /**
     * Saca 1 de la barra de gomones.
     */
    public synchronized void sacarGomon() {
        barraGomones.setValue(barraGomones.getValue() - 1);
        barraGomones.setString(String.format("Gomones: %d/5", barraGomones.getValue()));
    }

    public synchronized void printRestaurantes(String mensaje) {
        System.out.println(mensaje);
        areaTextoRestaurantes.append(mensaje + "\n");
        areaTextoRestaurantes.setCaretPosition(areaTextoRestaurantes.getDocument().getLength());
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

}
