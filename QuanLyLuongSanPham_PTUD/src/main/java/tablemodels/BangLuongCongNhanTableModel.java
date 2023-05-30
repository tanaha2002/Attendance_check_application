package tablemodels;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import dao.CongDoanSanPhamDAO;
import dao.SanPhamDAO;
import entity.BangChamCongCongNhan;
import entity.BangLuongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongDoanSanPham;
import entity.CongNhan;
import entity.SanPham;

public class BangLuongCongNhanTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MACONGNHAN = 0;
	private static final int TENCONGNHAN = 1;
	private static final int TENSANPHAM = 2;
	private static final int TENCONGDOAN = 3;
	private static final int SOLUONGLAM = 4;
	private static final int THOIGIAN = 5;
	private static final int TONGTIENCONGDOAN = 6;
	private static final int TIENTHUONGCHUYENCAN = 7;
	

	private String[] headers;
	private List<List<String>> danhSachBangLuongTableModel;

	public BangLuongCongNhanTableModel(String[] headers, List<List<String>> danhSachBangLuongTableModel) {
		this.danhSachBangLuongTableModel = danhSachBangLuongTableModel;
		this.headers = headers;

	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return danhSachBangLuongTableModel.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	// private static final int MABANGLUONG = 0;
//	private static final int MACONGNHAN = 1;
//	private static final int HOTEN = 2;
//	private static final int TENCONGDOAN = 3;
//	private static final int SOLUONGSP = 4;
//	private static final int TIENTHUONG = 5;
//	private static final int PHUCAP = 6;
//	private static final int LUONG = 7;
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<String> thongTinHienThi = danhSachBangLuongTableModel.get(rowIndex);
		switch (columnIndex) {

		case MACONGNHAN:
			return thongTinHienThi.get(0);
		case TENCONGNHAN:
			return thongTinHienThi.get(1);
		case TENSANPHAM:
			return thongTinHienThi.get(2);
		case TENCONGDOAN:
			return thongTinHienThi.get(3);
		case SOLUONGLAM:
			return thongTinHienThi.get(4);
		case TONGTIENCONGDOAN:
			double tongTien = Double.parseDouble(thongTinHienThi.get(5));
			NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			String tongTienCongDoan = formatter.format(tongTien);
			return tongTienCongDoan;
		case TIENTHUONGCHUYENCAN:
			double tienThuong = Double.parseDouble(thongTinHienThi.get(6));
			NumberFormat formatter2 = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			String tienThuongChuyenCan = formatter2.format(tienThuong);
			return tienThuongChuyenCan;
		case THOIGIAN:
			return thongTinHienThi.get(7);

		default:
			return thongTinHienThi;
		}
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}

}
