/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.ThongKeNhanVienDAO;

/**
 *
 * @author Tuong
 */
public class ThongKeLuongNV extends javax.swing.JPanel {
	ThongKeNhanVienDAO thongKeNhanVienDAO;
	JpnBieuDoNhanVien bieuDo;
	private void comBoBoxTieuChi2() {
		cmbTieuChi2.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Top 5 nhân viên lương cao nhất", "Top 5 nhân viên lương thấp nhất","Top 10 nhân viên lương cao nhất","Top 10 nhân viên lương thấp nhất" }));
		cmbTieuChi2.repaint();
	}
	private void tatComboboxDenThangNam() {
//	      lblDenNam.setForeground(Color.DARK_GRAY);
//	      lblDenThang.setForeground(Color.DARK_GRAY);
		cmbDenThang.enable(false);
		cmbDenThang.repaint();
		cmbDenNam.enable(false);
		cmbDenNam.repaint();

	}

	private void batComboboxDenThangNam() {

		cmbDenThang.enable(true);
		cmbDenThang.repaint();
		cmbDenNam.enable(true);
		cmbDenNam.repaint();
	}

	public ThongKeLuongNV() {
		thongKeNhanVienDAO = new ThongKeNhanVienDAO();
		initComponents();
		themSuKien();
		comBoBoxTieuChi2();
		tatComboboxDenThangNam();
	}

	public void themSuKien() {
		cmbLoaiThongKe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cmbLoaiThongKeActionPerformed(e);

			}

