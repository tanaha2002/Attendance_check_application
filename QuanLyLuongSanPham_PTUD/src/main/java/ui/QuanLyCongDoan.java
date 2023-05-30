package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import dao.CongDoanSanPhamDAO;
import dao.SanPhamDAO;
import entity.BangChamCongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongDoanSanPham;
import entity.NhanVien;
import entity.SanPham;
import tablemodels.CongDoanTableModel;
import tablemodels.SanPhamTableModel;

public class QuanLyCongDoan extends javax.swing.JPanel {

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
			"Tình trạng", "Thứ tự làm", "Mã sản phẩm" };
	private static final String[] HEADERS = { "Mã sản phẩm", "Tên sản phẩm" };
	private NhanVien nhanVienLogin;
	private BangPhanCongCongNhanDAO phanCongDAO;
	private BangChamCongCongNhanDAO chamCongDAO;

	public QuanLyCongDoan(NhanVien nvLogin) {
		nhanVienLogin = nvLogin;
		sanPhamDAO = new SanPhamDAO();
		congDoanDAO = new CongDoanSanPhamDAO();
		phanCongDAO = new BangPhanCongCongNhanDAO();
		chamCongDAO = new BangChamCongCongNhanDAO();
		initComponents();
		listenerInput();
		themSuKien();
	}

	private void listenerInput() {
		txtSoLuongCan.addKeyListener(new SoKeyListener());
		txtGiaTien.addKeyListener(new SoKeyListener());
	}

	public SanPham getSanPhamDangChon() {
		return sanPhamDangChon;
	}

	public void setSanPhamDangChon(SanPham sanPhamDangChon) {
		this.sanPhamDangChon = sanPhamDangChon;
	}

	public CongDoanSanPham getCongDoanDangChon() {
		return congDoanDangChon;
	}

	public void setCongDoanDangChon(CongDoanSanPham congDoanDangChon) {
		this.congDoanDangChon = congDoanDangChon;
	}

	public double getTongTienCacCD(String maSP) {
		double tongTien = 0;
		dsSP = sanPhamDAO.layDanhSachSanPham();
		sanPhamChon = tblDanhSachSanPham.getSelectedRow();
		dsCD = congDoanDAO.layDanhSachTheoMaSP(dsSP.get(sanPhamChon).getMaSanPham());
		for (CongDoanSanPham cd : dsCD) {
			if (cd.getSanPham().getMaSanPham().equals(maSP)) {
				tongTien += cd.getTienCongDoan();
			}
		}
		return tongTien;
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

	private void batTextfield() {
		txtGiaTien.setEditable(true);
		txtSoLuongCan.setEditable(true);
		txtTenCongDoan.setEditable(true);
		txaNoiDungCongDoan.setEditable(true);
	}

	private void tatTextfield() {
		txtGiaTien.setEditable(false);
		txtSoLuongCan.setEditable(false);
		txtTenCongDoan.setEditable(false);
		txtThuTuCongDoan.setEditable(false);
		txtMaCongDoan.setEditable(false);
		txaNoiDungCongDoan.setEditable(false);
	}

	private void xoaRong() {
		txtGiaTien.setText("");
		txtTenCongDoan.setText("");
		txaNoiDungCongDoan.setText("");
		txtCongDoanTienQuyet.setText("");
		txtSoLuongCan.setText("");
	}

	private boolean kiemTraTenCongDoan() {
		if (txtTenCongDoan.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Không để trống tên công đoạn!!");
			txtTenCongDoan.requestFocus();
			return false;
		}
		return true;
	}

	private boolean kiemTraSoLuongCanThem() throws Exception {
		int thuTu = Integer.parseInt(txtThuTuCongDoan.getText().trim());
		if (thuTu > 1) {
			if (txtSoLuongCan.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Không để trống số lượng công đoạn cần làm!!");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (!txtSoLuongCan.getText().matches("[\\d+\\s]*")) {
				JOptionPane.showMessageDialog(null, "Chỉ nhập ký tự số");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) <= 0) {
				JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) < sanPhamDangChon.getSoLuongSanXuat()) {
				JOptionPane.showMessageDialog(null, "Không nhập số bé hơn " + sanPhamDangChon.getSoLuongSanXuat());
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			}
			int soLuongCan = Integer.parseInt(txtSoLuongCan.getText().trim());
			int soLuongCDTruoc = congDoanDAO
					.timCongDoan(congDoanDAO.getMaCongDoanCaoNhatCuaSanPham(sanPhamDangChon.getMaSanPham()),
							sanPhamDangChon.getMaSanPham())
					.getSoLuong();
			if (soLuongCan > soLuongCDTruoc) {
				JOptionPane.showMessageDialog(null, "Không nhập số lớn hơn " + soLuongCDTruoc);
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			}

		} else {
			if (txtSoLuongCan.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Không để trống số lượng công đoạn cần làm!!");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (!txtSoLuongCan.getText().matches("[\\d+\\s]*")) {
				JOptionPane.showMessageDialog(null, "Chỉ nhập ký tự số");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) <= 0) {
				JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) < sanPhamDangChon.getSoLuongSanXuat()) {
				JOptionPane.showMessageDialog(null, "Không nhập số bé hơn " + sanPhamDangChon.getSoLuongSanXuat());
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) > (sanPhamDangChon.getSoLuongSanXuat()
					+ sanPhamDangChon.getSoLuongSanXuat() * 0.15)) {
				JOptionPane.showMessageDialog(null, "Không nhập số lớn hơn "
						+ (sanPhamDangChon.getSoLuongSanXuat() + sanPhamDangChon.getSoLuongSanXuat() * 0.15));
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			}
		}

		return true;
	}
	
	private boolean kiemTraSoLuongCanSua() throws Exception {
		int thuTu = Integer.parseInt(txtThuTuCongDoan.getText().trim());
		String maCDTienQuyet = (String) tblCongDoan.getModel().getValueAt(tblCongDoan.getSelectedRow() - 1, 1);
		if (thuTu > 1) {
			if (txtSoLuongCan.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Không để trống số lượng công đoạn cần làm!!");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (!txtSoLuongCan.getText().matches("[\\d+\\s]*")) {
				JOptionPane.showMessageDialog(null, "Chỉ nhập ký tự số");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) <= 0) {
				JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) < sanPhamDangChon.getSoLuongSanXuat()) {
				JOptionPane.showMessageDialog(null, "Không nhập số bé hơn " + sanPhamDangChon.getSoLuongSanXuat());
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			}
			int soLuongCan = Integer.parseInt(txtSoLuongCan.getText().trim());
			int soLuongCDTruoc = congDoanDAO.timCongDoan(maCDTienQuyet, sanPhamDangChon.getMaSanPham()).getSoLuong();
			if (soLuongCan > soLuongCDTruoc) {
				JOptionPane.showMessageDialog(null, "Không nhập số lớn hơn " + soLuongCDTruoc);
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			}

		} else {
			if (txtSoLuongCan.getText().isBlank()) {
				JOptionPane.showMessageDialog(null, "Không để trống số lượng công đoạn cần làm!!");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (!txtSoLuongCan.getText().matches("[\\d+\\s]*")) {
				JOptionPane.showMessageDialog(null, "Chỉ nhập ký tự số");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) <= 0) {
				JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) < sanPhamDangChon.getSoLuongSanXuat()) {
				JOptionPane.showMessageDialog(null, "Không nhập số bé hơn " + sanPhamDangChon.getSoLuongSanXuat());
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			} else if (Integer.parseInt(txtSoLuongCan.getText().trim()) > (sanPhamDangChon.getSoLuongSanXuat()
					+ sanPhamDangChon.getSoLuongSanXuat() * 0.15)) {
				JOptionPane.showMessageDialog(null, "Không nhập số lớn hơn "
						+ (sanPhamDangChon.getSoLuongSanXuat() + sanPhamDangChon.getSoLuongSanXuat() * 0.15));
				txtSoLuongCan.setText("");
				txtSoLuongCan.requestFocus();
				return false;
			}
		}

		return true;
	}

	private boolean kiemTraGiaTien() {
		if (txtGiaTien.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Không để trống giá tiền!!");
			txtGiaTien.requestFocus();
			return false;
		} else if (!txtGiaTien.getText().matches("^(?=.*\\d)\\d*(\\.\\d+)?$")) {
			JOptionPane.showMessageDialog(null, "Vui lòng chỉ nhập ký tự số");
			txtGiaTien.setText("");
			txtGiaTien.requestFocus();
			return false;
		} else if (Double.parseDouble(txtGiaTien.getText().trim()) <= 0) {
			JOptionPane.showMessageDialog(null, "Giá tiền phải lớn hơn 0");
			txtGiaTien.setText("");
			txtGiaTien.requestFocus();
			return false;
		} else if (Double.parseDouble(txtGiaTien.getText().trim()) > (getSanPhamDangChon().getGiaTien() / 2
				- getTongTienCacCD(txtMaSanPham.getText()))) {
			JOptionPane.showMessageDialog(null, "Giá tiền không được lớn hơn "
					+ (getSanPhamDangChon().getGiaTien() / 2 - getTongTienCacCD(txtMaSanPham.getText())));
			txtGiaTien.setText("");
			txtGiaTien.requestFocus();
			return false;
		}
		return true;
	}

	private boolean kiemTraDuLieuNhap() throws NumberFormatException, HeadlessException, Exception {
		boolean kt = true;
			if (!kiemTraTenCongDoan()) {
				kt = false;
			} else if (!kiemTraSoLuongCanThem()) {
				kt = false;
			} else if (!kiemTraGiaTien()) {
				kt = false;
			}
		return kt;
	}

	private CongDoanSanPham getDuLieuTuTextField() throws Exception {
		if (kiemTraDuLieuNhap()) {
			String maCD = txtMaCongDoan.getText().trim();
			String tenCD = txtTenCongDoan.getText().trim();
			String maSP = txtMaSanPham.getText().trim();
			Double giaTien = Double.parseDouble(txtGiaTien.getText().trim());
			int soLuongCan = Integer.parseInt(txtSoLuongCan.getText().trim());
			int thuTuLam = Integer.parseInt(txtThuTuCongDoan.getText().trim());
			String moTa = txaNoiDungCongDoan.getText().trim();
			CongDoanSanPham congDoan = new CongDoanSanPham(maCD, tenCD, giaTien, soLuongCan, null, moTa, thuTuLam,
					sanPhamDAO.timSanPham(maSP));
			return congDoan;
		}
		return null;
	}

	private boolean kiemTraDuLieuSua() throws Exception {
		boolean kt = true;
		
		if (!kiemTraTenCongDoan()) {
			kt = false;
		} else if (!kiemTraSoLuongCanSua()) {
			kt = false;
		} else if (!kiemTraGiaTien()) {
			kt = false;
		} else if (this.txtGiaTien.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Không để trống giá tiền!!");
			txtGiaTien.requestFocus();
			kt = false;
		} else if (!this.txtGiaTien.getText().matches("^(?=.*\\d)\\d*(\\.\\d+)?$")) {
			JOptionPane.showMessageDialog(null, "Vui lòng chỉ nhập số dương!!");
			txtGiaTien.setText("");
			txtGiaTien.requestFocus();
			kt = false;
		} else if (Double.parseDouble(txtGiaTien.getText().trim()) > (getSanPhamDangChon().getGiaTien() / 2
				- getTongTienCacCD(txtMaSanPham.getText()) + congDoanDangChon.getTienCongDoan())) {
			JOptionPane.showMessageDialog(null, "Giá tiền không được lớn hơn " + (getSanPhamDangChon().getGiaTien() / 2
					- getTongTienCacCD(txtMaSanPham.getText()) + congDoanDangChon.getTienCongDoan()));
			txtGiaTien.setText("");
			txtGiaTien.requestFocus();
			kt = false;
		}
		return kt;
	}

	private CongDoanSanPham getDuLieuTuTextFieldSua() throws Exception {
		if (kiemTraDuLieuSua()) {
			String maCD = txtMaCongDoan.getText().trim();
			String tenCD = txtTenCongDoan.getText().trim();
			String maSP = txtMaSanPham.getText().trim();
			Double giaTien = Double.parseDouble(txtGiaTien.getText().trim());
			int soLuongCan = Integer.parseInt(txtSoLuongCan.getText().trim());
			int thuTuLam = Integer.parseInt(txtThuTuCongDoan.getText().trim());
			String moTa = txaNoiDungCongDoan.getText().trim();
			CongDoanSanPham congDoan = new CongDoanSanPham(maCD, tenCD, giaTien, soLuongCan, null, moTa, thuTuLam,
					sanPhamDAO.timSanPham(maSP));
			return congDoan;
		}
		return null;
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
							setSanPhamDangChon(sp);
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
				txtThuTuCongDoan.setText("");
				txtCongDoanTienQuyet.setText("");
				txaNoiDungCongDoan.setText("");
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
							setCongDoanDangChon(cd);
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
				int selectedRow = tblCongDoan.getSelectedRow();
				if (selectedRow == 0) {
					txtCongDoanTienQuyet.setText("Không");
					return;
				}
				String maSP = (String) tblDanhSachSanPham.getModel().getValueAt(tblDanhSachSanPham.getSelectedRow(), 0);
				String maCDTruoc = (String) tblCongDoan.getModel().getValueAt(tblCongDoan.getSelectedRow() - 1, 1);
				CongDoanSanPham cdTienQuyet = congDoanDAO.timCongDoan(maCDTruoc, maSP);
				txtCongDoanTienQuyet.setText(cdTienQuyet.getTenCongDoan());
			}
		});

		btnThem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnPhanCongCongDoan.setEnabled(false);
				int selectedRow = tblDanhSachSanPham.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần thêm công đoạn");
					return;
				}
				try {
					btnThemActionPerformed(e);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			private void btnThemActionPerformed(ActionEvent evt) throws Exception {
				xoaRong();
				batTextfield();
				txtMaCongDoan.setText(getNewMaCongDoan());
				txtThuTuCongDoan.setText(String.valueOf(getNewThuTuCD()));
				trangThaiThem = true;
				pnlChucNangTo.removeAll();
				pnlChucNangTo.setLayout(new BorderLayout());
				PanelXacNhan pnlXacNhan = new PanelXacNhan();
				pnlChucNangTo.add(pnlXacNhan);

				ActionListener listenerHuy = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						txtThuTuCongDoan.setText("");
						tatTextfield();
						btnPhanCongCongDoan.setEnabled(true);
						trangThaiThem = false;
						pnlChucNangTo.removeAll();
						pnlChucNangTo.setLayout(new BorderLayout());
						pnlChucNangTo.add(pnlChucNang);
						pnlChucNangTo.updateUI();
					}
				};

				ActionListener listenerXoaRong = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						xoaRong();
					}
				};

				ActionListener listenerThem = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {

							SanPham sanPham = sanPhamDangChon;
							int soLuongCD = sanPhamDangChon.getSoLuongCongDoan();
							CongDoanSanPham congDoan = getDuLieuTuTextField();
							if (congDoan != null) {
								congDoanDAO.themCongDoan(congDoan, sanPham);
								sanPhamChon = tblDanhSachSanPham.getSelectedRow();
								if (sanPhamChon != -1) {
									dsCD = congDoanDAO.layDanhSachTheoMaSP(dsSP.get(sanPhamChon).getMaSanPham());
									updateCongDoanTableModel(HEADERSCD, dsCD);
								}
								soLuongCD++;
								sanPhamDangChon.setSoLuongCongDoan(soLuongCD);
								sanPhamDAO.capNhatSanPham(sanPhamDangChon);
								xoaRong();
								txtMaCongDoan.setText(getNewMaCongDoan());
								txtThuTuCongDoan.setText(String.valueOf(getNewThuTuCD()));
								JOptionPane.showMessageDialog(null, "Thêm thành công");
							}
						} catch (Exception e2) {
							e2.printStackTrace();
							// TODO: handle exception
						}

					}
					// @SuppressWarnings("deprecation")

				};
				pnlXacNhan.huyAddActionListener(listenerHuy);
				pnlXacNhan.xacNhanAddActionListener(listenerThem);
				pnlXacNhan.xoaRongAddActionListener(listenerXoaRong);
				pnlChucNangTo.repaint();
				pnlChucNangTo.revalidate();

			}

			private String getNewMaCongDoan() throws Exception {
				String maCD = congDoanDAO.getMaCongDoanCaoNhatCuaSanPham(sanPhamDangChon.getMaSanPham());
				int maCDt = 1;
				if (maCD != null) {
					maCDt = Integer.parseInt(maCD.replaceAll("\\D+", "")) + 1;
				}
				return String.format("CD%06d", maCDt);
			}

			private int getNewThuTuCD() throws Exception {
				int thuTuCD = congDoanDAO.getThuTuLamCuoiCuaCD(sanPhamDangChon.getMaSanPham());
				int thuTux = 1;
				if (thuTuCD != 0) {
					thuTux = thuTuCD + 1;
				}
				return thuTux;
			}
		});
		btnXoa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					btnXoaActionPerformed(e);
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}

			private void btnXoaActionPerformed(ActionEvent e) {
				int selectedRow = tblDanhSachSanPham.getSelectedRow();
				int selectedRowCD = tblCongDoan.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần xóa công đoạn");
					return;
				} else if (selectedRowCD == -1) {
					JOptionPane.showMessageDialog(null, "Chọn công đoạn cần xóa");
					return;
				}
				try {
					String maXoa = (String) tblCongDoan.getModel().getValueAt(tblCongDoan.getSelectedRow(), 1);
					String maSP = (String) tblDanhSachSanPham.getModel().getValueAt(tblDanhSachSanPham.getSelectedRow(),
							0);
					int soLuongCD = sanPhamDangChon.getSoLuongCongDoan();

					if (!phanCongDAO.checkCongDoanInBangChamCong(maXoa, maSP)) {
						JOptionPane.showMessageDialog(null,
								"Không thể xóa công đoạn vì công đoạn này đang tồn tại trong bảng phân công");
						return;
					}

					int luaChon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không", "Xóa",
							JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
					if (luaChon == JOptionPane.YES_OPTION) {
						boolean ketQua = congDoanDAO.xoaCongDoan(maXoa, sanPhamDangChon.getMaSanPham());
						if (ketQua) {
							sanPhamChon = tblDanhSachSanPham.getSelectedRow();
							if (sanPhamChon != -1) {
								dsCD = congDoanDAO.layDanhSachTheoMaSP(dsSP.get(sanPhamChon).getMaSanPham());
								updateCongDoanTableModel(HEADERSCD, dsCD);
							}
							soLuongCD--;
							sanPhamDangChon.setSoLuongCongDoan(soLuongCD);
							sanPhamDAO.capNhatSanPham(sanPhamDangChon);
							xoaRong();
							JOptionPane.showMessageDialog(null, "Xóa thành công");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int selectedRow = tblDanhSachSanPham.getSelectedRow();
				int selectedRowCD = tblCongDoan.getSelectedRow();
				String maCDSua = txtMaCongDoan.getText().trim();
				String maSP = txtMaSanPham.getText().trim();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần sửa công đoạn");
					return;
				} else if (selectedRowCD == -1) {
					JOptionPane.showMessageDialog(null, "Chọn công đoạn cần sửa");
					return;
				} else if (!phanCongDAO.checkCongDoanInBangChamCong(maCDSua, maSP)) {
					JOptionPane.showMessageDialog(null,
							"Không thể sửa công đoạn vì công đoạn này đang tồn tại trong bảng phân công");
					return;
				}
				btnSuaActionPerformed(evt);
			}

			private void btnSuaActionPerformed(ActionEvent evt) {
				// TODO add your handling code here:
				batTextfield();
				txtMaSanPham.setEditable(false);
				pnlChucNangTo.removeAll();
				pnlChucNangTo.setLayout(new BorderLayout());
				PanelXacNhan pnlXacNhan = new PanelXacNhan();
				pnlChucNangTo.add(pnlXacNhan);

				ActionListener listenerHuy = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						trangThaiThem = false;
						pnlChucNangTo.removeAll();
						pnlChucNangTo.setLayout(new BorderLayout());
						pnlChucNangTo.add(pnlChucNang);
						pnlChucNangTo.updateUI();
						tatTextfield();
					}
				};

				ActionListener listenerXoaRong = new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						xoaRong();
					}
				};

				ActionListener listenerSua = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							CongDoanSanPham congDoan = getDuLieuTuTextFieldSua();
							if (congDoan != null) {
								int luaChon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn sửa không", "Sửa",
										JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
								if (luaChon == JOptionPane.YES_OPTION) {
									boolean ketQua = congDoanDAO.capNhatCongDoan(congDoan, sanPhamDangChon);
									if (ketQua) {
										if (sanPhamChon != -1) {
											dsCD = congDoanDAO
													.layDanhSachTheoMaSP(dsSP.get(sanPhamChon).getMaSanPham());
											updateCongDoanTableModel(HEADERSCD, dsCD);
										}
										JOptionPane.showMessageDialog(null, "Thông tin của công đoạn đã được cập nhật");
									}
								}

							}

						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}

					}

				};
				pnlXacNhan.huyAddActionListener(listenerHuy);
				pnlXacNhan.xacNhanAddActionListener(listenerSua);
				pnlXacNhan.xoaRongAddActionListener(listenerXoaRong);
				pnlChucNangTo.repaint();
				pnlChucNangTo.revalidate();

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
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "PhanCong1.jpg");
			        ImageIcon imageIcon1 = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "PhanCong2.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        JLabel imageLabel2 = new JLabel(imageIcon1);
			        imagePanel.add(imageLabel2);
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn phân công", JOptionPane.PLAIN_MESSAGE);
				}
			});

		btnPhanCongCongDoan.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				btnPhanCongCongDoanMouseClicked(evt);
			}

			private void btnPhanCongCongDoanMouseClicked(java.awt.event.MouseEvent evt) {

				int selectedRow = tblDanhSachSanPham.getSelectedRow();
				int selectedRowCD = tblCongDoan.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Chọn sản phẩm có công đoạn cần phân công");
					return;
				} else if (selectedRowCD == -1) {
					JOptionPane.showMessageDialog(null, "Chọn công đoạn cần phân công");
					return;
				}

				String maCDPhanCong = (String) tblCongDoan.getModel().getValueAt(tblCongDoan.getSelectedRow(), 1);
				String maSPPhanCong = (String) tblDanhSachSanPham.getModel()
						.getValueAt(tblDanhSachSanPham.getSelectedRow(), 0);
				CongDoanSanPham cdPhanCong = congDoanDAO.timCongDoan(maCDPhanCong, maSPPhanCong);

				if (cdPhanCong.getThuTu() == 1) {
					String maSP = (String) tblDanhSachSanPham.getModel().getValueAt(selectedRow, 0);
					String maCD = (String) tblCongDoan.getModel().getValueAt(selectedRowCD, 1);
					int slCD = congDoanDAO.timCongDoan(maCD, maSP).getSoLuong();
					int slHoanThanh = (int) chamCongDAO.getSLHoanThanhCuaCongDoan(maCD, maSP);
					if (slHoanThanh == slCD) {
						JOptionPane.showMessageDialog(null, "Công đoạn này đã hoàn thành!!!");
					} else {
						JDialog phanCongCongDoan = new JDialog();
						phanCongCongDoan.add(new PhanCongCongDoan(congDoanDangChon, sanPhamDangChon, nhanVienLogin));
						phanCongCongDoan.setTitle("Phân công công đoạn");
						phanCongCongDoan.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

						phanCongCongDoan.addWindowFocusListener(new WindowFocusListener() {

							@Override
							public void windowLostFocus(WindowEvent e) {
								// TODO Auto-generated method stub
								phanCongCongDoan.requestFocus();

							}

							@Override
							public void windowGainedFocus(WindowEvent e) {
								// TODO Auto-generated method stub
							}
						});
						phanCongCongDoan.setSize(1250, 900);
						phanCongCongDoan.setLocationRelativeTo(null);
						phanCongCongDoan.setVisible(true);
					}
				} else if (cdPhanCong.getThuTu() > 1) {
					String maCDPhanCongTienQuyet = (String) tblCongDoan.getModel()
							.getValueAt(tblCongDoan.getSelectedRow() - 1, 1);
					CongDoanSanPham cdPhanCongTienQuyet = congDoanDAO.timCongDoan(maCDPhanCongTienQuyet, maSPPhanCong);
					if (chamCongDAO.getSLHoanThanhCuaCongDoan(maCDPhanCongTienQuyet,
							maSPPhanCong) != cdPhanCongTienQuyet.getSoLuong()) {
						JOptionPane.showMessageDialog(null,
								"Công đoạn trước đó chưa hoàn thành, không thể phân công cho công đoạn này!");
					} else if (chamCongDAO.getSLHoanThanhCuaCongDoan(maCDPhanCong, maSPPhanCong) == cdPhanCong
							.getSoLuong()) {
						JOptionPane.showMessageDialog(null, "Công đoạn này đã hoàn thành!!!");
					} else {
						JDialog phanCongCongDoan = new JDialog();
						phanCongCongDoan.add(new PhanCongCongDoan(congDoanDangChon, sanPhamDangChon, nhanVienLogin));
						phanCongCongDoan.setTitle("Phân công công đoạn");
						phanCongCongDoan.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

						phanCongCongDoan.addWindowFocusListener(new WindowFocusListener() {

							@Override
							public void windowLostFocus(WindowEvent e) {
								// TODO Auto-generated method stub
								phanCongCongDoan.requestFocus();

							}

							@Override
							public void windowGainedFocus(WindowEvent e) {
								// TODO Auto-generated method stub
							}
						});
						phanCongCongDoan.setSize(1250, 900);
						phanCongCongDoan.setLocationRelativeTo(null);
						phanCongCongDoan.setVisible(true);
					}
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// <editor-fold defaultstate="collapsed" desc="Generated
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
        jLabel1 = new javax.swing.JLabel();
        btnPhanCongCongDoan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        txtMaCongDoan = new javax.swing.JTextField();
        txtTenCongDoan = new javax.swing.JTextField();
        txtSoLuongCan = new javax.swing.JTextField();
        txtGiaTien = new javax.swing.JTextField();
        txtCongDoanTienQuyet = new javax.swing.JTextField();
        pnlChucNangTo = new javax.swing.JPanel();
        pnlChucNang = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        lblSoThuTuCongDoan1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaNoiDungCongDoan = new javax.swing.JTextArea();
        lblThuTuLam = new javax.swing.JLabel();
        txtThuTuCongDoan = new javax.swing.JTextField();
        btnHuongDan = new javax.swing.JButton();
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

        jPanel5.add(scrTableSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 440, 380));

        lblTenCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        lblTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenCongDoan.setText("Tên công đoạn:");
        jPanel5.add(lblTenCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, -1, 40));

        lblMaSanPham.setBackground(new java.awt.Color(255, 255, 255));
        lblMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSanPham.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaSanPham.setText("Mã sản phẩm:");
        jPanel5.add(lblMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, -1, 38));

        lblTenSanPham.setBackground(new java.awt.Color(255, 255, 255));
        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPham.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPham.setText("Tên sản phẩm:");
        jPanel5.add(lblTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, -1, 40));

        lblMaCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        lblMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongDoan.setText("Mã công đoạn:");
        jPanel5.add(lblMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, -1, 40));

        lblSoLuongCan.setBackground(new java.awt.Color(255, 255, 255));
        lblSoLuongCan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongCan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoLuongCan.setText("Số lượng cần:");
        jPanel5.add(lblSoLuongCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, -1, 40));

        lblNoiDung.setBackground(new java.awt.Color(255, 255, 255));
        lblNoiDung.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNoiDung.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNoiDung.setText("Nội dung:");
        jPanel5.add(lblNoiDung, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 210, -1, 40));

        lblGiaTien.setBackground(new java.awt.Color(255, 255, 255));
        lblGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblGiaTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGiaTien.setText("Tiền công đoạn:");
        jPanel5.add(lblGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, -1, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("THÔNG TIN CHI TIẾT CÔNG ĐOẠN SẢN PHẨM");
        jPanel5.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        btnPhanCongCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnPhanCongCongDoan.setText("Phân công công đoạn");
        btnPhanCongCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPhanCongCongDoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPhanCongCongDoanMouseClicked(evt);
            }
        });
        btnPhanCongCongDoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanCongCongDoanActionPerformed(evt);
            }
        });
        jPanel5.add(btnPhanCongCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, -1, 40));

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Chọn công đoạn bên dưới xong bấm vào nút này!");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, -1, -1));

        txtMaSanPham.setEditable(false);
        txtMaSanPham.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 190, 25));

        txtTenSanPham.setEditable(false);
        txtTenSanPham.setBackground(new java.awt.Color(255, 255, 255));
        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTenSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, 190, 25));

        txtMaCongDoan.setEditable(false);
        txtMaCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        txtMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtMaCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 160, 190, 25));

        txtTenCongDoan.setEditable(false);
        txtTenCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        txtTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtTenCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, 180, 25));

        txtSoLuongCan.setEditable(false);
        txtSoLuongCan.setBackground(new java.awt.Color(255, 255, 255));
        txtSoLuongCan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongCan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuongCan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtSoLuongCan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 270, 62, 25));

        txtGiaTien.setEditable(false);
        txtGiaTien.setBackground(new java.awt.Color(255, 255, 255));
        txtGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtGiaTien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtGiaTien.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 70, 196, 25));

        txtCongDoanTienQuyet.setEditable(false);
        txtCongDoanTienQuyet.setBackground(new java.awt.Color(255, 255, 255));
        txtCongDoanTienQuyet.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtCongDoanTienQuyet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtCongDoanTienQuyet.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtCongDoanTienQuyet, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 110, 196, 25));

        pnlChucNangTo.setBackground(new java.awt.Color(255, 255, 255));
        pnlChucNangTo.setLayout(new java.awt.BorderLayout());

        pnlChucNang.setBackground(new java.awt.Color(255, 255, 255));
        pnlChucNang.setPreferredSize(new java.awt.Dimension(698, 57));

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-add-20.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout pnlChucNangLayout = new javax.swing.GroupLayout(pnlChucNang);
        pnlChucNang.setLayout(pnlChucNangLayout);
        pnlChucNangLayout.setHorizontalGroup(
            pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChucNangLayout.createSequentialGroup()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlChucNangLayout.setVerticalGroup(
            pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChucNangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pnlChucNangTo.add(pnlChucNang, java.awt.BorderLayout.CENTER);

        jPanel5.add(pnlChucNangTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 390, 580, 50));

        lblSoThuTuCongDoan1.setBackground(new java.awt.Color(255, 255, 255));
        lblSoThuTuCongDoan1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoThuTuCongDoan1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoThuTuCongDoan1.setText("Công đoạn tiên quyết:");
        jPanel5.add(lblSoThuTuCongDoan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 100, -1, 40));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        txaNoiDungCongDoan.setEditable(false);
        txaNoiDungCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        txaNoiDungCongDoan.setColumns(20);
        txaNoiDungCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txaNoiDungCongDoan.setRows(5);
        jScrollPane1.setViewportView(txaNoiDungCongDoan);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 210, 300, 130));

        lblThuTuLam.setBackground(new java.awt.Color(255, 255, 255));
        lblThuTuLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThuTuLam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblThuTuLam.setText("Thứ tự làm:");
        jPanel5.add(lblThuTuLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 150, -1, 40));

        txtThuTuCongDoan.setEditable(false);
        txtThuTuCongDoan.setBackground(new java.awt.Color(255, 255, 255));
        txtThuTuCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtThuTuCongDoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtThuTuCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtThuTuCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 160, 196, 25));

        btnHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnHuongDan.setText("?");
        jPanel5.add(btnHuongDan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 30, 40, 40));

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
                "STT", "Mã công đoạn", "Tên  công đoạn", "Giá tiền", "Số lượng cần làm", "Tình trạng", "Thứ tự làm", "Mã sản phẩm"
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
            .addGap(0, 1351, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1232, Short.MAX_VALUE)
                .addComponent(scrTableCongDoan, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrTableCongDoan, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1351, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
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
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnPhanCongCongDoan;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblMaCongDoan;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblNoiDung;
    private javax.swing.JLabel lblSoLuongCan;
    private javax.swing.JLabel lblSoThuTuCongDoan1;
    private javax.swing.JLabel lblTenCongDoan;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblThuTuLam;
    private javax.swing.JPanel pnlChucNang;
    private javax.swing.JPanel pnlChucNangTo;
    private javax.swing.JScrollPane scrTableCongDoan;
    private javax.swing.JScrollPane scrTableSanPham;
    private javax.swing.JTable tblCongDoan;
    private javax.swing.JTable tblDanhSachSanPham;
    private javax.swing.JTextArea txaNoiDungCongDoan;
    private javax.swing.JTextField txtCongDoanTienQuyet;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaCongDoan;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuongCan;
    private javax.swing.JTextField txtTenCongDoan;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtThuTuCongDoan;
    // End of variables declaration//GEN-END:variables
}
