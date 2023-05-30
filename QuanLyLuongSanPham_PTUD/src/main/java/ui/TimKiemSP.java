/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import dao.BangPhanCongCongNhanDAO;
import dao.SanPhamDAO;
import entity.BangChamCongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.NhanVien;
import entity.SanPham;
import setting.PathSetting;
import tablemodels.SanPhamTableModel;

public class TimKiemSP extends javax.swing.JPanel {

	/**
	 * Creates new form QuanLyNV
	 */
	private SanPhamDAO sanPhamDAO;
	private SanPhamTableModel sanPhamTableModel;
	private List<SanPham> dsSP;
	private boolean trangThaiThem;
	private static final String[] HEADERS = {"Mã sản phẩm", "Tên sản phẩm" , "Số lượng công đoạn", "Giá tiền", "Số lượng sản xuất", "Chất liệu", "Kích thước"};
	private BangPhanCongCongNhanDAO phanCongDAO;
	public TimKiemSP() {
		phanCongDAO = new BangPhanCongCongNhanDAO();
		sanPhamDAO = new SanPhamDAO();
		initComponents();
		themSuKien();
		phimTatChoButton();
	}

	private void phimTatChoButton() {
		btnTimKiem.setMnemonic('T');
        btnXoaRong.setMnemonic('X');
        
	}
	private void setWidthTable() {
		if (tblSanPham.getColumnModel().getColumnCount() > 0) {

			tblSanPham.getColumnModel().getColumn(0).setMinWidth(200);
			tblSanPham.getColumnModel().getColumn(1).setMinWidth(280);
			tblSanPham.getColumnModel().getColumn(2).setMinWidth(60);
			tblSanPham.getColumnModel().getColumn(3).setMinWidth(200);
			tblSanPham.getColumnModel().getColumn(4).setMinWidth(150);
			tblSanPham.getColumnModel().getColumn(5).setMinWidth(190);
			tblSanPham.getColumnModel().getColumn(6).setMinWidth(100);
		}
	}

	private void updateModelTable(List<SanPham> dsSP,String[] headers) {
		sanPhamTableModel = new SanPhamTableModel(headers, dsSP);
		tblSanPham.setModel(sanPhamTableModel);
		setWidthTable();
		tblSanPham.updateUI();
	}
	
