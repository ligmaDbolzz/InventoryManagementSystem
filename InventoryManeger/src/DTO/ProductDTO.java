
package DTO;

/**
 *
 * @author 84328
 */
public class ProductDTO {
    private String code;
    private String name; //name of the product
    private String brand;
    private String address;
    private int space;
    private int quantity;
    private int price;

    public ProductDTO() {
    }

    public ProductDTO(String code, String name, String brand, int space, int quantity, int price, String address) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.address = address;
        this.space = space;
        this.quantity = quantity;
        this.price = price;
    }
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
