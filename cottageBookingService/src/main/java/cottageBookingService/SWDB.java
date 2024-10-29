package cottageBookingService;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;

public class SWDB {
    private String queryResult = "";

    // Search for cottages that meet booking requirements
    public void searchForResult(String pathOntology, String pathData, int places, int bedrooms, int maxLakeDistance, String city, int maxCityDistance, String startDate, int days) {
        // Load both RDF models from files
        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, pathOntology);
        RDFDataMgr.read(model, pathData);
     
        
        // Define SPARQL query string
        String queryString = "PREFIX : <http://localhost:8080/cottageBookingService/res/cottageOntology.ttl#> " +
        		"SELECT ?cottage ?address ?imgUrl ?places ?bedrooms ?lakeDist ?city ?cityDist " +
        		"WHERE { " +
        		"    ?cottage a :Cottage ;" +
        		"             :hasAddress ?address ;" +
        		"             :hasImgUrl ?imgUrl ;" +
        		"             :hasPlaces ?places ;" +
        		"             :hasBedrooms ?bedrooms ;" +
        		"             :distanceToLake ?lakeDist ;" +
        		"             :closestCity ?city ;" +
        		"             :distanceToClosestCity ?cityDist ." +
        		"    FILTER (?places >= " + places + " && ?bedrooms >= " + bedrooms + " && ?lakeDist <= " + maxLakeDistance +
        		" && ?cityDist <= " + maxCityDistance + " && str(?city) = \"" + city + "\")" +
        		"}";
        
        
        
        // Execute SPARQL query
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet results = qexec.execSelect();
        
        // Dealing with the dates
        String endDate = addDays(startDate, days);
        // Build JSON-like string for the results
        StringBuilder resultBuilder = new StringBuilder("[");
        boolean first = true;
        while (results.hasNext()) {
            if (!first) resultBuilder.append(",");
            QuerySolution soln = results.nextSolution();
            
            RDFNode address = soln.get("address");
            RDFNode imgUrl = soln.get("imgUrl");
            RDFNode place = soln.get("places");
            RDFNode bedroom = soln.get("bedrooms");
            RDFNode lakeDist = soln.get("lakeDist");
            RDFNode cityNode = soln.get("city");
            RDFNode cityDist = soln.get("cityDist");
            resultBuilder.append("{")
                    .append("\"address\":\"").append(address.toString()).append("\",")
                    .append("\"startingDate\":\"").append(startDate).append("\",")
                    .append("\"endingDate\":\"").append(endDate).append("\",")
                    .append("\"imgUrl\":\"").append(removeType(imgUrl.toString())).append("\",")
                    .append("\"places\":").append(place.asLiteral().getInt()).append(",")
                    .append("\"bedrooms\":").append(bedroom.asLiteral().getInt()).append(",")
                    .append("\"lakeDistance\":").append(lakeDist.asLiteral().getInt()).append(",")
                    .append("\"city\":\"").append(cityNode.toString()).append("\",")
                    .append("\"cityDistance\":").append(cityDist.asLiteral().getInt())
                    .append("}");
            first = false;
            
        }
        resultBuilder.append("]");
        queryResult = resultBuilder.toString();

        qexec.close();
    }
    
    //Calculating endDate offers
    private String addDays(String date, int days) {
    	DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    	Date startDate;
    	try {
    		startDate = dateFormat.parse(date);
    	} catch(Exception e) {
    		//TODO change to throw exception
    		return "invalid";
    	}
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(startDate);
    	calendar.add(Calendar.DATE, days);
    	Date dateToReturn = calendar.getTime();
		return dateFormat.format(dateToReturn);
    }
    
    private String removeType(String value) {
    	return value.split("\\^\\^")[0];
    }

    // Return the result as a JSON string
    public String getResult() {
        return queryResult;
    }
}