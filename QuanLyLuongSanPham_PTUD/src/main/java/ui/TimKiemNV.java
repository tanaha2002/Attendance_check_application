/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import dao.DiaChiDAO;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.DiaChi;
import entity.NhanVien;
import entity.TaiKhoan;
import javax.swing.UIManager;
import setting.PathSetting;
import tablemodels.NhanVienTableModel;

/**
 *
 * @author Hoang Truong
 */
public class TimKiemNV extends javax.swing.JPanel {

	/**
	 * Creates new form QuanLyNV
	 */
	private NhanVienDAO nhanVienDAO;
	private NhanVienTableModel nhanVienTableModel;
	private DiaChiDAO diaChiDAO;
	private TaiKhoanDAO taiKhoanDAO;
	private List<NhanVien> dsNV;
	private boolean trangThaiThem;
	private NhanVien nhanVienLogin;
	private static final String[] HEADERS = { "Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Email", "Số điện thoại",
			"Địa chỉ", "Ngày vào làm", "Giới tính", "Vai trò", "Trình độ", "Ngoại Ngữ" };

	public TimKiemNV() {
		nhanVienDAO = new NhanVienDAO();
		diaChiDAO = new DiaChiDAO();
		taiKhoanDAO = new TaiKhoanDAO();
		trangThaiThem = false;
		initComponents();
		listenerInput();
		themSuKien();
		phimTatChoButton();
		updateComboboxTinh();

	}

	private void phimTatChoButton() {
		btnTimKiem.setMnemonic('T');
        btnXoaRong.setMnemonic('X');
        
	}
	private void listenerInput(){
        txtSoDienThoai.addKeyListener(new SoKeyListener());
        txtTenNhanVien.addKeyListener(new ChuoiKeyListener());
    }
	private void setWidthTable() {
		if (tblNhanVien.getColumnModel().getColumnCount() > 0) {

			tblNhanVien.getColumnModel().getColumn(0).setMinWidth(60);
			tblNhanVien.getColumnModel().getColumn(1).setMinWidth(85);
			tblNhanVien.getColumnModel().getColumn(2).setMinWidth(65);
			tblNhanVien.getColumnModel().getColumn(3).setMinWidth(90);
			tblNhanVien.getColumnModel().getColumn(4).setMinWidth(70);
			tblNhanVien.getColumnModel().getColumn(5).setMinWidth(309);
			tblNhanVien.getColumnModel().getColumn(6).setMinWidth(68);
			tblNhanVien.getColumnModel().getColumn(7).setMinWidth(40);
			tblNhanVien.getColumnModel().getColumn(8).setMinWidth(59);
			tblNhanVien.getColumnModel().getColumn(9).setMinWidth(40);
			tblNhanVien.getColumnModel().getColumn(10).setMinWidth(40);

		}
	}

	// them su kien cho UI Quan Ly Nhan Vien
	private void updateModelTable(List<NhanVien> dsNV, String[] headers) {
		nhanVienTableModel = new NhanVienTableModel(headers, dsNV);
		tblNhanVien.setModel(nhanVienTableModel);
		setWidthTable();
		tblNhanVien.updateUI();

	}

	private void updateComboboxTinh() {
		List<String> dsTam = diaChiDAO.layDanhSachTinh();

		String[] dsTinh = new String[dsTam.size()];
		dsTam.toArray(dsTinh);
		cmbTinhTP.setModel(new DefaultComboBoxModel<String>(dsTinh));
		cmbTinhTP.updateUI();
		updateComboboxHuyen();
	}

	private void updateComboboxHuyen() {
		String tinh = (String) cmbTinhTP.getSelectedItem();
		List<String> dsTam = diaChiDAO.layDSHuyenTheoTinh(tinh);
		String[] dsHuyen = new String[dsTam.size()];
		dsTam.toArray(dsHuyen);
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

	private void xoaRong() {
		txtMaNhanVien.setText("");
		txtTenNhanVien.setText("");
		txtSoDienThoai.setText("");
		txtEmail.setText("");
		cmbVaiTro.setSelectedItem("All");
		cmbGioiTinh.setSelectedItem("All");
		dtNgaySinh.setDate(null);
		dtNgayVaoLam.setDate(null);
		lblAnhDaiDien.setIcon(new javax.swing.ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + "noimage.png"));
		cmbTrinhDoChuyenMon.setSelectedItem("All");
		cmbTrinhDoNgoaiNgu.setSelectedItem("All");
	}

