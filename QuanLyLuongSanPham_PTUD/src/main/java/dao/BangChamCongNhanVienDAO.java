package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import connectdb.ConnectDB;
import entity.BangChamCongNhanVien;
import entity.NhanVien;

public class BangChamCongNhanVienDAO {
	private Connection con;
	private NhanVienDAO nhanVienDAO;

	public BangChamCongNhanVienDAO() {
		// TODO Auto-generated constructor stub
		con = ConnectDB.getInstance().getConnection();
		nhanVienDAO = new NhanVienDAO();
	}

	public List<BangChamCongNhanVien> layDanhSachCCNV() {
		List<BangChamCongNhanVien> dsCCNV = new ArrayList<>();
		String sql = "select * from BANGCHAMCONGNHANVIEN";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangChamCongNhanVien chamCongNV = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2),
						rs.getDate(3), rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
				dsCCNV.add(chamCongNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCCNV;
	}

	public List<BangChamCongNhanVien> layDanhSachCCNV(int thang, int nam) {
		List<BangChamCongNhanVien> dsCCNV = new ArrayList<>();
		String sql = "select * from BANGCHAMCONGNHANVIEN where month(ngaychamcong) = ? and year(ngaychamcong) = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, thang);
			stmt.setInt(2, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangChamCongNhanVien chamCongNV = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2),
						rs.getDate(3), rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
				dsCCNV.add(chamCongNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCCNV;
	}
	public List<BangChamCongNhanVien> layDanhSachCCNVHomNay(Date ngay){
		List<BangChamCongNhanVien> dsCCNV = new ArrayList<>();
		String sql = "select * from BANGCHAMCONGNHANVIEN where ngaychamcong = ?";
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setDate(1, ngay);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				BangChamCongNhanVien chamCongNV = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2),
						rs.getDate(3), rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
				dsCCNV.add(chamCongNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCCNV;
	}
	//danh sách nhân viên chưa được chấm công hôm nay
	public List<NhanVien> layDanhSachNhanVienChuaChamCong(Date ngay){
		List<BangChamCongNhanVien> dsCCNVHomNay = layDanhSachCCNVHomNay(ngay);
		List<NhanVien> dsNV = nhanVienDAO.layDanhSachNhanVien();
		for (BangChamCongNhanVien ccnv : dsCCNVHomNay) {
	        NhanVien nv = ccnv.getNhanVien();

	        if (dsNV.contains(nv)) {
	        	dsNV.remove(nv);
	        }
	    }

		
		return dsNV;
	}
	public BangChamCongNhanVien timThongTinChamCongNhanVien(String maChamCong) {
		String sql = "select * from BANGCHAMCONGNHANVIEN where maChamCongNV = ?";
		BangChamCongNhanVien chamCongNhanVien = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maChamCong);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				chamCongNhanVien = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2), rs.getDate(3),
						rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chamCongNhanVien;
	}
	public String layMaChamCongCaoNhat() {
		String sql = "select MAX(maChamCongNV) from BANGCHAMCONGNHANVIEN";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public boolean themChamCongNhanVien(BangChamCongNhanVien nhanVienDuocChamCong) {
		String sql = "INSERT INTO [dbo].[BANGCHAMCONGNHANVIEN]\r\n" + "           ([maChamCongNV]\r\n"
				+ "           ,[soGioLamThem]\r\n" + "           ,[ngayChamCong]\r\n" + "           ,[trangThai]\r\n"
				+ "           ,[maNguoiChamCong]\r\n" + "           ,[maNhanVien]\r\n"
				+ "           ,[tinhTrangChamCong])\r\n" + "     VALUES\r\n" + "           (?,?,?,?,?,?,?)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nhanVienDuocChamCong.getMaChamCong());
			stmt.setDouble(2, nhanVienDuocChamCong.getSoGioLamThem());
			stmt.setDate(3, new Date(nhanVienDuocChamCong.getNgayChamCong().getTime()));
			stmt.setString(4, nhanVienDuocChamCong.getTrangThai());
			stmt.setString(5, nhanVienDuocChamCong.getNguoiChamCong().getMaNhanVien());
			stmt.setString(6, nhanVienDuocChamCong.getNhanVien().getMaNhanVien());
			stmt.setString(7, nhanVienDuocChamCong.getTinhTrangChamCong());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	public Date layNgayChamCongCuoiCung(int thang, int nam) {
		Date ngayCuoiCung = null;
		String sql = "SELECT"
				+ "     EOMONTH(ngaychamcong) AS LastDayDate FROM BANGCHAMCONGNHANVIEN\r\n"
				+ "	 where MONTH(ngaychamcong) = ? and year(ngayChamCong) = ?"
				+ "	 	 group by EOMONTH(ngaychamcong)";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, thang);
			stmt.setInt(2, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ngayCuoiCung = rs.getDate(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ngayCuoiCung;

	}

	public boolean xoaChamCongNhanVien(String maChamCong) {
		int dem = 0;

		String sql = "delete from BANGCHAMCONGNHANVIEN where maChamCongNV = ?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maChamCong);

			dem = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem > 0;
	}

	public boolean capNhatChamCongNV(BangChamCongNhanVien nhanVienDuocCapNhat) {
		String sql = "UPDATE [dbo].[BANGCHAMCONGNHANVIEN]\r\n" + "    SET  [soGioLamThem] = ? "
				+ "      ,[ngayChamCong] = ? " + "      ,[trangThai] = ? " + "      ,[tinhTrangChamCong] = ? "
				+ " WHERE maChamCongNV = ? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setDouble(1, nhanVienDuocCapNhat.getSoGioLamThem());
			stmt.setDate(2, new Date(nhanVienDuocCapNhat.getNgayChamCong().getTime()));
			stmt.setString(3, nhanVienDuocCapNhat.getTrangThai());
			stmt.setString(4, nhanVienDuocCapNhat.getTinhTrangChamCong());
			stmt.setString(5, nhanVienDuocCapNhat.getMaChamCong());
			stmt.executeUpdate();
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	public List<BangChamCongNhanVien> layDanhSachCCNVTheoNhanVien(String maNhanVien, int thang, int nam) {
		List<BangChamCongNhanVien> dsCCNV = new ArrayList<>();
		String sql = "select * from BANGCHAMCONGNHANVIEN where maNhanVien = ? and month(ngaychamcong) = ? and year(ngaychamcong) = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);
			stmt.setInt(2, thang);
			stmt.setInt(3, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangChamCongNhanVien chamCongNV = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2),
						rs.getDate(3), rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
				dsCCNV.add(chamCongNV);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCCNV;
	}

	public List<BangChamCongNhanVien> timKiemChamCongNhieuThuocTinh(String maNV, String tenNV,
			java.util.Date ngayChamCong, java.util.Date denNgay, String soGioLamThem, String tinhTrang, String maNguoiChamCong,
			String tenNguoiChamCong, String tinhTrangChamCong, int kieuTimKiem) {
		List<BangChamCongNhanVien> dsCCNV = new ArrayList<>();
		String sql = "SELECT CCNV.*, NVC.tenNhanVien AS tenNguoiChamCong, NV.tenNhanVien FROM BANGCHAMCONGNHANVIEN CCNV JOIN NHANVIEN NVC ON CCNV.maNguoiChamCong = NVC.maNhanVien JOIN NHANVIEN NV ON CCNV.maNhanVien = NV.maNhanVien ";
		List<String> thuocTinh = new ArrayList<>();
		List<Object> giaTri = new ArrayList<>();
		if (maNV != null && !maNV.isEmpty()) {
			thuocTinh.add("CCNV.maNhanVien = ?");
			giaTri.add(maNV);
		}
		if (tenNV != null && !tenNV.isEmpty()) {
			thuocTinh.add("NV.tenNhanVien LIKE ?");
			giaTri.add("%" + tenNV + "%");
		}
		if(kieuTimKiem ==1) {
			if (ngayChamCong != null) {
				thuocTinh.add("CCNV.ngayChamCong = ?");
				java.sql.Date ngayCham = new java.sql.Date(ngayChamCong.getTime());
				giaTri.add(ngayCham);
			}
		}
		if(kieuTimKiem == 2) {
			if (ngayChamCong != null) {
				thuocTinh.add("MONTH(CCNV.ngayChamCong) = ?");
				giaTri.add(ngayChamCong.getMonth() + 1);
				thuocTinh.add("YEAR(CCNV.ngayChamCong) = ?");
				giaTri.add(ngayChamCong.getYear() + 1900);
			}
		}
		if(kieuTimKiem == 3) {
			if (ngayChamCong != null && denNgay != null) {
				thuocTinh.add("CCNV.ngayChamCong between ? ");
				java.sql.Date ngayCham = new java.sql.Date(ngayChamCong.getTime());
				giaTri.add(ngayCham);
				thuocTinh.add(" ? ");
				java.sql.Date ngayCham2 = new java.sql.Date(denNgay.getTime());
				giaTri.add(ngayCham2);
			}
			
		}
		if (soGioLamThem != null) {
			thuocTinh.add("CCNV.soGioLamThem = ?");
			giaTri.add(soGioLamThem);
		}
		if (tinhTrang != null) {
			thuocTinh.add("CCNV.trangThai = ?");
			giaTri.add(tinhTrang);
		}
		if (maNguoiChamCong != null && !maNguoiChamCong.isEmpty()) {
			thuocTinh.add("CCNV.maNguoiChamCong = ?");
			giaTri.add(maNguoiChamCong);
		}
		if (tenNguoiChamCong != null && !tenNguoiChamCong.isEmpty()) {
			thuocTinh.add("NVC.tenNhanVien LIKE ?");
			giaTri.add("%" + tenNguoiChamCong + "%");
		}
		if (tinhTrangChamCong != null) {
			thuocTinh.add("CCNV.tinhTrangChamCong LIKE ?");
			giaTri.add("%" + tinhTrangChamCong + "%");
		}
		if (thuocTinh.size() > 0) {
			sql += "WHERE " + String.join(" AND ", thuocTinh);
		}
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			int i = 1;
			for (Object value : giaTri) {
				stmt.setObject(i++, value);
			}
	        System.out.println(sql);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					BangChamCongNhanVien bangChamCong = new BangChamCongNhanVien();
					bangChamCong.setMaChamCong(rs.getString("maChamCongNV"));
					bangChamCong.setSoGioLamThem(rs.getDouble("soGioLamThem"));
					bangChamCong.setNgayChamCong(rs.getDate("ngayChamCong"));
					bangChamCong.setTrangThai(rs.getString("trangThai"));
					NhanVien nguoiChamCong = new NhanVien();
					nguoiChamCong.setMaNhanVien(rs.getString("maNguoiChamCong"));
					nguoiChamCong.setTenNhanVien(rs.getString("tenNguoiChamCong"));
					bangChamCong.setNguoiChamCong(nguoiChamCong);
					bangChamCong.setTinhTrangChamCong(rs.getString("tinhTrangChamCong"));
					NhanVien nhanVien = new NhanVien();
					nhanVien.setMaNhanVien(rs.getString("maNhanVien"));
					nhanVien.setTenNhanVien(rs.getString("tenNhanVien"));
					bangChamCong.setNhanVien(nhanVien);
					dsCCNV.add(bangChamCong);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsCCNV;
	}

	public BangChamCongNhanVien timBangChamCongBangMaNhanVien(String maNhanVien,int thang, int nam) {
		String sql = "select * from bangchamcongnhanvien where manhanvien like N'" + maNhanVien + "' and month(ngayChamCong) = "+ thang+" and year(ngayChamCong) = "+ nam +" ";
		BangChamCongNhanVien bangChamCongNV = new BangChamCongNhanVien();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bangChamCongNV = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2), rs.getDate(3),
						rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bangChamCongNV;
	}
	public BangChamCongNhanVien timBangChamCongBangMaNhanVien(String maNhanVien) {
		String sql = "select * from bangchamcongnhanvien where manhanvien like N'" + maNhanVien + "' ";
		BangChamCongNhanVien bangChamCongNV = new BangChamCongNhanVien();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bangChamCongNV = new BangChamCongNhanVien(rs.getString(1), rs.getFloat(2), rs.getDate(3),
						rs.getString(4), nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(5)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(6)), rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bangChamCongNV;
	}

	
}
