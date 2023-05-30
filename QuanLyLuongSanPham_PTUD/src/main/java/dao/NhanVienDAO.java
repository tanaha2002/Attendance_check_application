package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import connectdb.ConnectDB;
import entity.CongNhan;
import entity.DiaChi;
import entity.NhanVien;
import entity.ToNhom;

public class NhanVienDAO {
	private Connection con;
	private DiaChiDAO diaChiDAO;

	public NhanVienDAO() {
		con = ConnectDB.getInstance().getConnection();
		diaChiDAO = new DiaChiDAO();

	}

	public List<NhanVien> layDanhSachNhanVien() {
		List<NhanVien> dsNhanVien = new ArrayList<>();
		String sql = "select * from NHANVIEN";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				NhanVien nhanvien = new NhanVien(rs.getString(1), rs.getString(2),rs.getDate(3),rs.getString(4) , rs.getString(5),diaChiDAO.layMaDiaChi(rs.getString(6)),
						rs.getDate(7),rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11),rs.getString(12));
				dsNhanVien.add(nhanvien);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsNhanVien;
	}

	public boolean themNhanVien(NhanVien nhanVien) {
		if (timNhanVienBangMaNhanVien(nhanVien.getMaNhanVien())!= null)
			return false;
		
		String sql = "INSERT INTO [dbo].[NHANVIEN]"
				+ "           ([maNhanVien]"
				+ "           ,[tenNhanVien]"
				+ "           ,[ngaySinh]"
				+ "           ,[email]"
				+ "           ,[sdt]"
				+ "           ,[maDC]"
				+ "           ,[ngayVaoLam]"
				+ "           ,[gioiTinh]"
				+ "           ,[vaiTro]"
				+ "           ,[anhDaiDien]"
				+ "           ,[trinhDoChuyenMon]"
				+ "           ,[trinhDoNgoaiNgu])"
				+ "     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nhanVien.getMaNhanVien());
			stmt.setString(2, nhanVien.getTenNhanVien());
			stmt.setDate(3, new Date(nhanVien.getNgaySinh().getTime()));
			stmt.setString(4, nhanVien.getEmail());
			stmt.setString(5, nhanVien.getSoDienThoai());
			stmt.setString(6, nhanVien.getDiaChi().getMaDC());
			stmt.setDate(7, new Date(nhanVien.getNgayVaoLam().getTime()));
			stmt.setString(8, nhanVien.getGioiTinh());
			stmt.setString(9, nhanVien.getVaiTro());
			stmt.setString(10, nhanVien.getAnhDaiDien());
			stmt.setString(11, nhanVien.getTrinhDoChuyenMon());
			stmt.setString(12, nhanVien.getTrinhDoNgoaiNgu());
			stmt.executeUpdate();
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public NhanVien timNhanVienBangMaNhanVien(String maNhanVien) {
		String sql = "select * from nhanvien where manhanvien = ?";
		NhanVien nhanVien = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				nhanVien = new NhanVien(rs.getString(1), rs.getString(2),rs.getDate(3),rs.getString(4) , rs.getString(5),diaChiDAO.layMaDiaChi(rs.getString(6)),
						rs.getDate(7),rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11),rs.getString(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nhanVien;
	}

	public boolean xoaNhanVien(String maNhanVien) {
		int dem = 0;

		String sql = "delete from NhanVien where maNhanVien = ?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);

			dem = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem > 0;
	}

	public boolean capNhatNhanVien(NhanVien nhanVien) {
		if (timNhanVienBangMaNhanVien(nhanVien.getMaNhanVien()) == null)
			return false;
		
		int dem = 0;
		
		
		String sql = "UPDATE [dbo].[NHANVIEN]\r\n"
				+ " SET  [tenNhanVien] = ?"
				+ "      ,[ngaySinh] = ?"
				+ "      ,[email] = ?"
				+ "      ,[sdt] = ?"
				+ "      ,[maDC] = ?"
				+ "      ,[ngayVaoLam] = ?"
				+ "      ,[gioiTinh] =?"
				+ "      ,[vaiTro] = ?"
				+ "      ,[anhDaiDien] = ?"
				+ "      ,[trinhDoChuyenMon] = ?"
				+ "      ,[trinhDoNgoaiNgu] = ?"
				+ " WHERE maNhanVien = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nhanVien.getTenNhanVien());
			stmt.setDate(2, new Date(nhanVien.getNgaySinh().getTime()));
			stmt.setString(3, nhanVien.getEmail());
			stmt.setString(4, nhanVien.getSoDienThoai());
			stmt.setString(5, nhanVien.getDiaChi().getMaDC());
			stmt.setDate(6, new Date(nhanVien.getNgayVaoLam().getTime()));
			stmt.setString(7, nhanVien.getGioiTinh());
			stmt.setString(8, nhanVien.getVaiTro());
			stmt.setString(9, nhanVien.getAnhDaiDien());
			stmt.setString(10, nhanVien.getTrinhDoChuyenMon());
			stmt.setString(11, nhanVien.getTrinhDoNgoaiNgu());
			stmt.setString(12, nhanVien.getMaNhanVien());
			stmt.executeUpdate();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem > 0;
	}
	public List<NhanVien> timKiemNhanVienTheoNhieuThuocTinh(String maNV, String tenNV, String gioiTinh,
		   java.util.Date ngaySinh, java.util.Date ngayVaoLam, String soDienThoai, String vaiTro, DiaChi diaChi, String trinhDoChuyenMon, String trinhDoNgoaiNgu) throws Exception {

		    String sql = "SELECT * FROM NhanVien nv JOIN DIACHI dc ON nv.maDC = dc.maDC ";
		    List<String> thuocTinh = new ArrayList<>();
		    List<Object> giaTri = new ArrayList<>();

		    if (maNV != null) {
		        thuocTinh.add("nv.maNhanVien = ?");
		        giaTri.add(maNV);
		    }

		    if (tenNV != null) {
		        thuocTinh.add("nv.tenNhanVien LIKE ?");
		        giaTri.add("%" + tenNV.trim() + "%");
		    }

		    if (gioiTinh != null) {
		        thuocTinh.add("nv.gioiTinh = ?");
		        giaTri.add(gioiTinh);
		    }

		    if (ngaySinh != null) {
		        thuocTinh.add("nv.ngaySinh = ?");
		        giaTri.add(ngaySinh);
		    }

		    if (ngayVaoLam != null) {
		        thuocTinh.add("nv.ngayVaoLam = ?");
		        giaTri.add(ngayVaoLam);
		    }

		    if (soDienThoai != null) {
		        thuocTinh.add("nv.sdt LIKE ?");
		        giaTri.add("%" + soDienThoai + "%");
		    }

		    if (vaiTro != null) {
		        thuocTinh.add("nv.vaiTro LIKE ?");
		        giaTri.add("%" + vaiTro + "%");
		    }
		    if (diaChi != null) {
		    	if(!diaChi.getTinhTP().equalsIgnoreCase("Tỉnh/Thành Phố")) {
		    		thuocTinh.add("dc.TinhTP  LIKE ?");
		    		giaTri.add("%" + diaChi.getTinhTP() + "%");
		    		if(!diaChi.getQuanHuyen().equalsIgnoreCase("Quận, Huyện")) {
		    			thuocTinh.add("dc.QuanHuyen LIKE ?");
		    			giaTri.add("%" + diaChi.getQuanHuyen() + "%");
		    		}	
		    		if(!diaChi.getPhuongXa().equalsIgnoreCase("Phường, Xã")) {
		    			thuocTinh.add("dc.PhuongXa LIKE ?");
		    			giaTri.add("%" + diaChi.getPhuongXa() + "%");
		    		}
		    	}
		    }
		    if(trinhDoChuyenMon != null) {
		    	thuocTinh.add("nv.trinhDoChuyenMon LIKE ?");
		    	giaTri.add("%" + trinhDoChuyenMon + "%");
		    }
		    if(trinhDoNgoaiNgu != null) {
		    	thuocTinh.add("nv.trinhDoNgoaiNgu LIKE ?");
		    	giaTri.add("%"+ trinhDoNgoaiNgu + "%");
		    }

		    if (!thuocTinh.isEmpty()) {
		        sql += "WHERE " + String.join(" AND ", thuocTinh);
		    }
		    System.out.println(sql);
		    try (PreparedStatement stmt = con.prepareStatement(sql)) {
		        for (int i = 0; i < giaTri.size(); i++) {
		            stmt.setObject(i + 1, giaTri.get(i));
		        }

		        ResultSet rs = stmt.executeQuery();
		        List<NhanVien> dsNV = new ArrayList<>();

		        while (rs.next()) {
		            NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2),rs.getDate(3),rs.getString(4) , rs.getString(5),diaChiDAO.layMaDiaChi(rs.getString(6)),
							rs.getDate(7),rs.getString(8), rs.getString(9),
							rs.getString(10),rs.getString(11),rs.getString(12));

		            dsNV.add(nv);
		        }

		        return dsNV;
		    }
		    catch (SQLException e) {
		        throw new Exception(e);
		    }
	}

	public String getMaNhanVienCaoNhat() throws Exception {
		String sql = "SELECT MAX(maNhanVien) FROM NhanVien";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		}
		return null;
	}
}
