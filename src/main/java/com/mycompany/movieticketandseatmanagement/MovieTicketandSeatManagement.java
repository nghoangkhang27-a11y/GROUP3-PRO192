/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.movieticketandseatmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author nghoa
 */
public class MovieTicketandSeatManagement {

   public static void main(String[] args) {
        RoomManager manager = new RoomManager();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("\n========== QUAN LY SUAT CHIEU & SO DO GHE ==========");
            System.out.println("1. Xem danh sach suat chieu ");
            System.out.println("2. Xem so do phong chieu theo ma suat");
            System.out.println("0. Thoat chuong trinh");
            System.out.print("-> Nhap lua chon cua ban: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Đọc bỏ ký tự xuống dòng
            } else {
                System.out.println("Vui long chi nhap so hop le!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    // Tra cứu danh sách suất chiếu[cite: 4, 7]
                    manager.displayShowtimes();
                    break;

                case 2:
                    // Xem sơ đồ ghế theo mã suất[cite: 4, 10]
                    System.out.print("Nhap ma suat chieu: ");
                    String stId = scanner.nextLine().trim();
                    manager.displaySeatMap(stId);
                    break;

                case 0:
                    System.out.println("Tam biet! Cam on ban da su dung chuong trinh.");
                    break;

                default:
                    System.out.println("Lua chon khong hop le! Vui long chon 1, 2 hoac 0.");
            }
        } while (choice != 0);
}
}
