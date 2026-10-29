package cinema.model;

/**
 * Trạng thái của một vé.
 */
public enum TicketStatus {
    ACTIVE("Còn hiệu lực"),
    CANCELLED("Đã hủy");

    private final String label;

    TicketStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
