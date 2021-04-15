import gql from 'graphql-tag';
export const SET_INFO = gql`
  mutation setInfo($email: String, $address: AddressInput, $secureKey: String!, $carrier: String) {
    setInfo(email: $email, address:$address, secureKey: $secureKey, carrier: $carrier) {
       id
        ref
        name
        phone
        email
        secureKey
        deliveryAddressId
        deliveryAddress {

             firstName
             lastName
             line1
             line2
             city
             country
             postalCode
save
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