import { useState } from "react";
import axios from "axios";

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

        <input
          type="text"
          name="severity"
          placeholder="Severity"
          value={formData.severity}
          onChange={handleChange}
        />

        <br /><br />

        <input
          type="number"
          step="any"
          name="latitude"
          placeholder="Latitude"
          value={formData.latitude}
          onChange={handleChange}
        />

        <br /><br />

        <input
          type="number"
          step="any"
          name="longitude"
          placeholder="Longitude"
          value={formData.longitude}
          onChange={handleChange}
        />

        <br /><br />

        <input
          type="number"
          name="citizenId"
          placeholder="Citizen ID"
          value={formData.citizenId}
          onChange={handleChange}
        />

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