import { useEffect, useState } from "react";
import axios from "axios";

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

function VehicleTrackingMap() {

  const [vehicles, setVehicles] =
    useState([]);

  const fetchTracking = async () => {

    try {

      const response =
        await axios.get(
          "http://localhost:8080/tracking/all"
        );

      setVehicles(
        response.data
      );

    } catch (error) {

      console.error(error);
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

  if (
    vehicles.length === 0
  ) {

    return (
      <h2>
        Loading...
      </h2>
    );
  }

  return (
    <div>

      <h2>
        Live Vehicle Tracking
      </h2>

      <MapContainer
        center={[
          vehicles[0].latitude,
          vehicles[0].longitude
        ]}
        zoom={10}
        style={{
          height: "600px",
          width: "100%"
        }}
      >

        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {vehicles.map(
          (vehicle) => (

            <Marker
              icon={vehicleIcon}
              key={
                vehicle.resourceId
              }
              position={[
                vehicle.latitude,
                vehicle.longitude
              ]}
            >

              <Popup>

                Resource ID:
                {" "}
                {
                  vehicle.resourceId
                }

                <br />

                Latitude:
                {" "}
                {
                  vehicle.latitude
                }

                <br />

                Longitude:
                {" "}
                {
                  vehicle.longitude
                }

                <br />

                Point:
                {" "}
                {
                  vehicle.currentPointIndex
                }

              </Popup>

            </Marker>

          )
        )}

      </MapContainer>

    </div>
  );
}

export default VehicleTrackingMap;