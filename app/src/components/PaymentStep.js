import React, {useReducer} from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import CkoFrames from './CkoFrames';
import {useMutation} from "@apollo/react-hooks";
import {PROCESS_PAYMENT} from "../graph/PROCESS_PAYMENT";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;
const initialState = {
    token: false,
    hideFrame: false
};
function reducer(state, action) {
    switch (action.type) {
        case 'PROCESS_BEGIN':;
            console.log('PROCESS_BEGIN', action.payload);
            return {token: action.payload};
        case 'PROCESS_DONE':
            console.log('done');
            return {
                token: false,
                hideFrame: true
            };
        default:
            throw new Error('Unknown step');
    }

}

export const PaymentStep = () => {

    const { register, handleSubmit } = useForm();
    //const [state, dispatch] = useReducer(reducer, initialState);
    const [processPaymentMutation,{loading, data}] = useMutation(PROCESS_PAYMENT);


    const onSubmit = data => { console.log(data); }

    const handleProcessPayment = async (token) => {
        console.log(token);
        let name="Ali Allawati";
        const {
            data: { processPayment },
        } = await processPaymentMutation({
            variables: {token, name}
        });
        console.log(processPayment, 'cart_info');
        if(processPayment.status == 'REDIRECT')
            window.location = processPayment.redirect;
         //   dispatch({type: 'PROCESS_DONE'});
        //sendTokenToServer(formData.email);
        return false;
    }
    if(loading)
        return "loading";


    return (
        <React.Fragment>
            <Typography variant="h6">Payment Step</Typography>
            <InfoDiv>

                    <CkoFrames handleProcessPayment={handleProcessPayment}/>

            </InfoDiv>
        </React.Fragment>
    )
}