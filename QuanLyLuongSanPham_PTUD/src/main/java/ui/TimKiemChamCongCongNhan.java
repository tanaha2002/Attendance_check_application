/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.border.MatteBorder;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import dao.CongNhanDAO;
import entity.BangChamCongCongNhan;
import entity.BangChamCongNhanVien;
import entity.BangPhanCongCongNhan;
import entity.NhanVien;
import tablemodels.BangChamCongCNTableModel;

/**
 *
 * @author Hoang Truong
 */
public class TimKiemChamCongCongNhan extends javax.swing.JPanel {

	private PopupMenu QuanLyCN;
	private List<BangChamCongCongNhan> dsCCCN;
	private BangChamCongCongNhanDAO chamCongCongNhanDAO;
	private BangChamCongCNTableModel chamCongCNTableModel;
	private BangPhanCongCongNhanDAO phanCongCongNhanDAO;
	List<BangPhanCongCongNhan> dsPhanCong = new ArrayList<>();
	private static final String[] HEADERS = { "Mã chấm công", "Mã công nhân", "Tên công nhân", "Tổ", "Ngày chấm công",
			"Ca làm", "Trạng thái", "Mã công đoạn", "Tên Công đoạn", "Số lượng làm được", "Mã sản phẩm", "Tên Sản phẩm",
			"Mã Người chấm công", "Tên người chấm công" };

	/**
	 * Creates new form ChamCongCongNhan
	 */
	public TimKiemChamCongCongNhan() {
		chamCongCongNhanDAO = new BangChamCongCongNhanDAO();
		phanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		initComponents();
		phimTatChoButton();
		themSuKien();
	}

	private void phimTatChoButton() {
		btnTimKiem.setMnemonic('T');
		btnXoaRong.setMnemonic('X');
		rdTimKiem1.setMnemonic('1');
		rdTimKiem2.setMnemonic('2');
		rdTimKiem3.setMnemonic('3');
	}

	private void updateChamCongCNTableModel(List<BangChamCongCongNhan> dsCCCN, String[] headers) {
		chamCongCNTableModel = new BangChamCongCNTableModel(dsCCCN, headers);
		tblChamCong.setModel(chamCongCNTableModel);
		tblChamCong.updateUI();
	}

	private void fillThongTinChamCong(BangChamCongCongNhan thongTinChamCongCN) {
		txtMaCongNhan.setText(thongTinChamCongCN.getPhanCong().getCongNhan().getMaCongNhan());
		txtTenCongNhan.setText(thongTinChamCongCN.getPhanCong().getCongNhan().getTenCongNhan());
		txtTo.setText(thongTinChamCongCN.getPhanCong().getCongNhan().getToNhom().getTenToNhom());
		dtNgayChamCong.setDate(thongTinChamCongCN.getNgayChamCong());
		cmbCaLam.setSelectedItem(thongTinChamCongCN.getCaLam());
		cmbTinhTrang.setSelectedItem(thongTinChamCongCN.getTrangthai());
		txtMaCongDoan2.setText(thongTinChamCongCN.getPhanCong().getCongDoan().getMaCongDoan());
		txtTenCongDoan2.setText(thongTinChamCongCN.getPhanCong().getCongDoan().getTenCongDoan());
		txtMaSanPham2.setText(thongTinChamCongCN.getPhanCong().getCongDoan().getSanPham().getMaSanPham());
		txtTenSanPham2.setText(thongTinChamCongCN.getPhanCong().getCongDoan().getSanPham().getTenSanPham());
		txtSoLuongLam.setText(String.format("%d", thongTinChamCongCN.getSoLuongLam()));
		txtMaNguoiChamCong.setText(thongTinChamCongCN.getNguoiChamCong().getMaNhanVien());
		txtTenNguoiChamCong.setText(thongTinChamCongCN.getNguoiChamCong().getTenNhanVien());
	}

