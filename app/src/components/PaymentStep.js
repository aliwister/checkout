import React, {useReducer, useState} from 'react';
import {Container, Grid, Paper, Typography, TextField, OutlinedInput} from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import CkoFrames from './CkoFrames';
import {useMutation, useQuery} from "@apollo/react-hooks";
import {PROCESS_PAYMENT} from "../graph/PROCESS_PAYMENT";
import {PAYMENT_METHODS} from "../graph/PAYMENT_METHODS";
import {ButtonDiv, NavButton} from "../App.styles";
import { FaExpeditedssl } from 'react-icons/fa';
import Loader from "./Loader";
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

export const PaymentStep = ({state, dispatch}) => {

    const { register, handleSubmit } = useForm();
    //const [state, dispatch] = useReducer(reducer, initialState);
    const { data, error, loading } = useQuery(PAYMENT_METHODS, {
        variables: {currency: state.cart.currency}
    });
    const [processPaymentMutation,{loading2, data2}] = useMutation(PROCESS_PAYMENT);
    const [paymentMethod, setPaymentMethod] = useState();
    const [name, setName] = useState(state.cart.name);


    const onSubmit = async () => {
        console.log("In onsubmit");
        console.log(data);
        const {
            data: { processPayment },
        } = await processPaymentMutation({
            variables: {token: null, ref:paymentMethod, secureKey}
        });
        if(processPayment.redirect)
            window.location = processPayment.redirect;
        else
            alert("Payment unsuccessful");
    }

    const handleProcessPayment = async (token) => {
        const {
            data: { processPayment },
        } = await processPaymentMutation({
            variables: {token, ref:paymentMethod, secureKey}
        });
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
                {data.paymentMethods.map(x => (
                    <Grid item sm={6} key={x.ref}>
                        <input name="pm" type="radio" value={x.ref} key={x.ref}
                               ref={register({ required: true })}
                               onChange={() => setPaymentMethod(x.ref)}
                               checked={paymentMethod === x.ref}
                        />
                        {x.image && (<img src ={x.image} />)}
                        {!x.image &&(<span>{x.name} </span>)}
                        {(x.ref == 'checkoutcom' && paymentMethod === x.ref) && (

                                <CkoFrames handleProcessPayment={handleProcessPayment} customerName={name}/>

                            )}

                    </Grid>
                ))}
                <form onSubmit={handleSubmit(onSubmit)}>
                    {(paymentMethod != 'checkoutcom') && (<><NavButton type="submit" variant="contained"
                           color="primary">
                    Complete Order
                </NavButton>
                        <NavButton variant="contained"
                        color="secondary" onClick={() =>  dispatch({type: 'PREV'})}>
                    {(loading)?<Loader />:(
                        <span>Back</span>
                        )}
                        </NavButton>
                    </>
                    )}
                </form>
            </InfoDiv>
        </React.Fragment>
    )
}