import Navbar from './Navbar';

export default function MainPage({ onLogout }: { onLogout: () => void }) {
  return (
    <div className="min-vh-100 bg-light">
      <Navbar onLogout={onLogout} />
      <div className="container py-5">
        <h1 className="display-5 fw-bold">Witamy w sklepie komputerowym!</h1>
        <p className="lead">Tutaj możesz przeglądać produkty, tworzyć zamówienia i więcej.</p>
      </div>
    </div>
  );
}
