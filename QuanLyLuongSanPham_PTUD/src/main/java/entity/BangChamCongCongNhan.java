package entity;

import java.util.Date;
import java.util.Objects;

public class BangChamCongCongNhan {
	private String maChamCong;
	private Date ngayChamCong;
	private String caLam;
	private String trangthai;
	private int soLuongLam;
	private BangPhanCongCongNhan phanCong;
	private NhanVien nguoiChamCong;
	public String getMaChamCong() {
		return maChamCong;
	}
	public void setMaChamCong(String maChamCong) {
		this.maChamCong = maChamCong;
	}
	public Date getNgayChamCong() {
		return ngayChamCong;
	}
	public void setNgayChamCong(Date ngayChamCong) {
		this.ngayChamCong = ngayChamCong;
	}
	public String getCaLam() {
		return caLam;
	}
	public void setCaLam(String caLam) {
		this.caLam = caLam;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public int getSoLuongLam() {
		return soLuongLam;
	}
	public void setSoLuongLam(int soLuongLam) {
		this.soLuongLam = soLuongLam;
	}
	public BangPhanCongCongNhan getPhanCong() {
		return phanCong;
	}
	public void setPhanCong(BangPhanCongCongNhan phanCong) {
		this.phanCong = phanCong;
	}
	public NhanVien getNguoiChamCong() {
		return nguoiChamCong;
	}
	public void setNguoiChamCong(NhanVien nguoiChamCong) {
		this.nguoiChamCong = nguoiChamCong;
	}
	public BangChamCongCongNhan(String maChamCong, Date ngayChamCong, String caLam, String trangthai, int soLuongLam,
			BangPhanCongCongNhan phanCong, NhanVien nguoiChamCong) {
		super();
		this.maChamCong = maChamCong;
		this.ngayChamCong = ngayChamCong;
		this.caLam = caLam;
		this.trangthai = trangthai;
		this.soLuongLam = soLuongLam;
		this.phanCong = phanCong;
		this.nguoiChamCong = nguoiChamCong;
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
		BangChamCongCongNhan other = (BangChamCongCongNhan) obj;
		return Objects.equals(maChamCong, other.maChamCong);
	}
	public BangChamCongCongNhan() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BangChamCongCongNhan [maChamCong=" + maChamCong + ", ngayChamCong=" + ngayChamCong + ", caLam=" + caLam
				+ ", trangthai=" + trangthai + ", soLuongLam=" + soLuongLam + ", phanCong=" + phanCong
				+ ", nguoiChamCong=" + nguoiChamCong + "]";
	}
	
	
}