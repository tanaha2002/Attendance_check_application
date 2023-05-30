package tablemodels;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import dao.BangChamCongNhanVienDAO;
import dao.BangLuongNhanVienDAO;
import entity.BangChamCongNhanVien;
import entity.BangLuongNhanVien;

public class BangLuongNhanVienTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MABANGLUONG = 0;
	private static final int MANHANVIEN = 1;
	private static final int HOTEN = 2;
	private static final int VAITRO = 3;
	private static final int HESOLUONG = 4;
	private static final int LUONGCOBAN = 5;
	private static final int PHUCAP = 6;
	private static final int SOPHEPDUOCNGHI = 7;
	private static final int NGAYLAPBANGLUONG = 8;
	private static final int TONGLUONG = 9;
	private String[] headers;
	
	private BangChamCongNhanVienDAO bangChamCongNhanVienDAO;
	private BangLuongNhanVienDAO bangLuongNhanVienDAO;
	private List<BangLuongNhanVien> danhSachBangLuongNhanVien;
	List<BangChamCongNhanVien> dsChamCong ;
	private boolean tinhLuong;
	private int thangLuong;
	private int namLuong;
//	private List<BangChamCongNhanVien> dsBangChamCongNhanVien = new ArrayList<>();
	public BangLuongNhanVienTableModel(String[] headers, List<BangLuongNhanVien> dsBLNV, boolean tinhLuong,int thangTinhLuong,int namTinhLuong) {
		super();
		this.headers = headers;
		this.danhSachBangLuongNhanVien = dsBLNV;
		this.tinhLuong = tinhLuong;
		this.thangLuong = thangTinhLuong;
		this.namLuong = namTinhLuong;
		bangChamCongNhanVienDAO = new BangChamCongNhanVienDAO();
		bangLuongNhanVienDAO = new BangLuongNhanVienDAO();
		
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
		BangLuongNhanVien blnv = danhSachBangLuongNhanVien.get(rowIndex);
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//		BangChamCongNhanVien ccnv = new BangChamCongNhanVien();
//		BangChamCongNhanVienDAO bangChamCongNhanVienDAO = new BangChamCongNhanVienDAO();
//		
//		
		switch (columnIndex) {
		case MABANGLUONG:
			return blnv.getMaBangLuong();
		case MANHANVIEN:
			return blnv.getNhanVien().getMaNhanVien();
		case HOTEN:
			return blnv.getNhanVien().getTenNhanVien();
		case LUONGCOBAN:

        	
        	String luongCoBan = formatter.format(blnv.getLuongCoBan());
			return luongCoBan;
		case PHUCAP:
			
        	String phuCap = formatter.format(blnv.getPhuCap());
			return phuCap;
		case TONGLUONG:


//			
			if(tinhLuong == true) {
				String luong = null;
				BangChamCongNhanVien ccnv = new BangChamCongNhanVien();
				
				ccnv = bangChamCongNhanVienDAO.timBangChamCongBangMaNhanVien(blnv.getNhanVien().getMaNhanVien(),thangLuong,namLuong);
				try {
					luong = bangLuongNhanVienDAO.tinhLuongNhanVienTheoThang(ccnv, thangLuong, namLuong);
					double luongs = Double.parseDouble(luong);
	            	
	            	luong = formatter.format(luongs);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 return luong;
				
			}
		case VAITRO:
			return blnv.getNhanVien().getVaiTro();
		case HESOLUONG:
			return blnv.getHeSoLuong();
		case SOPHEPDUOCNGHI:
			return blnv.getSoPhepDuocNghi();
		case NGAYLAPBANGLUONG:
			return blnv.getNgayTinhLuong();
		default:
			return blnv;
		}
	}
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return headers[column];
	}
	
	
	
}
