package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import connectdb.ConnectDB;
import entity.BangPhanCongCongNhan;
import entity.CongDoanSanPham;
import entity.CongNhan;
import entity.SanPham;

public class BangPhanCongCongNhanDAO {
	private Connection con;
	private NhanVienDAO nhanVienDAO;
	private CongDoanSanPhamDAO congDoanSanPhamDAO;
	private CongNhanDAO congNhanDAO;
	private DiaChiDAO diaChiDAO;
	private ToNhomDAO toNhomDAO;
	private SanPhamDAO sanPhamDAO;
	
	public BangPhanCongCongNhanDAO() {
		con = ConnectDB.getInstance().getConnection();
		nhanVienDAO = new NhanVienDAO();
		congDoanSanPhamDAO = new CongDoanSanPhamDAO();
		congNhanDAO = new CongNhanDAO();
		diaChiDAO = new DiaChiDAO();
		toNhomDAO = new ToNhomDAO();
		sanPhamDAO = new SanPhamDAO();
	}
	public BangPhanCongCongNhan timPhanCongCongNhan(String maPCCN) {
		BangPhanCongCongNhan bangPhanCong = null;
		String sql = "select * from PHANCONGCONGNHAN where maPhanCong = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPCCN);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				bangPhanCong = new BangPhanCongCongNhan(rs.getString(1), rs.getDate(2), 
						 rs.getInt(3), congDoanSanPhamDAO.timCongDoan(rs.getString(4), sanPhamDAO.timSanPham(rs.getString(5)).getMaSanPham()),
						sanPhamDAO.timSanPham(rs.getString(5)),
						congNhanDAO.timCongNhanBangMaCongNhan(rs.getString(6)), 
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bangPhanCong;
	}
	
