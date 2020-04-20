import React, {useState} from 'react';
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
import Loader from "./Loader";
import { string, object } from 'yup';
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from '@material-ui/lab/Alert';

function Alert(props) {
    return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

//{[{id:1,address1:"address1"},{id:0,address1:"address2"}]} address={{id:1, name:"Ali",line1:"Line1"}}
const schema = object().shape({
    alias: string().required("Alias is required and should be between 2 and 15 characters"),
    firstName: string().required("First name is required and should be between 2 and 15 characters"),
    lastName: string().required("Last name is required and should be between 2 and 15 characters"),
    line1: string().required("Address is required and should be between 2 and 15 characters" ),
    mobile: string().matches(/(968[7,9]{1}[0-9]{7})/, "A valid mobile number is required"),
    //password: string().matches(/(?=.*[\w])(?=.*[\W])[\w\W]{6,}/),
    //password: string().matches(/([\w\W]{6,})/, intl.formatMessage(messages.password)),
});
export const InfoStep =  (props) => {
    const { register, handleSubmit, watch, errors } = useForm();
    const [setInfoMutation,{loading, data2}] = useMutation(SET_INFO);
    const {state, dispatch} = props;
    const [open, setOpen] = React.useState(false);
    const [error, setError] = React.useState("");


    const handleClick = () => {
        setOpen(true);
    };

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    const onSubmit = async (formData) => {

        if(loading) return;
        let address= {
            id: formData.Address,
            firstName:formData.firstName,
            lastName:formData.lastName,
            line1:formData.line1,
            line2:formData.line2,
            city:formData.city,
            country:"Oman",
            postalCode: formData.postalCode,
            mobile: formData.mobile,
            save: formData.save,
            alias: formData.alias
        };
        let email= formData.email;
        console.log(formData);
        if(address.id < 0) {
            console.log("validating schema");
            await schema.validate(address).then(async function() {
                const {
                    data: { setInfo },
                } = await setInfoMutation({
                    variables: {email, address, secureKey}
                });
                if(setInfo)
                    dispatch({type: 'NEXT'});
            }).catch(function(err) {
                setError(err.message);
                setOpen(true);
                return;
            })
        } else {
            const {
                data: { setInfo },
            } = await setInfoMutation({
                variables: {email, address, secureKey}
            });
            if(setInfo)
                dispatch({type: 'NEXT'});
        }

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
                    {(!state.cart.items || !state.cart.items.length)?
                        <NavButton type="submit" variant="contained" disabled color="primary">Next</NavButton>:

                    <NavButton type="submit" variant="contained"
                               color="primary">
                        {(loading)?<Loader />:(
                        <span>Next</span>
                        )}
                    </NavButton>
                    }
                </ButtonDiv>
            </form>
            <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
                <Alert onClose={handleClose} severity="error">
                    {error}
                </Alert>
            </Snackbar>
        </React.Fragment>
    )
}