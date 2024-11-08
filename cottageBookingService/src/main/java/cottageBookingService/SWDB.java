package cottageBookingService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

public class SWDB {
    private String queryResult = "";

    // Search for cottages that meet booking requirements
    public void searchForResult(String pathOntology, String pathData, int places, int bedrooms, int maxLakeDistance, String city, int maxCityDistance, String startDateString, int days, String name, int maxShift) {
        // Load both RDF models from files
        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, pathOntology);
        RDFDataMgr.read(model, pathData);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(startDateString, formatter);
        LocalDate[] startDates = getDates(startDate, maxShift);
        LocalDate[] endDates = getDates(startDate.plusDays(days), maxShift);
     
        
        // Define SPARQL query string
        StringBuilder queryStringBuilder = new StringBuilder();
        queryStringBuilder
        	.append("PREFIX : <http://localhost:8080/cottageBookingService/res/cottageOntology.ttl#> ")
        	.append("SELECT ?cottage ?address ?imgUrl ?places ?bedrooms ?lakeDist ?city ?cityDist ")
        	.append("(GROUP_CONCAT(?start; separator=\", \") AS ?startDates) (GROUP_CONCAT(?end; separator=\", \") AS ?endDates) ")
        	.append("WHERE { ")
        	  .append("?cottage a :Cottage ; ")
        	    .append(":hasAddress ?address ; ")
        	    .append(":hasImgUrl ?imgUrl ; ")
        	    .append(":hasPlaces ?places ; ")
        	    .append(":hasBedrooms ?bedrooms ; ")
        	    .append(":distanceToLake ?lakeDist ; ")
        	    .append(":closestCity ?city ; ")
        	    .append(":distanceToClosestCity ?cityDist . ")
        	    .append("OPTIONAL {")
        	      .append("?cottage :hasBooking ?booking . ")
        	      .append("?booking :hasStartDate ?start ; ")
        	        .append(":hasEndDate ?end . ")
        	    .append("}")
        	    .append("FILTER (?places >= ").append(places)
        	      .append(" && ?bedrooms >= ").append(bedrooms)
        	      .append(" && ?lakeDist <= ").append(maxLakeDistance)
        	      .append(" && ?cityDist <= ").append(maxCityDistance)
        	      .append(" && str(?city) = \"").append(city).append("\")")
        	.append("}")
        	.append("GROUP BY ?cottage ?address ?imgUrl ?places ?bedrooms ?lakeDist ?city ?cityDist");
        
        String queryString = queryStringBuilder.toString();
        
        // Execute SPARQL query
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();

        // Build JSON-like string for the results
        StringBuilder resultBuilder = new StringBuilder("[");
        boolean first = true;
        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            Cottage cottage = new Cottage(soln);
            
            for (int i = 0; i < startDates.length; i++) {
	            if (!cottage.hasBookingOverlapping(startDates[i], endDates[i])) {            	
	            	if (!first) resultBuilder.append(",");
	            	first = false;
	            	resultBuilder.append(cottage.toBookingJson(generateBookingNumber(), 
	            			name, startDates[i].format(formatter), endDates[i].format(formatter)));            
	            }
            }
            
        }
        resultBuilder.append("]");
        queryResult = resultBuilder.toString();

        qexec.close();
    }
    
    /** 
     * @param startDate 
     * @param maxShift in days
     * @return Array of dates around startDate 
     */
    private LocalDate[] getDates(LocalDate startDate, int maxShift) {
    	int days = maxShift * 2 + 1;
    	LocalDate[] dates = new LocalDate[days];
    	for (int i = 0; i < days; i++) {
    		dates[i] = startDate.plusDays(i - maxShift);
    	}
    	return dates;
	}
    
	/**
	 * @return random number between 10000 and 99999
	 */
	private int generateBookingNumber() {
    	return 10000 + (int)(89999 * Math.random());
    }
    
    // Return the result as a JSON string
    public String getResult() {
        return queryResult;
    }
}