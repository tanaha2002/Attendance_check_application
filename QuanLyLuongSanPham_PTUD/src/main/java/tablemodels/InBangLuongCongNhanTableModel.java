package tablemodels;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import dao.BangChamCongCongNhanDAO;
import dao.BangPhanCongCongNhanDAO;
import entity.BangChamCongCongNhan;
import entity.BangLuongCongNhan;
import entity.BangPhanCongCongNhan;
import entity.CongNhan;

public class InBangLuongCongNhanTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final int MABANGLUONG = 0;
	private static final int MACONGNHAN = 1;
	private static final int HOTEN = 2;
	private static final int TONGLUONG = 3;
	private static final int XACNHAN = 4;
	private String[] headers;
	private List<BangLuongCongNhan> danhSachBangLuongCongNhan;
	private BangLuongCongNhan bangLuongCongNhan;
	
	public InBangLuongCongNhanTableModel(String[] headers, List<BangLuongCongNhan> danhSachBangLuongCongNhan) {
		super();
		this.headers = headers;
		this.danhSachBangLuongCongNhan = danhSachBangLuongCongNhan;
			}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return danhSachBangLuongCongNhan.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BangLuongCongNhan bangLuongCongNhan = danhSachBangLuongCongNhan.get(rowIndex);
		switch (columnIndex) {
		case MABANGLUONG:
			return bangLuongCongNhan.getMaBangLuong();
		case MACONGNHAN:
			return bangLuongCongNhan.getCongNhan().getMaCongNhan();
		case HOTEN:
			return bangLuongCongNhan.getCongNhan().getTenCongNhan();
		case TONGLUONG:

        	NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        	String luong = formatter.format(bangLuongCongNhan.getLuong() + bangLuongCongNhan.getTienThuongChuyenCan());
			return luong;
			
		case XACNHAN:

			return "";
			
		

		default:
			return bangLuongCongNhan;
			
	}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
}
