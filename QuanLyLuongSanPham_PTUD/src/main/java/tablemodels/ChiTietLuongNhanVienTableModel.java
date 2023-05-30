package tablemodels;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import entity.BangChamCongCongNhan;
import entity.BangChamCongNhanVien;

public class ChiTietLuongNhanVienTableModel extends AbstractTableModel  {
	
		private static final long serialVersionUID = 1L;
		private static final int STT = 0;
		private static final int NGAYLAM = 1;
		private static final int SOGIOLAMTHEM = 2;
		private static final int TRANGTHAIDILAM = 3;
		private static final int TINHTRANGCHAMCONG = 4;

		
	
		private String[] headers;
		private List<BangChamCongNhanVien> danhSachBangChamCongNhanVien;
		private BangChamCongNhanVien bangChamCongNhanVien;
		
		public ChiTietLuongNhanVienTableModel(String[] headers, List<BangChamCongNhanVien> danhSachBangChamCongNhanVien) {
			super();
			this.headers = headers;
			this.danhSachBangChamCongNhanVien = danhSachBangChamCongNhanVien;
				}
		
		public int getRowCount() {
			// TODO Auto-generated method stub
			return danhSachBangChamCongNhanVien.size();
		}

		public int getColumnCount() {
			// TODO Auto-generated method stub
			return headers.length;
		}
		
		
		//		private static final long STT = 0;
//		private static final long NGAYLAM = 0;
//		private static final long MASANPHAM = 0;
//		private static final long TENSANPHAM = 0;
//		private static final long MACONGDOAN = 0;
//		private static final long TENCONGDOAN = 0;
//		private static final long CALAM = 0;
//		private static final long SOLUONG = 0;
//		private static final long THANHTIEN = 0;

		public Object getValueAt(int rowIndex, int columnIndex) {
			BangChamCongNhanVien bangChamCong = danhSachBangChamCongNhanVien.get(rowIndex);
			int i = 0;
			switch (columnIndex) {
			case STT:
				
				return rowIndex + 1;
			case NGAYLAM:
				return bangChamCong.getNgayChamCong();
			
			case SOGIOLAMTHEM:
				return bangChamCong.getSoGioLamThem();
			case TRANGTHAIDILAM:
				
				return bangChamCong.getTrangThai();
			case TINHTRANGCHAMCONG:
				return bangChamCong.getTinhTrangChamCong();
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


