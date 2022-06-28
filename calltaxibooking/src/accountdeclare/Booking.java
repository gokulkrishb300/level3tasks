package accountdeclare;

public class Booking {
	private int bookingId;
	private int customerId;
	private Points startingPoint;
	private Points destinationPoint;
	private long time;
	private boolean bookingType;
	private String taxi;
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Points getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(Points startingPoint) {
		this.startingPoint = startingPoint;
	}
	public Points getDestinationPoint() {
		return destinationPoint;
	}
	public void setDestinationPoint(Points destinationPoint) {
		this.destinationPoint = destinationPoint;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public byte isBookingType() {
		if(bookingType == true)
		{
			return 1;
		}
		return 2;
	}
	
	public void setBookingType(boolean bookingType) {
		this.bookingType = bookingType;
	}
	public String getTaxi() {
		return taxi;
	}
	public void setTaxi(String taxi) {
		this.taxi = taxi;
	}
	@Override
	public String toString() {
		return "BookingId : " + bookingId + "\nAlloted Taxi : " + taxi;
	}
	
}
