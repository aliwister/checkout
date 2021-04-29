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
        deliveryAddressId
        deliveryAddress {
            alias
            save
            firstName
            lastName
            line1
            line2
            city
            country
            postalCode
            mobile
            lng
            lat
            plusCode
         
        }
        invoiceAddress {
             id
             firstName
             lastName
             line1
             line2
             city
             country
             postalCode
        }
        addresses {
            id
            alias
            mobile
            firstName
            lastName
            line1
            line2
            city
            country
            postalCode
            lng
            lat
            plusCode
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