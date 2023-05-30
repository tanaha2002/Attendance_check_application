package tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.BangChamCongCongNhanDAO;
import dao.CongDoanSanPhamDAO;
import entity.BangChamCongCongNhan;
import entity.CongDoanSanPham;

public class CongDoanTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int STT = 0;
	private static final int MACD = 1;
	private static final int TENCD = 2;
	private static final int TIENCD = 3;
	private static final int SOLUONG = 4;
	private static final int TINHTRANG = 5;
	private static final int THUTU = 6;
	private static final int MASANPHAM = 7;
	private List<CongDoanSanPham> dsCD = new ArrayList<>();
	private String[] headers;
	
	private BangChamCongCongNhanDAO chamCongDAO;
	public CongDoanTableModel(List<CongDoanSanPham> dsCD, String[] headers) {
		super();
		chamCongDAO = new BangChamCongCongNhanDAO();
		this.dsCD = dsCD;
		this.headers = headers;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsCD.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CongDoanSanPham congDoan = dsCD.get(rowIndex);
		switch (columnIndex) {
		case STT:
			return dsCD.indexOf(congDoan)+1;
		case MACD: {
			return congDoan.getMaCongDoan();
		}
		case TENCD:
			return congDoan.getTenCongDoan();
		case TIENCD:
			return congDoan.getTienCongDoan();
		case SOLUONG:
			return congDoan.getSoLuong();
		case TINHTRANG:
			double tiLeHoanThanh = (double) chamCongDAO.getSLHoanThanhCuaCongDoan(congDoan.getMaCongDoan(), congDoan.getSanPham().getMaSanPham())/congDoan.getSoLuong() * 100;
			return String.format("%.2f%%", tiLeHoanThanh);
		case THUTU:
			return congDoan.getThuTu();
		case MASANPHAM:
			return congDoan.getSanPham().getMaSanPham();
		default:
			return congDoan;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
}
