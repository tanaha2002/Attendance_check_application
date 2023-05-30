package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import connectdb.ConnectDB;

import java.util.ArrayList;
import java.util.List;
public class ThongKeDoanhThuDAO {
	private Connection con;
	public ThongKeDoanhThuDAO() {
		// TODO Auto-generated constructor stub
		con = ConnectDB.getInstance().getConnection();
	}
	public List<List<String>> topNamSanPhamHoanThanh(String tieuChi,String thang,String nam, String loaiThongKe){
		
		List<List<String>> dsSanPhamHoanThanh = new ArrayList<>();
		List<String> temp;
		String sql = "SELECT sp.tenSanPham, MIN(COALESCE(t.soLuongLam, 0)) AS soLuongHoanThanh,sp.giaTien,(sp.giaTien * MIN(COALESCE(t.soLuongLam, 0))) as TongDoanhThu\r\n"
				+ "FROM SANPHAM sp\r\n"
				+ "JOIN CONGDOANSANPHAM cd ON cd.maSanPham = sp.maSanPham\r\n"
				+ "LEFT JOIN (\r\n"
				+ "SELECT pc.maSanPham, pc.maCongDoan, SUM(bc.soLuongLam) AS soLuongLam\r\n"
				+ "FROM PHANCONGCONGNHAN pc\r\n"
				+ "JOIN BANGCHAMCONGCONGNHAN bc ON bc.maPhanCong = pc.maPhanCong where month(bc.ngayChamCong) = ? and year(bc.ngayChamCong) = ? \r\n"
				+ "GROUP BY pc.maSanPham, pc.maCongDoan\r\n"
				+ ") t ON cd.maCongDoan = t.maCongDoan AND cd.maSanPham = t.maSanPham\r\n"
				+ "GROUP BY sp.maSanPham, sp.tenSanPham, sp.giaTien, sp.moTaSanPham, sp.soLuongSanXuat, sp.chatLieu, sp.kichThuoc, sp.anhSanPham, sp.soLuongCongDoan\r\n"
				+ "HAVING MIN(COALESCE(t.soLuongLam, 0)) > 0 ORDER BY TongDoanhThu DESC ";
		if(loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
			sql = sql.replace("month(bc.ngayChamCong) = ? and year(bc.ngayChamCong) = ? ", "year(bc.ngayChamCong) = ? ");
		}
		if(tieuChi.equalsIgnoreCase("Top 5 sản phẩm có doanh thu ít nhất")) {
			sql = sql.replace("DESC", " ");
		}
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			if(loaiThongKe.equalsIgnoreCase("Thống kê theo năm")) {
				stmt.setString(1, nam);
			}
			else {
				stmt.setString(1, thang);
				stmt.setString(2, nam);
			}
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				temp = new ArrayList<>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				dsSanPhamHoanThanh.add(temp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsSanPhamHoanThanh;
	}
	public List<List<String>> topSanPhamTrongKhoangThoiGian(String tieuChi,String loaiThongKe, String tuThang, String tuNam, String denThang,String denNam){
		List<List<String>> dsTopSanPham = new ArrayList<>();
		List<String> temp;
		LocalDate ngayBatDau = LocalDate.of(Integer.parseInt(tuNam), Integer.parseInt(tuThang), 1);
        LocalDate ngayKetThuc = LocalDate.of(Integer.parseInt(denNam), Integer.parseInt(denThang), 1);
		ngayKetThuc = ngayKetThuc.withDayOfMonth(ngayKetThuc.lengthOfMonth());

		String sql = "SELECT sp.tenSanPham, MIN(COALESCE(t.soLuongLam, 0)) AS soLuongHoanThanh,sp.giaTien,(sp.giaTien * MIN(COALESCE(t.soLuongLam, 0))) as TongDoanhThu\r\n"
				+ "FROM SANPHAM sp\r\n"
				+ "JOIN CONGDOANSANPHAM cd ON cd.maSanPham = sp.maSanPham\r\n"
				+ "LEFT JOIN (\r\n"
				+ "SELECT pc.maSanPham, pc.maCongDoan, SUM(bc.soLuongLam) AS soLuongLam\r\n"
				+ "FROM PHANCONGCONGNHAN pc\r\n"
				+ "JOIN BANGCHAMCONGCONGNHAN bc ON bc.maPhanCong = pc.maPhanCong where bc.ngayChamCong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "' \r\n"
				+ "GROUP BY pc.maSanPham, pc.maCongDoan\r\n"
				+ ") t ON cd.maCongDoan = t.maCongDoan AND cd.maSanPham = t.maSanPham\r\n"
				+ "GROUP BY sp.maSanPham, sp.tenSanPham, sp.giaTien, sp.moTaSanPham, sp.soLuongSanXuat, sp.chatLieu, sp.kichThuoc, sp.anhSanPham, sp.soLuongCongDoan\r\n"
				+ "HAVING MIN(COALESCE(t.soLuongLam, 0)) > 0\r\n"
				+ "ORDER BY TongDoanhThu DESC ";
		if(tieuChi.equalsIgnoreCase("Top 5 sản phẩm có doanh thu ít nhất"))
			sql = sql.replace("DESC", " ");
		try (PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				temp = new ArrayList<>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				dsTopSanPham.add(temp);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsTopSanPham;
		
	}
}
