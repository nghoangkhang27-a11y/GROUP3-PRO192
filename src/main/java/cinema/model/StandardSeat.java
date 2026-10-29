package cinema.model;

/**
 * [Thành viên 1] Ghế thường (hàng A, B, C) - 70.000 đồng.
 */
public class StandardSeat extends Seat {

    public static final long PRICE = 70000L;

    public StandardSeat(char row, int number) {
        super(row, number);
    }

    @Override
    public long calculatePrice() {
        return StandardSeat.PRICE;
    }

    @Override
    public String getTypeName() {
        return "Standard";
    }
}
