package cinema;

import cinema.data.DataStore;
import cinema.data.DataStoreException;
import cinema.service.BookingService;
import cinema.ui.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String directory = args.length > 0 ? args[0] : "data";
        DataStore store = new DataStore(directory);
        Scanner scanner = new Scanner(System.in, "UTF-8");

        BookingService service;
        boolean saveEnabled = true;
        try {
            service = store.load();
            if (service == null) {
                service = BookingService.withSampleData();
                System.out.println("First run: sample data loaded(SC01, SC02).");

            } else {
                System.out.println("Data loaded from " + store.getDirectory() + ".");
            }
        } catch (DataStoreException e) {
            System.out.println("1.Exit so you can fix or delete the folder");
            System.out.println("2.Run temporarily with sample data(saving will be DISABLED)");
        }
        System.out.print("Choose (1/2): ");
        String answer = scanner.hasNextLine() ? scanner.nextLine().trim() : "1";
        if (!answer.equals("2")) {
            System.out.println("Exited. The data file was left unchanged.");
            return;
        }
        service = BookingService.withSampleData();
        saveEnabled = false;

        new Menu(service, store, scanner, saveEnabled).run();

    }
}