import Typography from "@material-ui/core/Typography";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import React from "react";
import { useForm } from "react-hook-form";
import styled from "styled-components";
import Currency from 'currency.js';
import {
  CartPopupBody,
  PopupHeader,
  PopupItemCount,
  CloseButton,
  ItemCards,
  ItemImgWrapper,
  ItemDetails,
  ItemTitle,
  ItemPrice,
  ItemWeight,
  TotalPrice,
  NoProductMsg,
  ItemWrapper,
} from './CartItemCard.style';
import CardHeader from "@material-ui/core/CardHeader";
import CardActionArea from "@material-ui/core/CardActionArea";
import Hidden from "@material-ui/core/Hidden";

import { Card } from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';


const CURRENCY = 'OMR'
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
  products.forEach(product => {
    const quantity = product.quantity ? product.quantity : 1;
    const price = product.salePrice ? product.salePrice : product.price;
    const itemPrice = Currency(quantity).multiply(price);
    total = Currency(total).add(itemPrice);
  });

  if (carrier) {
    console.log(carrier);
    total = Currency(total).add(carrier.cost);
  }
  finalTotal = Number(total.value);

  /*setLocalState('subTotalPrice', finalTotal);
  setSubTotalPrice(finalTotal);
  if (coupon.discountInPercent) {
      const discount = (finalTotal * Number(coupon.discountInPercent)) / 100;
      setLocalState('discount', discount);
      setDiscount(discount);
      finalTotal = finalTotal - discount;
  }*/
  return finalTotal;
};


const CartItem = ({ product, update }) => {
  const itemPrice = calculateItemPrice(product);
  return (
    <ItemCards key={product.productId}>
      <ListItemText
        type='vertical'
        value={product.quantity}
        style={{ marginRight: 15 }}
      />

      <ItemImgWrapper>
        {product.image &&
          <img
            className='ListImage'
            src={product.image}
            height='150'
            width='150'
          />}
      </ItemImgWrapper>

      <ItemDetails>
        <ItemTitle>{product.name}</ItemTitle>
        <ItemPrice>
          {CURRENCY}
          {product.salePrice ? product.salePrice : product.price}
        </ItemPrice>
        <ItemWeight>
          {product.quantity ? product.quantity : 1} x{' '}
          {product.unit ? product.unit : ''}
        </ItemWeight>
      </ItemDetails>
      <TotalPrice>
        {CURRENCY}
        {itemPrice}
      </TotalPrice>

    </ItemCards>
  );
};



const CartListItem = styled(ListItem)`
  padding: ${ props => props.theme.spacing(0, 0)}px;
`;

const TotalText = styled(Typography)`
  font-weight: '700px';
`;

export const CartSummary = ({ products, carrier }) => {
  const totalPrice = calculateTotalPrice(products, carrier);

  return (
    <Card >

      <List disablePadding>
        <Card.Content style={{ minHeight: '95vh' }}>

          <Typography variant="h6" gutterBottom>
            Order Summary
                </Typography>

          <CartListItem >
            <ItemWrapper>
              <>
                {products && products.length ? (
                  products.map(item => (
                    <CartItem
                      key={item.sku}

                      product={item}
                    />
                  ))
                ) : (
                    <NoProductMsg>
                      Cart is empty
                    </NoProductMsg>
                  )}
              </>
              {carrier && (
                <CartItem key={-1} product={{ productId: -1, name: carrier.name, price: carrier.cost }} />
              )}
            </ItemWrapper>

          </CartListItem>
        </Card.Content>
        <Hidden mdUp>
          <CardActionArea style={{ backgroundColor: '#ececec', position: 'fixed', bottom: '0' }}>
            <CartListItem >
              <ItemWrapper>
                <ItemCards style={{ backgroundColor: '#ececec' }}>
                  <ItemDetails>
                    Grand Total:
                            </ItemDetails>
                  <TotalPrice>
                    {CURRENCY}
                    {parseFloat(`${totalPrice}`).toFixed(1)}
                  </TotalPrice>
                </ItemCards>
              </ItemWrapper>
            </CartListItem>
          </CardActionArea>
        </Hidden>
        <Hidden smDown>
          <CardActionArea style={{ backgroundColor: '#ececec' }}>
            <CartListItem >
              <ItemWrapper>
                <ItemCards style={{ backgroundColor: '#ececec' }}>
                  <ItemDetails>
                    Grand Total:
                            </ItemDetails>
                  <TotalPrice>
                    {CURRENCY}
                    {parseFloat(`${totalPrice}`).toFixed(1)}
                  </TotalPrice>
                </ItemCards>
              </ItemWrapper>
            </CartListItem>
          </CardActionArea>
        </Hidden>
      </List>
    </Card>
  )
}