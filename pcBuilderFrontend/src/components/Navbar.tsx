import { useState, useEffect } from 'react';
import { ShoppingCart } from 'lucide-react';
import { Link } from 'react-router-dom';
import type { product } from "../dtos/response/product.ts";

interface CartItem extends product {
  quantity: number;
}

export default function Navbar({ onLogout }: { onLogout: () => void }) {
  const [cartCount, setCartCount] = useState(0);

  useEffect(() => {
    const updateCartCount = () => {
      const savedCart = localStorage.getItem('cart');
      const cart: CartItem[] = savedCart ? JSON.parse(savedCart) : [];
      const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
      setCartCount(totalItems);
    };

    updateCartCount();
    // Listen for storage changes (in case cart is updated in another tab)
    window.addEventListener('storage', updateCartCount);
    return () => window.removeEventListener('storage', updateCartCount);
  }, []);

  return (
      <nav className="navbar navbar-expand-lg navbar-light bg-white shadow-sm">
        <div className="container">
          <Link className="navbar-brand fw-bold" to="/">Sklep Komputerowy</Link>
          <button
              className="navbar-toggler"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarNav"
              aria-controls="navbarNav"
              aria-expanded="false"
              aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-auto">
              <li className="nav-item">
                <Link className="nav-link" to="/">Produkty</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link d-flex align-items-center" to="/cart">
                  <ShoppingCart className="me-1" size={20} />
                  Koszyk
                  {cartCount > 0 && (
                      <span className="badge bg-primary ms-2">{cartCount}</span>
                  )}
                </Link>
              </li>
              <li className="nav-item">
                <button className="nav-link btn btn-link" onClick={onLogout}>
                  Wyloguj
                </button>
              </li>
            </ul>
          </div>
        </div>
      </nav>
  );
}