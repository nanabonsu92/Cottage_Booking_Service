import CottageOffer from "./CottageOffer"

const CottageOffers = ({offers}) => {
  if (offers === undefined) return <></>
  
  return <div>
      <div>Name for reservation: {offers.name}</div>
      <div>Offer number: {offers.bookingNumber}</div>
      {offers.suggestions.map(offer => (<CottageOffer key={offer.address} offer={offer} />))}
    </div>

}

export default CottageOffers