import axios from "axios";

const baseUrl = 'http://localhost:3001/offers'

const getOffers = (params) => {
  const config = {
      params
    }
  
  return axios.get(baseUrl, config).then(response => {
    //console.log('koko resp', response)
    
    return response.data
  })
}

export default {getOffers}