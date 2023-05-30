/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.Rotation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author ADMIN
 */
public class JpnBieuDo {

    private javax.swing.JPanel BarChart;
    private javax.swing.JPanel LineChart;

    private List<List<String>> dsDuLieu;
    private String loaiThongKe;
    private String tieuChi1;
    private String tieuChi2;
    private String tieuDe;
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    DefaultPieDataset datasetPie = new DefaultPieDataset();

    public JpnBieuDo(javax.swing.JPanel BarChart, javax.swing.JPanel LineChart, String loaiThongKe, String tieuChi1, String tieuChi2) {
        this.BarChart = BarChart;
        this.LineChart = LineChart;
        this.loaiThongKe = loaiThongKe;
        this.tieuChi1 = tieuChi1;
        this.tieuChi2 = tieuChi2;

    }

    public void ThemDuLieuBieuDo(List<List<String>> dsDuLieu) {

        String tenCot = tieuChi1;
        if (loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
            if (tieuChi1.equalsIgnoreCase("Lương")) {
                tieuDe = "Tổng số tiền lương phải chi trả trong năm";
            }
            else if(tieuChi1.equalsIgnoreCase("Công đoạn")){
                tieuDe = "Tổng số công đoạn hoàn thành trong năm";
            }
        }
        else if (loaiThongKe.equalsIgnoreCase("Thống kê theo tháng")) {
                tieuDe = tieuChi2;
            }
        else if(loaiThongKe.equalsIgnoreCase("Thống kê theo khoảng thời gian")){
            if(tieuChi1.equalsIgnoreCase("Lương"))
                tieuDe = "Tổng lương phải chi trả";
            else if(tieuChi1.equalsIgnoreCase("Công đoạn"))
                tieuDe = "Tổng công đoạn đã hoàn thành";
        }
        try {
            for (List<String> duLieu : dsDuLieu) {
                System.out.println(duLieu);
                if(!duLieu.get(0).equalsIgnoreCase("Phần còn lại"))
                    dataset.addValue(Double.parseDouble(duLieu.get(1)), tenCot, duLieu.get(0));
                //add to pie chart
                datasetPie.setValue(duLieu.get(0), Double.parseDouble(duLieu.get(1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void HienThiBieuDo()  {

        // Create the bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                tieuDe, // chart title
                "Category", // domain axis label
                "Tiền", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );
//                barChart.setR
        barChart.getTitle().setPaint(new Color(51, 153, 255));

        // Customize the bar chart
        CategoryPlot barPlot = (CategoryPlot) barChart.getPlot();
        barPlot.setBackgroundPaint(new Color(245, 245, 245)); // Set the background color
        barPlot.setDomainGridlinePaint(Color.WHITE); // Set the grid line color
        barPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
//                barPlot.setRangeAxes(new NumberAxis("Tiền"));
        barPlot.setDomainAxis(new CategoryAxis() {
            @Override
            public void setTickLabelPaint(Paint paint) {
                super.setTickLabelPaint(new Color(50, 50, 50)); // Set the tick label color
            }

            @Override
            public void setTickLabelFont(Font font) {
                super.setTickLabelFont(font.deriveFont(Font.BOLD)); // Set the tick label font
            }
        });
        barPlot.setRangeAxis(new NumberAxis() {
            @Override
            public void setTickLabelPaint(Paint paint) {
                super.setTickLabelPaint(new Color(50, 50, 50)); // Set the tick label color
            }

            @Override
            public void setTickLabelFont(Font font) {
                super.setTickLabelFont(font.deriveFont(Font.BOLD)); // Set the tick label font
            }

            @Override
            public void setLabelPaint(Paint paint) {
                super.setLabelPaint(new Color(50, 50, 50)); // Set the axis label color
            }

            @Override
            public void setLabelFont(Font font) {
                super.setLabelFont(font.deriveFont(Font.BOLD)); // Set the axis label font
            }
        });
        barPlot.getRenderer().setSeriesPaint(0, new Color(60, 145, 230)); // Set the bar color
        barPlot.getRenderer().setSeriesOutlinePaint(0, Color.WHITE);
        ((BarRenderer) barPlot.getRenderer()).setBarPainter(new StandardBarPainter());

        


// Create the chart panel for the bar chart
        ChartPanel barChartPanel = new ChartPanel(barChart);
//                barChartPanel.setPreferredSize(new Dimension(400, 200));
        barChartPanel.setBackground(new Color(245, 245, 245));
        barChartPanel.setDomainZoomable(false); // Disable zooming and panning
        barChartPanel.setRangeZoomable(false);

// Add the bar chart panel to the first panel
        BarChart.removeAll();
        BarChart.add(barChartPanel);
        BarChart.revalidate();
        BarChart.repaint();

        // Create the line chart
        // Create a line chart
//                JFreeChart pieChart = ChartFactory.createPieChart3D(tieuChi, (PieDataset) dataset);
        JFreeChart pieChart = ChartFactory.createPieChart3D(
                tieuDe, // Chart title
                datasetPie, // Dataset
                false, // Show legend
                true, // Use tooltips
                false // Generate URLs
        );

// Customize the pie chart
//pieChart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
        pieChart.getTitle().setPaint(new Color(51, 153, 255));
        pieChart.setBackgroundPaint(new Color(255, 255, 255, 0));
//pieChart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 11));

        PiePlot3D plot = (PiePlot3D) pieChart.getPlot();
        plot.setOutlinePaint(null);
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(new Color(255, 255, 255, 200));
        plot.setLabelShadowPaint(new Color(0, 0, 0, 100));
        plot.setLabelOutlinePaint(null);
        plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.75f);
        plot.setBackgroundPaint(new Color(255, 255, 255, 0));
        plot.setStartAngle(290);
        plot.setDepthFactor(0.2f);
        plot.setCircular(true);
        plot.setNoDataMessage("Không có dữ liệu");
// Customizing the labels
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} VNĐ ({2})", // {0} is the section label, {1} is the value, {2} is the percentage
                new DecimalFormat("#,### "), // format for the value (use # to represent digits, and , to group thousands)
                new DecimalFormat("0.0%") // format for the percentage
        ));

// Customize the pie chart labels
        plot.setLabelOutlineStroke(new BasicStroke(
                0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{2, 2}, 0
        ));

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setOpaque(false);
        pieChartPanel.setBackground(new Color(255, 255, 255, 0));
        LineChart.removeAll();
        LineChart.add(pieChartPanel, BorderLayout.CENTER);
        LineChart.revalidate();
        LineChart.repaint();

    }


    

}
