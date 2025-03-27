import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Home } from "@/page/Home";

export const App = () => {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};
