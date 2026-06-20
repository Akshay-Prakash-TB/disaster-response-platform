import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function CitizenDashboard() {

  const [incidents, setIncidents] = useState([]);
  const [assignments, setAssignments] = useState({});

  const fetchCitizenIncidents = async () => {

    try {

      const citizenId =
        localStorage.getItem("userId");

      const response =
        await axios.get(
          `http://localhost:8080/incident/citizen/${citizenId}`
        );

      setIncidents(response.data);

      response.data.forEach(
        async (incident) => {

          try {

            const assignmentResponse =
              await axios.get(
                `http://localhost:8080/assignment/incident/${incident.id}`
              );

            const assignment =
              assignmentResponse.data;

            if (assignment) {

              const resourceResponse =
                await axios.get(
                  `http://localhost:8080/resource/${assignment.resourceId}`
                );

              setAssignments(prev => ({
                ...prev,
                [incident.id]: {
                  assignment,
                  resource:
                    resourceResponse.data
                }
              }));
            }

          } catch (error) {
            console.error(error);
          }
        }
      );

    } catch (error) {
      console.error(error);
    }
  };

    useEffect(() => {
    fetchCitizenIncidents();
  }, []);

  return (
    <div style={{ padding: "20px" }}>

      <h1>Citizen Dashboard</h1>

      <Link to="/incident">
        <button>
          Report New Incident
        </button>
      </Link>

      <hr />

      <h2>My Incidents</h2>

      {incidents.length === 0 ? (
        <p>
          No incidents reported.
        </p>
      ) : (
        incidents.map(
          (incident) => {

            const info =
              assignments[
                incident.id
              ];

            return (
              <div
                key={incident.id}
                style={{
                  border:
                    "1px solid gray",
                  padding: "15px",
                  marginBottom: "15px"
                }}
              >
                <h3>
                  {incident.title}
                </h3>

                <p>
                  {incident.description}
                </p>

                <p>
                  Severity:
                  {" "}
                  {incident.severity}
                </p>

                <p>
                  Status:
                  {" "}
                  {incident.status}
                </p>

                <p>
                  Type:
                  {" "}
                  {incident.incidentType}
                </p>

                {info ? (
                  <>
                    <hr />

                    <p>
                      Assigned Team:
                      {" "}
                      {info.resource.name}
                    </p>

                    <p>
                      Team Type:
                      {" "}
                      {info.resource.type}
                    </p>

                    <p>
                      Mission Status:
                      {" "}
                      {info.assignment.status}
                    </p>
                  </>
                ) : (
                  <p>
                    No team assigned yet.
                  </p>
                )}

              </div>
            );
          }
        )
      )}

    </div>
  );
}

export default CitizenDashboard;