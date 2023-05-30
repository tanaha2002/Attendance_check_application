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
import java.awt.GradientPaint;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;

import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
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
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
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
public class JpnBieuDoDoanhThu {

    private javax.swing.JPanel BarChart;
    private javax.swing.JPanel LineChart;

    private List<List<String>> dsDuLieu;
    private String loaiThongKe;
    private String tieuChi1;
    private String tieuDe;
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    DefaultPieDataset datasetPie = new DefaultPieDataset();

    public JpnBieuDoDoanhThu(javax.swing.JPanel BarChart, javax.swing.JPanel LineChart, String loaiThongKe, String tieuChi1) {
        this.BarChart = BarChart;
        this.LineChart = LineChart;
        this.loaiThongKe = loaiThongKe;
        this.tieuChi1 = tieuChi1;

    }

    public void ThemDuLieuBieuDo(List<List<String>> dsDuLieu) {

        String tenCot = tieuChi1;
        if (loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
            
                tieuDe = "Tổng số số doanh thu top 5 sản phẩm trong năm";
        }
       
        else if(loaiThongKe.equalsIgnoreCase("Thống kê theo khoảng thời gian")){
            
                tieuDe = "Top 5 sản phẩm trong khoảng thời gian này";
          
        }
        else {
        	tieuDe = tieuChi1;
        }
        try {
            for (List<String> duLieu : dsDuLieu) {
            	
                dataset.addValue(Double.parseDouble(duLieu.get(3)), "Doanh Thu", duLieu.get(0));
                //add to pie chart
                datasetPie.setValue(duLieu.get(0), Double.parseDouble(duLieu.get(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void HienThiBieuDo()  {

        // Create the bar chart
    	JFreeChart barChart = ChartFactory.createBarChart(
    			tieuDe,
    			"",
    			"Doanh Thu (VNĐ)",
    			dataset,
    			PlotOrientation.VERTICAL,
    			true, true, false);

        barChart.getTitle().setPaint(new Color(51, 153, 255));

		// Customize the bar chart
		CategoryPlot barPlot = (CategoryPlot) barChart.getPlot();
		
		
    			// Set the bar colors and make them flat
    			BarRenderer renderer = (BarRenderer) barPlot.getRenderer();
    			renderer.setSeriesPaint(0, new Color(52, 152, 219)); // blue
    			renderer.setSeriesPaint(1, new Color(255, 121, 63)); // orange
    			renderer.setSeriesPaint(2, new Color(46, 204, 113)); // green
    			renderer.setMaximumBarWidth(0.05);	

    			// Set the chart background and border
//    			barChart.setBackgroundPaint(new Color(0, 0, 0, 0)); // transparent
//    			barChart.setBorderVisible(false);




    			// Create the chart panel for the bar chart
    			ChartPanel barChartPanel = new ChartPanel(barChart);


//    			// Set the chart title font and color
//    			TextTitle title = barChart.getTitle();
//    			title.setFont(new Font("Roboto", Font.BOLD, 16));
//    			title.setPaint(new Color(44, 62, 80)); // dark blue

    			

    			// Add gradient paint to the bars
    			GradientPaint blueGradient = new GradientPaint(
    			0f, 0f, new Color(52, 152, 219), 0f, 0f, new Color(52, 152, 219, 180));
    			GradientPaint orangeGradient = new GradientPaint(
    			0f, 0f, new Color(255, 121, 63), 0f, 0f, new Color(255, 121, 63, 180));
    			GradientPaint greenGradient = new GradientPaint(
    			0f, 0f, new Color(46, 204, 113), 0f, 0f, new Color(46, 204, 113, 180));
    			renderer.setSeriesPaint(0, blueGradient);
    			renderer.setSeriesPaint(1, orangeGradient);
    			renderer.setSeriesPaint(2, greenGradient);



    			// Set the plot background

    			// Set the chart border
    			barChart.setBorderPaint(new Color(189, 195, 199)); // light grey
    			barChart.setBorderStroke(new BasicStroke(2));
    			barPlot.getRenderer().setSeriesOutlinePaint(0, Color.WHITE);
    	        ((BarRenderer) barPlot.getRenderer()).setBarPainter(new StandardBarPainter());
    			// Add a shadow effect to the chart panel
    			

    			// Set the container panel background
    	        barPlot.setBackgroundPaint(new Color(245, 245, 245)); // Set the background color
    	        barPlot.setDomainGridlinePaint(Color.WHITE); // Set the grid line color
    	        barPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
//    	                barPlot.setRangeAxes(new NumberAxis("Tiền"));
    	        
    	        barChartPanel.setBackground(new Color(245, 245, 245));
    			
    			// Refresh the chart panel
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
