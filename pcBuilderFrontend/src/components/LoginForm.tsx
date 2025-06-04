import { useState } from 'react';
import type {loginRequest} from "../dtos/request/loginRequest.ts";
import {login} from "../services/authService.ts";
import {Link, useNavigate} from "react-router-dom";

export default function LoginForm({ onLoginSuccess }: { onLoginSuccess: (data: any) => void }) {
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const loginRequest: loginRequest = {
        email: form.email,
        password: form.password
      };

      const data = await login(loginRequest);
      onLoginSuccess(data);
      navigate('/');
    } catch {
      setError('Błędne dane logowania');
    }
  };

  return (
      <div className="card p-4 shadow mx-auto" style={{maxWidth: 400}}>
        <h2 className="text-center mb-3">Logowanie</h2>
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={handleSubmit}>
          <input
              type="email"
              className="form-control mb-2"
              placeholder="Email"
              value={form.email}
              onChange={e => setForm({...form, email: e.target.value})}
              required
          />
          <input
              type="password"
              className="form-control mb-3"
              placeholder="Hasło"
              value={form.password}
              onChange={e => setForm({...form, password: e.target.value})}
              required
          />
          <button type="submit" className="btn btn-primary w-100">Zaloguj się</button>
        </form>

        <div className="text-center">
          <p className="mb-0">
            Nie masz konta? <Link to="/register" className="text-decoration-none">Zarejestruj się</Link>
          </p>
        </div>
      </div>
  );
}
