/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;

import dao.BangChamCongCongNhanDAO;
import dao.BangLuongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import dao.LuuFile;
import entity.BangChamCongCongNhan;
import entity.BangLuongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongDoanSanPham;
import entity.CongNhan;
import tablemodels.BangLuongCongNhanTableModel;

/**
 *

 */
public class QuanLyLuongCN extends javax.swing.JPanel {

	// private static final int MABANGLUONG = 0;
	// private static final int MACONGNHAN = 1;
	// private static final int HOTEN = 2;
	// private static final int TENCONGDOAN = 3;
	// private static final int SOLUONGSP = 4;
	// private static final int LUONG = 5;

	private static final String[] HEADERS = { "Mã công nhân", "Tên công nhân", "Tên sản phẩm", "Tên công đoạn",
			"Số lượng hoàn thành","Thời gian", "Tổng tiền công đoạn", "Tiền thưởng chuyên cần" };
	private CongNhan congNhan;
	private BangLuongCongNhanDAO bangLuongCongNhanDAO;
	private BangLuongCongNhanTableModel bangLuongCongNhanTableModel;
	private BangLuongCongNhan bangLuongCongNhan;
	private BangPhanCongCongNhan bangPhanCongCongNhan;
	private BangPhanCongCongNhanDAO bangPhanCongCongNhanDAO;
	private BangChamCongCongNhanDAO bangChamCongCongNhanDAO;
	private BangChamCongCongNhan bangChamCongCongNhan;
	private List<BangLuongCongNhan> dsbangLuongCongNhan;
	private List<BangPhanCongCongNhan> dsPCCN;

	private InBangLuongCongNhan inBangLuongUI;
	private boolean trangThaiThem;
	private String maPhanCong = "";
	private int soLuongSPDaLam = 0;
	private CongDoanSanPham congDoanSanPham;
	private double tienCongDoan = 0;
	private double tongLuong = 0;
	private List<BangLuongCongNhan> dsTinhLuong;
	private List<List<String>> dsHienThiThongTinLuong;
	private boolean tinhLuong;

	public List<BangLuongCongNhan> getDsTinhLuong() {
		return dsTinhLuong;
	}

	public void setDsTinhLuong(List<BangLuongCongNhan> dsTinhLuong) {
		this.dsTinhLuong = dsTinhLuong;
	}

	public double getTongLuong() {
		return tongLuong;
	}

	public void setTongLuong(double tongLuong) {
		this.tongLuong = tongLuong;
	}

	public QuanLyLuongCN() {
		dsHienThiThongTinLuong = new ArrayList<>();
		dsPCCN = new ArrayList<>();
		dsTinhLuong = new ArrayList<>();
		dsbangLuongCongNhan = new ArrayList<>();
		// inBangLuongUI = new InBangLuongCongNhan();
		congNhan = new CongNhan();
		tblQuanLiLuongCN = new JTable();
		bangLuongCongNhanDAO = new BangLuongCongNhanDAO();
		dsbangLuongCongNhan = new ArrayList<>();
		trangThaiThem = false;
		tinhLuong = false;
		bangPhanCongCongNhan = new BangPhanCongCongNhan();
		bangPhanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		bangChamCongCongNhanDAO = new BangChamCongCongNhanDAO();
		bangChamCongCongNhan = new BangChamCongCongNhan();
		tblQuanLiLuongCN.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		congDoanSanPham = new CongDoanSanPham();
		initComponents();
		themSuKien();

	}

	private void updateModelTable(String[] headers, List<List<String>> dsHienThiThongTin) {
		bangLuongCongNhanTableModel = new BangLuongCongNhanTableModel(headers, dsHienThiThongTin);
		tblQuanLiLuongCN.setModel(bangLuongCongNhanTableModel);
		tblQuanLiLuongCN.updateUI();
	}

	// private void updateModelTable(String[] headers, List<BangLuongCongNhan>
	// danhSachBangLuong) {
	// bangLuongCongNhanTableModel = new BangLuongCongNhanTableModel(headers,
	// dsHienThiThongTin);
	// tblQuanLiLuongCN.setModel(bangLuongCongNhanTableModel);
	// tblQuanLiLuongCN.updateUI();
	// }

