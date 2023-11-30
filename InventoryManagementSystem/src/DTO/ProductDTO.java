/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class ProductDTO {
    //Basic info
    private String code;
    private String type;
    private String name;
    private String brand;
    private Double size;
    private Double price;
    
    //Storing info
    private Integer quantity;
    private String location;
    private String dateIN;
    private String imageDIR;

    public ProductDTO() {
    }

    public ProductDTO(String code, String type, String name, String brand, Double size, Double price, Integer quantity, String location, String dateIN, String imageDIR) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
        this.dateIN = dateIN;
        this.imageDIR = imageDIR;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateIN() {
        return dateIN;
    }

    public void setDateIN(String dateIN) {
        this.dateIN = dateIN;
    }

    public String getImageDIR() {
        return imageDIR;
    }

    public void setImageDIR(String imageDIR) {
        this.imageDIR = imageDIR;
    }
    
    public int compareWith(int n, ProductDTO p2){
        switch (n) {
            case 1:
                return this.code.compareTo(p2.getCode());
            case 2:
                return this.type.compareTo(p2.getType());
            case 3:
                return this.name.compareTo(p2.getName());
            case 4:
                return this.brand.compareTo(p2.getBrand());
            case 5:
                return this.size.compareTo(p2.getSize());
            case 6:
                return this.price.compareTo(p2.getPrice());
            case 7:
                return this.quantity.compareTo(p2.getQuantity());
            case 8:
                return this.location.compareTo(p2.getLocation());
            case 9:
                Integer d1 = Integer.parseInt(this.dateIN.substring(0,2)), d2 = Integer.parseInt(p2.getDateIN().substring(0, 2));
                Integer m1 = Integer.parseInt(this.dateIN.substring(3,5)), m2 = Integer.parseInt(p2.getDateIN().substring(3, 5));
                Integer y1 = Integer.parseInt(this.dateIN.substring(6)), y2 = Integer.parseInt(p2.getDateIN().substring(6));
                if(y1==y2){
                    if(m1==m2){
                        return d1.compareTo(d2);
                    }else{
                        return m1.compareTo(m2);
                    }
                }else return y1.compareTo(y2);
            default:
                return 0;
        }
    }
}
