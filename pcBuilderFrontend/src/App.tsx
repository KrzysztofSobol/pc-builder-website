import { useEffect, useState } from 'react';
import LoginForm from './components/LoginForm';
import { RegisterForm } from './components/RegisterForm';
import MainPage from './components/MainPage';
import {authMe} from "./services/authService.ts";
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [checkingSession, setCheckingSession] = useState(true);

  useEffect(() => {
    authMe().then(res =>{
      if(res != null) {
        setIsAuthenticated(true);
      }
    })
        .catch(() => {})
        .finally(() => setCheckingSession(false));
  }, []);

  const handleLogin = () => setIsAuthenticated(true);
  const handleLogout = () => setIsAuthenticated(false);

  if (checkingSession) return <p className="text-center mt-5">Ładowanie...</p>;

  return (
    <Router>
      <Routes>
        <Route
            path="/"
            element={
              isAuthenticated ? (
                  <MainPage onLogout={handleLogout} />
              ) : (
                  <Navigate to="/login" replace />
              )
            }
        />

        <Route
          path="/login"
          element={
            !isAuthenticated ? (
                <LoginForm onLoginSuccess={handleLogin} />
            ) : (
                    <Navigate to="/" replace />
                )
          }
        />

        <Route
            path="/register"
            element={
              !isAuthenticated ? (
                  <RegisterForm onRegisterSuccess={handleLogin} />
              ) : (
                  <Navigate to="/" replace />
              )
            }
        />

        <Route
            path="*"
            element={<Navigate to={isAuthenticated ? "/" : "/login"} replace />}
        />

        <Route
            path="/stripe/success"
            element={<div> clap clap komponent tutaj koncowy z podsumowaniem zamowienia proszem </div>}
        />

        <Route
            path="/stripe/cancel"
            element={<div> boohoo stripe failed or u did something wrong component here </div>}
        />
      </Routes>
    </Router>
  );
}
