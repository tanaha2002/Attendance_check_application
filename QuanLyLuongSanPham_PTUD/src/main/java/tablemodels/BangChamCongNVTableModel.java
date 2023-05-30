package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.BangChamCongNhanVien;

public class BangChamCongNVTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MACC = 0;
	private static final int MANV = 1;
	private static final int TENNV = 2;
	private static final int SOGIOLAMTHEM = 3;
	private static final int NGAYCHAMCONG = 4;
	private static final int TRANGTHAI = 5;
	private static final int MANGUOICHAMCONG = 6;
	private static final int TENNGUOICHAMCONG = 7;
	private static final int TINHTRANGCONG = 8;
	
	
	private List<BangChamCongNhanVien> dsCCNV;
	private String[] headers;
	
	
	public BangChamCongNVTableModel(List<BangChamCongNhanVien> dsCCNV, String[] headers) {
		super();
		this.dsCCNV = dsCCNV;
		this.headers = headers;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsCCNV.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BangChamCongNhanVien chamCongNhanVien = dsCCNV.get(rowIndex);
		switch (columnIndex) {
		case MACC: {
			return chamCongNhanVien.getMaChamCong();
		}
		case MANV:
			return chamCongNhanVien.getNhanVien().getMaNhanVien();
		case TENNV:
			return chamCongNhanVien.getNhanVien().getTenNhanVien();
		case SOGIOLAMTHEM:
			return String.format("%.2f", chamCongNhanVien.getSoGioLamThem());
		case NGAYCHAMCONG:
			return chamCongNhanVien.getNgayChamCong();
		case TRANGTHAI:
			return chamCongNhanVien.getTrangThai();
		case MANGUOICHAMCONG:
			return chamCongNhanVien.getNguoiChamCong().getMaNhanVien();
		case TENNGUOICHAMCONG:
			return chamCongNhanVien.getNguoiChamCong().getTenNhanVien();
		case TINHTRANGCONG:
			return chamCongNhanVien.getTinhTrangChamCong();

		default:
			return chamCongNhanVien;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}

}
