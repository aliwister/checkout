import React from 'react';
import 'react-phone-input-2/lib/style.css';

import Heading from 'react-bulma-components/lib/components/heading';
import Container from 'react-bulma-components/lib/components/container';
import Columns from 'react-bulma-components/lib/components/columns';
import Dropdown from 'react-bulma-components/lib/components/dropdown';
import Modal from 'react-bulma-components/lib/components/modal';
import Section from 'react-bulma-components/lib/components/section';

import styled from 'styled-components';
import Control from 'react-bulma-components/lib/components/form/components/control';
import Radio from 'react-bulma-components/lib/components/form/components/radio';
import Select from 'react-bulma-components/lib/components/form/components/select';
import Input from 'react-bulma-components/lib/components/form/components/input';
import Label from 'react-bulma-components/lib/components/form/components/label';

export const InlineLabel = styled(Label)`
  display: inline-flex;
`
export const InlineRadio = styled(Radio)`
  display: inline-flex;
  margin-left: 5px;
  margin-right: 5px;
  flex-direction: row;
  align-items: center;
  
`
export const Alias = styled(Input)`
  font-size: 15px;
  margin: -6px 0px;
  display: inline-flex;
  max-width: 155px;
`;

export const CheckControl = styled(Control)`
  label {
    height: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-weight: 300;
  }
`;

export const InputColumns = styled(Columns.Column)`
  position: relative;
  padding-top: 0px !important;
  padding-bottom: 0px !important;
`;

export const AddressDropDown = styled(Dropdown)`
  width: 100%;
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

export const AddAddressRadio = styled(Radio)`
  width: 100%;
  margin: 20px 0px 15px 0px !important;
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

export const InfoInput = styled(Input)`
  font-size: 15px;
  margin: 10px 0px;
  
`;


export const ModalSection = styled(Section)`
    background-color: white;
    width: 100%;
    height: 100%;
    padding: 0px !important;
    display: flex;
    flex-direction: column;
`;

export const MapModalContainer = styled(Modal.Content)`
  max-width: 1000px;
  width: 90%;
  height: 800px;
`;

export const ModalHeadContainer = styled(Container)`
  width: 100%;
  height: 10px;
  background-color: white;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: flex-start;
`;

export const ModalHeading = styled(Heading)`
  margin: 0px !important;
  font-size: 20px;
  padding-left: 20px;
  padding-right: 50px;
`;

export const ModalLink = styled.a`
   font-size: 15px;
  :hover {
    text-decoration: underline;
  }
`;

export const MapSearchContainer = styled(Container)`
  width: 60%;
  padding: 0px 20px;
  position: absolute;
  margin: 0 20%;
  top: 10px;
  z-index: 10;
`;

export const InputSearch = styled(Input)`
   width: 100%;
   height: 50px;
   box-shadow: 2px 2px 8px #999999;
   border: none;
   font-size: 17px;
`;

export const LocateButton = styled.button` 
   position: absolute;
   top: 50px;
   right: 10px;
   z-index: 10;
   border-radius: 27px;
   padding: 0 20px;
   font-size: 20px;
   font-weight: 700;
   box-shadow: 1px 3px 12px #999999;
   border: 2px solid #151313;;
`;

export const ComfirmContainer = styled(Container)`
   width: 100%;
   background-color: white;
   display: flex;
   align-items: center;
   flex-direction: row;
   flex: 1;
   justify-content: flex-end;
`;

export const ComfirmDiv = styled.div`
    margin-right: 20px;
    border: 1px solid;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
`;

export const MapViewContainer = styled(Container)`
  width: 100%;
  flex: 10;
`;

export const PositionSection = styled(Section)`
  width: 100%;
  border: 1px solid #a6a6a6;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
`;

export const LeftContainer = styled(Container)`
  flex: 1;
`;

export const AddressDiv = styled.div`
  font-size: 20px;
  font-weight: 400;
  padding-left: 5px;
  padding-top: 5px;
`;

export const AddressMarkerDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: 15px;
  font-weight: 500;
`;

export const EditDiv = styled.div`
  width: 80px;
  height: auto;
  position: relative;
`;

export const EditMapDiv = styled.div`
  position: absolute;
  width: 100%;
  font-size: 13px;
  font-weight: 700;
  opacity: 0.5;
  background-color: #363636;
  bottom: 0;
  text-align: center;
  padding: 5px;
  border-radius: 5px;
  color: white;
  cursor: pointer;
  :hover {
    color: white;
  }
`;


export const apiKey = "AIzaSyBY_OmJQPkU83oYc6t9SU74T3pHe9ejGpI";
export const SelectBox = styled(Select)`
  width: 100%;
  height: auto !important;
  margin: 10px 0px;
  select {
    width: 100%;
    height: 2.9em;
    font-size: 15px;
  }
`;

