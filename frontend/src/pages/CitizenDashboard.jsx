import { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function CitizenDashboard() {

  const [incidents, setIncidents] =
    useState([]);

  const [assignments, setAssignments] =
    useState({});

  const [notifications, setNotifications] =
    useState([]);

  const [showNotifications, setShowNotifications] =
    useState(false);

  const fetchNotifications =
    async () => {

      try {

        const userId =
          sessionStorage.getItem(
            "userId"
          );

        const response =
          await axios.get(
            `http://localhost:8080/notification/${userId}`
          );

        setNotifications(
          response.data
        );

      } catch(error) {

        console.error(error);
      }
    };

  const fetchCitizenIncidents =
    async () => {

      try {

        const token =
          sessionStorage.getItem(
            "token"
          );

        const response =
          await axios.get(
            "http://localhost:8080/incident/my-incidents",
            {
              headers: {
                Authorization:
                  `Bearer ${token}`
              }
            }
          );

        setIncidents(
          response.data
        );

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

                setAssignments(
                  prev => ({
                    ...prev,
                    [incident.id]: {
                      assignment,
                      resource:
                        resourceResponse.data
                    }
                  })
                );
              }

            } catch (error) {

              console.error(
                error
              );
            }
          }
        );

      } catch (error) {

        console.error(
          error
        );
      }
    };

  useEffect(() => {

  fetchCitizenIncidents();
  fetchNotifications();

  const interval =
    setInterval(
      fetchCitizenIncidents,
      3000
    );

  const notificationInterval =
    setInterval(
      fetchNotifications,
      3000
    );

  return () => {

    clearInterval(
      interval
    );

    clearInterval(
      notificationInterval
    );

  };

}, []);

  return (
    <div
      style={{
        padding: "20px"
      }}
    >

      <div
  style={{
    display: "flex",
    justifyContent:
      "space-between",
    alignItems: "center"
  }}
>

  <h1>
    Citizen Dashboard
  </h1>

  <div
    style={{
      position: "relative"
    }}
  >

    <button
      onClick={() =>
        setShowNotifications(
          !showNotifications
        )
      }
    >
      🔔 (
      {
        notifications.filter(
          n => !n.read
        ).length
      }
      )
    </button>

    {showNotifications && (

      <div
            style={{
              position:
                "absolute",
              right: 0,
              width: "300px",
              background:
                "white",
              border:
                "1px solid gray",
              padding: "10px",
              zIndex: 1000
            }}
          >

            {notifications.length === 0 ? (

              <p>
                No notifications
              </p>

            ) : (

              notifications.map(
                notification => (

                  <div
                    key={
                      notification.id
                    }
                    style={{
                      borderBottom:
                        "1px solid #ddd",
                      marginBottom:
                        "10px",
                      paddingBottom:
                        "10px"
                    }}
                  >

                    <strong>
                      {
                        notification.title
                      }
                    </strong>

                    <p>
                      {
                        notification.message
                      }
                    </p>

                    {!notification.read && (

                      <button
                        onClick={async () => {

                          await axios.put(
                            `http://localhost:8080/notification/read/${notification.id}`
                          );

                          fetchNotifications();
                        }}
                      >
                        Mark Read
                      </button>

                    )}

                  </div>
                )
              )

            )}

          </div>

        )}

      </div>

    </div>

      <Link to="/incident">
        <button>
          Report New Incident
        </button>
      </Link>

      <hr />

      <h2>
        My Incidents
      </h2>

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
                  padding:
                    "15px",
                  marginBottom:
                    "15px"
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

                    {info.assignment.status === "IN_PROGRESS" && (

                      <button
                        onClick={() =>
                          window.open(
                            `/citizen-tracking?resourceId=${info.resource.id}`,
                            "_blank"
                          )
                        }
                      >
                        View Live Tracking
                      </button>

                    )}

                    {info.assignment.status === "COMPLETED" && (

                      <p
                        style={{
                          color:
                            "green",
                          fontWeight:
                            "bold"
                        }}
                      >
                        Mission Completed ✅
                      </p>

                    )}

                    {info.assignment.status === "ASSIGNED" && (

                      <p
                        style={{
                          color:
                            "orange"
                        }}
                      >
                        Waiting for team acceptance...
                      </p>

                    )}

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