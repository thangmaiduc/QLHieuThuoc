package model;

public class HieuThuocModel {
    private Integer maNT;
    private String tenNT;
    private String diaChi;


    public HieuThuocModel() {

    }

    public Integer getMaNT() {
        return maNT;
    }

    public void setMaNT(Integer maNT) {
        this.maNT = maNT;
    }

    public String getTenNT() {
        return tenNT;
    }

    public void setTenNT(String tenNT) {
        this.tenNT = tenNT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public HieuThuocModel(Integer maNT, String tenNT, String diaChi) {
        this.maNT = maNT;
        this.tenNT = tenNT;
        this.diaChi = diaChi;
    }

    @Override
    public String toString() {
        return this.maNT+"\t\t"+this.tenNT+"\n"+this.diaChi;
    }
}
