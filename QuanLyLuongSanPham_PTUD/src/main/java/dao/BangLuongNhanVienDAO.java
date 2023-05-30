package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import entity.BangChamCongNhanVien;
import entity.BangLuongNhanVien;

import entity.NhanVien;

public class BangLuongNhanVienDAO {
	private Connection con;
	private NhanVienDAO nhanVienDao;

	public BangLuongNhanVienDAO() {
		con = connectdb.ConnectDB.getInstance().getConnection();
		nhanVienDao = new NhanVienDAO();
	}

	public BangLuongNhanVien timBangLuongNhanVien(String maBangLuong) throws Exception {
		String sql = "select * from BangLuongNhanVien where maBangLuong = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maBangLuong);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new BangLuongNhanVien(rs.getString("maBangLuong"),
						nhanVienDao.timNhanVienBangMaNhanVien(rs.getString("maNhanVien")), rs.getDate("ngayTinhLuong"),
						rs.getDouble("luongCoBan"), rs.getDouble("phuCap"), rs.getDouble("heSoLuong"),
						rs.getInt("soPhepDuocNghi"));
			}
		}

		return null;
	}

	public BangLuongNhanVien timBangLuongNhanVienBangMaNhanVien(String maNhanVien) throws Exception {
		String sql = "select * from BangLuongNhanVien where maNhanVien = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new BangLuongNhanVien(rs.getString("maBangLuong"),
						nhanVienDao.timNhanVienBangMaNhanVien(rs.getString("maNhanVien")), rs.getDate("ngayTinhLuong"),
						rs.getDouble("luongCoBan"), rs.getDouble("phuCap"), rs.getDouble("heSoLuong"),
						rs.getInt("soPhepDuocNghi"));
			}
		}

		return null;
	}

	public List<BangLuongNhanVien> layDanhSachBangLuongNhanVien() {
		List<BangLuongNhanVien> danhSachBangLuongNhanVien = new ArrayList<>();
		String sql = "select * from BangLuongNhanVien";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangLuongNhanVien blnv = new BangLuongNhanVien(rs.getString("maBangLuong"),
						nhanVienDao.timNhanVienBangMaNhanVien(rs.getString("maNhanVien")), rs.getDate("ngayTinhLuong"),
						rs.getDouble("luongCoBan"), rs.getDouble("phuCap"), rs.getDouble("heSoLuong"),
						rs.getInt("soPhepDuocNghi"));
				danhSachBangLuongNhanVien.add(blnv);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return danhSachBangLuongNhanVien;
	}

	private String getMaBangLuongMoi() throws Exception {
		String maBangLuong = null;
		String maBangLuongCaoNhat = getMaBangLuongCaoNhat();
		if (maBangLuongCaoNhat == null) {
			maBangLuong = "BLNV0001";
		} else {
			int number = Integer.parseInt(maBangLuongCaoNhat.substring(4)) + 1;
			maBangLuong = String.format("BLNV%04d", number);
		}

		return maBangLuong;
	}

	
	public boolean capNhatMotBangLuong(BangLuongNhanVien bangLuongNhanVien) {

		int soDongThemDuoc = 0;
		String sql = "UPDATE [dbo].[BANGLUONGNHANVIEN]" + "           ([maBangLuong]" + "           ,[maNhanVien]"
				+ "           ,[ngayTinhLuong]" + "           ,[luongCoBan]" + "           ,[phuCap]"
				+ "           ,[heSoLuong]" + "           ,[soPhepDuocNghi])" + "     VALUES"
				+ "           (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, bangLuongNhanVien.getMaBangLuong());
			stmt.setString(2, bangLuongNhanVien.getNhanVien().getMaNhanVien());
			stmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			stmt.setDouble(4, bangLuongNhanVien.getLuongCoBan());
			stmt.setDouble(5, bangLuongNhanVien.getPhuCap());
			stmt.setDouble(6, bangLuongNhanVien.getHeSoLuong());
			stmt.setInt(7, bangLuongNhanVien.getSoPhepDuocNghi());

			soDongThemDuoc = stmt.executeUpdate();

			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public double tinhTongCongTrongThang(String maNhanVien, int thang, int nam) {
		// int tongCong = 0;
		// String trangThai = null;
		double tongCong = 0;
		String trangThai = null;

		String sql = "select  tinhTrangChamCong from BANGCHAMCONGNHANVIEN where maNhanVien = ? and month(ngayChamCong) = ? and YEAR(ngayChamCong) = ? ";

		// try(PreparedStatement stmt = con.prepareStatement(sql)) {
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);
			stmt.setInt(2, thang);
			stmt.setInt(3, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				trangThai = rs.getString(1);

				if (trangThai.equals("Nửa công")) {
					tongCong += 0.5;
				}

				else
					tongCong += 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tongCong;

	}

	public String getMaBangLuongCaoNhat() throws SQLException {
		String sql = "SELECT MAX(mabangluong) FROM bangluongnhanvien";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);

		}
		return null;
	}

	public void tinhTienLamThem(String maNhanVien, int thang, int nam) {

		int dem = 0;
		String sql = "UPDATE [dbo].[BANGLUONGNHANVIEN]\r\n" + "SET [phuCap] = (SELECT SUM(soGioLamThem) * 100000 \r\n"
				+ "                 FROM bangchamcongnhanvien \r\n"
				+ "                 WHERE MONTH(ngayChamCong) = ? \r\n"
				+ "                  AND YEAR(ngayChamCong) = ? \r\n" + "				  and manhanvien = ?)\r\n"
				+ "where maNhanVien = ?";

		// + "select phucap from BANGLUONGNHANVIEN" ;

		// try(PreparedStatement stmt = con.prepareStatement(sql)) {
		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, thang);
			stmt.setInt(2, nam);
			stmt.setString(3, maNhanVien);
			stmt.setString(4, maNhanVien);
			stmt.executeUpdate();
			dem += 1;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public BangLuongNhanVien themBangLuongNhanVien(String maNhanVien, int thang, int nam) throws Exception {

		// if bangchamcong.getngaychamcong(moth) < date.getnow(month) thi set cung ngay
		// lap bang luong = last(ngaychamcong

		int dem = 0;
		BangChamCongNhanVienDAO bangChamCongNhanVienDAO = new BangChamCongNhanVienDAO();
		NhanVien nhanVien = nhanVienDao.timNhanVienBangMaNhanVien(maNhanVien);
		BangChamCongNhanVien bangChamCongNhanVien = bangChamCongNhanVienDAO.timBangChamCongBangMaNhanVien(maNhanVien);

		String sql = "INSERT INTO [dbo].[BANGLUONGNHANVIEN]" + "           ([maBangLuong]" + "           ,[maNhanVien]"
				+ "           ,[ngayTinhLuong]" + "           ,[luongCoBan]" + "           ,[phuCap]"
				+ "           ,[heSoLuong]" + "           ,[soPhepDuocNghi])" + "     VALUES"
				+ "           (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, getMaBangLuongMoi());
			stmt.setString(2, maNhanVien);

			if (thang < new java.util.Date().getMonth() + 1) {
				stmt.setDate(3, bangChamCongNhanVienDAO.layNgayChamCongCuoiCung(thang, nam));
			} else
				stmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));

			if (nhanVien.getVaiTro().equals("Quản lý") && nhanVien.getTrinhDoChuyenMon().equals("Đại học")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 2.5);
			}

			if (nhanVien.getVaiTro().equals("Quản lý") && nhanVien.getTrinhDoChuyenMon().equals("Cao đẳng")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 2.3);
			}

			if (nhanVien.getVaiTro().equals("Quản lý") && nhanVien.getTrinhDoChuyenMon().equals("Trung cấp")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 2.0);
			}
			if (nhanVien.getVaiTro().equals("Kế toán") && nhanVien.getTrinhDoChuyenMon().equals("Đại học")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 1.8);
			}
			if (nhanVien.getVaiTro().equals("Kế toán") && nhanVien.getTrinhDoChuyenMon().equals("Cao đẳng")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 1.5);
			}

			if (nhanVien.getVaiTro().equals("Kế toán") && nhanVien.getTrinhDoChuyenMon().equals("Trung cấp")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 1.3);
			}

			if (nhanVien.getVaiTro().equals("Nhân viên") && nhanVien.getTrinhDoChuyenMon().equals("Đại học")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 1.8);
			}
			if (nhanVien.getVaiTro().equals("Nhân viên") && nhanVien.getTrinhDoChuyenMon().equals("Cao đẳng")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 1.5);
			}

			if (nhanVien.getVaiTro().equals("Nhân viên") && nhanVien.getTrinhDoChuyenMon().equals("Trung cấp")) {

				stmt.setDouble(4, 5500000);
				stmt.setDouble(5, 0);
				stmt.setDouble(6, 1.3);
			}

			
		
			stmt.setInt(7, 14 - tinhSoPhepDaNghi(maNhanVien, nam));

			dem = stmt.executeUpdate();
			tinhTienLamThem(maNhanVien, thang, nam);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	

	public int tinhSoPhepDaNghi(String maNhanVien, int nam) throws SQLException {
		int soPhepDaNghi = 0;
		String sql = "SELECT COUNT(*) AS ngayDaNghi\r\n"
				+ "FROM BANGCHAMCONGNHANVIEN\r\n"
				+ "WHERE maNhanVien = ? AND YEAR(ngaychamcong) = ?  " 
				+ "AND trangThai = N'Nghỉ có phép'";
	
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);
			stmt.setInt(2, nam);
		
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				soPhepDaNghi = rs.getInt(1);
				return soPhepDaNghi;
			}
				
			}
		

		return 0;

	}

	public String tinhLuongNhanVienTheoThang(BangChamCongNhanVien bangChamCongNhanVien, int thangTinhLuong,
			int namTinhLuong) throws Exception {

		double tongLuong = 0;
		double luongCoBan = 0;
		double tongCong = 0;
		NhanVienDAO nhanVienDAO = new NhanVienDAO();

		int namChamCong = (bangChamCongNhanVien.getNgayChamCong().getYear()) + 1900;
		int thangChamCong = (bangChamCongNhanVien.getNgayChamCong().getMonth()) + 1;
		String maNhanVien = bangChamCongNhanVien.getNhanVien().getMaNhanVien();
		BangLuongNhanVien bangLuongNhanVien = new BangLuongNhanVien();
		NhanVien nhanVien = nhanVienDAO.timNhanVienBangMaNhanVien(maNhanVien);

		if (thangChamCong == thangTinhLuong && namChamCong == namTinhLuong) {
			tongCong = tinhTongCongTrongThang(maNhanVien, thangTinhLuong, namTinhLuong);
			// tinhTienLamThem(maNhanVien, thangTinhLuong, namTinhLuong);
			// themBangLuongNhanVien(maNhanVien);

			String sql1 = "SELECT * FROM bangluongnhanvien bl join BANGCHAMCONGNHANVIEN cc on bl.maNhanVien = cc.maNhanVien\n"
					+ "WHERE bl.manhanvien = ? and MONTH(ngaytinhluong) = ? AND YEAR(ngaytinhluong) = ?";
			try (PreparedStatement stmt = con.prepareStatement(sql1)) {
				stmt.setString(1, maNhanVien);
				stmt.setInt(2, thangTinhLuong);
				stmt.setInt(3, namTinhLuong);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					bangLuongNhanVien = new BangLuongNhanVien(rs.getString("maBangLuong"),
							nhanVienDao.timNhanVienBangMaNhanVien(rs.getString("maNhanVien")),
							rs.getDate("ngayTinhLuong"), rs.getDouble("luongCoBan"), rs.getDouble("phucap"),
							rs.getDouble("heSoLuong"), 14 - tinhSoPhepDaNghi(maNhanVien, namTinhLuong));
					
					System.out.println(bangLuongNhanVien.getSoPhepDuocNghi());

				}

				// if(blnv.getNhanVien().getMaNhanVien() == null) {
				// blnv = themBangLuongNhanVien(maNhanVien);
				// }
			}

			// luongCoBan = (blnv.getHeSoLuong()* blnv.getLuongCoBan()+
			// blnv.getPhuCap())/26;
			// tongLuong = luongCoBan * tongCong;

		}

		luongCoBan = (bangLuongNhanVien.getHeSoLuong() * bangLuongNhanVien.getLuongCoBan()) / 26;
		tongLuong = luongCoBan * tongCong + bangLuongNhanVien.getPhuCap();

		return String.valueOf(tongLuong);

	}
	
	
	

	public List<BangLuongNhanVien> layDanhSachTinhLuong(int thang, int nam) throws SQLException {
		String sql = "SELECT * FROM bangluongnhanvien WHERE MONTH(ngayTinhLuong) = ? AND YEAR(ngayTinhLuong) = ?;";
		BangLuongNhanVien blnv = new BangLuongNhanVien();
		List<BangLuongNhanVien> dsBangLuong = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, thang);
			stmt.setInt(2, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				blnv = new BangLuongNhanVien(rs.getString("maBangLuong"),
						nhanVienDao.timNhanVienBangMaNhanVien(rs.getString("maNhanVien")), rs.getDate("ngayTinhLuong"),
						rs.getDouble("luongCoBan"), rs.getDouble("phucap"), rs.getDouble("heSoLuong"),
							14 - tinhSoPhepDaNghi(rs.getString("maNhanVien"), nam));
				dsBangLuong.add(blnv);

			}
		}
		return dsBangLuong;
	}

	public List<BangLuongNhanVien> timKiemNhieu(String maNhanVien, String tenNhanVien, String maBangLuong,
			String vaiTro, String heSoLuong, String luong, String thang, String nam, String phuCap)
			throws SQLException {

		String sql = "select * from BANGLUONGNHANVIEN bl join NHANVIEN nv on bl.maNhanVien = nv.maNhanVien  ";

		BangLuongNhanVien bangLuongNhanVien;
		List<String> thuocTinh = new ArrayList<>();
		List<Object> giaTri = new ArrayList<>();

		if (maNhanVien != null || tenNhanVien != null || maBangLuong != null || vaiTro != null || heSoLuong != null
				|| thang != "Tháng" && nam != "Năm" || phuCap != null) {
			List<BangLuongNhanVien> dsBL = new ArrayList<>();
			if (maNhanVien != null) {
				thuocTinh.add(" bl.manhanvien = ?");
				giaTri.add(maNhanVien);
			}
			if (tenNhanVien != null) {

				thuocTinh.add("nv.tenNhanVien like ?");
				giaTri.add("%" + tenNhanVien.trim() + "%");
			}
			if (maBangLuong != null) {
				thuocTinh.add("bl.mabangluong = ?");
				giaTri.add(maBangLuong);
			}
			if (vaiTro != null) {
				thuocTinh.add("nv.vaitro like ? ");
				giaTri.add("%" + vaiTro.trim() + "%");
			}
			if (heSoLuong != null) {
				thuocTinh.add("bl.hesoluong = ?");
				giaTri.add(heSoLuong);
			}

			if (thang.equals("All") && nam.equals("All")) {
				// No additional conditions needed
			} else if (!thang.equals("All") && !nam.equals("All")) {
				thuocTinh.add(" Month(ngaytinhluong) = ? AND Year(ngaytinhluong) = ?");
				giaTri.add(thang);
				giaTri.add(nam);
			} else if (thang.equals("All")) {
				thuocTinh.add(" Year(ngaytinhluong) = ?");
				giaTri.add(nam);
			} else if (nam.equals("All")) {
				thuocTinh.add(" Month(ngaytinhluong) = ?");
				giaTri.add(thang);
			}

			
//			if (luong != null) {
//				thuocTinh.add("bl.luongcoban = ?");
//				giaTri.add(luong);
//			}
//			if (phuCap != null) {
//				thuocTinh.add("bl.phucap = ?");
//				giaTri.add(phuCap);
//			}

			// where = where.replaceFirst("and", "");
			if (thuocTinh.size() != 0) {
				sql += " WHERE " + String.join(" AND ", thuocTinh);
			}

			System.out.println(sql);

			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				for (int i = 0; i < giaTri.size(); i++) {
					stmt.setObject(i + 1, giaTri.get(i));
				}
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					bangLuongNhanVien = new BangLuongNhanVien(rs.getString("maBangLuong"),
							nhanVienDao.timNhanVienBangMaNhanVien(rs.getString("maNhanVien")),
							rs.getDate("ngayTinhLuong"), rs.getDouble("luongCoBan"), rs.getDouble("phuCap"),
							rs.getDouble("heSoLuong"), rs.getInt("soPhepDuocNghi"));
					
					System.out.println(bangLuongNhanVien);
					dsBL.add(bangLuongNhanVien);
				}
			}

			System.out.println(dsBL);
			return dsBL;

		} else
			return layDanhSachBangLuongNhanVien();

	}

}
