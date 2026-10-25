/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movieticketandseatmanagement;

/**
 *
 * @author ASUS
 */
public class VipSeat extends Seat{
    public VipSeat(String seatId){
        super(seatId);
    }
    @Override
    public long calculatePrice(){
        return 100000;
    }
}
