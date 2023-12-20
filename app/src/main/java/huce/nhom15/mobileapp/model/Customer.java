package huce.nhom15.mobileapp.model;

public class Customer {
    public int getKH_MaKH() {
        return KH_MaKH;
    }

    public void setKH_MaKH(int KH_MaKH) {
        this.KH_MaKH = KH_MaKH;
    }

    public String getKH_HoTen() {
        return KH_HoTen;
    }

    public void setKH_HoTen(String KH_HoTen) {
        this.KH_HoTen = KH_HoTen;
    }

    public String getKH_SDT() {
        return KH_SDT;
    }

    public void setKH_SDT(String KH_SDT) {
        this.KH_SDT = KH_SDT;
    }

    public String getKH_GioiTinh() {
        return KH_GioiTinh;
    }

    public void setKH_GioiTinh(String KH_GioiTinh) {
        this.KH_GioiTinh = KH_GioiTinh;
    }

    public String getKH_Email() {
        return KH_Email;
    }

    public void setKH_Email(String KH_Email) {
        this.KH_Email = KH_Email;
    }

    public String getKH_Password() {
        return KH_Password;
    }

    public void setKH_Password(String KH_Password) {
        this.KH_Password = KH_Password;
    }

    int KH_MaKH;
    String KH_HoTen, KH_SDT, KH_GioiTinh, KH_Email, KH_Password;

    @Override
    public String toString() {
        return "Customer{" +
                "KH_MaKH=" + KH_MaKH +
                ", KH_HoTen='" + KH_HoTen + '\'' +
                ", KH_SDT='" + KH_SDT + '\'' +
                ", KH_GioiTinh='" + KH_GioiTinh + '\'' +
                ", KH_Email='" + KH_Email + '\'' +
                ", KH_Password='" + KH_Password + '\'' +
                '}';
    }
}

