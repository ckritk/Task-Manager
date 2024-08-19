package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Frame7 extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Frame7 frame = new Frame7(7, 3, 4, 0);
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
    public Frame7(int all, int comp, int pend, int aging) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 482, 430);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setTitle("Progress Summary");

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblDet1 = new JLabel("All Tasks(" + all + ")");
        lblDet1.setBounds(343, 239, 94, 14);
        contentPane.add(lblDet1);

        JLabel lblDet2 = new JLabel("Completed(" + comp + ")");
        lblDet2.setBounds(343, 264, 94, 12);
        contentPane.add(lblDet2);

        JLabel lblDet3 = new JLabel("Pending(" + pend + ")");
        lblDet3.setBounds(343, 287, 94, 17);
        contentPane.add(lblDet3);

        JLabel lblDet4 = new JLabel("Overdue(" + aging + ")");
        lblDet4.setBounds(343, 310, 94, 17);
        contentPane.add(lblDet4);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(new Color(255, 255, 255));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBack.setBounds(10, 11, 89, 23);
        contentPane.add(btnBack);

        DefaultPieDataset<String> dataset = createDataset(comp, pend, aging);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBounds(25, 89, 296, 256);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        contentPane.add(chartPanel);
        chartPanel.setLayout(null);

        JLabel lblPercent = new JLabel(comp * 100 / all + "%");
        lblPercent.setForeground(new Color(28, 216, 198));
        lblPercent.setBounds(90, 65, 114, 102);
        chartPanel.add(lblPercent);
        lblPercent.setHorizontalAlignment(SwingConstants.CENTER);
        lblPercent.setFont(new Font("Tahoma", Font.PLAIN, 44));
    }

    private DefaultPieDataset<String> createDataset(int completed, int pending, int overdue) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();
        dataset.setValue("Completed", completed);
        dataset.setValue("Pending", pending);
        dataset.setValue("Overdue", overdue);
        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset<String> dataset) {
        JFreeChart chart = ChartFactory.createRingChart(
                "",
                dataset,
                true,
                true,
                false
        );

        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setSectionDepth(0.20); // Set the depth of the donut

        // Set the colors as before
        plot.setSectionPaint("Completed", new Color(28, 216, 198));
        plot.setSectionPaint("Pending", new Color(208, 229, 254));
        plot.setSectionPaint("Overdue", new Color(72, 151, 252));

        plot.setLabelGenerator(null);

        // Set background and outline paint
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlinePaint(Color.WHITE);

        //plot.setLegendLabelGenerator(new LabelGenerator("{0} ({1})", null, null));
        return chart;
    }
}
