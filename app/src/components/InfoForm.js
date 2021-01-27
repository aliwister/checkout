import React, { useState } from 'react';
import styled, { ThemeProvider } from 'styled-components';

import { Form } from 'react-bulma-components';
const { Input } = Form;
import 'react-bulma-components/dist/react-bulma-components.min.css';

const InfoDiv = styled.div`
  //padding: ${ props => props.theme.spacing(8)}px;
  // margin-bottom: ${ props => props.theme.spacing(4)}px;
`;

const FormInput = styled(Input)`
  &:focus {
    border-color: #6f8f9d;
    box-shadow: none;
  }
`;

export const InfoForm = (props) => {
  const {register} = props;
  const [email, setEmail] = useState(props.email ? props.email : "");
  return (
    <React.Fragment>
      <InfoDiv>
        <FormInput
          id="filled-full-width"
          name="email"
          placeholder="Email"
          //helperText="Please Enter Your E-mail!"
          label="Email"
          InputLabelProps={{
            shrink: true,
          }}
          variant="filled"
          domRef={register}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
      </InfoDiv>
    </React.Fragment>
  )
}