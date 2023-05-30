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

public class CongNhanDAO {
	private Connection con;
	private DiaChiDAO diaChiDAO;
	private ToNhomDAO toNhomDAO;

	public CongNhanDAO() {
		con = ConnectDB.getInstance().getConnection();
		diaChiDAO = new DiaChiDAO();
		toNhomDAO = new ToNhomDAO();

	}

	public List<CongNhan> layDanhSachCongNhan() {
		List<CongNhan> dsCN = new ArrayList<>();
		String sql = "select * from CongNhan";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
			
				CongNhan congNhan1 = new CongNhan(rs.getString(1), rs.getString(2), rs.getString(3),
						diaChiDAO.layMaDiaChi(rs.getString(4)), rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getString(8), toNhomDAO.layToNhomTheoMa(rs.getString(9)));
				dsCN.add(congNhan1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsCN;
	}
	
	   public List<CongNhan> layDanhSachCongNhanChuaDuocPhanCong() {
        List<CongNhan> dsCN = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM CONGNHAN\n"
                + "WHERE maCongNhan NOT IN (\n"
                + "SELECT PHANCONGCONGNHAN.maCongNhan\n"
                + "FROM PHANCONGCONGNHAN\n"
                + "LEFT JOIN (\n"
                + "SELECT maPhanCong, SUM(soLuongLam) AS sumSoLuongLam\n"
                + "FROM BANGCHAMCONGCONGNHAN\n"
                + "GROUP BY maPhanCong\n"
                + ") AS sumChamCong ON PHANCONGCONGNHAN.maPhanCong = sumChamCong.maPhanCong\n"
                + "WHERE sumChamCong.maPhanCong IS NULL OR sumChamCong.sumSoLuongLam < PHANCONGCONGNHAN.soLuongPhanCong\n"
                + ");";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                CongNhan congNhan1 = new CongNhan(rs.getString(1), rs.getString(2), rs.getString(3),
                        diaChiDAO.layMaDiaChi(rs.getString(4)), rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getString(8), toNhomDAO.layToNhomTheoMa(rs.getString(9)));
                dsCN.add(congNhan1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsCN;
    }

    public String getMaCongNhanCaoNhat() throws Exception {
        String sql = "SELECT MAX(maCongNhan) FROM CongNhan";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }

	public boolean themCongNhan(CongNhan congNhan) {
		if (timCongNhanBangMaCongNhan(congNhan.getMaCongNhan()) != null)
			return false;
		String sql = "INSERT INTO [dbo].[CONGNHAN]" 
				+ "           ([maCongNhan]"
				+ "           ,[tenCongNhan]" 
				+ "           ,[sdt]" 
				+ "           ,[maDC]"
				+ "           ,[ngayVaoLam]" 
				+ "           ,[ngaySinh]" 
				+ "           ,[gioiTinh]"
				+ "           ,[anhDaiDien]" 
				+ "           ,[maTo])" 
				+ "     VALUES" + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, congNhan.getMaCongNhan());
			stmt.setString(2, congNhan.getTenCongNhan());
			stmt.setString(3, congNhan.getSoDienThoai());
			stmt.setString(4, congNhan.getDiaChi().getMaDC());
			stmt.setDate(5, new Date(congNhan.getNgayVaoLam().getTime()));
			stmt.setDate(6, new Date(congNhan.getNgaySinh().getTime()));
			stmt.setString(7, congNhan.getGioiTinh());
			stmt.setString(8, congNhan.getAnhDaiDien());
			stmt.setString(9, congNhan.getToNhom().getMaToNhom());
			stmt.executeUpdate();
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public CongNhan timCongNhanBangMaCongNhan(String maCongNhan) {
		String sql = "select * from CongNhan where maCongNhan = ?";
		CongNhan congNhan = null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCongNhan);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				congNhan = new CongNhan(rs.getString(1), rs.getString(2), rs.getString(3),
						diaChiDAO.layMaDiaChi(rs.getString(4)), rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getString(8),
						toNhomDAO.layToNhomTheoMa(rs.getString(9)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return congNhan;
	}

	public boolean xoaCongNhan(String maCongNhan) {
		int dem = 0;

		String sql = "delete from CongNhan where maCongNhan = ?";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCongNhan);

			dem = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem > 0;
	}

	public boolean capNhatCongNhan(CongNhan congNhan) {
		if (timCongNhanBangMaCongNhan(congNhan.getMaCongNhan()) == null)
			return false;
		int dem = 0;

		String sql = "UPDATE [dbo].[CONGNHAN]\r\n" 
				+ "   SET [tenCongNhan] = ?"
				+ "      ,[sdt] = ?" 
				+ "      ,[maDC] = ?" 
				+ "      ,[ngayVaoLam] = ?" 
				+ "      ,[ngaySinh] = ?"
				+ "      ,[gioiTinh] = ?" 
				+ "      ,[anhDaiDien] = ?" 
				+ "      ,[maTo] = ?" 
				+ " WHERE maCongNhan = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, congNhan.getTenCongNhan());
			stmt.setString(2, congNhan.getSoDienThoai());
			stmt.setString(3, congNhan.getDiaChi().getMaDC());
			stmt.setDate(4, new Date(congNhan.getNgayVaoLam().getTime()));
			stmt.setDate(5, new Date(congNhan.getNgaySinh().getTime()));
			stmt.setString(6, congNhan.getGioiTinh());
			stmt.setString(7, congNhan.getAnhDaiDien());
			stmt.setString(8, congNhan.getToNhom().getMaToNhom());
			stmt.setString(9, congNhan.getMaCongNhan());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dem > 0;
	}
	
	public List<CongNhan> timKiemCongNhanTheoNhieuThuocTinh(String maCN, String tenCN, String gioiTinh,
			   java.util.Date ngaySinh, java.util.Date ngayVaoLam, String soDienThoai, String tenTo, DiaChi diaChi) throws Exception {

			    String sql = "SELECT cn.* FROM Congnhan cn JOIN DIACHI dc ON cn.maDC = dc.maDC join ToNhom tn on tn.mato = cn.mato ";
			    List<String> thuocTinh = new ArrayList<>();
			    List<Object> giaTri = new ArrayList<>();

			    if (maCN != null) {
			        thuocTinh.add("cn.macongnhan = ?");
			        giaTri.add(maCN);
			    }

			    if (tenCN != null) {
			        thuocTinh.add("cn.tencongnhan LIKE ?");
			        giaTri.add("%" + tenCN.trim() + "%");
			    }

			    if (gioiTinh != null) {
			        thuocTinh.add("cn.gioiTinh = ?");
			        giaTri.add(gioiTinh);
			    }

			    if (ngaySinh != null) {
			        thuocTinh.add("cn.ngaySinh = ?");
			        giaTri.add(ngaySinh);
			    }

			    if (ngayVaoLam != null) {
			        thuocTinh.add("cn.ngayVaoLam = ?");
			        giaTri.add(ngayVaoLam);
			    }

			    if (soDienThoai != null) {
			        thuocTinh.add("cn.sdt LIKE ?");
			        giaTri.add("%" + soDienThoai + "%");
			    }

			    if ( tenTo != null) {
			        thuocTinh.add("tn.tenTo LIKE ?");
			        giaTri.add("%" +  tenTo + "%");
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

			    if (!thuocTinh.isEmpty()) {
			        sql += "WHERE " + String.join(" AND ", thuocTinh);
			    }
			    System.out.println(sql);
			    try (PreparedStatement stmt = con.prepareStatement(sql)) {
			        for (int i = 0; i < giaTri.size(); i++) {
			            stmt.setObject(i + 1, giaTri.get(i));
			        }

			        ResultSet rs = stmt.executeQuery();
			        List<CongNhan> dsCN = new ArrayList<>();

			        while (rs.next()) {
			        	CongNhan congNhan = new CongNhan(rs.getString(1), rs.getString(2), rs.getString(3),
								diaChiDAO.layMaDiaChi(rs.getString(4)), rs.getDate(5), rs.getDate(6), rs.getString(7), rs.getString(8), 
								toNhomDAO.layToNhomTheoMa(rs.getString(9)));
			            dsCN.add(congNhan);

			        }

			        return dsCN;
			    }
			    catch (SQLException e) {
			        throw new Exception(e);
			    }
		}
}
