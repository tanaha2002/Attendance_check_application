package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.SanPham;

public class SanPhamTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MASP = 0;
	private static final int TENSP = 1;
	private static final int SOLUONGCD = 2;
	private static final int GIATIEN = 3;
	private static final int SOLUONGSX = 4;
	private static final int CHATLIEU = 5;
	private static final int KICHTHUOC = 6;
	
	private String[] headers;
	private List<SanPham> dsSP;
	
	public SanPhamTableModel(String[] headers,List<SanPham> dsSP) {
		super();
		this.headers = headers;
		this.dsSP = dsSP;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsSP.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SanPham sp = dsSP.get(rowIndex);
		switch(columnIndex) {
		case MASP: {
			return sp.getMaSanPham();
		}
		case TENSP:
			return sp.getTenSanPham();
		case GIATIEN:
			return sp.getGiaTien();
		case SOLUONGSX:
			return sp.getSoLuongSanXuat();
		case CHATLIEU:
			return sp.getChatLieu();
		case KICHTHUOC:
			return sp.getKichThuoc();
		case SOLUONGCD:
			return sp.getSoLuongCongDoan();
		default:
			return sp;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == SOLUONGCD || columnIndex == SOLUONGSX) 
			return Integer.class;
		if(columnIndex == GIATIEN ) 
			return Double.class;
		return String.class;
	}
	
}
