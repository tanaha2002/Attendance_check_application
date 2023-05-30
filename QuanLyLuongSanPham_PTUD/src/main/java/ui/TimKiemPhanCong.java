/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import entity.BangChamCongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongDoanSanPham;
import tablemodels.BangPhanCongCongNhanTableModel;
import dao.CongNhanDAO;
import dao.NhanVienDAO;
import entity.CongNhan;
import dao.SanPhamDAO;
import dao.CongDoanSanPhamDAO;
import entity.SanPham;
import entity.NhanVien;
import setting.PathSetting;
import tablemodels.CongNhanTableModel;
import tablemodels.CongDoanTableModel;

/**
 *
 * @author ADMIN
 */
public class TimKiemPhanCong extends javax.swing.JPanel {

	/**
	 * Creates new form PhanCongCongDoan
	 */
	private List<CongNhan> dsCN;
	private CongNhanDAO congNhanDAO;
	private NhanVienDAO nhanVienDAO;
	private CongNhanTableModel congNhanTableModel;
	private BangPhanCongCongNhanDAO phanCongCongNhanDAO;
	private List<BangPhanCongCongNhan> dsPCCN;
	private BangPhanCongCongNhanTableModel phanCongCongNhanTableModel;
	private SanPhamDAO sanPhamDAO;
	private CongDoanSanPhamDAO congDoanSanPhamDAO;
	private List<SanPham> dsSP;
	private boolean trangThaiThem;
	private CongDoanSanPham cdDangChon;
	private SanPham spDangChon;
	private static final String[] HEADERS = { "Mã phân công", "Mã công nhân", "Tên công nhân", "Ngày phân công",
			"Số lượng phân công", "Mã công đoạn", "Mã sản phẩm", "Mã người phân công" };
	private NhanVien nhanVienLogin;
	private BangChamCongCongNhanDAO chamCongDAO;

	public TimKiemPhanCong() {
		congNhanDAO = new CongNhanDAO();
		sanPhamDAO = new SanPhamDAO();
		congDoanSanPhamDAO = new CongDoanSanPhamDAO();
		nhanVienDAO = new NhanVienDAO();
		phanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		chamCongDAO = new BangChamCongCongNhanDAO();
		trangThaiThem = false;
		initComponents();
		themSuKien();
		phimTatChoButton();
	}

	private void phimTatChoButton() {
		btnTimKiem.setMnemonic('T');
		btnXoaRong.setMnemonic('X');
		rdTimKiem1.setMnemonic('1');
		rdTimKiem2.setMnemonic('2');
		rdTimKiem3.setMnemonic('3');
	}

	public int getSoLuongCDDaPhanCong(String maCD, String maSP) {
		int sl = 0;
		dsPCCN = phanCongCongNhanDAO.layDanhSachPhanCongCN();
		for (BangPhanCongCongNhan pc : dsPCCN) {
			if (pc.getCongDoan().getMaCongDoan().equals(maCD) && pc.getSanPham().getMaSanPham().equals(maSP)) {
				sl += pc.getSoLuongPhanCong();
			}
		}
		return sl;
	}

	public int getSLConLaiCuaCDDangPhanCong() {
		return spDangChon.getSoLuongSanXuat()
				- getSoLuongCDDaPhanCong(cdDangChon.getMaCongDoan(), spDangChon.getMaSanPham());
	}

	private void updatePhanCongCongNhanTableModel(String[] headers, List<BangPhanCongCongNhan> dsPCCN) {
		phanCongCongNhanTableModel = new BangPhanCongCongNhanTableModel(headers, dsPCCN);
		tblPhanCongCongDoan.setModel(phanCongCongNhanTableModel);
		tblPhanCongCongDoan.updateUI();
	}

	private void xoaRong() {
		txtMaCongDoan.setText("");
		txtMaCongNhan.setText("");
		txtMaPhanCong.setText("");
		dtNgayPhanCong.setDate(null);
		dtNgayPhanCong1.setDate(null);
		txtSoLuongCDHien.setText("");
		txtMaSanPham.setText("");
	}

