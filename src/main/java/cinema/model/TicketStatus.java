package cinema.model;


public enum TicketStatus {
  ACTIVE("Still valid"),

    /**
     *
     */
    CANCELLED("CANCELLED");
  
  private final String label;
  
  TicketStatus(String label) {
      this.label = label;
  }
  public String getLabel() {
      return this.label;
  }
}
