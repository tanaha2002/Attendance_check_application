/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
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

import dao.BangChamCongNhanVienDAO;
import dao.BangLuongNhanVienDAO;
import dao.LuuFile;
import entity.BangChamCongNhanVien;
import entity.BangLuongCongNhan;
import entity.BangLuongNhanVien;
import entity.CongNhan;
import entity.NhanVien;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import tablemodels.BangLuongCongNhanTableModel;
import tablemodels.BangLuongNhanVienTableModel;
import tablemodels.CongNhanTableModel;

/**
 *
 *
 */
public class QuanLyLuongNV extends javax.swing.JPanel {

	/**
	 * Creates new form QuanLyLuongNV
	 */
	private static final String[] HEADERS = { "Mã bảng lương", "Mã nhân viên", "Họ tên", "Vai trò", "Hệ số lương",
			"Lương cơ bản", "Phụ cấp", "Số phép được nghỉ","Ngày lập bảng lương", "Tổng lương" };
	private NhanVien nhanVien;
	private BangLuongNhanVienDAO bangLuongNhanVienDAO;
	private BangLuongNhanVienTableModel blNVTableModel;
	private List<BangLuongNhanVien> dsBLNV;
	private boolean trangThaiThem;
	private BangLuongNhanVienIn inBangLuongUI;
	private List<BangLuongNhanVien> dsTinhLuong;
	private List<List<String>> dsInBangLuong;
	private boolean tinhLuong;
	private List<String> tongLuong;
	private int thangTinhLuong;
	private int namTinhLuong;
	private List<BangChamCongNhanVien> dsBangChamCongNhanVien;