			private void cmbLoaiThongKeActionPerformed(java.awt.event.ActionEvent evt) {

				System.out.println(".actionPerformed()");
				String selected = (String) cmbLoaiThongKe.getSelectedItem();
				if (selected.equalsIgnoreCase("Thống kê theo tháng")) {
					cmbTuThang.enable();
					cmbTuThang.repaint();
					tatComboboxDenThangNam();
					cmbTieuChi2.setModel(new javax.swing.DefaultComboBoxModel<>(
							new String[] { "Top 5 nhân viên lương cao nhất", "Top 5 nhân viên lương thấp nhất","Top 10 nhân viên lương cao nhất","Top 10 nhân viên lương thấp nhất" }));
					cmbTieuChi2.repaint();
				} else if (cmbLoaiThongKe.getSelectedItem().toString().trim()
						.equalsIgnoreCase("Thống kê theo khoảng thời gian")) {
					batComboboxDenThangNam();
					cmbTuThang.enable();
					cmbTuThang.repaint();
					cmbTieuChi2.setModel(new javax.swing.DefaultComboBoxModel<>(
							new String[] { "Top 5 nhân viên lương cao nhất", "Top 5 nhân viên lương thấp nhất","Top 10 nhân viên lương cao nhất","Top 10 nhân viên lương thấp nhất" }));
					cmbTieuChi2.repaint();

				} else if (cmbLoaiThongKe.getSelectedItem().toString().trim().equalsIgnoreCase("Thống kê theo năm")) {
					tatComboboxDenThangNam();
					cmbTuThang.enable(false);
					cmbTuThang.repaint();
					cmbTieuChi2.setModel(new javax.swing.DefaultComboBoxModel<>(
							new String[] { "Top 5 nhân viên lương cao nhất", "Top 5 nhân viên lương thấp nhất","Top 10 nhân viên lương cao nhất","Top 10 nhân viên lương thấp nhất" }));
					cmbTieuChi2.repaint();
				}
			}
		});
		
		btnHuongDan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnHuongDanActionPerformed(e);

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
				private void btnHuongDanActionPerformed(ActionEvent evt) throws Exception {
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "ThongKeNhanVien.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn thống kê lương nhân viên", JOptionPane.PLAIN_MESSAGE);
				}
			});
		
		btnThongKe.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThongKeActionPerformed(evt);

			}

			private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {
//                String tieuChi = "Thống kê top 5 công nhân lương cao nhất";
				String loaiThongKe = cmbLoaiThongKe.getSelectedItem().toString().trim();
				String tieuChi1 = cmbTieuChi1.getSelectedItem().toString().trim();
				String tieuChi2 = cmbTieuChi2.getSelectedItem().toString().trim();
				String tuThang = cmbTuThang.getSelectedItem().toString().trim();
				String tuNam = cmbTuNam.getSelectedItem().toString().trim();
				String denThang = cmbDenThang.getSelectedItem().toString().trim();
				String denNam = cmbDenNam.getSelectedItem().toString().trim();
				bieuDo = new JpnBieuDoNhanVien(BarChart, LineChart, loaiThongKe, tieuChi1, tieuChi2);
				if (!loaiThongKe.equalsIgnoreCase("Thống kê theo khoảng thời gian")) {
					bieuDo.ThemDuLieuBieuDo(
							thongKeNhanVienDAO.topNhanVienLuong(loaiThongKe, tieuChi2, tuThang, tuNam));
					bieuDo.HienThiBieuDo();
				}
				else {
					bieuDo.ThemDuLieuBieuDo(
							thongKeNhanVienDAO.luongNhanTheoKhoangThoiGian(loaiThongKe,tieuChi1, tieuChi2, tuThang, tuNam,denThang,denNam));
					bieuDo.HienThiBieuDo();
				}
//				else if (loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
//					if (tieuChi1.equalsIgnoreCase("Lương")) {
//						if (tieuChi2.equalsIgnoreCase("Tổng lương")) {
//							bieuDo.ThemDuLieuBieuDo(
//									thongKeCongNhanDAO.tongLuongTrongNam(cmbTuNam.getSelectedItem().toString()));
//							bieuDo.HienThiBieuDo();
//						} else {
//							bieuDo.ThemDuLieuBieuDo(
//									thongKeCongNhanDAO.topNamCongNhanTheoLuongTrongNam(tieuChi1, tieuChi2, tuNam));
//							bieuDo.HienThiBieuDo();
//						}
//
//					}
//
//				} else if (loaiThongKe.equalsIgnoreCase("Thống kê theo khoảng thời gian")) {
//					if (tieuChi1.equalsIgnoreCase("Lương")) {
//						if (tieuChi2.equalsIgnoreCase("Tổng lương")) {
//							bieuDo.ThemDuLieuBieuDo(
//									thongKeCongNhanDAO.tongLuongTheoKhoangThoiGian(tuThang, tuNam, denThang, denNam));
//							bieuDo.HienThiBieuDo();
//						} else {
//							bieuDo.ThemDuLieuBieuDo(thongKeCongNhanDAO.TopNamCongNhanTheoKhoangThoiGian(tuThang, tuNam,
//									denThang, denNam, tieuChi1, tieuChi2));
//							bieuDo.HienThiBieuDo();
//						}
//
//					}

//				}

			}

		});

	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jComboBox5 = new javax.swing.JComboBox<>();
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
		lblTieuChi1 = new javax.swing.JLabel();
		cmbTieuChi1 = new javax.swing.JComboBox<>();
		lblDenThang = new javax.swing.JLabel();
		cmbDenThang = new javax.swing.JComboBox<>();
		lblDenNam = new javax.swing.JLabel();
		cmbDenNam = new javax.swing.JComboBox<>();
		cmbTieuChi2 = new javax.swing.JComboBox<>();
		lblTieuChi2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		btnHuongDan = new javax.swing.JButton();

		jComboBox5.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
		cmbTuThang.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		jPanel26.add(cmbTuThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

		lblTuThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblTuThang.setText("Tháng");
		jPanel26.add(lblTuThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

		lblTuNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblTuNam.setText("Năm");
		jPanel26.add(lblTuNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, -1, -1));

		btnHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnHuongDan.setText("?");
        jPanel26.add(btnHuongDan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1540, 0, 40, 40));
		
		cmbLoaiThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		cmbLoaiThongKe.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Thống kê theo tháng", "Thống kê theo năm", "Thống kê theo khoảng thời gian" }));
		jPanel26.add(cmbLoaiThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 360, -1));

		lblLoaiThongKe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblLoaiThongKe.setText("Chọn loại thống kê:");
		jPanel26.add(lblLoaiThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, -1));

		BarChart.setBackground(new java.awt.Color(255, 255, 255));
		BarChart.setLayout(new java.awt.BorderLayout());
		jPanel26.add(BarChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 820, 560));

		lblTieuChi1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblTieuChi1.setText("Tiêu chí 1:");
		jPanel26.add(lblTieuChi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 180, 110, -1));

		cmbTieuChi1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		cmbTieuChi1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lương", "Số ngày đi làm" }));
		jPanel26.add(cmbTieuChi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 360, -1));

		lblDenThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblDenThang.setForeground(new java.awt.Color(204, 204, 204));
		lblDenThang.setText("Đến tháng");
		jPanel26.add(lblDenThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 90, -1, -1));

		cmbDenThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		cmbDenThang.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		jPanel26.add(cmbDenThang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, -1, -1));

		lblDenNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblDenNam.setForeground(new java.awt.Color(204, 204, 204));
		lblDenNam.setText("Năm");
		jPanel26.add(lblDenNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 90, -1, -1));

		cmbDenNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		cmbDenNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2021", "2022", "2023" }));
		jPanel26.add(cmbDenNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 90, -1, -1));

		cmbTieuChi2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		cmbTieuChi2.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Top 5 nhân viên lương cao nhất", "Top 5 nhân viên lương thấp nhất","Top 10 nhân viên lương cao nhất","Top 10 nhân viên lương thấp nhất" }));
		jPanel26.add(cmbTieuChi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 220, 360, -1));

		lblTieuChi2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblTieuChi2.setText("Tiêu chí 2:");
		jPanel26.add(lblTieuChi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 220, 110, -1));

		jLabel1.setBackground(new java.awt.Color(255, 255, 255));
		jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setText("THỐNG KÊ LƯƠNG NHÂN VIÊN");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addComponent(jPanel26,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addComponent(jLabel1)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jPanel26,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	private void btnInBangThongKeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInBangThongKeActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnInBangThongKeActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel BarChart;
	private javax.swing.JPanel LineChart;
	private javax.swing.JButton btnInBangThongKe;
	private javax.swing.JButton btnHuongDan;
	private javax.swing.JButton btnThongKe;
	private javax.swing.JComboBox<String> cmbDenNam;
	private javax.swing.JComboBox<String> cmbDenThang;
	private javax.swing.JComboBox<String> cmbLoaiThongKe;
	private javax.swing.JComboBox<String> cmbTieuChi1;
	private javax.swing.JComboBox<String> cmbTieuChi2;
	private javax.swing.JComboBox<String> cmbTuNam;
	private javax.swing.JComboBox<String> cmbTuThang;
	private javax.swing.JComboBox<String> jComboBox5;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel26;
	private javax.swing.JLabel lblDenNam;
	private javax.swing.JLabel lblDenThang;
	private javax.swing.JLabel lblLoaiThongKe;
	private javax.swing.JLabel lblTieuChi1;
	private javax.swing.JLabel lblTieuChi2;
	private javax.swing.JLabel lblTuNam;
	private javax.swing.JLabel lblTuThang;
	// End of variables declaration//GEN-END:variables
}
