package tablemodels;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import entity.BangChamCongCongNhan;

public class ChiTietLuongCongNhanTableModel extends AbstractTableModel  {
	
		private static final long serialVersionUID = 1L;
		private static final int STT = 0;
		private static final int NGAYLAM = 1;
		private static final int MASANPHAM = 2;
		private static final int TENSANPHAM = 3;
		private static final int MACONGDOAN = 4;
		private static final int TENCONGDOAN = 5;
		private static final int CALAM = 6;
		private static final int SOLUONG = 7;
		private static final int THANHTIEN = 8;
		
	
		private String[] headers;
		private List<BangChamCongCongNhan> danhSachBangChamCongCongNhan;
		private BangChamCongCongNhan bangChamCongCongNhan;
		
		public ChiTietLuongCongNhanTableModel(String[] headers, List<BangChamCongCongNhan> danhSachBangChamCongCongNhan) {
			super();
			this.headers = headers;
			this.danhSachBangChamCongCongNhan = danhSachBangChamCongCongNhan;
				}
		
		public int getRowCount() {
			// TODO Auto-generated method stub
			return danhSachBangChamCongCongNhan.size();
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
			BangChamCongCongNhan bangChamCong = danhSachBangChamCongCongNhan.get(rowIndex);
			NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

			switch (columnIndex) {
			case STT:
				return rowIndex + 1;
			case NGAYLAM:
				return bangChamCong.getNgayChamCong();
			case CALAM:
				
				return bangChamCong.getCaLam();
			case MACONGDOAN:
				return bangChamCong.getPhanCong().getCongDoan().getMaCongDoan();
			case MASANPHAM:
				return bangChamCong.getPhanCong().getCongDoan().getSanPham().getMaSanPham();
			case SOLUONG:
				return bangChamCong.getSoLuongLam();
			case TENCONGDOAN:
				return bangChamCong.getPhanCong().getCongDoan().getTenCongDoan();
			case TENSANPHAM:
				return bangChamCong.getPhanCong().getSanPham().getTenSanPham();
			case THANHTIEN:
				
				double tienLuong = bangChamCong.getPhanCong().getCongDoan().getTienCongDoan() * bangChamCong.getSoLuongLam();
				
				if(bangChamCong.getCaLam().equals("Tối") || bangChamCong.getCaLam().equals("Sáng CN") || bangChamCong.getCaLam().equals("Chiều CN")) {
					
					String thanhTien = formatter.format(tienLuong * 2);
					return thanhTien;
				}
				
				else{
					return formatter.format(tienLuong);
				}
				
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


