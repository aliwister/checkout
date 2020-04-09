import React, { useState } from 'react';
import {Container, Grid, Paper, Typography, Input, TextField} from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import startsWith from 'lodash.startswith';
import {
    RootGrid,
    InfoContainer,
    RightGrid,
    LeftGrid,
    Wrapper,
    Title,
    NavButton,
    ButtonDiv, Address
} from '../App.styles'
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";

export const AddressForm = (props) => {
    //const { register, handleSubmit, watch, errors } = useForm();
    //const onSubmit = data => { console.log(data); }

    const {addresses, register} = props;
    let address;
    if (!props.address) {
        console.log("!props.address");
        address = {firstName: '', lastName: '', line1: '', line2: '', city: '', postalCode: '', country: 'Oman', mobile: '', state: ''}
        console.log(address)
    }
    else
        address = props.address;

    const [edit, setEdit] = useState((!props.address || (!address.id && address.firstName))?-1:0);
    const [firstName,setFirstName] = useState(edit?address.firstName:"");
    const [lastName,setLastName] = useState(edit?address.lastName:"");
    const [line1,setLine1] = useState(edit?address.line1:"");
    const [line2,setLine2] = useState(edit?address.line2:"");
    const [mobile,setMobile] = useState(edit?address.mobile:"");
    const [city,setCity] = useState(edit?address.city:"");
    //const [state,setState] = useState(edit?address.state:"");
    const [country,setCountry] = useState("Oman");
    const [postalCode,setPostalcode] = useState(edit?address.postalCode:"");


    //const [,] = useState(!props.address.id && props.address.);
    console.log(edit);

    const setAddress = (id) => {
        console.log(id);
    }
    return (
        <React.Fragment>
            <Grid container spacing={3}>
                {addresses && addresses.map(x => (

                <Grid item sm={6} key={x.id}>
                    <Card>
                        <CardContent>
                    <input name="Address" type="radio" value={x.id} key={x.id}
                           ref={register({ required: true })}
                           onChange={() => setEdit(x.id)}
                           checked={edit !== 0 && edit === x.id}
                    />
                    <Address>{x.firstName} {x.lastName} <div>{x.line1 }</div><div>{x.line2}</div><div>{x.city}</div><div>{x.phone}</div></Address></CardContent></Card>
                </Grid>
                ))
                }
                <Grid item xs={12} sm={12}>
                <input name="Address" type="radio" value="-1" ref={register({ required: true })} onChange={() => setEdit(-1)} checked={edit === -1}/>
              Add a new address:
                </Grid>
                {edit == -1 &&
                (
                    <Grid container spacing={3}>
                    <Grid item xs={12} sm={6}>
                        <TextField size="small"
                            required
                            id="firstName"
                            name="firstName"
                            placeholder="First name"
                            fullWidth
                            autoComplete="fname"
                            inputRef={register}
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField size="small"
                            required
                            id="lastName"
                            name="lastName"
                            placeholder="Last name"
                            fullWidth
                            autoComplete="lname"
                            inputRef={register}
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField size="small"
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
                    <Grid item xs={6}>
                        <TextField size="small"
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
                    <Grid item xs={6}>
                        <PhoneInput
                            type='text'
                            country={'om'}
                            value={mobile}
                            onChange={mobile => setMobile(mobile.replace(/[^0-9]+/g,''))}
                            onlyCountries={['om']}
                            masks={{om: '+... ....-....'}}
                            copyNumbersOnly={true}
                            containerStyle={{direction:'ltr', marginBottom: '10px'}}
                            inputStyle={{width:'100%', height: '30px', paddingRight:'48px'}}
                            countryCodeEditable={false}
                            isValid={(inputNumber, onlyCountries) => {
                            return onlyCountries.some((country) => {
                                return startsWith(inputNumber, country.dialCode) || startsWith(country.dialCode, inputNumber);
                            });
                        }}
                        />
<input type="hidden" name="mobile" value={mobile} ref={register}/>
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField size="small"
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
                        <TextField size="small" id="state" name="state" placeholder="State/Province/Region" fullWidth
                        inputRef={register} />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField size="small"
                            required
                            id="postalCode"
                            name="postalCode"
                            placeholder="Zip / Postal code"
                            fullWidth
                            autoComplete="billing postal-code"
                            inputRef={register}
                            onChange={(e) => setPostalcode(e.target.value)}
                            value={postalCode}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField size="small"
                            required
                            id="country"
                            name="country"
                            placeholder="Country"
                            fullWidth
                            autoComplete="billing country"
                            inputRef={register}
                            value={country}

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