/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import dao.CongNhanDAO;
import dao.NhanVienDAO;
import entity.CongNhan;
import entity.NhanVien;
import setting.PathSetting;

/**
 *
 * @author Hoang Truong
 */
public class TrangChu extends javax.swing.JPanel {

    /**
     * Creates new form TrangChu
     * 
     */
	
	private CongNhanDAO congNhanDAO;
	
	private NhanVienDAO nhanVienDAO;
	private NhanVien nhanVienLogin;
    public TrangChu(NhanVien nvLogin) {

    	initComponents();
    	
        congNhanDAO = new CongNhanDAO();
        nhanVienDAO = new NhanVienDAO();
        this.nhanVienLogin = nvLogin;
        themSuKien();
        tiLeGioiTinhNhanVien();
        tiLeGioiTinhCongNhan();
    }
    private void themSuKien() {
        Timer timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lblTime.setText(new SimpleDateFormat("hh:mm:ss dd-MM-yyyy ").format(new java.util.Date()));
			}
		});
    	timer.start();
    	
    	List<CongNhan> dsCN = congNhanDAO.layDanhSachCongNhan();
    	List<NhanVien> dsNV = nhanVienDAO.layDanhSachNhanVien();
    	String soLuongCongNhan = String.valueOf(dsCN.size());
    	String soLuongNhanVien = String.valueOf(dsNV.size());
    	lblSoLuongCN.setText(soLuongCongNhan);
    	lblSoLuongNV.setText(soLuongNhanVien);

    	lblAnhLogin.setSize(100, 109);
    	String imagePath = PathSetting.PATH_IMAGE_NHANVIEN + nhanVienLogin.getAnhDaiDien();

    	ImageIcon imageIcon = new ImageIcon(imagePath);
    	int labelWidth = lblAnhLogin.getWidth();
    	int labelHeight = lblAnhLogin.getHeight();
    	Image scaledImage = imageIcon.getImage().getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
    	ImageIcon scaledIcon = new ImageIcon(scaledImage);
    	lblAnhLogin.setIcon(scaledIcon);

    	
    	lblTenNVLogin.setText(nhanVienLogin.getTenNhanVien().toUpperCase());
    	txtTenNhanVien.setText(nhanVienLogin.getTenNhanVien());
    	txtMaNhanVien.setText(nhanVienLogin.getMaNhanVien());
    	cmbVaiTro.setSelectedItem(nhanVienLogin.getVaiTro());
    }
    
    private void tiLeGioiTinhNhanVien() {

        List<NhanVien> dsNV = nhanVienDAO.layDanhSachNhanVien();
        int soLuongNam = 0;
        int soLuongNu = 0;
        if (dsNV != null) {
            for (NhanVien nhanVien : dsNV) {
                if (nhanVien.getGioiTinh().equals("Nam")) {
                    soLuongNam++;
                } else {
                    soLuongNu++;
                }
            }
        }
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Nam", soLuongNam);
        dataset.setValue("Nữ", soLuongNu);
        JFreeChart chart = ChartFactory.createPieChart("Tỉ lệ giới tính của nhân viên", dataset, true, true, true);
        PiePlot piePlot = (PiePlot) chart.getPlot();
        piePlot.setOutlineVisible(false);
        piePlot.setBackgroundPaint(Color.WHITE);
        Font legendFont = new Font("SansSerif", Font.PLAIN, 18); // Tạo font với kích thước chữ 14
        chart.getLegend().setItemFont(legendFont);
        ChartPanel chartPanel = new ChartPanel(chart);
        pnlBD2.add(chartPanel, BorderLayout.CENTER);
        pnlBD2.repaint();
        pnlBD2.validate();
    }
    
    private void tiLeGioiTinhCongNhan() {

        List<CongNhan> dsCN = congNhanDAO.layDanhSachCongNhan();
        int soLuongNam = 0;
        int soLuongNu = 0;
        if (dsCN != null) {
            for (CongNhan congNhan : dsCN) {
                if (congNhan.getGioiTinh().equals("Nam")) {
                    soLuongNam++;
                } else {
                    soLuongNu++;
                }
            }
        }
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Nam", soLuongNam);
        dataset.setValue("Nữ", soLuongNu);
        JFreeChart chart = ChartFactory.createPieChart("Tỉ lệ giới tính của công nhân", dataset, true, true, true);
        PiePlot piePlot = (PiePlot) chart.getPlot();
        piePlot.setOutlineVisible(false);
        piePlot.setBackgroundPaint(Color.WHITE);
        Font legendFont = new Font("SansSerif", Font.PLAIN, 18); // Tạo font với kích thước chữ 14
        chart.getLegend().setItemFont(legendFont);
        ChartPanel chartPanel = new ChartPanel(chart);
        pnlBD1.add(chartPanel, BorderLayout.CENTER);
        pnlBD1.repaint();
        pnlBD1.validate();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        roundedPanel1 = new ui.RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        lblAnhLogin = new javax.swing.JLabel();
        lblTenNVLogin = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        roundedPanel5 = new ui.RoundedPanel();
        jLabel10 = new javax.swing.JLabel();
        lblSoLuongCN = new javax.swing.JLabel();
        lblAnhCN = new javax.swing.JLabel();
        roundedPanel6 = new ui.RoundedPanel();
        jLabel11 = new javax.swing.JLabel();
        lblSoLuongNV = new javax.swing.JLabel();
        lblAnhNhanVien = new javax.swing.JLabel();
        roundedPanel2 = new ui.RoundedPanel();
        lblMaNhanVien = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        lblTenNhanVien = new javax.swing.JLabel();
        txtTenNhanVien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbVaiTro = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        typingLabel2 = new ui.TypingLabel();
        pnlBD1 = new javax.swing.JPanel();
        pnlBD2 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TRANG CHỦ");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 0, 1690, 50));

        roundedPanel1.setForeground(new java.awt.Color(255, 255, 255));
        roundedPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        jLabel1.setText("Xin chào,");
        roundedPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, 23));

        lblAnhLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/role-model.png"))); // NOI18N
        roundedPanel1.add(lblAnhLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 110, 110));

        lblTenNVLogin.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTenNVLogin.setText("NGUYỄN DƯƠNG THANH DỰ");
        roundedPanel1.add(lblTenNVLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, 30));

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblTime.setText(new SimpleDateFormat("hh:mm:ss dd-MM-yyyy ").format(new java.util.Date()));
        roundedPanel1.add(lblTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 40, 280, 40));

        add(roundedPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 77, 1650, 111));

        roundedPanel5.setForeground(new java.awt.Color(51, 153, 255));
        roundedPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel10.setText("TỔNG SỐ CÔNG NHÂN");
        roundedPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        lblSoLuongCN.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblSoLuongCN.setText("530");
        roundedPanel5.add(lblSoLuongCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, -1, 50));

        lblAnhCN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/role-model.png"))); // NOI18N
        roundedPanel5.add(lblAnhCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        add(roundedPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 230, 273, 152));

        roundedPanel6.setForeground(new java.awt.Color(51, 153, 255));
        roundedPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel11.setText("TỔNG SỐ NHÂN VIÊN");
        roundedPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        lblSoLuongNV.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        lblSoLuongNV.setText("530");
        roundedPanel6.add(lblSoLuongNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 100, -1, 40));

        lblAnhNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/role-model.png"))); // NOI18N
        roundedPanel6.add(lblAnhNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        add(roundedPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 230, 273, -1));

        roundedPanel2.setForeground(new java.awt.Color(0, 153, 204));

        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaNhanVien.setText("Mã nhân viên:");

        txtMaNhanVien.setEditable(false);
        txtMaNhanVien.setBackground(new java.awt.Color(0, 153, 204));
        txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaNhanVien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenNhanVien.setText("Tên nhân viên:");

        txtTenNhanVien.setEditable(false);
        txtTenNhanVien.setBackground(new java.awt.Color(0, 153, 204));
        txtTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenNhanVien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Vai trò:");

        cmbVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbVaiTro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lí", "Kế toán", "Nhân viên" }));
        cmbVaiTro.setEnabled(false);

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNhanVien)
                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNhanVien)
                    .addGroup(roundedPanel2Layout.createSequentialGroup()
                        .addComponent(cmbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 126, Short.MAX_VALUE))
                    .addComponent(txtTenNhanVien))
                .addContainerGap())
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(roundedPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel4.setText("THÔNG TIN CÁ NHÂN");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        typingLabel2.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        add(typingLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 760, 1390, 160));

        pnlBD1.setBackground(new java.awt.Color(204, 255, 255));
        pnlBD1.setLayout(new java.awt.BorderLayout());
        add(pnlBD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 400, 372, 313));

        pnlBD2.setBackground(new java.awt.Color(204, 255, 255));
        pnlBD2.setLayout(new java.awt.BorderLayout());
        add(pnlBD2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 400, 372, 310));
    }// </editor-fold>//GEN-END:initComponents
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbVaiTro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblAnhCN;
    private javax.swing.JLabel lblAnhLogin;
    private javax.swing.JLabel lblAnhNhanVien;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblSoLuongCN;
    private javax.swing.JLabel lblSoLuongNV;
    private javax.swing.JLabel lblTenNVLogin;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnlBD1;
    private javax.swing.JPanel pnlBD2;
    private ui.RoundedPanel roundedPanel1;
    private ui.RoundedPanel roundedPanel2;
    private ui.RoundedPanel roundedPanel5;
    private ui.RoundedPanel roundedPanel6;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtTenNhanVien;
    private ui.TypingLabel typingLabel2;
    // End of variables declaration//GEN-END:variables
}
