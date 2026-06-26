import {
  MapContainer,
  TileLayer,
  Marker,
  useMapEvents,
  useMap
} from "react-leaflet";

import { useState, useEffect } from "react";

import "leaflet/dist/leaflet.css";

import L from "leaflet";

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png",
  iconUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png",
  shadowUrl:
    "https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png",
});

function ChangeView({ latitude, longitude }) {

  const map = useMap();

  useEffect(() => {

    if (latitude != null && longitude != null) {

      map.setView([latitude, longitude], 15);

    }

  }, [latitude, longitude, map]);

  return null;

}

function LocationMarker({

  setCoordinates,

  latitude,

  longitude

}) {

  const [position, setPosition] = useState(

    latitude != null && longitude != null

      ? {
          lat: latitude,
          lng: longitude
        }

      : null

  );

  useEffect(() => {

    if (latitude != null && longitude != null) {

      setPosition({

        lat: latitude,

        lng: longitude

      });

    }

  }, [latitude, longitude]);

  useMapEvents({

    click(e) {

      setPosition(e.latlng);

      setCoordinates({

        latitude: e.latlng.lat,

        longitude: e.latlng.lng

      });

    }

  });

  return position

    ? <Marker position={position} />

    : null;

}

function MapPicker({

  setCoordinates,

  latitude,

  longitude

}) {

  return (

    <MapContainer

      center={

        latitude != null && longitude != null

          ? [latitude, longitude]

          : [11.1271, 78.6569]

      }

      zoom={7}

      style={{

        height: "400px",

        width: "100%",

        marginTop: "10px"

      }}

    >

      <ChangeView

        latitude={latitude}

        longitude={longitude}

      />

      <TileLayer

        attribution="&copy; OpenStreetMap contributors"

        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"

      />

      <LocationMarker

        setCoordinates={setCoordinates}

        latitude={latitude}

        longitude={longitude}

      />

    </MapContainer>

  );

}

export default MapPicker;