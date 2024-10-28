import { useState } from 'react'
import './App.css'
import CottageOffers from './CottageOffers'
import BookingForm from './BookingForm'
import offerService from './modules/Offers'

function App() {
  const [offers, setOffers] = useState([])

  const handleSubmit = (event) => {
    event.preventDefault()
    
    const form = event.target
    const params = {
      name: form.inputName.value,
      minPlaces: form.inputPlaces.value,
      minBedrooms: form.inputBedrooms.value,
      maxDistanceToLake: form.inputLakeDistance.value,
      city: form.inputCity.value,
      maxDistanceToCity: form.inputCityDistance.value,
      days: form.inputDays.value,
      startingDate: form.inputStartDate.value,
      maxDaysShift: form.inputPossibleShift.value
    }
    
    
    offerService.getOffers(params).then(recievedOffers => {
      setOffers(recievedOffers)
    }).catch(err => console.log(err))
  }

  return (
    <>
      <BookingForm handleSubmit={handleSubmit} />
      <CottageOffers offers={offers} />
    </>
  )
}

export default App
