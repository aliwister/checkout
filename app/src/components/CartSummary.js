import Typography from "@material-ui/core/Typography";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import React from "react";
import {useForm} from "react-hook-form";
import styled from "styled-components";

const products = [
    { name: 'Product 1', desc: 'A nice thing', price: '$9.99' },
    { name: 'Product 2', desc: 'Another thing', price: '$3.45' },
    { name: 'Product 3', desc: 'Something else', price: '$6.51' },
    { name: 'Product 4', desc: 'Best thing of all', price: '$14.11' },
    { name: 'Shipping', desc: '', price: 'Free' },
];

const CartListItem = styled(ListItem)`
  padding: ${ props  =>  props.theme.spacing(1, 0)}px;
`;

const TotalText = styled(Typography)`
  font-weight: '700px';
`;

export const CartSummary = ({cart}) => {
    const {register, handleSubmit} = useForm();
    const onSubmit = data => {
        console.log(data);
    }
    return (
        <React.Fragment>
            <Typography variant="h6" gutterBottom>
                Order Summary
            </Typography>
            <List disablePadding>
                {cart.map(item => (
                        <CartListItem key={item.sku}>
                            <ListItemText primary={item.name} secondary={item.sku}/>
                            <Typography variant="body2">{item.price}</Typography>
                        </CartListItem>
                    ))
                }
                <CartListItem >
                    <ListItemText primary="Total"/>
                    <TotalText variant="subtitle1">
                        $34.06
                    </TotalText>
                </CartListItem>
            </List>
        </React.Fragment>
    )
}