import { useEffect, useState } from 'react';
import LoginForm from './components/LoginForm';
import { RegisterForm } from './components/RegisterForm';
import MainPage from './components/MainPage';

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [checkingSession, setCheckingSession] = useState(true);
  const [showRegister, setShowRegister] = useState(false);

  useEffect(() => {
    fetch('http://localhost:8080/auth/me', {
      credentials: 'include',
    })
      .then(res => {
        if (res.ok) {
          setIsAuthenticated(true);
        }
      })
      .catch(() => {})
      .finally(() => setCheckingSession(false));
  }, []);

  const handleLogin = () => setIsAuthenticated(true);
  const handleLogout = () => setIsAuthenticated(false);

  if (checkingSession) return <p className="text-center mt-5">Ładowanie...</p>;
  if (isAuthenticated) return <MainPage onLogout={handleLogout} />;

  return (
    <div className="container py-4">
      {showRegister ? (
        <>
          <RegisterForm onRegisterSuccess={handleLogin} />
          <p className="mt-3 text-center">
            Masz już konto?{' '}
            <button className="btn btn-link" onClick={() => setShowRegister(false)}>
              Zaloguj się
            </button>
          </p>
        </>
      ) : (
        <>
          <LoginForm onLoginSuccess={handleLogin} />
          <p className="mt-3 text-center">
            Nie masz konta?{' '}
            <button className="btn btn-link" onClick={() => setShowRegister(true)}>
              Zarejestruj się
            </button>
          </p>
        </>
      )}
    </div>
  );
}
