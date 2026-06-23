import { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    setError("");
    setMessage("");

    try {

      const res = await api.post(
        "/auth/login",
        {
          email,
          password
        }
      );

      sessionStorage.setItem(
        "token",
        res.data.token
      );

      sessionStorage.setItem(
        "userId",
        res.data.userId
      );

      sessionStorage.setItem(
        "name",
        res.data.name
      );

      sessionStorage.setItem(
        "role",
        res.data.role
      );

      setMessage(
        "Login successful ✅"
      );

      if (
        res.data.role === "ADMIN"
      ) {

        navigate(
          "/admin/incidents"
        );

      } else if (
        res.data.role === "RESCUE"
      ) {

        navigate(
          "/rescue"
        );

      } else if (
        res.data.role === "CITIZEN"
      ) {

        navigate(
          "/citizen"
        );
      }

    } catch (err) {

      setError(
        err.response?.data?.message ||
        "Invalid credentials ❌"
      );
    }
  };

  return (
    <div style={styles.container}>
      <h2>Login</h2>

      <input
        placeholder="Email"
        onChange={(e) => setEmail(e.target.value)}
        style={styles.input}
      />

      <input
        placeholder="Password"
        type="password"
        onChange={(e) => setPassword(e.target.value)}
        style={styles.input}
      />

      <button onClick={handleLogin} style={styles.button}>
        Login
      </button>

      {message && <p style={{ color: "green" }}>{message}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

const styles = {
  container: {
    width: "300px",
    margin: "100px auto",
    padding: "20px",
    boxShadow: "0 0 10px #ccc",
    textAlign: "center"
  },
  input: {
    width: "100%",
    margin: "10px 0",
    padding: "8px"
  },
  button: {
    width: "100%",
    padding: "10px",
    background: "black",
    color: "white",
    cursor: "pointer"
  }
};

export default Login;