/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;
import java.awt.Window;
import javax.swing.SwingUtilities;
import setting.PathSetting;

/**
 *
 * @author 1805v
 */
public class DoiMatKhau extends javax.swing.JPanel {

	private String matKhauMoi;
	private String matKhauCu;
	private String xacNhanMatKhau;
	private TaiKhoanDAO taiKhoanDAO;
	private NhanVien nhanVienLogin;
	private TaiKhoan taiKhoan;


	public DoiMatKhau(NhanVien nhanVien) {
		this.nhanVienLogin = nhanVien;
		taiKhoanDAO = new TaiKhoanDAO();
		taiKhoan = new TaiKhoan();
		initComponents();
		themSuKien();

	}

	private void themSuKien() {
		lblAnh.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + "noimage.png"));
		lblTenNhanVien.setText(nhanVienLogin.getTenNhanVien().toUpperCase());
		lblChucVu.setText(nhanVienLogin.getVaiTro().toUpperCase());
		btnDoiMatKhau.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)  {
				try {
					taiKhoan = taiKhoanDAO.timTaiKhoan(nhanVienLogin.getMaNhanVien());
					String matKhauCu = String.valueOf(pwfMatKhauCu.getPassword());
					String matKhauMoi = String.valueOf(pwfMatKhauMoi.getPassword());
					String matKhauXacNhan = String.valueOf(pwfXacNhanMatKhau.getPassword());
					
					if(matKhauCu.isBlank()) {
						JOptionPane.showMessageDialog(null, "Chưa nhập mật khẩu cũ!");
					} else if(matKhauMoi.isBlank()) {
						JOptionPane.showMessageDialog(null, "Chưa nhập mật khẩu mới!");
					} else if(!matKhauMoi.matches("^.{6,}$")) {
						JOptionPane.showMessageDialog(null, "Mật khẩu phải có nhiều hơn 6 ký tự!");
					} else if(matKhauXacNhan.isBlank()) {
						JOptionPane.showMessageDialog(null, "Chưa xác nhận mật khẩu mới!");
					} else if (!matKhauMoi.equals(matKhauXacNhan)) {
						JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận không trùng!!!",
								"Lỗi",JOptionPane.ERROR_MESSAGE);
					} else if(!matKhauCu.equals(taiKhoan.getMatKhau())) {
						JOptionPane.showMessageDialog(null, "Mật khẩu cũ không chính xác!!!",
								"Lỗi",JOptionPane.ERROR_MESSAGE);
					} else {

						JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công!!");
						TaiKhoan tk = taiKhoanDAO.timTaiKhoan(nhanVienLogin.getMaNhanVien());
						tk.setMatKhau(matKhauMoi);
						taiKhoanDAO.doiMatKhau(tk);

					}
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMatKhauCu = new javax.swing.JLabel();
        lblMatKhauMoi = new javax.swing.JLabel();
        lblXacNhanMatKhau = new javax.swing.JLabel();
        pwfMatKhauCu = new javax.swing.JPasswordField();
        pwfMatKhauMoi = new javax.swing.JPasswordField();
        pwfXacNhanMatKhau = new javax.swing.JPasswordField();
        btnHuy = new javax.swing.JButton();
        btnDoiMatKhau = new javax.swing.JButton();
        lblAnh = new javax.swing.JLabel();
        lblChucVu = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐỔI MẬT KHẨU");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 790, 40));

        lblMatKhauCu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMatKhauCu.setText("Mật khẩu cũ:");
        add(lblMatKhauCu, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 110, -1));

        lblMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMatKhauMoi.setText("Mật khẩu mới:");
        add(lblMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, 120, -1));

        lblXacNhanMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblXacNhanMatKhau.setText("Xác nhận mật khẩu mới:");
        add(lblXacNhanMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 200, -1));

        pwfMatKhauCu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pwfMatKhauCu.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        add(pwfMatKhauCu, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 190, -1));

        pwfMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pwfMatKhauMoi.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        add(pwfMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 190, -1));

        pwfXacNhanMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pwfXacNhanMatKhau.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        add(pwfXacNhanMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 190, -1));

        btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 317, 190, 30));

        btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/check-mark.png"))); // NOI18N
        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });
        add(btnDoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 317, 170, 30));

        lblAnh.setBackground(new java.awt.Color(0, 255, 153));
        lblAnh.setForeground(new java.awt.Color(204, 204, 204));
        add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 180, 220));

        lblChucVu.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblChucVu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChucVu.setText("Quản lý");
        add(lblChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 220, 30));

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTenNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNhanVien.setText("Hoàng Xuân Trường 1000");
        add(lblTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 220, 30));
    }// </editor-fold>//GEN-END:initComponents

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
            Window window = SwingUtilities.getWindowAncestor(btnHuy);
		window.dispose();
	}//GEN-LAST:event_btnHuyActionPerformed

	private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
		// TODO add your handling code here:

	}//GEN-LAST:event_btnDoiMatKhauActionPerformed

	//
	//    private void doiMatKhau() {
	//        try {
	//        	if(pwfMatKhauCu.equals()) {
	//	            if (!this.pwfMatKhauMoi.getText().matches("((?=.*\\d)(?=.*[a-z]).{6,20})")) {
	//	                throw new IllegalArgumentException("Mật khẩu phải chứa ít nhất một ký tự chữ thường,"
	//	                        + " một ký tự số và có từ 6 đến 20 ký tự!");
	//	            }
	//	            if (!this.pwfMatKhauMoi.getText().equals(this.pwfXacNhanMatKhau.getText())) {
	//	                throw new IllegalArgumentException("Mật khẩu mới và xác nhận mật khẩu không khớp");
	//	            }
	//            	
	//        	}
	//           

	//            if (!Objects.isNull(userResponse)) {
	//
	//                this.txtMatKhau.setText("");
	//                this.txtMatKhauMoi.setText("");
	//                this.txtXacNhan.setText("");
	//                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
	//                return;
	//            }

	//            throw new RuntimeException("Something went wrong");
	//        } catch (Exception e) {
	//            JOptionPane.showMessageDialog(this, e.getMessage());
	//        }
	//	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnHuy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblChucVu;
    private javax.swing.JLabel lblMatKhauCu;
    private javax.swing.JLabel lblMatKhauMoi;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblXacNhanMatKhau;
    private javax.swing.JPasswordField pwfMatKhauCu;
    private javax.swing.JPasswordField pwfMatKhauMoi;
    private javax.swing.JPasswordField pwfXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables


}