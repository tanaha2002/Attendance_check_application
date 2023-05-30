/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import entity.NhanVien;
import setting.PathSetting;
import ui.MenuItem;
import java.awt.Window.Type;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author RavenPC
 */
public class MainUI extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private MenuItem menuCapNhatNV = null;
    private MenuItem menuChamCongNV = null;
    private MenuItem menuTimChamCongNV = null;
    private MenuItem menuChamCongChoNV = null;
    private MenuItem menuXemLuongNV = null;
    private MenuItem menuTimKiemNV = null;
    private MenuItem menuCapNhatCN = null;
    private MenuItem menuChamCongCN = null;
    private MenuItem menuTimChamCongCN = null;
    private MenuItem menuChamCongChoCN = null;
    private MenuItem menuXemLuongCN = null;
    private MenuItem menuTimKiemCN = null;
    private MenuItem menuCapNhatSP = null;
    private MenuItem menuTimPhanCong = null;
    private MenuItem menuTimCongDoan = null;
    private MenuItem menuPhanCongDoanChoCN = null;
    private MenuItem menuPhanCongDoan = null;
    private MenuItem menuTimKiemSP = null;
    private MenuItem menuTKLuongNV = null;
    private MenuItem menuTKLuongCN = null;
    private MenuItem menuTKDoanhThu = null;
    private MenuItem menuTrangChu = null;
    private MenuItem menuNhanVien = null;
    private MenuItem menuCongNhan = null;
    private MenuItem menuSanPham = null;
    private MenuItem menuThongKe = null;
    

    private ImageIcon iconSubMenu;
    private ImageIcon iconSubMenu_Selected;

    private static NhanVien nhanVienLogin;

    public MainUI(NhanVien nvLogin) {
    	
        this.nhanVienLogin = nvLogin;
//        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        
        initComponents();
        this.setDefaultCloseOperation(MainUI.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
//        this.setResizable(false); // Tắt khả năng auto resize
        this.setResizable(true);

        this.setVisible(true);
        
        execute();
        themSuKien();
    }

    private boolean isKeToan() {
        if (nhanVienLogin.getVaiTro().equals("Quản lý")) {
            return false;
        }
        return true;
    }

    private boolean isQuanLy() {
        if (nhanVienLogin.getVaiTro().equals("Kế toán")) {
            return false;
        }
        return true;
    }

    private void execute() {
    	lblAnhNhanVienDangLogin.setSize(100, 110);
    	
    	String imagePath1 = PathSetting.PATH_IMAGE_NHANVIEN + nhanVienLogin.getAnhDaiDien();

    	ImageIcon imageIcon1 = new ImageIcon(imagePath1);
    	int labelWidth1 = lblAnhNhanVienDangLogin.getWidth();
    	int labelHeight1 = lblAnhNhanVienDangLogin.getHeight();
    	Image scaledImage1 = imageIcon1.getImage().getScaledInstance(labelWidth1, labelHeight1, Image.SCALE_SMOOTH);
    	ImageIcon scaledIconImage = new ImageIcon(scaledImage1);
    	lblAnhNhanVienDangLogin.setIcon(scaledIconImage);
        ImageIcon iconTrangChu = new ImageIcon(setting.PathSetting.PATH_ICON + "iconshome.png");
        ImageIcon iconNhanVien = new ImageIcon(setting.PathSetting.PATH_ICON + "team.png");
        ImageIcon iconCongNhan = new ImageIcon(setting.PathSetting.PATH_ICON + "tutorial.png");
        ImageIcon iconSanPham = new ImageIcon(setting.PathSetting.PATH_ICON + "list.png");
        ImageIcon iconThongKe = new ImageIcon(setting.PathSetting.PATH_ICON + "growth.png");
        iconSubMenu = new ImageIcon(setting.PathSetting.PATH_ICON + "o.png");
        iconSubMenu_Selected = new ImageIcon(setting.PathSetting.PATH_ICON + "o_selected.png");

        panelBody.add(new TrangChu(nhanVienLogin));
        // create submenu nhanvien 
        menuCapNhatNV = new MenuItem(iconSubMenu, "Cập nhật", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new QuanLyNV(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuCapNhatNV);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuNhanVien);

            }
        });

        menuChamCongNV = new MenuItem(iconSubMenu, "Chấm công", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	panelBody.removeAll();
            	panelBody.add(new ChamCongNhanVien(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuChamCongNV);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuNhanVien);
            }
        });

        menuXemLuongNV = new MenuItem(iconSubMenu, "Lương", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new QuanLyLuongNV());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuXemLuongNV);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuNhanVien);
            }
        });
        menuTimKiemNV = new MenuItem(iconSubMenu, "Tìm kiếm", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new MenuTimKiemNhanVien());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuTimKiemNV);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuNhanVien);
            }
        });
        //  create submenu congnhan 
        menuCapNhatCN = new MenuItem(iconSubMenu, "Cập nhật", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new QuanLyCN(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuCapNhatCN);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuCongNhan);
            }

        });
        
        menuChamCongCN = new MenuItem(iconSubMenu, "Chấm công", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	panelBody.removeAll();
                panelBody.add(new ui.ChamCongCongNhan(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuChamCongCN);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuCongNhan);
            }
        });
        menuXemLuongCN = new MenuItem(iconSubMenu, "Lương", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new QuanLyLuongCN());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuXemLuongCN);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuCongNhan);
            }
        });
        menuTimKiemCN = new MenuItem(iconSubMenu, "Tìm kiếm", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new MenuTimKiemCongNhan());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuTimKiemCN);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuCongNhan);
            }
        });
        //  create submenu sanpham 
        menuCapNhatSP = new MenuItem(iconSubMenu, "Cập nhật", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new QuanLySP(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuCapNhatSP);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuSanPham);

            }
        });

        menuPhanCongDoan = new MenuItem(iconSubMenu, "Phân công đoạn", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	panelBody.removeAll();
                panelBody.add(new QuanLyCongDoan(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuPhanCongDoan);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuSanPham);
            }
        });
        menuTimKiemSP = new MenuItem(iconSubMenu, "Tìm kiếm", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new MenuTimKiemSanPham());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuTimKiemSP);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuSanPham);
            }
        });

        //  create submenu thongke
        menuTKLuongNV = new MenuItem(iconSubMenu, "Nhân viên", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new ThongKeLuongNV());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuTKLuongNV);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuThongKe);
            }
        });
        menuTKLuongCN = new MenuItem(iconSubMenu, "Công nhân", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new ThongKeLuongCN());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuTKLuongCN);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuThongKe);
            }
        });

        menuTKDoanhThu = new MenuItem(iconSubMenu, "Doanh thu", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new ThongKeDoanhThu());
                panelBody.repaint();
                panelBody.revalidate();
                iconMenuMacDinh(menuTKDoanhThu);
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuThongKe);
            }
        });

        menuTrangChu = new MenuItem(iconTrangChu, "Trang chủ", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBody.removeAll();
                panelBody.add(new TrangChu(nhanVienLogin));
                panelBody.repaint();
                panelBody.revalidate();
                setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                setSelectMenu(menuTrangChu);
            }
        });
        if (isQuanLy()) {
            menuNhanVien = new MenuItem(iconNhanVien, "Nhân viên", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuNhanVien);
                }
            }, menuCapNhatNV, menuChamCongNV, menuXemLuongNV, menuTimKiemNV);
            menuCongNhan = new MenuItem(iconCongNhan, "Công nhân", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuCongNhan);
                }
            }, menuCapNhatCN, menuChamCongCN, menuXemLuongCN, menuTimKiemCN);
            menuSanPham = new MenuItem(iconSanPham, "Sản phẩm", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuSanPham);
                }
            }, menuCapNhatSP, menuPhanCongDoan, menuTimKiemSP);
            menuThongKe = new MenuItem(iconThongKe, "Thống kê", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuThongKe);
                }
            },
                    menuTKLuongNV, menuTKLuongCN, menuTKDoanhThu);
        }

        if (isKeToan()) {
            menuNhanVien = new MenuItem(iconNhanVien, "Nhân viên", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuNhanVien);
                }
            }, menuXemLuongNV, menuTimKiemNV);
            menuCongNhan = new MenuItem(iconCongNhan, "Công nhân", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuCongNhan);
                }
            }, menuXemLuongCN, menuTimKiemCN);
            menuSanPham = new MenuItem(iconSanPham, "Sản phẩm", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuSanPham);
                }
            }, menuTimKiemSP);
            menuThongKe = new MenuItem(iconThongKe, "Thống kê", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setNonSelectMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
                    setSelectMenu(menuThongKe);
                }
            },
                    menuTKLuongNV, menuTKLuongCN, menuTKDoanhThu);
        }
        addMenu(menuTrangChu, menuNhanVien, menuCongNhan, menuSanPham, menuThongKe);
        ImageIcon imageIcon = new ImageIcon(PathSetting.PATH_IMAGE_NHANVIEN + nhanVienLogin.getAnhDaiDien());
        txtTenNVDangLogin.setText(nhanVienLogin.getTenNhanVien());
    }

    public void setSelectMenu(JPanel pnlSelect) {
        pnlSelect.setBackground(new Color(51, 153, 255));
        for (Component jc : pnlSelect.getComponents()) {
            if (jc instanceof JLabel) {
                JLabel label = (JLabel) jc;
                label.setForeground(new Color(0, 0, 0));
                label.setFont(new Font("Segoe UI", Font.BOLD, 17));
            }
        }
    }

    public void setNonSelectMenu(JPanel... jPanels) {
        for (JPanel jPanel : jPanels) {
            jPanel.setBackground(new Color(255, 255, 255));
            for (Component jc : jPanel.getComponents()) {
                if (jc instanceof JLabel) {
                    JLabel label = (JLabel) jc;
                    label.setForeground(new Color(12, 12, 12));
                    label.setFont(new Font("Segoe UI", Font.BOLD, 17));
                }
            }
        }

    }

    public void resetSelectMenuIcon() {
        menuCapNhatNV.setIcon(iconSubMenu);
        menuChamCongNV.setIcon(iconSubMenu);
        menuXemLuongNV.setIcon(iconSubMenu);
        menuTimKiemNV.setIcon(iconSubMenu);

        menuCapNhatCN.setIcon(iconSubMenu);
        menuChamCongCN.setIcon(iconSubMenu);
        menuXemLuongCN.setIcon(iconSubMenu);
        menuTimKiemCN.setIcon(iconSubMenu);

        menuCapNhatSP.setIcon(iconSubMenu);
        menuPhanCongDoan.setIcon(iconSubMenu);
        menuTimKiemSP.setIcon(iconSubMenu);

        menuTKLuongNV.setIcon(iconSubMenu);
        menuTKLuongCN.setIcon(iconSubMenu);
        menuTKDoanhThu.setIcon(iconSubMenu);

        menuCapNhatNV.setBackground(Color.white);
        menuChamCongNV.setBackground(Color.white);
        menuXemLuongNV.setBackground(Color.white);
        menuTimKiemNV.setBackground(Color.white);

        menuCapNhatCN.setBackground(Color.white);
        menuChamCongCN.setBackground(Color.white);
        menuXemLuongCN.setBackground(Color.white);
        menuTimKiemCN.setBackground(Color.white);

        menuCapNhatSP.setBackground(Color.white);
        menuPhanCongDoan.setBackground(Color.white);
        menuTimKiemSP.setBackground(Color.white);

        menuTKLuongNV.setBackground(Color.white);
        menuTKLuongCN.setBackground(Color.white);
        menuTKDoanhThu.setBackground(Color.white);

    }

    public void iconMenuMacDinh(MenuItem menu) {
        resetSelectMenuIcon();
        menu.setIcon(iconSubMenu_Selected);
        menu.setBackground(new Color(242, 242, 242));
    }

    public void resetSelectMenuChil() {
        menuTimChamCongNV.setBackground(Color.white);
        menuChamCongChoNV.setBackground(Color.white);
        menuTimChamCongCN.setBackground(Color.white);
        menuChamCongChoCN.setBackground(Color.white);
        menuTimCongDoan.setBackground(Color.white);
        menuTimPhanCong.setBackground(Color.white);
        menuPhanCongDoanChoCN.setBackground(Color.white);
    }

    private void addMenu(MenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
            menus.add(menu[i]);
            ArrayList<MenuItem> subMenu = menu[i].getSubMenu();
            for (MenuItem m : subMenu) {
                addMenu(m);
            }
        }
        menus.revalidate();
    }

    private void themSuKien() {
        btnDoiMatkhau.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                try {
                    btnDoiMatKhauActionPerformed(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

            private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
                // TODO add your handling code here:

                JDialog doiMatKhau = new JDialog();
                doiMatKhau.setTitle("Đổi mật khẩu");
                doiMatKhau.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                doiMatKhau.add(new DoiMatKhau(nhanVienLogin));
                doiMatKhau.setResizable(false);
                doiMatKhau.addWindowFocusListener(new WindowFocusListener() {
                    @Override
                    public void windowLostFocus(WindowEvent e) {
                        // TODO Auto-generated method stub
                        doiMatKhau.requestFocus();
                    }

                    @Override
                    public void windowGainedFocus(WindowEvent e) {
                        // TODO Auto-generated method stub

                    }
                });
                doiMatKhau.setSize(820, 450);
                doiMatKhau.setLocationRelativeTo(null);
                doiMatKhau.setVisible(true);
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

        panelMenu = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menus = new javax.swing.JPanel();
        panelAccount = new javax.swing.JPanel();
        lblAnhNhanVienDangLogin = new javax.swing.JLabel();
        txtTenNVDangLogin = new javax.swing.JTextField();
        btnDoiMatkhau = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        panelBody = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelMenu.setBackground(new java.awt.Color(255, 255, 255));
        panelMenu.setPreferredSize(new java.awt.Dimension(250, 384));

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(4, 4));

        menus.setBackground(new java.awt.Color(255, 255, 255));
        menus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        menus.setLayout(new javax.swing.BoxLayout(menus, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(menus);

        panelAccount.setBackground(new java.awt.Color(255, 255, 255));
        panelAccount.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblAnhNhanVienDangLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/role-model.png"))); // NOI18N

        txtTenNVDangLogin.setEditable(false);
        txtTenNVDangLogin.setBackground(new java.awt.Color(255, 255, 255));
        txtTenNVDangLogin.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTenNVDangLogin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTenNVDangLogin.setText("Nguyễn Văn Nam");
        txtTenNVDangLogin.setBorder(null);

        btnDoiMatkhau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDoiMatkhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/key-person.png"))); // NOI18N
        btnDoiMatkhau.setText("Đổi mật khẩu");

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAccountLayout = new javax.swing.GroupLayout(panelAccount);
        panelAccount.setLayout(panelAccountLayout);
        panelAccountLayout.setHorizontalGroup(
            panelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenNVDangLogin)
            .addGroup(panelAccountLayout.createSequentialGroup()
                .addGroup(panelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelAccountLayout.createSequentialGroup()
                            .addGap(74, 74, 74)
                            .addComponent(lblAnhNhanVienDangLogin))
                        .addGroup(panelAccountLayout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(btnDoiMatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        panelAccountLayout.setVerticalGroup(
            panelAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccountLayout.createSequentialGroup()
                .addComponent(lblAnhNhanVienDangLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenNVDangLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDoiMatkhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDangXuat))
        );

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(panelMenu, java.awt.BorderLayout.LINE_START);

        panelBody.setBackground(new java.awt.Color(255, 255, 255));
        panelBody.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panelBody, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1936, 1048));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

	private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed

            int result = JOptionPane.showConfirmDialog(this, "Bạn muốn đăng xuất khỏi hệ thống?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                // Hide current window
                this.setVisible(false);

                // Show new window
                DangNhap dangNhap = new DangNhap();
                dangNhap.setVisible(true);
                dangNhap.setLocationRelativeTo(null);
            }

	}//GEN-LAST:event_btnDangXuatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainUI(nhanVienLogin).setVisible(true);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDoiMatkhau;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAnhNhanVienDangLogin;
    private javax.swing.JPanel menus;
    private javax.swing.JPanel panelAccount;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JTextField txtTenNVDangLogin;
    // End of variables declaration//GEN-END:variables
}