	private void xoaRong() {
		txtMaCongNhan.setText("");
		txtTenCongNhan.setText("");
		txtTo.setText("");
		dtNgayChamCong.setDate(null);
		dtNgayChamCong1.setDate(null);
		cmbCaLam.setSelectedItem("");
		txtMaCongDoan2.setText("");
		txtTenCongDoan2.setText("");
		txtMaSanPham2.setText("");
		txtTenSanPham2.setText("");
		txtSoLuongLam.setText("");
		txtMaNguoiChamCong.setText("");
		txtTenNguoiChamCong.setText("");
		cmbCaLam.setSelectedItem("All");
		cmbTinhTrang.setSelectedItem("All");
	}

	private void themSuKien() {
//		dtNgayChamCong.setEnabled(true);
		dtNgayChamCong1.setEnabled(false);
		dsCCCN = chamCongCongNhanDAO.layDanhSachChamCongCN();
		updateChamCongCNTableModel(dsCCCN, HEADERS);

		tblChamCong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String maChamCong = (String) tblChamCong.getModel().getValueAt(tblChamCong.getSelectedRow(), 0);
				if (maChamCong != null) {
					BangChamCongCongNhan congNhanChamCong = null;
					try {
						congNhanChamCong = chamCongCongNhanDAO.timThongTinChamCong(maChamCong);
						fillThongTinChamCong(congNhanChamCong);
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
		});
		// kiem tra ngay chọn có phải là chủ nhật không
		dtNgayChamCong.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				if (dtNgayChamCong.getDate() != null && dtNgayChamCong.getDate().getDay() == 0) {
					cmbCaLam.setModel(
							new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Sáng CN", "Chiều CN" }));
					cmbCaLam.repaint();
				} else {
					cmbCaLam.setModel(
							new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Sáng", "Chiều", "Tối" }));
					cmbCaLam.repaint();
				}

			}
		});
		// check radio button tìm kiếm 1 theo ngày tháng
		rdTimKiem1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lblDenNgayChamCong.setEnabled(false);

