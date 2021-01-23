import React, { useEffect, useState } from "react";
import { compose, withProps } from "recompose";
import {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  Marker,
  GroundOverlay
} from "react-google-maps";

export const MapModal = compose(
  withProps({
    googleMapURL:
      "https://maps.googleapis.com/maps/api/js?key=AIzaSyDVHEfgaxWeseBO21SI3r3gkJzfk9JzvIc&v=3.exp&libraries=geometry,visualization,drawing,places",
    loadingElement: <div style={{ height: `100%` }} />,
    containerElement: <div style={{ height: `100%` }} />,
    mapElement: <div style={{ height: `100%` }} />
  }),
  withScriptjs,
  withGoogleMap
)(props => {
  const [mapPosition, setMapPosition] = useState({ lat: 21.4735, lng: 55.9754 });

  useEffect(() => {
    if(props.searchedPosition !== undefined) {
      setMapPosition(props.searchedPosition)
    }
  }, [props.searchedPosition]);
  return (
    <GoogleMap
      defaultZoom={8}
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

