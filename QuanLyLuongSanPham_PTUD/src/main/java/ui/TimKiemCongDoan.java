/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import dao.CongDoanSanPhamDAO;
import dao.SanPhamDAO;
import entity.CongDoanSanPham;
import entity.SanPham;
import tablemodels.CongDoanTableModel;
import tablemodels.SanPhamTableModel;

public class TimKiemCongDoan extends javax.swing.JPanel {

	/**
	 * Creates new form QuanLyCongDoan
	 */
	private SanPhamDAO sanPhamDAO;
	private CongDoanSanPhamDAO congDoanDAO;
	private SanPhamTableModel sanPhamPhanCongTableModel;
	private CongDoanTableModel congDoanTableModel;
	private List<SanPham> dsSP;
	private List<CongDoanSanPham> dsCD;
	private int sanPhamChon;
	private boolean trangThaiThem;
	private CongDoanSanPham congDoanDangChon;
	private SanPham sanPhamDangChon;
	private double tongTienCacCd;
	private static final String[] HEADERSCD = { "STT", "Mã công đoạn", "Tên  công đoạn", "Giá tiền", "Số lượng cần làm",
			"Tình trạng", "Mã sản phẩm" };
	private static final String[] HEADERS = { "Mã sản phẩm", "Tên sản phẩm" };
	private BangPhanCongCongNhanDAO phanCongDAO;
	private BangChamCongCongNhanDAO chamCongDAO;

	public TimKiemCongDoan() {
		sanPhamDAO = new SanPhamDAO();
		congDoanDAO = new CongDoanSanPhamDAO();
		phanCongDAO = new BangPhanCongCongNhanDAO();
		chamCongDAO = new BangChamCongCongNhanDAO();
		initComponents();
		themSuKien();
		phimTatChoButton();
	}
	private void phimTatChoButton() {
		btnTimKiem.setMnemonic('T');
        btnXoaRong.setMnemonic('X');
        
	}
	private void updateSanPhamTableModel(String[] headers, List<SanPham> dsSP) {
		sanPhamPhanCongTableModel = new SanPhamTableModel(headers, dsSP);
		tblDanhSachSanPham.setModel(sanPhamPhanCongTableModel);
		tblDanhSachSanPham.updateUI();
	}

	private void updateCongDoanTableModel(String[] headers, List<CongDoanSanPham> dsCD) {
		congDoanTableModel = new CongDoanTableModel(dsCD, headers);
		tblCongDoan.setModel(congDoanTableModel);
		tblCongDoan.updateUI();
	}

	private void xoaRong() {
		txtMaCongDoan.setText("");
		txtGiaTien.setText("");
		txtTenCongDoan.setText("");
		txaNoiDungCongDoan.setText("");
		txtSoLuongCan.setText("");
		txtThuTuCongDoan.setText("");
	}

