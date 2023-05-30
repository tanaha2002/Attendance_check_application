package entity;

import java.util.Date;
import java.util.Objects;

public class NhanVien {
	private String maNhanVien;
	private String tenNhanVien;
	private Date ngaySinh;
	private String email;
	private String soDienThoai;
	private DiaChi diaChi;
	private Date ngayVaoLam;
	private String gioiTinh;
	private String vaiTro;
	private String anhDaiDien;
	private String trinhDoChuyenMon;
	private String trinhDoNgoaiNgu;
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public DiaChi getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(DiaChi diaChi) {
		this.diaChi = diaChi;
	}
	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getVaiTro() {
		return vaiTro;
	}
	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}
	public String getAnhDaiDien() {
		return anhDaiDien;
	}
	public void setAnhDaiDien(String anhDaiDien) {
		this.anhDaiDien = anhDaiDien;
	}
	public String getTrinhDoChuyenMon() {
		return trinhDoChuyenMon;
	}
	public void setTrinhDoChuyenMon(String trinhDoChuyenMon) {
		this.trinhDoChuyenMon = trinhDoChuyenMon;
	}
	public String getTrinhDoNgoaiNgu() {
		return trinhDoNgoaiNgu;
	}
	public void setTrinhDoNgoaiNgu(String trinhDoNgoaiNgu) {
		this.trinhDoNgoaiNgu = trinhDoNgoaiNgu;
	}
	public NhanVien(String maNhanVien, String tenNhanVien, Date ngaySinh, String email, String soDienThoai,
			DiaChi diaChi, Date ngayVaoLam, String gioiTinh, String vaiTro, String anhDaiDien, String trinhDoChuyenMon,
			String trinhDoNgoaiNgu) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.ngaySinh = ngaySinh;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.ngayVaoLam = ngayVaoLam;
		this.gioiTinh = gioiTinh;
		this.vaiTro = vaiTro;
		this.anhDaiDien = anhDaiDien;
		this.trinhDoChuyenMon = trinhDoChuyenMon;
		this.trinhDoNgoaiNgu = trinhDoNgoaiNgu;
	}
	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", ngaySinh=" + ngaySinh
				+ ", email=" + email + ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + ", ngayVaoLam="
				+ ngayVaoLam + ", gioiTinh=" + gioiTinh + ", vaiTro=" + vaiTro + ", anhDaiDien=" + anhDaiDien
				+ ", trinhDoChuyenMon=" + trinhDoChuyenMon + ", trinhDoNgoaiNgu=" + trinhDoNgoaiNgu + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}
	
	
	
	
	
	
}
