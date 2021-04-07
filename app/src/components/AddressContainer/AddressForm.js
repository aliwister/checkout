import React from "react";
import Columns from 'react-bulma-components/lib/components/columns';
import {CheckControl, InfoInput, InputColumns, SelectBox} from "./AddressContainer.style";
import {Controller} from "react-hook-form";

export const AddressForm = ({register, control}) => {

  return (
        <>
          <Controller as={<InfoInput />}
             register={register}
             control={control}
              // insideLabel
              required
              id="alias"
              name="alias"
              placeholder="Address Name, e.g. Home or Work"
              rules={{ required: true, maxLength: 50, minLength: 2 }}
          />
          <Columns style={{ marginTop: '0px', marginBottom: '0px' }}>
            <InputColumns>
              <Controller as={<InfoInput />}
                  register={register}
                  control={control}
                  required
                  id="firstName"
                  name="firstName"
                  placeholder="First name"
                  autoComplete="fname"
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
             register={register}
             control={control}
              // insideLabel
              required
              id="line1"
              name="line1"
              placeholder="Address line 1"
              autoComplete="billing address-line1"
              rules={{ required: true, maxLength: 50, minLength: 2 }}
          />
          <Controller as={<InfoInput />}
             register={register}
             control={control}
              // insideLabel
              id="line2"
              name="line2"
              placeholder="Address line 2 "
              autoComplete="billing address-line2"
              rules={{ maxLength: 50, minLength: 2 }}
          />
          <Controller as={<InfoInput />}
             register={register}
             control={control}
              // insideLabel
              required
              id="city"
              name="city"
              placeholder="City"
              autoComplete="billing address-level2"
              rules={{ required: true, maxLength: 15, minLength: 2 }}
          />
          <Controller as={<InfoInput />}
             register={register}
             control={control}
              required
              id="postalCode"
              name="postalCode"
              placeholder="Zip / Postal code"
              autoComplete="billing postal-code"
              rules={{}}
          />
          <Controller as={<InfoInput />}
             register={register}
             control={control}
              required
              id="state"
              name="state"
              placeholder="State/Province/Region"
              autoComplete="billing postal-code"
              rules={{}}
          />
        </>
  )
}