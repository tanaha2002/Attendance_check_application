package entity;

public class ToNhom {
	private String maToNhom;
	private String tenToNhom;
	private int soLuongCongNhan;
	public String getMaToNhom() {
		return maToNhom;
	}
	public void setMaToNhom(String maToNhom) {
		this.maToNhom = maToNhom;
	}
	public String getTenToNhom() {
		return tenToNhom;
	}
	public void setTenToNhom(String tenToNhom) {
		this.tenToNhom = tenToNhom;
	}
	public int getSoLuongCongNhan() {
		return soLuongCongNhan;
	}
	public void setSoLuongCongNhan(int soLuongCongNhan) {
		this.soLuongCongNhan = soLuongCongNhan;
	}
	public ToNhom(String maToNhom, String tenToNhom, int soLuongCongNhan) {
		super();
		this.maToNhom = maToNhom;
		this.tenToNhom = tenToNhom;
		this.soLuongCongNhan = soLuongCongNhan;
	}
	public ToNhom() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ToNhom [maToNhom=" + maToNhom + ", tenToNhom=" + tenToNhom + ", soLuongCongNhan=" + soLuongCongNhan
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maToNhom == null) ? 0 : maToNhom.hashCode());
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
		ToNhom other = (ToNhom) obj;
		if (maToNhom == null) {
			if (other.maToNhom != null)
				return false;
		} else if (!maToNhom.equals(other.maToNhom))
			return false;
		return true;
	}
	
}
