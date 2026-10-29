package cinema.model;

/**
 * [Thành viên 1] Ghế VIP (hàng D) - 100.000 đồng.
 */
public class VipSeat extends Seat {

    public static final long PRICE = 100000L;

    public VipSeat(char row, int number) {
        super(row, number);
    }

    @Override
    public long calculatePrice() {
        return VipSeat.PRICE;
    }

    @Override
    public String getTypeName() {
        return "VIP";
    }
}
