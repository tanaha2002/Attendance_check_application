package entity;

public class TaiKhoan {
	private String tenDangNhap;
	private String matKhau;
	private String loaiTaiKhoan;
	private NhanVien nhanVien;
	public String getTenDangNhap() {
		return tenDangNhap;
	}
	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public String getLoaiTaiKhoan() {
		return loaiTaiKhoan;
	}
	public void setLoaiTaiKhoan(String loaiTaiKhoan) {
		this.loaiTaiKhoan = loaiTaiKhoan;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public TaiKhoan(String tenDangNhap, String matKhau, String loaiTaiKhoan, NhanVien nhanVien) {
		super();
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.loaiTaiKhoan = loaiTaiKhoan;
		this.nhanVien = nhanVien;
	}
	public TaiKhoan() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tenDangNhap == null) ? 0 : tenDangNhap.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaiKhoan other = (TaiKhoan) obj;
		if (tenDangNhap == null) {
			if (other.tenDangNhap != null)
				return false;
		} else if (!tenDangNhap.equals(other.tenDangNhap))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TaiKhoan [tenDangNhap=" + tenDangNhap + ", matKhau=" + matKhau + ", loaiTaiKhoan=" + loaiTaiKhoan
				+ ", nhanVien=" + nhanVien + "]";
	}
	
}
