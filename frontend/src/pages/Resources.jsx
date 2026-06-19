import { useEffect, useState } from "react";
import axios from "axios";
import MapPicker from "../components/MapPicker";

function Resources() {
  const [resources, setResources] = useState([]);

  const [formData, setFormData] = useState({
    name: "",
    type: "",
    latitude: "",
    longitude: "",
  });

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

  const addResource = async (e) => {
    e.preventDefault();

    try {
      await axios.post(
        "http://localhost:8080/resource/add",
        formData
      );

      setFormData({
        name: "",
        type: "",
        latitude: "",
        longitude: "",
      });

      fetchResources();
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchResources();
  }, []);

  return (
    <div>
      <h2>Resource Management</h2>

      <form onSubmit={addResource}>
        <input
          type="text"
          placeholder="Resource Name"
          value={formData.name}
          onChange={(e) =>
            setFormData({
              ...formData,
              name: e.target.value,
            })
          }
        />

        <select
          value={formData.type}
          onChange={(e) =>
            setFormData({
              ...formData,
              type: e.target.value,
            })
          }
        >
          <option value="">
            Select Type
          </option>

          <option value="BOAT">
            BOAT
          </option>

          <option value="AMBULANCE">
            AMBULANCE
          </option>

          <option value="VOLUNTEER">
            VOLUNTEER
          </option>
        </select>

        <h4>Select Resource Location</h4>

        <MapPicker
          setCoordinates={(coords) =>
            setFormData({
              ...formData,
              latitude: coords.latitude,
              longitude: coords.longitude,
            })
          }
        />

        <p>
          Latitude:{" "}
          {formData.latitude || "Not Selected"}
        </p>

        <p>
          Longitude:{" "}
          {formData.longitude || "Not Selected"}
        </p>

        <button type="submit">
          Add Resource
        </button>
      </form>

      <br />

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Status</th>
            <th>Latitude</th>
            <th>Longitude</th>
          </tr>
        </thead>

        <tbody>
          {resources.map((resource) => (
            <tr key={resource.id}>
              <td>{resource.id}</td>
              <td>{resource.name}</td>
              <td>{resource.type}</td>
              <td>{resource.status}</td>
              <td>{resource.latitude}</td>
              <td>{resource.longitude}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Resources;