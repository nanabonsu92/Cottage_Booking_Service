import CottageOffer from "./CottageOffer"

const CottageOffers = ({offers}) => {
  if (offers === undefined) return <></>
  
  return <div>
      {offers.map(offer => (<CottageOffer key={offer.bookingNumber} offer={offer} />))}
    </div>
      //<div>Name for reservation: {offers.name}</div>
      //<div>Offer number: {offers.bookingNumber}</div>

}

export default CottageOffers