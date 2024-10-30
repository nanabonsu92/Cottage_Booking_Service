import axios from "axios";

 const baseUrl = import.meta.env.PROD
  ? '/cottageBookingService/Booking' 
  : 'http://localhost:3001/offers'



const getOffers = (params) => {
  const config = {
      params
    }
  
  return axios.get(baseUrl, config).then(response => {
    return response.data
  })
}

export default {getOffers}