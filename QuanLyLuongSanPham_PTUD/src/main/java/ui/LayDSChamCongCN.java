/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import dao.CongDoanSanPhamDAO;
import dao.NhanVienDAO;
import dao.SanPhamDAO;
import entity.BangChamCongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongDoanSanPham;
import entity.NhanVien;
import tablemodels.BangPhanCongCongNhanTableModel;
import tablemodels.LayDanhSachChamCongTableModel;

/**
 *
 * @author Hoang Truong
 */
public class LayDSChamCongCN extends javax.swing.JPanel {

	/**
	 * Creates new form LayDSChamCongCN
	 */
	private Consumer<List<BangChamCongCongNhan>> callback;
	private List<BangChamCongCongNhan> dsCCCN;
	private List<BangPhanCongCongNhan> dsPhanCong;
	private LayDanhSachChamCongTableModel phanCongTableModel;
	private BangChamCongCongNhanDAO chamCongCongNhanDAO;
	private BangPhanCongCongNhanDAO phanCongCongNhanDAO;
	private NhanVienDAO nhanVienDAO;
	private NhanVien nhanVienLogin;
	private CongDoanSanPhamDAO congDoanDAO;
	private SanPhamDAO sanPhamDAO;
	private String maSanPham;
	private String tenSanPham;
	private String maCongDoan;
	private String tenCongDoan;
	private String caLam;
	private int demChamCong;
	private Date ngayChamCong;
	
	private static final String[] HEADERS = { "STT","Mã phân công", "Mã công nhân", "Tên công nhân", "Có mặt",
			"Vắng mặt (Có phép)","Ca làm", "Số lượng","Tiền công đoạn (VNĐ)","Tổng Tiền công (VNĐ)"};

	public LayDSChamCongCN(List<BangPhanCongCongNhan> dsPhanCong, List<BangChamCongCongNhan> dsCCCN, NhanVien nvLogin,
			Consumer<List<BangChamCongCongNhan>> callback, String maSanPham, String tenSanPham, String maCongDoan,
			String tenCongDoan, Date ngayChamCong) {
		this.dsPhanCong = new ArrayList<>();
		this.ngayChamCong = ngayChamCong;
		
		this.dsPhanCong = dsPhanCong;
		this.dsCCCN = dsCCCN;
		this.callback = callback;
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.maCongDoan = maCongDoan;
		this.tenCongDoan = tenCongDoan;
		this.chamCongCongNhanDAO = new BangChamCongCongNhanDAO();
		this.phanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		this.nhanVienDAO = new NhanVienDAO();
		this.nhanVienLogin = nvLogin;
		this.congDoanDAO = new CongDoanSanPhamDAO();
		this.sanPhamDAO = new SanPhamDAO();
		initComponents();
		tatChamCong();
		themSuKien();
	}
	private void ngayChuNhat() {
	//chủ nhật trong jdatechooser là 0
	int ngayChuNhat = dtNgayChamCong.getDate().getDay();
	if(ngayChuNhat == 0) {
		System.out.println("Ngày Chủ Nhật");
	}
}
	private void updatePhanCongTableModel(List<BangPhanCongCongNhan> dsPhanCong, String[] headers) {
		phanCongTableModel = new LayDanhSachChamCongTableModel(headers, dsPhanCong);
		tblPhanCongCongNhan.setModel(phanCongTableModel);
		tblPhanCongCongNhan.updateUI();
	}

	private void batChamCong() {
		dtNgayChamCong.setEnabled(false);
		btnChamCong1.setEnabled(true);
		cmbCaLam.setEnabled(true);
		
		
	}

	private void tatChamCong() {
		dtNgayChamCong.setEnabled(false);
		btnChamCong1.setEnabled(false);
		cmbCaLam.setEnabled(true);
		
	}

	private String maChamCongMoi(String maChamCong) {

		int number = Integer.parseInt(maChamCong.substring(4));
		number++;
		String maChamCongMoi = String.format("CCCN%04d", number);
		// System.out.println(maChamCongMoi);
		return maChamCongMoi;
	}


