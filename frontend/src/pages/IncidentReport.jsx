import { useState } from "react";
import axios from "axios";
import MapPicker from "../components/MapPicker";

function IncidentReport() {
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    severity: "",
    latitude: "",
    longitude: "",
    citizenId: ""
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8080/incident/report",
        formData
      );

      setMessage("Incident reported successfully");

      setFormData({
        title: "",
        description: "",
        severity: "",
        latitude: "",
        longitude: "",
        citizenId: ""
      });

    } catch (error) {
      setMessage("Failed to report incident");
    }
  };

  return (
    <div>
      <h2>Report Incident</h2>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="title"
          placeholder="Title"
          value={formData.title}
          onChange={handleChange}
        />

        <br /><br />

        <textarea
          name="description"
          placeholder="Description"
          value={formData.description}
          onChange={handleChange}
        />

        <br /><br />

        <select
        value={formData.severity}
        onChange={(e) =>
        setFormData({
        ...formData,
        severity: e.target.value
        })}>
        <option value="">Select Severity</option>
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
        <option value="VERY_HIGH">VERY HIGH</option>
        <option value="CRITICAL">CRITICAL</option>
        </select>

        <br /><br />

        <h3>Select Incident Location</h3>

        <MapPicker
          setCoordinates={(coords) =>
            setFormData({
              ...formData,
              latitude: coords.latitude,
              longitude: coords.longitude,
            })
          }
        />

        <br /><br />

        <p>
          Latitude: {formData.latitude || "Not Selected"}
        </p>

        <p>
          Longitude: {formData.longitude || "Not Selected"}
        </p>

        <br /><br />

        <button type="submit">
          Submit Incident
        </button>
      </form>

      <br />

      {message && <p>{message}</p>}
    </div>
  );
}

export default IncidentReport;