import React, { useState } from 'react';
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css';
import startsWith from 'lodash.startswith';
import { Form, Columns, Container, Card, Dropdown, Button, Box, Modal, Section, Heading, Navbar } from 'react-bulma-components';
import 'react-bulma-components/dist/react-bulma-components.min.css';
import styled from 'styled-components';
import { MapModal } from "./map-modal/mapModal";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Icon } from 'react-bulma-components';
import { faMapMarkerAlt } from '@fortawesome/fontawesome-free-solid';

const { Checkbox, Field, Input, Control, Radio, Select } = Form;

const CheckControl = styled(Control)`
  label {
    height: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-weight: 300;
  }
`;

const InputColumns = styled(Columns.Column)`
  position: relative;
  padding-top: 0px !important;
  padding-bottom: 0px !important;
`;

const AddressDropDown = styled(Dropdown)`
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

const ModalSection = styled(Section)`
    background-color: white;
    width: 100%;
    height: 100%;
    padding: 0px !important;
    display: flex;
    flex-direction: column;
`;

const MapModalContainer = styled(Modal.Content)`
  width: 1000px;
  height: 800px;
`;

const ModalHeadContainer = styled(Container)`
  width: 100%;
  height: 10px;
  background-color: white;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: flex-start;
`;

const ModalHeading = styled(Heading)`
  margin: 0px !important;
  font-size: 20px;
  padding-left: 20px;
  padding-right: 50px;
`;

const ModalLink = styled.a`
   font-size: 15px;
  :hover {
    text-decoration: underline;
  }
`;

const MapSearchContainer = styled(Container)`
  width: 60%;
  padding: 0px 20px;
  position: absolute;
  margin: 0 20%;
  top: 10px;
  z-index: 10;
`;

const InputSearch = styled(Input)`
   width: 100%;
   height: 50px;
   box-shadow: 2px 2px 8px #999999;
   border: none;
   font-size: 17px;
`;

const LocateButton = styled(Button)` 
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

const ComfirmContainer = styled(Container)`
   width: 100%;
   background-color: white;
   display: flex;
   align-items: center;
   flex-direction: row;
   flex: 1;
   justify-content: flex-end;
`;

const ComfirmDiv = styled.div`
    margin-right: 20px;
    border: 1px solid;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
`;

const MapViewContainer = styled(Container)`
  width: 100%;
  flex: 10;
`;

const PositionSection = styled(Section)`
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

const LeftContainer = styled(Container)`
  flex: 1;
`;

const AddressDiv = styled.div`
  font-size: 20px;
  font-weight: 400;
  padding-left: 5px;
  padding-top: 5px;
`;

const AddressMarkerDiv = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: 15px;
  font-weight: 500;
`;

const EditDiv = styled.div`
  width: 80px;
  height: auto;
  position: relative;
`;

