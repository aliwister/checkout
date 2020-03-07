import Typography from "@material-ui/core/Typography";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import React from "react";
import {useForm} from "react-hook-form";
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
    PromoCode,
    DeleteButton,
    CheckoutButtonWrapper,
    CheckoutButton,
    Title,
    PriceBox,
    NoProductMsg,
    ItemWrapper,
    CouponBoxWrapper,
    CouponCode,
    ErrorMsg,
} from './CartItemCard.style';

const CURRENCY = 'OMR'
const calculateItemPrice = (product) => {
    const quantity = product.quantity ? product.quantity : 1;
    const price = product.salePrice ? product.salePrice : product.price;
    const itemPrice = Currency(quantity).multiply(price);
    const itemPriceValue = Number(itemPrice.value);
    return itemPriceValue;
};

const calculateTotalPrice = (products, coupon) => {
    let total = Currency(0);
    let finalTotal;
    products.forEach(product => {
        const quantity = product.quantity ? product.quantity : 1;
        const price = product.salePrice ? product.salePrice : product.price;
        const itemPrice = Currency(quantity).multiply(price);
        total = Currency(total).add(itemPrice);
    });
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
        <ItemCards key={product.productIc}>
            <ListItemText
                type='vertical'
                value={product.quantity}
                style={{ marginRight: 15 }}
            />

            <ItemImgWrapper>
                <img
                    className='ListImage'
                    src={product.image}
                    height='150'
                    width='150'
                />
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
  padding: ${ props  =>  props.theme.spacing(1, 0)}px;
`;

const TotalText = styled(Typography)`
  font-weight: '700px';
`;

export const CartSummary = ({products}) => {
    const {register, handleSubmit} = useForm();
    const onSubmit = data => {
        console.log(data);
    }
    const totalPrice = calculateTotalPrice(products,null);
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Order Summary
            </Typography>
            <List disablePadding>

                <CartListItem >
                    <ItemWrapper>
                        <>
                        {products && products.length ? (
                            products.map(item => (
                                <CartItem
                                    key={`cartItem-${item.productId}`}

                                    product={item}
                                />
                            ))
                        ) : (
                            <NoProductMsg>
                                <FormattedMessage
                                    id='noProductFound'
                                    defaultMessage='No products found'
                                />
                            </NoProductMsg>
                        )}
                        </>
                        <PriceBox>
                            {CURRENCY}
                            {parseFloat(`${totalPrice}`).toFixed(1)}
                        </PriceBox>
                    </ItemWrapper>

                </CartListItem>
            </List>
        </React.Fragment>
    )
}