package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import entity.BangLuongCongNhan;
import entity.BangPhanCongCongNhan;

public class BangLuongCongNhanDAO {
	private Connection con;
	private CongNhanDAO congNhanDao;
	private BangLuongCongNhan bangLuongCongNhan;

	public BangLuongCongNhanDAO() {
		con = connectdb.ConnectDB.getInstance().getConnection();
		congNhanDao = new CongNhanDAO();
		bangLuongCongNhan = new BangLuongCongNhan();
	}

	public BangLuongCongNhan timBangLuongCongNhan(String maBangLuong) throws Exception {
		String sql = "select * from BangLuongCongNhan where maBangLuong = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maBangLuong);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new BangLuongCongNhan(rs.getString("maBangLuong"),
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString("maCongNhan")),
						rs.getDate("ngayLapBangLuong"), rs.getDouble("luong"), rs.getDouble("tienthuongchuyencan"));
			}
		}
		return null;
	}

	public String getMaBangLuongCaoNhat() throws SQLException {
		String sql = "SELECT MAX(mabangluong) FROM bangluongcongnhan";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);

		}
		return null;
	}

	private String getMaBangLuongMoi() throws Exception {
		String maBangLuong = null;
		String maBangLuongCaoNhat = getMaBangLuongCaoNhat();
		if (maBangLuongCaoNhat == null) {
			maBangLuong = "BLCN0001";
		} else {
			int number = Integer.parseInt(maBangLuongCaoNhat.substring(4)) + 1;
			maBangLuong = String.format("BLCN%04d", number);
		}
		// System.out.println(maBangLuong);
		return maBangLuong;
	}
	// tinh luong cong nhan theo cong doan

	public List<List<String>> tinhLuongCongNhanChoTableModel(String thang, String nam, String toCongNhan) {
		List<List<String>> danhSachCongNhanDuocTinhLuong = new ArrayList<>();
		List<String> temp;
		String sql = "SELECT\r\n"
				+ "    PHANCONGCONGNHAN.maCongNhan,\r\n"
				+ "    cn.tenCongNhan,\r\n"
				+ "    sanPham.tenSanPham,\r\n"
				+ "    CongDoanSanPham.tenCongDoan,\r\n"
				+ "    SUM(BANGCHAMCONGCONGNHAN.soLuongLam) AS soLuongLamSum,\r\n"
				+ "    SUM(BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
				+ "        + SUM(CASE\r\n"
				+ "            WHEN BANGCHAMCONGCONGNHAN.caLam = N'Tối' OR BANGCHAMCONGCONGNHAN.caLam = N'Sáng CN' OR BANGCHAMCONGCONGNHAN.caLam = N'Chiều CN'\r\n"
				+ "            THEN (BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
				+ "            ELSE 0\r\n"
				+ "        END) AS tongTienChoCongDoanVoiTangCa,\r\n"
				+ "    CASE\r\n"
				+ "        WHEN (\r\n"
				+ "            SELECT COUNT(tinhTrangChamCong)\r\n"
				+ "            FROM BANGCHAMCONGCONGNHAN\r\n"
				+ "            WHERE PHANCONGCONGNHAN.maPhanCong = BANGCHAMCONGCONGNHAN.maPhanCong and tinhTrangChamCong = N'Có mặt'  \r\n"
				+ "        ) >= 50 THEN 500000\r\n"
				+ "        ELSE 0\r\n"
				+ "    END AS tienThuong,\r\n"
				+ "    CONCAT(MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong), '-', YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong)) AS ngayChamCong\r\n"
				+ "FROM\r\n"
				+ "    BANGCHAMCONGCONGNHAN\r\n"
				+ "    JOIN PHANCONGCONGNHAN ON BANGCHAMCONGCONGNHAN.maPhanCong = PHANCONGCONGNHAN.maPhanCong\r\n"
				+ "    JOIN CongDoanSanPham ON PHANCONGCONGNHAN.MASANPHAM = CongDoanSanPham.MASANPHAM\r\n"
				+ "        AND PHANCONGCONGNHAN.MACONGDOAN = CongDoanSanPham.MACONGDOAN\r\n"
				+ "    JOIN sanPham ON PHANCONGCONGNHAN.MASANPHAM = sanPham.MASANPHAM\r\n"
				+ "    JOIN CONGNHAN cn ON cn.maCongNhan = PHANCONGCONGNHAN.maCongNhan\r\n"
				+ "    JOIN TONHOM tn ON cn.maTo = tn.maTo  WHERE  MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong)= ? "
				+ "	AND YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong)= ?  GROUP BY PHANCONGCONGNHAN.maCongNhan,cn.tenCongNhan, PHANCONGCONGNHAN.MASANPHAM, PHANCONGCONGNHAN.MACONGDOAN, sanPham.tenSanPham, CongDoanSanPham.tenCongDoan, PHANCONGCONGNHAN.maPhanCong , MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) ,\r\n"
				+ "     YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong) \r\n"
				+ "";

		if (!toCongNhan.equalsIgnoreCase("All"))
			sql = sql.replace("%%", "%" + toCongNhan + "%");
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, thang);
			stmt.setString(2, nam);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				temp = new ArrayList<>();
				temp.add(rs.getString(1));
				temp.add(rs.getString(2));
				temp.add(rs.getString(3));
				temp.add(rs.getString(4));
				temp.add(rs.getString(5));
				temp.add(rs.getString(6));
				
				
