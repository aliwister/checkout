import gql from 'graphql-tag';
export const CARRIERS = gql`
  query carriers($state: String!, $city: String!, $weight: BigDecimal!) {
    carriers(state: $state, city: $city, weight: $weight) {
        name
        value
        cost
    }
  }
`;