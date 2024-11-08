import CottageOffer from "./CottageOffer"

const CottageOffers = ({offers}) => {
  if (offers === undefined) return <></>
  
  return <div>
      {offers.map(offer => (<CottageOffer key={offer.bookingNumber} offer={offer} />))}
    </div>
}

export default CottageOffers