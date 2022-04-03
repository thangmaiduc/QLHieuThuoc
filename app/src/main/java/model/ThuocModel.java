package model;

public class ThuocModel {
    private Integer maThuoc;
    private String tenThuoc;
    private String DVT;
    private Double donGia;

    public ThuocModel() {

    }

    public ThuocModel(Integer maThuoc, String tenThuoc, String DVT, Double donGia) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.DVT = DVT;
        this.donGia = donGia;
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
}
