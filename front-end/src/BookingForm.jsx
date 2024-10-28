const BookingForm = ({handleSubmit}) => {
  return <form onSubmit={handleSubmit}>
    <div>
      <input name='inputName' placeholder='Enter name' />
      <input name='inputPlaces'placeholder='Required places' />
    </div>
    <div>
      <input name='inputBedrooms' placeholder='Required bedrooms' />
      <input name='inputLakeDistance' placeholder='Max distance to lake (meters)' />
    </div>
      <input name='inputCity' placeholder='Nearest city' />
      <input name='inputCityDistance' placeholder='Max distance to the city (kilometers)' />
    <div>
    <input name='inputDays' placeholder='Lenght of stay (days)' />
    <input name='inputStartDate' placeholder='Starting date (dd.mm.yyyy)' />
    </div>
    <div>
    <input name='inputPossibleShift' placeholder='Possible shift in starting date. (days)' />
    </div>
    <button type='submit'>Search</button>
  </form>
}


export default BookingForm