import React, { useEffect, useState } from "react";
import { compose, withProps } from "recompose";
import {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  Marker,
  GroundOverlay
} from "react-google-maps";

const GMapModal = compose(
  withProps({
    googleMapURL:
      `https://maps.googleapis.com/maps/api/js?key=AIzaSyBY_OmJQPkU83oYc6t9SU74T3pHe9ejGpI&v=3.exp&libraries=geometry,visualization,drawing,places`,
    loadingElement: <div style={{ height: `100%` }} />,
    containerElement: <div style={{ height: `100%` }} />,
    mapElement: <div style={{ height: `100%` }} />
  }),
  withScriptjs,
  withGoogleMap
)(props => {
  const [mapPosition, setMapPosition] = useState({ lat: 23.5886235, lng: 58.3884731 });
  console.log("props.searchedPosition",props.searchedPosition)
  useEffect(() => {
    if(props.searchedPosition !== undefined && props.searchedPosition) {
      setMapPosition(props.searchedPosition)
    }
  }, [props.searchedPosition]);
  return (
    <GoogleMap
      defaultZoom={14}
      center={mapPosition}
      onClick={props.positionClick}
    >
      {!!props.clickedPosition.markers && props.clickedPosition.markers.map((marker, index) => {
        return <Marker key={index} title={marker.googleReverseGeolocation} position={{ lat: marker.position.lat, lng: marker.position.lng }} />
      })
      }

    </GoogleMap>
  )

});
export default GMapModal;
