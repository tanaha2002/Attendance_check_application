package tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import entity.BangChamCongCongNhan;
import entity.BangPhanCongCongNhan;


public class LayDanhSachChamCongTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final int STT = 0;
    private static final int MAPHANCONG = 1;
    private static final int MACONGNHAN = 2;
    private static final int TENCONGNHAN = 3;
    private static final int COMAT = 4; // Cột mới cho nút radio "Đi làm"
    private static final int VANGMAT = 5;
    private static final int CALAM = 6;
    private static final int SOLUONG = 7;
    private static final int TIENCONGDOAN = 8;
    private static final int TIENCONG = 9;
    
    
    

    private String[] headers;
    private List<BangPhanCongCongNhan> dsPCCN;
 

    public LayDanhSachChamCongTableModel(String[] headers, List<BangPhanCongCongNhan> dsPCCN) {
        super();
        this.headers = headers;
        this.dsPCCN = dsPCCN;
 
        
        
    }

    @Override
    public int getRowCount() {
        return dsPCCN.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	BangPhanCongCongNhan pccn = dsPCCN.get(rowIndex);

        switch (columnIndex) {
	        case STT:
				return dsPCCN.indexOf(pccn)+1;
            case MAPHANCONG:
                return pccn.getMaPhanCong();
          
            case MACONGNHAN:
                return pccn.getCongNhan().getMaCongNhan();
            case TENCONGNHAN:
                return pccn.getCongNhan().getTenCongNhan();
            case COMAT:
            	if(pccn.getTrangThai() == null)
            	{
            		return false;
            	}
                return pccn.getTrangThai();
            case VANGMAT:
            	if(pccn.getTrangThai() == null) {
            		return false;
            	}
            	return !pccn.getTrangThai();
            case CALAM:
            	return pccn.getCaLam();
            case SOLUONG:
            	return pccn.getSoLuongLamDuoc();
            case TIENCONG:
            	return pccn.getTienCong();
            case TIENCONGDOAN:
            	return pccn.getCongDoan().getTienCongDoan();
            	
            default:
                return pccn;
        }
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == COMAT) {
            return Boolean.class; // Đặt kiểu dữ liệu của cột "Đi làm" là Boolean
        }
        if (columnIndex == VANGMAT) {
        	return Boolean.class;
        }
        if(columnIndex == SOLUONG) {
        	return Integer.class;
        }
        if(columnIndex == TIENCONG) {
        	return Double.class;
        }
        if(columnIndex == TIENCONGDOAN) {
        	return Double.class;
        }
        return super.getColumnClass(columnIndex);
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return columnIndex == COMAT || columnIndex == VANGMAT; // Chỉ cho phép chỉnh sửa cột "Đi làm"
//        
//    }
    
//    @Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        if (columnIndex == COMAT || columnIndex == VANGMAT) {
//            fireTableCellUpdated(rowIndex, columnIndex);
//        }
//    }

}


