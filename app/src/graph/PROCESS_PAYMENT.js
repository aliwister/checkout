import gql from 'graphql-tag';
export const PROCESS_PAYMENT = gql`
    mutation processPayment($token: String, $ref: String, $secureKey: String) {
      processPayment(token: $token, ref: $ref, secureKey: $secureKey ) {
        message
        payload
        status
      }
    }
`;