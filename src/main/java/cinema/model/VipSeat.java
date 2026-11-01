package cinema.model;

public class VipSeat extends Seat {
    public static final long price = 100000;

    public VipSeat(char row, int number) {
        super(row, number);
    }

    @Override
    public long calculatePrice() {
        return price;
    }

    @Override
    public String getTypeName() {
        return "VIP";
    }
}