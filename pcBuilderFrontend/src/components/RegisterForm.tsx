import { useState } from 'react';
import type {registerRequest} from "../dtos/request/registerRequest.ts";
import {register} from "../services/authService.ts";
import {Link, useNavigate} from "react-router-dom";

export function RegisterForm({ onRegisterSuccess }: { onRegisterSuccess: (data: any) => void }) {
  const [form, setForm] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (form.password !== form.confirmPassword) {
      setError('Hasła się nie zgadzają.');
      return;
    }

    if(form.email.trim() !== ''){
      setError("Username cannot be just whitespace :P");
      return;
    }

    try {
      const registerData: registerRequest = {
        username: form.username,
        email: form.email,
        password: form.password,
        confirmPassword: form.password
      };

      const data = await register(registerData);
      onRegisterSuccess(data);
      navigate('/');
    } catch (error: any) {
      if(error.response?.data?.message) {
        setError(error.response.data.message);
      } else if (error.message) {
        setError(error.message);
      } else {
        setError('Register error');
      }
    }
  };

  return (
      <div className="card p-4 shadow mx-auto" style={{maxWidth: 400}}>
        <h2 className="text-center mb-3">Rejestracja</h2>
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={handleSubmit}>
          <input
              type="text"
              className="form-control mb-2"
              placeholder="Nazwa użytkownika"
              value={form.username}
              onChange={e => setForm({...form, username: e.target.value})}
              required
          />
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
              className="form-control mb-2"
              placeholder="Hasło"
              value={form.password}
              onChange={e => setForm({...form, password: e.target.value})}
              required
          />
          <input
              type="password"
              className="form-control mb-3"
              placeholder="Potwierdź hasło"
              value={form.confirmPassword}
              onChange={e => setForm({...form, confirmPassword: e.target.value})}
              required
          />
          <button type="submit" className="btn btn-success w-100">Zarejestruj się</button>
        </form>

        <div className="text-center">
          <p className="mb-0">
            Masz już konto? <Link to="/login" className="text-decoration-none">Zaloguj się</Link>
          </p>
        </div>
      </div>
  );
}
