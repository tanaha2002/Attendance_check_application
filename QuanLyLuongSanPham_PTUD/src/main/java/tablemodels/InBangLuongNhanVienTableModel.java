package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import entity.BangChamCongCongNhan;
import entity.BangLuongCongNhan;
import entity.BangLuongNhanVien;
import entity.BangPhanCongCongNhan;
import entity.CongNhan;

public class InBangLuongNhanVienTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final int MABANGLUONG = 0;
	private static final int MANHANVIEN = 1;
	private static final int HOTEN = 2;
	private static final int TONGLUONG = 3;
	private static final int XACNHAN = 4;
	private String[] headers;
	private List<BangLuongNhanVien> danhSachBangLuongNhanVien;
	private BangLuongNhanVien bangLuongNhanVien;
	private List<String> tongLuong;
	
	
	public InBangLuongNhanVienTableModel(String[] headers, List<BangLuongNhanVien> danhSachBangLuongNhanVien, List<String> tongLuong) {
		super();
		this.headers = headers;
		this.danhSachBangLuongNhanVien = danhSachBangLuongNhanVien;
		this.tongLuong = tongLuong;
		
			}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return danhSachBangLuongNhanVien.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BangLuongNhanVien bangLuongNhanVien = danhSachBangLuongNhanVien.get(rowIndex);
		String tongLuongNhanVien = "";
		if(tongLuong.size() == 0)
			tongLuongNhanVien = "";
		else tongLuongNhanVien = tongLuong.get(rowIndex);
		
		switch (columnIndex) {
		case MABANGLUONG:
			return bangLuongNhanVien.getMaBangLuong();
		case MANHANVIEN:
			return bangLuongNhanVien.getNhanVien().getMaNhanVien();
		case HOTEN:
			return bangLuongNhanVien.getNhanVien().getTenNhanVien();
		case TONGLUONG:
			
				
			return tongLuongNhanVien;
			
		case XACNHAN:

			
			

			return "";
			
		

		default:
			return bangLuongNhanVien;
			
	}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
}
