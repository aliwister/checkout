import React, { useState } from 'react';
import {Container, Grid, Paper, Typography, Input, OutlinedInput } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";

import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";

import {RootGrid,
    InfoContainer,
    RightGrid,
    LeftGrid,
    Wrapper,
    Title,
    NavButton,
    ButtonDiv} from '../App.styles'

export const AddressForm = (props) => {
    //const { register, handleSubmit, watch, errors } = useForm();
    //const onSubmit = data => { console.log(data); }
    const {addresses, register} = props;
    //console.log(Object.getOwnPropertyNames(props.address).length);


    const initEdit = (!props.address.id && props.address.name)?-1:0;
    console.log(!props.address.id);
    console.log(!props.address.name);
    console.log(initEdit);

    const [edit, setEdit] = useState(initEdit);
    const [name,setName] = useState(initEdit?props.address.name:"");
    //const [,] = useState(!props.address.id && props.address.);


    const setAddress = (id) => {
        console.log(id);
    }
    return (
        <React.Fragment>
            <Grid container spacing={3}>
                {addresses.map(item => (
                <Grid item sm={6} key={item.id}>
                    <input name="Address" type="radio" value={item.address1} key={item.id}
                           ref={register({ required: true })}
                           onClick={() => setEdit(item.id)}
                           checked={edit === 0 && props.address.id === item.id}
                    />
                    <label>{item.address1}</label>
                </Grid>
                ))
                }
                <Grid item xs={12} sm={12}>
                <input name="Address" type="radio" value="-1" ref={register({ required: true })} onChange={() => setEdit(-1)} checked={edit === -1}/>
                <label>Add a new address:</label>
                </Grid>
                {edit == -1 &&
                (
                    <Grid container spacing={3}>
                    <Grid item xs={12} sm={6}>
                        <OutlinedInput
                            required
                            id="firstName"
                            name="firstName"
                            placeholder="First name"
                            fullWidth
                            autoComplete="fname"
                            inputRef={register}
                            value={name}
                            onChange={() => setName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <OutlinedInput
                            required
                            id="lastName"
                            name="lastName"
                            placeholder="Last name"
                            fullWidth
                            autoComplete="lname"
                            inputRef={register}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <OutlinedInput
                            required
                            id="address1"
                            name="address1"
                            placeholder="Address line 1"
                            fullWidth
                            autoComplete="billing address-line1"
                            inputRef={register}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <OutlinedInput
                            id="address2"
                            name="address2"
                            placeholder="Address line 2"
                            fullWidth
                            autoComplete="billing address-line2"
                            inputRef={register}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <OutlinedInput
                            required
                            id="city"
                            name="city"
                            placeholder="City"
                            fullWidth
                            autoComplete="billing address-level2"
                            inputRef={register}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <OutlinedInput id="state" name="state" placeholder="State/Province/Region" fullWidth
                        inputRef={register} />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <OutlinedInput
                            required
                            id="zip"
                            name="zip"
                            placeholder="Zip / Postal code"
                            fullWidth
                            autoComplete="billing postal-code"
                            inputRef={register}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <OutlinedInput
                            required
                            id="country"
                            name="country"
                            placeholder="Country"
                            fullWidth
                            autoComplete="billing country"
                            inputRef={register}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={<Checkbox color="secondary" name="saveAddress" value="yes"
                            inputRef={register} />}
                            label="Use this address for payment details"
                        />
                    </Grid>
                    </Grid>
                )}

            </Grid>
        </React.Fragment>
    )
}