	private boolean kiemTraThongTinChamCong(String soLuongLam) {
		boolean kt = true;
		
		
		String maPhanCong = (String) tblPhanCongCongNhan.getModel().getValueAt(tblPhanCongCongNhan.getSelectedRow(), 1);
		int slCongDoanDaHoanThanh = chamCongCongNhanDAO.getSLHoanThanhCongDoanCuaCN(maPhanCong);

		int slDuocPhanCong = phanCongCongNhanDAO.timPhanCongCongNhan(maPhanCong).getSoLuongPhanCong();

		int slCongDoanChuaDuocLam = slDuocPhanCong - slCongDoanDaHoanThanh;
		Date date = new Date();

		if (soLuongLam.equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(null, "Chưa nhập số lượng làm được");
			kt = false;
		} else if (!soLuongLam.matches("[\\d+\\s]*")) {
			JOptionPane.showMessageDialog(null, "Chỉ được nhập số nguyên dương");
			kt = false;
		} else if (Integer.parseInt(soLuongLam) > slCongDoanChuaDuocLam) {
			JOptionPane.showMessageDialog(null, "Không nhập lớn hơn số lượng phân công: " + slCongDoanChuaDuocLam);

			kt = false;
		 
		} 

		return kt;
	}
	private void capNhatTienCong(double giaTien,int soLuongLamDuoc,int row) {
		double tienCong = chamCongCongNhanDAO.tinhTienChamCong(giaTien, dsPhanCong.get(row).getCaLam(), soLuongLamDuoc);
		if(dsPhanCong.get(row).getCaLam().equalsIgnoreCase("Tối") || dsPhanCong.get(row).getCaLam().equalsIgnoreCase("Sáng CN") || dsPhanCong.get(row).getCaLam().equalsIgnoreCase("Chiều CN")) {
        	
        	dsPhanCong.get(row).setTienCong(tienCong*1.5);
        }
        else if (dsPhanCong.get(row).getCaLam().equalsIgnoreCase("Sáng") || dsPhanCong.get(row).getCaLam().equalsIgnoreCase("Chiều")) {
        	dsPhanCong.get(row).setTienCong(tienCong);

        }
        else {
        	dsPhanCong.get(row).setTienCong(0);
        }
	}
	private BangChamCongCongNhan layDuLieuChamCongCN(int i, String maPhanCong) {
		
		String caLam = dsPhanCong.get(i).getCaLam();							
        String trangThaiChamCong = dsPhanCong.get(i).getTrangThai() == true? "Có mặt" : "Vắng mặt";
		int soLuongLamDuoc = dsPhanCong.get(i).getSoLuongLamDuoc();
		BangPhanCongCongNhan thongTinPhanCong = phanCongCongNhanDAO.timPhanCongCongNhan(maPhanCong);
		String maChamCongMoi = maChamCongMoi(chamCongCongNhanDAO.layMaChamCongCaoNhat());
		BangChamCongCongNhan thongTinChamCong = new BangChamCongCongNhan(maChamCongMoi,
				dtNgayChamCong.getDate(), caLam, trangThaiChamCong, soLuongLamDuoc, thongTinPhanCong,
				nhanVienLogin);
		return thongTinChamCong;
	}

	
	//kiểm tra công nhân đã được chấm ca này hay chưa
	private boolean kiemTraChamCong(int row) {
		 List<BangChamCongCongNhan> dsCCHomNay = chamCongCongNhanDAO.layDanhSachChamCongHomNay(dtNgayChamCong.getDate());
         BangChamCongCongNhan congNhanHienTai = layDuLieuChamCongCN(row,(String) tblPhanCongCongNhan.getModel().getValueAt(row, 1));
         System.out.println(congNhanHienTai.getCaLam());
         System.out.println(congNhanHienTai.getTrangthai());
         System.out.println(congNhanHienTai.getPhanCong().getCongNhan().getMaCongNhan());
         
         for(BangChamCongCongNhan congNhanChamCong : dsCCHomNay) {
        	 if(congNhanChamCong.getCaLam().equalsIgnoreCase(congNhanHienTai.getCaLam())
        			 && congNhanHienTai.getPhanCong().getCongNhan().getMaCongNhan().equalsIgnoreCase(
        					 congNhanChamCong.getPhanCong().getCongNhan().getMaCongNhan())) {
        		 JOptionPane.showMessageDialog(null, "Công nhân này đã được chấm công ca này trong ngày rồi");
        		 dsPhanCong.get(row).setTrangThai(null);
        		 dsPhanCong.get(row).setSoLuongLamDuoc(0);
        		 dsPhanCong.get(row).setCaLam(null);
        		 dsPhanCong.get(row).setTienCong(0);
        		 return false;
        	 }
         }
         
         return true;
	}
	//xóa công nhân đã chấm công đủ 3 công
	private void kiemTraDuCong() {
	    int demCongNgayThuong = 0;
	    int demCongChuNhat = 0;
	    List<BangChamCongCongNhan> dsCC = chamCongCongNhanDAO.layDanhSachChamCongHomNay(dtNgayChamCong.getDate());
	    List<BangPhanCongCongNhan> phanCongCanXoa = new ArrayList<>(); // Danh sách tạm thời để lưu trữ các phần tử cần xóa

	    for (BangChamCongCongNhan congNhanChamCong : dsCC) {
	        for (BangPhanCongCongNhan congNhanPhanCong : dsPhanCong) {
	            if (dtNgayChamCong.getDate().getDay() == 0) {
	                if (congNhanPhanCong.getCongNhan().getMaCongNhan().equalsIgnoreCase(congNhanChamCong
	                        .getPhanCong().getCongNhan().getMaCongNhan()) && (congNhanChamCong.getCaLam().equalsIgnoreCase("Sáng CN")
	                        || congNhanChamCong.getCaLam().equalsIgnoreCase("Chiều CN"))) {
	                    demCongChuNhat += 1;
	                    if (demCongChuNhat == 2) {
	                        phanCongCanXoa.add(congNhanPhanCong); // Thêm phần tử cần xóa vào danh sách tạm thời
	                    }
	                }
	            } else {
	                if (congNhanPhanCong.getCongNhan().getMaCongNhan().equalsIgnoreCase(congNhanChamCong
	                        .getPhanCong().getCongNhan().getMaCongNhan()) && (congNhanChamCong.getCaLam().equalsIgnoreCase("Sáng")
	                        || congNhanChamCong.getCaLam().equalsIgnoreCase("Chiều")
	                        || congNhanChamCong.getCaLam().equalsIgnoreCase("Tối"))) {
	                    demCongNgayThuong += 1;
	                    if (demCongNgayThuong == 3) {
	                        phanCongCanXoa.add(congNhanPhanCong); // Thêm phần tử cần xóa vào danh sách tạm thời
	                    }
	                }
	            }
	        }
	    }

	    // Xóa các phần tử trong danh sách tạm thời khỏi dsPhanCong
	    dsPhanCong.removeAll(phanCongCanXoa);

	    updatePhanCongTableModel(dsPhanCong, HEADERS);
	}

