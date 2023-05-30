package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.BangChamCongCongNhan;

public class BangChamCongCNTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MACC = 0;
	private static final int MACN = 1;
	private static final int TENCN = 2;
	private static final int TO = 3;
	private static final int NGAYCHAMCONG = 4;
	private static final int CALAM = 5;
	private static final int TRANGTHAI = 6;
	private static final int MACD = 7;
	private static final int TENCD = 8;
	private static final int SOLUONGLAMDUOC = 9;
	private static final int MASP = 10;
	private static final int TENSP = 11;
	private static final int MANGUOICHAMCONG = 12;
	private static final int TENNGUOICHAMCONG = 13;
	
	private List<BangChamCongCongNhan> dsBangChamCongCN;
	private String[] headers;
	
	
	
	
	public BangChamCongCNTableModel(List<BangChamCongCongNhan> dsBangChamCongCN, String[] headers) {
		super();
		this.dsBangChamCongCN = dsBangChamCongCN;
		this.headers = headers;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dsBangChamCongCN.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BangChamCongCongNhan bangChamCong = dsBangChamCongCN.get(rowIndex);
		switch (columnIndex) {
		case MACC : {
			
			return bangChamCong.getMaChamCong();
		}
		case MACN:
			return bangChamCong.getPhanCong().getCongNhan().getMaCongNhan();
		case TENCN:
			return bangChamCong.getPhanCong().getCongNhan().getTenCongNhan();
		case TO:
			return bangChamCong.getPhanCong().getCongNhan().getToNhom().getTenToNhom();
		case NGAYCHAMCONG:
			return bangChamCong.getNgayChamCong();
		case CALAM:
			return bangChamCong.getCaLam();
		case TRANGTHAI:
			return bangChamCong.getTrangthai();
		case MACD:
			return bangChamCong.getPhanCong().getCongDoan().getMaCongDoan();
		case MASP:
			return bangChamCong.getPhanCong().getCongDoan().getSanPham().getMaSanPham();
		case SOLUONGLAMDUOC:
			return bangChamCong.getSoLuongLam();
		case MANGUOICHAMCONG:
			return bangChamCong.getNguoiChamCong().getMaNhanVien();
		case TENCD:
			return bangChamCong.getPhanCong().getCongDoan().getTenCongDoan();
		case TENSP:
			return bangChamCong.getPhanCong().getSanPham().getTenSanPham();
		case TENNGUOICHAMCONG:
			return bangChamCong.getNguoiChamCong().getTenNhanVien();
		default:
			return bangChamCong;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}

}
