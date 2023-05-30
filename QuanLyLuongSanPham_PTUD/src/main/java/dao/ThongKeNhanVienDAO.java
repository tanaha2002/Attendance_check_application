package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectdb.ConnectDB;

public class ThongKeNhanVienDAO {
	private Connection con;
	public ThongKeNhanVienDAO() {
		// TODO Auto-generated constructor stub
		con = ConnectDB.getInstance().getConnection();
	}
	public List<List<String>> topNhanVienLuong(String loaiThongKe,String tieuChi2,String thang,String nam){
		List<List<String>> dsTopNamNhanVien = new ArrayList<>();
		List<String> temp;
		String sql = "SELECT TOP 5  NV.maNhanVien,NV.tenNhanVien, \r\n"
				+ "  (((BL.luongCoBan * BL.heSoLuong) / 26) * \r\n"
				+ "  (SELECT SUM(CASE \r\n"
				+ "                  WHEN BBCC.tinhTrangChamCong LIKE N'%Nữa công%' THEN 0.5\r\n"
				+ "                  WHEN BBCC.tinhTrangChamCong LIKE N'%Đủ công%' THEN 1\r\n"
				+ "                  ELSE 0\r\n"
				+ "                END)\r\n"
				+ "   FROM BANGCHAMCONGNHANVIEN BBCC \r\n"
				+ "   WHERE NV.maNhanVien = BBCC.maNhanVien \r\n"
				+ "   AND YEAR(BBCC.ngayChamCong) = ? AND MONTH(BBCC.ngayChamCong) = ?\r\n"
				+ "  )) + BL.phuCap AS salary\r\n"
				+ "FROM NHANVIEN NV\r\n"
				+ "JOIN BANGLUONGNHANVIEN BL ON NV.maNhanVien = BL.maNhanVien\r\n"
				+ "LEFT JOIN BANGCHAMCONGNHANVIEN BBCC ON NV.maNhanVien = BBCC.maNhanVien\r\n"
				+ "  AND YEAR(BBCC.ngayChamCong) = ? AND MONTH(BBCC.ngayChamCong) = ? \r\n"
				+ "GROUP BY  NV.maNhanVien,NV.tenNhanVien, BL.luongCoBan, BL.heSoLuong, BL.phuCap\r\n"
				+ "HAVING (((BL.luongCoBan * BL.heSoLuong) / 26) *\r\n"
				+ "(SELECT SUM(CASE\r\n"
				+ "WHEN BBCC.tinhTrangChamCong LIKE N'%Nữa công%' THEN 0.5\r\n"
				+ "WHEN BBCC.tinhTrangChamCong LIKE N'%Đủ công%' THEN 1\r\n"
				+ "ELSE 0\r\n"
				+ "END)\r\n"
				+ "FROM BANGCHAMCONGNHANVIEN BBCC\r\n"
				+ "WHERE NV.maNhanVien = BBCC.maNhanVien\r\n"
				+ "AND YEAR(BBCC.ngayChamCong) = ? AND MONTH(BBCC.ngayChamCong) = ? \r\n"
				+ ")) + BL.phuCap > 0 order by salary DESC ";
		if(loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
			sql = sql.replace("YEAR(BBCC.ngayChamCong) = ? AND MONTH(BBCC.ngayChamCong) = ?", "YEAR(BBCC.ngayChamCong) = ?");
				
				
			
		}
		if(tieuChi2.contains("thấp nhất")) {
			sql = sql.replace("DESC", " ");
		}
		
		if(tieuChi2.contains("Top 10")) {
			sql = sql.replace("TOP 5", "TOP 10");
		}
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			if(loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
				stmt.setString(1, nam);
				stmt.setString(2, nam);
				stmt.setString(3, nam);
				
			}
			else {
				stmt.setString(1, nam);
				stmt.setString(2, thang);
				stmt.setString(3, nam);
				stmt.setString(4, thang);
				stmt.setString(5, nam);
				stmt.setString(6, thang);
				
			}
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				temp = new ArrayList<>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				dsTopNamNhanVien.add(temp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsTopNamNhanVien;
	}
	public List<List<String>> luongNhanTheoKhoangThoiGian(String loaiThongKe,String tieuChi1,String tieuChi2,String tuThang,String tuNam,String denThang,String denNam){
		List<List<String>> dsLuongNhanVien = new ArrayList<>();
		List<String> temp;
		LocalDate ngayBatDau = LocalDate.of(Integer.parseInt(tuNam), Integer.parseInt(tuThang), 1);
		LocalDate ngayKetThuc = LocalDate.of(Integer.parseInt(denNam), Integer.parseInt(denThang), 1);
		ngayKetThuc = ngayKetThuc.withDayOfMonth(ngayKetThuc.lengthOfMonth());

		String sql= " SELECT TOP 5  NV.maNhanVien,NV.tenNhanVien, \r\n"
				+ "  (((BL.luongCoBan * BL.heSoLuong) / 26) * \r\n"
				+ "  (SELECT SUM(CASE \r\n"
				+ "                  WHEN BBCC.tinhTrangChamCong LIKE N'%Nữa công%' THEN 0.5\r\n"
				+ "                  WHEN BBCC.tinhTrangChamCong LIKE N'%Đủ công%' THEN 1\r\n"
				+ "                  ELSE 0\r\n"
				+ "                END)\r\n"
				+ "   FROM BANGCHAMCONGNHANVIEN BBCC \r\n"
				+ "   WHERE NV.maNhanVien = BBCC.maNhanVien \r\n"
				+ "   AND BBCC.ngayChamCong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "' \r\n"
				+ "  )) + BL.phuCap AS salary\r\n"
				+ "FROM NHANVIEN NV\r\n"
				+ "JOIN BANGLUONGNHANVIEN BL ON NV.maNhanVien = BL.maNhanVien\r\n"
				+ "LEFT JOIN BANGCHAMCONGNHANVIEN BBCC ON NV.maNhanVien = BBCC.maNhanVien\r\n"
				+ "  AND BBCC.ngayChamCong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "' \r\n"
				+ "GROUP BY  NV.maNhanVien,NV.tenNhanVien, BL.luongCoBan, BL.heSoLuong, BL.phuCap\r\n"
				+ "HAVING (((BL.luongCoBan * BL.heSoLuong) / 26) *\r\n"
				+ "(SELECT SUM(CASE\r\n"
				+ "WHEN BBCC.tinhTrangChamCong LIKE N'%Nữa công%' THEN 0.5\r\n"
				+ "WHEN BBCC.tinhTrangChamCong LIKE N'%Đủ công%' THEN 1\r\n"
				+ "ELSE 0\r\n"
				+ "END)\r\n"
				+ "FROM BANGCHAMCONGNHANVIEN BBCC\r\n"
				+ "WHERE NV.maNhanVien = BBCC.maNhanVien\r\n"
				+ "AND BBCC.ngayChamCong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "' \r\n"
				+ ")) + BL.phuCap > 0\r\n"
				+ "order by salary DESC";
		if(tieuChi2.contains("thấp nhất")) {
			sql = sql.replace("DESC", " ");
		}
		
		if(tieuChi2.contains("Top 10")) {
			sql = sql.replace("TOP 5", "TOP 10");
		}
		try (PreparedStatement stmt =con.prepareStatement(sql)){
			ResultSet rs =stmt.executeQuery();
			while(rs.next()) {
				temp = new ArrayList<>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				dsLuongNhanVien.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsLuongNhanVien;
	}
	
}
