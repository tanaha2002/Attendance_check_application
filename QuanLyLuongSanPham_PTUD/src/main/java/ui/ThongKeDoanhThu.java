/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.border.MatteBorder;

import dao.ThongKeCongNhanDAO;
import dao.ThongKeDoanhThuDAO;

/**
 *
 * @author Tuong
 */
public class ThongKeDoanhThu extends javax.swing.JPanel {

    /**
     * Creates new form ThongKeDoanhThu
     */
	 private void tatComboboxDenThangNam(){
//	      lblDenNam.setForeground(Color.DARK_GRAY);
//	      lblDenThang.setForeground(Color.DARK_GRAY);
	      cmbDenThang.enable(false);
	      cmbDenThang.repaint();
	      cmbDenNam.enable(false);
	      cmbDenNam.repaint();

	  }
	  private void batComboboxDenThangNam(){

	      cmbDenThang.enable(true);
	      cmbDenThang.repaint();
	      cmbDenNam.enable(true);
	      cmbDenNam.repaint();
	  }
	private JpnBieuDoDoanhThu bieuDo;
	private ThongKeDoanhThuDAO thongKeDoanhThuDAO;
    public ThongKeDoanhThu() {
    	thongKeDoanhThuDAO = new ThongKeDoanhThuDAO();
        initComponents();
        themSuKien();
        tatComboboxDenThangNam();
        
    }


private void themSuKien() {
	cmbLoaiThongKe.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			cmbLoaiThongKeActionPerformed(e);

		}

