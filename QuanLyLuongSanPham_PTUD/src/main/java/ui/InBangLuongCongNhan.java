/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import dao.BangLuongCongNhanDAO;
import dao.LuuFile;
import entity.BangLuongCongNhan;
import entity.CongNhan;
import entity.DiaChi;
import tablemodels.BangLuongCongNhanTableModel;
import tablemodels.InBangLuongCongNhanTableModel;

/**
 *
 * @author Tuong
 */
public class InBangLuongCongNhan extends javax.swing.JPanel {

	private static final String[] HEADERS = { "Mã bảng lương", "Mã công nhân", "Họ tên", "Tổng lương", "Xác nhận" };

	private InBangLuongCongNhanTableModel inBangLuongCongNhanTableModel;
	private List<BangLuongCongNhan> dsbl;
	private BangLuongCongNhan bangLuongCongNhan;
	private BangLuongCongNhanDAO bangLuongCongNhanDAO;
	private QuanLyLuongCN quanLyLuongCN;
	private String thangTinhLuong;
	private String namTinhLuong;
	private String toNhom;

	/**
	 * Creates new form InBangLuong
	 */
	public InBangLuongCongNhan(String thangTinhLuong, String namTinhLuong, String toNhom) {
		tblInBangLuong = new JTable();
		dsbl = new ArrayList<>();
		this.thangTinhLuong = thangTinhLuong;
		this.namTinhLuong = namTinhLuong;
		this.toNhom = toNhom;
		bangLuongCongNhan = new BangLuongCongNhan();
		bangLuongCongNhanDAO = new BangLuongCongNhanDAO();
//    	quanLyLuongCN = new QuanLyLuongCN();
		initComponents();
		themSuKien();

	}

	private void updateModelTable(List<BangLuongCongNhan> dsbl, String[] headers) {
		inBangLuongCongNhanTableModel = new InBangLuongCongNhanTableModel(headers, dsbl);
		tblInBangLuong.setModel(inBangLuongCongNhanTableModel);
		tblInBangLuong.updateUI();
	}



	private void themSuKien() {

		List<BangLuongCongNhan> dsBangLuongCongNhan = bangLuongCongNhanDAO.layDanhSachBangLuong(thangTinhLuong,
				namTinhLuong, toNhom);
		

		if (dsBangLuongCongNhan.size() == 0) {
			lblTieuDe.setText("BẢNG LƯƠNG CÔNG NHÂN");
		} else {

			lblTieuDe.setText("BẢNG LƯƠNG CÔNG NHÂN \t" + dsBangLuongCongNhan.get(0).getNgayLapBangLuong());

		}
		updateModelTable(dsBangLuongCongNhan, HEADERS);

		btnInBangLuong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Date ngayIn = new java.sql.Date(new java.util.Date().getTime());
				SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
				String ngayInLuong = dt.format(ngayIn);
				String temp = null;
				String outputFilePath = null;
				String thang = thangTinhLuong;
				String nam = namTinhLuong;

				try {

					JFileChooser luuFile = new JFileChooser();
					luuFile.setDialogTitle("Chọn thư mục lưu");

					int luaChon = luuFile.showSaveDialog(null);

					if (luaChon == JFileChooser.APPROVE_OPTION) {
						String filePath = luuFile.getSelectedFile().getAbsolutePath();
						
						if (filePath != null) {
							outputFilePath = filePath + ".pdf";
						}


						outputFilePath = filePath + ".pdf";

					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				LuuFile luuFile = new LuuFile();
				
				boolean ketQua = luuFile.InBangLuong(outputFilePath, thang, nam, toNhom, tblInBangLuong);
				
				if (ketQua){
					JOptionPane.showMessageDialog(null, "In thành công");
				}
				
			}

		});

		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}

			private void btnHuyActionPerformed(ActionEvent evt) {
				Window window = SwingUtilities.getWindowAncestor(btnHuy);
				window.dispose();

			}
		});

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		pnlInBangLuong = new javax.swing.JPanel();
		lblTieuDe = new javax.swing.JLabel();
		jScrollPane9 = new javax.swing.JScrollPane();
		tblInBangLuong = new javax.swing.JTable();
		btnInBangLuong = new javax.swing.JButton();
		btnHuy = new javax.swing.JButton();
		jLabel102 = new javax.swing.JLabel();
		jScrollPane10 = new javax.swing.JScrollPane();
		jTextPane1 = new javax.swing.JTextPane();

		pnlInBangLuong.setBackground(new java.awt.Color(255, 255, 255));

		lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
		lblTieuDe.setText("BẢNG LƯƠNG THÁNG 8/2023");

		tblInBangLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		tblInBangLuong.setModel(new javax.swing.table.DefaultTableModel(new Object[][] { { "", "", "", "", null },
				{ "", "", "", "", null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null } },
				new String[] { "Mã nhân viên", "Mã lương", "Họ và tên ", "Tổng lương", "Xác nhận" }));
		jScrollPane9.setViewportView(tblInBangLuong);

		btnInBangLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		btnInBangLuong.setText("Xác nhận in");

		btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
		btnHuy.setText("Hủy");

		jLabel102.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		jLabel102.setText("Tổng tiền:");

		jTextPane1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		jScrollPane10.setViewportView(jTextPane1);

		javax.swing.GroupLayout pnlInBangLuongLayout = new javax.swing.GroupLayout(pnlInBangLuong);
		pnlInBangLuong.setLayout(pnlInBangLuongLayout);
		pnlInBangLuongLayout.setHorizontalGroup(pnlInBangLuongLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						pnlInBangLuongLayout.createSequentialGroup().addContainerGap().addComponent(jScrollPane9))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						pnlInBangLuongLayout.createSequentialGroup().addGap(0, 166, Short.MAX_VALUE)
								.addGroup(pnlInBangLuongLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInBangLuongLayout
												.createSequentialGroup().addComponent(jLabel102).addGap(18, 18, 18)
												.addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE,
														145, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(29, 29, 29).addComponent(btnInBangLuong).addGap(18, 18, 18)
												.addComponent(btnHuy).addGap(214, 214, 214))
										.addGroup(pnlInBangLuongLayout.createSequentialGroup()

												.addComponent(lblTieuDe).addGap(256, 256, 256)))));

		pnlInBangLuongLayout.setVerticalGroup(pnlInBangLuongLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pnlInBangLuongLayout.createSequentialGroup().addGap(18, 18, 18)
						.addComponent(lblTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 377,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(34, 34, 34)
						.addGroup(pnlInBangLuongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(pnlInBangLuongLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(btnHuy).addComponent(btnInBangLuong))
								.addGroup(pnlInBangLuongLayout.createSequentialGroup().addGap(6, 6, 6).addComponent(
										jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jLabel102, javax.swing.GroupLayout.Alignment.TRAILING))
						.addContainerGap(34, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(pnlInBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 6, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(pnlInBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnInBangLuong;
	private javax.swing.JButton btnHuy;
	private javax.swing.JLabel lblTieuDe;
	private javax.swing.JLabel jLabel102;
	private javax.swing.JScrollPane jScrollPane10;
	private javax.swing.JScrollPane jScrollPane9;
	public javax.swing.JTable tblInBangLuong;
	private javax.swing.JTextPane jTextPane1;
	private javax.swing.JPanel pnlInBangLuong;
	// End of variables declaration//GEN-END:variables
}
