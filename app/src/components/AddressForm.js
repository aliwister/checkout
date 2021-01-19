import React, { useState } from 'react';
import { Grid, Paper, Typography } from '@material-ui/core';
import styled, { ThemeProvider } from 'styled-components';
import { useForm, Controller } from "react-hook-form";
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
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

import { Form, Columns, Container } from 'react-bulma-components';
const { Checkbox, Label, Field, Input, Control } = Form;
import { Card } from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';

export const AddressForm = (props) => {
  //const { register, handleSubmit, watch, errors } = useForm();
  //const onSubmit = data => { console.log(data); }

  const { addresses, register } = props;
  let address;
  if (!props.address) {
    console.log("!props.address");
    address = { firstName: '', lastName: '', line1: '', line2: '', city: '', postalCode: '', country: 'Oman', mobile: '', state: '' }
    console.log(address)
  }
  else
    address = props.address;

  const [edit, setEdit] = useState((!props.address || (!address.id && address.firstName)) ? -1 : 0);
  const [alias, setAlias] = useState(edit ? address.firstName : "");
  const [firstName, setFirstName] = useState(edit ? address.firstName : "");
  const [lastName, setLastName] = useState(edit ? address.lastName : "");
  const [line1, setLine1] = useState(edit ? address.line1 : "");
  const [line2, setLine2] = useState(edit ? address.line2 : "");
  const [mobile, setMobile] = useState(edit ? address.mobile : "");
  const [city, setCity] = useState(edit ? address.city : "");
  //const [state,setState] = useState(edit?address.state:"");
  const [country, setCountry] = useState("Oman");
  const [postalCode, setPostalcode] = useState(edit ? address.postalCode : "");
  const [save, setSave] = useState(edit ? address.save : false);


  //const [,] = useState(!props.address.id && props.address.);
  // console.log(edit);

  const setAddress = (id) => {
    //console.log(id);
  }
  return (
    <>
      <Container>
            <>
              <Columns>
                <Columns.Column>
                  <Input size="normal"
                    required
                    id="firstName"
                    name="firstName"
                    placeholder="First name"
                    fullWidth
                    autoComplete="fname"
                    inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                  />
                </Columns.Column>
                <Columns.Column>
                  <Input size="normal"
                    required
                    id="lastName"
                    name="lastName"
                    placeholder="Last name"
                    fullWidth
                    autoComplete="lname"
                    inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                  />
                </Columns.Column>
              </Columns>
              <Columns>
                <Columns.Column>
                  <Input size="normal"
                    required
                    id="alias"
                    name="alias"
                    placeholder="Address Name, e.g. Home or Work"
                    fullWidth
                    inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={alias}
                    onChange={(e) => setAlias(e.target.value)}
                  />
                </Columns.Column>
              </Columns>
              <Grid container spacing={3}>
                <Grid item xs={12}>
                  <Input size="normal"
                    required
                    id="line1"
                    name="line1"
                    placeholder="Address line 1"
                    fullWidth
                    autoComplete="billing address-line1"
                    inputRef={register({ required: true, maxLength: 50, minLength: 2 })}
                    value={line1}
                    onChange={(e) => setLine1(e.target.value)}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Input size="normal"
                    id="line2"
                    name="line2"
                    placeholder="Address line 2"
                    fullWidth
                    autoComplete="billing address-line2"
                    inputRef={register({ maxLength: 50, minLength: 2 })}
                    value={line2}
                    onChange={(e) => setLine2(e.target.value)}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Input size="normal"
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
                <Grid item xs={12}>
                  <Input size="normal"
                    required
                    id="city"
                    name="city"
                    placeholder="City"
                    fullWidth
                    autoComplete="billing address-level2"
                    inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Input size="normal"
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
                  <PhoneInput
                    type='text'
                    country={'om'}
                    value={mobile}
                    onChange={mobile => setMobile(mobile.replace(/[^0-9]+/g, ''))}
                    onlyCountries={['om']}
                    masks={{ om: '+... ....-....' }}
                    copyNumbersOnly={true}
                    containerStyle={{ direction: 'ltr', marginBottom: '10px' }}
                    inputStyle={{ width: '100%', height: '30px', paddingRight: '48px' }}
                    countryCodeEditable={false}
                    isValid={(inputNumber, onlyCountries) => {
                      return onlyCountries.some((country) => {
                        return startsWith(inputNumber, country.dialCode) || startsWith(country.dialCode, inputNumber);
                      });
                    }}
                  />
                  <input type="hidden" name="mobile" value={mobile} ref={register} />
                </Grid>
                <Grid item xs={12}>
                  <Field>
                    <Control>
                      <Checkbox name="save">
                        &nbsp;&nbsp;&nbsp;Save this information for next time
                      </Checkbox>
                    </Control>
                  </Field>
                </Grid>
              </Grid>
            </>
      </Container>
    </>
  )
}
