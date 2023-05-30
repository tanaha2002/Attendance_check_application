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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import dao.BangPhanCongCongNhanDAO;
import dao.CongNhanDAO;
import dao.DiaChiDAO;
import dao.ToNhomDAO;
import entity.BangPhanCongCongNhan;
import entity.CongNhan;
import entity.DiaChi;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.ToNhom;
import setting.PathSetting;
import tablemodels.CongNhanTableModel;

public class TimKiemCN extends javax.swing.JPanel {

	/**
	 * Creates new form QuanLyNV
	 */
	private CongNhanDAO congNhanDAO;
	private CongNhanTableModel congNhanTableModel;
	private CongNhan congNhan;
	private List<CongNhan> dsCN;
	private boolean trangThaiThem;
	private DiaChiDAO diaChiDAO;
	private ToNhomDAO toNhomDAO;
	private BangPhanCongCongNhanDAO phanCongDAO;
	private static final String[] HEADERS = { "Mã công nhân", "Tên công nhân", "Số điện thoai", "Địa chỉ",
			"Ngày vào làm", "Ngày sinh", "Giới tính", "Tổ" };

	public TimKiemCN() {
		phanCongDAO = new BangPhanCongCongNhanDAO();
		tblCongNhan = new JTable();
		congNhanDAO = new CongNhanDAO();
		trangThaiThem = false;
		diaChiDAO = new DiaChiDAO();
		toNhomDAO = new ToNhomDAO();
		initComponents();
		listenerInput();
		themSuKien();
		phimTatChoButton();
		updateComboboxTinh();
		updateComboboxGioiTinh();
		updateComboboxTo();
	}
	private void phimTatChoButton() {
		btnTimKiem.setMnemonic('T');
        btnXoaRong.setMnemonic('X');
        
	}
	private void listenerInput(){
        txtSoDienThoai.addKeyListener(new SoKeyListener());
        txtTenCongNhan.addKeyListener(new ChuoiKeyListener());
    }
	private void setWidthTable() {
		if (tblCongNhan.getColumnModel().getColumnCount() > 0) {

			tblCongNhan.getColumnModel().getColumn(0).setMinWidth(100);
			tblCongNhan.getColumnModel().getColumn(1).setMinWidth(100);
			tblCongNhan.getColumnModel().getColumn(2).setMinWidth(100);
			tblCongNhan.getColumnModel().getColumn(3).setMinWidth(400);
			tblCongNhan.getColumnModel().getColumn(4).setMinWidth(100);
			tblCongNhan.getColumnModel().getColumn(5).setMinWidth(100);
			tblCongNhan.getColumnModel().getColumn(6).setMinWidth(70);
			tblCongNhan.getColumnModel().getColumn(7).setMinWidth(40);
		}
	}

	private void updateModelTable(List<CongNhan> dsCN, String[] headers) {
		congNhanTableModel = new CongNhanTableModel(headers, dsCN);
		tblCongNhan.setModel(congNhanTableModel);
		setWidthTable();
		tblCongNhan.updateUI();
	}

	private void updateComboboxTinh() {
		List<String> dsCacTinh = diaChiDAO.layDanhSachTinh();

		String[] dsTinh = new String[dsCacTinh.size()];
		dsCacTinh.toArray(dsTinh);
		cmbTinhTP.setModel(new DefaultComboBoxModel<String>(dsTinh));
		cmbTinhTP.updateUI();
		updateComboboxHuyen();
	}

	private void updateComboboxHuyen() {
		String tinh = (String) cmbTinhTP.getSelectedItem();
		List<String> dsCacHuyen = diaChiDAO.layDSHuyenTheoTinh(tinh);
		String[] dsHuyen = new String[dsCacHuyen.size()];
		dsCacHuyen.toArray(dsHuyen);
		cmbQuanHuyen.setModel(new DefaultComboBoxModel<String>(dsHuyen));
		cmbQuanHuyen.updateUI();
		updateComboboxXa();

	}

	private void updateComboboxXa() {
		String tinh = (String) cmbTinhTP.getSelectedItem();
		String huyen = (String) cmbQuanHuyen.getSelectedItem();

		List<String> dsTam = diaChiDAO.layDSXaTheoHuyenTinh(tinh, huyen);
		String[] dsXa = new String[dsTam.size()];
		dsTam.toArray(dsXa);
		cmbPhuongXa.setModel(new DefaultComboBoxModel<String>(dsXa));
		cmbPhuongXa.updateUI();

	}

