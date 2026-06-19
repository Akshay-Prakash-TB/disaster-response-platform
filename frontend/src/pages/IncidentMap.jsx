import { useEffect, useState } from "react";
import axios from "axios";

import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
} from "react-leaflet";

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

function IncidentMap() {
  const [incidents, setIncidents] = useState([]);
  const [resources, setResources] = useState([]);

  const fetchIncidents = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/incident/all"
      );

      setIncidents(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchResources = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/resource/all"
      );

      setResources(response.data);
    } catch (error) {
      console.error(error);
    }
  };

    useEffect(() => {
    fetchIncidents();
    fetchResources();
  }, []);

  return (
    <div>
      <h2>Disaster Response Map</h2>

      <MapContainer
        center={[20.5937, 78.9629]}
        zoom={5}
        style={{
          height: "600px",
          width: "100%",
        }}
      >
        <TileLayer
          attribution="&copy; OpenStreetMap contributors"
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />

        {incidents
          .filter(
            (incident) =>
              incident.latitude != null &&
              incident.longitude != null
          )
          .map((incident) => (
            <Marker
              key={`incident-${incident.id}`}
              position={[
                incident.latitude,
                incident.longitude,
              ]}
            >
              <Popup>
                <strong>
                  {incident.title}
                </strong>

                <br />

                {incident.description}

                <br />

                Severity: {incident.severity}

                <br />

                Status: {incident.status}
              </Popup>
            </Marker>
          ))}

        {resources
          .filter(
            (resource) =>
              resource.latitude != null &&
              resource.longitude != null
          )
          .map((resource) => (
            <Marker
              key={`resource-${resource.id}`}
              position={[
                resource.latitude,
                resource.longitude,
              ]}
            >
              <Popup>
                <strong>
                  {resource.name}
                </strong>

                <br />

                Type: {resource.type}

                <br />

                Status: {resource.status}

                <br />

                Assigned Incident:
                {" "}
                {resource.incidentId ?? "None"}
              </Popup>
            </Marker>
          ))}
      </MapContainer>
    </div>
  );
}

export default IncidentMap;