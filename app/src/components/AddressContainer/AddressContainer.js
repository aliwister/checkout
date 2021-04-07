import React, { useState } from 'react';

import { Form, Columns, Container, Dropdown} from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Icon } from 'react-bulma-components';
import { faMapMarkerAlt } from '@fortawesome/fontawesome-free-solid';
import {
  AddAddressRadio,
  AddressDiv, AddressDropDown,
  AddressMarkerDiv,
  CheckControl,
  EditDiv,
  EditMapDiv,
  LeftContainer,
  PositionSection,
  SelectBox
} from "./AddressContainer.style";
import {FormattedPhone} from "./FormattedPhone";

import {AddressForm} from "./AddressForm";
import {MapModal} from "./MapModal";
import {Controller} from "react-hook-form";
import {SmallAddressForm} from "./SmallAddressForm";
import {TYPES} from "../InfoStep/InfoStep";

const { Checkbox, Field, Control } = Form;


export const AddressContainer = (props) => {
  const { register, control, state, dispatch } = props;

  //const [edit, setEdit] = useState(!props.address || (!address.id && address.plusCode)?-1:(!address.id && address.firstName)?1:0);
  //const [edit, setEdit] = useState((props.address && props.address.id)?0:props.address && props.address.map && props.address.map.plusCode?-1:-2);


  //const [mobile, setMobile] = useState(edit ? address.mobile : "");
  //const [country, setCountry] = useState("Oman");
  //const [save, setSave] = useState(edit ? address.save : false);
  //const [addressSelect, setAddressSelect] = useState(addresses[0]);

  const selectNewAddress = (e) => {
    dispatch({type: 'SELECT_NEW_ADDRESS', payload: e})
  }
  const selectSavedAddress = (e) => {
    dispatch({type: 'SELECT_SAVED_ADDRESS', payload: e})
  }
  const selectSavedAddressEmpty = () => {
    dispatch({type: 'SELECT_SAVED_ADDRESS'})
  }


  return (
    <>
      <Container>
        <MapModal show={state.showMap} dispatch={dispatch} state={state}/>
        <AddAddressRadio name="add-address" onClick={selectSavedAddressEmpty} checked={state.addressType === TYPES.SELECT} disabled={state.addresses < 1}>
          &nbsp;Use a Saved Address
        </AddAddressRadio>
        <AddressDropDown
          value={state.selectedAddress} onChange={selectSavedAddress}
        >
          {state.addresses && state.addresses.map(x => (
            <Dropdown.Item value={x.id} key={x.id} >
              {x.firstName} {x.lastName} - {x.line1} {x.line2} {x.city} {x.phone}
            </Dropdown.Item>
          ))
          }
        </AddressDropDown>
      </Container>

      <Container>
        <AddAddressRadio name="add-address" onChange={selectNewAddress} checked={state.addressType === TYPES.EDIT} >
          &nbsp;Add a New Address
        </AddAddressRadio>
        {state.addressType === TYPES.EDIT &&
          (
            <div>
              <PositionSection>
                <LeftContainer>
                  <AddressMarkerDiv>
                    <Icon>
                      <FontAwesomeIcon icon={faMapMarkerAlt} />
                    </Icon>Set from map
                  </AddressMarkerDiv>
                  <AddressDiv>
                    {state.addressFromMap?state.addressFromMap.plusCode:""}
                  </AddressDiv>
                </LeftContainer>
                <EditDiv>
                  <img src={require(`../../assets/map-icon.svg`)} />
                  <EditMapDiv onClick={() => dispatch({type:'MAP_ADDRESS_START'})}>Edit</EditMapDiv>
                </EditDiv>
              </PositionSection>
              <SmallAddressForm register={register} control={control}/>
            </div>
          )}
{/*        <AddAddressRadio name="add-address" onClick={() => setEdit(1)} checked={edit === 1} >
          &nbsp;Enter address manually:
        </AddAddressRadio>*/}
       {/* {edit === 1 && <AddressForm register={register} control={control}/>}*/}

        {(state.addressType === TYPES.EDIT)  &&
       <Control>
         <Controller as={<SelectBox><option value="Oman">Oman</option>
         </SelectBox>
         }
           id="country"
           name="country"
           //onChange={(e) => setCountry(e.target.value)}
           register={register}
           control={control}
           />
         <FormattedPhone mobile={state.mobile} setMobile={(mob) => dispatch({type:'SET_MOBILE', payload:mob})} />
         <Field>

           <CheckControl>

             <input type="checkbox" value={true} name="save" id="save" ref={register("save")} />
               &nbsp;&nbsp;&nbsp;Save this information for next time

           </CheckControl>
         </Field>
       </Control> }
      </Container>
    </>
  )
}
