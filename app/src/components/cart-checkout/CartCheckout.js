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
import Currency from 'currency.js';

const { Input } = Form;

const CURRENCY = 'OMR';
const calculateItemPrice = (product) => {
  const quantity = product.quantity ? product.quantity : 1;
  const price = product.salePrice ? product.salePrice : product.price;
  const itemPrice = Currency(quantity).multiply(price);
  const itemPriceValue = Number(itemPrice.value);
  return itemPriceValue;
};

const calculateTotalPrice = (products, carrier, coupon) => {
  let total = Currency(0);
  let finalTotal;
  let subTotal;
  let subTotalPrice;
  products.forEach(product => {
    const quantity = product.quantity ? product.quantity : 1;
    const price = product.salePrice ? product.salePrice : product.price;
    const itemPrice = Currency(quantity).multiply(price);
    subTotal = Currency(total).add(itemPrice);
    total = Currency(total).add(itemPrice);
  });

  if (carrier) {
    total = Currency(total).add(carrier.cost);
  }
  finalTotal = Number(total.value);
  subTotalPrice = Number(subTotal.value);

  return { finalTotal, subTotalPrice };
};


const CartItem = ({ product }) => {
  const productPrice = calculateItemPrice(product);
  return (
    <ProductContainer>
      <ProductImageContainer>
        <ProductImage src={product.image} />
        <ProductsNumberBadge>{product.quantity ? product.quantity : 1}</ProductsNumberBadge>
      </ProductImageContainer>
      <ProductHeadingContainer>
        <ProductHeading>{product.name}</ProductHeading>
        {/* <ProductHeading subtitle>500GM</ProductHeading> */}
      </ProductHeadingContainer>
      <ProductPrice>{productPrice}&nbsp;{CURRENCY}</ProductPrice>
    </ProductContainer>
  )
}

export const CartCheckout = ({ products, carrier }) => {
  const [cardInfo, setCardInfo] = useState("");
  const finalPrices = calculateTotalPrice(products, carrier);
  return (
    <CartCheckoutSection>
      <ProductsSection>
        {products.map((item, i) =>
          <CartItem key={i} product={item} />
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
          <ProductInfoSubTitle fontSize="15px" fontWeight="600" subtitle>{finalPrices.subTotalPrice}&nbsp;{CURRENCY}</ProductInfoSubTitle>
        </ProductInfoRowContainer>
        <ProductInfoRowContainer>
          <ProductInfoTitle>Shipping</ProductInfoTitle>
          <ProductInfoSubTitle subtitle>Calculated at next step</ProductInfoSubTitle>
        </ProductInfoRowContainer>
      </ProductInfosSection>
      <ProductInfoRowContainer padding="20px 0px 0px 0px">
        <ProductInfoTitle>Total</ProductInfoTitle>
        <ProductInfoSubTitle fontSize="20px" fontWeight="600" subtitle>{finalPrices.finalTotal}&nbsp;{CURRENCY}</ProductInfoSubTitle>
      </ProductInfoRowContainer>
    </CartCheckoutSection>
  )
}
