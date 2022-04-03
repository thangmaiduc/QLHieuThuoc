package model;

public class HoaDonModel {
    private Integer maThuoc;
    private String tenThuoc;
    private String DVT;
    private Double donGia;
    private Integer SL;
    public HoaDonModel() {

    }

    public HoaDonModel(Integer maThuoc, String tenThuoc, String DVT, Double donGia,Integer SL) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.DVT = DVT;
        this.donGia = donGia;
        this.SL = SL;
    }

    public Integer getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(Integer maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Integer getSL() {
        return SL;
    }

    public void setSL(Integer SL) {
        this.SL = SL;
    }

    @Override
    public String toString() {
        return this.maThuoc+"\t\t"+this.tenThuoc+"\n"+this.DVT+"\n"+this.donGia*this.SL;
    }
}
