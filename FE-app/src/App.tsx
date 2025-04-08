import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home } from "@/page/Home";
import { Login } from "./page/Login";
import { Register } from "./page/Register";

export const App = () => {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/home" element={<Home />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};
