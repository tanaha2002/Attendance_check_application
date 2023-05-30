/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
public class PhanCongCongDoan extends javax.swing.JPanel {

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
	private static final String[] HEADERSCN = { "Mã công nhân", "Tên công nhân" };
	private static final String[] HEADERS = { "Mã phân công", "Mã công nhân", "Tên công nhân", "Ngày phân công",
			"Số lượng phân công", "Mã công đoạn", "Mã sản phẩm", "Mã người phân công" };
	private NhanVien nhanVienLogin;
	private BangChamCongCongNhanDAO chamCongDAO;

	public PhanCongCongDoan(CongDoanSanPham cdDangChon, SanPham spDangChon, NhanVien nv) {
		this.cdDangChon = cdDangChon;
		this.spDangChon = spDangChon;
		this.nhanVienLogin = nv;
		congNhanDAO = new CongNhanDAO();
		sanPhamDAO = new SanPhamDAO();
		congDoanSanPhamDAO = new CongDoanSanPhamDAO();
		nhanVienDAO = new NhanVienDAO();
		phanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		chamCongDAO = new BangChamCongCongNhanDAO();
		trangThaiThem = false;
		initComponents();
		listenerInput();
		themSuKien();
	}
	
	private void listenerInput() {
		txtSoLuong.addKeyListener(new SoKeyListener());
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
		return cdDangChon.getSoLuong()
				- getSoLuongCDDaPhanCong(cdDangChon.getMaCongDoan(), spDangChon.getMaSanPham());
	}

	private void updateCongNhanTableModel(String[] headers, List<CongNhan> dsCN) {
		congNhanTableModel = new CongNhanTableModel(headers, dsCN);
		tblDanhSachCongNhan.setModel(congNhanTableModel);
		tblDanhSachCongNhan.updateUI();
	}

	private void updatePhanCongCongNhanTableModel(String[] headers, List<BangPhanCongCongNhan> dsPCCN) {
		phanCongCongNhanTableModel = new BangPhanCongCongNhanTableModel(headers, dsPCCN);
		tblPhanCongCongDoan.setModel(phanCongCongNhanTableModel);
		tblPhanCongCongDoan.updateUI();
	}

	private boolean kiemTraDuLieuNhap() {
		boolean kt = true;
		int slConLaiCuaCongDoanDangPhanCong = getSLConLaiCuaCDDangPhanCong();
		if (this.txtSoLuong.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Không để trống số lượng!!");
			txtSoLuong.requestFocus();
			kt = false;
		} else if (Integer.parseInt(this.txtSoLuong.getText().trim()) <=0) {
			JOptionPane.showMessageDialog(null,"Nhập số lớn hơn 0");
			txtSoLuong.requestFocus();
			kt = false;
		} else if (slConLaiCuaCongDoanDangPhanCong == 0) {
			txtSoLuong.setText("0");
			JOptionPane.showMessageDialog(null, "Công đoạn này đã được phân công hết!!");
			kt = false;
		} else if (Integer.parseInt(this.txtSoLuong.getText().trim()) > slConLaiCuaCongDoanDangPhanCong) {
			JOptionPane.showMessageDialog(null,
					"Công đoạn chỉ còn lại " + slConLaiCuaCongDoanDangPhanCong + " chưa được phân công");
			txtSoLuong.requestFocus();
			kt = false;
		}
		return kt;
	}

