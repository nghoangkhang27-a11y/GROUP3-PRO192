/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movieticketandseatmanagement;

/**
 *
 * @author ASUS
 */
public abstract class Seat { // Lớp quản lý ghế
    private String seatId; // Mã số ghế
    private char row;
    private int number; // giá ghế

    public Seat(String seatId) {
        this.seatId = seatId;
        this.row = this.seatId.charAt(0);
        this.number = Integer.parseInt(this.seatId.substring(1));
    }
    public abstract long calculatePrice();
    public String getSeatId(){
        return seatId;
    }
    public char getRow(){
        return row;
    }
    public int getNumber(){
        return number;
    }
}