	private void themSuKien() {
		dsPCCN = phanCongCongNhanDAO.layDanhSachPhanCongCN();
		updatePhanCongCongNhanTableModel(HEADERS, dsPCCN);
		tblPhanCongCongDoan.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String item = (String) tblPhanCongCongDoan.getModel().getValueAt(tblPhanCongCongDoan.getSelectedRow(),
						0);
				if (item != null) {
					BangPhanCongCongNhan pccn = null;
					try {
						if (!trangThaiThem) {
							pccn = phanCongCongNhanDAO.timPhanCongCongNhan(item);
							int slPC = pccn.getSoLuongPhanCong();
							fillThongTinPhanCong(pccn);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

			private void fillThongTinPhanCong(BangPhanCongCongNhan pccn) throws Exception {
				// TODO Auto-generated method stub
				txtMaPhanCong.setText(pccn.getMaPhanCong());
				txtMaCongNhan.setText(pccn.getCongNhan().getMaCongNhan());
				txtMaCongDoan.setText(pccn.getCongDoan().getMaCongDoan());
				txtSoLuongCDHien.setText(String.valueOf(pccn.getSoLuongPhanCong()));
				dtNgayPhanCong.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(pccn.getNgayPhanCong().toString()));
				txtMaSanPham.setText(pccn.getSanPham().getMaSanPham());
			}

		});
		// check radio button tìm kiếm 1 theo ngày tháng
		rdTimKiem1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lblDenNgayPhanCong.setEnabled(false);

					dtNgayPhanCong1.setEnabled(false);
					dtNgayPhanCong1.setDate(null);
					System.out.println("Ngày");
				}

			}
		});
		// tim kiem theo tháng
		rdTimKiem2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lblDenNgayPhanCong.setEnabled(false);
					dtNgayPhanCong1.setEnabled(false);
					dtNgayPhanCong1.setDate(null);
					System.out.println("Tháng");
				}

			}
		});
		// tìm kiếm theo khoảng thời gian
		rdTimKiem3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					lblDenNgayPhanCong.setEnabled(true);
					dtNgayPhanCong1.setEnabled(true);
					for (Component c : dtNgayPhanCong1.getComponents()) {
						c.setBackground(Color.WHITE);
					}

				}

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
					int kieuTimKiem = 1;
					if (rdTimKiem1.isSelected()) {
						kieuTimKiem = 1;
					} else if (rdTimKiem2.isSelected()) {
						kieuTimKiem = 2;
					} else if (rdTimKiem3.isSelected()) {
						kieuTimKiem = 3;

					}
					if (dtNgayPhanCong1.getDate() == null && kieuTimKiem == 3) {
						JOptionPane.showMessageDialog(null, "Chưa chọn khoảng thời gian");
					}
					List<BangPhanCongCongNhan> dsTimKiem = new ArrayList<>();

					dsTimKiem = layDanhSachPhanCongTimKiem(kieuTimKiem);
					if (dsTimKiem != null && dsTimKiem.size() != 0) {
						updatePhanCongCongNhanTableModel(HEADERS, dsTimKiem);
					} else {
						JOptionPane.showMessageDialog(null, "Không tìm thấy phân công công nhân nào");

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

			private List<BangPhanCongCongNhan> layDanhSachPhanCongTimKiem(int kieuTimKiem) throws Exception {
				String maPC = txtMaPhanCong.getText().isEmpty() ? null : txtMaPhanCong.getText().trim();
				String maSP = txtMaSanPham.getText().isEmpty() ? null : txtMaSanPham.getText().trim();
				String maCD = txtMaCongDoan.getText().isEmpty() ? null : txtMaCongDoan.getText().trim();

				String maCN = txtMaCongNhan.getText().isEmpty() ? null : txtMaCongNhan.getText().trim();
				String soLuongLam = txtSoLuongCDHien.getText().isEmpty() ? null : txtSoLuongCDHien.getText().trim();
				Date ngayPhanCong = null;
				Date denNgay = null;
				try {
					ngayPhanCong = dtNgayPhanCong.getDate();
					denNgay = dtNgayPhanCong1.getDate();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				return phanCongCongNhanDAO.timKiemPhanCongTheoNhieuThuocTinh(maPC, maCN, maSP, null, maCD, null,
						ngayPhanCong, denNgay, soLuongLam, kieuTimKiem);

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupTimKiem = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblMaCongDoan = new javax.swing.JLabel();
        lblMaPhanCOng = new javax.swing.JLabel();
        lblMaCongNhan = new javax.swing.JLabel();
        txtMaPhanCong = new javax.swing.JTextField();
        txtMaCongNhan = new javax.swing.JTextField();
        txtMaCongDoan = new javax.swing.JTextField();
        lblNgayPhanCong = new javax.swing.JLabel();
        dtNgayPhanCong = new com.toedter.calendar.JDateChooser();
        lblSoLuong2 = new javax.swing.JLabel();
        txtSoLuongCDHien = new javax.swing.JTextField();
        lblMaSP = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnXoaRong = new javax.swing.JButton();
        dtNgayPhanCong1 = new com.toedter.calendar.JDateChooser();
        lblDenNgayPhanCong = new javax.swing.JLabel();
        lblKieuTimKiem = new javax.swing.JLabel();
        rdTimKiem1 = new javax.swing.JRadioButton();
        rdTimKiem2 = new javax.swing.JRadioButton();
        rdTimKiem3 = new javax.swing.JRadioButton();
        lblChiTietKieuTimKiem3 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem2 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem1 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem4 = new javax.swing.JLabel();
        lblChiTietKieuTimKiem = new javax.swing.JLabel();
        scrTablePhanCongCongDoan = new javax.swing.JScrollPane();
        tblPhanCongCongDoan = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1250, 475));

        lblMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongDoan.setText("Mã công đoạn:");

        lblMaPhanCOng.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaPhanCOng.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaPhanCOng.setText("Mã phân công:");

        lblMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongNhan.setText("Mã công nhân:");

        txtMaPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaPhanCong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaPhanCong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongNhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblNgayPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayPhanCong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNgayPhanCong.setText("Ngày phân công:");

        dtNgayPhanCong.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblSoLuong2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuong2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoLuong2.setText("Số lượng:");

        txtSoLuongCDHien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongCDHien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuongCDHien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaSP.setText("Mã sản phẩm:");

        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.setPreferredSize(new java.awt.Dimension(137, 33));

        dtNgayPhanCong1.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayPhanCong1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblDenNgayPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDenNgayPhanCong.setText("Đến ngày:");
        lblDenNgayPhanCong.setEnabled(false);

        lblKieuTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblKieuTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblKieuTimKiem.setText("Kiểu tìm kiếm");

        groupTimKiem.add(rdTimKiem1);
        rdTimKiem1.setSelected(true);
        rdTimKiem1.setText("(1)");

        groupTimKiem.add(rdTimKiem2);
        rdTimKiem2.setText("(2)");

        groupTimKiem.add(rdTimKiem3);
        rdTimKiem3.setText("(3)");

        lblChiTietKieuTimKiem3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem3.setText("(3) Tìm kiếm theo khoảng thời gian");

        lblChiTietKieuTimKiem2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem2.setText("(2) Tìm kiếm theo tháng");

        lblChiTietKieuTimKiem1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem1.setText("(1) Tìm kiếm theo ngày");

        lblChiTietKieuTimKiem4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblChiTietKieuTimKiem4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem4.setText("Chú thích (?)");

        lblChiTietKieuTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblChiTietKieuTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChiTietKieuTimKiem.setText("Hướng dẫn sử dụng");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(lblMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txtMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(lblMaCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(txtMaCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(lblMaPhanCOng, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(txtMaPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(lblSoLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(20, 20, 20)
                                        .addComponent(txtSoLuongCDHien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblDenNgayPhanCong)
                                            .addComponent(lblNgayPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(dtNgayPhanCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(dtNgayPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblKieuTimKiem)
                        .addGap(23, 23, 23)
                        .addComponent(rdTimKiem1)
                        .addGap(13, 13, 13)
                        .addComponent(rdTimKiem2)
                        .addGap(13, 13, 13)
                        .addComponent(rdTimKiem3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 567, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblChiTietKieuTimKiem)
                    .addComponent(lblChiTietKieuTimKiem4)
                    .addComponent(lblChiTietKieuTimKiem1)
                    .addComponent(lblChiTietKieuTimKiem2)
                    .addComponent(lblChiTietKieuTimKiem3))
                .addGap(131, 131, 131))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaPhanCOng, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgayPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtNgayPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDenNgayPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtNgayPhanCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSoLuong2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuongCDHien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaCongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoaRong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblKieuTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdTimKiem1)
                            .addComponent(rdTimKiem2)
                            .addComponent(rdTimKiem3)))))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblChiTietKieuTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lblChiTietKieuTimKiem4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblChiTietKieuTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblChiTietKieuTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblChiTietKieuTimKiem3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        scrTablePhanCongCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        scrTablePhanCongCongDoan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phân công", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        tblPhanCongCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblPhanCongCongDoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã phân công", "Mã công nhân", "Tên công nhân", "Mã công đoạn", "Tên công đoạn", "Mã sản phẩm", "Tên sản phẩm", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhanCongCongDoan.setSelectionBackground(new java.awt.Color(232, 57, 95));
        scrTablePhanCongCongDoan.setViewportView(tblPhanCongCongDoan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1817, Short.MAX_VALUE)
            .addComponent(scrTablePhanCongCongDoan)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrTablePhanCongCongDoan, javax.swing.GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1817, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 857, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void tblCongNhanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblDanhSachSanPhamMouseClicked

	}// GEN-LAST:event_tblDanhSachSanPhamMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaRong;
    private com.toedter.calendar.JDateChooser dtNgayPhanCong;
    private com.toedter.calendar.JDateChooser dtNgayPhanCong1;
    private javax.swing.ButtonGroup groupTimKiem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblChiTietKieuTimKiem;
    private javax.swing.JLabel lblChiTietKieuTimKiem1;
    private javax.swing.JLabel lblChiTietKieuTimKiem2;
    private javax.swing.JLabel lblChiTietKieuTimKiem3;
    private javax.swing.JLabel lblChiTietKieuTimKiem4;
    private javax.swing.JLabel lblDenNgayPhanCong;
    private javax.swing.JLabel lblKieuTimKiem;
    private javax.swing.JLabel lblMaCongDoan;
    private javax.swing.JLabel lblMaCongNhan;
    private javax.swing.JLabel lblMaPhanCOng;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblNgayPhanCong;
    private javax.swing.JLabel lblSoLuong2;
    private javax.swing.JRadioButton rdTimKiem1;
    private javax.swing.JRadioButton rdTimKiem2;
    private javax.swing.JRadioButton rdTimKiem3;
    private javax.swing.JScrollPane scrTablePhanCongCongDoan;
    private javax.swing.JTable tblPhanCongCongDoan;
    private javax.swing.JTextField txtMaCongDoan;
    private javax.swing.JTextField txtMaCongNhan;
    private javax.swing.JTextField txtMaPhanCong;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuongCDHien;
    // End of variables declaration//GEN-END:variables
}
