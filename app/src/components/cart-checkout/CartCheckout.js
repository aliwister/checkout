import React, { useState } from "react";
import { Form } from "react-bulma-components";
import {
  CartCheckoutSection,
  ProductsSection,
  ProductContainer,
  ProductImageContainer,
  ProductImage,
  ProductsNumberBadge,
  ProductHeadingContainer,
  ProductHeading,
  ProductPrice,
  CardInputFormField,
  CardInputForm,
  ApplyButton,
  ProductInfosSection,
  ProductInfoRowContainer,
  ProductInfoTitle,
  ProductInfoSubTitle,
} from "./CartCheckout.styles";

const { Input } = Form;

export const CartCheckout = ({ products }) => {
  const [cardInfo, setCardInfo] = useState("");

  return (
    <CartCheckoutSection>
      <ProductsSection>
        {products.map((item, i) =>
          <ProductContainer key={i}>
            <ProductImageContainer>
              <ProductImage src={item.image} />
              <ProductsNumberBadge>1</ProductsNumberBadge>
            </ProductImageContainer>
            <ProductHeadingContainer>
              <ProductHeading>SOM Fresh Beef</ProductHeading>
              <ProductHeading subtitle>500GM</ProductHeading>
            </ProductHeadingContainer>
            <ProductPrice>1,250OMR</ProductPrice>
          </ProductContainer>
        )}
      </ProductsSection>
      <CardInputFormField>
        <CardInputForm>
          <Input
            value={cardInfo}
            onChange={(e) => setCardInfo(e.target.value)}
            placeholder="Gift card or discount code"
          />
          <ApplyButton>Apply</ApplyButton>
        </CardInputForm>
      </CardInputFormField>
      <ProductInfosSection>
          <ProductInfoRowContainer>
            <ProductInfoTitle>Subtotal</ProductInfoTitle>
            <ProductInfoSubTitle fontSize="15px" fontWeight="600" subtitle>1,250 OMR</ProductInfoSubTitle>
          </ProductInfoRowContainer>
          <ProductInfoRowContainer>
            <ProductInfoTitle>Shipping</ProductInfoTitle>
            <ProductInfoSubTitle subtitle>Calculated at next step</ProductInfoSubTitle>
          </ProductInfoRowContainer>
      </ProductInfosSection>
      <ProductInfoRowContainer padding="20px 0px 0px 0px">
            <ProductInfoTitle>Total</ProductInfoTitle>
            <ProductInfoSubTitle fontSize="20px" fontWeight="600" subtitle>1,250 OMR</ProductInfoSubTitle>
      </ProductInfoRowContainer>
    </CartCheckoutSection>
  )
}
