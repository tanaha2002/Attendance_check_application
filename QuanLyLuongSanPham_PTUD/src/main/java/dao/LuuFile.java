package dao;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class LuuFile {

	

	public boolean InBangLuong(String outputFilePath, String thang, String nam,String toNhom,  JTable tblLuong) {
	    Document document = new Document();
	    PdfWriter writer = null;
	    
	    final String finalToNhom = toNhom;
	    try {
	    	if(outputFilePath != null) {
	        writer = PdfWriter.getInstance(document, new FileOutputStream(outputFilePath));
	        writer.setPageEvent(new PdfPageEventHelper() {
	           
	        	public void onEndPage(PdfWriter writer, Document document) {

	                Date ngayIn = new java.sql.Date(new java.util.Date().getTime());
	                SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");
	                String formatDate = dt.format(ngayIn);

	                com.itextpdf.text.Rectangle pageSize = document.getPageSize();

	                BaseFont baseFont;
	                try {
	                    baseFont = BaseFont.createFont("font/vuTimes.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	                    com.itextpdf.text.Font font = new com.itextpdf.text.Font(baseFont, 14, Font.BOLD);

	                    // Add the header
	                    float headerX = (pageSize.getLeft() + pageSize.getRight()) / 2;
	                    float headerY = pageSize.getTop() - 20;
	                    
	                    
	                    if(!thang.equals("All") || !nam.equals("All")) {
	                    if (finalToNhom.equalsIgnoreCase("NV")) {
	                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
	                                new Phrase("BẢNG LƯƠNG NHÂN VIÊN THÁNG " + thang, font), headerX, headerY, 0);
	                    } 
	                    else if (finalToNhom.equalsIgnoreCase("All")) {
	                    	
	                    	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
	                                new Phrase("BẢNG LƯƠNG CÔNG NHÂN TẤT CẢ TỔ THÁNG " + thang, font), headerX, headerY, 0);
	                    }
	                   else {

	   	                        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
	   	                                new Phrase("BẢNG LƯƠNG CÔNG NHÂN " + finalToNhom + " THÁNG " + thang, font), headerX, headerY, 0);
	   	                    } 
	   	                 
	                    }
	                    
	                    else {
	                    	
	                    	 ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
		                                new Phrase("BẢNG LƯƠNG CÔNG NHÂN ", font), headerX, headerY, 0);
	                    	
	                    }
	                        
	                    

	                    
	                    // Add the footer
	                    float footerX = (pageSize.getLeft() + pageSize.getRight()) / 2;
	                    float footerY = pageSize.getBottom() + 20;
	                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
	                            new Phrase(toNhom + " ----- [Ngày in " + formatDate + "] --- Nhóm 4 ", font), footerX, footerY, 0);
	                } catch (DocumentException e) {
	                    e.printStackTrace();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        });

	        document.open();

	        // Create a font for the table
	        BaseFont baseFont = null;
	        try {
	            baseFont = BaseFont.createFont("font/vuTimes.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        } catch (DocumentException e1) {
	            e1.printStackTrace();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	        com.itextpdf.text.Font font2 = new com.itextpdf.text.Font(baseFont, 12, Font.PLAIN);
	        com.itextpdf.text.Font fontBold = new com.itextpdf.text.Font(baseFont, 12, Font.BOLD);

	        // Create the table
	        PdfPTable table = new PdfPTable(tblLuong.getColumnCount());

	        com.itextpdf.text.Rectangle pageSize = document.getPageSize();
	        

	        // Add table headers
	        for (int i = 0; i < tblLuong.getColumnCount(); i++) {
	            PdfPCell headerCell = new PdfPCell();
	            headerCell.setPadding(2);
	            Phrase phrase = new Phrase(tblLuong.getColumnName(i), fontBold);
	            headerCell.setPhrase(phrase);
	            table.addCell(headerCell);
	        }
	        // Add table rows
	        for (int row = 0; row < tblLuong.getRowCount(); row++) {
	            for (int col = 0; col < tblLuong.getColumnCount(); col++) {
	                PdfPCell dataCell = new PdfPCell();
	                dataCell.setPadding(2);
	                dataCell.setPhrase(new Phrase(tblLuong.getValueAt(row, col).toString(), font2));
	                table.addCell(dataCell);
	            }
	        }

	        // Add the table to the document
	        try {
	            document.add(table);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }

	        document.close();
	        writer.close();

	      
	        return true;
	    }
	    }catch (FileNotFoundException e2) {
	        e2.printStackTrace();
	    } catch (DocumentException e2) {
	        e2.printStackTrace();
	    }
	    return false;
	}


};