	private void themSuKien() {
		dsSP = sanPhamDAO.layDanhSachSanPham();
		updateSanPhamTableModel(HEADERS, dsSP);
		tblDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblDanhSachSanPhamMouseClicked(evt);
				sanPhamChon = tblDanhSachSanPham.getSelectedRow();
				if (sanPhamChon != -1) {
					if (!trangThaiThem) {
						dsCD = congDoanDAO.layDanhSachTheoMaSP(dsSP.get(sanPhamChon).getMaSanPham());
						updateCongDoanTableModel(HEADERSCD, dsCD);
					}
				}
				String itemSP = (String) tblDanhSachSanPham.getModel().getValueAt(tblDanhSachSanPham.getSelectedRow(),
						0);
				if (itemSP != null) {
					SanPham sp = null;
					try {
						if (!trangThaiThem) {
							sp = sanPhamDAO.timSanPham(itemSP);
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
				txtTenCongDoan.setText("");
				txtMaCongDoan.setText("");
				txtSoLuongCan.setText("");
				txtGiaTien.setText("");
				txaNoiDungCongDoan.setText("");
				txtThuTuCongDoan.setText("");
			}
		});

		tblCongDoan.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblCongDoanMouseClicked(evt);
				String itemSP = (String) tblDanhSachSanPham.getModel().getValueAt(tblDanhSachSanPham.getSelectedRow(),
						0);
				String itemCD = (String) tblCongDoan.getModel().getValueAt(tblCongDoan.getSelectedRow(), 1);

				if (itemCD != null && itemSP != null) {
					CongDoanSanPham cd = null;
					try {
						if (!trangThaiThem) {
							cd = congDoanDAO.timCongDoan(itemCD, itemSP);
							fillThongTinCongDoan(cd);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinCongDoan(CongDoanSanPham cd) throws Exception {
				txtTenCongDoan.setText(cd.getTenCongDoan());
				txtMaCongDoan.setText(cd.getMaCongDoan());
				txtSoLuongCan.setText(String.valueOf(cd.getSoLuong()));
				txtGiaTien.setText(String.valueOf(cd.getTienCongDoan()));
				txaNoiDungCongDoan.setText(cd.getMoTa());
				txtThuTuCongDoan.setText(String.valueOf(cd.getThuTu()));
			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				int selectedRow = tblDanhSachSanPham.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần tìm kiếm công đoạn");
					return;
				}
				try {
					timKiemActionPerformed();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void timKiemActionPerformed() {
				try {
					try {
						dsCD = layDanhSachCongDoanTimKiem();
						if (dsCD.size() != 0) {
							updateCongDoanTableModel(HEADERSCD, dsCD);

						} else
							JOptionPane.showMessageDialog(null, "Không tìm thấy công đoạn nào");

					} catch (Exception e1) {
						e1.printStackTrace();
					}

					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private List<CongDoanSanPham> layDanhSachCongDoanTimKiem() throws Exception {
				String maCD = txtMaCongDoan.getText().isEmpty() ? null : txtMaCongDoan.getText().trim();
				String tenCD = txtTenCongDoan.getText().trim().isEmpty() ? null : txtTenCongDoan.getText().trim();
				String giaTien = txtGiaTien.getText().isEmpty() ? null : txtGiaTien.getText().trim();
				String noiDung = txaNoiDungCongDoan.getText().isEmpty() ? null : txaNoiDungCongDoan.getText().trim();
				String thuTu = txtThuTuCongDoan.getText().trim().isEmpty() ? null : txtThuTuCongDoan.getText().trim();
				String soLuong = txtSoLuongCan.getText().trim().isEmpty() ? null : txtSoLuongCan.getText().trim();
				return congDoanDAO.timKiemCongDoanTrongSPTheoNhieuThuocTinh(maCD, tenCD, giaTien,
						txtMaSanPham.getText().trim(), noiDung, thuTu, soLuong);

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
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        scrTableSanPham = new javax.swing.JScrollPane();
        tblDanhSachSanPham = new javax.swing.JTable();
        lblTenCongDoan = new javax.swing.JLabel();
        lblMaSanPham = new javax.swing.JLabel();
        lblTenSanPham = new javax.swing.JLabel();
        lblMaCongDoan = new javax.swing.JLabel();
        lblSoLuongCan = new javax.swing.JLabel();
        lblNoiDung = new javax.swing.JLabel();
        lblGiaTien = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        txtMaCongDoan = new javax.swing.JTextField();
        txtTenCongDoan = new javax.swing.JTextField();
        txtSoLuongCan = new javax.swing.JTextField();
        txtGiaTien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaNoiDungCongDoan = new javax.swing.JTextArea();
        btnTimKiem = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        txtThuTuCongDoan = new javax.swing.JTextField();
        lblThuTuLam = new javax.swing.JLabel();
        scrTableCongDoan = new javax.swing.JScrollPane();
        tblCongDoan = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1250, 475));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrTableSanPham.setBackground(new java.awt.Color(255, 255, 255));
        scrTableSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        tblDanhSachSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblDanhSachSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSanPham.setSelectionBackground(new java.awt.Color(51, 153, 255));
        tblDanhSachSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSanPhamMouseClicked(evt);
            }
        });
        scrTableSanPham.setViewportView(tblDanhSachSanPham);

        jPanel5.add(scrTableSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 440, 390));

        lblTenCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        lblTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenCongDoan.setText("Tên công đoạn:");
        jPanel5.add(lblTenCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, -1, 40));

        lblMaSanPham.setBackground(new java.awt.Color(255, 255, 255));
        lblMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSanPham.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaSanPham.setText("Mã sản phẩm:");
        jPanel5.add(lblMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, 38));

