package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectdb.ConnectDB;
import entity.CongDoanSanPham;
import entity.SanPham;

public class CongDoanSanPhamDAO {
	private Connection con;
	private SanPhamDAO sanPhamDAO;
	public CongDoanSanPhamDAO() {
		// TODO Auto-generated constructor stub
		con = ConnectDB.getInstance().getConnection();
		sanPhamDAO = new SanPhamDAO();
	}
	public boolean themCongDoan(CongDoanSanPham congDoan, SanPham sanPham) {
		if(timCongDoan(congDoan.getMaCongDoan(), sanPham.getMaSanPham())!= null)
			return false;
		int dem = 0;
		String sql = "INSERT INTO [dbo].[CONGDOANSANPHAM]\r\n"
				+ "           ([maCongDoan]\r\n"
				+ "           ,[tenCongDoan]\r\n"
				+ "           ,[tienCongDoan]\r\n"
				+ "           ,[soLuong]\r\n"
				+ "           ,[tinhTrang]\r\n"
				+ "           ,[noiDung]\r\n"
				+ "           ,[thuTu]\r\n"
				+ "           ,[maSanPham])\r\n"
				+ "     VALUES\r\n"
				+ "           (?,?,?,?,?,?,?,?)";
		try (PreparedStatement stmt  = con.prepareStatement(sql)){
			stmt.setString(1, congDoan.getMaCongDoan());
			stmt.setString(2, congDoan.getTenCongDoan());
			stmt.setDouble(3, congDoan.getTienCongDoan());
			stmt.setInt(4, congDoan.getSoLuong());
			stmt.setString(5, congDoan.getTinhTrang());
			stmt.setString(6, congDoan.getMoTa());
			stmt.setInt(7, congDoan.getThuTu());
			stmt.setString(8, congDoan.getSanPham().getMaSanPham());
			dem = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dem >0;
	}
	
	public CongDoanSanPham timCongDoan(String maCongDoan, String maSanPham) {
		String sql = "select * from CongDoanSanPham where maCongDoan = ? and maSanPham =?";
		CongDoanSanPham congDoan = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maCongDoan);
			stmt.setString(2, maSanPham);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				congDoan = new CongDoanSanPham(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), sanPhamDAO.timSanPham(rs.getString(8)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return congDoan;
	}
	public boolean xoaCongDoan(String maCongDoan, String maSanPham) {
		if(timCongDoan(maCongDoan, maSanPham)== null)
			return false;
		String sql = "delete from CongDoanSanPham where maCongDoan = ? and maSanPham = ?";
		int dem = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maCongDoan);
			stmt.setString(2, maSanPham);
			dem = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem >0;
		
	}
	public boolean capNhatCongDoan(CongDoanSanPham congDoan, SanPham sanPham) {
		if(timCongDoan(congDoan.getMaCongDoan(), sanPham.getMaSanPham())== null)
			return false;
		String sql = "UPDATE [dbo].[CONGDOANSANPHAM]\r\n"
				+ "   SET [tenCongDoan] = ? "
				+ "      ,[tienCongDoan] = ? "
				+ "      ,[soLuong] = ? "
				+ "      ,[tinhTrang] = ? "
				+ "      ,[noiDung] = ? "
				+ "      ,[thuTu] = ? "
				+ "      ,[maSanPham] = ? "
				+ " WHERE maCongDoan = ? AND maSanPham = ?";
		int dem = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, congDoan.getTenCongDoan());
			stmt.setDouble(2, congDoan.getTienCongDoan());
			stmt.setInt(3, congDoan.getSoLuong());
			stmt.setString(4, congDoan.getTinhTrang());
			stmt.setString(5, congDoan.getMoTa());
			stmt.setInt(6, congDoan.getThuTu());
			stmt.setString(7, congDoan.getSanPham().getMaSanPham());
			stmt.setString(8, congDoan.getMaCongDoan());
			stmt.setString(9, sanPham.getMaSanPham());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem >0;
	}
	public List<CongDoanSanPham> layDanhSachCongDoan(){
		List<CongDoanSanPham> dsCongDoan = new ArrayList<>();
		String sql = "select * from CongDoanSanPham";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				CongDoanSanPham congDoan = new CongDoanSanPham(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), sanPhamDAO.timSanPham(rs.getString(8)));
				dsCongDoan.add(congDoan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCongDoan;
	}
	public List<CongDoanSanPham> layDanhSachTheoMaSP(String maSP){
		List<CongDoanSanPham> dsCongDoan = new ArrayList<>();
		String sql = "select * from CongDoanSanPham where maSanPham = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maSP);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				CongDoanSanPham congDoan = new CongDoanSanPham(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), sanPhamDAO.timSanPham(rs.getString(8)));
				dsCongDoan.add(congDoan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCongDoan;
		
	}
	public List<CongDoanSanPham> timKiemCongDoanTrongSPTheoNhieuThuocTinh(String maCD, String tenCD, String giaTien, String maSP, String moTa, String thuTu, String soLuong) throws Exception {
		String sql = "select * from CongDoanSanPham ";
		String where = "where ";
		if (maCD != null || tenCD != null || giaTien != null || moTa != null || thuTu != null || soLuong != null) {
			List<CongDoanSanPham> dsCD = new ArrayList<>();
			if (maCD != null)
				where += " maCongDoan = '" + maCD + "'";
			else {
				if (tenCD != null)
					where += "and tenCongDoan like N'%" + tenCD.trim() + "%' ";
				if (giaTien != null)
					where += "and tienCongDoan = '" + Double.parseDouble(giaTien) + "' ";
				if (moTa != null)
					where += "and noiDung like N'%" + moTa.trim() + "%' ";
				if (thuTu != null)
					where += "and thuTu = '" + Integer.parseInt(thuTu) + "' ";
				if (soLuong != null)
					where += "and soLuong = '" + Integer.parseInt(soLuong) + "' ";
				where = where.replaceFirst("and", "");
			}
			sql += where +" and maSanPham = '" + maSP +"'";
			System.out.println(sql);
			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					CongDoanSanPham cd = new CongDoanSanPham(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), sanPhamDAO.timSanPham(rs.getString(8)));
					dsCD.add(cd);
				}
			}
			return dsCD;
		} else
			return layDanhSachTheoMaSP(maSP);
	}
	
	public String getMaCongDoanCaoNhatCuaSanPham(String maSanPham) throws Exception {
		String sql = "SELECT MAX(maCongDoan) FROM CongDoanSanPham where maSanPham = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSanPham);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		}
		return null;
	}
	
	public int getThuTuLamCuoiCuaCD(String maSanPham) throws Exception {
		String sql = "SELECT MAX(thuTu) FROM CongDoanSanPham where maSanPham = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSanPham);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		}
		return 0;
	}
}
