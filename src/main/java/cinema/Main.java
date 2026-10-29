package cinema;

import cinema.data.DataStore;
import cinema.data.DataStoreException;
import cinema.service.BookingService;
import cinema.ui.Menu;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * [Thành viên 5] Điểm vào chương trình.
 * Chỉ khởi động: nạp dữ liệu (hoặc dữ liệu mẫu) rồi giao cho Menu.
 *
 * Cách dùng: java -jar MovieTicketandSeatManagement.jar [thư-mục-dữ-liệu]
 * Mặc định thư mục dữ liệu là "data" (đường dẫn tương đối).
 */
public class Main {

    public static void main(String[] args) {
        Main.useUtf8Console();
        String directory = args.length > 0 ? args[0] : "data";
        DataStore store = new DataStore(directory);
        Scanner scanner = new Scanner(System.in, "UTF-8");

        BookingService service;
        boolean saveEnabled = true;
        try {
            service = store.load();
            if (service == null) {
                service = BookingService.withSampleData();
                System.out.println("Lần chạy đầu tiên: đã nạp dữ liệu mẫu (SC01, SC02).");
            } else {
                System.out.println("Đã nạp dữ liệu từ '" + store.getDirectory() + "'.");
            }
        } catch (DataStoreException e) {
            System.out.println("LỖI DỮ LIỆU: " + e.getMessage());
            System.out.println("Chương trình KHÔNG ghi đè file lỗi.");
            System.out.println(" 1. Thoát để tự sửa hoặc xóa thư mục '" + store.getDirectory() + "'");
            System.out.println(" 2. Chạy tạm với dữ liệu mẫu (việc lưu file sẽ bị TẮT)");
            System.out.print("Chọn (1/2): ");
            String answer = scanner.hasNextLine() ? scanner.nextLine().trim() : "1";
            if (!answer.equals("2")) {
                System.out.println("Đã thoát, file dữ liệu giữ nguyên.");
                return;
            }
            service = BookingService.withSampleData();
            saveEnabled = false;
        }
        new Menu(service, store, scanner, saveEnabled).run();
    }

    /**
     * Ép xuất ra bằng UTF-8 để tiếng Việt không bị đổi thành dấu '?'
     * dù bảng mã mặc định của máy là gì.
     */
    private static void useUtf8Console() {
        try {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // UTF-8 luôn có trên mọi JVM; nếu lỗi thì giữ nguyên console mặc định
        }
    }
}