package cottageBookingService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Servlet implementation class Booking
 */
@WebServlet("/Booking")
public class Booking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Booking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = response.getWriter();
        // Retrieve parameters from the HTTP request
		writer.append("name:");
    	String name = request.getParameter("name");
    	writer.append(name);
    	int places = Integer.parseInt(request.getParameter("minPlaces"));
    	writer.append(" places:").append(Integer.toString(places));
    	
    	writer.append(" bedrooms:");    	
        int bedrooms = Integer.parseInt(request.getParameter("minBedrooms"));
        writer.append(Integer.toString(bedrooms));
        
        writer.append(" maxLakeDistance:");
        int maxLakeDistance = Integer.parseInt(request.getParameter("maxDistanceToLake"));
        writer.append(Integer.toString(maxLakeDistance));
        
        writer.append(" city:");
        String city = request.getParameter("city");
        writer.append(city);
        
        writer.append(" maxCityDistance:");
        int maxCityDistance = Integer.parseInt(request.getParameter("maxDistanceToCity"));
        writer.append(Integer.toString(maxCityDistance));
        
        
		
        // Instantiate SWDB and perform the query
        SWDB mediator = new SWDB();
        String pathOntology = "http://localhost:8080/cottageBookingService/res/cottageOntology.ttl"; // this.getServletContext().getRealPath("/res/cottageOntology.ttl");
        String pathData = "http://localhost:8080/cottageBookingService/res/cottages.ttl";//this.getServletContext().getRealPath("/res/cottages.ttl");
        
        
        mediator.searchForResult(pathOntology, pathData, places, bedrooms, maxLakeDistance, city, maxCityDistance);

        // Get the results and prepare JSON response
        String result = mediator.getResult();
        writer.append(result);
        /*
        // Set response type to JSON and write output
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"name\":\"" + name + "\",");
        out.println("\"bookingNumber\":\"" + UUID.randomUUID() + "\",");
        out.println("\"suggestions\":" + result);
        out.println("}");
        out.flush();
        out.close(); */
	}

}
