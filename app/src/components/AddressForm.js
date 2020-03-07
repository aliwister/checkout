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
    const {addresses, register, address} = props;
    //const initEdit = ;


    const [edit, setEdit] = useState((!props.address || (!props.address.id && props.address.name))?-1:0);
    const [name,setName] = useState(edit?address.name:"");
    const [line1,setLine1] = useState(edit?address.line1:"");
    const [line2,setLine2] = useState(edit?address.line2:"");
    const [city,setCity] = useState(edit?address.city:"");
    const [country,setCountry] = useState(edit?address.country:"");
    //const [,] = useState(!props.address.id && props.address.);
    console.log(edit);

    const setAddress = (id) => {
        console.log(id);
    }
    return (
        <React.Fragment>
            <Grid container spacing={3}>
                {addresses.map(x => (
                <Grid item sm={6} key={x.id}>
                    <input name="Address" type="radio" value={x.id} key={x.id}
                           ref={register({ required: true })}
                           onChange={() => setEdit(x.id)}
                           checked={edit !== 0 && edit === x.id}
                    />
                    <label>{x.name} <br/> {x.line1} <br/> {x.line2} <br/> {x.city}</label>
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
                            onChange={(e) => setName(e.target.value)}
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
                            id="line1"
                            name="line1"
                            placeholder="Address line 1"
                            fullWidth
                            autoComplete="billing address-line1"
                            inputRef={register}
                            value={line1}
                            onChange={(e) => setLine1(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <OutlinedInput
                            id="line2"
                            name="line2"
                            placeholder="Address line 2"
                            fullWidth
                            autoComplete="billing address-line2"
                            inputRef={register}
                            value={line2}
                            onChange={(e) => setLine2(e.target.value)}
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
                            value={city}
                            onChange={(e) => setCity(e.target.value)}
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
                            value={country}
                            onChange={(e) => setCountry(e.target.value)}
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