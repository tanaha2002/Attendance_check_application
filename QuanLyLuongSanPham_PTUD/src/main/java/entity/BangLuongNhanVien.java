package entity;

import java.util.Date;
import java.util.Objects;

public class BangLuongNhanVien {
	private String maBangLuong;
	private NhanVien nhanVien;
	private Date ngayTinhLuong;
	private double luongCoBan;
	private double phuCap;
	private double heSoLuong;
	private int soPhepDuocNghi;
	
	public String getMaBangLuong() {
		return maBangLuong;
	}
	public void setMaBangLuong(String maBangLuong) {
		this.maBangLuong = maBangLuong;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public Date getNgayTinhLuong() {
		return ngayTinhLuong;
	}
	public void setNgayTinhLuong(Date ngayTinhLuong) {
		this.ngayTinhLuong = ngayTinhLuong;
	}
	public double getLuongCoBan() {
		return luongCoBan;
	}
	public void setLuongCoBan(double luongCoBan) {
		this.luongCoBan = luongCoBan;
	}
	public double getPhuCap() {
		return phuCap;
	}
	public void setPhuCap(double phuCap) {
		this.phuCap = phuCap;
	}
	public double getHeSoLuong() {
		return heSoLuong;
	}
	public void setHeSoLuong(double heSoLuong) {
		this.heSoLuong = heSoLuong;
	}
	
	
	public int getSoPhepDuocNghi() {
		return soPhepDuocNghi;
	}
	public void setSoPhepDuocNghi(int soPhepDuocNghi) {
		this.soPhepDuocNghi = soPhepDuocNghi;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maBangLuong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BangLuongNhanVien other = (BangLuongNhanVien) obj;
		return Objects.equals(maBangLuong, other.maBangLuong);
	}
	
	public BangLuongNhanVien(String maBangLuong, NhanVien nhanVien, Date ngayTinhLuong, double luongCoBan,
			double phuCap, double heSoLuong, int soPhepDuocNghi) {
		super();
		this.maBangLuong = maBangLuong;
		this.nhanVien = nhanVien;
		this.ngayTinhLuong = ngayTinhLuong;
		this.luongCoBan = luongCoBan;
		this.phuCap = phuCap;
		this.heSoLuong = heSoLuong;
		this.soPhepDuocNghi = soPhepDuocNghi;
	}
	public BangLuongNhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BangLuongNhanVien [maBangLuong=" + maBangLuong + ", nhanVien=" + nhanVien + ", ngayTinhLuong="
				+ ngayTinhLuong + ", luongCoBan=" + luongCoBan + ", phuCap=" + phuCap + ", heSoLuong=" + heSoLuong
				+ "]\n";
	}
	
	
}
