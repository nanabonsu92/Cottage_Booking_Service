package cottageBookingService;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.riot.RDFDataMgr;

import java.util.UUID;

public class SWDB {
    private String queryResult = "";

    // Search for cottages that meet booking requirements
    public void searchForResult(String pathOntology, String pathData, int places, int bedrooms, int maxLakeDistance, String city, int maxCityDistance) {
        // Load both RDF models from files
        Model model = ModelFactory.createDefaultModel();
        RDFDataMgr.read(model, pathOntology);
        RDFDataMgr.read(model, pathData);

        // Define SPARQL query string
        String queryString = "PREFIX : <http://localhost:8080/cottageBookingService/res/cottageOntology.ttl#> " +
                "SELECT ?address ?imgUrl ?places ?bedrooms ?lakeDist ?city ?cityDist " +
                "WHERE { " +
                "    ?cottage rdf:type :Cottage ;" +
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

        // Build JSON array string for the results
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
                    .append("\"name\":\"").append("User Name").append("\",")
                    .append("\"bookingNumber\":").append(UUID.randomUUID().toString().hashCode()).append(",")
                    .append("\"address\":\"").append(address.toString()).append("\",")
                    .append("\"imgUrl\":\"").append(imgUrl.toString()).append("\",")
                    .append("\"places\":").append(place.asLiteral().getInt()).append(",")
                    .append("\"bedrooms\":").append(bedroom.asLiteral().getInt()).append(",")
                    .append("\"distanceToLake\":").append(lakeDist.asLiteral().getInt()).append(",")
                    .append("\"closestCity\":\"").append(cityNode.toString()).append("\",")
                    .append("\"distanceToCity\":").append(cityDist.asLiteral().getInt()).append(",")
                    .append("\"startingDate\":\"21.10.2025\",") // Static dates as examples
                    .append("\"endingDate\":\"30.10.2025\"")
                    .append("}");
            first = false;
        }
        resultBuilder.append("]");
        queryResult = resultBuilder.toString();

        qexec.close();
    }

    // Return the result as a JSON string
    public String getResult() {
        return queryResult;
    }
}