	private void xoaRong() {
		txtHoTen.setText("");
		txtLuong.setText("");

		txtLuong.setText("");
		txtMaCN.setText("");
		txtSoLuongSP.setText("");
		txtTenCongDoan.setText("");

	}

	private void fillDuLieuVaoTextField() {
		txtHoTen.setEditable(true);
		txtLuong.setEditable(true);

		txtLuong.setEditable(false);
		txtMaCN.setEditable(true);
		txtSoLuongSP.setEditable(true);
		txtTenCongDoan.setEditable(false);

	}

	private void tatDuLieuVaoTextField() {
		txtHoTen.setEditable(false);
		txtLuong.setEditable(false);

		txtLuong.setEditable(false);
		txtMaCN.setEditable(false);
		txtSoLuongSP.setEditable(false);
		txtTenCongDoan.setEditable(false);

	}

	private void fillThongTinBangLuong(List<List<String>> dsBangLuong, BangLuongCongNhan bangLuongCongNhan,
			int selectedRow) throws Exception {
		// tatDuLieuVaoTextField();

		// String maCongNhan = dsHienThiThongTinLuong.get(selectedRow).get(0);

		int duLieu = tblQuanLiLuongCN.getSelectedRow();

		if (dsBangLuong.size() != 0) {
			if (duLieu != -1) {
				List<String> rowData = dsBangLuong.get(duLieu);
				String maCongNhan = rowData.get(0);
				String hoTen = rowData.get(1);
				String tenSP = rowData.get(2);
				String tenCD = rowData.get(3);
				String soLuongHoanThanh = rowData.get(4);
				String luong = rowData.get(5);
				double TongTien = Double.parseDouble(luong);
				NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				luong = formatter.format(TongTien);
				// Format the luong value to VNĐ

				// Set the values to the text fields
				txtMaCN.setText(maCongNhan);
				txtHoTen.setText(hoTen);
				txtSoLuongSP.setText(soLuongHoanThanh);
				txtTenCongDoan.setText(tenCD);
				txtLuong.setText(luong);
			}
		}
		//

	}

