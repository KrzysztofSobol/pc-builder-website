export default function Navbar({ onLogout }: { onLogout: () => void }) {
  const handleLogout = async () => {
    try {
      await fetch('http://localhost:8080/auth/logout', {
        method: 'POST',
        credentials: 'include',
      });
    } catch (e) {
      console.warn('Wylogowanie nie powiodło się, ale kontynuuję lokalnie.');
    } finally {
      onLogout(); 
    }
  };

  return (
    <nav className="navbar navbar-dark bg-dark px-4">
      <span className="navbar-brand">🖥 PCBuilder</span>
      <button onClick={handleLogout} className="btn btn-outline-light">
        Wyloguj się
      </button>
    </nav>
  );
}
