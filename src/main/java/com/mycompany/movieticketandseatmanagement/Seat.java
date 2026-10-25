/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movieticketandseatmanagement;

/**
 *
 * @author ASUS
 */
public class Seat { // Lớp quản lý ghế
    private String seatId; // Mã số ghế
    private String type; // loại ghê: thường và VIP
    private double price; // giá ghế

    public Seat(String seatId, String type, double price) {
        this.seatId = seatId;
        this.type = type;
        this.price = price;
    }
    public String getSeatId(){
        return seatId;
    }
    public String getType(){
        return type;
    }
    public double getPrice(){
        return price;
    }
}
