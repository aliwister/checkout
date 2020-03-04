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
import {NavButton} from "../App.styles";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

export const InfoStep = (props) => {
    const { register, handleSubmit, watch, errors } = useForm();
    const [setInfo,{loading, data2}] = useMutation(SET_INFO);
    let {data} = props;


    const sendTokenToServer = async (email) => {
        let address= {name:"Ali", line1:"line1", line2:"line2",city:"city",country:"Oman"};
        //let email= "aliwister@gmail.com";
        const cart_info = await setInfo({variables:{email, address,secureKey}
        });
        //data = cart_info;
        console.log(cart_info, 'cart_info');
    };

    const onSubmit = formData => {
        console.log("FORM DATA:")
        console.log(formData);
        let address= {name:"Ali", line1:"line1", line2:"line2",city:"city",country:"Oman"};
        let email= formData.email;
        setInfo({variables:{email, address, secureKey}
        });
        //sendTokenToServer(formData.email);
        return false;
    }
    return (
        <React.Fragment>
            {loading && <p>Loading...</p>}
            <Typography variant="h6">Customer Information</Typography>
            <form onSubmit={handleSubmit(onSubmit)}>
                <InfoDiv>
                    <InfoForm register={register} email={data.cart.email}/>
                </InfoDiv>
                <Typography variant="h6" gutterBottom>
                    Shipping address
                </Typography>
                <AddressForm addresses={[{id:1,address1:"address1"},{id:0,address1:"address2"}]} address={{id:1, name:"Ali",line1:"Line1"}} register={register} />
                <PaymentStep register={register} />
                <NavButton type="submit" ariant="contained"
                           color="primary">
                    Next
                </NavButton>
            </form>
        </React.Fragment>
    )
}