package rmi;
import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String price;
    private String quantity;
    private String ngaysx;
    private String ngayhethan;

    public Product() {
        // Constructor mặc định
    }

    public Product(int id, String name, String price, String quantity, String ngaysx, String ngayhethan) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.ngaysx = ngaysx;
        this.ngayhethan = ngayhethan;
    }

    // Getter và setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và setter cho price
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getNgaysx() {
        return ngaysx;
    }

    public void setNgaysx(String ngaysx) {
        this.ngaysx = ngaysx;
    }

    public String getNgayhethan() {
        return ngayhethan;
    }

    public void setNgayhethan(String ngayhethan) {
        this.ngayhethan = ngayhethan;
    }

    // Getter và setter cho quantity
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", ngaysx='" + ngaysx + '\'' +
                ", ngayhethan='" + ngayhethan + '\'' +
                '}';
    }

}


