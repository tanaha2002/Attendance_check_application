package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.BangLuongCongNhan;
import entity.BangPhanCongCongNhan;

public class BangPhanCongCongNhanTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private static final int MAPHANCONG = 0;
	private static final int MACONGNHAN = 1;
	private static final int TENCONGNHAN = 2;
	private static final int NGAYPHANCONG = 3;
	private static final int SOLUONGPHANCONG = 4;
	private static final int MACONGDOAN = 5;
	private static final int MASANPHAM = 6;
	private static final int MANGUOIPHANCONG = 7;
	private String[] headers;
	private List<BangPhanCongCongNhan> dsPCCN;
	
	public BangPhanCongCongNhanTableModel(String[] headers, List<BangPhanCongCongNhan> dsPCCN) {
		super();
		this.headers = headers;
		this.dsPCCN = dsPCCN;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsPCCN.size();
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BangPhanCongCongNhan pccn = dsPCCN.get(rowIndex);
		switch (columnIndex) {
		case MAPHANCONG:
			return pccn.getMaPhanCong();
		case NGAYPHANCONG:
			return pccn.getNgayPhanCong();
		case SOLUONGPHANCONG:
			return pccn.getSoLuongPhanCong();
		case MACONGDOAN:
			return pccn.getCongDoan().getMaCongDoan();
		case MASANPHAM:
			return pccn.getCongDoan().getSanPham().getMaSanPham();
		case MACONGNHAN:
			return pccn.getCongNhan().getMaCongNhan();
		case TENCONGNHAN:
			return pccn.getCongNhan().getTenCongNhan();
		case MANGUOIPHANCONG:
			return pccn.getNguoiPhanCong().getMaNhanVien();
		default:
			return pccn;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	
}
