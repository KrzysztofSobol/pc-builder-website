import { useState } from 'react';

export function RegisterForm({ onRegisterSuccess }: { onRegisterSuccess: (data: any) => void }) {
  const [form, setForm] = useState({
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (form.password !== form.confirmPassword) {
      setError('Hasła się nie zgadzają.');
      return;
    }

    try {
      const res = await fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(form),
      });

      if (!res.ok) {
        const { message } = await res.json();
        setError(message || 'Błąd rejestracji.');
        return;
      }

      const data = await res.json();
      onRegisterSuccess(data);
    } catch {
      setError('Błąd połączenia z serwerem.');
    }
  };

  return (
    <div className="card p-4 shadow mx-auto" style={{ maxWidth: 400 }}>
      <h2 className="text-center mb-3">Rejestracja</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          className="form-control mb-2"
          placeholder="Nazwa użytkownika"
          value={form.username}
          onChange={e => setForm({ ...form, username: e.target.value })}
        />
        <input
          type="email"
          className="form-control mb-2"
          placeholder="Email"
          value={form.email}
          onChange={e => setForm({ ...form, email: e.target.value })}
        />
        <input
          type="password"
          className="form-control mb-2"
          placeholder="Hasło"
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
        />
        <input
          type="password"
          className="form-control mb-3"
          placeholder="Potwierdź hasło"
          value={form.confirmPassword}
          onChange={e => setForm({ ...form, confirmPassword: e.target.value })}
        />
        <button type="submit" className="btn btn-success w-100">Zarejestruj się</button>
      </form>
    </div>
  );
}
