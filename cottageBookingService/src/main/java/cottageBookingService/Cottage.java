package cottageBookingService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.RDFNode;

public class Cottage {
	private String address;
	private String imgUrl;
	private int places;
	private int bedrooms;
	private int lakeDistance;
	private String city;
	private int cityDistance;
	private LocalDate[] bookingStartDays;
	private LocalDate[] bookingEndDays;
	

	public Cottage(QuerySolution soln) {
        RDFNode address = soln.get("address");
        RDFNode imgUrl = soln.get("imgUrl");
        RDFNode places = soln.get("places");
        RDFNode bedrooms = soln.get("bedrooms");
        RDFNode lakeDist = soln.get("lakeDist");
        RDFNode cityNode = soln.get("city");
        RDFNode cityDist = soln.get("cityDist");
        RDFNode startDays = soln.get("startDates");
        RDFNode endDays = soln.get("endDates");
        
        this.address = address.toString();
        this.imgUrl = removeType(imgUrl.toString());
        this.places = places.asLiteral().getInt();
        this.bedrooms = bedrooms.asLiteral().getInt();
        this.lakeDistance = lakeDist.asLiteral().getInt();
        this.city = cityNode.toString();
        this.cityDistance = cityDist.asLiteral().getInt();
        

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (startDays != null) {
        	String[] days = startDays.toString().split(", ");
        	this.bookingStartDays = new LocalDate[days.length];
        	for (int i = 0; i < days.length; i++) {
        		this.bookingStartDays[i] = LocalDate.parse(days[i], formatter);
        	}
        }
        if (endDays != null) {
        	String[] days = endDays.toString().split(", ");
        	this.bookingEndDays = new LocalDate[days.length];
        	for (int i = 0; i < days.length; i++) {
        		this.bookingEndDays[i] = LocalDate.parse(days[i], formatter);
        	}
        }

	}
	/**
	 * Bookings do not overlap if start date of one is the same as end date of the other. 
	 * @param startDate Format dd.mm.yyyy
	 * @param endDate   Format dd.mm.yyyy
	 * @return true, if cottage has booking overlapping given date range.
	 */
	public boolean hasBookingOverlapping(LocalDate queryStartDate, LocalDate queryEndDate) {
		if (this.bookingStartDays == null) return false;
        
		boolean hasOverlappingBooking = false;
        for (int i = 0; i < this.bookingStartDays.length; i++) {
        	boolean doesNotOverlap = 
        					this.bookingEndDays[i].isBefore(queryStartDate) ||
        					this.bookingEndDays[i].isEqual(queryStartDate)  ||
        					this.bookingStartDays[i].isAfter(queryEndDate) ||
        					this.bookingStartDays[i].isEqual(queryEndDate);
        			
        	if (!doesNotOverlap) {
        		hasOverlappingBooking = true;
        	}
        }
		return hasOverlappingBooking;
	}
	
	public String toBookingJson(int bookingNumber, String name, String startDate, String endDate) {
		StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("{")
		.append("\"name\":\"").append(name).append("\",")
		.append("\"bookingNumber\":\"").append(bookingNumber).append("\",")
        .append("\"address\":\"").append(address.toString()).append("\",")
        .append("\"startingDate\":\"").append(startDate).append("\",")
        .append("\"endingDate\":\"").append(endDate).append("\",")
        .append("\"imgUrl\":\"").append(removeType(imgUrl.toString())).append("\",")
        .append("\"places\":").append(places).append(",")
        .append("\"bedrooms\":").append(bedrooms).append(",")
        .append("\"lakeDistance\":").append(lakeDistance).append(",")
        .append("\"city\":\"").append(city).append("\",")
        .append("\"cityDistance\":").append(cityDistance)
        .append("}");
		return resultBuilder.toString();
	}
	
	private String removeType(String value) {
    	return value.split("\\^\\^")[0];
    }
	
}
