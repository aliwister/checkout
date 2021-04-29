import React from "react";
import {InfoInput, InputColumns, Alias, InlineLabel, InlineRadio} from "./AddressContainer.style";
import Columns from 'react-bulma-components/lib/components/columns';
import {Controller} from "react-hook-form";

import Field from 'react-bulma-components/lib/components/form/components/field';

export const SmallAddressForm = ({register, control, errors, state, dispatch}) => (
    <>
    <Field>
      <InlineLabel>Address Type: </InlineLabel>
        <InlineRadio onClick={() => dispatch({type:'SELECT_ALIAS', payload:'Home'})} checked={state.alias === 'Home'}>Home</InlineRadio>
        <InlineRadio onClick={() => dispatch({type:'SELECT_ALIAS', payload:'Work'})} checked={state.alias === 'Work'}>Work</InlineRadio>
        <InlineRadio onClick={() => dispatch({type:'SELECT_ALIAS', payload:'Other'})} checked={state.alias === 'Other'}>Other: </InlineRadio>
        <Controller as={<Alias/>}
                    register={register}
                    control={control}
                    id="alias"
                    name="alias"
                    maxlength="10"
                    placeholder="Address Name, e.g. Home or Work"
                    rules={{ maxLength: 10, minLength: 2 }}
                    disabled={state.alias !== "Other"}
                    required={state.alias === "Other"}
        />
      {/*<Help color="success">This username is available</Help>*/}
    </Field>




      {errors.alias && errors.alias.type === "maxLength" && <Alert>Address Nickname can only be 10 characters or less</Alert>}
      <Columns style={{ marginTop: '0px', marginBottom: '0px' }}>
        <InputColumns>
          <Controller as={<InfoInput />}
              register={register}
              control={control}
              required
              id="firstName"
              name="firstName"
              placeholder="First name"
              rules={{ required: true, maxLength: 15, minLength: 2 }}
          />
        </InputColumns>
        <InputColumns>
          <Controller as={<InfoInput />}
              register={register}
              control={control}
              required
              id="lastName"
              name="lastName"
              placeholder="Last name"
              autoComplete="lname"
              rules={{ required: true, maxLength: 15, minLength: 2 }}
          />
        </InputColumns>
      </Columns>
      <Controller as={<InfoInput />}
          required
          id="line2"
          name="line2"
          placeholder="House #, Apt #, Landmark"
          autoComplete="billing address-line2"
          register={register}
          control={control}
          rules={{ required: true, maxLength: 50, minLength: 2 }}
      />
</>
)