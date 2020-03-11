import gql from 'graphql-tag';
export const SET_INFO = gql`
  mutation setInfo($email: String, $address: AddressInput, $secureKey: String!) {
    setInfo(email: $email, address:$address, secureKey: $secureKey) {
       id
        ref
        name
        phone
        email
        secureKey
        deliveryAddress {

             firstName
             lastName
             line1
             line2
             city
             country
             postalCode
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
             firstName
             lastName
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