	private void themSuKien() {

		
		demChamCong = 0;
	
		dtNgayChamCong.setDate(ngayChamCong);
		
		updatePhanCongTableModel(dsPhanCong, HEADERS);
		kiemTraDuCong();
		System.out.println(demChamCong);
		dtNgayChamCong.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				if(dtNgayChamCong.getDate().getDay() == 0) {
					cmbCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sáng CN", "Chiều CN" }));
					cmbCaLam.repaint();
				}
				else {
					cmbCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sáng", "Chiều", "Tối" }));
					cmbCaLam.repaint();
				}
				
			}
		});
		tblPhanCongCongNhan.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	
		        int column = tblPhanCongCongNhan.columnAtPoint(e.getPoint());
		        int row = tblPhanCongCongNhan.rowAtPoint(e.getPoint());
		        if (column == 4 && (dsPhanCong.get(row).getTrangThai() == null || dsPhanCong.get(row).getTrangThai() == false)) {
		        	//kiem tra xem co dang tich hay khong, neu dang tich roi thi khong cong nua
		        	if(dsPhanCong.get(row).getTrangThai() != null) {
		        		demChamCong -=1;
		        	}
		        	dsPhanCong.get(row).setTrangThai(true);
		        	while (true) {
		        	    String soLuongLam = JOptionPane.showInputDialog("Nhập số lượng:");
		        	    if(soLuongLam == null) {
		        	    	break; //nhấn cancel hoặc đóng hộp thoại
		        	    }
		        	    if (kiemTraThongTinChamCong(soLuongLam)) {
		        	    	dsPhanCong.get(row).setSoLuongLamDuoc(Integer.parseInt(soLuongLam));
		        	    	updatePhanCongTableModel(dsPhanCong, HEADERS);
		        	    	break;
		        	    }
		        	}
		        	dsPhanCong.get(row).setCaLam(cmbCaLam.getSelectedItem().toString());
		        	//kiểm tra ca làm trong ngày
		        	if(kiemTraChamCong(row)) {
		        		//tinh tien cong
			        	capNhatTienCong(dsPhanCong.get(row).getCongDoan().getTienCongDoan(),dsPhanCong.get(row).getSoLuongLamDuoc(), row);
			            updatePhanCongTableModel(dsPhanCong, HEADERS);
			            demChamCong +=1;
		        	}
	        		
		        	
		        	

		        } else if (column == 5 && (dsPhanCong.get(row).getTrangThai() == null || dsPhanCong.get(row).getTrangThai() == true)) {
		        	//kiem tra xem co dang tich hay khong, neu dang tich roi thi khong cong nua
		        	if(dsPhanCong.get(row).getTrangThai() != null) {
		        		demChamCong -=1;
		        	}
		        	dsPhanCong.get(row).setTrangThai(false);

		            dsPhanCong.get(row).setSoLuongLamDuoc(0);
		            dsPhanCong.get(row).setCaLam(cmbCaLam.getSelectedItem().toString());
		            //kiểm tra ca làm trong ngày
		            if(kiemTraChamCong(row)) {
		            	 //tinh tien cong
			            capNhatTienCong(dsPhanCong.get(row).getCongDoan().getTienCongDoan(),dsPhanCong.get(row).getSoLuongLamDuoc(), row);
			            updatePhanCongTableModel(dsPhanCong, HEADERS);
		        		demChamCong +=1;
		            }
	            	
		            
		           

		        }
		        //sửa số lượng trên bảng
		        else if (column == 7 && dsPhanCong.get(row).getTrangThai() != null && dsPhanCong.get(row).getTrangThai() == true) {

		        	while (true) {
		        	    String soLuongLam = JOptionPane.showInputDialog("Nhập số lượng:");
		        	    if(soLuongLam == null) {
		        	    	break; //nhấn cancel hoặc đóng hộp thoại
		        	    }
		        	    if (kiemTraThongTinChamCong(soLuongLam)) {
		        	    	dsPhanCong.get(row).setSoLuongLamDuoc(Integer.parseInt(soLuongLam));
		        	    	//cap nhat tien cong
		        	    	capNhatTienCong(dsPhanCong.get(row).getCongDoan().getTienCongDoan(),dsPhanCong.get(row).getSoLuongLamDuoc(), row);

		        	    	updatePhanCongTableModel(dsPhanCong, HEADERS);
		        	    	break;
		        	    }
		        	    
		        	}
		        }
		        else if(column == 4 && dsPhanCong.get(row).getTrangThai() == true) {
		        	dsPhanCong.get(row).setTrangThai(null);
		            dsPhanCong.get(row).setSoLuongLamDuoc(0);
		            dsPhanCong.get(row).setCaLam("");
		            //xoa tien cong
		            capNhatTienCong(dsPhanCong.get(row).getCongDoan().getTienCongDoan(),dsPhanCong.get(row).getSoLuongLamDuoc(), row);
		            updatePhanCongTableModel(dsPhanCong, HEADERS);
		            demChamCong -=1;
		            
		        }
		        else if(column ==5 && dsPhanCong.get(row).getTrangThai() == false) {
		        	dsPhanCong.get(row).setTrangThai(null);
		            dsPhanCong.get(row).setSoLuongLamDuoc(0);
		            dsPhanCong.get(row).setCaLam("");
		            //xoa tien cong
		            capNhatTienCong(dsPhanCong.get(row).getCongDoan().getTienCongDoan(),dsPhanCong.get(row).getSoLuongLamDuoc(), row);

		            updatePhanCongTableModel(dsPhanCong, HEADERS);
		            demChamCong -=1;
		        }
		        
	        	tblPhanCongCongNhan.setRowSelectionInterval(row, row); // Chọn dòng mới;	        

	        	if(row != -1 && demChamCong >0 && !kiemTraChamCong(row)) {
                	demChamCong -=1;
                }
	        	
	        	if(demChamCong >0) {
	        		batChamCong();
	        	}
		        else {
		        	tatChamCong();
				}
		        	
		        
		        
		    }
		});
		//cap nhat trang thai ca lam khi select
		cmbCaLam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblPhanCongCongNhan.getSelectedRow();
                if (row >= 0 && dsPhanCong.get(row).getTrangThai() != null) {
                    String selectedCaLam = cmbCaLam.getSelectedItem().toString();
                    dsPhanCong.get(row).setCaLam(selectedCaLam);
                    //cap nhat tien cong theo ca lam
                    capNhatTienCong(dsPhanCong.get(row).getCongDoan().getTienCongDoan(),dsPhanCong.get(row).getSoLuongLamDuoc(), row);
                    updatePhanCongTableModel(dsPhanCong, HEADERS);
                  //kiểm tra đã chấm công công nhân này trong ngày này và ca này chưa
                    if(row != -1 && demChamCong >0 && !kiemTraChamCong(row)) {
                    	demChamCong -=1;
                    }
                    
                    
                }
                
                if(row != -1) {
                	tblPhanCongCongNhan.setRowSelectionInterval(row, row);
                }
                if(demChamCong >0) {
	        		batChamCong();
	        	}
		        else {
		        	tatChamCong();
				}
                

                
            }
        });
		
		btnChamCong1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnChamCongActionPerformed(e);
			}

			private void btnChamCongActionPerformed(java.awt.event.ActionEvent evt) {
				// TODO add your handling code here:
				System.out.println(demChamCong);
				
				try {
					
						for(int i = 0;i<dsPhanCong.size();i++) {
							if(dsPhanCong.get(i).getTrangThai()!= null) {
									String maPhanCong = dsPhanCong.get(i).getMaPhanCong();
								
									BangChamCongCongNhan thongTinChamCong = layDuLieuChamCongCN(i,maPhanCong);
									chamCongCongNhanDAO.themChamCongCongNhan(thongTinChamCong);
									callback.accept(dsCCCN);
//									JOptionPane.showMessageDialog(null, "Đã chấm công thành công!!");
//									dsPhanCong = phanCongCongNhanDAO.timKiemPhanCongConLai(null, null, maSanPham, tenSanPham,
//											maCongDoan, tenCongDoan, null, null);
//									updatePhanCongTableModel(dsPhanCong, HEADERS);
								
							}
						}
						JOptionPane.showMessageDialog(null, "Đã chấm công thành công!!");
						
						dsPhanCong = phanCongCongNhanDAO.timKiemPhanCongConLai(null, null, maSanPham, tenSanPham,
								maCongDoan, tenCongDoan, null, null);
						updatePhanCongTableModel(dsPhanCong, HEADERS);
					
					
					
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
//			private void btnChamCongActionPerformed(java.awt.event.ActionEvent evt) {
//				try {
//					for(int i = 0;i<dsPhanCong.size();i++) {
//						if(dsPhanCong.get(i).getTrangThai()!= null) {
//							System.out.println(dsPhanCong.get(i).getSoLuongLamDuoc());
//							System.out.println(dsPhanCong.get(i).getCaLam());
//							System.out.println(dsPhanCong.get(i).getTrangThai());
//						}
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}

		});
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDanhSachPhanCong = new javax.swing.JPanel();
        scrTablePhanCong = new javax.swing.JScrollPane();
        tblPhanCongCongNhan = new javax.swing.JTable();
        dtNgayChamCong = new com.toedter.calendar.JDateChooser();
        lblNgayChamCong = new javax.swing.JLabel();
        btnTroVe = new javax.swing.JButton();
        btnChamCong1 = new javax.swing.JButton();
        lblSoLuongLam2 = new javax.swing.JLabel();
        cmbCaLam = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1400, 642));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlDanhSachPhanCong.setBackground(new java.awt.Color(255, 255, 255));
        pnlDanhSachPhanCong.setMinimumSize(new java.awt.Dimension(1900, 621));

        scrTablePhanCong.setBackground(new java.awt.Color(255, 255, 255));
        scrTablePhanCong.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phân công", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16))); // NOI18N

        tblPhanCongCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblPhanCongCongNhan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPhanCongCongNhan.setSelectionBackground(new java.awt.Color(51, 153, 255));
        scrTablePhanCong.setViewportView(tblPhanCongCongNhan);

        dtNgayChamCong.setDateFormatString("yyyy-MM-dd");
        dtNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayChamCong.setText("Ngày chấm công:");

        btnTroVe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTroVe.setText("Trở về");
        btnTroVe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroVeActionPerformed(evt);
            }
        });

        btnChamCong1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnChamCong1.setText("Chấm công");
        btnChamCong1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblSoLuongLam2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongLam2.setText("Ca làm:");

        cmbCaLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sáng", "Chiều", "Tối" }));

        javax.swing.GroupLayout pnlDanhSachPhanCongLayout = new javax.swing.GroupLayout(pnlDanhSachPhanCong);
        pnlDanhSachPhanCong.setLayout(pnlDanhSachPhanCongLayout);
        pnlDanhSachPhanCongLayout.setHorizontalGroup(
            pnlDanhSachPhanCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                .addGroup(pnlDanhSachPhanCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblNgayChamCong)
                        .addGap(21, 21, 21)
                        .addComponent(dtNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(lblSoLuongLam2)
                        .addGap(22, 22, 22)
                        .addComponent(cmbCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(571, 571, 571)
                        .addComponent(btnChamCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(scrTablePhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 1362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(528, Short.MAX_VALUE))
        );
        pnlDanhSachPhanCongLayout.setVerticalGroup(
            pnlDanhSachPhanCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(scrTablePhanCong, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlDanhSachPhanCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblNgayChamCong))
                    .addComponent(dtNgayChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlDanhSachPhanCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChamCong1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDanhSachPhanCongLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlDanhSachPhanCongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSoLuongLam2)
                            .addComponent(cmbCaLam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        add(pnlDanhSachPhanCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 642));
    }// </editor-fold>//GEN-END:initComponents

	private void btnTroVeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnTroVeActionPerformed
		// TODO add your handling code here:
		Window window = SwingUtilities.getWindowAncestor(btnTroVe);
		window.dispose();

	}// GEN-LAST:event_btnTroVeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChamCong1;
    private javax.swing.JButton btnTroVe;
    private javax.swing.JComboBox<String> cmbCaLam;
    private com.toedter.calendar.JDateChooser dtNgayChamCong;
    private javax.swing.JLabel lblNgayChamCong;
    private javax.swing.JLabel lblSoLuongLam2;
    private javax.swing.JPanel pnlDanhSachPhanCong;
    private javax.swing.JScrollPane scrTablePhanCong;
    private javax.swing.JTable tblPhanCongCongNhan;
    // End of variables declaration//GEN-END:variables
}