		private void cmbLoaiThongKeActionPerformed(java.awt.event.ActionEvent evt) {
			String selected = null;

			Object obj = evt.getSource();
			if (obj == cmbLoaiThongKe) {

				selected = (String) cmbLoaiThongKe.getSelectedItem();
				
			}
			if (selected.equalsIgnoreCase("Thống kê theo tháng")) {
				cmbTuThang.enable();
				cmbTuThang.repaint();
				tatComboboxDenThangNam();
				

			} else if (cmbLoaiThongKe.getSelectedItem().toString().trim()
					.equalsIgnoreCase("Thống kê theo khoảng thời gian")) {
				batComboboxDenThangNam();
				cmbTuThang.enable();
				cmbTuThang.repaint();

			} else if (cmbLoaiThongKe.getSelectedItem().toString().trim().equalsIgnoreCase("Thống kê theo năm")) {
				tatComboboxDenThangNam();
				cmbTuThang.enable(false);
				cmbTuThang.repaint();

			}
		}

	});
    btnThongKe.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnThongKeActionPerformed(evt);

        }
        private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {
        	String loaiThongKe = cmbLoaiThongKe.getSelectedItem().toString();
        	String tieuChi = cmbTieuChi1.getSelectedItem().toString();
        	String thang = cmbTuThang.getSelectedItem().toString();
        	String nam = cmbTuNam.getSelectedItem().toString();
        	bieuDo = new JpnBieuDoDoanhThu(BarChart, LineChart, loaiThongKe, tieuChi);
        	if(!loaiThongKe.equalsIgnoreCase("Thống kê theo khoảng thời gian")) {
        		bieuDo.ThemDuLieuBieuDo(thongKeDoanhThuDAO.topNamSanPhamHoanThanh(tieuChi, thang, nam, loaiThongKe));
        		bieuDo.HienThiBieuDo();
        	}
        	else {
        		String denThang = cmbDenThang.getSelectedItem().toString();
        		String denNam = cmbDenNam.getSelectedItem().toString();
        		bieuDo.ThemDuLieuBieuDo(thongKeDoanhThuDAO.topSanPhamTrongKhoangThoiGian(tieuChi, loaiThongKe, thang, nam, denThang, denNam));
        		bieuDo.HienThiBieuDo();
        	}
        }
        });
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel26 = new javax.swing.JPanel();
        LineChart = new javax.swing.JPanel();
        btnInBangThongKe = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        cmbTuNam = new javax.swing.JComboBox<>();
        cmbTuThang = new javax.swing.JComboBox<>();
        lblTuThang = new javax.swing.JLabel();
        lblTuNam = new javax.swing.JLabel();
        cmbLoaiThongKe = new javax.swing.JComboBox<>();
        lblLoaiThongKe = new javax.swing.JLabel();
        BarChart = new javax.swing.JPanel();
        cmbTieuChi1 = new javax.swing.JComboBox<>();
        lblDenThang = new javax.swing.JLabel();
        cmbDenThang = new javax.swing.JComboBox<>();
        lblDenNam = new javax.swing.JLabel();
        cmbDenNam = new javax.swing.JComboBox<>();
        lblTieuChi2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LineChart.setBackground(new java.awt.Color(255, 255, 255));
        LineChart.setLayout(new java.awt.BorderLayout());
        jPanel26.add(LineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 320, 750, 550));

        btnInBangThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnInBangThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_printbangluong.png"))); // NOI18N
        btnInBangThongKe.setText("In bảng thống kê");
        btnInBangThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInBangThongKeActionPerformed(evt);
            }
        });
        jPanel26.add(btnInBangThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 140, 200, 40));

        btnThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/growth.png"))); // NOI18N
        btnThongKe.setText("Thống kê");
        jPanel26.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 140, 200, -1));

        cmbTuNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTuNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2021", "2022", "2023" }));
        jPanel26.add(cmbTuNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 20, -1, -1));

        cmbTuThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTuThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel26.add(cmbTuThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        lblTuThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTuThang.setText("Tháng");
        jPanel26.add(lblTuThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        lblTuNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTuNam.setText("Năm");
        jPanel26.add(lblTuNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, -1));

        cmbLoaiThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbLoaiThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thống kê theo tháng", "Thống kê theo năm", "Thống kê theo khoảng thời gian" }));
        jPanel26.add(cmbLoaiThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 360, -1));

        lblLoaiThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLoaiThongKe.setText("Chọn loại thống kê:");
        jPanel26.add(lblLoaiThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, -1));

        BarChart.setBackground(new java.awt.Color(255, 255, 255));
        BarChart.setLayout(new java.awt.BorderLayout());
        jPanel26.add(BarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 820, 560));

        cmbTieuChi1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTieuChi1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Top 5 sản phẩm có doanh thu cao nhất", "Top 5 sản phẩm có doanh thu ít nhất" }));
        jPanel26.add(cmbTieuChi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 360, -1));

        lblDenThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDenThang.setForeground(new java.awt.Color(204, 204, 204));
        lblDenThang.setText("Đến tháng");
        jPanel26.add(lblDenThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

        cmbDenThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbDenThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        jPanel26.add(cmbDenThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, -1, -1));

        lblDenNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDenNam.setForeground(new java.awt.Color(204, 204, 204));
        lblDenNam.setText("Năm");
        jPanel26.add(lblDenNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, -1, -1));

        cmbDenNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbDenNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2021", "2022", "2023" }));
        jPanel26.add(cmbDenNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, -1, -1));

        lblTieuChi2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTieuChi2.setText("Tiêu chí:");
        jPanel26.add(lblTieuChi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 110, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THỐNG KÊ DOANH THU");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInBangThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInBangThongKeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInBangThongKeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarChart;
    private javax.swing.JPanel LineChart;
    private javax.swing.JButton btnInBangThongKe;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JComboBox<String> cmbDenNam;
    private javax.swing.JComboBox<String> cmbDenThang;
    private javax.swing.JComboBox<String> cmbLoaiThongKe;
    private javax.swing.JComboBox<String> cmbTieuChi1;
    private javax.swing.JComboBox<String> cmbTuNam;
    private javax.swing.JComboBox<String> cmbTuThang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JLabel lblDenNam;
    private javax.swing.JLabel lblDenThang;
    private javax.swing.JLabel lblLoaiThongKe;
    private javax.swing.JLabel lblTieuChi2;
    private javax.swing.JLabel lblTuNam;
    private javax.swing.JLabel lblTuThang;
    // End of variables declaration//GEN-END:variables
}