	private void themSuKien() {
		txtSoLuongConLai.setText(String.valueOf(getSLConLaiCuaCDDangPhanCong()));
		dsCN = congNhanDAO.layDanhSachCongNhanChuaDuocPhanCong();
		updateCongNhanTableModel(HEADERSCN, dsCN);
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

		btnPhanCong.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRowCN = tblDanhSachCongNhan.getSelectedRow();
					if (selectedRowCN == -1) {
						JOptionPane.showMessageDialog(null, "Chưa chọn công nhân!");
					} else if (kiemTraDuLieuNhap()) {
						String maPhanCong = getNewMaPhanCong();
						String maCN = (String) tblDanhSachCongNhan.getModel()
								.getValueAt(tblDanhSachCongNhan.getSelectedRow(), 0);
						int slPhanCong = Integer.parseInt(txtSoLuong.getText().trim());
						Date ngayPhanCong = new Date();
						BangPhanCongCongNhan pcCN = new BangPhanCongCongNhan(maPhanCong, ngayPhanCong,
								slPhanCong, cdDangChon, spDangChon, congNhanDAO.timCongNhanBangMaCongNhan(maCN),
								nhanVienLogin);

						int luaChon = JOptionPane.showConfirmDialog(null,
								"Bạn có chắc muốn phân công đoạn cho công nhân này?", "Phân công",
								JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
						if (luaChon == JOptionPane.YES_OPTION) {
							boolean ketQua = phanCongCongNhanDAO.themPhanCong(pcCN);
							if (ketQua) {
								dsCN = congNhanDAO.layDanhSachCongNhanChuaDuocPhanCong();
								updateCongNhanTableModel(HEADERSCN, dsCN);
								dsPCCN = phanCongCongNhanDAO.layDanhSachPhanCongCN();
								updatePhanCongCongNhanTableModel(HEADERS, dsPCCN);
								txtSoLuongConLai.setText(String.valueOf(getSLConLaiCuaCDDangPhanCong()));
								JOptionPane.showMessageDialog(null, "Phân công thành công");

							}

						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			private String getNewMaPhanCong() throws Exception {
				String maPhanCong = phanCongCongNhanDAO.getMaPhanCongCaoNhat();
				int maPhanCongC = 1;
				if (maPhanCong != null)
					maPhanCongC = Integer.parseInt(maPhanCong.replaceAll("\\D+", "")) + 1;
				return String.format("PCCN%04d", maPhanCongC);
			}
		});

		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblPhanCongCongDoan.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Chọn phân công cần xóa");
					return;
				}
				try {
					String maCNCham = (String) tblPhanCongCongDoan.getModel()
							.getValueAt(tblPhanCongCongDoan.getSelectedRow(), 1);
					String maXoa = (String) tblPhanCongCongDoan.getModel()
							.getValueAt(tblPhanCongCongDoan.getSelectedRow(), 0);

					if (!chamCongDAO.checkMaPhanCongInBangChamCong(maXoa)) {
						JOptionPane.showMessageDialog(null,
								"Không thể xóa phân công vì đã tồn tại trong bảng chấm công.");
						return;
					}
					List<BangChamCongCongNhan> dsChamCong = chamCongDAO.layDanhSachChamCongCN();

					int luaChon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không", "Xóa",
							JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
					if (luaChon == JOptionPane.YES_OPTION) {

						boolean ketQua = phanCongCongNhanDAO.xoaPhanCong(maXoa);
						if (ketQua) {
							dsPCCN = phanCongCongNhanDAO.layDanhSachPhanCongCN();
							updatePhanCongCongNhanTableModel(HEADERS, dsPCCN);
							JOptionPane.showMessageDialog(null, "Xóa thành công");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")

	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        scrTableCongNhan = new javax.swing.JScrollPane();
        tblDanhSachCongNhan = new javax.swing.JTable();
        lblMaCongDoan = new javax.swing.JLabel();
        lblMaPhanCOng = new javax.swing.JLabel();
        lblMaCongNhan = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtMaPhanCong = new javax.swing.JTextField();
        txtMaCongNhan = new javax.swing.JTextField();
        txtMaCongDoan = new javax.swing.JTextField();
        pnlChucNangTo = new javax.swing.JPanel();
        pnlChucNang = new javax.swing.JPanel();
        btnPhanCong = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        lblNgayPhanCong = new javax.swing.JLabel();
        dtNgayPhanCong = new com.toedter.calendar.JDateChooser();
        lblSoLuong2 = new javax.swing.JLabel();
        txtSoLuongCDHien = new javax.swing.JTextField();
        lblMaSP = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSoLuongConLai = new javax.swing.JTextField();
        scrTablePhanCongCongDoan = new javax.swing.JScrollPane();
        tblPhanCongCongDoan = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(1250, 475));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrTableCongNhan.setBackground(new java.awt.Color(255, 255, 255));
        scrTableCongNhan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách công nhân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        tblDanhSachCongNhan.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblDanhSachCongNhan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã công nhân", "Tên công nhân"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachCongNhan.setSelectionBackground(new java.awt.Color(232, 57, 95));
        scrTableCongNhan.setViewportView(tblDanhSachCongNhan);

        jPanel5.add(scrTableCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 380, 470));

        lblMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongDoan.setText("Mã công đoạn:");
        jPanel5.add(lblMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 140, 170, 30));

        lblMaPhanCOng.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaPhanCOng.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaPhanCOng.setText("Mã phân công:");
        jPanel5.add(lblMaPhanCOng, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 190, 30));

        lblMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongNhan.setText("Mã công nhân:");
        jPanel5.add(lblMaCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 190, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("PHÂN CÔNG");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Số lượng:");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("THÔNG TIN CHI TIẾT PHÂN CÔNG");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, -1));

        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 100, 25));

        txtMaPhanCong.setEditable(false);
        txtMaPhanCong.setBackground(new java.awt.Color(255, 255, 255));
        txtMaPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaPhanCong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaPhanCong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaPhanCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 60, 200, 25));

        txtMaCongNhan.setEditable(false);
        txtMaCongNhan.setBackground(new java.awt.Color(255, 255, 255));
        txtMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongNhan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 200, 25));

        txtMaCongDoan.setEditable(false);
        txtMaCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        txtMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 140, 200, 25));

        pnlChucNangTo.setBackground(new java.awt.Color(255, 255, 255));

        pnlChucNang.setBackground(new java.awt.Color(255, 255, 255));
        pnlChucNang.setPreferredSize(new java.awt.Dimension(698, 57));

        btnPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPhanCong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/check-mark.png"))); // NOI18N
        btnPhanCong.setText("Phân công");
        btnPhanCong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlChucNangLayout = new javax.swing.GroupLayout(pnlChucNang);
        pnlChucNang.setLayout(pnlChucNangLayout);
        pnlChucNangLayout.setHorizontalGroup(
            pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChucNangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlChucNangLayout.setVerticalGroup(
            pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChucNangLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlChucNangToLayout = new javax.swing.GroupLayout(pnlChucNangTo);
        pnlChucNangTo.setLayout(pnlChucNangToLayout);
        pnlChucNangToLayout.setHorizontalGroup(
            pnlChucNangToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChucNangToLayout.createSequentialGroup()
                .addComponent(pnlChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlChucNangToLayout.setVerticalGroup(
            pnlChucNangToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel5.add(pnlChucNangTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 450, 570, 50));

        lblNgayPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayPhanCong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNgayPhanCong.setText("Ngày phân công:");
        jPanel5.add(lblNgayPhanCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 220, 170, 30));

        dtNgayPhanCong.setBackground(new java.awt.Color(255, 255, 255));
        dtNgayPhanCong.setEnabled(false);
        dtNgayPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel5.add(dtNgayPhanCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 220, 200, 30));

        lblSoLuong2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuong2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoLuong2.setText("Số lượng:");
        jPanel5.add(lblSoLuong2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 260, 170, 30));

        txtSoLuongCDHien.setEditable(false);
        txtSoLuongCDHien.setBackground(new java.awt.Color(255, 255, 255));
        txtSoLuongCDHien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongCDHien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuongCDHien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtSoLuongCDHien, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 260, 100, 30));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSP.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaSP.setText("Mã sản phẩm:");
        jPanel5.add(lblMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 180, 170, 30));

        txtMaSanPham.setEditable(false);
        txtMaSanPham.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 180, 200, 25));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Số lượng còn lại:");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, -1, -1));

        txtSoLuongConLai.setEditable(false);
        txtSoLuongConLai.setBackground(new java.awt.Color(204, 204, 204));
        txtSoLuongConLai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongConLai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuongConLai.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtSoLuongConLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 100, 25));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 1223, 520));

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

        jPanel1.add(scrTablePhanCongCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 524, 1223, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1223, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void tblCongNhanMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblDanhSachSanPhamMouseClicked

	}// GEN-LAST:event_tblDanhSachSanPhamMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPhanCong;
    private javax.swing.JButton btnXoa;
    private com.toedter.calendar.JDateChooser dtNgayPhanCong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblMaCongDoan;
    private javax.swing.JLabel lblMaCongNhan;
    private javax.swing.JLabel lblMaPhanCOng;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblNgayPhanCong;
    private javax.swing.JLabel lblSoLuong2;
    private javax.swing.JPanel pnlChucNang;
    private javax.swing.JPanel pnlChucNangTo;
    private javax.swing.JScrollPane scrTableCongNhan;
    private javax.swing.JScrollPane scrTablePhanCongCongDoan;
    private javax.swing.JTable tblDanhSachCongNhan;
    private javax.swing.JTable tblPhanCongCongDoan;
    private javax.swing.JTextField txtMaCongDoan;
    private javax.swing.JTextField txtMaCongNhan;
    private javax.swing.JTextField txtMaPhanCong;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongCDHien;
    private javax.swing.JTextField txtSoLuongConLai;
    // End of variables declaration//GEN-END:variables
}
