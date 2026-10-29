package cinema.data;

/**
 * Lỗi đọc/ghi file dữ liệu (file hỏng, thiếu file, không ghi được...).
 */
public class DataStoreException extends Exception {

    private static final long serialVersionUID = 1L;

    public DataStoreException(String message) {
        super(message);
    }

    public DataStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
