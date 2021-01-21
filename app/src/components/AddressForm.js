import React, { useState } from 'react';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import startsWith from 'lodash.startswith';
import { Form, Columns, Container, Card, Dropdown, Box } from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';
import styled from 'styled-components';
import { Address } from '../App.styles';

const { Checkbox, Field, Input, Control, Select, Label, Radio } = Form;

const SelectBox = styled(Select)`
  width: 100%;
  height: auto !important;
  margin: 10px 0px;
  select {
    width: 100%;
    height: 2.9em;
    font-size: 15px;
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

const InsideInputLabel = styled(Label)`
  position: absolute;
  z-index: 10;
  font-size: 13px;
  font-weight: 200;
  margin-left: 12px;
`;

const InputColumns = styled(Columns.Column)`
  position: relative;
  padding-top: 0px !important;
  padding-bottom: 0px !important;
`;

const AddressDropDown = styled(Dropdown)`
  width: 100%;
  border: 1px solid #a6a6a6;
  border-radius: 5px;

  .dropdown-trigger {
    width: 100%;
  }

  button {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .dropdown-menu {
    width: 100%;
  }
`;

const AddAddressRadio = styled(Radio)`
  width: 100%;
  margin: 20px 0px 15px 0px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border: none;
  padding: 0px !important;
  justify-content: flex-start;
  :focus {
    outline: none;
  }
`;

const InfoInput = styled(Input)`
  font-size: 15px;
  margin: 10px 0px;
`;

export const AddressForm = (props) => {
  //const { register, handleSubmit, watch, errors } = useForm();
  //const onSubmit = data => { console.log(data); }

  const { addresses, register } = props;
  let address;
  if (!props.address) {
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
  const [state, setState] = useState(edit?address.state:"");
  const [country, setCountry] = useState("Oman");
  const [postalCode, setPostalcode] = useState(edit ? address.postalCode : "");
  const [save, setSave] = useState(edit ? address.save : false);
  const [addressSelect, setAddressSelect] = useState(addresses[0]);
  //const [,] = useState(!props.address.id && props.address.);
  // console.log(edit);

  const setAddress = (selected) => {
    //console.log(id);
    setAddressSelect(selected);
    setEdit(selected.id);
  }
  return (
    <>
      <Container>
        <AddressDropDown
          value={addressSelect}
          onChange={setAddress}
        >
          {addresses && addresses.map(x => (

            <Dropdown.Item value={x.id} key={x.id}>
              {x.firstName} {x.lastName} - {x.line1} {x.line2} {x.city} {x.phone}
            </Dropdown.Item>
          ))
          }
        </AddressDropDown>
      </Container>
      <Container>
        <AddAddressRadio name="add-address" checked={edit === -1} onChange={() => setEdit(-1)}>
          &nbsp;Add a new address:
        </AddAddressRadio>
        {edit == -1 &&
          (
            <>
              <InfoInput
                insideLabel
                required
                id="alias"
                name="alias"
                placeholder="Address Name, e.g. Home or Work"
                inputRef={register({ required: true, maxLength: 50, minLength: 2 })}
                value={alias}
                onChange={(e) => setAlias(e.target.value)}
              />
              <Columns style={{marginTop: '0px', marginBottom: '0px'}}>
                <InputColumns>
                  <InfoInput
                    required
                    id="firstName"
                    name="firstName"
                    placeholder="First name"
                    autoComplete="fname"
                    inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                  />
                </InputColumns>
                <InputColumns>
                  <InfoInput
                    required
                    id="lastName"
                    name="lastName"
                    placeholder="Last name"
                    autoComplete="lname"
                    inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                  />
                </InputColumns>
              </Columns>
              <InfoInput
                insideLabel
                required
                id="line1"
                name="line1"
                placeholder="Address line 1"
                autoComplete="billing address-line1"
                inputRef={register({ required: true, maxLength: 50, minLength: 2 })}
                value={line1}
                onChange={(e) => setLine1(e.target.value)}
              />
              <InfoInput
                insideLabel
                id="line2"
                name="line2"
                placeholder="Address line 2 "
                autoComplete="billing address-line2"
                inputRef={register({ maxLength: 50, minLength: 2 })}
                value={line2}
                onChange={(e) => setLine2(e.target.value)}
              />
              <InfoInput
                insideLabel
                required
                id="city"
                name="city"
                placeholder="City"
                autoComplete="billing address-level2"
                inputRef={register({ required: true, maxLength: 15, minLength: 2 })}
                value={city}
                onChange={(e) => setCity(e.target.value)}
              />
              <InfoInput
                required
                id="postalCode"
                name="postalCode"
                placeholder="Zip / Postal code"
                autoComplete="billing postal-code"
                inputRef={register}
                onChange={(e) => setPostalcode(e.target.value)}
                value={postalCode}
              />
              <InfoInput
                required
                id="state"
                name="state"
                placeholder="State/Province/Region"
                autoComplete="billing postal-code"
                inputRef={register}
                onChange={(e) => setState(e.target.value)}
                value={state}
              />
              <SelectBox
                insideLabel
                id="country"
                name="country"
                onChange={(e) => setCountry(e.target.value)}
                name="country"
                value={country}
              >
                <option value="Oman">Oman</option>
                <option value="Nigeria">Nigeria</option>
                <option value="Kenya">Kenya</option>
              </SelectBox>
              <PhoneInput
                type='text'
                country={'om'}
                value={mobile}
                onChange={mobile => setMobile(mobile.replace(/[^0-9]+/g, ''))}
                onlyCountries={['om']}
                masks={{ om: '+... ....-....' }}
                copyNumbersOnly={true}
                containerStyle={{ direction: 'ltr' }}
                inputStyle={{ width: '100%', height: '40px', paddingRight: '48px' }}
                style={{margin: '10px 0px'}}
                countryCodeEditable={false}
                isValid={(inputNumber, onlyCountries) => {
                  return onlyCountries.some((country) => {
                    return startsWith(inputNumber, country.dialCode) || startsWith(country.dialCode, inputNumber);
                  });
                }}
              />
              <input type="hidden" name="mobile" value={mobile} ref={register} />
              <Field>
                <CheckControl>
                  <Checkbox name="save">
                    &nbsp;&nbsp;&nbsp;Save this information for next time
                      </Checkbox>
                </CheckControl>
              </Field>
            </>
          )}
      </Container>
    </>
  )
}
