package procon.tpof2019;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

public class VistaParque extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JSpinner visitorsCountSpinner;
    private JButton startButton;
    private JProgressBar mainProgressBar;
    private JLabel timeValueLabel;
    private JSlider timeSlider;
    private JLabel parkVisitorsCountLabel;
    private JProgressBar tourBusProgressBar;
    private JProgressBar pinwheelProgressBar;
    private JTextArea parkTextArea;
    private JLabel shopVisitorsCountLabel;
    private JTextField cashRegister1TextField;
    private JTextField cashRegister2TextField;
    private JTextArea shopTextArea;
    private JSlider trainLocationSlider;
    private JProgressBar trainProgressBar;
    private JProgressBar bikesProgressBar;
    private JProgressBar simpleInflatableBoatsProgressBar;
    private JProgressBar doubleInflatableBoatsProgressBar;
    private JTextArea inflatableBoatRaceTextArea;
    private JProgressBar restaurant1ProgressBar;
    private JProgressBar restaurant2ProgressBar;
    private JProgressBar restaurant3ProgressBar;
    private JTextArea restaurantsTextArea;
    private JProgressBar stairsProgressBar;
    private JProgressBar slide1ProgressBar;
    private JProgressBar slide2ProgressBar;
    private JTextArea lighthouseTextArea;
    private JProgressBar snorkelKitProgressBar;
    private JProgressBar snorkelVisitorsCountProgressBar;
    private JTextPane snorkelTextPane;
    private JLabel threadCountValueLabel;
    private JLabel clockValueLabel;
    private JProgressBar dolphinSwimVisitorsProgressBar;
    private JProgressBar pool1ProgressBar;
    private JProgressBar pool2ProgressBar;
    private JProgressBar pool3ProgressBar;
    private JProgressBar pool4ProgressBar;
    private JTextPane dolphinSwimTextPane;

    private SimpleAttributeSet attribs = new SimpleAttributeSet();
    private static final VistaParque instancia = new VistaParque();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    VistaParque frame = VistaParque.getInstancia();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    private VistaParque() {
        /*
         * try { for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) { if
         * ("Nimbus".equals(info.getName())) {
         * UIManager.setLookAndFeel(info.getClassName()); break; } } } catch (Exception
         * e) { // If Nimbus is not available, you can set the GUI to another look and
         * feel. }
         */

        setTitle("Parque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel northPanel = new JPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);
        GridBagLayout gbl_northPanel = new GridBagLayout();
        gbl_northPanel.columnWidths = new int[] { 0, 0, 65, 0, 0, 200, 0 };
        gbl_northPanel.rowHeights = new int[] { 27, 0 };
        gbl_northPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_northPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        northPanel.setLayout(gbl_northPanel);

        startButton = new JButton("Iniciar");
        startButton.setMnemonic('I');
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                visitorsCountSpinner.setEnabled(false);
                startButton.setEnabled(false);
                mainProgressBar.setVisible(true);
                parkTextArea.setText(null);
                shopTextArea.setText(null);
                inflatableBoatRaceTextArea.setText(null);
                restaurantsTextArea.setText(null);
                lighthouseTextArea.setText(null);
                snorkelTextPane.setText(null);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Main.iniciar((Integer) visitorsCountSpinner.getValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        JLabel visitorsThreadCountLabel = new JLabel("Cantidad de visitantes:");
        visitorsThreadCountLabel.setToolTipText("Cada visitante es un hilo de ejecución");
        GridBagConstraints gbc_visitorsThreadCountLabel = new GridBagConstraints();
        gbc_visitorsThreadCountLabel.insets = new Insets(0, 0, 0, 5);
        gbc_visitorsThreadCountLabel.gridx = 0;
        gbc_visitorsThreadCountLabel.gridy = 0;
        northPanel.add(visitorsThreadCountLabel, gbc_visitorsThreadCountLabel);

        visitorsCountSpinner = new JSpinner();
        visitorsCountSpinner.setToolTipText("Cantidad de visitantes a crear");
        visitorsCountSpinner.setModel(new SpinnerNumberModel(100, 0, 1000, 5));
        GridBagConstraints gbc_visitorsCountSpinner = new GridBagConstraints();
        gbc_visitorsCountSpinner.insets = new Insets(0, 0, 0, 5);
        gbc_visitorsCountSpinner.gridx = 1;
        gbc_visitorsCountSpinner.gridy = 0;
        northPanel.add(visitorsCountSpinner, gbc_visitorsCountSpinner);
        GridBagConstraints gbc_startButton = new GridBagConstraints();
        gbc_startButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_startButton.insets = new Insets(0, 0, 0, 5);
        gbc_startButton.gridx = 2;
        gbc_startButton.gridy = 0;
        northPanel.add(startButton, gbc_startButton);

        mainProgressBar = new JProgressBar();
        mainProgressBar.setVisible(false);
        mainProgressBar.setIndeterminate(true);
        GridBagConstraints gbc_mainProgressBar = new GridBagConstraints();
        gbc_mainProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_mainProgressBar.insets = new Insets(0, 0, 0, 5);
        gbc_mainProgressBar.gridx = 3;
        gbc_mainProgressBar.gridy = 0;
        northPanel.add(mainProgressBar, gbc_mainProgressBar);

        timeValueLabel = new JLabel("1 hora = 1000 milis");
        GridBagConstraints gbc_timeValueLabel = new GridBagConstraints();
        gbc_timeValueLabel.insets = new Insets(0, 0, 0, 5);
        gbc_timeValueLabel.gridx = 4;
        gbc_timeValueLabel.gridy = 0;
        northPanel.add(timeValueLabel, gbc_timeValueLabel);

        timeSlider = new JSlider();
        timeSlider.setMinorTickSpacing(250);
        timeSlider.setPaintTicks(true);
        timeSlider.setMinimum(250);
        timeSlider.setSnapToTicks(true);
        timeSlider.addChangeListener(new ChangeListener() {
            private int lastValue = -1;

            @Override
            public void stateChanged(ChangeEvent ce) {
                int newValue = timeSlider.getValue();
                if (newValue != lastValue) {
                    lastValue = newValue;
                    Tiempo.setDuracion(1000 * 1000 / newValue);
                    if ((newValue % 250) == 0) {
                        timeValueLabel.setText(String.format("1 hora = %d milis", Tiempo.getDuracion()));
                    }
                }
            }
        });
        timeSlider.setMaximum(5000);
        timeSlider.setValue(1000);
        GridBagConstraints gbc_timeSlider = new GridBagConstraints();
        gbc_timeSlider.fill = GridBagConstraints.HORIZONTAL;
        gbc_timeSlider.gridx = 5;
        gbc_timeSlider.gridy = 0;
        northPanel.add(timeSlider, gbc_timeSlider);

        JPanel activitiesPanel = new JPanel();
        contentPane.add(activitiesPanel);
        GridBagLayout gbl_activitiesPanel = new GridBagLayout();
        gbl_activitiesPanel.columnWidths = new int[] { 0, 0, 0, 0 };
        gbl_activitiesPanel.rowHeights = new int[] { 255, 255, 0 };
        gbl_activitiesPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0 };
        gbl_activitiesPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        activitiesPanel.setLayout(gbl_activitiesPanel);

        JPanel parkPanel = new JPanel();
        GridBagConstraints gbc_parkPanel = new GridBagConstraints();
        gbc_parkPanel.fill = GridBagConstraints.BOTH;
        gbc_parkPanel.insets = new Insets(0, 0, 5, 5);
        gbc_parkPanel.gridx = 0;
        gbc_parkPanel.gridy = 0;
        activitiesPanel.add(parkPanel, gbc_parkPanel);
        parkPanel.setBorder(new TitledBorder(null, "Parque", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagLayout gbl_parkPanel = new GridBagLayout();
        gbl_parkPanel.columnWidths = new int[] { 75, 0, 0 };
        gbl_parkPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_parkPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_parkPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        parkPanel.setLayout(gbl_parkPanel);

        JLabel parkVisitorsLabel = new JLabel("Visitantes:");
        GridBagConstraints gbc_parkVisitorsLabel = new GridBagConstraints();
        gbc_parkVisitorsLabel.anchor = GridBagConstraints.WEST;
        gbc_parkVisitorsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_parkVisitorsLabel.gridx = 0;
        gbc_parkVisitorsLabel.gridy = 0;
        parkPanel.add(parkVisitorsLabel, gbc_parkVisitorsLabel);

        parkVisitorsCountLabel = new JLabel("0");
        parkVisitorsCountLabel.setToolTipText("Cantidad de visitantes en el parque");
        GridBagConstraints gbc_parkVisitorsCountLabel = new GridBagConstraints();
        gbc_parkVisitorsCountLabel.insets = new Insets(0, 0, 5, 0);
        gbc_parkVisitorsCountLabel.gridx = 1;
        gbc_parkVisitorsCountLabel.gridy = 0;
        parkPanel.add(parkVisitorsCountLabel, gbc_parkVisitorsCountLabel);

        JLabel tourBusLabel = new JLabel("Tour:");
        GridBagConstraints gbc_tourBusLabel = new GridBagConstraints();
        gbc_tourBusLabel.anchor = GridBagConstraints.WEST;
        gbc_tourBusLabel.insets = new Insets(0, 0, 5, 5);
        gbc_tourBusLabel.gridx = 0;
        gbc_tourBusLabel.gridy = 1;
        parkPanel.add(tourBusLabel, gbc_tourBusLabel);

        tourBusProgressBar = new JProgressBar();
        tourBusProgressBar.setString("0/25");
        tourBusProgressBar.setMaximum(25);
        tourBusProgressBar.setToolTipText("Colectivo del tour");
        tourBusProgressBar.setStringPainted(true);
        GridBagConstraints gbc_tourBusProgressBar = new GridBagConstraints();
        gbc_tourBusProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_tourBusProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_tourBusProgressBar.gridx = 1;
        gbc_tourBusProgressBar.gridy = 1;
        parkPanel.add(tourBusProgressBar, gbc_tourBusProgressBar);

        JLabel pinwheelLabel = new JLabel("Molitenes:");
        GridBagConstraints gbc_pinwheelLabel = new GridBagConstraints();
        gbc_pinwheelLabel.anchor = GridBagConstraints.WEST;
        gbc_pinwheelLabel.insets = new Insets(0, 0, 5, 5);
        gbc_pinwheelLabel.gridx = 0;
        gbc_pinwheelLabel.gridy = 2;
        parkPanel.add(pinwheelLabel, gbc_pinwheelLabel);

        pinwheelProgressBar = new JProgressBar();
        pinwheelProgressBar.setString("0/5");
        pinwheelProgressBar.setMaximum(5);
        pinwheelProgressBar.setToolTipText("Molinetes del parque");
        pinwheelProgressBar.setStringPainted(true);
        GridBagConstraints gbc_pinwheelProgressBar = new GridBagConstraints();
        gbc_pinwheelProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_pinwheelProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_pinwheelProgressBar.gridx = 1;
        gbc_pinwheelProgressBar.gridy = 2;
        parkPanel.add(pinwheelProgressBar, gbc_pinwheelProgressBar);

        JScrollPane parkScrollPane = new JScrollPane();
        GridBagConstraints gbc_parkScrollPane = new GridBagConstraints();
        gbc_parkScrollPane.gridwidth = 2;
        gbc_parkScrollPane.fill = GridBagConstraints.BOTH;
        gbc_parkScrollPane.gridx = 0;
        gbc_parkScrollPane.gridy = 3;
        parkPanel.add(parkScrollPane, gbc_parkScrollPane);

        parkTextArea = new JTextArea();
        parkTextArea.setEditable(false);
        parkScrollPane.setViewportView(parkTextArea);

        JPanel shopPanel = new JPanel();
        GridBagConstraints gbc_shopPanel = new GridBagConstraints();
        gbc_shopPanel.fill = GridBagConstraints.BOTH;
        gbc_shopPanel.insets = new Insets(0, 0, 5, 5);
        gbc_shopPanel.gridx = 1;
        gbc_shopPanel.gridy = 0;
        activitiesPanel.add(shopPanel, gbc_shopPanel);
        shopPanel.setBorder(new TitledBorder(null, "Shop", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagLayout gbl_shopPanel = new GridBagLayout();
        gbl_shopPanel.columnWidths = new int[] { 75, 0, 0 };
        gbl_shopPanel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_shopPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_shopPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        shopPanel.setLayout(gbl_shopPanel);

        JLabel shopVisitorsLabel = new JLabel("Visitantes:");
        GridBagConstraints gbc_shopVisitorsLabel = new GridBagConstraints();
        gbc_shopVisitorsLabel.anchor = GridBagConstraints.WEST;
        gbc_shopVisitorsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_shopVisitorsLabel.gridx = 0;
        gbc_shopVisitorsLabel.gridy = 0;
        shopPanel.add(shopVisitorsLabel, gbc_shopVisitorsLabel);

        shopVisitorsCountLabel = new JLabel("0");
        shopVisitorsCountLabel.setToolTipText("Cantidad de visitantes en el shop");
        GridBagConstraints gbc_shopVisitorsCountLabel = new GridBagConstraints();
        gbc_shopVisitorsCountLabel.insets = new Insets(0, 0, 5, 0);
        gbc_shopVisitorsCountLabel.gridx = 1;
        gbc_shopVisitorsCountLabel.gridy = 0;
        shopPanel.add(shopVisitorsCountLabel, gbc_shopVisitorsCountLabel);

        JLabel cashRegistersLabel = new JLabel("Cajas:");
        GridBagConstraints gbc_cashRegistersLabel = new GridBagConstraints();
        gbc_cashRegistersLabel.anchor = GridBagConstraints.WEST;
        gbc_cashRegistersLabel.insets = new Insets(0, 0, 5, 5);
        gbc_cashRegistersLabel.gridx = 0;
        gbc_cashRegistersLabel.gridy = 1;
        shopPanel.add(cashRegistersLabel, gbc_cashRegistersLabel);

        JPanel cashRegistersPanel = new JPanel();
        GridBagConstraints gbc_cashRegistersPanel = new GridBagConstraints();
        gbc_cashRegistersPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_cashRegistersPanel.insets = new Insets(0, 0, 5, 0);
        gbc_cashRegistersPanel.gridx = 1;
        gbc_cashRegistersPanel.gridy = 1;
        shopPanel.add(cashRegistersPanel, gbc_cashRegistersPanel);
        cashRegistersPanel.setLayout(new BoxLayout(cashRegistersPanel, BoxLayout.X_AXIS));

        cashRegister1TextField = new JTextField();
        cashRegister1TextField.setToolTipText("Clientes en caja 1");
        cashRegister1TextField.setText("0");
        cashRegister1TextField.setEditable(false);
        cashRegistersPanel.add(cashRegister1TextField);
        cashRegister1TextField.setColumns(10);

        cashRegister2TextField = new JTextField();
        cashRegister2TextField.setToolTipText("Clientes en caja 2");
        cashRegister2TextField.setEditable(false);
        cashRegister2TextField.setText("0");
        cashRegistersPanel.add(cashRegister2TextField);
        cashRegister2TextField.setColumns(10);

        JScrollPane shopScrollPane = new JScrollPane();
        GridBagConstraints gbc_shopScrollPane = new GridBagConstraints();
        gbc_shopScrollPane.gridwidth = 2;
        gbc_shopScrollPane.fill = GridBagConstraints.BOTH;
        gbc_shopScrollPane.gridx = 0;
        gbc_shopScrollPane.gridy = 2;
        shopPanel.add(shopScrollPane, gbc_shopScrollPane);

        shopTextArea = new JTextArea();
        shopTextArea.setEditable(false);
        shopScrollPane.setViewportView(shopTextArea);

        JPanel inflatableBoatRacePanel = new JPanel();
        GridBagConstraints gbc_inflatableBoatRacePanel = new GridBagConstraints();
        gbc_inflatableBoatRacePanel.insets = new Insets(0, 0, 0, 5);
        gbc_inflatableBoatRacePanel.gridheight = 2;
        gbc_inflatableBoatRacePanel.fill = GridBagConstraints.BOTH;
        gbc_inflatableBoatRacePanel.gridx = 2;
        gbc_inflatableBoatRacePanel.gridy = 0;
        activitiesPanel.add(inflatableBoatRacePanel, gbc_inflatableBoatRacePanel);
        inflatableBoatRacePanel.setBorder(
                new TitledBorder(null, "Carrera de gomones", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagLayout gbl_inflatableBoatRacePanel = new GridBagLayout();
        gbl_inflatableBoatRacePanel.columnWidths = new int[] { 75, 0, 0 };
        gbl_inflatableBoatRacePanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gbl_inflatableBoatRacePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_inflatableBoatRacePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
        inflatableBoatRacePanel.setLayout(gbl_inflatableBoatRacePanel);

        JPanel trainLocationPanel = new JPanel();
        GridBagConstraints gbc_trainLocationPanel = new GridBagConstraints();
        gbc_trainLocationPanel.gridwidth = 2;
        gbc_trainLocationPanel.insets = new Insets(0, 0, 5, 0);
        gbc_trainLocationPanel.fill = GridBagConstraints.BOTH;
        gbc_trainLocationPanel.gridx = 0;
        gbc_trainLocationPanel.gridy = 0;
        inflatableBoatRacePanel.add(trainLocationPanel, gbc_trainLocationPanel);

        trainLocationSlider = new JSlider();
        trainLocationSlider.setPaintLabels(true);
        trainLocationSlider.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        trainLocationSlider.setPaintTrack(false);
        trainLocationSlider.setEnabled(false);
        trainLocationSlider.setFocusable(false);
        trainLocationSlider.setValue(0);
        trainLocationSlider.setSnapToTicks(true);
        trainLocationSlider.setPaintTicks(true);
        trainLocationSlider.setMinorTickSpacing(1);
        trainLocationSlider.setMaximum(2);
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(0, new JLabel("Parque"));
        labels.put(1, new JLabel("Inicio"));
        labels.put(2, new JLabel("Fin"));
        trainLocationSlider.setLabelTable(labels);
        trainLocationPanel.add(trainLocationSlider);

        JLabel trainLabel = new JLabel("Tren:");
        GridBagConstraints gbc_trainLabel = new GridBagConstraints();
        gbc_trainLabel.anchor = GridBagConstraints.WEST;
        gbc_trainLabel.insets = new Insets(0, 0, 5, 5);
        gbc_trainLabel.gridx = 0;
        gbc_trainLabel.gridy = 1;
        inflatableBoatRacePanel.add(trainLabel, gbc_trainLabel);

        trainProgressBar = new JProgressBar();
        trainProgressBar.setString("0/15");
        trainProgressBar.setMaximum(15);
        trainProgressBar.setStringPainted(true);
        GridBagConstraints gbc_trainProgressBar = new GridBagConstraints();
        gbc_trainProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_trainProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_trainProgressBar.gridx = 1;
        gbc_trainProgressBar.gridy = 1;
        inflatableBoatRacePanel.add(trainProgressBar, gbc_trainProgressBar);

        JLabel bikesLabel = new JLabel("Bicicletas:");
        GridBagConstraints gbc_bikesLabel = new GridBagConstraints();
        gbc_bikesLabel.anchor = GridBagConstraints.WEST;
        gbc_bikesLabel.insets = new Insets(0, 0, 5, 5);
        gbc_bikesLabel.gridx = 0;
        gbc_bikesLabel.gridy = 2;
        inflatableBoatRacePanel.add(bikesLabel, gbc_bikesLabel);

        bikesProgressBar = new JProgressBar();
        bikesProgressBar.setString("10/10");
        bikesProgressBar.setValue(10);
        bikesProgressBar.setMaximum(10);
        bikesProgressBar.setStringPainted(true);
        GridBagConstraints gbc_bikesProgressBar = new GridBagConstraints();
        gbc_bikesProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_bikesProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_bikesProgressBar.gridx = 1;
        gbc_bikesProgressBar.gridy = 2;
        inflatableBoatRacePanel.add(bikesProgressBar, gbc_bikesProgressBar);

        JLabel simpleInflatableBoatsLabel = new JLabel("Gomones simples:");
        GridBagConstraints gbc_simpleInflatableBoatsLabel = new GridBagConstraints();
        gbc_simpleInflatableBoatsLabel.anchor = GridBagConstraints.WEST;
        gbc_simpleInflatableBoatsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_simpleInflatableBoatsLabel.gridx = 0;
        gbc_simpleInflatableBoatsLabel.gridy = 3;
        inflatableBoatRacePanel.add(simpleInflatableBoatsLabel, gbc_simpleInflatableBoatsLabel);

        simpleInflatableBoatsProgressBar = new JProgressBar();
        simpleInflatableBoatsProgressBar.setString("5/5");
        simpleInflatableBoatsProgressBar.setValue(5);
        simpleInflatableBoatsProgressBar.setMaximum(5);
        simpleInflatableBoatsProgressBar.setStringPainted(true);
        GridBagConstraints gbc_simpleInflatableBoatsProgressBar = new GridBagConstraints();
        gbc_simpleInflatableBoatsProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_simpleInflatableBoatsProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_simpleInflatableBoatsProgressBar.gridx = 1;
        gbc_simpleInflatableBoatsProgressBar.gridy = 3;
        inflatableBoatRacePanel.add(simpleInflatableBoatsProgressBar, gbc_simpleInflatableBoatsProgressBar);

        JLabel doubleInflatableBoatsLabel = new JLabel("Gomones dobles:");
        GridBagConstraints gbc_doubleInflatableBoatsLabel = new GridBagConstraints();
        gbc_doubleInflatableBoatsLabel.anchor = GridBagConstraints.WEST;
        gbc_doubleInflatableBoatsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_doubleInflatableBoatsLabel.gridx = 0;
        gbc_doubleInflatableBoatsLabel.gridy = 4;
        inflatableBoatRacePanel.add(doubleInflatableBoatsLabel, gbc_doubleInflatableBoatsLabel);

        doubleInflatableBoatsProgressBar = new JProgressBar();
        doubleInflatableBoatsProgressBar.setString("5/5");
        doubleInflatableBoatsProgressBar.setValue(5);
        doubleInflatableBoatsProgressBar.setMaximum(5);
        doubleInflatableBoatsProgressBar.setStringPainted(true);
        GridBagConstraints gbc_doubleInflatableBoatsProgressBar = new GridBagConstraints();
        gbc_doubleInflatableBoatsProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_doubleInflatableBoatsProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_doubleInflatableBoatsProgressBar.gridx = 1;
        gbc_doubleInflatableBoatsProgressBar.gridy = 4;
        inflatableBoatRacePanel.add(doubleInflatableBoatsProgressBar, gbc_doubleInflatableBoatsProgressBar);

        JScrollPane inflatableBoatRaceScrollPane = new JScrollPane();
        GridBagConstraints gbc_inflatableBoatRaceScrollPane = new GridBagConstraints();
        gbc_inflatableBoatRaceScrollPane.gridwidth = 2;
        gbc_inflatableBoatRaceScrollPane.fill = GridBagConstraints.BOTH;
        gbc_inflatableBoatRaceScrollPane.gridx = 0;
        gbc_inflatableBoatRaceScrollPane.gridy = 5;
        inflatableBoatRacePanel.add(inflatableBoatRaceScrollPane, gbc_inflatableBoatRaceScrollPane);

        inflatableBoatRaceTextArea = new JTextArea();
        inflatableBoatRaceTextArea.setEditable(false);
        inflatableBoatRaceScrollPane.setViewportView(inflatableBoatRaceTextArea);

        JPanel dolphinSwimPanel = new JPanel();
        dolphinSwimPanel.setBorder(
                new TitledBorder(null, "Nado con delfines", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_dolphinSwimPanel = new GridBagConstraints();
        gbc_dolphinSwimPanel.insets = new Insets(0, 0, 5, 0);
        gbc_dolphinSwimPanel.fill = GridBagConstraints.BOTH;
        gbc_dolphinSwimPanel.gridx = 3;
        gbc_dolphinSwimPanel.gridy = 0;
        activitiesPanel.add(dolphinSwimPanel, gbc_dolphinSwimPanel);
        GridBagLayout gbl_dolphinSwimPanel = new GridBagLayout();
        gbl_dolphinSwimPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_dolphinSwimPanel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_dolphinSwimPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_dolphinSwimPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        dolphinSwimPanel.setLayout(gbl_dolphinSwimPanel);

        JLabel dolphinSwimVisitorsLabel = new JLabel("Visitantes:");
        GridBagConstraints gbc_dolphinSwimVisitorsLabel = new GridBagConstraints();
        gbc_dolphinSwimVisitorsLabel.anchor = GridBagConstraints.WEST;
        gbc_dolphinSwimVisitorsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_dolphinSwimVisitorsLabel.gridx = 0;
        gbc_dolphinSwimVisitorsLabel.gridy = 0;
        dolphinSwimPanel.add(dolphinSwimVisitorsLabel, gbc_dolphinSwimVisitorsLabel);

        dolphinSwimVisitorsProgressBar = new JProgressBar();
        dolphinSwimVisitorsProgressBar.setString("0");
        dolphinSwimVisitorsProgressBar.setToolTipText("Cantidad de visitantes nadando con delfines");
        dolphinSwimVisitorsProgressBar.setStringPainted(true);
        GridBagConstraints gbc_dolphinSwimVisitorsProgressBar = new GridBagConstraints();
        gbc_dolphinSwimVisitorsProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_dolphinSwimVisitorsProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_dolphinSwimVisitorsProgressBar.gridx = 1;
        gbc_dolphinSwimVisitorsProgressBar.gridy = 0;
        dolphinSwimPanel.add(dolphinSwimVisitorsProgressBar, gbc_dolphinSwimVisitorsProgressBar);

        JPanel poolsPanel = new JPanel();
        poolsPanel.setBorder(new TitledBorder(null, "Piletas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_poolsPanel = new GridBagConstraints();
        gbc_poolsPanel.gridwidth = 2;
        gbc_poolsPanel.insets = new Insets(0, 0, 5, 0);
        gbc_poolsPanel.fill = GridBagConstraints.BOTH;
        gbc_poolsPanel.gridx = 0;
        gbc_poolsPanel.gridy = 1;
        dolphinSwimPanel.add(poolsPanel, gbc_poolsPanel);
        poolsPanel.setLayout(new GridLayout(2, 2, 4, 4));

        pool1ProgressBar = new JProgressBar();
        pool1ProgressBar.setToolTipText("Pileta 1");
        pool1ProgressBar.setString("0/10");
        poolsPanel.add(pool1ProgressBar);
        pool1ProgressBar.setStringPainted(true);
        pool1ProgressBar.setMaximum(10);

        pool2ProgressBar = new JProgressBar();
        pool2ProgressBar.setToolTipText("Pileta 2");
        pool2ProgressBar.setString("0/10");
        poolsPanel.add(pool2ProgressBar);
        pool2ProgressBar.setStringPainted(true);
        pool2ProgressBar.setMaximum(10);

        pool3ProgressBar = new JProgressBar();
        pool3ProgressBar.setToolTipText("Pileta 3");
        pool3ProgressBar.setString("0/10");
        poolsPanel.add(pool3ProgressBar);
        pool3ProgressBar.setMaximum(10);
        pool3ProgressBar.setStringPainted(true);

        pool4ProgressBar = new JProgressBar();
        pool4ProgressBar.setToolTipText("Pileta 4");
        pool4ProgressBar.setString("0/10");
        poolsPanel.add(pool4ProgressBar);
        pool4ProgressBar.setStringPainted(true);
        pool4ProgressBar.setMaximum(10);

        JScrollPane dolphinSwimScrollPane = new JScrollPane();
        GridBagConstraints gbc_dolphinSwimScrollPane = new GridBagConstraints();
        gbc_dolphinSwimScrollPane.gridwidth = 2;
        gbc_dolphinSwimScrollPane.fill = GridBagConstraints.BOTH;
        gbc_dolphinSwimScrollPane.gridx = 0;
        gbc_dolphinSwimScrollPane.gridy = 2;
        dolphinSwimPanel.add(dolphinSwimScrollPane, gbc_dolphinSwimScrollPane);

        dolphinSwimTextPane = new JTextPane();
        dolphinSwimTextPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        dolphinSwimTextPane.setEditable(false);
        dolphinSwimScrollPane.setViewportView(dolphinSwimTextPane);

        JPanel restaurantsPanel = new JPanel();
        GridBagConstraints gbc_restaurantsPanel = new GridBagConstraints();
        gbc_restaurantsPanel.fill = GridBagConstraints.BOTH;
        gbc_restaurantsPanel.insets = new Insets(0, 0, 0, 5);
        gbc_restaurantsPanel.gridx = 0;
        gbc_restaurantsPanel.gridy = 1;
        activitiesPanel.add(restaurantsPanel, gbc_restaurantsPanel);
        restaurantsPanel.setBorder(
                new TitledBorder(null, "Restaurantes", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagLayout gbl_restaurantsPanel = new GridBagLayout();
        gbl_restaurantsPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_restaurantsPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_restaurantsPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_restaurantsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        restaurantsPanel.setLayout(gbl_restaurantsPanel);

        JLabel restaurant1Label = new JLabel("Restaurante 1:");
        GridBagConstraints gbc_restaurant1Label = new GridBagConstraints();
        gbc_restaurant1Label.anchor = GridBagConstraints.WEST;
        gbc_restaurant1Label.insets = new Insets(0, 0, 5, 5);
        gbc_restaurant1Label.gridx = 0;
        gbc_restaurant1Label.gridy = 0;
        restaurantsPanel.add(restaurant1Label, gbc_restaurant1Label);

        restaurant1ProgressBar = new JProgressBar();
        restaurant1ProgressBar.setMaximum(10);
        restaurant1ProgressBar.setString("0/10");
        restaurant1ProgressBar.setToolTipText("Lugares ocupados en restaurante 1");
        restaurant1ProgressBar.setStringPainted(true);
        GridBagConstraints gbc_restaurant1ProgressBar = new GridBagConstraints();
        gbc_restaurant1ProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_restaurant1ProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_restaurant1ProgressBar.gridx = 1;
        gbc_restaurant1ProgressBar.gridy = 0;
        restaurantsPanel.add(restaurant1ProgressBar, gbc_restaurant1ProgressBar);

        JLabel restaurant2Label = new JLabel("Restaurante 2:");
        GridBagConstraints gbc_restaurant2Label = new GridBagConstraints();
        gbc_restaurant2Label.insets = new Insets(0, 0, 5, 5);
        gbc_restaurant2Label.gridx = 0;
        gbc_restaurant2Label.gridy = 1;
        restaurantsPanel.add(restaurant2Label, gbc_restaurant2Label);

        restaurant2ProgressBar = new JProgressBar();
        restaurant2ProgressBar.setMaximum(15);
        restaurant2ProgressBar.setString("0/15");
        restaurant2ProgressBar.setToolTipText("Lugares ocupados en restaurante 2");
        restaurant2ProgressBar.setStringPainted(true);
        GridBagConstraints gbc_restaurant2ProgressBar = new GridBagConstraints();
        gbc_restaurant2ProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_restaurant2ProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_restaurant2ProgressBar.gridx = 1;
        gbc_restaurant2ProgressBar.gridy = 1;
        restaurantsPanel.add(restaurant2ProgressBar, gbc_restaurant2ProgressBar);

        JLabel restaurant3Label = new JLabel("Restaurante 3:");
        GridBagConstraints gbc_restaurant3Label = new GridBagConstraints();
        gbc_restaurant3Label.insets = new Insets(0, 0, 5, 5);
        gbc_restaurant3Label.gridx = 0;
        gbc_restaurant3Label.gridy = 2;
        restaurantsPanel.add(restaurant3Label, gbc_restaurant3Label);

        restaurant3ProgressBar = new JProgressBar();
        restaurant3ProgressBar.setMaximum(20);
        restaurant3ProgressBar.setString("0/20");
        restaurant3ProgressBar.setToolTipText("Lugares ocupados en restaurante 3");
        restaurant3ProgressBar.setStringPainted(true);
        GridBagConstraints gbc_restaurant3ProgressBar = new GridBagConstraints();
        gbc_restaurant3ProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_restaurant3ProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_restaurant3ProgressBar.gridx = 1;
        gbc_restaurant3ProgressBar.gridy = 2;
        restaurantsPanel.add(restaurant3ProgressBar, gbc_restaurant3ProgressBar);

        JScrollPane restaurantsScrollPane = new JScrollPane();
        GridBagConstraints gbc_restaurantsScrollPane = new GridBagConstraints();
        gbc_restaurantsScrollPane.gridwidth = 2;
        gbc_restaurantsScrollPane.fill = GridBagConstraints.BOTH;
        gbc_restaurantsScrollPane.gridx = 0;
        gbc_restaurantsScrollPane.gridy = 3;
        restaurantsPanel.add(restaurantsScrollPane, gbc_restaurantsScrollPane);

        restaurantsTextArea = new JTextArea();
        restaurantsTextArea.setEditable(false);
        restaurantsScrollPane.setViewportView(restaurantsTextArea);

        JPanel lighthousePanel = new JPanel();
        GridBagConstraints gbc_lighthousePanel = new GridBagConstraints();
        gbc_lighthousePanel.fill = GridBagConstraints.BOTH;
        gbc_lighthousePanel.insets = new Insets(0, 0, 0, 5);
        gbc_lighthousePanel.gridx = 1;
        gbc_lighthousePanel.gridy = 1;
        activitiesPanel.add(lighthousePanel, gbc_lighthousePanel);
        lighthousePanel.setBorder(
                new TitledBorder(null, "Faro-Mirador", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagLayout gbl_lighthousePanel = new GridBagLayout();
        gbl_lighthousePanel.columnWidths = new int[] { 75, 0, 0 };
        gbl_lighthousePanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_lighthousePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_lighthousePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        lighthousePanel.setLayout(gbl_lighthousePanel);

        JLabel stairsLabel = new JLabel("Escalera:");
        GridBagConstraints gbc_stairsLabel = new GridBagConstraints();
        gbc_stairsLabel.anchor = GridBagConstraints.WEST;
        gbc_stairsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_stairsLabel.gridx = 0;
        gbc_stairsLabel.gridy = 0;
        lighthousePanel.add(stairsLabel, gbc_stairsLabel);

        stairsProgressBar = new JProgressBar();
        stairsProgressBar.setMaximum(10);
        stairsProgressBar.setString("0/10");
        stairsProgressBar.setToolTipText("Cantidad de visitantes en la escalera");
        stairsProgressBar.setStringPainted(true);
        GridBagConstraints gbc_stairsProgressBar = new GridBagConstraints();
        gbc_stairsProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_stairsProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_stairsProgressBar.gridx = 1;
        gbc_stairsProgressBar.gridy = 0;
        lighthousePanel.add(stairsProgressBar, gbc_stairsProgressBar);

        JLabel slide1Label = new JLabel("Tobogán 1:");
        GridBagConstraints gbc_slide1Label = new GridBagConstraints();
        gbc_slide1Label.anchor = GridBagConstraints.WEST;
        gbc_slide1Label.insets = new Insets(0, 0, 5, 5);
        gbc_slide1Label.gridx = 0;
        gbc_slide1Label.gridy = 1;
        lighthousePanel.add(slide1Label, gbc_slide1Label);

        slide1ProgressBar = new JProgressBar();
        slide1ProgressBar.setMaximum(1);
        slide1ProgressBar.setString("Desocupado");
        slide1ProgressBar.setToolTipText("Estado del tobogán 1");
        slide1ProgressBar.setStringPainted(true);
        GridBagConstraints gbc_slide1ProgressBar = new GridBagConstraints();
        gbc_slide1ProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_slide1ProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_slide1ProgressBar.gridx = 1;
        gbc_slide1ProgressBar.gridy = 1;
        lighthousePanel.add(slide1ProgressBar, gbc_slide1ProgressBar);

        JLabel slide2Label = new JLabel("Tobogán 2:");
        GridBagConstraints gbc_slide2Label = new GridBagConstraints();
        gbc_slide2Label.anchor = GridBagConstraints.WEST;
        gbc_slide2Label.insets = new Insets(0, 0, 5, 5);
        gbc_slide2Label.gridx = 0;
        gbc_slide2Label.gridy = 2;
        lighthousePanel.add(slide2Label, gbc_slide2Label);

        slide2ProgressBar = new JProgressBar();
        slide2ProgressBar.setMaximum(1);
        slide2ProgressBar.setString("Desocupado");
        slide2ProgressBar.setToolTipText("Estado del tobogán 2");
        slide2ProgressBar.setStringPainted(true);
        GridBagConstraints gbc_slide2ProgressBar = new GridBagConstraints();
        gbc_slide2ProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_slide2ProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_slide2ProgressBar.gridx = 1;
        gbc_slide2ProgressBar.gridy = 2;
        lighthousePanel.add(slide2ProgressBar, gbc_slide2ProgressBar);

        JScrollPane lighthouseScrollPane = new JScrollPane();
        GridBagConstraints gbc_lighthouseScrollPane = new GridBagConstraints();
        gbc_lighthouseScrollPane.gridwidth = 2;
        gbc_lighthouseScrollPane.fill = GridBagConstraints.BOTH;
        gbc_lighthouseScrollPane.gridx = 0;
        gbc_lighthouseScrollPane.gridy = 3;
        lighthousePanel.add(lighthouseScrollPane, gbc_lighthouseScrollPane);

        lighthouseTextArea = new JTextArea();
        lighthouseTextArea.setEditable(false);
        lighthouseScrollPane.setViewportView(lighthouseTextArea);

        JPanel snorkelPanel = new JPanel();
        snorkelPanel.setBorder(new TitledBorder(null, "Snorkel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GridBagConstraints gbc_snorkelPanel = new GridBagConstraints();
        gbc_snorkelPanel.fill = GridBagConstraints.BOTH;
        gbc_snorkelPanel.gridx = 3;
        gbc_snorkelPanel.gridy = 1;
        activitiesPanel.add(snorkelPanel, gbc_snorkelPanel);
        GridBagLayout gbl_snorkelPanel = new GridBagLayout();
        gbl_snorkelPanel.columnWidths = new int[] { 75, 0, 0 };
        gbl_snorkelPanel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_snorkelPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_snorkelPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        snorkelPanel.setLayout(gbl_snorkelPanel);

        JLabel snorkelKitCountLabel = new JLabel("Equipos:");
        GridBagConstraints gbc_snorkelKitCountLabel = new GridBagConstraints();
        gbc_snorkelKitCountLabel.anchor = GridBagConstraints.WEST;
        gbc_snorkelKitCountLabel.insets = new Insets(0, 0, 5, 5);
        gbc_snorkelKitCountLabel.gridx = 0;
        gbc_snorkelKitCountLabel.gridy = 0;
        snorkelPanel.add(snorkelKitCountLabel, gbc_snorkelKitCountLabel);

        snorkelKitProgressBar = new JProgressBar();
        snorkelKitProgressBar.setToolTipText("Cantidad de equipos disponibles");
        snorkelKitProgressBar.setString("0/20");
        snorkelKitProgressBar.setValue(20);
        snorkelKitProgressBar.setStringPainted(true);
        snorkelKitProgressBar.setMaximum(20);
        GridBagConstraints gbc_snorkelKitProgressBar = new GridBagConstraints();
        gbc_snorkelKitProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_snorkelKitProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_snorkelKitProgressBar.gridx = 1;
        gbc_snorkelKitProgressBar.gridy = 0;
        snorkelPanel.add(snorkelKitProgressBar, gbc_snorkelKitProgressBar);

        JLabel snorkelVisitorsCountLabel = new JLabel("Visitantes:");
        GridBagConstraints gbc_snorkelVisitorsCountLabel = new GridBagConstraints();
        gbc_snorkelVisitorsCountLabel.anchor = GridBagConstraints.WEST;
        gbc_snorkelVisitorsCountLabel.insets = new Insets(0, 0, 5, 5);
        gbc_snorkelVisitorsCountLabel.gridx = 0;
        gbc_snorkelVisitorsCountLabel.gridy = 1;
        snorkelPanel.add(snorkelVisitorsCountLabel, gbc_snorkelVisitorsCountLabel);

        snorkelVisitorsCountProgressBar = new JProgressBar();
        snorkelVisitorsCountProgressBar.setString("0");
        snorkelVisitorsCountProgressBar.setToolTipText("Cantidad de visitantes haciendo Snorkel");
        snorkelVisitorsCountProgressBar.setStringPainted(true);
        GridBagConstraints gbc_snorkelVisitorsCountProgressBar = new GridBagConstraints();
        gbc_snorkelVisitorsCountProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_snorkelVisitorsCountProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_snorkelVisitorsCountProgressBar.gridx = 1;
        gbc_snorkelVisitorsCountProgressBar.gridy = 1;
        snorkelPanel.add(snorkelVisitorsCountProgressBar, gbc_snorkelVisitorsCountProgressBar);

        JScrollPane snorkelScrollPane = new JScrollPane();
        GridBagConstraints gbc_snorkelScrollPane = new GridBagConstraints();
        gbc_snorkelScrollPane.gridwidth = 2;
        gbc_snorkelScrollPane.fill = GridBagConstraints.BOTH;
        gbc_snorkelScrollPane.gridx = 0;
        gbc_snorkelScrollPane.gridy = 2;
        snorkelPanel.add(snorkelScrollPane, gbc_snorkelScrollPane);

        snorkelTextPane = new JTextPane();
        snorkelTextPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        snorkelTextPane.setEditable(false);
        snorkelScrollPane.setViewportView(snorkelTextPane);

        JPanel southPanel = new JPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);
        southPanel.setLayout(new GridLayout(0, 2, 0, 0));

        JPanel leftSouthPanel = new JPanel();
        FlowLayout fl_leftSouthPanel = (FlowLayout) leftSouthPanel.getLayout();
        fl_leftSouthPanel.setHgap(4);
        fl_leftSouthPanel.setAlignment(FlowLayout.LEFT);
        fl_leftSouthPanel.setVgap(0);
        southPanel.add(leftSouthPanel);

        JLabel threadCountLabel = new JLabel("Hilos en ejecución:");
        leftSouthPanel.add(threadCountLabel);

        threadCountValueLabel = new JLabel("0");
        leftSouthPanel.add(threadCountValueLabel);

        JPanel rightSouthPanel = new JPanel();
        FlowLayout fl_rightSouthPanel = (FlowLayout) rightSouthPanel.getLayout();
        fl_rightSouthPanel.setHgap(4);
        fl_rightSouthPanel.setAlignment(FlowLayout.RIGHT);
        fl_rightSouthPanel.setVgap(0);
        southPanel.add(rightSouthPanel);

        JLabel clockLabel = new JLabel("Hora del parque:");
        rightSouthPanel.add(clockLabel);

        clockValueLabel = new JLabel("00:00");
        rightSouthPanel.add(clockValueLabel);
    }

    public static final VistaParque getInstancia() {
        return instancia;
    }

    public synchronized void finalizar() {
        mainProgressBar.setVisible(false);
        startButton.setEnabled(true);
        visitorsCountSpinner.setEnabled(true);
    }

    public synchronized void printParque(String mensaje) {
        System.out.println(mensaje);
        parkTextArea.append(mensaje + "\n");
        parkTextArea.setCaretPosition(parkTextArea.getDocument().getLength());
    }

    public synchronized void agregarVisitanteMolinete() {
        pinwheelProgressBar.setValue(pinwheelProgressBar.getValue() + 1);
        pinwheelProgressBar.setString(pinwheelProgressBar.getValue() + "/" + Parque.CANTIDAD_MOLINETES);
    }

    public synchronized void sacarVisitanteMolinete() {
        pinwheelProgressBar.setValue(pinwheelProgressBar.getValue() - 1);
        pinwheelProgressBar.setString(pinwheelProgressBar.getValue() + "/" + Parque.CANTIDAD_MOLINETES);
    }

    public synchronized void agregarVisitante() {
        parkVisitorsCountLabel.setText(String.valueOf(Integer.valueOf(parkVisitorsCountLabel.getText()) + 1));
    }

    public synchronized void sacarVisitante() {
        parkVisitorsCountLabel.setText(String.valueOf(Integer.valueOf(parkVisitorsCountLabel.getText()) - 1));
    }

    @Deprecated
    public synchronized void printTour(String mensaje) {
        System.out.println(mensaje);
        parkTextArea.append(mensaje + "\n");
        parkTextArea.setCaretPosition(parkTextArea.getDocument().getLength());
    }

    public synchronized void printShop(String mensaje) {
        System.out.println(mensaje);
        shopTextArea.append(mensaje + "\n");
        shopTextArea.setCaretPosition(shopTextArea.getDocument().getLength());
    }

    public synchronized void agregarClienteShop() {
        shopVisitorsCountLabel.setText(String.valueOf(Integer.valueOf(shopVisitorsCountLabel.getText()) + 1));
    }

    public synchronized void sacarClienteShop() {
        shopVisitorsCountLabel.setText(String.valueOf(Integer.valueOf(shopVisitorsCountLabel.getText()) - 1));
    }

    public synchronized void agregarClienteCaja(int i) {
        JTextField textField = null;

        switch (i) {
        case 1:
            textField = cashRegister1TextField;
            break;
        case 2:
            textField = cashRegister2TextField;
            break;
        }

        if (textField != null)
            textField.setText(String.valueOf(Integer.valueOf(textField.getText()) + 1));
    }

    public synchronized void sacarClienteCaja(int i) {
        JTextField textField = null;

        switch (i) {
        case 1:
            textField = cashRegister1TextField;
            break;
        case 2:
            textField = cashRegister2TextField;
            break;
        }

        if (textField != null)
            textField.setText(String.valueOf(Integer.valueOf(textField.getText()) - 1));
    }

    /**
     * Muestra un mensaje en la consola y en el area de texto de la actividad
     * "Carrera de gomones por el río".
     *
     * @param mensaje el mensaje
     */
    public synchronized void printCarreraGomones(String mensaje) {
        System.out.println(mensaje);
        inflatableBoatRaceTextArea.append(mensaje + "\n");
        inflatableBoatRaceTextArea.setCaretPosition(inflatableBoatRaceTextArea.getDocument().getLength());
    }

    public synchronized void ubicarTren(int ubicacion) {
        trainLocationSlider.setValue(ubicacion % 3);
    }

    /**
     * Agrega 1 a la barra del tren.
     */
    public synchronized void agregarPasajero() {
        trainProgressBar.setValue(trainProgressBar.getValue() + 1);
        trainProgressBar.setString(trainProgressBar.getValue() + "/" + CarreraGomones.CAPACIDAD_TREN);
    }

    /**
     * Saca 1 de la barra del tren.
     */
    public synchronized void sacarPasajero() {
        trainProgressBar.setValue(trainProgressBar.getValue() - 1);
        trainProgressBar.setString(trainProgressBar.getValue() + "/" + CarreraGomones.CAPACIDAD_TREN);
    }

    /**
     * Agrega 1 a la barra de las bicicletas.
     */
    public synchronized void agregarBicicleta() {
        bikesProgressBar.setValue(bikesProgressBar.getValue() + 1);
        bikesProgressBar.setString(String.format("%d/10", bikesProgressBar.getValue()));
    }

    /**
     * Saca 1 de la barra de las bicicletas.
     */
    public synchronized void sacarBicicleta() {
        bikesProgressBar.setValue(bikesProgressBar.getValue() - 1);
        bikesProgressBar.setString(String.format("%d/10", bikesProgressBar.getValue()));
    }

    /**
     * Agrega 1 a la barra de gomones simples.
     */
    public synchronized void agregarGomonSimple() {
        simpleInflatableBoatsProgressBar.setValue(simpleInflatableBoatsProgressBar.getValue() + 1);
        simpleInflatableBoatsProgressBar
                .setString(simpleInflatableBoatsProgressBar.getValue() + "/" + CarreraGomones.CANTIDAD_GOMONES_SIMPLES);
    }

    /**
     * Saca 1 de la barra de gomones simples.
     */
    public synchronized void sacarGomonSimple() {
        simpleInflatableBoatsProgressBar.setValue(simpleInflatableBoatsProgressBar.getValue() - 1);
        simpleInflatableBoatsProgressBar
                .setString(simpleInflatableBoatsProgressBar.getValue() + "/" + CarreraGomones.CANTIDAD_GOMONES_SIMPLES);
    }

    /**
     * Agrega 1 a la barra de gomones dobles.
     */
    public synchronized void agregarGomonDoble() {
        doubleInflatableBoatsProgressBar.setValue(doubleInflatableBoatsProgressBar.getValue() + 1);
        doubleInflatableBoatsProgressBar
                .setString(doubleInflatableBoatsProgressBar.getValue() + "/" + CarreraGomones.CANTIDAD_GOMONES_DOBLES);
    }

    /**
     * Saca 1 de la barra de gomones dobles.
     */
    public synchronized void sacarGomonDoble() {
        doubleInflatableBoatsProgressBar.setValue(doubleInflatableBoatsProgressBar.getValue() - 1);
        doubleInflatableBoatsProgressBar
                .setString(doubleInflatableBoatsProgressBar.getValue() + "/" + CarreraGomones.CANTIDAD_GOMONES_DOBLES);
    }

    public synchronized void printRestaurantes(String mensaje) {
        System.out.println(mensaje);
        restaurantsTextArea.append(mensaje + "\n");
        restaurantsTextArea.setCaretPosition(restaurantsTextArea.getDocument().getLength());
    }

    public synchronized void agregarClienteRestaurante(int restaurante) {
        JProgressBar progressBar = null;
        int capacidad = 0;

        switch (restaurante) {
        case 0:
            progressBar = restaurant1ProgressBar;
            capacidad = 10;
            break;
        case 1:
            progressBar = restaurant2ProgressBar;
            capacidad = 15;
            break;
        case 2:
            progressBar = restaurant3ProgressBar;
            capacidad = 20;
            break;
        }

        if (progressBar != null) {
            progressBar.setValue(progressBar.getValue() + 1);
            progressBar.setString(String.format("%d/%d", progressBar.getValue(), capacidad));
        }
    }

    public synchronized void sacarClienteRestaurante(int restaurante) {
        JProgressBar progressBar = null;
        int capacidad = 0;

        switch (restaurante) {
        case 0:
            progressBar = restaurant1ProgressBar;
            capacidad = 10;
            break;
        case 1:
            progressBar = restaurant2ProgressBar;
            capacidad = 15;
            break;
        case 2:
            progressBar = restaurant3ProgressBar;
            capacidad = 20;
            break;
        }

        if (progressBar != null) {
            progressBar.setValue(progressBar.getValue() - 1);
            progressBar.setString(String.format("%d/%d", progressBar.getValue(), capacidad));
        }
    }

    public synchronized void printFaroMirador(String mensaje) {
        System.out.println(mensaje);
        lighthouseTextArea.append(mensaje + "\n");
        lighthouseTextArea.setCaretPosition(lighthouseTextArea.getDocument().getLength());
    }

    public synchronized void agregarVisitanteEscaleraFaroMirador() {
        stairsProgressBar.setValue(stairsProgressBar.getValue() + 1);
        stairsProgressBar.setString(stairsProgressBar.getValue() + "/" + FaroMirador.CAPACIDAD_ESCALERA);
    }

    public synchronized void sacarVisitanteEscaleraFaroMirador() {
        stairsProgressBar.setValue(stairsProgressBar.getValue() - 1);
        stairsProgressBar.setString(stairsProgressBar.getValue() + "/" + FaroMirador.CAPACIDAD_ESCALERA);
    }

    public synchronized void ocuparTobogan(int tobogan) {
        JProgressBar progressBar = null;

        switch (tobogan) {
        case 0:
            progressBar = slide1ProgressBar;
            break;
        case 1:
            progressBar = slide2ProgressBar;
            break;
        }

        if (progressBar != null) {
            progressBar.setValue(progressBar.getValue() + 1);
            progressBar.setString("Ocupado");
        }
    }

    public synchronized void desocuparTobogan(int tobogan) {
        JProgressBar progressBar = null;

        switch (tobogan) {
        case 0:
            progressBar = slide1ProgressBar;
            break;
        case 1:
            progressBar = slide2ProgressBar;
            break;
        }

        if (progressBar != null) {
            progressBar.setValue(progressBar.getValue() - 1);
            progressBar.setString("Desocupado");
        }
    }

    public synchronized void actualizarHora(String hora) {
        clockValueLabel.setText(hora);
    }

    public synchronized void printSnorkel(String mensaje) {
        System.out.println(mensaje);

        try {
            snorkelTextPane.getStyledDocument().insertString(snorkelTextPane.getStyledDocument().getLength(),
                    mensaje + "\n", attribs);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        snorkelTextPane.setCaretPosition(snorkelTextPane.getDocument().getLength());
    }

    public synchronized void agregarEquipoSnorkel() {
        snorkelKitProgressBar.setValue(snorkelKitProgressBar.getValue() + 1);
        snorkelKitProgressBar
                .setString(String.format("%d/%d", snorkelKitProgressBar.getValue(), Parque.CANTIDAD_EQUIPOS_SNORKEL));
    }

    public synchronized void sacarEquipoSnorkel() {
        snorkelKitProgressBar.setValue(snorkelKitProgressBar.getValue() - 1);
        snorkelKitProgressBar
                .setString(String.format("%d/%d", snorkelKitProgressBar.getValue(), Parque.CANTIDAD_EQUIPOS_SNORKEL));
    }

    public synchronized void agregarVisitanteSnorkel() {
        int value = snorkelVisitorsCountProgressBar.getValue() + 1;
        snorkelVisitorsCountProgressBar.setValue(value);
        snorkelVisitorsCountProgressBar.setString(String.valueOf(value));

        if (value > 0) {
            snorkelVisitorsCountProgressBar.setIndeterminate(true);
        }
    }

    public synchronized void sacarVisitanteSnorkel() {
        int value = snorkelVisitorsCountProgressBar.getValue() - 1;
        snorkelVisitorsCountProgressBar.setValue(value);
        snorkelVisitorsCountProgressBar.setString(String.valueOf(value));

        if (value == 0) {
            snorkelVisitorsCountProgressBar.setIndeterminate(false);
        }
    }

    public synchronized void printNadoDelfines(String mensaje) {
        System.out.println(mensaje);
        try {
            dolphinSwimTextPane.getStyledDocument().insertString(dolphinSwimTextPane.getStyledDocument().getLength(),
                    mensaje + "\n", attribs);
            dolphinSwimTextPane.setCaretPosition(dolphinSwimTextPane.getDocument().getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public synchronized void agregarVisitanteNadoDelfines() {
        int value = dolphinSwimVisitorsProgressBar.getValue() + 1;
        dolphinSwimVisitorsProgressBar.setValue(value);
        dolphinSwimVisitorsProgressBar.setString(String.valueOf(value));
        if (value > 0)
            dolphinSwimVisitorsProgressBar.setIndeterminate(true);
    }

    public synchronized void sacarVisitanteNadoDelfines() {
        int value = dolphinSwimVisitorsProgressBar.getValue() - 1;
        dolphinSwimVisitorsProgressBar.setValue(value);
        dolphinSwimVisitorsProgressBar.setString(String.valueOf(value));
        if (value == 0)
            dolphinSwimVisitorsProgressBar.setIndeterminate(false);
    }

    public synchronized void agregarVisitantePileta(int pileta) {
        JProgressBar progressBar = null;

        switch (pileta) {
        case 0:
            progressBar = pool1ProgressBar;
            break;
        case 1:
            progressBar = pool2ProgressBar;
            break;
        case 2:
            progressBar = pool3ProgressBar;
            break;
        case 3:
            progressBar = pool4ProgressBar;
            break;
        }

        if (progressBar != null) {
            int newValue = progressBar.getValue() + 1;
            progressBar.setValue(newValue);
            progressBar.setString(String.format("%d/10", newValue));
        }
    }

    public synchronized void sacarVisitantePileta(int pileta) {
        JProgressBar progressBar = null;

        switch (pileta) {
        case 0:
            progressBar = pool1ProgressBar;
            break;
        case 1:
            progressBar = pool2ProgressBar;
            break;
        case 2:
            progressBar = pool3ProgressBar;
            break;
        case 3:
            progressBar = pool4ProgressBar;
            break;
        }

        if (progressBar != null) {
            int newValue = progressBar.getValue() - 1;
            progressBar.setValue(newValue);
            progressBar.setString(String.format("%d/10", newValue));
        }
    }

}
