import { useState } from 'react';

export default function LoginForm({ onLoginSuccess }: { onLoginSuccess: (data: any) => void }) {
  const [form, setForm] = useState({ email: '', password: '' });
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    try {
      const res = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(form),
      });

      if (!res.ok) {
        setError('Nieprawidłowy login lub hasło.');
        return;
      }

      const data = await res.json();
      onLoginSuccess(data);
    } catch {
      setError('Błąd połączenia z serwerem.');
    }
  };

  return (
    <div className="card p-4 shadow mx-auto" style={{ maxWidth: 400 }}>
      <h2 className="text-center mb-3">Logowanie</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          className="form-control mb-2"
          placeholder="Email"
          value={form.email}
          onChange={e => setForm({ ...form, email: e.target.value })}
        />
        <input
          type="password"
          className="form-control mb-3"
          placeholder="Hasło"
          value={form.password}
          onChange={e => setForm({ ...form, password: e.target.value })}
        />
        <button type="submit" className="btn btn-primary w-100">Zaloguj się</button>
      </form>
    </div>
  );
}
