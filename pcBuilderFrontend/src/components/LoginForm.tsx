import { useState } from 'react';
import { login, authMe } from '../services/authService';
import type { loginRequest } from '../dtos/request/loginRequest';
import type { userResponse } from '../dtos/response/userResponse';
import {Link} from "react-router-dom";

interface LoginFormProps {
  onLoginSuccess: (user: userResponse) => void;
}

export default function LoginForm({ onLoginSuccess }: LoginFormProps) {
  const [credentials, setCredentials] = useState<loginRequest>({
    email: '',
    password: ''
  });
  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      await login(credentials);
      const user = await authMe();
      onLoginSuccess(user);
    } catch (err) {
      setError('Błąd logowania. Sprawdź dane i spróbuj ponownie.');
    }
  };

  return (
      <div className="container py-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <div className="card">
              <div className="card-body">
                <h3 className="card-title text-center mb-4">Logowanie</h3>
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label htmlFor="email" className="form-label">Email</label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        name="email"
                        value={credentials.email}
                        onChange={handleChange}
                        required
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="password" className="form-label">Hasło</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        name="password"
                        value={credentials.password}
                        onChange={handleChange}
                        required
                    />
                  </div>
                  {error && (
                      <div className="alert alert-danger" role="alert">
                        {error}
                      </div>
                  )}
                  <button type="submit" className="btn btn-primary w-100">
                    Zaloguj się
                  </button>
                </form>
                <div className="text-center mt-3">
                  <p>Nie masz konta? <Link to="/register">Zarejestruj się</Link></p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}