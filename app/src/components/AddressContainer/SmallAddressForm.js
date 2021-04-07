import {InfoInput, InputColumns} from "./AddressContainer.style";
import {Columns} from "react-bulma-components";
import {Controller} from "react-hook-form";
import React from "react";

export const SmallAddressForm = ({register, control}) => (
    <>
      <Controller as={<InfoInput />}
          register={register}
          control={control}
          required
          id="alias"
          name="alias"
          placeholder="Address Name, e.g. Home or Work"
          rules={{ required: true, maxLength: 10, minLength: 2 }}
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