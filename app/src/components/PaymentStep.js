import React from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import CkoFrames from './CkoFrames';

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

export const PaymentStep = () => {
    const { register, handleSubmit } = useForm();
    const onSubmit = data => { console.log(data); }
    return (
        <React.Fragment>
            <Typography variant="h6">Payment Step</Typography>
            <InfoDiv>
                <CkoFrames />

            </InfoDiv>
        </React.Fragment>
    )
}