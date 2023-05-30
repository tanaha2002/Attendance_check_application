package application;

import java.util.List;

import javax.swing.UIManager;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import entity.BangLuongCongNhan;

import java.awt.Color;

import ui.DangNhap;
import ui.MainUI;

public class Application {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new FlatMacLightLaf());
			// thay doi mau combobox khi disable
			UIManager.put("ComboBox.disabledBackground", Color.white);
			UIManager.put("ComboBox.disabledForeground", Color.black); 
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		//		MainUI appUI = new MainUI();
		//		appUI.setVisible(true);
		DangNhap frmDangNhap = new DangNhap();
		frmDangNhap.setLocationRelativeTo(null);
		frmDangNhap.setVisible(true);
	}
}