	private void updateComboboxGioiTinh() {

		String gioiTinh = (String) cmbGioiTinh.getSelectedItem();

		List<String> dsTam = new ArrayList<>();
		dsTam = List.of("ALL", "Nam", "Nu");

		String[] dsGioiTinh = new String[dsTam.size()];

		dsTam.toArray(dsGioiTinh);

		cmbGioiTinh.setModel(new DefaultComboBoxModel<String>(dsGioiTinh));
		cmbGioiTinh.updateUI();

	}

	private void updateComboboxTo() {

		String to = (String) cmbGioiTinh.getSelectedItem();

		List<String> dsTam = new ArrayList<>();

		dsTam = List.of("ALL", "Tổ áo khoác", "Tổ áo thun", "Tổ áo sơ mi", "Tổ quần tây", "Tổ đồ bộ");

		String[] dsTo = new String[dsTam.size()];

		dsTam.toArray(dsTo);

		cmbTo.setModel(new DefaultComboBoxModel<String>(dsTo));
		cmbTo.updateUI();

	}

	private void xoaRong() {
		txtMaCongNhan.setText("");
		txtTenCongNhan.setText("");
		txtSoDienThoai.setText("");
		dtNgayVaoLam.setDate(null);
		dtNgaySinh.setDate(null);
		cmbTo.setSelectedItem("ALL");
		cmbGioiTinh.setSelectedItem("ALL");
		lblAnhDaiDien.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + "noimage.png"));
	}

	private void themSuKien() {
		dsCN = congNhanDAO.layDanhSachCongNhan();
		updateModelTable(dsCN, HEADERS);
		updateComboboxGioiTinh();
		if (tblCongNhan.getColumnModel().getColumnCount() > 0) {
			tblCongNhan.getColumnModel().getColumn(3).setMinWidth(320);
		}
		tblCongNhan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String item = (String) tblCongNhan.getModel().getValueAt(tblCongNhan.getSelectedRow(), 0);
				if (item != null) {
					CongNhan congNhan = null;
					try {
						if (!trangThaiThem) {

							congNhan = congNhanDAO.timCongNhanBangMaCongNhan(item);
							fillThongTinCongNhan(congNhan);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinCongNhan(CongNhan congNhan) throws Exception {
				txtMaCongNhan.setText(congNhan.getMaCongNhan());
				txtTenCongNhan.setText(congNhan.getTenCongNhan());
				txtSoDienThoai.setText(congNhan.getSoDienThoai());
				cmbTinhTP.addItem(congNhan.getDiaChi().getTinhTP());
				cmbTinhTP.setSelectedItem(congNhan.getDiaChi().getTinhTP());
				cmbQuanHuyen.addItem(congNhan.getDiaChi().getQuanHuyen());
				cmbQuanHuyen.setSelectedItem(congNhan.getDiaChi().getQuanHuyen());
				cmbPhuongXa.addItem(congNhan.getDiaChi().getPhuongXa());
				cmbPhuongXa.setSelectedItem(congNhan.getDiaChi().getPhuongXa());
				// cmbVaiTro.addItem(nv.getVaiTro());
				cmbTo.setSelectedItem(congNhan.getToNhom().getTenToNhom());
				cmbGioiTinh.setSelectedItem(congNhan.getGioiTinh().compareTo("Nu") == 0 ? "Nu" : "Nam");
				dtNgaySinh.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(congNhan.getNgaySinh().toString()));
				dtNgayVaoLam.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(congNhan.getNgayVaoLam().toString()));
				lblAnhDaiDien.setIcon(new ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + congNhan.getAnhDaiDien()));
			}
		});

		cmbTinhTP.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				updateComboboxHuyen();

			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

		});
		cmbQuanHuyen.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				updateComboboxXa();

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}
		});

		cmbPhuongXa.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub

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
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "TimKiemCN.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn tìm kiếm công nhân", JOptionPane.PLAIN_MESSAGE);
				}
			});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnTimKiemActionPerformed(e);

			}
			private void btnTimKiemActionPerformed(ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
				// TODO Auto-generated method stub
				try {

					dsCN = layDanhSachCongNhanTimKiem();

					if (dsCN.size() != 0) {
						updateModelTable(dsCN, HEADERS);

					} else
						JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên nào");

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}
			private List<CongNhan> layDanhSachCongNhanTimKiem() {
				String maNV = txtMaCongNhan.getText().isEmpty() ? null : txtMaCongNhan.getText().trim();
				String tenNV = txtTenCongNhan.getText().trim().isEmpty() ? null : txtTenCongNhan.getText().trim();
				String soDienThoai = txtSoDienThoai.getText().isEmpty() ? null : txtSoDienThoai.getText().trim();
				Date ngaySinh = null;
				Date ngayVaoLam = null;
				try {
					ngaySinh = dtNgaySinh.getDate();
					ngayVaoLam = dtNgayVaoLam.getDate();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				String gioiTinh = cmbGioiTinh.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbGioiTinh.getSelectedItem().toString().trim();
				String tinhTP = cmbTinhTP.getSelectedItem().toString().trim();
				String quanHuyen = cmbQuanHuyen.getSelectedItem().toString().trim();
				String phuongXa = cmbPhuongXa.getSelectedItem().toString().trim();
				String toNhom = cmbTo.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbTo.getSelectedItem().toString().trim();

				DiaChi diaChiTam = new DiaChi(tinhTP + quanHuyen + phuongXa, tinhTP, quanHuyen, phuongXa);
				try {
					return congNhanDAO.timKiemCongNhanTheoNhieuThuocTinh(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam,
							soDienThoai, toNhom, diaChiTam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

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
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlQLCN = new javax.swing.JPanel();
        lblMaCongNhan = new javax.swing.JLabel();
        lblTenCongNhan = new javax.swing.JLabel();
        lblSoDienThoai = new javax.swing.JLabel();
        txtMaCongNhan = new javax.swing.JTextField();
        txtTenCongNhan = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        dtNgayVaoLam = new com.toedter.calendar.JDateChooser();
        lblDiaChi = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblNgayVaoLam = new javax.swing.JLabel();
        lblTo = new javax.swing.JLabel();
        cmbTinhTP = new javax.swing.JComboBox<>();
        cmbQuanHuyen = new javax.swing.JComboBox<>();
        cmbPhuongXa = new javax.swing.JComboBox<>();
        cmbGioiTinh = new javax.swing.JComboBox<>();
        lblAnhDaiDien = new javax.swing.JLabel();
        cmbTo = new javax.swing.JComboBox<>();
        scrTableCongNhan = new javax.swing.JScrollPane();
        tblCongNhan = new javax.swing.JTable();
        btnChonAnh = new javax.swing.JButton();
        dtNgaySinh = new com.toedter.calendar.JDateChooser();
        btnTimKiem = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        btnHuongDan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlQLCN.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLCN.setPreferredSize(new java.awt.Dimension(1250, 475));

        lblMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan.setText("Mã công nhân:");

        lblTenCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongNhan.setText("Tên công nhân:");

        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoDienThoai.setText("Số điện thoại:");

        txtMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongNhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongNhan.setBorder(null);
        txtTenCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoDienThoai.setBorder(null);
        txtSoDienThoai.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        dtNgayVaoLam.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDiaChi.setText("Địa chỉ:");

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGioiTinh.setText("Giới tính:");

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgaySinh.setText("Ngày sinh:");

        lblNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayVaoLam.setText("Ngày vào làm:");

        lblTo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTo.setText("Tổ:");

        cmbTinhTP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTinhTP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tỉnh/Thành phố" }));

        cmbQuanHuyen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbQuanHuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quận/Huyện" }));

        cmbPhuongXa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbPhuongXa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phường/Xã" }));

        cmbGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Nam", "Nữ" }));

        lblAnhDaiDien.setBackground(new java.awt.Color(0, 204, 153));
        lblAnhDaiDien.setForeground(new java.awt.Color(0, 255, 204));

        cmbTo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Tổ" }));

        scrTableCongNhan.setBackground(new java.awt.Color(255, 255, 255));
        scrTableCongNhan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách công nhân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16))); // NOI18N

        tblCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCongNhan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã công nhân", "Tên công nhân", "Số điện thoại", "Email", "Địa chỉ", "Giới tính", "Ngày sinh", "Ngày vào làm", "Tổ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCongNhan.setSelectionBackground(new java.awt.Color(51, 153, 255));
        tblCongNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCongNhanMouseClicked(evt);
            }
        });
        scrTableCongNhan.setViewportView(tblCongNhan);

        btnChonAnh.setText("Hình ảnh");
        btnChonAnh.setEnabled(false);

        dtNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        dtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

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

        javax.swing.GroupLayout pnlQLCNLayout = new javax.swing.GroupLayout(pnlQLCN);
        pnlQLCN.setLayout(pnlQLCNLayout);
        pnlQLCNLayout.setHorizontalGroup(
            pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLCNLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaCongNhan)
                    .addComponent(lblTenCongNhan)
                    .addComponent(lblSoDienThoai)
                    .addComponent(lblDiaChi))
                .addGap(6, 6, 6)
                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlQLCNLayout.createSequentialGroup()
                        .addComponent(cmbTinhTP, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLCNLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGioiTinh)
                    .addComponent(lblNgaySinh)
                    .addComponent(lblNgayVaoLam)
                    .addComponent(lblTo))
                .addGap(31, 31, 31)
                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLCNLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnChonAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(170, Short.MAX_VALUE))
                    .addGroup(pnlQLCNLayout.createSequentialGroup()
                        .addComponent(lblAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(scrTableCongNhan)
        );
        pnlQLCNLayout.setVerticalGroup(
            pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLCNLayout.createSequentialGroup()
                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLCNLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLCNLayout.createSequentialGroup()
                                .addComponent(lblMaCongNhan)
                                .addGap(22, 22, 22)
                                .addComponent(lblTenCongNhan)
                                .addGap(20, 20, 20)
                                .addComponent(lblSoDienThoai)
                                .addGap(21, 21, 21)
                                .addComponent(lblDiaChi))
                            .addGroup(pnlQLCNLayout.createSequentialGroup()
                                .addComponent(txtMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(txtTenCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTinhTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(pnlQLCNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlQLCNLayout.createSequentialGroup()
                                .addComponent(lblGioiTinh)
                                .addGap(22, 22, 22)
                                .addComponent(lblNgaySinh)
                                .addGap(22, 22, 22)
                                .addComponent(lblNgayVaoLam)
                                .addGap(16, 16, 16)
                                .addComponent(lblTo))
                            .addGroup(pnlQLCNLayout.createSequentialGroup()
                                .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(dtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(dtNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlQLCNLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlQLCNLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(btnChonAnh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrTableCongNhan, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLCN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1649, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlQLCN, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void tblCongNhanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblCongNhanMouseClicked

	}// GEN-LAST:event_tblCongNhanMouseClicked

	// private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//
	// GEN-FIRST:event_btnThemActionPerformed
	// pnlChucNangTo.removeAll();
	// pnlChucNangTo.setLayout(new BorderLayout());
	// PanelXacNhan pnlXacNhan = new PanelXacNhan();
	// pnlChucNangTo.add(pnlXacNhan);
	//
	// ActionListener listenerHuy = new java.awt.event.ActionListener() {
	// public void actionPerformed(java.awt.event.ActionEvent evt) {
	// System.out.println("ok");
	// pnlChucNangTo.removeAll();
	// pnlChucNangTo.setLayout(new BorderLayout());
	// pnlChucNangTo.add(pnlChucNang);
	// pnlChucNangTo.updateUI();
	// }
	// };
	// pnlXacNhan.huyAddActionListener(listenerHuy);
	// pnlChucNangTo.repaint();
	// pnlChucNangTo.revalidate();
	// }// GEN-LAST:event_btnThemActionPerformed

	// private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//
	// GEN-FIRST:event_btnSuaActionPerformed
	// // TODO add your handling code here:
	// }// GEN-LAST:event_btnSuaActionPerformed
	//
	// private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//
	// GEN-FIRST:event_btnXoaActionPerformed
	// // TODO add your handling code here:
	// }// GEN-LAST:event_btnXoaActionPerformed
	//
	// private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//
	// GEN-FIRST:event_btnTimKiemActionPerformed
	// // TODO add your handling code here:
	// }// GEN-LAST:event_btnTimKiemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonAnh;
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JComboBox<String> cmbGioiTinh;
    private javax.swing.JComboBox<String> cmbPhuongXa;
    private javax.swing.JComboBox<String> cmbQuanHuyen;
    private javax.swing.JComboBox<String> cmbTinhTP;
    private javax.swing.JComboBox<String> cmbTo;
    private com.toedter.calendar.JDateChooser dtNgaySinh;
    private com.toedter.calendar.JDateChooser dtNgayVaoLam;
    private javax.swing.JLabel lblAnhDaiDien;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblMaCongNhan;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblTenCongNhan;
    private javax.swing.JLabel lblTo;
    private javax.swing.JPanel pnlQLCN;
    private javax.swing.JScrollPane scrTableCongNhan;
    private javax.swing.JTable tblCongNhan;
    private javax.swing.JTextField txtMaCongNhan;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenCongNhan;
    // End of variables declaration//GEN-END:variables
}
