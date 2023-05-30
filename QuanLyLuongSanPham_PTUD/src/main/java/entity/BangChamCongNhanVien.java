package entity;

import java.util.Date;
import java.util.Objects;

public class BangChamCongNhanVien {
	private String maChamCong;
	private double soGioLamThem;
	private Date ngayChamCong;
	private String trangThai;
	private NhanVien nguoiChamCong;
	private NhanVien nhanVien;
	private String tinhTrangChamCong;
	public String getMaChamCong() {
		return maChamCong;
	}
	public void setMaChamCong(String maChamCong) {
		this.maChamCong = maChamCong;
	}
	public double getSoGioLamThem() {
		return soGioLamThem;
	}
	public void setSoGioLamThem(double soGioLamThem) {
		this.soGioLamThem = soGioLamThem;
	}
	public Date getNgayChamCong() {
		return ngayChamCong;
	}
	public void setNgayChamCong(Date ngayChamCong) {
		this.ngayChamCong = ngayChamCong;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public NhanVien getNguoiChamCong() {
		return nguoiChamCong;
	}
	public void setNguoiChamCong(NhanVien nguoiChamCong) {
		this.nguoiChamCong = nguoiChamCong;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public String getTinhTrangChamCong() {
		return tinhTrangChamCong;
	}
	public void setTinhTrangChamCong(String tinhTrangChamCong) {
		this.tinhTrangChamCong = tinhTrangChamCong;
	}
	public BangChamCongNhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BangChamCongNhanVien(String maChamCong, double soGioLamThem, Date ngayChamCong, String trangThai,
			NhanVien nguoiChamCong, NhanVien nhanVien, String tinhTrangChamCong) {
		super();
		this.maChamCong = maChamCong;
		this.soGioLamThem = soGioLamThem;
		this.ngayChamCong = ngayChamCong;
		this.trangThai = trangThai;
		this.nguoiChamCong = nguoiChamCong;
		this.nhanVien = nhanVien;
		this.tinhTrangChamCong = tinhTrangChamCong;
	}
	@Override
	public String toString() {
		return "BangChamCongNhanVien [maChamCong=" + maChamCong + ", soGioLamThem=" + soGioLamThem + ", ngayChamCong="
				+ ngayChamCong + ", trangThai=" + trangThai + ", nguoiChamCong=" + nguoiChamCong + ", nhanVien="
				+ nhanVien + ", tinhTrangChamCong=" + tinhTrangChamCong + "]\n";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maChamCong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BangChamCongNhanVien other = (BangChamCongNhanVien) obj;
		return Objects.equals(maChamCong, other.maChamCong);
	}
	
	
	
	
	
}
