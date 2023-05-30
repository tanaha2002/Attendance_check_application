package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connectdb.ConnectDB;
import entity.DiaChi;
import entity.ToNhom;

public class ToNhomDAO {
	private Connection connection;

	public ToNhomDAO() {
		ConnectDB.getInstance();
		connection = ConnectDB.getInstance().getConnection();
	}

	public List<ToNhom> layDanhSachToNhom() {
		List<ToNhom> dsToNhom = new ArrayList<ToNhom>();
		String sql = "select * from TONHOM";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				ToNhom toNhom = new ToNhom(result.getString(1), result.getString(2), result.getInt(3));

				dsToNhom.add(toNhom);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return dsToNhom;
	}

	public ToNhom layToNhomTheoMa(String maTo) {
		String sql = "select * from tonhom where maTo = ?";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, maTo);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				return new ToNhom(rs.getString(1), rs.getString(2), rs.getInt(3));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ToNhom timToNhomBangTenTo(String tenTo) {
		String sql = "select * from tonhom where tenTo = ?";
		try(PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, tenTo);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				return new ToNhom(rs.getString(1), rs.getString(2), rs.getInt(3));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	

	
}
