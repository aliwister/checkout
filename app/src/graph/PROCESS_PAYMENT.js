import gql from 'graphql-tag';
export const PROCESS_PAYMENT = gql`
    mutation processPayment($token: String) {
      processPayment(token: $token) {
        message
        redirect
        status
      }
    }
`;