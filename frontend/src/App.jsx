import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import IncidentReport from "./pages/IncidentReport";
import AdminIncidents from "./pages/AdminIncidents";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/incident" element={<IncidentReport />} />
        <Route path="/admin/incidents" element={<AdminIncidents />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;