package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connectdb.ConnectDB;
import entity.BangChamCongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongNhan;
import entity.NhanVien;

public class BangChamCongCongNhanDAO {
	private Connection con;
	private BangPhanCongCongNhanDAO bangPhanCongCongNhanDAO;
	private NhanVienDAO nhanVienDAO;
	private DiaChiDAO diaChiDAO;
	private ToNhomDAO toNhomDAO;

	public BangChamCongCongNhanDAO() {
		// TODO Auto-generated constructor stub
		con = ConnectDB.getInstance().getConnection();
		bangPhanCongCongNhanDAO = new BangPhanCongCongNhanDAO();
		nhanVienDAO = new NhanVienDAO();
		diaChiDAO = new DiaChiDAO();
		toNhomDAO = new ToNhomDAO();
	}

	public List<BangChamCongCongNhan> layDanhSachChamCongCN() {
		String sql = "select * from BANGCHAMCONGCONGNHAN";
		List<BangChamCongCongNhan> dsCCCN = new ArrayList<>();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangChamCongCongNhan bangChamCong = new BangChamCongCongNhan(rs.getString(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getInt(5),
						bangPhanCongCongNhanDAO.timPhanCongCongNhan(rs.getString(6)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
				dsCCCN.add(bangChamCong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCCCN;
	}

	public boolean checkMaPhanCongInBangChamCong(String maPhanCong) {
		String sql = "SELECT * FROM BANGCHAMCONGCONGNHAN WHERE maPhanCong = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPhanCong);
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

	public BangChamCongCongNhan timCongNhanBangMaCongNhan(String maCongNhan) {
		String sql = "select * from BANGCHAMCONGCONGNHAN where macongnhan = ?";
		BangChamCongCongNhan bangChamCongCongNhan = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCongNhan);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bangChamCongCongNhan = new BangChamCongCongNhan(rs.getString(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getInt(5),
						bangPhanCongCongNhanDAO.timPhanCongCongNhan(rs.getString(6)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bangChamCongCongNhan;

	}

	public BangChamCongCongNhan timCongNhanBangMaPhanCong(String maPhanCong) {
		String sql = "select * from BANGCHAMCONGCONGNHAN where maphancong = ?";
		BangChamCongCongNhan bangChamCongCongNhan = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPhanCong);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bangChamCongCongNhan = new BangChamCongCongNhan(rs.getString(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getInt(5),
						bangPhanCongCongNhanDAO.timPhanCongCongNhan(rs.getString(6)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bangChamCongCongNhan;

	}

	public Date layNgayChamCongCuoiCung(int thang, int nam) {
		Date ngayCuoiCung = null;
		String sql = "SELECT" + "     EOMONTH(ngaychamcong) AS LastDayDate FROM BANGCHAMCONGCONGNHAN\r\n"
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

	public BangChamCongCongNhan timThongTinChamCong(String maChamCong) {
		String sql = "select * from BANGCHAMCONGCONGNHAN where maChamCong = ? ";
		BangChamCongCongNhan chamCongCongNhan = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maChamCong);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				chamCongCongNhan = new BangChamCongCongNhan(rs.getString(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getInt(5),
						bangPhanCongCongNhanDAO.timPhanCongCongNhan(rs.getString(6)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return chamCongCongNhan;
	}

	public int getSLHoanThanhCuaCongDoan(String maCD, String maSP) {
		String sql = "SELECT pccn.maSanPham, pccn.maCongDoan, SUM(cc.soLuongLam) AS tong_soluong "
				+ "FROM BANGCHAMCONGCONGNHAN cc " + "JOIN PHANCONGCONGNHAN pccn ON cc.maPhanCong = pccn.maPhanCong "
				+ "WHERE pccn.maCongDoan = ? AND pccn.maSanPham = ? " + "GROUP BY pccn.maSanPham, pccn.maCongDoan";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCD);
			stmt.setString(2, maSP);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("tong_soluong");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int getSLHoanThanhCongDoanCuaCN(String maPC) {
		String sql = "SELECT SUM(soLuongLam) AS tong_soluong\r\n" + "				FROM BANGCHAMCONGCONGNHAN cc\r\n"
				+ "				JOIN PHANCONGCONGNHAN pccn ON cc.maPhanCong = pccn.maPhanCong\r\n"
				+ "				where cc.maPhanCong = ? ";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPC);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("tong_soluong");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String layMaChamCongCaoNhat() {
		String sql = "select MAX(maChamCong) from BANGCHAMCONGCONGNHAN";
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

	// public boolean themChamCongCongNhan(BangChamCongCongNhan thongTinChamCong) {
	// String sql = ""
	// }
	public boolean themChamCongCongNhan(BangChamCongCongNhan thongTinChamCong) {
		String sql = "INSERT INTO [dbo].[BANGCHAMCONGCONGNHAN]\r\n"
				+ "           ([maChamCong]\r\n"
				+ "           ,[ngayChamCong]\r\n"
				+ "           ,[caLam]\r\n"
				+ "           ,[tinhTrangChamCong]\r\n"
				+ "           ,[soLuongLam]\r\n"
				+ "           ,[maPhanCong]\r\n"
				+ "           ,[maNguoiChamCong])\r\n"
				+ "     VALUES\r\n"
				+ "           (?,?,?,?,?,?,?)\r\n";
		int n = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, thongTinChamCong.getMaChamCong());
			stmt.setDate(2, new java.sql.Date(thongTinChamCong.getNgayChamCong().getTime()));
			stmt.setString(3, thongTinChamCong.getCaLam());
			stmt.setString(4, thongTinChamCong.getTrangthai());
			stmt.setInt(5, thongTinChamCong.getSoLuongLam());
			stmt.setString(6, thongTinChamCong.getPhanCong().getMaPhanCong());
			stmt.setString(7, thongTinChamCong.getNguoiChamCong().getMaNhanVien());
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean xoaChamCongNhanVien(String maChamCong) {
		String sql = "DELETE FROM [dbo].[BANGCHAMCONGCONGNHAN]\r\n" + "      WHERE maChamCong = ?";
		int n = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maChamCong);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return n > 0;
	}
	//lấy danh sách chấm công hôm nay
	public List<BangChamCongCongNhan> layDanhSachChamCongHomNay(Date ngay){
		List<BangChamCongCongNhan> dsCC = new ArrayList<>();
		String sql = "select * from BANGCHAMCONGCONGNHAN where ngaychamcong  = ? ";
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setDate(1, new java.sql.Date(ngay.getTime()));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BangChamCongCongNhan bangChamCong = new BangChamCongCongNhan(rs.getString(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getInt(5),
						bangPhanCongCongNhanDAO.timPhanCongCongNhan(rs.getString(6)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
				dsCC.add(bangChamCong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCC;
	}

	public List<BangChamCongCongNhan> timKiemChamCongTheoNhieuThuocTinh(String maCN, String tenCN, String to,
			Date ngayChamCong,Date denNgay, String caLam, String tinhTrangChamCong, String maCD, String tenCD, String maSP, String tenSP, String soLuongLam,
			String maNguoiChamCong, String tenNguoiChamCong, int kieuTimKiem) {

		List<BangChamCongCongNhan> dsCCCN = new ArrayList<>();
		String sql = "select cccn.*,nv.maNhanVien from BANGCHAMCONGCONGNHAN cccn join PHANCONGCONGNHAN "
				+ "pccn on cccn.maPhanCong= pccn.maPhanCong join CONGNHAN cn on pccn.maCongNhan = cn.maCongNhan join CONGDOANSANPHAM cdsp on "
				+ "pccn.maCongDoan = cdsp.maCongDoan join SANPHAM sp on pccn.maSanPham = sp.maSanPham join NHANVIEN nv on "
				+ "cccn.maNguoiChamCong = nv.maNhanVien join TONHOM tn on cn.maTo = tn.maTo  ";
		List<String> thuocTinh = new ArrayList<>();
		List<Object> giaTri = new ArrayList<>();
		if (maCN != null) {
			thuocTinh.add("pccn.maCongNhan = ?");
			giaTri.add(maCN);
		}
		if (tenCN != null) {
			thuocTinh.add("cn.tenCongNhan LIKE ?");
			giaTri.add("%" + tenCN + "%");

		}
		if (to != null) {
			thuocTinh.add("tn.tenTo = ?");
			giaTri.add(to);
		}
		if(kieuTimKiem ==1) {
			if (ngayChamCong != null) {
				thuocTinh.add("cccn.ngayChamCong = ?");
				java.sql.Date ngayCham = new java.sql.Date(ngayChamCong.getTime());
				giaTri.add(ngayCham);
			}
		}
		if(kieuTimKiem == 2) {
			if (ngayChamCong != null) {
				thuocTinh.add("MONTH(cccn.ngayChamCong) = ?");
				giaTri.add(ngayChamCong.getMonth() + 1);
				thuocTinh.add("YEAR(cccn.ngayChamCong) = ?");
				giaTri.add(ngayChamCong.getYear() + 1900);
			}
		}
		if(kieuTimKiem == 3) {
			if (ngayChamCong != null && denNgay != null) {
				thuocTinh.add("cccn.ngayChamCong between ? ");
				java.sql.Date ngayCham = new java.sql.Date(ngayChamCong.getTime());
				giaTri.add(ngayCham);
				thuocTinh.add(" ? ");
				java.sql.Date ngayCham2 = new java.sql.Date(denNgay.getTime());
				giaTri.add(ngayCham2);
			}
			
		}
		if (caLam != null) {
			thuocTinh.add("cccn.caLam = ?");
			giaTri.add(caLam);
		}

		if (tinhTrangChamCong != null) {
			thuocTinh.add("cccn.tinhTrangChamCong = ?");
			giaTri.add(tinhTrangChamCong);
		}
		
		if (maCD != null) {
			thuocTinh.add("cdsp.maCongDoan = ?");
			giaTri.add(maCD);
		}
		if (tenCD != null) {
			thuocTinh.add("cdsp.tenCongDoan LIKE ?");
			giaTri.add("%" + tenCD + "%");
		}
		if (maSP != null) {
			thuocTinh.add("sp.maSanPham = ?");
			giaTri.add(maSP);
		}
		if (tenSP != null) {
			thuocTinh.add("sp.tenSanPham LIKE ?");
			giaTri.add("%" + tenSP + "%");
		}
		if (soLuongLam != null) {
			thuocTinh.add("cccn.soLuongLam = ?");
			giaTri.add(Integer.parseInt(soLuongLam));
		}
		if (maNguoiChamCong != null) {
			thuocTinh.add("cccn.maNguoiChamCong = ?");
			giaTri.add(maNguoiChamCong);
		}
		if (tenNguoiChamCong != null) {
			thuocTinh.add("nv.tenNhanVien LIKE ?");
			giaTri.add("%" + tenNguoiChamCong + "%");
		}
		if (!thuocTinh.isEmpty()) {
			sql += "where " + String.join(" and ", thuocTinh);
			
		}
		String groupBy = " group by cccn.maChamCong,cccn.ngayChamCong,cccn.soLuongLam,cccn.maPhanCong, cccn.caLam, cccn.tinhTrangChamCong, cccn.maNguoiChamCong, nv.maNhanVien";
		sql += groupBy;
		System.out.println(sql);
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			int i = 1;
			for (Object value : giaTri) {
				System.out.println("Value" + value);
				stmt.setObject(i++, value);
			}
			
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				BangChamCongCongNhan chamCongCongNhan = new BangChamCongCongNhan();
				chamCongCongNhan.setMaChamCong(rs.getString("maChamCong"));
				chamCongCongNhan.setNgayChamCong(rs.getDate("ngayChamCong"));
				chamCongCongNhan.setCaLam(rs.getString("caLam"));
				chamCongCongNhan.setTrangthai(rs.getString("tinhTrangChamCong"));
				BangPhanCongCongNhan phanCongCN = bangPhanCongCongNhanDAO
						.timPhanCongCongNhan(rs.getString("maPhanCong"));
				chamCongCongNhan.setPhanCong(phanCongCN);
				chamCongCongNhan.setSoLuongLam(Integer.parseInt(rs.getString("soLuongLam")));
				NhanVien nhanVienChamCong = nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString("maNhanVien"));
				chamCongCongNhan.setNguoiChamCong(nhanVienChamCong);
				dsCCCN.add(chamCongCongNhan);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dsCCCN;
	}
public List<BangChamCongCongNhan> layDanhSachChamCongTheoThang(String maCongNhan, int thang, int nam){
		
		String sql = "select * from BANGCHAMCONGCONGNHAN cc join PHANCONGCONGNHAN  pc"
				+ " on cc.maPhanCong = pc.maPhanCong where pc.maCongNhan = ? and MONTH(ngayChamCong) = ? and YEAR(ngayChamCong) = ?"
				+ "";
		List<BangChamCongCongNhan> dsBangChamCongCongNhan = new ArrayList<>();
		BangChamCongCongNhan bangChamCongCongNhan = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCongNhan );
			stmt.setInt(2, thang );
			stmt.setInt(3, nam);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				bangChamCongCongNhan = new BangChamCongCongNhan(rs.getString(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getInt(5),
						bangPhanCongCongNhanDAO.timPhanCongCongNhan(rs.getString(6)),
						nhanVienDAO.timNhanVienBangMaNhanVien(rs.getString(7)));
				dsBangChamCongCongNhan.add(bangChamCongCongNhan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsBangChamCongCongNhan;
	}
	public double tinhTienChamCong(double giaTien,String caLam,int soLuong) {
		if(caLam.equalsIgnoreCase("Tối") || caLam.equalsIgnoreCase("Sáng CN") || caLam.equalsIgnoreCase("Chiều CN")) {
			giaTien *= 1.5;
		}
		return giaTien * soLuong;
	}

}
