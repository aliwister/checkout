import React, {useState} from "react";
import {Icon, Modal} from "react-bulma-components";
import {
  ComfirmContainer, ComfirmDiv,
  InputSearch, LocateButton,
  MapModalContainer,
  MapSearchContainer,
  MapViewContainer,
  ModalHeadContainer,
  ModalHeading, ModalLink,
  ModalSection
} from "./AddressContainer.style";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMapMarkerAlt} from "@fortawesome/fontawesome-free-solid";
import {GMapModal} from "../map-modal/mapModal";


const apiKey = "AIzaSyBY_OmJQPkU83oYc6t9SU74T3pHe9ejGpI";
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
  return address;
}

export const MapModal = (props) => {

  const { state, dispatch } = props;
  const [addressPosition, setAddressPosition] = useState({...state.initPosition});
  const [mapKeyword, setSearchKeyword] = useState("");

  const [mapAddress, setMapAddress] = useState(state.initPosition);

  const [clickedPosition, setClickedPosition] = useState({
    latClick: 0,
    lngClick: 0,
    markers: []
  });


  const positionClick = (e) => {
    const lat = e.latLng.lat();
    const lng = e.latLng.lng();
    let url = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=${apiKey}`

    axios.get(url).then(response => {
      setClickedPosition({
        googleReverseGeolocation: response.data.results[0].formatted_address,
        markers: [{ position: { lat: e.latLng.lat(), lng: e.latLng.lng() } }],
      });
      console.log(response.data);
      //setMapAddresses(response.data.results[0].formatted_address);
      //setAddressPosition(response.data.results[0].geometry.location);
      setPosition(response);

      //setAddressFromMap(getAddressObject(response.data.results[0].address_components));
    });
  }

  // Locate Button
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
      //setMapAddresses(response.data.results[0].formatted_address);
      setAddressPosition(response.data.results[0].geometry.location);
      setPosition(response);
    });
  }

  const handleMapSearch = (e) => {
    if (e.key === 'Enter') {
      let addressText = e.target.value;
      if (addressText.includes("+")) addressText = addressText.replace("+", "%2B");
      let url = `https://maps.googleapis.com/maps/api/geocode/json?address=${addressText}&key=${apiKey}`
      axios.get(url).then(response => {
        setAddressPosition(response.data.results[0].geometry.location);

        setClickedPosition({
          googleReverseGeolocation: response.data.results[0].formatted_address,
          markers: [{ position: response.data.results[0].geometry.location }],
        });

        //setMapAddresses(response.data.results[0].formatted_address);
        setPosition(response);
      });
    }
  };

  const setPosition = (response) => {
    const address_obj = getAddressObject(response.data.results[0].address_components);

    setSearchKeyword(response.data.results[0].formatted_address);
    setMapAddress(
        {
          ...response.data.results[0].geometry.location,
          address: response.data.results[0].formatted_address,
          plusCode: response.data.plus_code.global_code,
          city: address_obj.city,
          line1: address_obj.street
        }
    );
  }

  const handleConfirm = () => {
    console.log("mapAddress", mapAddress);
    if(!clickedPosition.markers.length)
      return false;
    dispatch({type:'MAP_ADDRESS_END', payload: mapAddress});
  }


  return (
      <Modal
          onClose={() => dispatch({type:'MAP_ADDRESS_END', payload: mapAddress})}
          show={state.showMap}
      >
        <MapModalContainer>
          <ModalSection>
            <ModalHeadContainer>
              <ModalHeading>
                Add a New address
              </ModalHeading>
              {/*<ModalLink href="#" onClick={() => { setMapModal(false); setEdit(1) }} defaultChecked={edit === -1}>Enter address manually instead</ModalLink>*/}
            </ModalHeadContainer>
            <MapViewContainer>
              <MapSearchContainer>
                <InputSearch value={mapKeyword} onChange={(e) => setSearchKeyword(e.target.value)} onKeyDown={handleMapSearch} placeholder="Search for your location" />
              </MapSearchContainer>
              <LocateButton onClick={() => getGeolocation()}>
                <Icon>
                  <FontAwesomeIcon icon={faMapMarkerAlt} />
                </Icon>
                &nbsp;&nbsp;&nbsp;Locate Me
              </LocateButton>
              <GMapModal clickedPosition={clickedPosition} searchedPosition={addressPosition} positionClick={(e) => positionClick(e)} />
            </MapViewContainer>
            <ComfirmContainer>
              <ComfirmDiv onClick={handleConfirm} >Confirm Location</ComfirmDiv>
            </ComfirmContainer>
          </ModalSection>
        </MapModalContainer>
      </Modal>
  )
}