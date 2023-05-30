/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ChamCongCongNhan extends javax.swing.JPanel {

	private PopupMenu QuanLyCN;
	private List<BangChamCongCongNhan> dsCCCN;
	private BangChamCongCongNhanDAO chamCongCongNhanDAO;
	private BangChamCongCNTableModel chamCongCNTableModel;
	private BangPhanCongCongNhanDAO phanCongCongNhanDAO;
	List<BangPhanCongCongNhan> dsPhanCong = new ArrayList<>();
	private static final String[] HEADERS = {"Mã chấm công", "Mã công nhân", "Tên công nhân", "Tổ", "Ngày chấm công", "Ca làm", "Trạng thái", "Mã công đoạn", "Tên Công đoạn", "Số lượng làm được", "Mã sản phẩm", "Tên Sản phẩm","Mã Người chấm công","Tên người chấm công"};
	//    private CongNhanDAO congNhanDAO;

	/**
	 * Creates new form ChamCongCongNhan
	 */
	private NhanVien nhanVienLogin;
	public ChamCongCongNhan(NhanVien nvLogin) {
		this.nhanVienLogin = nvLogin;
		chamCongCongNhanDAO = new BangChamCongCongNhanDAO();
		phanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		initComponents();
		ThemSuKien();
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
		txtSoLuongLam.setText(String.format("%d", thongTinChamCongCN.getSoLuongLam())) ;
		txtMaNguoiChamCong.setText(thongTinChamCongCN.getNguoiChamCong().getMaNhanVien());
		txtTenNguoiChamCong.setText(thongTinChamCongCN.getNguoiChamCong().getTenNhanVien());
	}
	//lấy danh sách theo checkbox ngày hôm nay
	private void danhSachChamCongHomNay() {
		Date homNay = dtNgayChamCong1.getDate();
		List<BangChamCongCongNhan> dsHomNay = new ArrayList<>();
		dsHomNay = chamCongCongNhanDAO.layDanhSachChamCongHomNay(homNay);
		updateChamCongCNTableModel(dsHomNay, HEADERS);
	}
	private void ThemSuKien() {
		dtNgayChamCong1.setDate(new Date());
		dsCCCN = chamCongCongNhanDAO.layDanhSachChamCongCN();
		updateChamCongCNTableModel(dsCCCN, HEADERS);

		tblChamCong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String maChamCong = (String) tblChamCong.getModel().getValueAt(tblChamCong.getSelectedRow(), 0);
				if(maChamCong != null) {
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
		
		
		//checkbox hôm nay
		cbLichHomNay.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(cbLichHomNay.isSelected() == true) {
							danhSachChamCongHomNay();
						}
						else {
							updateChamCongCNTableModel(dsCCCN, HEADERS);
						}
						
					}
				});
		//listener ngày chấm công
				dtNgayChamCong1.addPropertyChangeListener(new PropertyChangeListener() {
				    @Override
				    public void propertyChange(PropertyChangeEvent evt) {
				    	
				        // Kiểm tra xem thuộc tính đã thay đổi có phải là ngày không
				        if ("date".equals(evt.getPropertyName())) {
				            // Lấy ngày mới được chọn
				            Date ngay = (Date) evt.getNewValue();
							
							
							if(cbLichHomNay.isSelected() == true) {
								danhSachChamCongHomNay();
							}
							
				            
				            
				        }
				      //kiem tra ngay ChuNhat
//				    	ngayChuNhat();
				    }
				});
			
		btnLayDSPhanCong.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					btnLayDSPhanCongActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



			}
			private void updateDanhSachChamCong(List<BangChamCongCongNhan> dsChamCong) {
				if(cbLichHomNay.isSelected()) {
					dsChamCong = chamCongCongNhanDAO.layDanhSachChamCongHomNay(dtNgayChamCong1.getDate());
				}
				else {
					dsChamCong = chamCongCongNhanDAO.layDanhSachChamCongCN();
				}
				
				updateChamCongCNTableModel(dsChamCong, HEADERS);

			}
			private void btnLayDSPhanCongActionPerformed(java.awt.event.ActionEvent evt) throws Exception {                                                 
				// TODO add your handling code here:

				String maSanPham  = txtMaSanPham.getText().trim().isEmpty()? null :txtMaSanPham.getText().trim() ;
//				System.out.println(maSanPham);
				String tenSanPham = txtTenSanPham.getText().trim().isEmpty() ? null : txtTenSanPham.getText().trim();
				String maCongDoan = txtMaCongDoan.getText().trim().isEmpty() ? null: txtMaCongDoan.getText().trim();
				String tenCongDoan = txtTenCongDoan.getText().trim().isEmpty()? null : txtTenCongDoan.getText().trim();
//				System.out.println(tenCongDoan);
				dsPhanCong = phanCongCongNhanDAO.timKiemPhanCongConLai(null, null, maSanPham, tenSanPham, maCongDoan, tenCongDoan, null, null);
				if(!dsPhanCong.isEmpty()) {
					JDialog layDsPhanCong = new JDialog();
					LayDSChamCongCN layDSChamCongCN = new LayDSChamCongCN(dsPhanCong, dsCCCN, nhanVienLogin, this::updateDanhSachChamCong,maSanPham,tenSanPham,maCongDoan,tenCongDoan,dtNgayChamCong1.getDate());
					layDsPhanCong.add(layDSChamCongCN);
					layDsPhanCong.setTitle("Danh sách phân công công nhân");
					layDsPhanCong.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

					layDsPhanCong.addWindowFocusListener(new WindowFocusListener() {

						@Override
						public void windowLostFocus(WindowEvent e) {
							// TODO Auto-generated method stub
							layDsPhanCong.requestFocus();

						}

						@Override
						public void windowGainedFocus(WindowEvent e) {
							// TODO Auto-generated method stub

						}
					});
					layDsPhanCong.setSize(1400, 700);
					layDsPhanCong.setLocationRelativeTo(null);
					layDsPhanCong.setVisible(true);


				}
				else
					JOptionPane.showMessageDialog(null, "Không tìm thấy phân công nào theo dữ liệu bạn nhập");
			}
		});
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        lblNgayChamCong = new javax.swing.JLabel();
        lblSoLuongSanPham = new javax.swing.JLabel();
        lblMaCongNhan = new javax.swing.JLabel();
        lblTenCongDoanRight = new javax.swing.JLabel();
        lblTenSanPhamRight = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        lblMaCongDoan = new javax.swing.JLabel();
        lblMaSanPham = new javax.swing.JLabel();
        lblTenSanPham = new javax.swing.JLabel();
        lblTenCongDoan = new javax.swing.JLabel();
        lblToNhom = new javax.swing.JLabel();
        lblMaCongNhan1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTenSanPhamRight1 = new javax.swing.JLabel();
        lblTenSanPhamRight2 = new javax.swing.JLabel();
        cmbTinhTrang = new javax.swing.JComboBox<>();
        btnLayDSPhanCong = new javax.swing.JButton();
        txtMaSanPham = new javax.swing.JTextField();
        txtTenSanPham = new javax.swing.JTextField();
        txtMaCongDoan = new javax.swing.JTextField();
        txtTenCongDoan = new javax.swing.JTextField();
        cmbCaLam = new javax.swing.JComboBox<>();
        txtSoLuongLam = new javax.swing.JTextField();
        txtTenCongNhan = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        txtMaCongNhan = new javax.swing.JTextField();
        txtMaCongDoan2 = new javax.swing.JTextField();
        txtTenCongDoan2 = new javax.swing.JTextField();
        txtMaSanPham2 = new javax.swing.JTextField();
        txtTenSanPham2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dtNgayChamCong = new com.toedter.calendar.JDateChooser();
        lblNguoiChamCong = new javax.swing.JLabel();
        txtMaNguoiChamCong = new javax.swing.JTextField();
        lblTenNguoiChamCong = new javax.swing.JLabel();
        txtTenNguoiChamCong = new javax.swing.JTextField();
        lblLichHomNay = new javax.swing.JLabel();
        cbLichHomNay = new javax.swing.JCheckBox();
        lblNgayChamCong1 = new javax.swing.JLabel();
        dtNgayChamCong1 = new com.toedter.calendar.JDateChooser();
        scrChamCong = new javax.swing.JScrollPane();
        tblChamCong = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel5.setPreferredSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayChamCong.setText("Ngày chấm công:");
        jPanel5.add(lblNgayChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 210, 140, 40));

        lblSoLuongSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSoLuongSanPham.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoLuongSanPham.setText("Số lượng làm:");
        jPanel5.add(lblSoLuongSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(1068, 250, -1, 40));

        lblMaCongNhan.setBackground(new java.awt.Color(255, 255, 255));
        lblMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongNhan.setText("Tên công nhân:");
        jPanel5.add(lblMaCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 160, 40));

        lblTenCongDoanRight.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongDoanRight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenCongDoanRight.setText("Tên sản phẩm:");
        jPanel5.add(lblTenCongDoanRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(1068, 210, -1, 40));

        lblTenSanPhamRight.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPhamRight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPhamRight.setText("Mã công đoạn:");
        jPanel5.add(lblTenSanPhamRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(1068, 90, 122, 40));

        lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTrangThai.setText("Tình trạng:");
        jPanel5.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, 90, 40));

        lblMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblMaCongDoan.setText("Mã công đoạn:");
        jPanel5.add(lblMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 40));

        lblMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaSanPham.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblMaSanPham.setText("Mã sản phẩm:");
        jPanel5.add(lblMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 120, 30));

        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPham.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTenSanPham.setText("Tên sản phẩm:");
        jPanel5.add(lblTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 120, 30));

        lblTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenCongDoan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTenCongDoan.setText("Tên công đoạn:");
        jPanel5.add(lblTenCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 130, 40));

        lblToNhom.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblToNhom.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblToNhom.setText("Tổ:");
        jPanel5.add(lblToNhom, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, 40, 40));

        lblMaCongNhan1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaCongNhan1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMaCongNhan1.setText("Mã công nhân:");
        jPanel5.add(lblMaCongNhan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 160, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Ca làm:");
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, -1, -1));

        lblTenSanPhamRight1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPhamRight1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPhamRight1.setText("Tên công đoạn:");
        jPanel5.add(lblTenSanPhamRight1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1068, 130, -1, 40));

        lblTenSanPhamRight2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenSanPhamRight2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenSanPhamRight2.setText("Mã sản phẩm:");
        jPanel5.add(lblTenSanPhamRight2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1068, 170, -1, 40));

        cmbTinhTrang.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Có mặt", "Vắng mặt" }));
        cmbTinhTrang.setEnabled(false);
        jPanel5.add(cmbTinhTrang, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 300, 150, 30));

        btnLayDSPhanCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLayDSPhanCong.setText("Lấy danh sách phân công");
        btnLayDSPhanCong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel5.add(btnLayDSPhanCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 250, 40));

        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 220, 30));

        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenSanPham.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 220, 30));

        txtMaCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtMaCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 220, 30));

        txtTenCongDoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongDoan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenCongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 220, 30));

        cmbCaLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cmbCaLam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sáng", "Chiều", "Tối" }));
        cmbCaLam.setEnabled(false);
        jPanel5.add(cmbCaLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 260, 120, 30));

        txtSoLuongLam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtSoLuongLam.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtSoLuongLam.setEnabled(false);
        jPanel5.add(txtSoLuongLam, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 250, 260, 30));

        txtTenCongNhan.setEditable(false);
        txtTenCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTenCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 260, 30));

        txtTo.setEditable(false);
        txtTo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTo.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        jPanel5.add(txtTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 260, 30));

        txtMaCongNhan.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtMaCongNhan.setEditable(false);
        txtMaCongNhan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel5.add(txtMaCongNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 260, 30));

        txtMaCongDoan2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaCongDoan2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtMaCongDoan2.setEnabled(false);
        jPanel5.add(txtMaCongDoan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 90, 260, 30));

        txtTenCongDoan2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenCongDoan2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtTenCongDoan2.setEnabled(false);
        jPanel5.add(txtTenCongDoan2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 130, 260, 30));

        txtMaSanPham2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaSanPham2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtMaSanPham2.setEnabled(false);
        jPanel5.add(txtMaSanPham2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 170, 260, 30));

        txtTenSanPham2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenSanPham2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtTenSanPham2.setEnabled(false);
        jPanel5.add(txtTenSanPham2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 210, 260, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CHẤM CÔNG CÔNG NHÂN");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1800, 50));

        dtNgayChamCong.setEnabled(false);
        dtNgayChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel5.add(dtNgayChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 220, 260, 30));

        lblNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNguoiChamCong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNguoiChamCong.setText("Mã người chấm công:");
        jPanel5.add(lblNguoiChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 290, -1, 40));

        txtMaNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtMaNguoiChamCong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtMaNguoiChamCong.setEnabled(false);
        jPanel5.add(txtMaNguoiChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 290, 260, 30));

        lblTenNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTenNguoiChamCong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenNguoiChamCong.setText("Tên người chấm công:");
        jPanel5.add(lblTenNguoiChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 330, -1, 40));

        txtTenNguoiChamCong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTenNguoiChamCong.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        txtTenNguoiChamCong.setEnabled(false);
        jPanel5.add(txtTenNguoiChamCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 330, 260, 30));

        lblLichHomNay.setText("Lịch hôm nay");
        jPanel5.add(lblLichHomNay, new org.netbeans.lib.awtextra.AbsoluteConstraints(1560, 460, -1, 20));
        jPanel5.add(cbLichHomNay, new org.netbeans.lib.awtextra.AbsoluteConstraints(1640, 460, -1, -1));

        lblNgayChamCong1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNgayChamCong1.setText("Ngày chấm công:");
        jPanel5.add(lblNgayChamCong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        dtNgayChamCong1.setDateFormatString("yyyy-MM-dd");
        dtNgayChamCong1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel5.add(dtNgayChamCong1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 220, 30));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(scrChamCong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1796, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrChamCong, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
	//	//	//

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLayDSPhanCong;
    private javax.swing.JCheckBox cbLichHomNay;
    private javax.swing.JComboBox<String> cmbCaLam;
    private javax.swing.JComboBox<String> cmbTinhTrang;
    private com.toedter.calendar.JDateChooser dtNgayChamCong;
    private com.toedter.calendar.JDateChooser dtNgayChamCong1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblLichHomNay;
    private javax.swing.JLabel lblMaCongDoan;
    private javax.swing.JLabel lblMaCongNhan;
    private javax.swing.JLabel lblMaCongNhan1;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JLabel lblNgayChamCong;
    private javax.swing.JLabel lblNgayChamCong1;
    private javax.swing.JLabel lblNguoiChamCong;
    private javax.swing.JLabel lblSoLuongSanPham;
    private javax.swing.JLabel lblTenCongDoan;
    private javax.swing.JLabel lblTenCongDoanRight;
    private javax.swing.JLabel lblTenNguoiChamCong;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblTenSanPhamRight;
    private javax.swing.JLabel lblTenSanPhamRight1;
    private javax.swing.JLabel lblTenSanPhamRight2;
    private javax.swing.JLabel lblToNhom;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JScrollPane scrChamCong;
    private javax.swing.JTable tblChamCong;
    private javax.swing.JTextField txtMaCongDoan;
    private javax.swing.JTextField txtMaCongDoan2;
    private javax.swing.JTextField txtMaCongNhan;
    private javax.swing.JTextField txtMaNguoiChamCong;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtMaSanPham2;
    private javax.swing.JTextField txtSoLuongLam;
    private javax.swing.JTextField txtTenCongDoan;
    private javax.swing.JTextField txtTenCongDoan2;
    private javax.swing.JTextField txtTenCongNhan;
    private javax.swing.JTextField txtTenNguoiChamCong;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenSanPham2;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
