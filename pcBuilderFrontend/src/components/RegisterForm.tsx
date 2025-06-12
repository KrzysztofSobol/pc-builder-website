import { useState } from 'react';
import { register, authMe } from '../services/authService';
import type { registerRequest } from '../dtos/request/registerRequest';
import type { userResponse } from '../dtos/response/userResponse';
import {Link} from "react-router-dom";

interface RegisterFormProps {
  onRegisterSuccess: (user: userResponse) => void;
}

export default function RegisterForm({ onRegisterSuccess }: RegisterFormProps) {
  const [formData, setFormData] = useState<registerRequest>({
    email: '',
    password: '',
    confirmPassword: '',
    username: ''
  });
  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    try {
      await register(formData); // Register and set HTTP-only cookie
      const user = await authMe(); // Fetch user details including role
      onRegisterSuccess(user);
    } catch (err) {
      setError('Błąd rejestracji. Sprawdź dane i spróbuj ponownie.');
    }
  };

  return (
      <div className="container py-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <div className="card">
              <div className="card-body">
                <h3 className="card-title text-center mb-4">Rejestracja</h3>
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label htmlFor="username" className="form-label">Nazwa użytkownika</label>
                    <input
                        type="text"
                        className="form-control"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="email" className="form-label">Email</label>
                    <input
                        type="email"
                        className="form-control"
                        id="email"
                        name="email"
                        value={formData.email}
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
                        value={formData.password}
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
                    Zarejestruj się
                  </button>
                </form>
                <div className="text-center mt-3">
                  <p>Masz już konto? <Link to="/login">Zaloguj się</Link></p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}