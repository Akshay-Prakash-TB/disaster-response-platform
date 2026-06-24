import { useEffect, useState } from "react";
import axios from "axios";
import { useSearchParams } from "react-router-dom";

import {
  MapContainer,
  TileLayer,
  Marker,
  Popup
} from "react-leaflet";

import "leaflet/dist/leaflet.css";
import L from "leaflet";

const vehicleIcon = L.icon({
  iconUrl:
    "https://cdn-icons-png.flaticon.com/512/684/684908.png",
  iconSize: [40, 40]
});

function CitizenTrackingMap() {

  const [tracking, setTracking] =
    useState(null);

  const [searchParams] =
    useSearchParams();

  const resourceId =
    searchParams.get(
      "resourceId"
    );

  const fetchTracking =
    async () => {

      try {

        const response =
          await axios.get(
            `http://localhost:8080/tracking/${resourceId}`
          );

        setTracking(
          response.data
        );

      } catch(error) {

        console.error(
          error
        );
      }
    };

  useEffect(() => {

    fetchTracking();

    const interval =
      setInterval(
        fetchTracking,
        1000
      );

    return () =>
      clearInterval(
        interval
      );

  }, []);

  if(!tracking) {

    return (
      <h2>
        Waiting for team...
      </h2>
    );
  }

  return (
    <div>

      <h2>
        Assigned Team Tracking
      </h2>

      <MapContainer
        center={[
          tracking.latitude,
          tracking.longitude
        ]}
        zoom={13}
        style={{
          height: "600px",
          width: "100%"
        }}
      >

        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        <Marker
          position={[
            tracking.startLatitude,
            tracking.startLongitude
          ]}
        >
          <Popup>
            Rescue Team Start Point
          </Popup>
        </Marker>

        <Marker
          position={[
            tracking.destinationLatitude,
            tracking.destinationLongitude
          ]}
        >
          <Popup>
            Incident Location
          </Popup>
        </Marker>

        <Marker
          icon={vehicleIcon}
          position={[
            tracking.latitude,
            tracking.longitude
          ]}
        >

          <Popup>

            Assigned Rescue Team

          </Popup>

        </Marker>

      </MapContainer>

    </div>
  );
}

export default CitizenTrackingMap;