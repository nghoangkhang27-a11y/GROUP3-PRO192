package cinema.model;

/**
 * [Thành viên 1] Lớp cơ sở trừu tượng của ghế.
 * Chỉ chứa dữ liệu CỐ ĐỊNH của ghế (mã, hàng, số, loại, giá).
 * Tình trạng đặt ghế KHÔNG nằm ở đây, vì nó phụ thuộc từng suất chiếu.
 */
public abstract class Seat {

    private final String seatId;
    private final char row;
    private final int number;

    protected Seat(char row, int number) {
        this.row = Character.toUpperCase(row);
        this.number = number;
        this.seatId = "" + this.row + this.number;
    }

    /** Tính đơn giá của ghế. Lớp con override để trả giá riêng (đa hình). */
    public abstract long calculatePrice();

    /** Tên loại ghế để hiển thị. */
    public abstract String getTypeName();

    public String getSeatId() {
        return this.seatId;
    }

    public char getRow() {
        return this.row;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return this.seatId + " (" + this.getTypeName() + ")";
    }
}
