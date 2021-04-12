import React, { useState } from 'react';

import Icon  from 'react-bulma-components/lib/components/icon';
import Form  from 'react-bulma-components/lib/components/form';
import Container  from 'react-bulma-components/lib/components/container';
import Dropdown  from 'react-bulma-components/lib/components/dropdown';
import LocationOnIcon from '@material-ui/icons/LocationOn';

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

import img from '../../assets/map-icon.png';


export const AddressContainer = (props) => {
  const { register, control, errors, state, dispatch } = props;

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
              ({x.alias}) {x.firstName}, {x.city} - {x.mobile}
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
                      <LocationOnIcon/>
                    </Icon>Set from map
                  </AddressMarkerDiv>
                  <AddressDiv>
                    {state.addressFromMap?`${state.addressFromMap.address} ${state.addressFromMap.plusCode}` :""}
                  </AddressDiv>
                </LeftContainer>
                <EditDiv>
                  <img src={img} />
                  <EditMapDiv onClick={() => dispatch({type:'MAP_ADDRESS_START'})}>Edit</EditMapDiv>
                </EditDiv>
              </PositionSection>
              <SmallAddressForm register={register} control={control} errors={errors} state={state} dispatch={dispatch}/>
            </div>
          )}
{/*        <AddAddressRadio name="add-address" onClick={() => setEdit(1)} checked={edit === 1} >
          &nbsp;Enter address manually:
        </AddAddressRadio>*/}
       {/* {edit === 1 && <AddressForm register={register} control={control}/>}*/}

        {(state.addressType === TYPES.EDIT)  &&
       <>
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
             <input type="checkbox" value={true} name="save" id="save" ref={register} />
               &nbsp;&nbsp;&nbsp;Save this information for next time
       </> }
      </Container>
    </>
  )
}