	public QuanLyLuongNV() {
		dsBangChamCongNhanVien = new ArrayList<>();
		nhanVien = new NhanVien();
		tongLuong = new ArrayList<>();
		tblQuanLiLuongNV = new JTable();
		bangLuongNhanVienDAO = new BangLuongNhanVienDAO();
		dsBLNV = new ArrayList<>();
		trangThaiThem = false;
		tinhLuong = false;
		dsTinhLuong = new ArrayList<>();
		tblQuanLiLuongNV.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 15));
		initComponents();
		themSuKien();

	}

	private void updateModelTable(List<BangLuongNhanVien> dsCN, String[] headers, boolean tinhLuong) {
		blNVTableModel = new BangLuongNhanVienTableModel(headers, dsCN, tinhLuong, thangTinhLuong, namTinhLuong);
		tblQuanLiLuongNV.setModel(blNVTableModel);
		tblQuanLiLuongNV.updateUI();
	}

	private void xoaRong() {
		txtHoTen.setText("");
		txtHeSoLuong.setText("");
		txtLuong.setText("");
		txtMaLuong.setText("");
		txtPhuCap.setText("");

		txtVaiTro.setText("");
		
		txtMaNV.setText("");

	}

	private void fillDuLieuVaoTextfield() {
		txtMaNV.setEditable(true);
		txtMaLuong.setEditable(true);
		txtHoTen.setEditable(true);
		txtLuong.setEditable(false);
		txtHeSoLuong.setEditable(true);
		txtPhuCap.setEditable(false);
		txtVaiTro.setEditable(true);
		
	}

	private void tatDuLieuVaoTextfield() {
		txtMaNV.setEditable(false);
		txtMaLuong.setEditable(false);
		txtHoTen.setEditable(false);
		txtLuong.setEditable(false);
		txtHeSoLuong.setEditable(false);
		txtPhuCap.setEditable(false);
		txtVaiTro.setEditable(false);
		
	}

	private void comBoBoxYear() {
		cmbNam.removeAllItems();
		int currentYear = LocalDate.now().getYear();
		for (int i = 0; i < 4; i++) {
			cmbNam.addItem(Integer.toString(currentYear - i));
		}
	}
	private void themSuKien() {
		cmbThang.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		comBoBoxYear();
		//		dsBLNV = bangLuongNhanVienDAO.layDanhSachBangLuongNV();
		//		updateModelTable(dsBLNV, HEADERS, false);
		//		if (tblQuanLiLuongNV.getColumnModel().getColumnCount() > 0) {
		//			tblQuanLiLuongNV.getColumnModel().getColumn(3).setMinWidth(320);
		//		}
		tblQuanLiLuongNV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String item = (String) tblQuanLiLuongNV.getModel().getValueAt(tblQuanLiLuongNV.getSelectedRow(), 0);
				if (item != null) {
					BangLuongNhanVien bangLuongNhanVien = null;
					try {
						if (!trangThaiThem) {

							bangLuongNhanVien = bangLuongNhanVienDAO.timBangLuongNhanVien(item);
							fillThongTinBangLuong(bangLuongNhanVien);
							
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}

			private void fillThongTinBangLuong(BangLuongNhanVien bangLuongNhanVien) throws Exception {
				tatDuLieuVaoTextfield();
				String heSoLuong = Double.toString(bangLuongNhanVien.getHeSoLuong());
				String phuCap = Double.toString(bangLuongNhanVien.getPhuCap());
				String luongCoBan = Double.toString(bangLuongNhanVien.getLuongCoBan());
				double luong = Double.parseDouble(luongCoBan);
				NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
				luongCoBan = formatter.format(luong);
				txtHoTen.setText(bangLuongNhanVien.getNhanVien().getTenNhanVien());
				txtHeSoLuong.setText(heSoLuong);
				txtLuong.setText(luongCoBan);
				txtMaLuong.setText(bangLuongNhanVien.getMaBangLuong());
				txtPhuCap.setText(phuCap);
				
				txtVaiTro.setText(bangLuongNhanVien.getNhanVien().getVaiTro());
				
				
				txtMaNV.setText(bangLuongNhanVien.getNhanVien().getMaNhanVien());

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
			        ImageIcon imageIcon = new ImageIcon(setting.PathSetting.PATH_IMAGE_HUONGDAN + "TinhLuongNV.jpg");
			        
			        JPanel imagePanel = new JPanel();
			        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
			        
			        JLabel imageLabel1 = new JLabel(imageIcon);
			        imagePanel.add(imageLabel1);
			        
			        JScrollPane scrollPane = new JScrollPane(imagePanel);
			        scrollPane.setPreferredSize(new Dimension(1200, 700));
			        
			        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
			        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
			        
			        JOptionPane.showMessageDialog(null, scrollPane, "Hướng dẫn tính lương nhân viên", JOptionPane.PLAIN_MESSAGE);
				}
			});
		
		btnTinhLuong.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				
				
				 int chonLua = JOptionPane.showConfirmDialog(null, "Lưu ý: nút tính lương chỉ được bấm 1 lần duy nhất trong tháng và nên là ngày"
					  		+ "cuối cùng trong tháng hoặc là ngày 5 tháng tiếp theo", "Xác nhận bấm nút tính lương", JOptionPane.YES_NO_OPTION);

				        if (chonLua == JOptionPane.YES_OPTION) {
					
				List<BangLuongNhanVien> dsTinhLuong = new ArrayList<>();
				List<BangChamCongNhanVien> dsChamCongNhanVien = new ArrayList<>();
				BangLuongNhanVienDAO bangLuongNhanVienDAO = new BangLuongNhanVienDAO();
				
				
				
				thangTinhLuong = Integer.parseInt(cmbThang.getSelectedItem().toString());
				namTinhLuong = Integer.parseInt(cmbNam.getSelectedItem().toString());
				
				
				
				BangChamCongNhanVienDAO bangChamCongNhanVienDAO = new BangChamCongNhanVienDAO();
				//				
				dsBangChamCongNhanVien = bangChamCongNhanVienDAO.layDanhSachCCNV();

				List<BangLuongNhanVien> dsBangLuongNhanVien = new ArrayList<>();
				try {
					dsBangLuongNhanVien = bangLuongNhanVienDAO.layDanhSachTinhLuong(thangTinhLuong, namTinhLuong);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				List<BangChamCongNhanVien> dsChamCong = bangChamCongNhanVienDAO.layDanhSachCCNV(thangTinhLuong, namTinhLuong);
//				System.out.println(dsChamCong);

				try {
					List<BangLuongNhanVien> dsLuongHienTai = bangLuongNhanVienDAO.layDanhSachTinhLuong(thangTinhLuong,
							namTinhLuong);

					for (BangChamCongNhanVien bangChamCong : dsChamCong) {
						boolean exists = false;
						int thangChamCong = bangChamCong.getNgayChamCong().getMonth() + 1;
						int namChamCong = bangChamCong.getNgayChamCong().getYear() + 1900;
						try {
							
							//lay ds luong hien tai
							for (BangLuongNhanVien blNV : dsLuongHienTai) {
								if (blNV.getNhanVien().getMaNhanVien()
										.equals(bangChamCong.getNhanVien().getMaNhanVien())) {
									bangLuongNhanVienDAO.tinhTienLamThem(blNV.getNhanVien().getMaNhanVien(), 
											thangTinhLuong, namTinhLuong);
									exists = true;
									break;
								}
							}
							if (!exists) {
								BangLuongNhanVien bangLuongNhanVien = new BangLuongNhanVien();
								bangLuongNhanVien = bangLuongNhanVienDAO.themBangLuongNhanVien(
										bangChamCong.getNhanVien().getMaNhanVien(), thangChamCong, namChamCong);
								dsLuongHienTai = bangLuongNhanVienDAO.layDanhSachTinhLuong(
										thangTinhLuong,namTinhLuong);

							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				List<BangLuongNhanVien> dsHienThi;
				try {
					dsHienThi = bangLuongNhanVienDAO.layDanhSachTinhLuong(thangTinhLuong, namTinhLuong);
					if(dsHienThi.size() == 0) {
						JOptionPane.showMessageDialog(null, "Không có ai được chấm công trong tháng này");
					}
					updateModelTable(dsHienThi, HEADERS, true);

					int rowIndex = 0;
					int colIndex = 9;

					for (BangLuongNhanVien bl : dsHienThi) {
						String temp = tblQuanLiLuongNV.getModel().getValueAt(rowIndex, colIndex).toString();
						tongLuong.add(temp);
						//						System.out.println(tongLuong);
						rowIndex += 1;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//						updateModelTable(dsBLNV, HEADERS, true);

				tblQuanLiLuongNV.repaint();
				tblQuanLiLuongNV.revalidate();

				//				
			}
			}
		});

		btnInBangLuong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnInBangLuongActionPerformed(evt);
			}

			
			private void btnInBangLuongActionPerformed(java.awt.event.ActionEvent evt) {
				Date ngayIn = new java.sql.Date(new java.util.Date().getTime());
				SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
				String ngayInLuong = dt.format(ngayIn);
				String thang = cmbThang.getSelectedItem().toString();
				String nam = cmbNam.getSelectedItem().toString();

				
				String outputFilePath = null;
				try {

					JFileChooser luuFile = new JFileChooser();
					luuFile.setDialogTitle("Chọn thư mục lưu");

					int luaChon = luuFile.showSaveDialog(null);

					if (luaChon == JFileChooser.APPROVE_OPTION) {
						String filePath = luuFile.getSelectedFile().getAbsolutePath(); 

						outputFilePath = filePath + ".pdf"; 
						
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				LuuFile luuFile = new LuuFile();
				
				if(luuFile.InBangLuong(outputFilePath, thang, nam, "NV", tblQuanLiLuongNV)) {
			        JOptionPane.showMessageDialog(null, "In thành công");

				}
			}
		}

			
			);
		btnTimKiem.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				
				thangTinhLuong = Integer.parseInt(cmbThang.getSelectedItem().toString());
				namTinhLuong = Integer.parseInt(cmbNam.getSelectedItem().toString());
				fillDuLieuVaoTextfield();

				//				List<BangLuongCongNhan> dsBangLuong = new ArrayList<>();
				try {

					List<BangLuongNhanVien> dsBangLuong = layDanhSachBangLuongTimKiem();
					xoaRong();
					if (dsBangLuong.size() != 0) {
						//						


						updateModelTable(dsBangLuong, HEADERS, true);
						int rowIndex = 0;
						int colIndex = 9;

						for (BangLuongNhanVien bl : dsBangLuong) {
							String temp = tblQuanLiLuongNV.getModel().getValueAt(rowIndex, colIndex).toString();
							tongLuong.add(temp);
							//						System.out.println(tongLuong);
							rowIndex += 1;
						}
						//			

					} else
						JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên nào");

				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}
			private List<BangLuongNhanVien> layDanhSachBangLuongTimKiem() {
				String maNhanVien = txtMaNV.getText().isEmpty() ? null : txtMaNV.getText();
				String maBangLuong = txtMaLuong.getText().isEmpty() ? null : txtMaLuong.getText();
				String tenNhanVien = txtHoTen.getText().trim().isEmpty() ? null : txtHoTen.getText().trim();
				String vaiTro= txtVaiTro.getText().isEmpty() ? null : txtVaiTro.getText().trim();
				String phuCap = txtPhuCap.getText().isEmpty() ? null : txtPhuCap.getText().trim();

				String heSoLuong = txtHeSoLuong.getText().isEmpty() ? null : txtHeSoLuong.getText().trim();
				String luong = txtLuong.getText().trim().isEmpty() ? null : txtLuong.getText().trim();
				String thang = ((String) cmbThang.getSelectedItem()).isEmpty() ? null : (String) cmbThang.getSelectedItem();
				String nam = ((String) cmbNam.getSelectedItem()).isEmpty() ? null : (String) cmbNam.getSelectedItem();

				try {
					//					
					List<BangLuongNhanVien> dsTimKiem = bangLuongNhanVienDAO.timKiemNhieu(maNhanVien, tenNhanVien, maBangLuong, vaiTro,
							heSoLuong, luong, thang, nam, phuCap);
					
				
					return dsTimKiem;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}

		});
		
		btnXoaRong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				xoaRong();
			}
		});
		
		btnXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXemChiTietActionPerformed(evt);
			}
			private void btnXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {
				
				String maNhanVien = txtMaNV.getText();
				if(!maNhanVien.equals("")) {
					
				
				int thangTinhLuong = Integer.parseInt(cmbThang.getSelectedItem().toString());
				int namTinhLuong = Integer.parseInt(cmbNam.getSelectedItem().toString());
                
                
                
				ChiTietBangLuongNhanVien chiTietBangLuongNhanVien = new ChiTietBangLuongNhanVien(thangTinhLuong,namTinhLuong,maNhanVien);
				JDialog chiTietLuong = new JDialog();
				chiTietLuong.add(chiTietBangLuongNhanVien);
				chiTietLuong.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				chiTietLuong.setSize(1320, 638);
				chiTietLuong.setLocationRelativeTo(null);
				chiTietLuong.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xem chi tiết");
				}
				
		//

			}// GEN-LAST:event_btnInBangLuongMouseClicked
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

        jScrollPane7 = new javax.swing.JScrollPane();
        tblQuanLiLuongNV = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        btnInBangLuong = new javax.swing.JButton();
        lblHoTen = new javax.swing.JLabel();
        txtMaLuong = new javax.swing.JTextField();
        lblHeSoLuong = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        txtHeSoLuong = new javax.swing.JTextField();
        lblLuong = new javax.swing.JLabel();
        txtLuong = new javax.swing.JTextField();
        cmbThang = new javax.swing.JComboBox<>();
        btnTinhLuong = new javax.swing.JButton();
        lblMaLuong = new javax.swing.JLabel();
        txtPhuCap = new javax.swing.JTextField();
        lblPhuCap = new javax.swing.JLabel();
        btnXoaRong = new javax.swing.JButton();
        btnTimKiem = new javax.swing.JButton();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtVaiTro = new javax.swing.JTextField();
        lblVaiTro = new javax.swing.JLabel();
        cmbNam = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnXemChiTiet = new javax.swing.JButton();
        btnHuongDan = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));

        tblQuanLiLuongNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã bảng lương", "Mã nhân viên", "Họ tên", "Vai trò", "Hệ số lương", "Lương cơ bản", "Phụ cấp", "Số phép được nghỉ", "Tổng lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQuanLiLuongNV.setFillsViewportHeight(true);
        tblQuanLiLuongNV.setFocusable(false);
        tblQuanLiLuongNV.setRequestFocusEnabled(false);
        tblQuanLiLuongNV.setRowHeight(30);
        tblQuanLiLuongNV.setSelectionBackground(new java.awt.Color(0, 153, 255));
        tblQuanLiLuongNV.setSurrendersFocusOnKeystroke(true);
        jScrollPane7.setViewportView(tblQuanLiLuongNV);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("QUẢN LÝ LƯƠNG NHÂN VIÊN");

        btnInBangLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnInBangLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_printbangluong.png"))); // NOI18N
        btnInBangLuong.setText("In bảng lương");
        btnInBangLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHoTen.setText("Họ tên:");

        txtMaLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaLuong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblHeSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblHeSoLuong.setText("Hệ số lương:");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtHoTen.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtHeSoLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtHeSoLuong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblLuong.setText("Lương:");

        txtLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtLuong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        cmbThang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        cmbThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbThangActionPerformed(evt);
            }
        });

        btnTinhLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTinhLuong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_tinhluong.png"))); // NOI18N
        btnTinhLuong.setText("Tính lương");
        btnTinhLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTinhLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTinhLuongActionPerformed(evt);
            }
        });

        lblMaLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaLuong.setText("Mã lương:");

        txtPhuCap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPhuCap.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblPhuCap.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPhuCap.setText("Phụ cấp:");

        btnXoaRong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXoaRong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eraser.png"))); // NOI18N
        btnXoaRong.setText("Xóa rỗng");
        btnXoaRong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXoaRong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaRongActionPerformed(evt);
            }
        });

        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaNV.setText("Mã nhân viên:");

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaNV.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        txtVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtVaiTro.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblVaiTro.setText("Vai trò:");

        cmbNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2021", "2022", "2023" }));
        cmbNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNamActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Năm");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tháng");

        btnXemChiTiet.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnXemChiTiet.setText("Xem chi tiết");
        btnXemChiTiet.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemChiTietActionPerformed(evt);
            }
        });

        btnHuongDan.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnHuongDan.setText("?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblHoTen)
                        .addGap(81, 81, 81)
                        .addComponent(txtHoTen))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaLuong)
                            .addComponent(lblVaiTro))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVaiTro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMaNV)
                        .addGap(27, 27, 27)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHeSoLuong)
                                    .addComponent(lblPhuCap))
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtHeSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                    .addComponent(txtPhuCap)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblLuong)
                                .addGap(91, 91, 91)
                                .addComponent(txtLuong)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cmbThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(cmbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(435, 435, 435)
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnXemChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(btnTinhLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(btnInBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane7)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblHeSoLuong)
                            .addComponent(txtHeSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaNV)
                            .addComponent(cmbThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaLuong)
                            .addComponent(txtMaLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPhuCap)
                            .addComponent(txtPhuCap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblHoTen))
                            .addComponent(lblLuong)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnInBangLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblVaiTro)
                            .addComponent(txtVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnTinhLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnXoaRong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnXemChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemChiTietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXemChiTietActionPerformed

	private void cmbThangActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbThangActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_cmbThangActionPerformed

	private void btnTinhLuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTinhLuongActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnTinhLuongActionPerformed

	private void btnXoaRongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXoaRongActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_btnXoaRongActionPerformed

	private void cmbNamActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cmbNamActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_cmbNamActionPerformed

	//    private void btnInBangLuongMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnInBangLuongMouseClicked
	//        JDialog inBangLuong = new JDialog();
	//        inBangLuong.add(new InBangLuongNhanVien(tongLuong));
	//        inBangLuong.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
	//        inBangLuong.setSize(900, 700);
	//        inBangLuong.setLocationRelativeTo(null);
	//        inBangLuong.setVisible(true);
	//        inBangLuongUI = new InBangLuongNhanVien(tongLuong);
	//
	//    }// GEN-LAST:event_btnInBangLuongMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuongDan;
    private javax.swing.JButton btnInBangLuong;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnTinhLuong;
    private javax.swing.JButton btnXemChiTiet;
    private javax.swing.JButton btnXoaRong;
    private javax.swing.JComboBox<String> cmbNam;
    private javax.swing.JComboBox<String> cmbThang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblHeSoLuong;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblLuong;
    private javax.swing.JLabel lblMaLuong;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblPhuCap;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JTable tblQuanLiLuongNV;
    private javax.swing.JTextField txtHeSoLuong;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaLuong;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtPhuCap;
    private javax.swing.JTextField txtVaiTro;
    // End of variables declaration//GEN-END:variables
}
