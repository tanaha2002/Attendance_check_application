package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.NhanVien;

public class NhanVienTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private static final int MANV = 0;
	private static final int TENNV = 1;
	private static final int NGAYSINH = 2;
	private static final int EMAIL = 3;
	private static final int SDT = 4;
	private static final int DIACHI = 5;
	private static final int NGAYVAOLAM = 6;
	private static final int GIOITINH = 7;
	private static final int VAITRO = 8;
	private static final int TRINHDOCHUYENMON = 9;
	private static final int TRINHDONGOAINGU = 10;
	
	private String[] headers;
	private List<NhanVien> dsNV;

	
	
	public NhanVienTableModel(String[] headers, List<NhanVien> dsNV) {
		super();
		this.headers = headers;
		this.dsNV = dsNV;
	}

	@Override
	public int getRowCount() {
		return dsNV.size();
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
		NhanVien nhanVien = dsNV.get(rowIndex);
		switch(columnIndex) {
		case MANV: {
			return nhanVien.getMaNhanVien();
		}
		case TENNV:
			return nhanVien.getTenNhanVien();
		case NGAYSINH:
			return nhanVien.getNgaySinh();
		case EMAIL:
			return nhanVien.getEmail();
		case SDT:
			return nhanVien.getSoDienThoai();
		case DIACHI:
			return nhanVien.getDiaChi();
		case NGAYVAOLAM:
			return nhanVien.getNgayVaoLam();
		case GIOITINH:
			return nhanVien.getGioiTinh();
		case VAITRO:
			return nhanVien.getVaiTro();
		case TRINHDOCHUYENMON:
			return nhanVien.getTrinhDoChuyenMon();
		case TRINHDONGOAINGU:
			return nhanVien.getTrinhDoNgoaiNgu();
		default:
			return nhanVien;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}

}