	private void xoaRong() {
		txtMaSanPham.setText("");
		txtTenSanPham.setText("");
		txtGiaTien.setText("");
		txaMoTaSP.setText("");
		txtSoLuong.setText("");
		cmbChatLieu.setSelectedIndex(0);
		cmbKichThuoc.setSelectedIndex(0);
		txtSoLuongCongDoan.setText("");
		lblAnhDaiDien.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_SANPHAM + "noimage.png"));
	}

	private void themSuKien() {
		dsSP = sanPhamDAO.layDanhSachSanPham();
		updateModelTable(dsSP, HEADERS);
		if (tblSanPham.getColumnModel().getColumnCount() > 0) {
			tblSanPham.getColumnModel().getColumn(6).setMinWidth(320);

		}
		tblSanPham.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String item = (String) tblSanPham.getModel().getValueAt(tblSanPham.getSelectedRow(), 0);
				if (item != null) {
					SanPham sp = null;
					try {
						if(!trangThaiThem) {
							sp = sanPhamDAO.timSanPham(item);
							fillThongTinSanPham(sp);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinSanPham(SanPham sp) throws Exception {
				// TODO Auto-generated method stub
				txtTenSanPham.setText(sp.getTenSanPham());
				txtMaSanPham.setText(sp.getMaSanPham());
				txtGiaTien.setText(String.valueOf(sp.getGiaTien()));
				txtSoLuong.setText(String.valueOf(sp.getSoLuongSanXuat()));
				cmbChatLieu.setSelectedItem(sp.getChatLieu());
				cmbKichThuoc.setSelectedItem(sp.getKichThuoc());
				txtSoLuongCongDoan.setText(String.valueOf(sp.getSoLuongCongDoan()));
				txaMoTaSP.setText(sp.getMoTa());
				lblAnhDaiDien.setIcon(new ImageIcon(PathSetting.PATH_IMAGE_SANPHAM + sp.getAnhSP()));
				lblAnhDaiDien.updateUI();
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
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "TimKiemSP.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn tìm kiếm sản phẩm", JOptionPane.PLAIN_MESSAGE);
				}
			});

		btnTimKiem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					timKiemActionPerformed();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void timKiemActionPerformed() {
				try {

					try {
						dsSP = layDanhSachSanPhamTimKiem();

						if (dsSP.size() != 0) {
							updateModelTable(dsSP, HEADERS);

						} else
							JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm nào");


					} catch (Exception e1) {
						e1.printStackTrace();
					}

					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private List<SanPham> layDanhSachSanPhamTimKiem() throws Exception {
				String maSP = txtMaSanPham.getText().isEmpty() ? null : txtMaSanPham.getText().trim();
				String tenSP = txtTenSanPham.getText().trim().isEmpty() ? null : txtTenSanPham.getText().trim();
				String giaTien = txtGiaTien.getText().isEmpty() ? null : txtGiaTien.getText().trim();
				String moTa = txaMoTaSP.getText().trim().isEmpty() ? null : txaMoTaSP.getText().trim();
				String soLuong = txtSoLuong.getText().isEmpty() ? null : txtSoLuong.getText().trim();
				String chatLieu = cmbChatLieu.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbChatLieu.getSelectedItem().toString();
				String kichThuoc = cmbKichThuoc.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbKichThuoc.getSelectedItem().toString();
				String soLuongCD = txtSoLuongCongDoan.getText().isEmpty() ? null : txtSoLuongCongDoan.getText().trim();
				return sanPhamDAO.timKiemSanPhamTheoNhieuThuocTinh(maSP, tenSP, giaTien, moTa, soLuong, chatLieu, kichThuoc, soLuongCD);

			}
		});
		
		btnXoaRong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnXoaRongActionPerformed(e);

			}

			private void btnXoaRongActionPerformed(ActionEvent evt) {
				xoaRong();

			}

		});
	}
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlQLSP = new javax.swing.JPanel();
        lblMaSanPham = new javax.swing.JLabel();
        lblTenSanPham = new javax.swing.JLabel();
        lblGiaTien = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        txtGiaTien = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        lblChatLieu = new javax.swing.JLabel();
        lblKichThuoc = new javax.swing.JLabel();
        lblSoLuongCongDoan = new javax.swing.JLabel();
        cmbChatLieu = new javax.swing.JComboBox<>();
        cmbKichThuoc = new javax.swing.JComboBox<>();
        lblAnhDaiDien = new javax.swing.JLabel();
        scrTableSanPham = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnChonAnh = new javax.swing.JButton();
        txtSoLuongCongDoan = new javax.swing.JTextField();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMoTaSP = new javax.swing.JTextArea();
        btnTimKiem = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        btnHuongDan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlQLSP.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLSP.setPreferredSize(new java.awt.Dimension(1250, 475));

        lblMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSanPham.setText("Mã sản phẩm:");

        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPham.setText("Tên sản phẩm:");

        lblGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGiaTien.setText("Giá tiền:");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmail.setText("Số lượng:");

        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenSanPham.setBorder(null);
        txtTenSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtGiaTien.setBorder(null);
        txtGiaTien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuong.setBorder(null);
        txtSoLuong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblChatLieu.setText("Chất liệu:");

        lblKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKichThuoc.setText("Kích thước:");

        lblSoLuongCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongCongDoan.setText("Số lượng công đoạn:");

        cmbChatLieu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "Vải cotton", "Vải da", "Vải thun", "Vải len", "Vải jean", "Vải denim" }));

        cmbKichThuoc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALL", "S", "M", "L", "XL", "XXL", "3XL", "4XL" }));

        lblAnhDaiDien.setBackground(new java.awt.Color(0, 204, 153));
        lblAnhDaiDien.setForeground(new java.awt.Color(0, 255, 204));

        scrTableSanPham.setBackground(new java.awt.Color(255, 255, 255));
        scrTableSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16))); // NOI18N

        tblSanPham.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblSanPham.setSelectionBackground(new java.awt.Color(51, 153, 255));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        scrTableSanPham.setViewportView(tblSanPham);

        btnChonAnh.setText("Hình ảnh");
        btnChonAnh.setEnabled(false);

        txtSoLuongCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuongCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblMoTa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMoTa.setText("Mô tả:");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txaMoTaSP.setColumns(20);
        txaMoTaSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txaMoTaSP.setRows(5);
        jScrollPane1.setViewportView(txaMoTaSP);

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.setPreferredSize(new java.awt.Dimension(137, 33));

        btnHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnHuongDan.setText("?");

        javax.swing.GroupLayout pnlQLSPLayout = new javax.swing.GroupLayout(pnlQLSP);
        pnlQLSP.setLayout(pnlQLSPLayout);
        pnlQLSPLayout.setHorizontalGroup(
            pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLSPLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaSanPham)
                    .addComponent(lblTenSanPham)
                    .addComponent(lblGiaTien)
                    .addComponent(lblEmail)
                    .addComponent(lblChatLieu))
                .addGap(5, 5, 5)
                .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45)
                .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                        .addComponent(lblKichThuoc)
                        .addGap(13, 13, 13)
                        .addComponent(cmbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                        .addComponent(lblSoLuongCongDoan)
                        .addGap(32, 32, 32)
                        .addComponent(txtSoLuongCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblMoTa)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(131, 131, 131)
                .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(btnChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                        .addComponent(lblAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(scrTableSanPham)
        );
        pnlQLSPLayout.setVerticalGroup(
            pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLSPLayout.createSequentialGroup()
                .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLSPLayout.createSequentialGroup()
                                .addComponent(lblAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnChonAnh))
                            .addGroup(pnlQLSPLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                                        .addComponent(lblMaSanPham)
                                        .addGap(25, 25, 25)
                                        .addComponent(lblTenSanPham)
                                        .addGap(25, 25, 25)
                                        .addComponent(lblGiaTien)
                                        .addGap(15, 15, 15)
                                        .addComponent(lblEmail)
                                        .addGap(25, 25, 25)
                                        .addComponent(lblChatLieu))
                                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(txtTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(cmbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(pnlQLSPLayout.createSequentialGroup()
                                        .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblKichThuoc)
                                            .addComponent(cmbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addGroup(pnlQLSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSoLuongCongDoan)
                                            .addComponent(txtSoLuongCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(15, 15, 15)
                                        .addComponent(lblMoTa)
                                        .addGap(5, 5, 5)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrTableSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLSP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1697, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlQLSP, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

	}//GEN-LAST:event_tblSanPhamMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JComboBox<String> cmbChatLieu;
    private javax.swing.JComboBox<String> cmbKichThuoc;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnhDaiDien;
    private javax.swing.JLabel lblChatLieu;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblKichThuoc;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblSoLuongCongDoan;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JPanel pnlQLSP;
    private javax.swing.JScrollPane scrTableSanPham;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextArea txaMoTaSP;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongCongDoan;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}
