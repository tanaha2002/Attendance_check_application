/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import entity.BangChamCongCongNhan;
import entity.BangLuongCongNhan;
import entity.CongNhan;
import tablemodels.ChiTietLuongCongNhanTableModel;
import dao.BangChamCongCongNhanDAO;
import dao.BangLuongCongNhanDAO;
import dao.CongNhanDAO;
import dao.LuuFile;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;

/**
 *
 * @author 1805v
 */
public class ChiTietBangLuongCongNhan extends javax.swing.JPanel {

	private static final String[] HEADERS = { "STT", "Ngày làm", "Mã sản phẩm", "Tên sản phẩm", "Mã công đoạn",
			"Tên công đoạn", "Ca làm", "Số lượng", "Thành tiền" };
	private ChiTietLuongCongNhanTableModel chiTietLuongCongNhanTableModel;
	private String maCongNhan;
	private int thangChamCong;
	private int namChamCong;
	private CongNhan congNhan;

	/**
	 * Creates new form ChiTietBangLuongCongNhan
	 */
	public ChiTietBangLuongCongNhan(int thang, int nam, String maCongNhan) {
		this.thangChamCong = thang;
		this.namChamCong = nam;
		this.maCongNhan = maCongNhan;
		congNhan = new CongNhan();

		initComponents();
		themSuKien();
	}

	private void updateModelTable(List<BangChamCongCongNhan> dsChamCong, String[] headers) {
		chiTietLuongCongNhanTableModel = new ChiTietLuongCongNhanTableModel(headers, dsChamCong);
		tblBangLuongChiTiet.setModel(chiTietLuongCongNhanTableModel);
		tblBangLuongChiTiet.updateUI();
	}

	private void themSuKien() {

		BangChamCongCongNhanDAO bangChamCongCongNhanDAO = new BangChamCongCongNhanDAO();
		List<BangChamCongCongNhan> dsbangChamCongCongNhan = bangChamCongCongNhanDAO
				.layDanhSachChamCongTheoThang(maCongNhan, thangChamCong, namChamCong);
		
			
			
			
		CongNhanDAO congNhanDAO = new CongNhanDAO();
		congNhan = congNhanDAO.timCongNhanBangMaCongNhan(maCongNhan);
		txtMaCongNhan.setText(maCongNhan);
		txtTenCongNhan.setText(congNhan.getTenCongNhan());
		txtMaCongNhan.setEditable(false);
		txtTenCongNhan.setEditable(false);
		
		double  tongTien = 0;
		String tongTienFormat = null;
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		BangLuongCongNhanDAO bangLuongCongNhanDAO = new BangLuongCongNhanDAO();
		for(BangChamCongCongNhan bangChamCongCongNhan : dsbangChamCongCongNhan)
		{
//			double tienTangCa = 0;
//			double tienTheoNgay = bangChamCongCongNhan.getPhanCong().getCongDoan().getTienCongDoan() * bangChamCongCongNhan.getSoLuongLam();
//			if(bangChamCongCongNhan.getCaLam().equals("Tối")) {
//				tienTangCa = tienTheoNgay;
//			}
//			String thanhTien = formatter.format( tienTheoNgay);
//			tongTien += tienTheoNgay ;
//			tongTien += tienTangCa;
			try {
				BangLuongCongNhan bangLuongCongNhan = bangLuongCongNhanDAO.timBangLuongTheoMaCongNhan(bangChamCongCongNhan.getPhanCong().getCongNhan().getMaCongNhan());
				if(bangLuongCongNhan != null) {
					tongTien = bangLuongCongNhan.getLuong();
					
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
		
		
		tongTienFormat = formatter.format(tongTien);
		txtTongTien.setText(tongTienFormat);
		txtTongTien.setEditable(false);
		
		updateModelTable(dsbangChamCongCongNhan, HEADERS);
		
		
		btnInChiTiet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Date ngayIn = new java.sql.Date(new java.util.Date().getTime());
				SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
				String formatDate = dt.format(ngayIn);



//				MessageFormat header = new MessageFormat("CHI TIẾT BẢNG LƯƠNG CÔNG NHÂN " + congNhan.getTenCongNhan() );
//				MessageFormat footer = new MessageFormat("Quản lí lương sản phẩm - Nhóm 4" +  formatDate  );
//
//
//				
//				
//				try {
//					if(tblBangLuongChiTiet.print(JTable.PrintMode.FIT_WIDTH, header, footer))
//						JOptionPane.showMessageDialog(null, "In thành công");
//				} catch (Exception e2) {
//					JOptionPane.showMessageDialog(null, "In thất bại" + e2.getMessage());
//				}
//
//			}
				String outputFilePath = null;
				String thang = String.valueOf(thangChamCong);
				String nam = String.valueOf(namChamCong);
				
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
				
				boolean ketQua = luuFile.InBangLuong(outputFilePath, thang, nam, "NV", tblBangLuongChiTiet);
				
				if (ketQua){
					JOptionPane.showMessageDialog(null, "In thành công");
				}
				else {
					JOptionPane.showMessageDialog(null, "In thất bại");

				}

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrChiTietBangLuong = new javax.swing.JScrollPane();
        tblBangLuongChiTiet = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenCongNhan = new javax.swing.JTextField();
        txtMaCongNhan = new javax.swing.JTextField();
        btnInChiTiet = new javax.swing.JButton();
        txtTongTien = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrChiTietBangLuong.setBackground(new java.awt.Color(255, 255, 255));
        scrChiTietBangLuong.setBorder(null);

        tblBangLuongChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblBangLuongChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Ngày làm", "Mã sản phẩm", "Tên sản phẩm", "Mã công đoạn", "Tên công đoạn", "Ca làm", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangLuongChiTiet.setSelectionBackground(new java.awt.Color(232, 57, 95));
        scrChiTietBangLuong.setViewportView(tblBangLuongChiTiet);

        add(scrChiTietBangLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 150, 1060, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("CHI TIẾT LƯƠNG CÔNG NHÂN TRONG THÁNG");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tên công nhân");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mã công nhân");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        txtTenCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        add(txtTenCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, 170, 30));

        txtMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
       
        add(txtMaCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 160, 30));

        btnInChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnInChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_printbangluong.png"))); // NOI18N
        btnInChiTiet.setText("In chi tiết");
        add(btnInChiTiet, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 90, -1, -1));

        txtTongTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTongTien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtTongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongTienActionPerformed(evt);
            }
        });
        add(txtTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, 140, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Tổng tiền");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 90, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void txtTongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongTienActionPerformed

	private void txtMaSanPham1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMaSanPham1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_txtMaSanPham1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInChiTiet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane scrChiTietBangLuong;
    private javax.swing.JTable tblBangLuongChiTiet;
    private javax.swing.JTextField txtMaCongNhan;
    private javax.swing.JTextField txtTenCongNhan;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
