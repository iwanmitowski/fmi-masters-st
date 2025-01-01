import { Route, Routes } from "react-router-dom";
import "./App.css";
import Main from "./components/shared/Main/Main";
import Login from "./components/Auth/Login/Login";
import Register from "./components/Auth/Register/Register";
import NoAuthGuard from "./components/Guards/NoAuthGuard";

function App() {
  return (
    <div className="App">
      <Main>
        <Routes style={{ overflowX: "hidden", minHeight: "100vh" }}>
          <Route
            path="/login"
            element={
              <NoAuthGuard>
                <Login />
              </NoAuthGuard>
            }
          />
          <Route
            path="/register"
            element={
              <NoAuthGuard>
                <Register />
              </NoAuthGuard>
            }
          />
        </Routes>
      </Main>
    </div>
  );
}

export default App;
