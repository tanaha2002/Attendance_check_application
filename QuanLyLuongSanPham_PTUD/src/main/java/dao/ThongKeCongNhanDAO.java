/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import connectdb.ConnectDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ThongKeCongNhanDAO {

    private Connection connection;

    public ThongKeCongNhanDAO() {
        connection = ConnectDB.getInstance().getConnection();
    }

    public List<List<String>> topNamCongNhanTheoLuong(String tieuChi1, String tieuChi2, String thang, String nam) {
        String sql = null;
        if (tieuChi1.equalsIgnoreCase("Lương")) {
            sql = "SELECT  tenCongNhan, tongTien\n"
                    + "FROM (\n"
                    + "    SELECT TOP 5  cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"
                    + "    FROM bangluongcongnhan blcn\n"
                    + "    INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "    WHERE month(ngayLapBangLuong) = ? and year(ngayLapBangLuong) = ? \n"
                    + "    GROUP BY  cn.tenCongNhan\n"
                    + "    ORDER BY tongTien DESC \n"
                    + ") AS top5\n"
                    + "UNION ALL\n"
                    + "SELECT  N'Phần còn lại', SUM(tongTien) AS tongTien\n"
                    + "FROM (\n"
                    + "    SELECT  cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"	
                    + "    FROM bangluongcongnhan blcn\n"
                    + "    INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "    WHERE month(ngayLapBangLuong) = ? and year(ngayLapBangLuong) = ? \n"
                    + "    GROUP BY  cn.tenCongNhan\n"
                    + "    HAVING cn.tenCongNhan NOT IN (\n"
                    + "        SELECT TOP 5 cn.tenCongNhan\n"
                    + "        FROM bangluongcongnhan blcn\n"
                    + "        INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "        WHERE month(ngayLapBangLuong) = ? and year(ngayLapBangLuong) = ? \n"
                    + "        GROUP BY cn.tenCongNhan\n"
                    + "        ORDER BY SUM(blcn.luong) DESC \n"
                    + "    )\n"
                    + ") AS phanconlai\n"
                    + "ORDER BY tongTien DESC ";
            if (!tieuChi2.equalsIgnoreCase("Top 5 công nhân lương cao nhất")) {
                sql = sql.replace("DESC", "");
            }
        }

        System.out.println(sql);
        List<List<String>> dsTopLuong = new ArrayList<>();
        List<String> temp;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, thang);
            stmt.setString(2, nam);
            stmt.setString(3, thang);
            stmt.setString(4, nam);
            stmt.setString(5, thang);
            stmt.setString(6, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String tenCN = rs.getString(1);
                String luong = rs.getString(2);
                if (tenCN.equalsIgnoreCase("Phần còn lại") && luong.equalsIgnoreCase("NULL")) {
                    continue;
                }

                temp = new ArrayList<>();
                temp.add(tenCN);
                temp.add(luong);

                dsTopLuong.add(temp);

            }
            System.out.println(dsTopLuong);
//            System.out.println(dsTopLuong);
        } catch (Exception e) {
        }

        return dsTopLuong;
    }

    public List<List<String>> topNamCongNhanTheoLuongTrongNam(String tieuChi1, String tieuChi2, String nam) {
        String sql = null;
        if (tieuChi1.equalsIgnoreCase("Lương")) {
//            sql = "SELECT TOP 5 cn.tenCongNhan,lg.luong from BANGLUONGCONGNHAN lg join CONGNHAN cn on lg.maCongNhan = cn.maCongNhan where year(ngayLapBangLuong) = ?  order by lg.luong ";
            sql = "SELECT  tenCongNhan, tongTien\n"
                    + "FROM (\n"
                    + "    SELECT TOP 5  cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"
                    + "    FROM bangluongcongnhan blcn\n"
                    + "    INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "    WHERE year(ngayLapBangLuong) = ? \n"
                    + "    GROUP BY  cn.tenCongNhan\n"
                    + "    ORDER BY tongTien DESC \n"
                    + ") AS top5\n"
                    + "UNION ALL\n"
                    + "SELECT  N'Phần còn lại', SUM(tongTien) AS tongTien\n"
                    + "FROM (\n"
                    + "    SELECT  cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"
                    + "    FROM bangluongcongnhan blcn\n"
                    + "    INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "    WHERE year(ngayLapBangLuong) = ? \n"
                    + "    GROUP BY  cn.tenCongNhan\n"
                    + "    HAVING cn.tenCongNhan NOT IN (\n"
                    + "        SELECT TOP 5 cn.tenCongNhan\n"
                    + "        FROM bangluongcongnhan blcn\n"
                    + "        INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "        WHERE year(ngayLapBangLuong) = ? \n"
                    + "        GROUP BY cn.tenCongNhan\n"
                    + "        ORDER BY SUM(blcn.luong) DESC \n"
                    + "    )\n"
                    + ") AS phanconlai\n"
                    + "ORDER BY tongTien DESC ";
            if (!tieuChi2.equalsIgnoreCase("Top 5 công nhân lương cao nhất")) {
                sql = sql.replace("DESC", "");
            }
        }

        System.out.println(sql);
        List<List<String>> dsTopLuong = new ArrayList<>();
        List<String> temp;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nam);
            stmt.setString(2, nam);
            stmt.setString(3, nam);
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String tenCN = rs.getString(1);
                String luong = rs.getString(2);
                if (tenCN.equalsIgnoreCase("Phần còn lại") && luong.equalsIgnoreCase("NULL")) {
                    continue;
                }

                temp = new ArrayList<>();
                temp.add(tenCN);
                temp.add(luong);

                dsTopLuong.add(temp);

            }
            System.out.println(dsTopLuong);
