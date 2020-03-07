import gql from 'graphql-tag';
export const CART = gql`
  query cart($secureKey: String!) {
    cart(secureKey: $secureKey) {
        id
        ref
        name
        phone
        email
        secureKey
        deliveryAddress {

             name
             line1
             line2
             city
             country
             postalCode
        }
        invoiceAddress {
            id
             name
             line1
             line2
             city
             country
             postalCode
        }
        addresses {
            id
             name
             line1
             line2
             city
             country
             postalCode
        }
        paymentMethods {
            ref
        }
        carrier
        currency
        items {
            productId
            sku
            image
            name
            quantity
            price
            subTotal
        }
        tenantId
    }
  }
`;