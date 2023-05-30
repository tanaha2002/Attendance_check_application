package entity;

import java.util.Objects;

public class CongDoanSanPham {
	private String maCongDoan;
	private String tenCongDoan;
	private double tienCongDoan;
	private int soLuong;
	private String tinhTrang;
	private String moTa;
	private int thuTu;
	private SanPham sanPham;
	public String getMaCongDoan() {
		return maCongDoan;
	}
	public void setMaCongDoan(String maCongDoan) {
		this.maCongDoan = maCongDoan;
	}
	public String getTenCongDoan() {
		return tenCongDoan;
	}
	public void setTenCongDoan(String tenCongDoan) {
		this.tenCongDoan = tenCongDoan;
	}
	public double getTienCongDoan() {
		return tienCongDoan;
	}
	public void setTienCongDoan(double tienCongDoan) {
		this.tienCongDoan = tienCongDoan;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public int getThuTu() {
		return thuTu;
	}
	public void setThuTu(int thuTu) {
		this.thuTu = thuTu;
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maCongDoan);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CongDoanSanPham other = (CongDoanSanPham) obj;
		return Objects.equals(maCongDoan, other.maCongDoan);
	}
	public CongDoanSanPham(String maCongDoan, String tenCongDoan, double tienCongDoan, int soLuong, String tinhTrang,
			String moTa, int thuTu, SanPham sanPham) {
		super();
		this.maCongDoan = maCongDoan;
		this.tenCongDoan = tenCongDoan;
		this.tienCongDoan = tienCongDoan;
		this.soLuong = soLuong;
		this.tinhTrang = tinhTrang;
		this.moTa = moTa;
		this.thuTu = thuTu;
		this.sanPham = sanPham;
	}
	public CongDoanSanPham() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CongDoanSanPham [maCongDoan=" + maCongDoan + ", tenCongDoan=" + tenCongDoan + ", tienCongDoan="
				+ tienCongDoan + ", soLuong=" + soLuong + ", tinhTrang=" + tinhTrang + ", moTa=" + moTa + ", thuTu="
				+ thuTu + ", sanPham=" + sanPham + "]";
	}
	
	
}
