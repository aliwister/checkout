import gql from 'graphql-tag';
export const PROCESS_PAYMENT = gql`
    mutation processPayment($token: String, $name: String, $secureKey: String) {
      processPayment(token: $token, name: $name, secureKey: $secureKey ) {
        message
        redirect
        status
      }
    }
`;