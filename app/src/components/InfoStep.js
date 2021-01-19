import React, { useState } from 'react';
import styled from 'styled-components';
import { useForm } from "react-hook-form";

import { useMutation } from '@apollo/react-hooks';
import { SET_INFO } from '../graph/setInfo';

import { InfoForm } from './InfoForm';
import { AddressForm } from './AddressForm';
import { ButtonDiv, NavButton } from "../App.styles";
import Loader from "./Loader";
import { string, object } from 'yup';
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from '@material-ui/lab/Alert';

import { Form, Container, Button } from 'react-bulma-components';
const { Checkbox, Field, Control, Radio } = Form;
import 'react-bulma-components/dist/react-bulma-components.min.css';
import { Heading } from 'react-bulma-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Icon } from 'react-bulma-components';
import { faAngleLeft, faTruck } from '@fortawesome/fontawesome-free-solid';
import { faBoxOpen } from '@fortawesome/fontawesome-free-solid';


function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const InfoDiv = styled.div`
  //padding: ${props => props.theme.spacing(8)}px;
  // margin-bottom: ${props => props.theme.spacing(4)}px;
`;

const RadioDiv = styled.div`
   width : 100%;
   border: 1px solid #e6e6e6;
   padding: 5px;
   padding-left: 15px;
`;

const HeadingInformation = styled(Heading)`
    margin-bottom: 15px;
    margin-top: 40px;
`;

const CheckControl = styled(Control)`
  margin-top: 15px;
  label {
    height: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-weight: 300;
  }
`;

const RadioWapper = styled(Radio)`
    height: auto;
    display: flex; 
    flex-direction: row; 
    align-items: center; 
    font-weight: 400; 
    font-size: 15px;
`;

const ButtonsRowContainer = styled(Container)`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-top: 20px;
  margin-bottom: 20px;
`;

const ReturnToCartButton = styled(Button)`
  border: none;
  display: flex;
  flex-direction: row;
  align-items: center;
  color: #999999;
  padding: 0px;
`;

const LoginButtonWrapper = styled.p`
  color: #999999;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

const LoginLinkButton = styled.span`
  color: blue;
  font-size: 14px;
  cursor: pointer;
`;


//{[{id:1,address1:"address1"},{id:0,address1:"address2"}]} address={{id:1, name:"Ali",line1:"Line1"}}
const schema = object().shape({
  alias: string().required("Alias is required and should be between 2 and 15 characters"),
  firstName: string().required("First name is required and should be between 2 and 15 characters"),
  lastName: string().required("Last name is required and should be between 2 and 15 characters"),
  line1: string().required("Address is required and should be between 2 and 15 characters"),
  mobile: string().matches(/(968[7,9]{1}[0-9]{7})/, "A valid mobile number is required"),

  //password: string().matches(/(?=.*[\w])(?=.*[\W])[\w\W]{6,}/),
  //password: string().matches(/([\w\W]{6,})/, intl.formatMessage(messages.password)),
});
export const InfoStep = (props) => {
  const { register, handleSubmit, watch, errors } = useForm();
  const [setInfoMutation, { loading, data2 }] = useMutation(SET_INFO);
  const { state, dispatch } = props;
  const [open, setOpen] = React.useState(false);
  const [error, setError] = React.useState("");
  const [ship, setShip] = useState({ value: "ship" });


  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

  const onSubmit = async (formData) => {

    if (loading) return;
    let address = {
      id: formData.Address,
      firstName: formData.firstName,
      lastName: formData.lastName,
      line1: formData.line1,
      line2: formData.line2,
      city: formData.city,
      country: "Oman",
      postalCode: formData.postalCode,
      mobile: formData.mobile,
      save: formData.save,
      alias: formData.alias
    };
    let email = formData.email;
    console.log(formData);
    if (address.id < 0) {
      console.log("validating schema");
      await schema.validate(address).then(async function () {
        const {
          data: { setInfo },
        } = await setInfoMutation({
          variables: { email, address, secureKey }
        });
        if (setInfo)
          dispatch({ type: 'NEXT' });
      }).catch(function (err) {
        setError(err.message);
        setOpen(true);
        return;
      })
    } else {
      const {
        data: { setInfo },
      } = await setInfoMutation({
        variables: { email, address, secureKey }
      });
      if (setInfo)
        dispatch({ type: 'NEXT' });
    }

    return false;
  }
  return (
    <React.Fragment>
      {loading && <p>Loading...</p>}
      <ButtonsRowContainer>
        <Heading subtitle size={6} style={{margin: "0px"}}>Contact Information</Heading>
        <LoginButtonWrapper>
          Already have an account?&nbsp;
          <LoginLinkButton>Log in</LoginLinkButton>
        </LoginButtonWrapper>
      </ButtonsRowContainer>
      <form onSubmit={handleSubmit(onSubmit)}>
        <InfoDiv>
          <InfoForm register={register} email={state.cart.email} />
        </InfoDiv>
        <Field>
          <CheckControl>
            <Checkbox name="save">
              &nbsp;&nbsp;&nbsp;Keep me up to date on news and exclusive offers
            </Checkbox>
          </CheckControl>
        </Field>
        <HeadingInformation subtitle size={6} >Delivery method</HeadingInformation>
        <RadioDiv style={{ borderBottom: "none", borderRadius: "5px 5px 0 0" }}>
          <RadioWapper name="ship" onChange={(e) => setShip({ value: "ship" })} checked={ship.value === "ship"} style={{ color: ship.value === "ship" ? "#6f8f9d" : "black" }}>
            <Icon style={{ marginLeft: "5px", marginRight: "5px" }}>
              <FontAwesomeIcon icon={faTruck} />
            </Icon>
            Ship
            </RadioWapper>
        </RadioDiv>
        <RadioDiv style={{ borderRadius: "0 0 5px 5px" }}>
          <RadioWapper name="ship" onChange={(e) => setShip({ value: "pick" })} checked={ship.value === "pick"} style={{ color: ship.value === "pick" ? "#6f8f9d" : "black" }}>
            <Icon style={{ marginLeft: "5px", marginRight: "5px" }}>
              <FontAwesomeIcon icon={faBoxOpen} />
            </Icon>
            Pick up
            </RadioWapper>
        </RadioDiv>
        <HeadingInformation subtitle size={6}>
          Shipping address
        </HeadingInformation>
        <AddressForm addresses={state.cart.addresses} address={state.cart.deliveryAddress} register={register} />
        <ButtonsRowContainer>
          <ReturnToCartButton>
            <Icon>
              <FontAwesomeIcon icon={faAngleLeft} />
            </Icon>
            &nbsp;Return to cart
          </ReturnToCartButton>
          <ButtonDiv>
            {(!state.cart.items || !state.cart.items.length) ?
              <NavButton type="submit" variant="contained" disabled color="primary">Next</NavButton> :

              <NavButton type="submit" variant="contained"
              >
                {(loading) ? <Loader /> : (
                  <span>Contiune to shipping</span>
                )}
              </NavButton>
            }
          </ButtonDiv>
        </ButtonsRowContainer>
      </form>
      <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error">
          {error}
        </Alert>
      </Snackbar>
    </React.Fragment>
  )
}
