import { useState } from "react";
import api from "../services/api";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("CITIZEN");

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleRegister = async () => {
    setError("");
    setMessage("");

    try {
      await api.post("/auth/register", {
        name,
        email,
        password,
        role
      });

      setMessage("Registration successful ✅");
    } catch (err) {
      setError(
        err.response?.data?.message || "User already exists ❌"
      );
    }
  };

  return (
    <div style={styles.container}>
      <h2>Register</h2>

      <input placeholder="Name" onChange={(e) => setName(e.target.value)} style={styles.input} />
      <input placeholder="Email" onChange={(e) => setEmail(e.target.value)} style={styles.input} />
      <input placeholder="Password" type="password" onChange={(e) => setPassword(e.target.value)} style={styles.input} />

      <select onChange={(e) => setRole(e.target.value)} style={styles.input}>
        <option value="CITIZEN">Citizen</option>
        <option value="NGO">NGO</option>
        <option value="RESCUE">Rescue</option>
        <option value="ADMIN">Admin</option>
      </select>

      <button onClick={handleRegister} style={styles.button}>
        Register
      </button>

      {message && <p style={{ color: "green" }}>{message}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

const styles = {
  container: {
    width: "300px",
    margin: "80px auto",
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

export default Register;