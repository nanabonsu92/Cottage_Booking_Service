import CottageOffer from "./CottageOffer"

const CottageOffers = ({offers}) => {
  
  return <div>
      {offers.map(offer => (<CottageOffer key={offer.bookingNumber} offer={offer} />))}
  </div>

}

export default CottageOffers