					dtNgayChamCong1.setEnabled(false);
					dtNgayChamCong1.setDate(null);
					System.out.println("Ngày");
				}

			}
		});
		// tim kiem theo tháng
		rdTimKiem2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lblDenNgayChamCong.setEnabled(false);
					dtNgayChamCong1.setEnabled(false);
					dtNgayChamCong1.setDate(null);
					System.out.println("Tháng");
				}

			}
		});
		// tìm kiếm theo khoảng thời gian
		rdTimKiem3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lblDenNgayChamCong.setEnabled(true);
					dtNgayChamCong1.setEnabled(true);
					for (Component c : dtNgayChamCong1.getComponents()) {
						c.setBackground(Color.WHITE);
					}

				}

			}
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				btnTimKiemActionPerformed(e);

			}

			private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {

				try {
					int kieuTimKiem = 1;
					if (rdTimKiem1.isSelected()) {
						kieuTimKiem = 1;
					} else if (rdTimKiem2.isSelected()) {
						kieuTimKiem = 2;
					} else if (rdTimKiem3.isSelected()) {
						kieuTimKiem = 3;

					}
					if (dtNgayChamCong1.getDate() == null && kieuTimKiem == 3) {
						JOptionPane.showMessageDialog(null, "Chưa chọn khoảng thời gian");
					}
					List<BangChamCongCongNhan> dsTimKiem = new ArrayList<>();

					dsTimKiem = layDanhSachChamCongTimKiem(kieuTimKiem);
					if (dsTimKiem != null && dsTimKiem.size() != 0) {
						updateChamCongCNTableModel(dsTimKiem, HEADERS);
					} else {

						JOptionPane.showMessageDialog(null, "Không tìm thấy chấm công công nhân nào");

					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			private List<BangChamCongCongNhan> layDanhSachChamCongTimKiem(int kieuTimKiem) {
				String maCD = txtMaCongDoan2.getText().isEmpty() ? null : txtMaCongDoan2.getText().trim();
				String tenCD = txtTenCongDoan2.getText().trim().isEmpty() ? null : txtTenCongDoan2.getText().trim();
				String maSP = txtMaSanPham2.getText().isEmpty() ? null : txtMaSanPham2.getText().trim();
				String tenSP = txtTenSanPham2.getText().isEmpty() ? null : txtTenSanPham2.getText().trim();
				String maCN = txtMaCongNhan.getText().isEmpty() ? null : txtMaCongNhan.getText().trim();
				String tenCN = txtTenCongNhan.getText().isEmpty() ? null : txtTenCongNhan.getText().trim();
				String to = txtTo.getText().isEmpty() ? null : txtTo.getText().trim();
				String maNguoiChamCong = txtMaNguoiChamCong.getText().isEmpty() ? null
						: txtMaNguoiChamCong.getText().trim();
				String tenNguoiChamCong = txtTenNguoiChamCong.getText().isEmpty() ? null
						: txtTenNguoiChamCong.getText().trim();
				Date ngayChamCong = null;
				Date denNgay = null;
				String soLuongLam = txtSoLuongLam.getText().isEmpty() ? null : txtSoLuongLam.getText().trim();
				String caLam = cmbCaLam.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbCaLam.getSelectedItem().toString().trim();
				String trangThai = cmbTinhTrang.getSelectedItem().toString().trim().equalsIgnoreCase("All") ? null
						: cmbTinhTrang.getSelectedItem().toString().trim();
				try {
					ngayChamCong = dtNgayChamCong.getDate();
					denNgay = dtNgayChamCong1.getDate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					return chamCongCongNhanDAO.timKiemChamCongTheoNhieuThuocTinh(maCN, tenCN, to, ngayChamCong, denNgay,
							caLam, trangThai, maCD, tenCD, maSP, tenSP, soLuongLam, maNguoiChamCong, tenNguoiChamCong,
							kieuTimKiem);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			};
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

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupTimKiem = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        lblNgayChamCong = new javax.swing.JLabel();
        lblSoLuongSanPham = new javax.swing.JLabel();
        lblMaCongNhan = new javax.swing.JLabel();
        lblTenCongDoanRight = new javax.swing.JLabel();
        lblTenSanPhamRight = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        lblToNhom = new javax.swing.JLabel();
        lblMaCongNhan1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTenSanPhamRight1 = new javax.swing.JLabel();
        lblTenSanPhamRight2 = new javax.swing.JLabel();
        cmbTinhTrang = new javax.swing.JComboBox<>();
        cmbCaLam = new javax.swing.JComboBox<>();
        txtSoLuongLam = new javax.swing.JTextField();
        txtTenCongNhan = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        txtMaCongNhan = new javax.swing.JTextField();
        txtMaCongDoan2 = new javax.swing.JTextField();
        txtTenCongDoan2 = new javax.swing.JTextField();
        txtMaSanPham2 = new javax.swing.JTextField();
        txtTenSanPham2 = new javax.swing.JTextField();
        dtNgayChamCong = new com.toedter.calendar.JDateChooser();
        lblNguoiChamCong = new javax.swing.JLabel();
        txtMaNguoiChamCong = new javax.swing.JTextField();
        lblKieuTimKiem = new javax.swing.JLabel();
        txtTenNguoiChamCong = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        scrChamCong = new javax.swing.JScrollPane();
        tblChamCong = new javax.swing.JTable();
        lblDenNgayChamCong = new javax.swing.JLabel();
        dtNgayChamCong1 = new com.toedter.calendar.JDateChooser();
        lblTenNguoiChamCong1 = new javax.swing.JLabel();
        rdTimKiem3 = new javax.swing.JRadioButton();
        rdTimKiem1 = new javax.swing.JRadioButton();
        rdTimKiem2 = new javax.swing.JRadioButton();
        lblChiTietKieuTimKiem1 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem = new javax.swing.JLabel();
        lblChiTietKieuTimKiem2 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem3 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel5.setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));

        lblNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayChamCong.setText("Ngày chấm công:");

        lblSoLuongSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongSanPham.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoLuongSanPham.setText("Số lượng làm:");

        lblMaCongNhan.setBackground(new java.awt.Color(255, 255, 255));
        lblMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongNhan.setText("Tên công nhân:");

        lblTenCongDoanRight.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongDoanRight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenCongDoanRight.setText("Tên sản phẩm:");

        lblTenSanPhamRight.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPhamRight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPhamRight.setText("Mã công đoạn:");

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTrangThai.setText("Tình trạng:");

        lblToNhom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblToNhom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblToNhom.setText("Tổ:");

        lblMaCongNhan1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongNhan1.setText("Mã công nhân:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Ca làm:");

        lblTenSanPhamRight1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPhamRight1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPhamRight1.setText("Tên công đoạn:");

        lblTenSanPhamRight2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPhamRight2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPhamRight2.setText("Mã sản phẩm:");

        cmbTinhTrang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Có mặt", "Vắng mặt" }));

        cmbCaLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Sáng", "Chiều", "Tối" }));
        cmbCaLam.setToolTipText("");

        txtSoLuongLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongLam.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTo.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtMaCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtMaCongDoan2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenCongDoan2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongDoan2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtMaSanPham2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenSanPham2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenSanPham2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        dtNgayChamCong.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNguoiChamCong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNguoiChamCong.setText("Mã người chấm công:");

        txtMaNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaNguoiChamCong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblKieuTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKieuTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKieuTimKiem.setText("Kiểu tìm kiếm");

        txtTenNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenNguoiChamCong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.setPreferredSize(new java.awt.Dimension(137, 33));

        scrChamCong.setBackground(new java.awt.Color(255, 255, 255));
        scrChamCong.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bảng chấm công", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        tblChamCong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblChamCong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã chấm công", "Mã công nhân", "Họ và tên", "Tổ", "Ngày chấm công", "Ca làm", "Tình trạng", "Mã công đoạn", "Tên Công đoạn", "Mã sản phẩm", "Tên Sản phẩm", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChamCong.setSelectionBackground(new java.awt.Color(51, 153, 255));
        scrChamCong.setViewportView(tblChamCong);

        lblDenNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDenNgayChamCong.setText("Đến ngày:");
        lblDenNgayChamCong.setEnabled(false);

        dtNgayChamCong1.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayChamCong1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblTenNguoiChamCong1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNguoiChamCong1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenNguoiChamCong1.setText("Tên người chấm công:");

        groupTimKiem.add(rdTimKiem3);
        rdTimKiem3.setText("(3)");

        groupTimKiem.add(rdTimKiem1);
        rdTimKiem1.setSelected(true);
        rdTimKiem1.setText("(1)");

        groupTimKiem.add(rdTimKiem2);
        rdTimKiem2.setText("(2)");

        lblChiTietKieuTimKiem1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem1.setText("(1) Tìm kiếm theo ngày");

        lblChiTietKieuTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblChiTietKieuTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem.setText("Hướng dẫn sử dụng");

        lblChiTietKieuTimKiem2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem2.setText("(2) Tìm kiếm theo tháng");

        lblChiTietKieuTimKiem3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem3.setText("(3) Tìm kiếm theo khoảng thời gian");

        lblChiTietKieuTimKiem4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblChiTietKieuTimKiem4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem4.setText("Chú thích (?)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblMaCongNhan1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(lblTenSanPhamRight, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(txtMaCongDoan2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(370, 370, 370)
                        .addComponent(lblChiTietKieuTimKiem))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtTenCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(lblTenSanPhamRight1)
                        .addGap(77, 77, 77)
                        .addComponent(txtTenCongDoan2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lblToNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(lblTenSanPhamRight2)
                        .addGap(88, 88, 88)
                        .addComponent(txtMaSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(lblNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(dtNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(110, 110, 110)
                        .addComponent(lblTenCongDoanRight)
                        .addGap(85, 85, 85)
                        .addComponent(txtTenSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDenNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtNgayChamCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSoLuongSanPham)
                                    .addComponent(lblNguoiChamCong)
                                    .addComponent(lblTenNguoiChamCong1))))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuongLam, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNguoiChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenNguoiChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(lblKieuTimKiem)
                                .addGap(23, 23, 23)
                                .addComponent(rdTimKiem1)
                                .addGap(13, 13, 13)
                                .addComponent(rdTimKiem2)))
                        .addGap(10, 10, 10)
                        .addComponent(rdTimKiem3)
                        .addGap(313, 313, 313)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblChiTietKieuTimKiem4)
                            .addComponent(lblChiTietKieuTimKiem1)
                            .addComponent(lblChiTietKieuTimKiem2)
                            .addComponent(lblChiTietKieuTimKiem3))))
                .addContainerGap(464, Short.MAX_VALUE))
            .addComponent(scrChamCong)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblMaCongNhan1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblTenSanPhamRight, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtMaCongDoan2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblChiTietKieuTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtTenCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTenSanPhamRight1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenCongDoan2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblToNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtTo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTenSanPhamRight2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(dtNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTenCongDoanRight, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenSanPham2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblDenNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(lblTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(dtNgayChamCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cmbCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cmbTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblSoLuongSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblNguoiChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblTenNguoiChamCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtSoLuongLam, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtMaNguoiChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtTenNguoiChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblKieuTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdTimKiem1)
                                    .addComponent(rdTimKiem2)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(rdTimKiem3))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblChiTietKieuTimKiem4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblChiTietKieuTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblChiTietKieuTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblChiTietKieuTimKiem3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addComponent(scrChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 2050, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
		// // //

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JComboBox<String> cmbCaLam;
    private javax.swing.JComboBox<String> cmbTinhTrang;
    private com.toedter.calendar.JDateChooser dtNgayChamCong;
    private com.toedter.calendar.JDateChooser dtNgayChamCong1;
    private javax.swing.ButtonGroup groupTimKiem;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblChiTietKieuTimKiem;
    private javax.swing.JLabel lblChiTietKieuTimKiem1;
    private javax.swing.JLabel lblChiTietKieuTimKiem2;
    private javax.swing.JLabel lblChiTietKieuTimKiem3;
    private javax.swing.JLabel lblChiTietKieuTimKiem4;
    private javax.swing.JLabel lblDenNgayChamCong;
    private javax.swing.JLabel lblKieuTimKiem;
    private javax.swing.JLabel lblMaCongNhan;
    private javax.swing.JLabel lblMaCongNhan1;
    private javax.swing.JLabel lblNgayChamCong;
    private javax.swing.JLabel lblNguoiChamCong;
    private javax.swing.JLabel lblSoLuongSanPham;
    private javax.swing.JLabel lblTenCongDoanRight;
    private javax.swing.JLabel lblTenNguoiChamCong1;
    private javax.swing.JLabel lblTenSanPhamRight;
    private javax.swing.JLabel lblTenSanPhamRight1;
    private javax.swing.JLabel lblTenSanPhamRight2;
    private javax.swing.JLabel lblToNhom;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JRadioButton rdTimKiem1;
    private javax.swing.JRadioButton rdTimKiem2;
    private javax.swing.JRadioButton rdTimKiem3;
    private javax.swing.JScrollPane scrChamCong;
    private javax.swing.JTable tblChamCong;
    private javax.swing.JTextField txtMaCongDoan2;
    private javax.swing.JTextField txtMaCongNhan;
    private javax.swing.JTextField txtMaNguoiChamCong;
    private javax.swing.JTextField txtMaSanPham2;
    private javax.swing.JTextField txtSoLuongLam;
    private javax.swing.JTextField txtTenCongDoan2;
    private javax.swing.JTextField txtTenCongNhan;
    private javax.swing.JTextField txtTenNguoiChamCong;
    private javax.swing.JTextField txtTenSanPham2;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
