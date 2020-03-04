import React from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

export const CarrierStep = () => {
    const { register, handleSubmit } = useForm();
    const onSubmit = data => { console.log(data); }
    return (
        <React.Fragment>
            <Typography variant="h6">Carrier Step</Typography>
            <InfoDiv>
                <form onSubmit={handleSubmit(onSubmit)}>
                    <TextField
                        id="filled-full-width"
                        name="email"
                        placeholder="Email"
                        helperText="Please Enter Your E-mail!"
                        fullWidth
                        label="Email"
                        InputLabelProps={{
                            shrink: true,
                        }}
                        variant="filled"
                        inputRef={register}
                    />
                    <input type="submit" />
                </form>

            </InfoDiv>
        </React.Fragment>
    )
}