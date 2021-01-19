import React, { useState } from 'react';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import startsWith from 'lodash.startswith';
import { Form, Columns, Container } from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';
import styled from 'styled-components';

const { Checkbox, Field, Input, Control, Select } = Form;

const SelectBox = styled(Select)`
  width: 100%;
  height: auto !important;
  select {
    width: 100%;
    height: 2.9em;
  }
`;

const CheckControl = styled(Control)`
  label {
    height: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-weight: 300;
  }
`;

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
  const [company, setCompany] = useState("");
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
                id="company"
                name="company"
                placeholder="Company (optional)"
                fullWidth
                inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                value={company}
                onChange={(e) => setCompany(e.target.value)}
              />
            </Columns.Column>
          </Columns>
          <Columns>
            <Columns.Column>
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
            </Columns.Column>
          </Columns>
          <Columns>
            <Columns.Column>
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
            </Columns.Column>
          </Columns>
          <Columns>
            <Columns.Column>
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
            </Columns.Column>
          </Columns>
          <Columns>
            <Columns.Column>
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
            </Columns.Column>
          </Columns>
          <Columns>
            <Columns.Column>
              <SelectBox
                id="country"
                name="country"
                size="normal"
                onChange={(e) => setCountry(e.target.value)}
                name="country"
                fullWidth
                value={country}
              >
                <option value="Oman">Oman</option>
                <option value="Nigeria">Nigeria</option>
                <option value="Kenya">Kenya</option>
              </SelectBox>
            </Columns.Column>
          </Columns>
          <Columns style={{marginBottom: "0px"}}>
            <Columns.Column>

              <PhoneInput
                type='text'
                country={'ke'}
                value={mobile}
                onChange={mobile => setMobile(mobile.replace(/[^0-9]+/g, ''))}
                onlyCountries={['ke']}
                masks={{ om: '+... ....-....' }}
                copyNumbersOnly={true}
                containerStyle={{ direction: 'ltr' }}
                inputStyle={{ width: '100%', height: '40px', paddingRight: '48px' }}
                countryCodeEditable={false}
                isValid={(inputNumber, onlyCountries) => {
                  return onlyCountries.some((country) => {
                    return startsWith(inputNumber, country.dialCode) || startsWith(country.dialCode, inputNumber);
                  });
                }}
              />
              <input type="hidden" name="mobile" value={mobile} ref={register} />
            </Columns.Column>
          </Columns>
          <Field>
            <CheckControl>
              <Checkbox name="save">
                &nbsp;&nbsp;&nbsp;Save this information for next time
                      </Checkbox>
            </CheckControl>
          </Field>
        </>
      </Container>
    </>
  )
}
