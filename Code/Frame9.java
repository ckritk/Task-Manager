package test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class Frame9 extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame9(String title, int total, int completed, int pending, int overdue) {
       super(title);
 
        DefaultPieDataset<String> dataset = createDataset(completed, pending, overdue);

        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(new Color(255, 255, 255));
        chartPanel.setPreferredSize(new Dimension(400, 300));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(chartPanel, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private DefaultPieDataset<String> createDataset(int completed, int pending, int overdue) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<String>();
        dataset.setValue("Completed", completed);
        dataset.setValue("Pending", pending);
        dataset.setValue("Overdue", overdue);
        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset<String> dataset) {
        return ChartFactory.createPieChart(
                "Task Status",
                dataset,
                true,
                true,
                false
        );
    }

    public static void main(String[] args) {
        // Example usage
        SwingUtilities.invokeLater(() -> {
            new Frame9("Task Status", 20, 10, 5, 5);
        });
    }
}