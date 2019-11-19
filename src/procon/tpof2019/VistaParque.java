package procon.tpof2019;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    private JLabel threadCountValueLabel;

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
        gbl_northPanel.columnWidths = new int[] { 0, 0, 65, 0, 48, 0, 200, 0 };
        gbl_northPanel.rowHeights = new int[] { 27, 0 };
        gbl_northPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_northPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        northPanel.setLayout(gbl_northPanel);

        startButton = new JButton("Iniciar");
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

        JLabel visitorsThreadCountLabel = new JLabel("Cantidad de visitantes (hilos):");
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

        JLabel timeLabel = new JLabel("Tiempo:");
        GridBagConstraints gbc_timeLabel = new GridBagConstraints();
        gbc_timeLabel.anchor = GridBagConstraints.EAST;
        gbc_timeLabel.insets = new Insets(0, 0, 0, 5);
        gbc_timeLabel.gridx = 4;
        gbc_timeLabel.gridy = 0;
        northPanel.add(timeLabel, gbc_timeLabel);

        timeValueLabel = new JLabel("1x");
        GridBagConstraints gbc_timeValueLabel = new GridBagConstraints();
        gbc_timeValueLabel.insets = new Insets(0, 0, 0, 5);
        gbc_timeValueLabel.gridx = 5;
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
        gbc_timeSlider.gridx = 6;
        gbc_timeSlider.gridy = 0;
        northPanel.add(timeSlider, gbc_timeSlider);

        JPanel activitiesPanel = new JPanel();
        contentPane.add(activitiesPanel);
        GridBagLayout gbl_activitiesPanel = new GridBagLayout();
        gbl_activitiesPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_activitiesPanel.rowHeights = new int[] { 255, 255, 0 };
        gbl_activitiesPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
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
        gbc_inflatableBoatRacePanel.gridheight = 2;
        gbc_inflatableBoatRacePanel.fill = GridBagConstraints.BOTH;
        gbc_inflatableBoatRacePanel.insets = new Insets(0, 0, 5, 0);
        gbc_inflatableBoatRacePanel.gridx = 2;
        gbc_inflatableBoatRacePanel.gridy = 0;
        activitiesPanel.add(inflatableBoatRacePanel, gbc_inflatableBoatRacePanel);
        inflatableBoatRacePanel.setBorder(
                new TitledBorder(null, "Carrera de gomones", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagLayout gbl_inflatableBoatRacePanel = new GridBagLayout();
        gbl_inflatableBoatRacePanel.columnWidths = new int[] { 75, 0, 0 };
        gbl_inflatableBoatRacePanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gbl_inflatableBoatRacePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_inflatableBoatRacePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        inflatableBoatRacePanel.setLayout(gbl_inflatableBoatRacePanel);

        JLabel trainLabel = new JLabel("Tren:");
        GridBagConstraints gbc_trainLabel = new GridBagConstraints();
        gbc_trainLabel.anchor = GridBagConstraints.WEST;
        gbc_trainLabel.insets = new Insets(0, 0, 5, 5);
        gbc_trainLabel.gridx = 0;
        gbc_trainLabel.gridy = 0;
        inflatableBoatRacePanel.add(trainLabel, gbc_trainLabel);

        trainProgressBar = new JProgressBar();
        trainProgressBar.setStringPainted(true);
        GridBagConstraints gbc_trainProgressBar = new GridBagConstraints();
        gbc_trainProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_trainProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_trainProgressBar.gridx = 1;
        gbc_trainProgressBar.gridy = 0;
        inflatableBoatRacePanel.add(trainProgressBar, gbc_trainProgressBar);

        JLabel bikesLabel = new JLabel("Bicicletas:");
        GridBagConstraints gbc_bikesLabel = new GridBagConstraints();
        gbc_bikesLabel.anchor = GridBagConstraints.WEST;
        gbc_bikesLabel.insets = new Insets(0, 0, 5, 5);
        gbc_bikesLabel.gridx = 0;
        gbc_bikesLabel.gridy = 1;
        inflatableBoatRacePanel.add(bikesLabel, gbc_bikesLabel);

        bikesProgressBar = new JProgressBar();
        bikesProgressBar.setValue(10);
        bikesProgressBar.setMaximum(10);
        bikesProgressBar.setStringPainted(true);
        GridBagConstraints gbc_bikesProgressBar = new GridBagConstraints();
        gbc_bikesProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_bikesProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_bikesProgressBar.gridx = 1;
        gbc_bikesProgressBar.gridy = 1;
        inflatableBoatRacePanel.add(bikesProgressBar, gbc_bikesProgressBar);

        JLabel simpleInflatableBoatsLabel = new JLabel("Gomones simples:");
        GridBagConstraints gbc_simpleInflatableBoatsLabel = new GridBagConstraints();
        gbc_simpleInflatableBoatsLabel.anchor = GridBagConstraints.WEST;
        gbc_simpleInflatableBoatsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_simpleInflatableBoatsLabel.gridx = 0;
        gbc_simpleInflatableBoatsLabel.gridy = 2;
        inflatableBoatRacePanel.add(simpleInflatableBoatsLabel, gbc_simpleInflatableBoatsLabel);

        simpleInflatableBoatsProgressBar = new JProgressBar();
        simpleInflatableBoatsProgressBar.setValue(5);
        simpleInflatableBoatsProgressBar.setMaximum(5);
        simpleInflatableBoatsProgressBar.setStringPainted(true);
        GridBagConstraints gbc_simpleInflatableBoatsProgressBar = new GridBagConstraints();
        gbc_simpleInflatableBoatsProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_simpleInflatableBoatsProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_simpleInflatableBoatsProgressBar.gridx = 1;
        gbc_simpleInflatableBoatsProgressBar.gridy = 2;
        inflatableBoatRacePanel.add(simpleInflatableBoatsProgressBar, gbc_simpleInflatableBoatsProgressBar);

        JLabel doubleInflatableBoatsLabel = new JLabel("Gomones dobles:");
        GridBagConstraints gbc_doubleInflatableBoatsLabel = new GridBagConstraints();
        gbc_doubleInflatableBoatsLabel.anchor = GridBagConstraints.WEST;
        gbc_doubleInflatableBoatsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_doubleInflatableBoatsLabel.gridx = 0;
        gbc_doubleInflatableBoatsLabel.gridy = 3;
        inflatableBoatRacePanel.add(doubleInflatableBoatsLabel, gbc_doubleInflatableBoatsLabel);

        doubleInflatableBoatsProgressBar = new JProgressBar();
        doubleInflatableBoatsProgressBar.setValue(5);
        doubleInflatableBoatsProgressBar.setMaximum(5);
        doubleInflatableBoatsProgressBar.setStringPainted(true);
        GridBagConstraints gbc_doubleInflatableBoatsProgressBar = new GridBagConstraints();
        gbc_doubleInflatableBoatsProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_doubleInflatableBoatsProgressBar.insets = new Insets(0, 0, 5, 0);
        gbc_doubleInflatableBoatsProgressBar.gridx = 1;
        gbc_doubleInflatableBoatsProgressBar.gridy = 3;
        inflatableBoatRacePanel.add(doubleInflatableBoatsProgressBar, gbc_doubleInflatableBoatsProgressBar);

        JScrollPane inflatableBoatRaceScrollPane = new JScrollPane();
        GridBagConstraints gbc_inflatableBoatRaceScrollPane = new GridBagConstraints();
        gbc_inflatableBoatRaceScrollPane.gridwidth = 2;
        gbc_inflatableBoatRaceScrollPane.fill = GridBagConstraints.BOTH;
        gbc_inflatableBoatRaceScrollPane.gridx = 0;
        gbc_inflatableBoatRaceScrollPane.gridy = 4;
        inflatableBoatRacePanel.add(inflatableBoatRaceScrollPane, gbc_inflatableBoatRaceScrollPane);

        inflatableBoatRaceTextArea = new JTextArea();
        inflatableBoatRaceTextArea.setEditable(false);
        inflatableBoatRaceScrollPane.setViewportView(inflatableBoatRaceTextArea);

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

        JPanel southPanel = new JPanel();
        FlowLayout fl_southPanel = (FlowLayout) southPanel.getLayout();
        fl_southPanel.setVgap(0);
        fl_southPanel.setHgap(2);
        fl_southPanel.setAlignment(FlowLayout.LEFT);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        JLabel threadCountLabel = new JLabel("Hilos en ejecución:");
        southPanel.add(threadCountLabel);

        threadCountValueLabel = new JLabel("0");
        southPanel.add(threadCountValueLabel);
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

    /**
     * Agrega 1 a la barra del tren.
     */
    public synchronized void agregarPasajero() {
        trainProgressBar.setValue(trainProgressBar.getValue() + 1);
        trainProgressBar.setString(String.format("Tren: %d/15", trainProgressBar.getValue()));
    }

    /**
     * Saca 1 de la barra del tren.
     */
    public synchronized void sacarPasajero() {
        trainProgressBar.setValue(trainProgressBar.getValue() - 1);
        trainProgressBar.setString(String.format("Tren: %d/15", trainProgressBar.getValue()));
    }

    /**
     * Agrega 1 a la barra de las bicicletas.
     */
    public synchronized void agregarBicicleta() {
        bikesProgressBar.setValue(bikesProgressBar.getValue() + 1);
        bikesProgressBar.setString(String.format("Bicicletas: %d/10", bikesProgressBar.getValue()));
    }

    /**
     * Saca 1 de la barra de las bicicletas.
     */
    public synchronized void sacarBicicleta() {
        bikesProgressBar.setValue(bikesProgressBar.getValue() - 1);
        bikesProgressBar.setString(String.format("Bicicletas: %d/10", bikesProgressBar.getValue()));
    }

    /**
     * Agrega 1 a la barra de gomones.
     */
    public synchronized void agregarGomon() {
        simpleInflatableBoatsProgressBar.setValue(simpleInflatableBoatsProgressBar.getValue() + 1);
        simpleInflatableBoatsProgressBar
                .setString(String.format("Gomones: %d/5", simpleInflatableBoatsProgressBar.getValue()));
    }

    /**
     * Saca 1 de la barra de gomones.
     */
    public synchronized void sacarGomon() {
        simpleInflatableBoatsProgressBar.setValue(simpleInflatableBoatsProgressBar.getValue() - 1);
        simpleInflatableBoatsProgressBar
                .setString(String.format("Gomones: %d/5", simpleInflatableBoatsProgressBar.getValue()));
    }

    public synchronized void printRestaurantes(String mensaje) {
        System.out.println(mensaje);
        restaurantsTextArea.append(mensaje + "\n");
        restaurantsTextArea.setCaretPosition(restaurantsTextArea.getDocument().getLength());
    }

    public synchronized void agregarClienteRestaurante(int restaurante) {
        JProgressBar progressBar = null;

        switch (restaurante) {
        case 0:
            progressBar = restaurant1ProgressBar;
            break;
        case 1:
            progressBar = restaurant2ProgressBar;
            break;
        case 2:
            progressBar = restaurant3ProgressBar;
            break;
        }

        if (progressBar != null)
            progressBar.setValue(progressBar.getValue() + 1);
    }

    public synchronized void sacarClienteRestaurante(int restaurante) {
        JProgressBar progressBar = null;

        switch (restaurante) {
        case 0:
            progressBar = restaurant1ProgressBar;
            break;
        case 1:
            progressBar = restaurant2ProgressBar;
            break;
        case 2:
            progressBar = restaurant3ProgressBar;
            break;
        }

        if (progressBar != null)
            progressBar.setValue(progressBar.getValue() - 1);
    }

    public synchronized void printFaroMirador(String mensaje) {
        System.out.println(mensaje);
        lighthouseTextArea.append(mensaje + "\n");
        lighthouseTextArea.setCaretPosition(lighthouseTextArea.getDocument().getLength());
    }
}
