package setting;

public interface PathSetting {
	final String PATH_TO_PACKAGE = System.getProperty("user.dir");
//	String PATH_IMAGE_SYSTEM = PATH_TO_PACKAGE + "\\Image\\System";
	final String PATH_ICON = PATH_TO_PACKAGE + "\\icon\\";
	final String PATH_IMAGE_SANPHAM = PATH_ICON + "SanPham\\";
	final String PATH_IMAGE_NHANVIEN = PATH_ICON + "NhanVien\\";
	final String PATH_IMAGE_CONGNHAN = PATH_ICON + "CongNhan\\";
	final String PATH_IMAGE_HUONGDAN = PATH_ICON + "HuongDan\\";
}