//				if (duocThuongChuyenCan( thang, nam, rs.getString(1))) {
//					temp.add("500000");
//				}
//				else {
//					temp.add("0");
//				}
				temp.add(rs.getString(7));
				temp.add(rs.getString(8));
				
				System.out.println(temp);

				danhSachCongNhanDuocTinhLuong.add(temp);

			}

		} catch (Exception e) {
		}
		return danhSachCongNhanDuocTinhLuong;
	}

	public boolean diLamDayDu(String thang, String nam, String maCongNhan) {
		String sql = "select COUNT(cc.maChamCong) AS soLuongBangChamCong\r\n"
				+ "                FROM BANGCHAMCONGCONGNHAN cc join PHANCONGCONGNHAN pc on cc.maPhanCong = pc.maPhanCong\r\n"
				+ "				WHERE MONTH(cc.ngayChamCong) =?"
				+ "               AND YEAR(cc.ngayChamCong) = ? and pc.maCongNhan = ?" + "";
		return false;

	}

	public List<BangLuongCongNhan> tinhLuongCongNhan(String thang, String nam, String toCongNhan) {
		List<BangLuongCongNhan> dsBangLuong = new ArrayList<>();
		String sql = "	 SELECT PHANCONGCONGNHAN.maCongNhan, \r\n"
				+ "                SUM(BANGCHAMCONGCONGNHAN.soLuongLam) AS soLuongLamSum,\r\n"
				+ "               SUM(BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money)) + SUM(CASE WHEN BANGCHAMCONGCONGNHAN.caLam = N'Tối' \r\n"
				+ "	OR BANGCHAMCONGCONGNHAN.caLam = N'Sáng CN' OR BANGCHAMCONGCONGNHAN.caLam = N'Chiều CN'  \r\n"
				+ "	THEN (BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money)) ELSE 0 END) AS tongTienChoCongDoanVoiTangCa\r\n"
				+ "                FROM BANGCHAMCONGCONGNHAN\r\n" + "                INNER JOIN PHANCONGCONGNHAN\r\n"
				+ "                ON BANGCHAMCONGCONGNHAN.maPhanCong = PHANCONGCONGNHAN.maPhanCong\r\n"
				+ "                INNER JOIN CongDoanSanPham\r\n"
				+ "                ON PHANCONGCONGNHAN.MASANPHAM = CongDoanSanPham.MASANPHAM\r\n"
				+ "               AND PHANCONGCONGNHAN.MACONGDOAN = CongDoanSanPham.MACONGDOAN\r\n"
				+ "                INNER JOIN sanPham\r\n"
				+ "               ON PHANCONGCONGNHAN.MASANPHAM = sanPham.MASANPHAM\r\n"
				+ "               inner join CONGNHAN cn on cn.maCongNhan = PHANCONGCONGNHAN.maCongNhan\r\n"
				+ "              inner join TONHOM tn on cn.maTo = tn.maTo\r\n"
				+ "                WHERE MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) = ?\r\n"
				+ "               AND YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong) = ? and tn.TENTO LIKE N'%%'\r\n"
				+ "               GROUP BY PHANCONGCONGNHAN.maCongNhan";
		if (!toCongNhan.equalsIgnoreCase("ALL")) {
			sql = sql.replace("%%", "%" + toCongNhan + "%");
		}
		
		
		
		System.out.println(sql);
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, thang);
			stmt.setString(2, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				
				
				BangLuongCongNhan bangLuong = new BangLuongCongNhan(null,
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString(1)),
						new java.sql.Date(new java.util.Date().getTime()), Double.parseDouble(rs.getString(3)), 
						0);

				
				
			
				
				
				if(duocThuongChuyenCan(thang, nam, rs.getString(1))) {
					bangLuong.setTienThuongChuyenCan(500000);
				}
				
				
				dsBangLuong.add(bangLuong);
			}

		} catch (Exception e) {
		}
		return dsBangLuong;
	}

	public boolean capNhatBangLuong(BangLuongCongNhan bangLuongCongNhan, double luongMoi) throws SQLException {

		if (bangLuongCongNhan.getLuong() < luongMoi) {
			String sql = "UPDATE [dbo].[BANGLUONGCONGNHAN]\r\n" + "   SET \r\n" + "      [luong] = ?\r\n"
					+ " WHERE maBangLuong = ?";

			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				stmt.setDouble(1, luongMoi);
				stmt.setString(2, bangLuongCongNhan.getMaBangLuong());
				stmt.executeUpdate();
				return true;
			}

		}

		return false;

	}
	
	
	public boolean capNhatTienThuongChuyenCan(BangLuongCongNhan bangLuongCongNhan, double tienThuong) throws SQLException {

			String sql = "UPDATE [dbo].[BANGLUONGCONGNHAN]\r\n" + "   SET \r\n" + "      [tienthuongChuyencan] = ?\r\n"
					+ " WHERE maBangLuong = ?";

			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				stmt.setDouble(1, tienThuong);
				stmt.setString(2, bangLuongCongNhan.getMaBangLuong());
				stmt.executeUpdate();
				return true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return false;
	}
	
	public boolean duocThuongChuyenCan(String thang, String nam, String maCongNhan) throws SQLException {
	    String sql = "SELECT maCongNhan, SUM(soBangChamCong) AS tongBangChamCong " +
	            "FROM (" +
	            "  SELECT maCongNhan, ngayChamCong, COUNT(*) AS soBangChamCong " +
	            "  FROM BANGCHAMCONGCONGNHAN bcc JOIN PHANCONGCONGNHAN pc ON bcc.maPhanCong = pc.maPhanCong " +
	            "  WHERE maCongNhan = ? AND MONTH(ngayChamCong) = ? AND YEAR(ngayChamCong) = ? " +
	            "  GROUP BY maCongNhan, ngayChamCong " +
	            ") AS subquery " +
	            "GROUP BY maCongNhan";

	    int tongBangChamCong = 0;

	    try (PreparedStatement stmt = con.prepareStatement(sql)) {
	        stmt.setString(1, maCongNhan);
	        stmt.setString(2, thang);
	        stmt.setString(3, nam);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            tongBangChamCong = rs.getInt("tongBangChamCong");

	            

	            if (tongBangChamCong >= 50) {
	                return true;
	            }
	        }
	    }
	    return false;
	}



	public List<BangLuongCongNhan> layDanhSachBangLuongCongNhan() {
		List<BangLuongCongNhan> danhSachBangLuongCongNhan = new ArrayList<>();
		BangLuongCongNhan bangLuongCongNhan;

		String sql = "select * from BangLuongCongNhan";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				bangLuongCongNhan = new BangLuongCongNhan(rs.getString("maBangLuong"),
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString("maCongNhan")),
						rs.getDate("ngayLapBangLuong"), rs.getDouble("luong"), rs.getDouble("tienThuongChuyenCan"));
				danhSachBangLuongCongNhan.add(bangLuongCongNhan);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return danhSachBangLuongCongNhan;
	}

	public List<BangLuongCongNhan> layDanhSachBangLuong(String thang, String nam, String toNhom) {
		String sql = "select blcn.* from bangluongcongnhan blcn join congnhan cn on cn.maCongNhan = blcn.maCongNhan\r\n"
				+ "join toNhom tn on tn.maTo = cn.maTo\r\n"
				+ "where month(ngayLapBangLuong) = ? and year(ngayLapBangLuong) = ? and tn.tenTo like N'%%'";
		if (!toNhom.equalsIgnoreCase("All"))
			sql = sql.replace("%%", "%" + toNhom + "%");
		
		List<BangLuongCongNhan> danhSachBangLuong = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, thang);
			stmt.setString(2, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangLuongCongNhan bangLuong = new BangLuongCongNhan(rs.getString(1),
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString(2)), rs.getDate(3), rs.getDouble(4), rs.getDouble(5));
				danhSachBangLuong.add(bangLuong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return danhSachBangLuong;
	}

	public List<BangLuongCongNhan> layDanhSachBangLuongTheoThang(String thang, String toNhom) {
		String sql = "select blcn.* from bangluongcongnhan blcn join congnhan cn on cn.maCongNhan = blcn.maCongNhan\r\n"
				+ "join toNhom tn on tn.maTo = cn.maTo\r\n"
				+ "where month(ngayLapBangLuong) = ?  and tn.tenTo like N'%%'";
		if (!toNhom.equalsIgnoreCase("All"))
			sql = sql.replace("%%", "%" + toNhom + "%");
		
		
		List<BangLuongCongNhan> danhSachBangLuong = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, thang);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangLuongCongNhan bangLuong = new BangLuongCongNhan(rs.getString(1),
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString(2)), rs.getDate(3), rs.getDouble(4), rs.getDouble(5));
				danhSachBangLuong.add(bangLuong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return danhSachBangLuong;
	}
	
	public List<BangLuongCongNhan> layDanhSachBangLuongTheoNam(String nam, String toNhom) {
		String sql = "select blcn.* from bangluongcongnhan blcn join congnhan cn on cn.maCongNhan = blcn.maCongNhan\r\n"
				+ "join toNhom tn on tn.maTo = cn.maTo\r\n"
				+ "where year(ngayLapBangLuong) = ?  and tn.tenTo like N'%%'";
		if (!toNhom.equalsIgnoreCase("All"))
			sql = sql.replace("%%", "%" + toNhom + "%");
		
		
		List<BangLuongCongNhan> danhSachBangLuong = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangLuongCongNhan bangLuong = new BangLuongCongNhan(rs.getString(1),
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString(2)), rs.getDate(3), rs.getDouble(4), rs.getDouble(5));
				danhSachBangLuong.add(bangLuong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return danhSachBangLuong;
	}
	
	
	public boolean themBangLuongCongNhan(BangLuongCongNhan bangLuong) throws Exception {

		int dem = 0;
		String sql = "INSERT INTO [dbo].[BANGLUONGCONGNHAN]\r\n"
				+ "           ([maBangLuong]\r\n"
				+ "           ,[maCongNhan]\r\n"
				+ "           ,[ngayLapBangLuong]\r\n"
				+ "           ,[luong]\r\n"
				+ "           ,[tienThuongChuyenCan])\r\n"
				+ "     VALUES\r\n"
				+ "           (?, ?, ?, ?, ?)";
				

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			String maBangLuongMoi = getMaBangLuongMoi();
			stmt.setString(1, maBangLuongMoi);
			stmt.setString(2, bangLuong.getCongNhan().getMaCongNhan());
			stmt.setDate(3, new Date(bangLuong.getNgayLapBangLuong().getTime()));
			stmt.setDouble(4, bangLuong.getLuong());
			stmt.setDouble(5, bangLuong.getTienThuongChuyenCan());
			
			
			dem = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem > 0;
	}

	public BangLuongCongNhan timBangLuongTheoMaCongNhan(String maCongNhan)
			throws SQLException {
		String sql1 = "SELECT * FROM bangluongcongnhan WHERE macongnhan = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql1)) {
			stmt.setString(1, maCongNhan);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bangLuongCongNhan = new BangLuongCongNhan(rs.getString("maBangLuong"),
						congNhanDao.timCongNhanBangMaCongNhan(rs.getString("maCongNhan")),
						rs.getDate("ngayLapBangLuong"), rs.getDouble("luong"), rs.getDouble("tienThuongChuyenCan"));

			}
		}
		return bangLuongCongNhan;
	}

	
	

	public boolean kiemTraThangTinhLuong(String thangTinhLuong, String namTinhLuong) {
		LocalDate thoiGianHienTai = LocalDate.now();
		if (thoiGianHienTai.getMonthValue() == Integer.parseInt(thangTinhLuong)
				&& thoiGianHienTai.getYear() == Integer.parseInt(namTinhLuong))
			return true;
		return false;
	}

	public boolean themBangLuongCongNhanTheoThang(BangLuongCongNhan bangLuong, String thangTinhLuong,
			String namTinhLuong) {

		int dem = 0;
		int year = Integer.parseInt(namTinhLuong);
		int month = Integer.parseInt(thangTinhLuong);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int lastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date ngayCuoiTrongThang = new Date(year - 1900, month - 1, lastDate);

		String sql = "INSERT INTO [dbo].[BANGLUONGCONGNHAN]\r\n"
				+ "           ([maBangLuong]\r\n"
				+ "           ,[maCongNhan]\r\n"
				+ "           ,[ngayLapBangLuong]\r\n"
				+ "           ,[luong]\r\n"
				+ "           ,[tienThuongChuyenCan])\r\n"
				+ "     VALUES\r\n"
				+ "           (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			String maBangLuongMoi = getMaBangLuongMoi();
			stmt.setString(1, maBangLuongMoi);
			stmt.setString(2, bangLuong.getCongNhan().getMaCongNhan());
			stmt.setDate(3, ngayCuoiTrongThang);
			stmt.setDouble(4, bangLuong.getLuong());
			stmt.setDouble(5, bangLuong.getTienThuongChuyenCan());
			dem = stmt.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return dem > 0;
	}

	public List<List<String>> timKiemBangLuongCongNhan(String maCongNhan, String tenCongNhan, String tenCongDoan,
			String soLuongHoanThanh, String luong, String toNhom, String thang, String nam) {
		List<List<String>> danhSachCongNhanTimDuoc = new ArrayList<>();
		List<String> thuocTinh = new ArrayList<>();
		List<Object> giaTri = new ArrayList<>();
		List<String> temp;
		String having = null;

		String sql = "SELECT\r\n"
				+ "    PHANCONGCONGNHAN.maCongNhan,\r\n"
				+ "    cn.tenCongNhan,\r\n"
				+ "    sanPham.tenSanPham,\r\n"
				+ "    CongDoanSanPham.tenCongDoan,\r\n"
				+ "    SUM(BANGCHAMCONGCONGNHAN.soLuongLam) AS soLuongLamSum,\r\n"
				+ "    SUM(BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
				+ "        + SUM(CASE\r\n"
				+ "            WHEN BANGCHAMCONGCONGNHAN.caLam = N'Tối' OR BANGCHAMCONGCONGNHAN.caLam = N'Sáng CN' OR BANGCHAMCONGCONGNHAN.caLam = N'Chiều CN'\r\n"
				+ "            THEN (BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
				+ "            ELSE 0\r\n"
				+ "        END) AS tongTienChoCongDoanVoiTangCa,\r\n"
				+ "    CASE\r\n"
				+ "        WHEN (\r\n"
				+ "            SELECT COUNT(tinhTrangChamCong)\r\n"
				+ "            FROM BANGCHAMCONGCONGNHAN\r\n"
				+ "            WHERE PHANCONGCONGNHAN.maPhanCong = BANGCHAMCONGCONGNHAN.maPhanCong\r\n and tinhTrangChamCong = N'Có mặt' "
				+ "        ) >= 50 THEN 500000\r\n"
				+ "        ELSE 0\r\n"
				+ "    END AS tienThuong,\r\n"
				+ "    CONCAT(MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong), '-', YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong)) AS ngayChamCong\r\n"
				+ "FROM\r\n"
				+ "    BANGCHAMCONGCONGNHAN\r\n"
				+ "    JOIN PHANCONGCONGNHAN ON BANGCHAMCONGCONGNHAN.maPhanCong = PHANCONGCONGNHAN.maPhanCong\r\n"
				+ "    JOIN CongDoanSanPham ON PHANCONGCONGNHAN.MASANPHAM = CongDoanSanPham.MASANPHAM\r\n"
				+ "        AND PHANCONGCONGNHAN.MACONGDOAN = CongDoanSanPham.MACONGDOAN\r\n"
				+ "    JOIN sanPham ON PHANCONGCONGNHAN.MASANPHAM = sanPham.MASANPHAM\r\n"
				+ "    JOIN CONGNHAN cn ON cn.maCongNhan = PHANCONGCONGNHAN.maCongNhan\r\n"
				+ "    JOIN TONHOM tn ON cn.maTo = tn.maTo  ";
				
		//
		// + "WHERE MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) = ? \n"
		// + "AND YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong) = ? and tn.TENTO LIKE
		// N'%%'\n";
		//

		String groupby = " GROUP BY PHANCONGCONGNHAN.maCongNhan,cn.tenCongNhan, PHANCONGCONGNHAN.MASANPHAM, PHANCONGCONGNHAN.MACONGDOAN, sanPham.tenSanPham, CongDoanSanPham.tenCongDoan, PHANCONGCONGNHAN.maPhanCong , MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) ,\r\n"
				+ "     YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong) ";
		List<BangLuongCongNhan> danhSachBangLuong = new ArrayList<>();
		if (thang.equals("All") && nam.equals("All")) {
			
		
//			groupby = groupby + ","
//					+ "	 MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) , Year(BANGCHAMCONGCONGNHAN.ngayChamCong) ";
			
			
		} else if (!thang.equals("All") && !nam.equals("All")) {
			thuocTinh.add(" MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong)= ? AND YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong)= ?");
			giaTri.add(thang);
			giaTri.add(nam);
			
			danhSachBangLuong = layDanhSachBangLuong(thang, nam, toNhom);
		} else if (thang.equals("All") && !nam.equals("All") ) {
			thuocTinh.add(" YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong) = ?");
			giaTri.add(nam);
//			groupby = groupby + ","
//					+ "	 MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) ";
			
			
			
			
		} else if (nam.equals("All") && !thang.equals("All") ) {
			thuocTinh.add(" MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) = ?");
			giaTri.add(thang);
//			groupby = groupby + ","
//					+ "	 Year(BANGCHAMCONGCONGNHAN.ngayChamCong) ";
			
			
		}

//		if (!thang.equalsIgnoreCase("All")) {
//			thuocTinh.add("MONTH(BANGCHAMCONGCONGNHAN.ngayChamCong) = ?");
//			giaTri.add(thang);
//		}
//		if (!nam.equalsIgnoreCase("All")) {
//			thuocTinh.add("YEAR(BANGCHAMCONGCONGNHAN.ngayChamCong) = ?");
//			giaTri.add(nam);
//		}
		if (!toNhom.equalsIgnoreCase("All")) {
			thuocTinh.add(" tn.TENTO LIKE ?");
			giaTri.add("%" + toNhom + "%");
		}
		if (tenCongNhan != null) {
			thuocTinh.add("cn.tenCongNhan like ?");
			giaTri.add("%" + tenCongNhan.trim() + "%");
		}
		if (maCongNhan != null) {
			thuocTinh.add("cn.maCongNhan = ?");
			giaTri.add(maCongNhan);
		}
		if (tenCongDoan != null) {
			thuocTinh.add("congDoanSanPham.tencongdoan like ?");
			giaTri.add("%" + tenCongDoan.trim() + "%");
		}

		

		if (soLuongHoanThanh != null) {
			if(having == null) {

			having = "Having SUM(BANGCHAMCONGCONGNHAN.soLuongLam) = " + soLuongHoanThanh + " ";
			}
			
		}
		
		if (luong != null) {
//			thuocTinh.add(
//					" CAST(BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money) AS varchar(50)) = ?");
			luong = luong.replace("₫", "");

			luong = luong.replace(".", "");
//			    	
			luong = luong.replaceAll("[^\\d\\s.]", "").replaceAll("\\s", "");
			
			if(having == null) {
			having += "Having SUM(BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
					+ "        + SUM(CASE\r\n"
					+ "            WHEN BANGCHAMCONGCONGNHAN.caLam = N'Tối' OR BANGCHAMCONGCONGNHAN.caLam = N'Sáng CN' OR BANGCHAMCONGCONGNHAN.caLam = N'Chiều CN'\r\n"
					+ "            THEN (BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
					+ "            ELSE 0\r\n"
					+ "        END) = " + luong + " ";
		}
			else {
				having += " and SUM(BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
						+ "        + SUM(CASE\r\n"
						+ "            WHEN BANGCHAMCONGCONGNHAN.caLam = N'Tối' OR BANGCHAMCONGCONGNHAN.caLam = N'Sáng CN' OR BANGCHAMCONGCONGNHAN.caLam = N'Chiều CN'\r\n"
						+ "            THEN (BANGCHAMCONGCONGNHAN.soLuongLam * CAST(CongDoanSanPham.tienCongDoan AS money))\r\n"
						+ "            ELSE 0\r\n"
						+ "        END) = " + luong + " ";
			}
		
		}

		if (!thuocTinh.isEmpty()) {
			sql += "  WHERE " + String.join(" AND ", thuocTinh);

		}
		sql = sql + " " + groupby;
		
		if (having != null) {
			having += 
			sql = sql + " " +  having;
		}



	
		
		
		
		
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
		    int thuTu = 1;
		    for (Object value : giaTri) {
		        stmt.setObject(thuTu++, value);
		    }
		    ResultSet rs = stmt.executeQuery();

		    
	
		    
		    while (rs.next()) {
		        temp = new ArrayList<>();
		        temp.add(rs.getString(1));
		        temp.add(rs.getString(2));
		        temp.add(rs.getString(3));
		        temp.add(rs.getString(4));
		        temp.add(rs.getString(5));
		        temp.add(rs.getString(6));
		        
		        
		     
		        
		        temp.add(rs.getString(7));
		        temp.add(rs.getString(8));

		        danhSachCongNhanTimDuoc.add(temp);
		        
		    }
		    
		}
		


		catch (Exception e) {
			e.printStackTrace();
		}

		return danhSachCongNhanTimDuoc;
	}

}
