package cottageBookingService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.UUID;

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
		response.setContentType("application/json");
		
        // Retrieve parameters from the HTTP request
    	String name = request.getParameter("name");
    	int places = Integer.parseInt(request.getParameter("minPlaces"));
    	int bedrooms = Integer.parseInt(request.getParameter("minBedrooms"));
    	int maxLakeDistance = Integer.parseInt(request.getParameter("maxDistanceToLake"));
    	String city = request.getParameter("city");
    	int maxCityDistance = Integer.parseInt(request.getParameter("maxDistanceToCity"));
    	
    	int days = Integer.parseInt(request.getParameter("days"));
    	String startDateString = request.getParameter("startingDate");
    	
    	
    	
        // Instantiate SWDB and perform the query
        SWDB mediator = new SWDB();
        String pathOntology = this.getServletContext().getRealPath("/res/cottageOntology.ttl");
        String pathData = this.getServletContext().getRealPath("/res/cottages.ttl");
        
        
        mediator.searchForResult(pathOntology, pathData, places, bedrooms, maxLakeDistance, city, maxCityDistance, startDateString, days);

        // Get the results and prepare JSON response
        PrintWriter writer = response.getWriter();
        String result = mediator.getResult();
        
        // Set response type to JSON and write output
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"name\":\"" + name + "\",");
        out.println("\"bookingNumber\":\"" + UUID.randomUUID().toString() + "\",");
        out.println("\"suggestions\":" + result);
        out.println("}");
        out.flush();
        out.close(); 
	}

}
