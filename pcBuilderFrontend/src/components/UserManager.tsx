import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';


type User = {
  userID: string;
  username: string;
  email: string;
};

export default function UserManager() {
  const [users, setUsers] = useState<User[]>([]);
  const [form, setForm] = useState({
    username: '',
    email: '',
    password: '',
  });
  const [error, setError] = useState<string | null>(null);

  // Pobierz użytkowników
  const fetchUsers = async () => {
    try {
      const res = await fetch('http://localhost:8080/users', {
        credentials: 'include',
      });
      if (!res.ok) throw new Error('Błąd pobierania użytkowników');
      const data: User[] = await res.json();
      setUsers(data);
    } catch (err) {
      console.error(err);
      setError('Nie udało się pobrać użytkowników.');
    }
  };

  // Dodaj użytkownika
  const addUser = async (e: FormEvent) => {
    e.preventDefault();
    setError(null);

    if (!form.username || !form.email || !form.password) {
      setError('Wszystkie pola są wymagane.');
      return;
    }

    try {
      const res = await fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(form),
      });

      if (!res.ok) {
        const message = await res.text();
        throw new Error(message || 'Błąd dodawania użytkownika');
      }

      setForm({ username: '', email: '', password: '' });
      fetchUsers();
    } catch (err) {
      console.error(err);
      setError('Nie udało się dodać użytkownika.');
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <div className="p-4">
      <h2 className="text-xl font-bold mb-4">Lista użytkowników</h2>
      {error && <p className="text-red-500">{error}</p>}
      <ul className="mb-6">
        {users.map((user) => (
          <li key={user.userID}>
            {user.username} - {user.email}
          </li>
        ))}
      </ul>

      <h3 className="text-lg font-semibold mb-2">Dodaj użytkownika</h3>
      <form onSubmit={addUser} className="space-y-2">
        <input
          type="text"
          placeholder="Nazwa użytkownika"
          value={form.username}
          onChange={(e) => setForm({ ...form, username: e.target.value })}
          className="border p-2 block w-full"
        />
        <input
          type="email"
          placeholder="Email"
          value={form.email}
          onChange={(e) => setForm({ ...form, email: e.target.value })}
          className="border p-2 block w-full"
        />
        <input
          type="password"
          placeholder="Hasło"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
          className="border p-2 block w-full"
        />
        <button type="submit" className="bg-blue-500 text-white p-2 rounded">
          Dodaj użytkownika
        </button>
      </form>
    </div>
  );
}