const EditMapDiv = styled.div`
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

export const AddressForm = (props) => {
  //const { register, handleSubmit, watch, errors } = useForm();
  //const onSubmit = data => { console.log(data); }

  const { addresses, register, setAddressFromMap } = props;
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
  const [state, setState] = useState(edit ? address.state : "");
  const [country, setCountry] = useState("Oman");
  const [postalCode, setPostalcode] = useState(edit ? address.postalCode : "");
  const [save, setSave] = useState(edit ? address.save : false);
  const [addressSelect, setAddressSelect] = useState(addresses[0]);
  const [mapAddresses, setMapAddresses] = useState("");
  const [mapModal, setMapModal] = useState(false);
  const [addressPosition, setAddressPosition] = useState();

  const [mapSearch, setMapSearch] = useState("");
  const [clickedPosition, setClickedPosition] = useState({
    latClick: 0,
    lngClick: 0,
    markers: []
  });
  //const [,] = useState(!props.address.id && props.address.);
  // console.log(edit);

  // useEffect(() => {
  //   setClickedPosition(mapSearch)
  // }, [mapSearch])

  const setAddress = (selected) => {
    //console.log(id);
    setAddressSelect(selected);
    setEdit(selected.id);
  }

  const positionClick = (e) => {
    const lat = e.latLng.lat();
    const lng = e.latLng.lng();
    console.log("position", lat, lng)
    let url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`
    axios.get(url).then(response => {
      console.log("address===", response.data.results[0]);
      setClickedPosition({
        googleReverseGeolocation: response.data.results[0].formatted_address,
        markers: [{ position: { lat: e.latLng.lat(), lng: e.latLng.lng() } }],
      });
      setMapAddresses(response.data.results[0].formatted_address);
      setAddressPosition(response.data.results[0].geometry.location);
      setMapSearch(response.data.results[0].formatted_address);
      setAddressFromMap(getAddressObject(response.data.results[0].address_components));
    });
  }

  const getAddressObject = (address_components) => {
    let ShouldBeComponent = {
      home: ["street_number"],
      postal_code: ["postal_code"],
      street: ["street_address", "route"],
      region: [
        "administrative_area_level_1",
        "administrative_area_level_2",
        "administrative_area_level_3",
        "administrative_area_level_4",
        "administrative_area_level_5"
      ],
      city: [
        "locality",
        "sublocality",
        "sublocality_level_1",
        "sublocality_level_2",
        "sublocality_level_3",
        "sublocality_level_4"
      ],
      country: ["country"]
    };

    let address = {
      home: "",
      postal_code: "",
      street: "",
      region: "",
      city: "",
      country: ""
    };
    address_components.forEach(component => {
      for (var shouldBe in ShouldBeComponent) {
        if (ShouldBeComponent[shouldBe].indexOf(component.types[0]) !== -1) {
          if (shouldBe === "country") {
            address[shouldBe] = component.short_name;
          } else {
            address[shouldBe] = component.long_name;
          }
        }
      }
    });
    console.log("address object", address);
    return address;
  }

  const handleMapSearch = (e) => {
    if (e.key === 'Enter') {
      let url = `https://maps.googleapis.com/maps/api/geocode/json?address=${e.target.value}&key=${apiKey}`
      axios.get(url).then(response => {
        setAddressPosition(response.data.results[0].geometry.location);

        setClickedPosition({
          googleReverseGeolocation: response.data.results[0].formatted_address,
          markers: [{ position: response.data.results[0].geometry.location }],
        });
      });
    }
  };

  const getGeolocation = () => {
    const url = `https://www.googleapis.com/geolocation/v1/geolocate?key=${apiKey}`;

    axios.post(url, {}).then(response => {
      getLocation(response.data.location);
    })
  };

  const getLocation = (location) => {
    const lat = location.lat;
    const lng = location.lng;
    let url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`
    axios.get(url).then(response => {
      setClickedPosition({
        googleReverseGeolocation: response.data.results[0].formatted_address,
        markers: [{ position: { lat: lat, lng: lng } }],
      });
      setMapAddresses(response.data.results[0].formatted_address);
      setAddressPosition(response.data.results[0].geometry.location);
      setMapSearch(response.data.results[0].formatted_address);
    });
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
      <Modal
        onClose={() => setMapModal(false)}
        show={mapModal}
      >
        <MapModalContainer>
          <ModalSection>
            <ModalHeadContainer>
              <ModalHeading>
                Add a New address
              </ModalHeading>
              <ModalLink href="#" onClick={() => { setMapModal(false); setEdit(1) }} checked={edit === 1}>Enter address manually instead</ModalLink>
            </ModalHeadContainer>
            <MapViewContainer>
              <MapSearchContainer>
                <InputSearch value={mapSearch} onChange={(e) => setMapSearch(e.target.value)} onKeyDown={handleMapSearch} placeholder="Search for your location" />
              </MapSearchContainer>
              <LocateButton onClick={() => getGeolocation()}>
                <Icon>
                  <FontAwesomeIcon icon={faMapMarkerAlt} />
                </Icon>
              &nbsp;&nbsp;&nbsp;Locate Me
            </LocateButton>
              <MapModal clickedPosition={clickedPosition} searchedPosition={addressPosition} positionClick={(e) => positionClick(e)} />
            </MapViewContainer>
            <ComfirmContainer>
              <ComfirmDiv onClick={() => { setMapModal(false); setEdit(-1) }} >COMFIRM LOCATION</ComfirmDiv>
            </ComfirmContainer>
          </ModalSection>
        </MapModalContainer>
      </Modal>
      <Container>
        <AddAddressRadio name="add-address" onChange={() => setMapModal(true)} checked={edit === -1 || edit === 1} >
          &nbsp;Add a new address:
        </AddAddressRadio>
        {edit == -1 &&
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
                    {mapAddresses}
                  </AddressDiv>
                </LeftContainer>
                <EditDiv>
                  <img src={require(`../assets/map-icon.svg`)} />
                  <EditMapDiv onClick={() => setMapModal(true)}>Edit</EditMapDiv>
                </EditDiv>
              </PositionSection>
                  <InfoInput
                    required
                    id="alias"
                    name="alias"
                    placeholder="Address Name, e.g. Home or Work"
                    domRef={register({ required: true, maxLength: 50, minLength: 2 })}
                    value={alias}
                    onChange={(e) => setAlias(e.target.value)}
                  />
              <Columns style={{ marginTop: '0px', marginBottom: '0px' }}>
                <InputColumns>
                  <InfoInput
                    required
                    id="firstName"
                    name="firstName"
                    placeholder="First name"
                    autoComplete="fname"
                    domRef={register({ required: true, maxLength: 15, minLength: 2 })}
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
                    domRef={register({ required: true, maxLength: 15, minLength: 2 })}
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                  />
                </InputColumns>
              </Columns>
              <InfoInput
                insideLabel
                required
                id="line2"
                name="line2"
                placeholder="Address line 2"
                autoComplete="billing address-line2"
                domRef={register({ required: true, maxLength: 50, minLength: 2 })}
                value={line2}
                onChange={(e) => setLine2(e.target.value)}
              />
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
                style={{ margin: '10px 0px' }}
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
            </div>
          )}
        {edit === 1 &&
          (
            <Control>
              <InfoInput
                // insideLabel
                required
                id="alias"
                name="alias"
                placeholder="Address Name, e.g. Home or Work"
                domRef={register({ required: true, maxLength: 50, minLength: 2 })}
                value={alias}
                onChange={(e) => setAlias(e.target.value)}
              />
              <Columns style={{ marginTop: '0px', marginBottom: '0px' }}>
                <InputColumns>
                  <InfoInput
                    required
                    id="firstName"
                    name="firstName"
                    placeholder="First name"
                    autoComplete="fname"
                    domRef={register({ required: true, maxLength: 15, minLength: 2 })}
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
                    domRef={register({ required: true, maxLength: 15, minLength: 2 })}
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
                domRef={register({ required: true, maxLength: 50, minLength: 2 })}
                value={line1}
                onChange={(e) => setLine1(e.target.value)}
              />
              <InfoInput
                insideLabel
                id="line2"
                name="line2"
                placeholder="Address line 2 "
                autoComplete="billing address-line2"
                domRef={register({ maxLength: 50, minLength: 2 })}
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
                domRef={register({ required: true, maxLength: 15, minLength: 2 })}
                value={city}
                onChange={(e) => setCity(e.target.value)}
              />
              <InfoInput
                required
                id="postalCode"
                name="postalCode"
                placeholder="Zip / Postal code"
                autoComplete="billing postal-code"
                domRef={register}
                onChange={(e) => setPostalcode(e.target.value)}
                value={postalCode}
              />
              <InfoInput
                required
                id="state"
                name="state"
                placeholder="State/Province/Region"
                autoComplete="billing postal-code"
                domRef={register}
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
                style={{ margin: '10px 0px' }}
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
            </Control>
          )}
      </Container>
    </>
  )
}
