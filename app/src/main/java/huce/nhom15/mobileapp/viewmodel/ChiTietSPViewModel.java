package huce.nhom15.mobileapp.viewmodel;

public class ChiTietSPViewModel {
    private String MauSac;
    private String ManHinh;
    private String Camera;
    private String HDH;
    private String RAM;
    private String TgTaiNghe;
    private String TgHopSac;
    private String MoTa;
    private String test;

    public ChiTietSPViewModel() {
    }


    public ChiTietSPViewModel(String mauSac, String manHinh, String camera, String HDH, String ram, String tgTaiNghe, String tgHopSac, String moTa, String test) {
        MauSac = mauSac;
        ManHinh = manHinh;
        Camera = camera;
        this.HDH = HDH;
        RAM = ram;
        TgTaiNghe = tgTaiNghe;
        TgHopSac = tgHopSac;
        MoTa = moTa;
        this.test=test;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getMauSac() {
        return MauSac;
    }

    public void setMauSac(String mauSac) {
        MauSac = mauSac;
    }

    public String getManHinh() {
        return ManHinh;
    }

    public void setManHinh(String manHinh) {
        ManHinh = manHinh;
    }

    public String getCamera() {
        return Camera;
    }

    public void setCamera(String camera) {
        Camera = camera;
    }

    public String getHDH() {
        return HDH;
    }

    public void setHDH(String HDH) {
        this.HDH = HDH;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String ram) {
        RAM = ram;
    }

    public String getTgTaiNghe() {
        return TgTaiNghe;
    }

    public void setTgTaiNghe(String tgTaiNghe) {
        TgTaiNghe = tgTaiNghe;
    }

    public String getTgHopSac() {
        return TgHopSac;
    }

    public void setTgHopSac(String tgHopSac) {
        TgHopSac = tgHopSac;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
