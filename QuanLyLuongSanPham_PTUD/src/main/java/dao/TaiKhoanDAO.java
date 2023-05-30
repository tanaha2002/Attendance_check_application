package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectdb.ConnectDB;
import entity.TaiKhoan;

public class TaiKhoanDAO {
	private Connection con;
	private NhanVienDAO nhanVienDAO;
	public TaiKhoanDAO() {
		// TODO Auto-generated constructor stub
		con = ConnectDB.getInstance().getConnection();
		nhanVienDAO = new NhanVienDAO();
	}
	public boolean themTaiKhoan(TaiKhoan tk) throws Exception {
		if(timTaiKhoan(tk.getTenDangNhap()) !=null) {
			return false;
		}
		String sql = "INSERT INTO [dbo].[TAIKHOAN]\r\n"
				+ "           ([tendangnhap]\r\n"
				+ "           ,[matkhau]\r\n"
				+ "           ,[loaiTaiKhoan]\r\n"
				+ "           ,[maNhanVien])\r\n"
				+ "     VALUES\r\n"
				+ "           (?,?,?,?)";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, tk.getTenDangNhap());
			stmt.setString(2, tk.getMatKhau());
			stmt.setString(3, tk.getLoaiTaiKhoan());
			stmt.setString(4, tk.getNhanVien().getMaNhanVien());
			n = stmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return n>0;
	}

	public TaiKhoan timTaiKhoan(String tenTK) throws Exception {
		String sql = "select * from TaiKhoan where tendangnhap = ? ";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, tenTK);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return new TaiKhoan(rs.getString(1),rs.getString(2),rs.getString(3),nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(4)));
			}
		}
		return null;
	}
	public List<TaiKhoan> layDanhSachTK() throws Exception {
		List<TaiKhoan> dsTK = new ArrayList<>();
		String sql = "select * from TaiKhoan";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				TaiKhoan tk = new TaiKhoan(rs.getString(1),rs.getString(2),rs.getString(3),nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(4)));
				dsTK.add(tk);
			}
		}
		return dsTK;
	}
	public boolean doiMatKhau(TaiKhoan tk) throws Exception {
		if(timTaiKhoan(tk.getTenDangNhap())!=null){	
			String sql = "UPDATE [dbo].[TAIKHOAN]\r\n"
					+ "      ,[matkhau] = ? "
					+ " WHERE tendangnhap = ?";
			
			try(PreparedStatement stmt = con.prepareStatement(sql)){
				stmt.setString(1, tk.getMatKhau());
				stmt.setString(2, tk.getTenDangNhap());
				int n = stmt.executeUpdate();
				return n>0;
			}		
		}
		return false;
	}
	public boolean xoaTaiKhoan(String tenTaiKhoan) throws Exception {
		String sql = "DELETE FROM [dbo].[TaiKhoan] "
				+ "      WHERE tendangnhap = ? ";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, tenTaiKhoan);
			n = stmt.executeUpdate();
		}
		return n>0;
	}
}
