package test;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class NormalDistribution extends ApplicationFrame {

    /**
     * Affichage d'une courbe gaussienne théorique
     * @param title  titre de la fenêtre
     */
    public NormalDistribution(final String title) {

        super(title);
        Function2D normal = new NormalDistributionFunction2D(0.0, 1.0);
        XYDataset dataset = DatasetUtilities.sampleFunction2D(normal, -5.0, 5.0, 100, "Normal");
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Gaussienne théorique",
            "X", 
            "Y", 
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

    /**
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final NormalDistributionDemo demo = new NormalDistributionDemo("Normal Distribution");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}
