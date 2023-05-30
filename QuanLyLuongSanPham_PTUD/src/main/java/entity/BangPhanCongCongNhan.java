package entity;

import java.util.Date;
import java.util.Objects;

public class BangPhanCongCongNhan {
	private String maPhanCong;
	private Date ngayPhanCong;
	private int soLuongPhanCong;
	private CongDoanSanPham congDoan;
	private SanPham sanPham;
	private CongNhan congNhan;
	private NhanVien nguoiPhanCong;
	//các thuộc tính chỉ dùng cho hiển thị trên bảng
	private int soLuongLamDuoc;
	private String caLam;
	private Boolean trangThai;
	private double tienCong;
	
	public double getTienCong() {
		return tienCong;
	}
	public void setTienCong(double tienCong) {
		this.tienCong = tienCong;
	}
	public String getMaPhanCong() {
		return maPhanCong;
	}
	public void setMaPhanCong(String maPhanCong) {
		this.maPhanCong = maPhanCong;
	}
	public Date getNgayPhanCong() {
		return ngayPhanCong;
	}
	public void setNgayPhanCong(Date ngayPhanCong) {
		this.ngayPhanCong = ngayPhanCong;
	}
	public int getSoLuongPhanCong() {
		return soLuongPhanCong;
	}
	public void setSoLuongPhanCong(int soLuongPhanCong) {
		this.soLuongPhanCong = soLuongPhanCong;
	}
	public CongDoanSanPham getCongDoan() {
		return congDoan;
	}
	public void setCongDoan(CongDoanSanPham congDoan) {
		this.congDoan = congDoan;
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public CongNhan getCongNhan() {
		return congNhan;
	}
	public void setCongNhan(CongNhan congNhan) {
		this.congNhan = congNhan;
	}
	public NhanVien getNguoiPhanCong() {
		return nguoiPhanCong;
	}
	public void setNguoiPhanCong(NhanVien nguoiPhanCong) {
		this.nguoiPhanCong = nguoiPhanCong;
	}
	public BangPhanCongCongNhan(String maPhanCong, Date ngayPhanCong, int soLuongPhanCong, CongDoanSanPham congDoan,
			SanPham sanPham, CongNhan congNhan, NhanVien nguoiPhanCong) {
		super();
		this.maPhanCong = maPhanCong;
		this.ngayPhanCong = ngayPhanCong;
		this.soLuongPhanCong = soLuongPhanCong;
		this.congDoan = congDoan;
		this.sanPham = sanPham;
		this.congNhan = congNhan;
		this.nguoiPhanCong = nguoiPhanCong;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maPhanCong);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BangPhanCongCongNhan other = (BangPhanCongCongNhan) obj;
		return Objects.equals(maPhanCong, other.maPhanCong);
	}
	public BangPhanCongCongNhan() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BangPhanCongCongNhan [maPhanCong=" + maPhanCong + ", ngayPhanCong=" + ngayPhanCong
				+ ", soLuongPhanCong=" + soLuongPhanCong + ", congDoan=" + congDoan + ", sanPham=" + sanPham
				+ ", congNhan=" + congNhan + ", nguoiPhanCong=" + nguoiPhanCong + "]";
	}
	public int getSoLuongLamDuoc() {
		return soLuongLamDuoc;
	}
	public void setSoLuongLamDuoc(int soLuongLamDuoc) {
		this.soLuongLamDuoc = soLuongLamDuoc;
	}
	public String getCaLam() {
		return caLam;
	}
	public void setCaLam(String caLam) {
		this.caLam = caLam;
	}
	public Boolean getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(Boolean trangThai) {
		this.trangThai = trangThai;
	}
	public BangPhanCongCongNhan(String maPhanCong, Date ngayPhanCong, int soLuongPhanCong, CongDoanSanPham congDoan,
			SanPham sanPham, CongNhan congNhan, NhanVien nguoiPhanCong, int soLuongLamDuoc, String caLam,
			Boolean trangThai, double tienCong) {
		super();
		this.maPhanCong = maPhanCong;
		this.ngayPhanCong = ngayPhanCong;
		this.soLuongPhanCong = soLuongPhanCong;
		this.congDoan = congDoan;
		this.sanPham = sanPham;
		this.congNhan = congNhan;
		this.nguoiPhanCong = nguoiPhanCong;
		this.soLuongLamDuoc = soLuongLamDuoc;
		this.caLam = caLam;
		this.trangThai = trangThai;
		this.tienCong = tienCong;
	}
	
	

	
}