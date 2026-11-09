package cinema.model;

public class VipSeat extends Seat {
    private long price = 100000;

    public VipSeat(char row, int number, long price) {
        super(row, number);
        this.price = price;
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