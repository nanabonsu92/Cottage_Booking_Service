const BookingForm = ({handleSubmit}) => {
  return <form onSubmit={handleSubmit}>
    <div>
      <label htmlFor="inputName">Name</label>
      <input name='inputName' placeholder='Enter name' />
    </div>
    <div>
      <label htmlFor="inputPlaces">Places</label>
      <input name='inputPlaces'placeholder='Required places' />
    </div>
    <div>
      <label htmlFor="inputBedrooms">Bedrooms</label>
      <input name='inputBedrooms' placeholder='Required bedrooms' />
    </div>
    <div>
      <label htmlFor="inputLakeDistance">Max lake distance</label>
      <input name='inputLakeDistance' placeholder='Max distance to lake (meters)' />
    </div>
    <div>
      <label htmlFor="inputCity">City</label>
      <input name='inputCity' placeholder='Nearest city' />
    </div>
    <div>
      <label htmlFor="inputCityDistance">Max distance to city</label>
      <input name='inputCityDistance' placeholder='Max distance to the city (kilometers)' />
    </div>
    <div>
      <label htmlFor="inputDays">Length of stay</label>
      <input name='inputDays' placeholder='Lenght of stay (days)' />
    </div>
    <div>
      <label htmlFor="inputStartDate">Start date</label>
      <input name='inputStartDate' placeholder='Starting date (dd.mm.yyyy)' />
    </div>
    <div>
    <label htmlFor="inputPossibleShift">Possible shift in days</label>
      <input name='inputPossibleShift' placeholder='Possible shift in starting date. (days)' />
    </div>
    <button type='submit'>Search</button>
  </form>
}


export default BookingForm