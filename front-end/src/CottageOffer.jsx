const CottageOffer = ({offer}) => {
  //console.log(offer)
    return  (
        <div style={styles.container}>
          <img style={styles.image} src={offer.imgUrl} />
          <div style={styles.text}>
            <div>address: {offer.address}</div>
            <div>places: {offer.places}</div>
            <div>bedrooms: {offer.bedrooms}</div>
            <div>distance to nearest lake: {offer.lakeDistance}</div>
            <div>closest city: {offer.city}</div>
            <div>distance to the city: {offer.cityDistance}</div>
            <div>booking period: {offer.startingDate} - {offer.endingDate}</div>
          </div>
        </div>
    )
  }
//  <div>name: {offer.name}</div>
//  <div>booking number: {offer.bookingNumber}</div>

const styles = {
  container: {
    display: 'flex',
    alignItems: 'center', // Vertically center items
    justifyContent: 'center', // Horizontally center the image and text
    width: '60%',
    margin: '0 auto', // Center the container within its parent
    padding: '1rem',
    borderTop: '1px solid #ddd', 
  },
  text: {
    textAlign: 'left',
    marginLeft: '2%'
  },
  image: {
    marginRight: '2%', 
    width: '50%',
  }
};


export default CottageOffer