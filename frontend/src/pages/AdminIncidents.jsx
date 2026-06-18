import { useEffect, useState } from "react";
import axios from "axios";

function AdminIncidents() {
  const [incidents, setIncidents] = useState([]);

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

  useEffect(() => {
    fetchIncidents();
  }, []);

  return (
    <div>
      <h2>All Incidents</h2>

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Severity</th>
            <th>Status</th>
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
                <button
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
                  onClick={() =>
                    updateStatus(
                      incident.id,
                      "RESOLVED"
                    )
                  }
                  style={{ marginLeft: "10px" }}
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