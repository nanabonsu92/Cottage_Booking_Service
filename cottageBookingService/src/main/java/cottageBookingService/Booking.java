package cottageBookingService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/Booking")
public class Booking extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Booking() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the HTTP request
        String name = request.getParameter("name");
        int places = Integer.parseInt(request.getParameter("places"));
        int bedrooms = Integer.parseInt(request.getParameter("bedrooms"));
        int maxLakeDistance = Integer.parseInt(request.getParameter("lakeDistance"));
        String city = request.getParameter("city");
        int maxCityDistance = Integer.parseInt(request.getParameter("cityDistance"));

        // Instantiate SWDB and perform the query
        SWDB mediator = new SWDB();
        String pathOntology = this.getServletContext().getRealPath("/WEB-INF/res/cottageOntology.ttl");
        String pathData = this.getServletContext().getRealPath("/WEB-INF/res/cottages.ttl");
        mediator.searchForResult(pathOntology, pathData, places, bedrooms, maxLakeDistance, city, maxCityDistance);

        // Get the results and prepare JSON response
        String result = mediator.getResult();

        // Set response type to JSON and write output
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"name\":\"" + name + "\",");
        out.println("\"bookingNumber\":\"" + UUID.randomUUID() + "\",");
        out.println("\"suggestions\":" + result);
        out.println("}");
        out.flush();
        out.close();
    }
}