	private void themSuKien() {
		dsNV = nhanVienDAO.layDanhSachNhanVien();
		updateModelTable(dsNV, HEADERS);
		updateComboboxTinh();
		tblNhanVien.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String maNhanVien = (String) tblNhanVien.getModel().getValueAt(tblNhanVien.getSelectedRow(), 0);
				if (maNhanVien != null) {
					NhanVien nv = null;
					try {
						if (!trangThaiThem) {
							nv = nhanVienDAO.timNhanVienBangMaNhanVien(maNhanVien);
							fillThongTinNhanVien(nv);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinNhanVien(NhanVien nv) throws Exception {
				// TODO Auto-generated method stub
				txtTenNhanVien.setText(nv.getTenNhanVien());
				txtMaNhanVien.setText(nv.getMaNhanVien());
				txtSoDienThoai.setText(nv.getSoDienThoai());
				cmbTinhTP.setSelectedItem(nv.getDiaChi().getTinhTP());
				cmbQuanHuyen.addItem(nv.getDiaChi().getQuanHuyen());
				cmbQuanHuyen.setSelectedItem(nv.getDiaChi().getQuanHuyen());
				cmbPhuongXa.addItem(nv.getDiaChi().getPhuongXa());
				cmbPhuongXa.setSelectedItem(nv.getDiaChi().getPhuongXa());
				cmbVaiTro.setSelectedItem(nv.getVaiTro());
				cmbGioiTinh.setSelectedItem(nv.getGioiTinh().compareTo("Nữ") == 0 ? "Nữ" : "Nam");
				txtEmail.setText(nv.getEmail());
				dtNgaySinh.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgaySinh().toString()));
				dtNgayVaoLam.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(nv.getNgayVaoLam().toString()));
				lblAnhDaiDien.setIcon(new ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + nv.getAnhDaiDien()));
				cmbTrinhDoChuyenMon.setSelectedItem(nv.getTrinhDoChuyenMon());
				cmbTrinhDoNgoaiNgu.setSelectedItem(nv.getTrinhDoNgoaiNgu());
			}

		});
		// cap nhat lai combobox Huyen sau khi combobox tinh duoc select
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
		// cap nhat lai combobox xa sau khi combobox huyen duoc selected
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
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "TimKiemNV.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn tìm kiếm nhân viên", JOptionPane.PLAIN_MESSAGE);
				}
			});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnTimKiemActionPerformed(e);

			}

			private void btnTimKiemActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				try {
					dsNV = layDanhSachNVTimKiem();
					if (dsNV.size() != 0) {
						updateModelTable(dsNV, HEADERS);

					} else
						JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên nào");

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}

			private List<NhanVien> layDanhSachNVTimKiem() {
				String maNV = txtMaNhanVien.getText().isEmpty() ? null : txtMaNhanVien.getText().trim();
				String tenNV = txtTenNhanVien.getText().trim().isEmpty() ? null : txtTenNhanVien.getText().trim();
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
				String vaiTro = cmbVaiTro.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbVaiTro.getSelectedItem().toString().trim();

				String tinhTP = cmbTinhTP.getSelectedItem().toString().trim();
				String quanHuyen = cmbQuanHuyen.getSelectedItem().toString().trim();
				String phuongXa = cmbPhuongXa.getSelectedItem().toString().trim();

				DiaChi diaChiTam = new DiaChi(tinhTP + quanHuyen + phuongXa, tinhTP, quanHuyen, phuongXa);
				String trinhDoChuyenMon = cmbTrinhDoChuyenMon.getSelectedItem().toString().trim()
						.equalsIgnoreCase("All") ? null : cmbTrinhDoChuyenMon.getSelectedItem().toString().trim();
				String trinhDoNgoaiNgu = cmbTrinhDoNgoaiNgu.getSelectedItem().toString().trim().equalsIgnoreCase("All")
						? null
						: cmbTrinhDoNgoaiNgu.getSelectedItem().toString().trim();
				try {
					return nhanVienDAO.timKiemNhanVienTheoNhieuThuocTinh(maNV, tenNV, gioiTinh, ngaySinh, ngayVaoLam,
							soDienThoai, vaiTro, diaChiTam, trinhDoChuyenMon, trinhDoNgoaiNgu);
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

		
		
		btnGuiEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnXoaRongActionPerformed(e);

			}

			private void btnXoaRongActionPerformed(ActionEvent evt) {
				String emailNhanVien = txtEmail.getText().toString();
				
				
				
				JDialog giaoDienGuiMail = new JDialog();
				
				GuiEmailChoNhanVien guiEmail = new GuiEmailChoNhanVien(emailNhanVien);
				giaoDienGuiMail.add(guiEmail);
				giaoDienGuiMail.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				giaoDienGuiMail.setSize(980, 850);
				giaoDienGuiMail.setLocationRelativeTo(null);
				giaoDienGuiMail.setVisible(true);

			}

		});

	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlQLNV = new javax.swing.JPanel();
        lblMaNhanVien = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();
        lblSoDienThoai = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        txtTenNhanVien = new javax.swing.JTextField();
        txtSoDienThoai = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lblDiaChi = new javax.swing.JLabel();
        lblGioiTinh = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblNgayVaoLam = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        cmbTinhTP = new javax.swing.JComboBox<>();
        cmbQuanHuyen = new javax.swing.JComboBox<>();
        cmbGioiTinh = new javax.swing.JComboBox<>();
        dtNgaySinh = new com.toedter.calendar.JDateChooser();
        dtNgayVaoLam = new com.toedter.calendar.JDateChooser();
        lblAnhDaiDien = new javax.swing.JLabel();
        cmbVaiTro = new javax.swing.JComboBox<>();
        scrTableNhanVien = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        btnChonHinhAnh = new javax.swing.JButton();
        cmbPhuongXa = new javax.swing.JComboBox<>();
        lblTrinhDoChuyenMon = new javax.swing.JLabel();
        cmbTrinhDoChuyenMon = new javax.swing.JComboBox<>();
        lblTrinhDoNgoaiNgu = new javax.swing.JLabel();
        cmbTrinhDoNgoaiNgu = new javax.swing.JComboBox<>();
        btnTimKiem = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        btnGuiEmail = new javax.swing.JButton();
        btnHuongDan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlQLNV.setBackground(new java.awt.Color(255, 255, 255));
        pnlQLNV.setPreferredSize(new java.awt.Dimension(1250, 475));

        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaNhanVien.setText("Mã nhân viên:");

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNhanVien.setText("Tên nhân viên:");

        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoDienThoai.setText("Số điện thoại:");

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEmail.setText("Email:");

        txtMaNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaNhanVien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenNhanVien.setBorder(null);
        txtTenNhanVien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoDienThoai.setBorder(null);
        txtSoDienThoai.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtEmail.setBorder(null);
        txtEmail.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDiaChi.setText("Địa chỉ:");

        lblGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGioiTinh.setText("Giới tính:");

        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgaySinh.setText("Ngày sinh:");

        lblNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayVaoLam.setText("Ngày vào làm:");

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblVaiTro.setText("Vai trò:");

        cmbTinhTP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTinhTP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tỉnh/Thành phố" }));

        cmbQuanHuyen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbQuanHuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quận/Huyện" }));

        cmbGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Nam", "Nữ" }));

        dtNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        dtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        dtNgayVaoLam.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayVaoLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblAnhDaiDien.setBackground(new java.awt.Color(0, 204, 153));
        lblAnhDaiDien.setForeground(new java.awt.Color(0, 255, 204));

        cmbVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbVaiTro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Quản lí", "Kế toán", "Nhân viên" }));

        scrTableNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        scrTableNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16))); // NOI18N

        tblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNhanVien.setSelectionBackground(new java.awt.Color(51, 153, 255));
        scrTableNhanVien.setViewportView(tblNhanVien);

        btnChonHinhAnh.setText("Hình ảnh");
        btnChonHinhAnh.setEnabled(false);

        cmbPhuongXa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbPhuongXa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phường/Xã" }));

        lblTrinhDoChuyenMon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTrinhDoChuyenMon.setText("Trình độ:");

        cmbTrinhDoChuyenMon.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTrinhDoChuyenMon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Đại học", "Cao đẳng", "Trung cấp" }));

        lblTrinhDoNgoaiNgu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTrinhDoNgoaiNgu.setText("Ngoại ngữ:");

        cmbTrinhDoNgoaiNgu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTrinhDoNgoaiNgu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Sơ cấp", "Trung cấp", "Cao cấp" }));

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.setPreferredSize(new java.awt.Dimension(137, 33));

        btnGuiEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGuiEmail.setText("Gửi email");
        btnGuiEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnHuongDan.setText("?");

        javax.swing.GroupLayout pnlQLNVLayout = new javax.swing.GroupLayout(pnlQLNV);
        pnlQLNV.setLayout(pnlQLNVLayout);
        pnlQLNVLayout.setHorizontalGroup(
            pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNVLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaNhanVien)
                    .addComponent(lblTenNhanVien)
                    .addComponent(lblSoDienThoai)
                    .addComponent(lblEmail)
                    .addComponent(lblTrinhDoChuyenMon)
                    .addComponent(lblDiaChi))
                .addGap(6, 6, 6)
                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlQLNVLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cmbTrinhDoChuyenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(417, 417, 417)
                        .addComponent(lblTrinhDoNgoaiNgu)
                        .addGap(31, 31, 31)
                        .addComponent(cmbTrinhDoNgoaiNgu, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLNVLayout.createSequentialGroup()
                        .addComponent(cmbTinhTP, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmbPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlQLNVLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnGuiEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGioiTinh)
                    .addComponent(lblNgaySinh)
                    .addComponent(lblNgayVaoLam)
                    .addComponent(lblVaiTro))
                .addGap(31, 31, 31)
                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQLNVLayout.createSequentialGroup()
                        .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlQLNVLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(btnChonHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(88, 88, 88))
                    .addGroup(pnlQLNVLayout.createSequentialGroup()
                        .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addComponent(scrTableNhanVien)
        );
        pnlQLNVLayout.setVerticalGroup(
            pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLNVLayout.createSequentialGroup()
                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlQLNVLayout.createSequentialGroup()
                        .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlQLNVLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(lblMaNhanVien)
                                .addGap(22, 22, 22)
                                .addComponent(lblTenNhanVien)
                                .addGap(20, 20, 20)
                                .addComponent(lblSoDienThoai)
                                .addGap(18, 18, 18)
                                .addComponent(lblEmail)
                                .addGap(24, 24, 24)
                                .addComponent(lblTrinhDoChuyenMon)
                                .addGap(18, 18, 18)
                                .addComponent(lblDiaChi))
                            .addGroup(pnlQLNVLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTrinhDoChuyenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTrinhDoNgoaiNgu)
                                    .addComponent(cmbTrinhDoNgoaiNgu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbTinhTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(11, 11, 11)
                                .addGroup(pnlQLNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnGuiEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlQLNVLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(lblGioiTinh)
                                .addGap(25, 25, 25)
                                .addComponent(lblNgaySinh)
                                .addGap(22, 22, 22)
                                .addComponent(lblNgayVaoLam)
                                .addGap(16, 16, 16)
                                .addComponent(lblVaiTro))
                            .addGroup(pnlQLNVLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(cmbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(dtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(dtNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(cmbVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLNVLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAnhDaiDien, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnChonHinhAnh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(scrTableNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1643, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlQLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonHinhAnh;
    private javax.swing.JButton btnGuiEmail;
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JComboBox<String> cmbGioiTinh;
    private javax.swing.JComboBox<String> cmbPhuongXa;
    private javax.swing.JComboBox<String> cmbQuanHuyen;
    private javax.swing.JComboBox<String> cmbTinhTP;
    private javax.swing.JComboBox<String> cmbTrinhDoChuyenMon;
    private javax.swing.JComboBox<String> cmbTrinhDoNgoaiNgu;
    private javax.swing.JComboBox<String> cmbVaiTro;
    private com.toedter.calendar.JDateChooser dtNgaySinh;
    private com.toedter.calendar.JDateChooser dtNgayVaoLam;
    private javax.swing.JLabel lblAnhDaiDien;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNgayVaoLam;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblTrinhDoChuyenMon;
    private javax.swing.JLabel lblTrinhDoNgoaiNgu;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pnlQLNV;
    private javax.swing.JScrollPane scrTableNhanVien;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenNhanVien;
    // End of variables declaration//GEN-END:variables
}
