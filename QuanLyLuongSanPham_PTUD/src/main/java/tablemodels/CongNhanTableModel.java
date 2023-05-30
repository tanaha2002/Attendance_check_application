package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.CongNhanDAO;
import entity.CongNhan;

public class CongNhanTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final int MACN = 0;
	private static final int TENCN = 1;
	private static final int SDT = 2;
	private static final int DC = 3;
	private static final int NGAYVAOLAM = 4;
	private static final int NGAYSINH = 5;
	private static final int GIOITINH = 6;
	private static final int TENTO = 7;
	
	private CongNhanDAO congNhanDAO = new CongNhanDAO();
	private String[] headers;
	private List<CongNhan> dsCN;

	
	
	public CongNhanTableModel(String[] headers, List<CongNhan> dsCN) {
		super();
		this.headers = headers;
		this.dsCN = dsCN;
	}

	@Override
	public int getRowCount() {
		return dsCN.size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	
	//private static final int MACN = 0;
//	private static final int TENCN = 1;
//	private static final int SDT = 2;
//	private static final int MADC = 3;
//	private static final int NGAYVAOLAM = 4;
//	private static final int NGAYSINH = 5;
//	private static final int GIOITINH = 6;
//	private static final int MATO = 7;
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CongNhan congNhan = dsCN.get(rowIndex);
		switch(columnIndex) {
		case MACN: {
			return congNhan.getMaCongNhan();
		}
		case TENCN:
			return congNhan.getTenCongNhan();
		case SDT:
			return congNhan.getSoDienThoai();
		case DC:
			return congNhan.getDiaChi().toString();
		case NGAYVAOLAM:
			return congNhan.getNgayVaoLam();
		case NGAYSINH:
			return congNhan.getNgaySinh();
		case GIOITINH:
			return congNhan.getGioiTinh();
		case TENTO:
			return congNhan.getToNhom().getTenToNhom();
		default:
			return congNhan;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}

}
