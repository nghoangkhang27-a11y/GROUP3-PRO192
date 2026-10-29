package cinema.service;

/**
 * Lỗi nghiệp vụ (ghế sai, vé không tồn tại, tên rỗng...).
 * Thông điệp được in thẳng cho người dùng.
 */
public class BookingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BookingException(String message) {
        super(message);
    }
}
