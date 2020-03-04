import React, {useState} from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

export const CreditCardForm = (props) => {
    const {register} = props;
    const cardTypes = ["VISA", "MasterCard"];

    const [type, setType] = useState();
    const [cc, setCc] = useState();
    const [cvv, setCvv] = useState();
    const [expiration, setExpiration] = useState();

    return (
        <React.Fragment>
            {cardTypes.map(item => (
                <Grid item sm={6} key={item.id}>
                    <input name="type" type="radio" value={item} key={item}
                           ref={register({ required: true })}
                           onClick={() => setType(item)}
                           checked={type === item}
                    />
                    <label>{item}</label>
                </Grid>
            ))
            }
            <TextField
                id="filled-full-width"
                name="cc"
                placeholder="Credit Card #"
                helperText="Please Enter Your Credit Card #!"
                fullWidth
                label="CC #"
                InputLabelProps={{
                    shrink: true,
                }}
                variant="filled"
                inputRef={register}
                value={cc}
                onChange={e => setCc(e.target.value)}
            />
            <TextField
                id="filled-full-width"
                name="cvv"
                placeholder="CVV #"
                helperText="Please Enter Your E-mail!"
                label="Email"
                InputLabelProps={{
                    shrink: true,
                }}
                variant="filled"
                inputRef={register}
                value={cvv}
                onChange={e => setCvv(e.target.value)}
            />
            <TextField
                id="filled-full-width"
                name="expiration"
                placeholder="Credit Card Expiration"
                helperText="Please Enter Your Card Expiration!"
                fullWidth
                label="Expiration Date"
                InputLabelProps={{
                    shrink: true,
                }}
                variant="filled"
                inputRef={register}
                value={expiration}
                onChange={e => setExpiration(e.target.value)}
            />

        </React.Fragment>
    )
}