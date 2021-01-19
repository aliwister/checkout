import React from "react";
import { Section, Container, Image } from "react-bulma-components";
import styled from "styled-components";

const CartCheckoutSection = styled(Section)`
    width: 100%;
    padding: 0px 20px;
`;

const ProductContainer = styled(Container)`
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-center: space-between;
`;

const ProductImage = styled(Image)`
  width: 100px;
  height: 100px;
  border-radius: 10px;
`;

export const CartCheckout = ({ products }) => {
  return (
    <CartCheckoutSection>
      {products.map((item, i) =>
        <ProductContainer key={i}>
          <ProductImage src={item.image} />
        </ProductContainer>
      )}
    </CartCheckoutSection>
  )
}
