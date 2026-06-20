import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

function AdminIncidents() {
  const [incidents, setIncidents] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [resources, setResources] = useState([]);
  const [selectedResources, setSelectedResources] = useState({});
  const [recommendations,setRecommendations] = useState({});

  const fetchIncidents = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/incident/all"
      );

      setIncidents(response.data);
    } catch (error) {
      console.error("Error fetching incidents:", error);
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

  const searchIncidents = async () => {
    try {
      if (searchKeyword.trim() === "") {
        fetchIncidents();
        return;
      }

      const response = await axios.get(
        `http://localhost:8080/incident/search?keyword=${searchKeyword}`
      );

      setIncidents(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const filterIncidents = async (status) => {
    try {
      if (status === "ALL") {
        fetchIncidents();
        return;
      }

      const response = await axios.get(
        `http://localhost:8080/incident/filter?status=${status}`
      );

      setIncidents(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const updateStatus = async (id, status) => {
    try {
      await axios.put(
        `http://localhost:8080/incident/${id}/status?status=${status}`
      );

      fetchIncidents();
    } catch (error) {
      console.error("Error updating status:", error);
    }
  };

  const assignResource = async (
    incidentId,
    resourceId
  ) => {
    if (!resourceId) {
      alert("Please select a resource");
      return;
    }

    try {
      await axios.put(
        `http://localhost:8080/resource/assign?resourceId=${resourceId}&incidentId=${incidentId}`
      );

      fetchIncidents();
      fetchResources();
    } catch (error) {
      console.error(error);
    }
  };

  const getRecommendations = async (incidentId) => {
    try {

      const response =
        await axios.get(
          `http://localhost:8080/resource/recommend/${incidentId}`
        );

      setRecommendations({
        ...recommendations,
        [incidentId]: response.data
      });

    } catch(error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchIncidents();
    fetchResources();
  }, []);

  return (
  <div>
    <h2>All Incidents</h2>

    <div style={{ marginBottom: "20px" }}>
      <input
        type="text"
        placeholder="Search by title..."
        value={searchKeyword}
        onChange={(e) =>
          setSearchKeyword(e.target.value)
        }
      />

      <button
        onClick={searchIncidents}
        style={{ marginLeft: "10px" }}
      >
        Search
      </button>

      <button
        onClick={fetchIncidents}
        style={{ marginLeft: "10px" }}
      >
        Reset
      </button>
    </div>

    <div style={{ marginBottom: "20px" }}>
      <label>Status Filter: </label>

      <select
        value={statusFilter}
        onChange={(e) => {
          const status = e.target.value;
          setStatusFilter(status);
          filterIncidents(status);
        }}
      >
        <option value="ALL">ALL</option>
        <option value="OPEN">OPEN</option>
        <option value="ASSIGNED">ASSIGNED</option>
        <option value="IN_PROGRESS">
          IN_PROGRESS
        </option>
        <option value="RESOLVED">
          RESOLVED
        </option>
      </select>
    </div>

    <div style={{ marginBottom: "20px" }}>
      <Link to="/assignments">
        <button>
          Assignment History
        </button>
      </Link>
    </div>

    <table border="1" cellPadding="10">
      <thead>
        <tr>
          <th>ID</th>
          <th>Title</th>
          <th>Description</th>
          <th>Severity</th>
          <th>Status</th>
          <th>Assign Resource</th>
          <th>Recommendations</th>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody>
        {incidents.map((incident) => (
          <tr key={incident.id}>
            <td>{incident.id}</td>
            <td>{incident.title}</td>
            <td>{incident.description}</td>
            <td>{incident.severity}</td>
            <td>{incident.status}</td>

            <td>
              <select
                value={
                  selectedResources[
                    incident.id
                  ] || ""
                }
                onChange={(e) =>
                  setSelectedResources({
                    ...selectedResources,
                    [incident.id]:
                      e.target.value,
                  })
                }
              >
                <option value="">
                  Select Resource
                </option>

                {resources
                  .filter(
                    (resource) =>
                      resource.status ===
                      "AVAILABLE"
                  )
                  .map((resource) => (
                    <option
                      key={resource.id}
                      value={resource.id}
                    >
                      {resource.name} (
                      {resource.type})
                    </option>
                  ))}
              </select>

              <button
                style={{
                  marginLeft: "10px",
                }}
                onClick={() =>
                  assignResource(
                    incident.id,
                    selectedResources[
                      incident.id
                    ]
                  )
                }
              >
                Assign
              </button>
            </td>

            <td>
              <button
                onClick={() =>
                  getRecommendations(
                    incident.id
                  )
                }
              >
                Recommend
              </button>

              {recommendations[
                incident.id
              ] && (
                <div
                  style={{
                    marginTop: "10px",
                  }}
                >
                  {recommendations[
                    incident.id
                  ].map((rec) => (
                    <div
                      key={
                        rec.resourceId
                      }
                      style={{
                        border:
                          "1px solid #ccc",
                        padding: "6px",
                        marginTop: "5px",
                      }}
                    >
                      <strong>
                        {
                          rec.resourceName
                        }
                      </strong>

                      <br />

                      Type:{" "}
                      {
                        rec.resourceType
                      }

                      <br />

                      Distance:{" "}
                      {rec.distanceKm.toFixed(
                        2
                      )}{" "}
                      km

                      <br />

                      ETA: {rec.eta}

                      <br />

                      <button
                        style={{
                          marginTop:
                            "5px",
                        }}
                        onClick={() =>
                          assignResource(
                            incident.id,
                            rec.resourceId
                          )
                        }
                      >
                        Assign
                      </button>
                    </div>
                  ))}
                </div>
              )}
            </td>

            <td>
              <button
                disabled={
                  incident.status !==
                  "ASSIGNED"
                }
                onClick={() =>
                  updateStatus(
                    incident.id,
                    "IN_PROGRESS"
                  )
                }
              >
                Start
              </button>

              <button
                style={{
                  marginLeft: "10px",
                }}
                disabled={
                  incident.status !==
                  "IN_PROGRESS"
                }
                onClick={() =>
                  updateStatus(
                    incident.id,
                    "RESOLVED"
                  )
                }
              >
                Resolve
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);
}

export default AdminIncidents;