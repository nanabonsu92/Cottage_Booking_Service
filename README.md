# Cottage Booking Service

This project is a web-based application that allows users to search for available cottages based on their booking preferences. The backend is implemented in Java using Servlets, RDF data, and SPARQL queries, while the frontend is built using React for a dynamic user interface. The system allows users to filter cottages based on various criteria, such as location, amenities, and booking dates.

## Features

- **Cottage Search**: Search for cottages based on the number of places, bedrooms, distance to the lake, city proximity, and available dates.
- **Booking Dates**: Check cottage availability by providing a start date and a range of possible shifts.
- **Real-time Results**: Fetches and displays available cottages in real-time, showing booking availability for a specified time frame.
- **Backend Integration**: Utilizes a backend service to query RDF data, fetching cottage details based on the user's input.
- **User Interface**: A React-based frontend that interacts with the backend, allowing users to easily input their preferences and view available cottages.

## Tech Stack

- **Frontend**: React
- **Backend**: Java, Servlets, SPARQL, RDF (Jena)
- **Database**: RDF files for cottage data and ontology
- **Other Technologies**: Apache Jena (for handling RDF queries)

## Project Structure

- **Backend (`cottageBookingService` package)**:
  - `Booking.java`: Servlet that handles the booking request, interacts with the SWDB class to query data, and returns results as a JSON response.
  - `Cottage.java`: Represents a Cottage object, including details such as address, image URL, number of places, bedrooms, and booking availability.
  - `SWDB.java`: Handles the RDF-based querying logic, including searching for available cottages and checking for booking overlaps.
  
- **Frontend (`src` folder)**:
  - `App.js`: Main React component that includes the form for users to input search parameters and displays the available cottage offers.
  - `BookingForm.js`: A form that collects user input for the search query.
  - `CottageOffers.js`: Displays the list of available cottages based on the search results.
  - `offerService.js`: A module for interacting with the backend API to fetch offers based on user input.

## How to Run the Project

### Backend Setup

1. Clone this repository to your local machine.
2. Set up a Java Servlet container (e.g., Tomcat).
3. Place the `cottageOntology.ttl` and `cottages.ttl` files into the `/res` directory of the project (you can adjust paths as needed).
4. Deploy the project to your Servlet container and access it via your browser.

### Frontend Setup

1. Install dependencies:

   ```bash
   npm install