	private void themSuKien() {
		dsbangLuongCongNhan = bangLuongCongNhanDAO.layDanhSachBangLuongCongNhan();
		// System.out.println(dsbangLuongCongNhan);

		updateModelTable(HEADERS, dsHienThiThongTinLuong);

		tblQuanLiLuongCN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String item = null;
				item = (String) tblQuanLiLuongCN.getModel().getValueAt(tblQuanLiLuongCN.getSelectedRow(), 0);
				if (item != null) {
					BangLuongCongNhan bangLuongCongNhan = null;
					try {
						if (!trangThaiThem) {

							fillThongTinBangLuong(dsHienThiThongTinLuong, bangLuongCongNhan,
									tblQuanLiLuongCN.getSelectedRow());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

		});

		btnTinhLuong.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String thangTinhLuong = cmbThang.getSelectedItem().toString().trim();
				String namTinhLuong = cmbNam.getSelectedItem().toString().trim();
				String toNhom = cmbTo.getSelectedItem().toString().trim();
				
				
				if(thangTinhLuong.equals("All") || namTinhLuong.equals("All")) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn tháng và năm cần tính lương");
				}

				else {
				  int chonLua = JOptionPane.showConfirmDialog(null, "Lưu ý: nút tính lương chỉ nên bấm 1 lần duy nhất trong tháng và nên là ngày"
				  		+ "cuối cùng trong tháng hoặc là ngày 5 tháng tiếp theo", "Xác nhận bấm nút tính lương", JOptionPane.YES_NO_OPTION);

			        if (chonLua == JOptionPane.YES_OPTION) {
				
				dsHienThiThongTinLuong = bangLuongCongNhanDAO.tinhLuongCongNhanChoTableModel(thangTinhLuong,
						namTinhLuong, toNhom);
				
				
				if (dsHienThiThongTinLuong.size() == 0) {
					JOptionPane.showMessageDialog(null, "Không có ai được chấm công trong tháng này");
				}
				updateModelTable(HEADERS, dsHienThiThongTinLuong);

				dsTinhLuong = bangLuongCongNhanDAO.tinhLuongCongNhan(thangTinhLuong, namTinhLuong, toNhom);

				// check thang Tinh Luong
				boolean thangHienTai = bangLuongCongNhanDAO.kiemTraThangTinhLuong(thangTinhLuong, namTinhLuong);
				List<BangLuongCongNhan> dsBangLuongHienTai = bangLuongCongNhanDAO.layDanhSachBangLuong(thangTinhLuong,
						namTinhLuong, toNhom);

				// System.out.println(dsBangLuongHienTai);
				for (BangLuongCongNhan bangLuong : dsTinhLuong) {

					try {
						boolean exists = false;
						for (BangLuongCongNhan bl : dsBangLuongHienTai) {

							if (bangLuong.getCongNhan().getMaCongNhan().equals(bl.getCongNhan().getMaCongNhan())) {
								exists = true;
								if(bangLuong.getLuong() > bl.getLuong()) {
									bangLuongCongNhanDAO.capNhatBangLuong(bl, bangLuong.getLuong());
							

								}
								
								if(bangLuongCongNhanDAO.duocThuongChuyenCan(thangTinhLuong, namTinhLuong, bl.getCongNhan().getMaCongNhan()))
									bangLuongCongNhanDAO.capNhatTienThuongChuyenCan(bl, 500000);
								break;
							}
						}
						if (!exists) {
							if (thangHienTai == true) {
								bangLuongCongNhanDAO.themBangLuongCongNhan(bangLuong);
								
							} else {
								bangLuongCongNhanDAO.themBangLuongCongNhanTheoThang(bangLuong, thangTinhLuong,
										namTinhLuong);
							}

						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			        } 
				}

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
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "TinhLuongCN.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn tính lương công nhân", JOptionPane.PLAIN_MESSAGE);
				}
			});

		
		btnInBangLuong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInBangLuongActionPerformed(evt);
			}

			private void btnInBangLuongActionPerformed(java.awt.event.ActionEvent evt) {

				
				
				
				String thangTinhLuong = cmbThang.getSelectedItem().toString().trim();
				String namTinhLuong = cmbNam.getSelectedItem().toString().trim();
				String toNhom = cmbTo.getSelectedItem().toString().trim();
				
				if(!thangTinhLuong.equals("All") || !namTinhLuong.equals("All")) {
				InBangLuongCongNhan inBangLuongUI = new InBangLuongCongNhan(thangTinhLuong, namTinhLuong, toNhom);
				JDialog inBangLuong = new JDialog();
				inBangLuong.add(inBangLuongUI);
				inBangLuong.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				inBangLuong.setSize(900, 700);
				inBangLuong.setLocationRelativeTo(null);
				inBangLuong.setVisible(true);

				}
				
				else {
					JOptionPane.showMessageDialog(null, "Chọn tháng năm cần in bảng lương");
				}
		}
		}

		);

		btnXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXemChiTietActionPerformed(evt);
			}

			private void btnXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {

				String maCongNhan = txtMaCN.getText();
				if (!cmbThang.getSelectedItem().toString().equals("All")
						&& !cmbNam.getSelectedItem().toString().equals("All")) {
					if (!maCongNhan.equals("")) {

						int thangTinhLuong = Integer.parseInt(cmbThang.getSelectedItem().toString());
						int namTinhLuong = Integer.parseInt(cmbNam.getSelectedItem().toString());
						String toNhom = cmbTo.getSelectedItem().toString().trim();

						ChiTietBangLuongCongNhan chiTietBangLuongCongNhan = new ChiTietBangLuongCongNhan(thangTinhLuong,
								namTinhLuong, maCongNhan);
						JDialog chiTietLuong = new JDialog();
						chiTietLuong.add(chiTietBangLuongCongNhan);
						chiTietLuong.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
						chiTietLuong.setSize(1148, 638);
						chiTietLuong.setLocationRelativeTo(null);
						chiTietLuong.setVisible(true);

					}
					
					else {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn công nhân cần xem chi tiết");
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng tháng năm để xem chi tiết");
				}
				//

			}// GEN-LAST:event_btnInBangLuongMouseClicked
		});

		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					List<List<String>> dsTimKiem = layDanhSachBangLuongTimKiem();
					xoaRong();
					if (dsTimKiem.size() != 0) {

						updateModelTable(HEADERS, dsTimKiem);

						tblQuanLiLuongCN.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								String item = null;
								item = (String) tblQuanLiLuongCN.getModel()
										.getValueAt(tblQuanLiLuongCN.getSelectedRow(), 0);
								if (item != null) {
									BangLuongCongNhan bangLuongCongNhan = null;
									try {
										if (!trangThaiThem) {

											fillThongTinBangLuong(dsTimKiem, bangLuongCongNhan,
													tblQuanLiLuongCN.getSelectedRow());
										}
									} catch (Exception e1) {
										e1.printStackTrace();
									}

								}
							}
						});

						//

					} else
						JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên nào");

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}

			private List<List<String>> layDanhSachBangLuongTimKiem() {
				String maCongNhan = txtMaCN.getText().isEmpty() ? null : txtMaCN.getText();
				String tenCongNhan = txtHoTen.getText().trim().isEmpty() ? null : txtHoTen.getText().trim();
				// String maBangLuong = txtMaLuong.getText().isEmpty() ? null :
				// txtMaLuong.getText().trim();
				String tenCongDoan = txtTenCongDoan.getText().trim().isEmpty() ? null : txtTenCongDoan.getText().trim();
				String soLuongSP = txtSoLuongSP.getText().trim().isEmpty() ? null : txtSoLuongSP.getText().trim();
				String luong = txtLuong.getText().trim().isEmpty() ? null : txtLuong.getText().trim();
				String toNhom = cmbTo.getSelectedItem().toString().trim();
				String thang = cmbThang.getSelectedItem().toString().trim();
				String nam = cmbNam.getSelectedItem().toString().trim();

				try {
					//

					List<List<String>> dsTimKiem = bangLuongCongNhanDAO.timKiemBangLuongCongNhan(maCongNhan,
							tenCongNhan, tenCongDoan, soLuongSP, luong, toNhom, thang, nam);

					return dsTimKiem;
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
				xoaRong();
				fillDuLieuVaoTextField();

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
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTimKiem = new javax.swing.JButton();
        lblMaCN = new javax.swing.JLabel();
        txtMaCN = new javax.swing.JTextField();
        jLabel96 = new javax.swing.JLabel();
        lblSoLuongSP = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        lblLuong = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        cmbThang = new javax.swing.JComboBox<>();
        cmbNam = new javax.swing.JComboBox<>();
        btnTinhLuong = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuongSP = new javax.swing.JTextField();
        txtTenCongDoan = new javax.swing.JTextField();
        lblTenCongDoan = new javax.swing.JLabel();
        btnXoaRong = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cmbTo = new javax.swing.JComboBox<>();
        btnInBangLuong = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblQuanLiLuongCN = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnXemChiTiet = new javax.swing.JButton();
        btnHuongDan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        lblMaCN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCN.setText("Mã công nhân:");

        txtMaCN.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCN.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        jLabel96.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel96.setText("Tên công nhân:");

        lblSoLuongSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongSP.setText("Số lượng hoàn thành:");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtHoTen.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLuong.setText("Tổng tiền:");

        txtLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtLuong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        cmbThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cmbThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbThangActionPerformed(evt);
            }
        });

        cmbNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "2020", "2021", "2022", "2023" }));

        btnTinhLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTinhLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_tinhluong.png"))); // NOI18N
        btnTinhLuong.setText("Tính lương");
        btnTinhLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("QUẢN LÝ LƯƠNG CÔNG NHÂN");

        txtSoLuongSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongSP.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongDoan.setText("Tên công đoạn:");

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaRongActionPerformed(evt);
            }
        });

        cmbTo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Tổ áo khoác", "Tổ áo thun", "Tổ áo sơ mi", "Tổ quần tây", "Tổ đồ bộ" }));
        cmbTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbToActionPerformed(evt);
            }
        });

        btnInBangLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnInBangLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_printbangluong.png"))); // NOI18N
        btnInBangLuong.setText("In bảng lương");
        btnInBangLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnInBangLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInBangLuongActionPerformed(evt);
            }
        });

        jScrollPane8.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lương theo công đoạn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16))); // NOI18N

        tblQuanLiLuongCN.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblQuanLiLuongCN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã công nhân", "Tên công nhân", "Tên sản phẩm", "Tên công đoạn", "Số lượng hoàn thành", "Tổng tiền", "Tiền thưởng chuyên cần"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLiLuongCN.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tblQuanLiLuongCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQuanLiLuongCNMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblQuanLiLuongCN);
        if (tblQuanLiLuongCN.getColumnModel().getColumnCount() > 0) {
            tblQuanLiLuongCN.getColumnModel().getColumn(0).setResizable(false);
            tblQuanLiLuongCN.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tháng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Năm");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Tổ");

        btnXemChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXemChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_printbangluong.png"))); // NOI18N
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnHuongDan.setText("?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMaCN)
                        .addGap(23, 23, 23)
                        .addComponent(txtMaCN, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(lblSoLuongSP)
                        .addGap(8, 8, 8)
                        .addComponent(txtSoLuongSP, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addGap(11, 11, 11)
                        .addComponent(cmbThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addGap(12, 12, 12)
                        .addComponent(cmbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel96)
                        .addGap(20, 20, 20)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(lblLuong)
                        .addGap(101, 101, 101)
                        .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTenCongDoan)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(363, 363, 363)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(btnTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnInBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnXemChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 14, Short.MAX_VALUE))
            .addComponent(jScrollPane8)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lblMaCN))
                            .addComponent(txtMaCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSoLuongSP)
                            .addComponent(txtSoLuongSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(cmbTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(cmbThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(cmbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel96))
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLuong)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(lblTenCongDoan))
                            .addComponent(txtTenCongDoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1))
                    .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnInBangLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTinhLuong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaRong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	private void tblQuanLiLuongCNMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblQuanLiLuongCNMouseClicked
		// // TODO add your handling code here:
	}// GEN-LAST:event_tblQuanLiLuongCNMouseClicked

	private void btnInBangLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInBangLuongActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnInBangLuongActionPerformed

	private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTimKiemActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnTimKiemActionPerformed

	private void cmbThangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbThangActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_cmbThangActionPerformed

	private void btnXoaRongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaRongActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnXoaRongActionPerformed

	private void cmbToActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbToActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_cmbToActionPerformed

	// private void btnTinhLuongActionPerformed(java.awt.event.ActionEvent evt) {//
	// GEN-FIRST:event_btnTinhLuongActionPerformed
	// // TODO add your handling code here:
	// }// GEN-LAST:event_btnTinhLuongActionPerformed

	// private void btnInBangLuongActionPerformed(java.awt.event.ActionEvent evt)
	// {// GEN-FIRST:event_btnInBangLuongActionPerformed
	//
	// }// GEN-LAST:event_btnInBangLuongActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnInBangLuong;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTinhLuong;
    private javax.swing.JButton btnXemChiTiet;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JComboBox<String> cmbNam;
    private javax.swing.JComboBox<String> cmbThang;
    private javax.swing.JComboBox<String> cmbTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblLuong;
    private javax.swing.JLabel lblMaCN;
    private javax.swing.JLabel lblSoLuongSP;
    private javax.swing.JLabel lblTenCongDoan;
    private javax.swing.JTable tblQuanLiLuongCN;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaCN;
    private javax.swing.JTextField txtSoLuongSP;
    private javax.swing.JTextField txtTenCongDoan;
    // End of variables declaration//GEN-END:variables
}
