import React from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";

import {useMutation, useQuery} from '@apollo/react-hooks';
import {SET_INFO} from '../graph/setInfo';

import { InfoForm } from './InfoForm';
import { AddressForm } from './AddressForm';
import { CreditCardForm } from './CreditCardForm';
import { PaymentStep } from './PaymentStep';
import {ButtonDiv, NavButton} from "../App.styles";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;
//{[{id:1,address1:"address1"},{id:0,address1:"address2"}]} address={{id:1, name:"Ali",line1:"Line1"}}
export const InfoStep = (props) => {
    const { register, handleSubmit, watch, errors } = useForm();
    const [setInfoMutation,{loading, data2}] = useMutation(SET_INFO);
    let {state, dispatch} = props;

    const onSubmit = async (formData) => {
        let address= {firstName:formData.firstName, lastName:formData.lastName, line1:formData.line1, line2:formData.line2,city:formData.city,country:"Oman"};
        let email= formData.email;
        const {
            data: { setInfo },
        } = await setInfoMutation({
            variables: {email, address, secureKey}
        });
        if(setInfo)
            dispatch({type: 'NEXT'});
        return false;
    }
    return (
        <React.Fragment>
            {loading && <p>Loading...</p>}
            <Typography variant="h6">Customer Information</Typography>
            <form onSubmit={handleSubmit(onSubmit)}>
                <InfoDiv>
                    <InfoForm register={register} email={state.cart.email}/>
                </InfoDiv>
                <Typography variant="h6" gutterBottom>
                    Shipping address
                </Typography>
                <AddressForm addresses={state.cart.addresses} address={state.cart.deliveryAddress} register={register} />
                <ButtonDiv>
                    <NavButton type="submit" variant="contained"
                               color="primary">
                        Next
                    </NavButton>
                </ButtonDiv>
            </form>
        </React.Fragment>
    )
}