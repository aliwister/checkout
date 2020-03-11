import React, {useState} from 'react';
import {Container, Grid, Paper, Typography, TextField } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";

const InfoDiv = styled.div`
  //padding: ${ props  =>  props.theme.spacing(8)}px;
  margin-bottom: ${ props  =>  props.theme.spacing(4)}px;
`;

export const InfoForm = (props) => {
    const {register} = props;
    const [email, setEmail] = useState(props.email);
    return (
        <React.Fragment>
           <InfoDiv>
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
                    value={email}
                />
            </InfoDiv>
        </React.Fragment>
    )
}