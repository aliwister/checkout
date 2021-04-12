import React, {useEffect, useReducer, useState} from 'react';
import styled from 'styled-components';
import { useForm } from "react-hook-form";

import { useMutation } from '@apollo/react-hooks';
import { SET_INFO } from '../../graph/setInfo';

import { InfoForm } from './InfoForm';
import { ButtonDiv, NavButton } from "../../App.styles";
import Loader from "../Loader/Loader";
import { string, object } from 'yup';
import Snackbar from "@material-ui/core/Snackbar";
import MuiAlert from '@material-ui/lab/Alert';

import Heading from 'react-bulma-components/lib/components/heading';
import Icon  from 'react-bulma-components/lib/components/icon';
import ArrowBackSharpIcon from '@material-ui/icons/ArrowBackSharp';
import LocalShippingIcon from '@material-ui/icons/LocalShipping';
import {AddressContainer} from "../AddressContainer/AddressContainer";
import {
  ButtonsRowContainer,
  HeadingInformation,
  InfoDiv,
  RadioDiv,
  RadioWapper,
  ReturnToCartButton
} from "./InfoStep.style";


function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

//{[{id:1,address1:"address1"},{id:0,address1:"address2"}]} address={{id:1, name:"Ali",line1:"Line1"}}
const schema = object().shape({
  alias: string().required("Alias is required and should be between 2 and 15 characters"),
  firstName: string().required("First name is required and should be between 2 and 15 characters"),
  lastName: string().required("Last name is required and should be between 2 and 15 characters"),
  line1: string().required("Address is required and should be between 2 and 15 characters"),
  mobile: string().required("A valid mobile number is required").matches(/(968[7,9]{1}[0-9]{7})/, "A valid mobile number is required"),

  //password: string().matches(/(?=.*[\w])(?=.*[\W])[\w\W]{6,}/),
  //password: string().matches(/([\w\W]{6,})/, intl.formatMessage(messages.password)),
});

export const TYPES = {
  SELECT:"SELECT",
  ADD: "ADD",
  EDIT: "EDIT"
}

function reducer(state, action) {
  console.log(action)
  switch (action.type) {
    case 'SELECT_NEW_ADDRESS':
      return {
        ...state,
        showMap: true
      }
    case 'SELECT_SAVED_ADDRESS':
      return {
        ...state,
        addressType: TYPES.SELECT,
        selectedAddress: action.payload
      }
    case 'MAP_ADDRESS_START':
      return {
        ...state,
        showMap: true
      }
    case 'MAP_ADDRESS_END':
      if(action.payload)
      return {
        ...state,
        showMap: false,
        addressType: TYPES.EDIT,
        addressFromMap: {
          ...action.payload
        }
      }
      return {
          ...state,
          showMap: false,
      }
    case 'SHOW_ERROR':
      return {
        ...state,
        error: true,
        errMsg: action.payload
      }
    case 'HIDE_ERROR':
      return {
        ...state,
        error: false,
        errMsg: action.payload
      }
    case 'SET_MOBILE':
      return {
        ...state,
        mobile: action.payload
      }
    case 'SELECT_ALIAS':
      return {
        ...state,
        alias: action.payload
      }
    case 'INIT_ADDRESS':
      const payload = action.payload;
      return {
        ...state,
        mobile: payload.mobile?payload.mobile:"",
        initPosition: payload.lng && payload.lat?{lng: payload.lng, lat:payload.lat}:false,
        addressType: state.selectedAddress?TYPES.SELECT:payload && payload.plusCode?TYPES.EDIT:TYPES.ADD,
        alias: payload && payload.alias != "Home" && payload.alias != "Work" ? "Other": payload ? payload.alias:false,
        addressFromMap: payload.plusCode?{
          lng: payload.lng?payload.lng:null,
          lat: payload.lat?payload.lat:null,
          plusCode: payload.plusCode?payload.plusCode:null,
          city: payload.city?payload.city:null,
          address: payload.line1?payload.line1:null,
        }: false
      }
    default:
      throw new Error('Unknown step');
  }

}



