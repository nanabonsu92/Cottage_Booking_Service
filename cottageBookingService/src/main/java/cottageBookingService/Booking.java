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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    	int maxShift = Integer.parseInt(request.getParameter("maxDaysShift"));
    	
    	
        // Instantiate SWDB and perform the query
        SWDB mediator = new SWDB();
        String pathOntology = this.getServletContext().getRealPath("/res/cottageOntology.ttl");
        String pathData = this.getServletContext().getRealPath("/res/cottages.ttl");
        
        
        mediator.searchForResult(pathOntology, pathData, places, bedrooms, maxLakeDistance, city, maxCityDistance, startDateString, days, name, maxShift);

        // Get the results and prepare JSON response
        String result = mediator.getResult();
        
        // Set response type to JSON and write output
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close(); 
	}

}
