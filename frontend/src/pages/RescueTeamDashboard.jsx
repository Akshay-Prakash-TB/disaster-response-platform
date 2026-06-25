import { useEffect, useState } from "react";
import axios from "axios";

function RescueTeamDashboard() {

  const [missions, setMissions] =
    useState([]);

  const [notifications, setNotifications] =
    useState([]);

  const [showNotifications,
    setShowNotifications] =
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

  const fetchMissions =
    async () => {

      try {

        const userId =
          sessionStorage.getItem(
            "userId"
          );

        const response =
          await axios.get(
            `http://localhost:8080/assignment/user/${userId}`
          );

        setMissions(
          response.data
        );

      } catch (error) {

        console.error(error);
      }
    };

  const acceptMission =
    async (id) => {

      try {

        await axios.put(
          `http://localhost:8080/assignment/${id}/accept`
        );

        fetchMissions();

      } catch (error) {

        console.error(error);
      }
    };

  const completeMission =
    async (id) => {

      try {

        await axios.put(
          `http://localhost:8080/assignment/${id}/complete`
        );

        fetchMissions();

      } catch (error) {

        console.error(error);
      }
    };

  useEffect(() => {

    fetchMissions();
    fetchNotifications();

    const missionInterval =
      setInterval(
        fetchMissions,
        3000
      );

    const notificationInterval =
      setInterval(
        fetchNotifications,
        3000
      );

    return () => {

      clearInterval(
        missionInterval
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
          alignItems:
            "center"
        }}
      >

        <h2>
          Rescue Team Dashboard
        </h2>

        <div
          style={{
            position:
              "relative"
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

      {missions.length === 0 ? (

        <p>
          No missions assigned.
        </p>

      ) : (

        missions.map(
          (mission) => (

            <div
              key={
                mission.assignmentId
              }
              style={{
                border:
                  "1px solid gray",
                padding:
                  "15px",
                marginBottom:
                  "15px",
                borderRadius:
                  "8px"
              }}
            >

              <h3>
                {mission.title}
              </h3>

              <p>
                {mission.description}
              </p>

              <p>
                Severity:
                {" "}
                {mission.severity}
              </p>

              <p>
                Citizen:
                {" "}
                {mission.citizenName}
              </p>

              <p>
                Email:
                {" "}
                {mission.citizenEmail}
              </p>

              <p>
                Incident ID:
                {" "}
                {mission.incidentId}
              </p>

              <p>
                Latitude:
                {" "}
                {mission.latitude}
              </p>

              <p>
                Longitude:
                {" "}
                {mission.longitude}
              </p>

              <p>
                Status:
                {" "}
                {mission.assignmentStatus}
              </p>

              {mission.assignmentStatus ===
                "ASSIGNED" && (

                <button
                  onClick={() =>
                    acceptMission(
                      mission.assignmentId
                    )
                  }
                >
                  Accept Mission
                </button>

              )}

              {mission.assignmentStatus ===
                "IN_PROGRESS" && (

                <button
                  onClick={() =>
                    completeMission(
                      mission.assignmentId
                    )
                  }
                >
                  Complete Mission
                </button>

              )}

            </div>

          )
        )

      )}

    </div>

  );
}

export default RescueTeamDashboard;