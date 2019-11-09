package procon.tpof2019;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class VistaParque {

    private final JTextArea ta1;

    public VistaParque() {
        JFrame frame = new JFrame("Vista del Parque");

        ta1 = new JTextArea();
        ta1.setEditable(false);
        JScrollPane scroll1 = new JScrollPane(ta1);

        JTextArea ta2 = new JTextArea();
        ta2.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(ta2);

        JTextArea ta3 = new JTextArea();
        ta3.setEditable(false);
        JScrollPane scroll3 = new JScrollPane(ta3);

        scroll1.setBorder(new TitledBorder("Parque"));
        scroll2.setBorder(new TitledBorder("Tour"));
        scroll3.setBorder(new TitledBorder("Carrera con gomones"));

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(2, 3, 8, 8));

        contentPane.add(scroll1);
        contentPane.add(scroll2);
        contentPane.add(scroll3);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public void mostrar(String mensaje) {
        //System.out.println(mensaje);
        ta1.append(mensaje + "\n");
    }

}
