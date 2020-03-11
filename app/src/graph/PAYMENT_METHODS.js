import gql from 'graphql-tag';
export const PAYMENT_METHODS = gql`
  query paymentMethods($currency: String) {
    paymentMethods(currency: $currency) {
        ref
        name
        image
    }
  }
`;