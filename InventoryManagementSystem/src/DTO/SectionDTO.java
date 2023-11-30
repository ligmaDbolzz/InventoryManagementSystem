/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Admin
 */
public class SectionDTO {
    private String code;
    private Double size;
    private Double free;
    private Double value;
    private String imageDIR;

    public SectionDTO() {
    }

    public SectionDTO(String code, Double size) {
        this.code = code;
        this.size = size;
        this.free = size;
        this.value = 0.0;
        this.imageDIR = "";
    }

    public SectionDTO(String code, Double size, Double free, Double value,String imageDIR) {
        this.code = code;
        this.size = size;
        this.free = free;
        this.value = value;
        this.imageDIR = imageDIR;
    }
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Double getFree() {
        return free;
    }

    public void setFree(Double free) {
        this.free = free;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getImageDIR() {
        return imageDIR;
    }

    public void setImageDIR(String imageDIR) {
        this.imageDIR = imageDIR;
    }
    
    
}
