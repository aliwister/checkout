import gql from 'graphql-tag';
export const CARRIERS = gql`
  query carriers($secureKey: String!) {
    carriers(secureKey: $secureKey) {
        name
        value
        cost
    }
  }
`;