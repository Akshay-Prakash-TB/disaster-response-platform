import { useState } from "react";
import axios from "axios";
import MapPicker from "../components/MapPicker";

function IncidentReport() {

const [formData, setFormData] = useState({
title: "",
description: "",
severity: "",
incidentType: "",
latitude: "",
longitude: "",
image: null
});

const [message, setMessage] =
useState("");

const handleChange = (e) => {
setFormData({
...formData,
[e.target.name]:
e.target.value
});
};

const handleSubmit = async (e) => {

    e.preventDefault();

    try {

        const token =
            sessionStorage.getItem(
                "token"
            );

        const data =
            new FormData();

        const incident = {

            title: formData.title,
            description: formData.description,
            severity: formData.severity,
            incidentType: formData.incidentType,
            latitude: formData.latitude,
            longitude: formData.longitude

        };

        data.append(

            "incident",

            new Blob(
                [JSON.stringify(incident)],
                {
                    type: "application/json"
                }
            )

        );

        if (formData.image) {

            data.append(
                "image",
                formData.image
            );

        }

        await axios.post(

            "http://localhost:8080/incident/report",

            data,

            {

                headers: {

                    Authorization:
                        `Bearer ${token}`,

                    "Content-Type":
                        "multipart/form-data"

                }

            }

        );

        setMessage(
            "Incident reported successfully"
        );

        setFormData({

            title: "",
            description: "",
            severity: "",
            incidentType: "",
            latitude: "",
            longitude: "",
            image: null

        });

    }

    catch (error) {

        console.error(error);

        setMessage(
            "Failed to report incident"
        );

    }

};

return (
<div>

  <h2>
    Report Incident
  </h2>

  <form
    onSubmit={
      handleSubmit
    }
  >

    <input
      type="text"
      name="title"
      placeholder="Title"
      value={
        formData.title
      }
      onChange={
        handleChange
      }
    />

    <br />
    <br />

    <textarea
      name="description"
      placeholder="Description"
      value={
        formData.description
      }
      onChange={
        handleChange
      }
    />

    <br />
    <br />

    <select
      value={
        formData.severity
      }
      onChange={(e) =>
        setFormData({
          ...formData,
          severity:
            e.target.value
        })
      }
    >
      <option value="">
        Select Severity
      </option>

      <option value="LOW">
        LOW
      </option>

      <option value="MEDIUM">
        MEDIUM
      </option>

      <option value="HIGH">
        HIGH
      </option>

      <option value="VERY_HIGH">
        VERY HIGH
      </option>

      <option value="CRITICAL">
        CRITICAL
      </option>
    </select>

    <br />
    <br />

    <select
      name="incidentType"
      value={
        formData.incidentType
      }
      onChange={
        handleChange
      }
    >
      <option value="">
        Select Type
      </option>

      <option value="FLOOD">
        FLOOD
      </option>

      <option value="MEDICAL">
        MEDICAL
      </option>

      <option value="RESCUE">
        RESCUE
      </option>
    </select>

    <br />
    <br />

    <h3>
      Select Incident Location
    </h3>

    <MapPicker
      setCoordinates={
        (coords) =>
          setFormData({
            ...formData,
            latitude:
              coords.latitude,
            longitude:
              coords.longitude
          })
      }
    />

    <br />
    <br />

    <p>
      Latitude:{" "}
      {formData.latitude ||
        "Not Selected"}
    </p>

    <p>
      Longitude:{" "}
      {formData.longitude ||
        "Not Selected"}
    </p>

    <br />
    <br />

    <h3>Upload Incident Image (Optional)</h3>

      <input
          type="file"
          accept="image/*"
          onChange={(e) =>
              setFormData({
                  ...formData,
                  image: e.target.files[0]
              })
          }
      />

    <br />
    <br />

    <button
      type="submit"
    >
      Submit Incident
    </button>

  </form>

  <br />

  {message && (
    <p>
      {message}
    </p>
  )}

</div>

);
}

export default IncidentReport;