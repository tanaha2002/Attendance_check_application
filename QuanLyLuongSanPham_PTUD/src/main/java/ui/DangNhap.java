/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;

import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.NhanVien;
import entity.TaiKhoan;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import setting.PathSetting;

public class DangNhap extends javax.swing.JFrame {

	private NhanVien nhanVien;
	private TaiKhoanDAO taiKhoanDAO;
	private TaiKhoan taiKhoan;
	private NhanVienDAO nhanVienDAO;
	public DangNhap() {
		nhanVienDAO = new NhanVienDAO();
		nhanVien = new NhanVien();
		taiKhoanDAO = new TaiKhoanDAO();
		taiKhoan = new TaiKhoan();
		initComponents();
	}

	private boolean validateLoginForm() throws Exception {
		boolean kt = true;
		String username = this.txtTenDangNhap.getText();
		String password = new String(this.txtMatKhau.getPassword());
		taiKhoan = taiKhoanDAO.timTaiKhoan(username);
		
		if(username.isBlank()) {
			JOptionPane.showMessageDialog(null, "Không bỏ trống tên đăng nhập");
			kt = false;
		} else if(password.isBlank()) {
			JOptionPane.showMessageDialog(null, "Không bỏ trống mật khẩu");
			kt = false;	
		} else if(taiKhoan == null) {
			JOptionPane.showMessageDialog(null, "Tài khoản không đúng");
			txtTenDangNhap.requestFocus();
			kt = false;
		} else if(!password.equals(taiKhoan.getMatKhau())) {
			JOptionPane.showMessageDialog(null, "Mật khẩu không chính xác!");
			txtMatKhau.requestFocus();
			kt = false;
		}
		
		return kt;
	}
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblTieuDeDangNhap = new javax.swing.JLabel();
        lblTenDangNhap = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        txtTenDangNhap = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        btnOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAnh.setIcon(new ImageIcon(PathSetting.PATH_ICON + "LoginA.jpg"));
        jPanel1.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 500));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 500));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTieuDeDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        lblTieuDeDangNhap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDeDangNhap.setText("ĐĂNG NHẬP");
        lblTieuDeDangNhap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(lblTieuDeDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 550, 40));

        lblTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenDangNhap.setText("Tên đăng nhập:");
        jPanel2.add(lblTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 360, -1));

        lblMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMatKhau.setText("Mật khẩu:");
        jPanel2.add(lblMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 360, -1));

        btnDangNhap.setBackground(new java.awt.Color(51, 153, 255));
        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setText("ĐĂNG NHẬP");
        btnDangNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					btnDangNhapActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        jPanel2.add(btnDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 340, 47));

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenDangNhap.setText("nv000010");
        txtTenDangNhap.setBorder(null);
        txtTenDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDangNhapActionPerformed(evt);
            }
        });
        txtTenDangNhap.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel2.add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 360, 30));

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMatKhau.setText("123456");
        txtMatKhau.setBorder(null);
        txtMatKhau.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel2.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 360, 30));

        btnOut.setIcon(new ImageIcon(PathSetting.PATH_ICON + "close_30px.png"));
        btnOut.setBorder(null);
        btnOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOutMouseClicked(evt);
            }
        });
        jPanel2.add(btnOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 30, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, 550, 500));
        setUndecorated(true);
        setResizable(false);
        
        pack();
    }// </editor-fold>//GEN-END:initComponents

	protected void btnOutMouseClicked(MouseEvent evt) {
		System.exit(0);

	}

	private void txtTenDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDangNhapActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_txtTenDangNhapActionPerformed

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) throws Exception {//GEN-FIRST:event_btnDangNhapActionPerformed
        
		if(validateLoginForm()) {
			String tenTK = txtTenDangNhap.getText();
			String matKhau = txtMatKhau.getText();
			try {
				taiKhoan = taiKhoanDAO.timTaiKhoan(tenTK);
			} catch (Exception ex) {
				Logger.getLogger(DangNhap.class.getName()).log(Level.SEVERE, null, ex);
			}

			NhanVien nhanVienDangDN = nhanVienDAO.timNhanVienBangMaNhanVien(tenTK);
			if(taiKhoan !=  null) {
				if(matKhau.equals(taiKhoan.getMatKhau())) {
					try {
						UIManager.setLookAndFeel(new FlatMacLightLaf());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					MainUI appUI = new MainUI(nhanVienDangDN);
					appUI.setVisible(true);
					this.dispose();
				}
		}
		
		}//GEN-LAST:event_btnDangNhapActionPerformed
    }//GEN-LAST:event_btnDangNhapActionPerformed

	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new DangNhap().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JButton btnOut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblTenDangNhap;
    private javax.swing.JLabel lblTieuDeDangNhap;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
