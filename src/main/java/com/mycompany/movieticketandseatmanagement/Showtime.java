/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movieticketandseatmanagement;

/**
 *
 * @author ASUS
 */
public class Showtime { //Lớp quản lý suất chiếu
    private String showTimeId; // Mã xuất chiếu
    private String movieName; // Tên phim
    private String time; // Giờ chiếu

    public Showtime(String showtimeId, String movieName, String time) {
        this.showTimeId = showtimeId;
        this.movieName = movieName;
        this.time = time;
    }

    public String getShowtimeId() {
        return showTimeId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getTime() {
        return time;
    }
    public void displayShowTimeInfo(int availableSeatsCount){
        System.out.printf("Ma suat: %-6s | Phim: %-25s | Gio: %-8s | Ghe trong: %d/20\n", showTimeId, movieName, time, availableSeatsCount);
    }
}
