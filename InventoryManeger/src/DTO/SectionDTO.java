/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author 84328
 */
public class SectionDTO {
    private String code;
    private int space;
    private int free;

    public SectionDTO() {
    }
    
    public SectionDTO(String code, int space, int free) {
        this.code = code;
        this.space = space;
        this.free = free;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }
    
    
}