//            System.out.println(dsTopLuong);
        } catch (Exception e) {
        }

        return dsTopLuong;
    }

    public List<List<String>> tongLuongTrongNam(String nam) {
        List<List<String>> dsTongLuong = new ArrayList<>();
        List<String> temp;
        String sql = "SELECT \n"
                + "    t.thang AS thang, \n"
                + "    COALESCE(SUM(b.luong), 0) AS tong_tien\n"
                + "FROM \n"
                + "    (VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)) AS t(thang)\n"
                + "    LEFT JOIN BANGLUONGCONGNHAN b ON MONTH(b.ngayLapBangLuong) = t.thang AND YEAR(b.ngayLapBangLuong) = ? \n"
                + "GROUP BY \n"
                + "    t.thang\n"
                + "ORDER BY \n"
                + "    t.thang";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nam);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                temp = new ArrayList<>();
                temp.add(rs.getString(1));
                temp.add(rs.getString(2));
                dsTongLuong.add(temp);
            }
            System.out.println(dsTongLuong);
        } catch (Exception e) {
        }

        return dsTongLuong;
    }

    public List<List<String>> tongLuongTheoKhoangThoiGian(String tuThang, String tuNam, String denThang, String denNam) {
        List<List<String>> dsTongLuong = new ArrayList<>();
        List<String> temp;
        LocalDate ngayBatDau = LocalDate.of(Integer.parseInt(tuNam), Integer.parseInt(tuThang), 1);
        LocalDate ngayKetThuc = LocalDate.of(Integer.parseInt(denNam), Integer.parseInt(denThang), 1);
		ngayKetThuc = ngayKetThuc.withDayOfMonth(ngayKetThuc.lengthOfMonth());

        
        String sql = "select ngayLapBangLuong, sum(luong) as tongTien from bangluongcongnhan where ngayLapBangLuong >= '" + ngayBatDau.toString() + "' and ngayLapBangLuong <= '" + ngayKetThuc.toString() + "'  group by ngayLapBangLuong";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                temp = new ArrayList<>();
                temp.add(rs.getString(1));
                temp.add(rs.getString(2));
                dsTongLuong.add(temp);
            }
            System.out.println(dsTongLuong);
        } catch (Exception e) {
        }

        return dsTongLuong;
    }

    public List<List<String>> TopNamCongNhanTheoKhoangThoiGian(String tuThang, String tuNam, String denThang, String denNam, String tieuChi1, String tieuChi2) {
        List<List<String>> dsTopNam = new ArrayList<>();
        List<String> temp;
        LocalDate ngayBatDau = LocalDate.of(Integer.parseInt(tuNam), Integer.parseInt(tuThang), 1);
        LocalDate ngayKetThuc = LocalDate.of(Integer.parseInt(denNam), Integer.parseInt(denThang), 30);
        if (Integer.parseInt(denThang) == 2) {
            ngayKetThuc = LocalDate.of(Integer.parseInt(denNam), Integer.parseInt(denThang), 28);
        }
        String sql = null;
        if (tieuChi1.equalsIgnoreCase("Lương")) {
//            sql = "SELECT top 5 cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"
//                    + "FROM bangluongcongnhan blcn\n"
//                    + "INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
//                    + "WHERE blcn.ngayLapBangLuong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "'\n"
//                    + "GROUP BY cn.tenCongNhan "
//                    +"ORDER BY tongTien ";
            sql = "SELECT  tenCongNhan, tongTien\n"
                    + "FROM (\n"
                    + "    SELECT TOP 5  cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"
                    + "    FROM bangluongcongnhan blcn\n"
                    + "    INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "    WHERE blcn.ngayLapBangLuong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "'\n"
                    + "    GROUP BY  cn.tenCongNhan\n"
                    + "    ORDER BY tongTien DESC \n"
                    + ") AS top5\n"
                    + "UNION ALL\n"
                    + "SELECT  N'Phần còn lại', SUM(tongTien) AS tongTien\n"
                    + "FROM (\n"
                    + "    SELECT  cn.tenCongNhan, SUM(blcn.luong) AS tongTien\n"
                    + "    FROM bangluongcongnhan blcn\n"
                    + "    INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "    WHERE blcn.ngayLapBangLuong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "'\n"
                    + "    GROUP BY  cn.tenCongNhan\n"
                    + "    HAVING cn.tenCongNhan NOT IN (\n"
                    + "        SELECT TOP 5 cn.tenCongNhan\n"
                    + "        FROM bangluongcongnhan blcn\n"
                    + "        INNER JOIN congnhan cn ON blcn.maCongNhan = cn.maCongNhan\n"
                    + "        WHERE blcn.ngayLapBangLuong BETWEEN '" + ngayBatDau.toString() + "' AND '" + ngayKetThuc.toString() + "'\n"
                    + "        GROUP BY cn.tenCongNhan\n"
                    + "        ORDER BY SUM(blcn.luong) DESC \n"
                    + "    )\n"
                    + ") AS phanconlai\n"
                    + "ORDER BY tongTien DESC ";
            if (!tieuChi2.equalsIgnoreCase("Top 5 công nhân lương cao nhất")) {
                sql = sql.replace("DESC", "");
            }
        }
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase("Phần còn lại") && rs.getString(2).equalsIgnoreCase("NULL")) {
                    continue;
                }
                temp = new ArrayList<>();
                temp.add(rs.getString(1));
                temp.add(rs.getString(2));
                dsTopNam.add(temp);

            }
        } catch (Exception e) {
        }

        return dsTopNam;
    }
    

}
