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
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
        }
        RoomManager manager = new RoomManager();
        Scanner scanner = new Scanner(System.in);
        List<String> bookedSeats = new ArrayList<>();
        int choice = -1;
        do {
            System.out.println("\n================ QUAN LY VE XEM PHIM ================");
            System.out.println("1. Xem danh sach suat chieu");
            System.out.println("2. Xem so do phong chieu");
            System.out.println("0. Thoat chuong trinh");
            System.out.println("-> Nhap lua chon cua ban: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Vui long nhap so hop le!");
                scanner.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    manager.displayShowtimes();
                    break;
                case 2:
                    manager.displaySeatMap();
                    break;
                case 0:
                    System.out.println("Cam on ban da su dung chuong trinh. Hen gap lai!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai!");
            }
        } while (choice != 0);
    }
}