        lblTenSanPham.setBackground(new java.awt.Color(255, 255, 255));
        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPham.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPham.setText("Tên sản phẩm:");
        jPanel5.add(lblTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, -1, 40));

        lblMaCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        lblMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongDoan.setText("Mã công đoạn:");
        jPanel5.add(lblMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, -1, 40));

        lblSoLuongCan.setBackground(new java.awt.Color(255, 255, 255));
        lblSoLuongCan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongCan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoLuongCan.setText("Số lượng cần:");
        jPanel5.add(lblSoLuongCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, -1, 40));

        lblNoiDung.setBackground(new java.awt.Color(255, 255, 255));
        lblNoiDung.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNoiDung.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoiDung.setText("Nội dung:");
        jPanel5.add(lblNoiDung, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 140, -1, 40));

        lblGiaTien.setBackground(new java.awt.Color(255, 255, 255));
        lblGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGiaTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGiaTien.setText("Tiền công đoạn:");
        jPanel5.add(lblGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 50, -1, 40));

        txtMaSanPham.setEditable(false);
        txtMaSanPham.setBackground(new java.awt.Color(204, 204, 204));
        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 190, 25));

        txtTenSanPham.setEditable(false);
        txtTenSanPham.setBackground(new java.awt.Color(204, 204, 204));
        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTenSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, 190, 25));

        txtMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 150, 190, 25));

        txtTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTenCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 200, 180, 25));

        txtSoLuongCan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongCan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuongCan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtSoLuongCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 260, 62, 25));

        txtGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtGiaTien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGiaTien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 60, 196, 25));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txaNoiDungCongDoan.setColumns(20);
        txaNoiDungCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txaNoiDungCongDoan.setRows(5);
        jScrollPane1.setViewportView(txaNoiDungCongDoan);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 150, 300, 120));

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.add(btnTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 390, 170, 40));

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.setPreferredSize(new java.awt.Dimension(137, 33));
        jPanel5.add(btnXoaRong, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 390, 180, 40));

        txtThuTuCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtThuTuCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtThuTuCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtThuTuCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 100, 196, 25));

        lblThuTuLam.setBackground(new java.awt.Color(255, 255, 255));
        lblThuTuLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThuTuLam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblThuTuLam.setText("Thứ tự làm:");
        jPanel5.add(lblThuTuLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 90, -1, 40));

        scrTableCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        scrTableCongDoan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách công đoạn sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16))); // NOI18N

        tblCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCongDoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã công đoạn", "Tên  công đoạn", "Số lượng cần làm", "Số lượng đã làm", "Tổng tiền công đoạn", "Thời hạn", "Mưc độ hoàn thành (%)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCongDoan.setSelectionBackground(new java.awt.Color(51, 102, 255));
        tblCongDoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCongDoanMouseClicked(evt);
            }
        });
        scrTableCongDoan.setViewportView(tblCongDoan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1264, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1232, Short.MAX_VALUE)
                .addComponent(scrTableCongDoan, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrTableCongDoan, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1264, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 846, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void tblDanhSachSanPhamMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblDanhSachSanPhamMouseClicked

	}// GEN-LAST:event_tblDanhSachSanPhamMouseClicked

	private void tblCongDoanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblCongDoanMouseClicked

	}// GEN-LAST:event_tblCongDoanMouseClicked

	private void btnPhanCongCongDoanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnPhanCongCongDoanMouseClicked

	}

	// GEN-LAST:event_btnPhanCongCongDoanMouseClicked

	private void btnPhanCongCongDoanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnPhanCongCongDoanActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnPhanCongCongDoanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblMaCongDoan;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblNoiDung;
    private javax.swing.JLabel lblSoLuongCan;
    private javax.swing.JLabel lblTenCongDoan;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblThuTuLam;
    private javax.swing.JScrollPane scrTableCongDoan;
    private javax.swing.JScrollPane scrTableSanPham;
    private javax.swing.JTable tblCongDoan;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTextArea txaNoiDungCongDoan;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaCongDoan;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuongCan;
    private javax.swing.JTextField txtTenCongDoan;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtThuTuCongDoan;
    // End of variables declaration//GEN-END:variables
}