export const InfoStep = (props) => {
  const dAddress = props.state.cart.deliveryAddress;
  const initialState = {
    deliveryAddress: dAddress,
    addresses: props.state.cart.addresses,
    email: props.state.cart.email,
    showMap: false,
    initPosition: false,
    selectedAddress: props.state.cart.deliveryAddressId,
    addressType: props.state.cart.deliveryAddressId?TYPES.SELECT:TYPES.ADD,
    error: false
  }
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    if (dAddress)
      dispatch({type: 'INIT_ADDRESS', payload: dAddress})
  }, [])


  const { register, handleSubmit, watch, errors, control } = useForm({
      defaultValues: {
        ...dAddress
      }
  });

  const [setInfoMutation, { loading, data2 }] = useMutation(SET_INFO);
  const [ship, setShip] = useState({ value: "ship" });

  //console.log("addressFromMap", addressFromMap);
  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    dispatch({type:'HIDE_ERROR'});
  };

  const onSubmit = async (formData) => {
    console.log("InfoStep: Submit");
/*    console.log("formData:",formData);
    console.log("state:",state);*/
    if (loading) return;
    let address = {
      id: null,
      firstName: formData.firstName,
      lastName: formData.lastName,
      line1: state.addressFromMap ? state.addressFromMap.address : formData.line1,
      line2: formData.line2,
      city: state.addressFromMap ? state.addressFromMap.city : formData.city,
      country: "Oman",
      postalCode: formData.postalCode,
      mobile: state.mobile,
      save: formData.save,
      alias: state.alias === "Other"?formData.alias: state.alias,
      lat: state.addressFromMap ? state.addressFromMap.lat:null,
      lng: state.addressFromMap ? state.addressFromMap.lng:null,
      plusCode: state.addressFromMap ? state.addressFromMap.plusCode:null
    };
    const email = state.email;
    const secureKey = props.state.cart.secureKey;
    if(state.addressType === TYPES.SELECT) {
      let selectedAddressId = state.selectedAddress
      if(!state.selectedAddress)
        selectedAddressId = state.addresses[0].id;

      const {
        data: { setInfo },
      } = await setInfoMutation({
        variables: { email, address:{id: selectedAddressId}, secureKey }
      });
      console.log(setInfo);
      if (setInfo)
        props.dispatch({ type: 'SAVE_INFO', payload: setInfo });
      return true;
    }


    console.log("validating schema");
    await schema.validate(address).then(async function () {
        const {
          data: { setInfo },
        } = await setInfoMutation({
          variables: { email, address, secureKey }
        });
        if (setInfo)
          props.dispatch({ type: 'SAVE_INFO', payload: setInfo });
      }).catch(function (err) {
        dispatch({type:'SHOW_ERROR', payload:err.message});
        setOpen(true);
        return true;
      })

    return false;
  }

  return (
    <React.Fragment>
      {loading && <p>Loading...</p>}
      <ButtonsRowContainer>
        <Heading subtitle size={6} style={{ margin: "0px" }}>Contact Information</Heading>
{/*        <LoginButtonWrapper>
          Already have an account?&nbsp;
          <LoginLinkButton>Log in</LoginLinkButton>
        </LoginButtonWrapper>*/}
      </ButtonsRowContainer>
      <form onSubmit={handleSubmit(onSubmit)}>
        <InfoDiv>
          <InfoForm state={state} dispatch={dispatch} />
        </InfoDiv>

{/*          <Field> <CheckControl>
            <Checkbox name="save">
              &nbsp;&nbsp;&nbsp;Keep me up to date on news and exclusive offers
            </Checkbox>
          </CheckControl></Field>*/}

        <HeadingInformation subtitle size={6} >Delivery method</HeadingInformation>
       {/* <RadioDiv style={{ borderBottom: "none", borderRadius: "5px 5px 0 0" }}>*/}
          <RadioDiv style={{borderRadius: "5px 5px 5px 5px " }}>
          <RadioWapper
            name="ship"
            onChange={(e) => setShip({ value: "ship" })}
            checked={ship.value === "ship"}
            style={{ color: ship.value === "ship" ? "#6f8f9d" : "black" }}
          >
            <Icon style={{ marginLeft: "5px", marginRight: "5px" }}>
              <LocalShippingIcon/>
            </Icon>
            Ship
            </RadioWapper>
        </RadioDiv>
{/*        <RadioDiv style={{ borderRadius: "0 0 5px 5px" }}>
          <RadioWapper
            name="ship"
            onChange={(e) => setShip({ value: "pick" })}
            checked={ship.value === "pick"}
            style={{ color: ship.value === "pick" ? "#6f8f9d" : "black" }}
          >
            <Icon style={{ marginLeft: "5px", marginRight: "5px" }}>
              <FontAwesomeIcon icon={faBoxOpen} />
            </Icon>
            Pick up
            </RadioWapper>
        </RadioDiv>*/}
        <HeadingInformation subtitle size={6}>
          Shipping address
        </HeadingInformation>
        <AddressContainer state={state} dispatch={dispatch} register={register} errors={errors} control={control}/>
        <ButtonsRowContainer>
          <ReturnToCartButton onClick={() => window.history.back()}>
            <Icon>
<ArrowBackSharpIcon/>
            </Icon>
            &nbsp;&nbsp;&nbsp;Return to cart
          </ReturnToCartButton>
          <ButtonDiv>
            {(!props.state.cart.items || !props.state.cart.items.length) ?
              <NavButton type="submit" variant="contained" disabled color="primary">Next</NavButton> :

              <NavButton type="submit" variant="contained"
              >
                {(loading) ? <Loader /> : (
                  <span>Continue to shipping</span>
                )}
              </NavButton>
            }
          </ButtonDiv>
        </ButtonsRowContainer>
      </form>
      <Snackbar open={state.error} autoHideDuration={4000} onClose={handleClose}>
        <Alert onClose={handleClose} severity="error">
          {state.errMsg}
        </Alert>
      </Snackbar>
    </React.Fragment>
  )
}