	public boolean checkCongDoanInBangChamCong(String maCD, String maSP) {
		String sql = "SELECT * FROM PHANCONGCONGNHAN WHERE maCongDoan = ? and maSanPham =?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCD);
			stmt.setString(2, maSP);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean checkSanPhamInBangChamCong(String maSP) {
		String sql = "SELECT * FROM PHANCONGCONGNHAN WHERE maSanPham =?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSP);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public BangPhanCongCongNhan timPhanCongCongNhanBangMaCongNhan(String maCongNhan) {
		BangPhanCongCongNhan bangPhanCong = null;
		String sql = "select * from PHANCONGCONGNHAN where maCongNhan = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCongNhan);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				bangPhanCong = new BangPhanCongCongNhan(rs.getString(1), rs.getDate(2), 
						 rs.getInt(3), congDoanSanPhamDAO.timCongDoan(rs.getString(4), sanPhamDAO.timSanPham(rs.getString(5)).getMaSanPham()),
						sanPhamDAO.timSanPham(rs.getString(5)),
						congNhanDAO.timCongNhanBangMaCongNhan(rs.getString(6)), 
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bangPhanCong;
	}
	
	public boolean capNhatThongTinPhanCong(BangPhanCongCongNhan phanCong) {
		if(timPhanCongCongNhan(phanCong.getMaPhanCong()) == null)
			return false;
		String sql = "UPDATE [dbo].[PHANCONGCONGNHAN]\r\n"
				+ "   SET [ngayPhanCong] = ? "
				+ "      ,[soLuongPhanCong] = ? "
				+ "      ,[maCongDoan] = ? "
				+ "      ,[maSanPham] = ? "
				+ "      ,[maCongNhan] = ? "
				+ "      ,[maNguoiPhanCong] = ? "
				+ " WHERE maPhanCong = ?";
		int dem = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setDate(1, new Date(phanCong.getNgayPhanCong().getTime()));
			stmt.setDouble(2, phanCong.getSoLuongPhanCong());
			stmt.setString(3, phanCong.getCongDoan().getMaCongDoan());
			stmt.setString(4, phanCong.getSanPham().getMaSanPham());
			stmt.setString(5, phanCong.getCongNhan().getMaCongNhan());
			stmt.setString(6, phanCong.getNguoiPhanCong().getMaNhanVien());
			stmt.setString(7, phanCong.getMaPhanCong());
			dem = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem >0;
	}
	
	public List<BangPhanCongCongNhan> layDanhSachPhanCongCN() {
		List<BangPhanCongCongNhan> dsPCCN = new ArrayList<>();
		BangPhanCongCongNhan bangPCCN;
		String sql = "select * from PhanCongCongNhan";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				bangPCCN = new BangPhanCongCongNhan(rs.getString(1), rs.getDate(2), 
						 rs.getInt(3), congDoanSanPhamDAO.timCongDoan(rs.getString(4), sanPhamDAO.timSanPham(rs.getString(5)).getMaSanPham()),
						sanPhamDAO.timSanPham(rs.getString(5)),
						congNhanDAO.timCongNhanBangMaCongNhan(rs.getString(6)), 
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
				dsPCCN.add(bangPCCN);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsPCCN;
	}
	
	public List<BangPhanCongCongNhan> layDanhSachPhanCongCNChuaHoanThanh() {
		List<BangPhanCongCongNhan> dsPCCN = new ArrayList<>();
		BangPhanCongCongNhan bangPCCN;
		String sql = "select * from PhanCongCongNhan where soLuongPhanCong > 0";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				bangPCCN = new BangPhanCongCongNhan(rs.getString(1), rs.getDate(2), 
						 rs.getInt(3), congDoanSanPhamDAO.timCongDoan(rs.getString(4), sanPhamDAO.timSanPham(rs.getString(5)).getMaSanPham()),
						sanPhamDAO.timSanPham(rs.getString(5)),
						congNhanDAO.timCongNhanBangMaCongNhan(rs.getString(6)), 
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
				dsPCCN.add(bangPCCN);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsPCCN;
	}
	

	public boolean themPhanCong(BangPhanCongCongNhan pcCN) {
		String sql = "INSERT INTO [dbo].[PHANCONGCONGNHAN]"
				+ "           ([maPhanCong]"
				+ "           ,[ngayPhanCong]"
				+ "           ,[soLuongPhanCong]"
				+ "           ,[maCongDoan]"
				+ "           ,[maSanPham]"
				+ "           ,[maCongNhan]"
				+ "           ,[maNguoiPhanCong])"
				+ "     VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, pcCN.getMaPhanCong());
			stmt.setDate(2, new Date(pcCN.getNgayPhanCong().getTime()));
			stmt.setInt(3, pcCN.getSoLuongPhanCong());
			stmt.setString(4, pcCN.getCongDoan().getMaCongDoan());
			stmt.setString(5, pcCN.getSanPham().getMaSanPham());
			stmt.setString(6, pcCN.getCongNhan().getMaCongNhan());
			stmt.setString(7, pcCN.getNguoiPhanCong().getMaNhanVien());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean xoaPhanCong(String maPhanCong) {
		String sql = "delete from PhanCongCongNhan where maPhanCong = ?";
		int dem = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maPhanCong);
			dem = stmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem >0;
	}
	
	public List<BangPhanCongCongNhan> timKiemPhanCongTheoNhieuThuocTinh(String maPhanCong, String maCongNhan, String maSanPham,String tenSanPham, String maCongDoan,String tenCongDoan, java.util.Date ngayPhanCong, java.util.Date denNgay, String soLuong, int kieuTimKiem) throws Exception {
		String sql = "select pccn.* from PhanCongCongNhan pccn join  CONGDOANSANPHAM cd on pccn.maCongDoan = cd.maCongDoan and pccn.maSanPham = cd.maSanPham join SanPham sp on cd.maSanPham = sp.maSanPham ";
		String where = "where ";
		if (maPhanCong != null || maCongNhan != null || maSanPham != null || maCongDoan != null || ngayPhanCong != null || soLuong != null || tenCongDoan != null || tenSanPham != null) {
			List<BangPhanCongCongNhan> dsPC = new ArrayList<>();
			if (maPhanCong != null)
				where += " pccn.maPhanCong like N'%" + maPhanCong + "%' ";
			else {
				if (maCongNhan != null)
					where += "and pccn.maCongNhan like N'%" + maCongNhan.trim() + "%' ";
				if (maSanPham != null)
					where += "and pccn.maSanPham like N'%" + maSanPham.trim() + "%' ";
				if(tenSanPham != null)
					where += "and sp.tenSanPham like N'%" + tenSanPham.trim() + "%'";
				if (maCongDoan != null)
					where += "and cd.maCongDoan like N'%" + maCongDoan.trim() + "%' ";
				if(tenCongDoan != null)
					where += "and cd.tenCongDoan like N'%" + tenCongDoan.trim() + "%'";
				
				if (kieuTimKiem == 1) {
				    if (ngayPhanCong != null) {
				        where += "and pccn.ngayPhanCong = '" + new Date(ngayPhanCong.getTime()) + "' ";
				    }
				} else if (kieuTimKiem == 2) {
				    if (ngayPhanCong != null) {
				        where += "and MONTH(pccn.ngayPhanCong) = '" + (ngayPhanCong.getMonth() + 1) + "' ";
				        where += "and YEAR(pccn.ngayPhanCong) = '" + (ngayPhanCong.getYear() + 1900) + "' ";
				    }
				} else if (kieuTimKiem == 3) {
				    if (ngayPhanCong != null && denNgay != null) {
				        where += "and pccn.ngayPhanCong between '" + new Date(ngayPhanCong.getTime()) + "' ";
				        where += "and '" + new Date(denNgay.getTime()) + "' ";
				    }
				}

				if (soLuong != null)
					where += "and pccn.soLuongPhanCong = '" + Integer.parseInt(soLuong) + "' ";
		
				where = where.replaceFirst("and", "");
			}
			sql += where;
			
			System.out.println(sql);
			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					BangPhanCongCongNhan pccn = new BangPhanCongCongNhan(rs.getString(1), rs.getDate(2), 
							 rs.getInt(3), congDoanSanPhamDAO.timCongDoan(rs.getString(4), sanPhamDAO.timSanPham(rs.getString(5)).getMaSanPham()),
								sanPhamDAO.timSanPham(rs.getString(5)),
								congNhanDAO.timCongNhanBangMaCongNhan(rs.getString(6)), 
								nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
					dsPC.add(pccn);
				}
			}
			return dsPC;
		} else
			return layDanhSachPhanCongCN();
	}
        public List<BangPhanCongCongNhan> timKiemPhanCongConLai(String maPhanCong, String maCongNhan, String maSanPham, String tenSanPham, String maCongDoan, String tenCongDoan, java.util.Date ngayPhanCong, String soLuong) throws Exception {
        String sql = "SELECT pccn.maPhanCong, pccn.ngayPhanCong, (pccn.SOLUONGPHANCONG - COALESCE(SUM(BCCCN.soLuongLam), 0)) AS soLuongConLaiChuaLam, pccn.maCongDoan, pccn.maSanPham, pccn.maCongNhan, pccn.maNguoiPhanCong\r\n"
        		+ "FROM PHANCONGCONGNHAN pccn\r\n"
        		+ "LEFT JOIN BANGCHAMCONGCONGNHAN BCCCN ON pccn.MAPHANCONG = BCCCN.maPhanCong join  CONGDOANSANPHAM cd on pccn.maCongDoan = cd.maCongDoan and pccn.maSanPham = cd.maSanPham join SanPham sp on cd.maSanPham = sp.maSanPham\r\n"
        		+ "GROUP BY pccn.maPhanCong, pccn.ngayPhanCong, pccn.SOLUONGPHANCONG, pccn.maCongDoan, pccn.maSanPham, pccn.maCongNhan, pccn.maNguoiPhanCong\r\n"
        		+ "HAVING (pccn.SOLUONGPHANCONG - COALESCE(SUM(BCCCN.soLuongLam), 0)) > 0 ";
        String where = " ";
            
            List<BangPhanCongCongNhan> dsPC = new ArrayList<>();
                if (maCongNhan != null) {
                    where += "and pccn.maCongNhan like N'%" + maCongNhan.trim() + "%' ";
                }
                if (maSanPham != null) {
                    where += "and pccn.maSanPham like N'%" + maSanPham.trim() + "%' ";
                }
                if (tenSanPham != null) {
                    where += "and sp.tenSanPham like N'%" + tenSanPham.trim() + "%'";
                }
                if (maCongDoan != null) {
                    where += "and cd.maCongDoan like N'%" + maCongDoan.trim() + "%' ";
                }
                if (tenCongDoan != null) {
                    where += "and cd.tenCongDoan like N'%" + tenCongDoan.trim() + "%'";
                }
                if (ngayPhanCong != null) {
                    where += "and pccn.ngayPhanCong = '" + new Date(ngayPhanCong.getTime()) + "' ";
                }
                if (soLuong != null) {
                    where += "and pccn.soLuongPhanCong = '" + Integer.parseInt(soLuong) + "' ";
                }

//                where = where.replaceFirst("and", "");
//                System.out.println(sql);          
            sql += where;

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    BangPhanCongCongNhan pccn = new BangPhanCongCongNhan(rs.getString(1), rs.getDate(2), 
   						 rs.getInt(3), congDoanSanPhamDAO.timCongDoan(rs.getString(4), sanPhamDAO.timSanPham(rs.getString(5)).getMaSanPham()),
 						sanPhamDAO.timSanPham(rs.getString(5)),
 						congNhanDAO.timCongNhanBangMaCongNhan(rs.getString(6)), 
 						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
                    dsPC.add(pccn);
                }
            }
            return dsPC;
        
           
        
    }
	
	public String getMaPhanCongCaoNhat() throws Exception {
		String sql = "SELECT MAX(maPhanCong) FROM PhanCongCongNhan";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		}
		return null;
	}
	
}
