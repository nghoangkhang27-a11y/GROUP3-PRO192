/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movieticketandseatmanagement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */

public class RoomManager {
    
    // Danh sách suất chiếu mẫu
    private List<String> showtimes = new ArrayList<>();
    
    // Danh sách 20 ghế trong phòng (4 hàng x 5 cột)
    private String[] allSeats = {
        "A1", "A2", "A3", "A4", "A5",
        "B1", "B2", "B3", "B4", "B5",
        "C1", "C2", "C3", "C4", "C5",
        "D1", "D2", "D3", "D4", "D5"
    };

    public RoomManager() {
        // Khởi tạo thông tin suất chiếu mẫu
        showtimes.add("SC01 | Phim: Mai | Gio chieu: 18:00");
        showtimes.add("SC02 | Phim: Kung Fu Panda 4 | Gio chieu: 20:30");
    }

    // Tra cứu suất chiếu
    public void displayShowtimes() {
        System.out.println("\n========== DANH SACH SUAT CHIEU ==========");
        for (String st : showtimes) {
            System.out.println(st);
        }
        System.out.println("------------------------------------------");
    }

    // Hiển thị sơ đồ phòng chiếu (không cần truyền tham số)
    public void displaySeatMap() {
        System.out.println("\n============== SO DO PHONG CHIEU (4x5) ==============");
        System.out.println("                [ MAN HINH CHIEU ]                  ");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < allSeats.length; i++) {
            System.out.print("[" + allSeats[i] + "] ");
            
            // Cứ đủ 5 ghế (1 hàng) thì xuống dòng và phân loại Standard / VIP
            if ((i + 1) % 5 == 0) {
                if (i < 15) {
                    System.out.println("  <- Hang Thuong (Standard)");
                } else {
                    System.out.println("  <- Hang VIP");
                }
            }
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("Ghi chu: Hang A, B, C (Standard) | Hang D (VIP)\n");
    }
}