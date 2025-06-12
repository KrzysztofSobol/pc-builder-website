import { useEffect, useState } from 'react';
import LoginForm from './components/LoginForm';
import MainPage from './components/MainPage';
import Cart from './components/Cart';
import CheckoutForm from './components/CheckoutForm';
import OrderSummary from './components/OrderSummary';
import Navbar from './components/Navbar';
import { authMe, logout } from './services/authService';
import { AuthProvider } from './AuthContext';
import type { userResponse } from './dtos/response/userResponse';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import RegisterForm from "./components/RegisterForm.tsx";

export default function App() {
    const [user, setUser] = useState<userResponse | null>(null);
    const [checkingSession, setCheckingSession] = useState(true);

    useEffect(() => {
        authMe()
            .then(res => {
                setUser(res);
            })
            .catch(() => {
                setUser(null);
            })
            .finally(() => setCheckingSession(false));
    }, []);

    const handleLogin = (response: userResponse) => {
        setUser(response);
    };

    const handleLogout = async () => {
        try {
            await logout();
            setUser(null);
            localStorage.removeItem('cart');
        } catch (err) {
            console.error('Logout error:', err);
        }
    };

    if (checkingSession) return <p className="text-center mt-5">Ładowanie...</p>;

    return (
        <AuthProvider user={user}>
            <Router>
                {user && <Navbar onLogout={handleLogout} />}
                <Routes>
                    <Route
                        path="/"
                        element={user ? <MainPage /> : <Navigate to="/login" replace />}
                    />
                    <Route
                        path="/cart"
                        element={user ? <Cart /> : <Navigate to="/login" replace />}
                    />
                    <Route
                        path="/checkout"
                        element={user ? <CheckoutForm /> : <Navigate to="/login" replace />}
                    />
                    <Route
                        path="/order-summary"
                        element={user ? <OrderSummary /> : <Navigate to="/login" replace />}
                    />
                    <Route
                        path="/login"
                        element={
                            !user ? (
                                <LoginForm onLoginSuccess={handleLogin} />
                            ) : (
                                <Navigate to="/" replace />
                            )
                        }
                    />
                    <Route
                        path="/register"
                        element={
                            !user ? (
                                <RegisterForm onRegisterSuccess={handleLogin} />
                            ) : (
                                <Navigate to="/" replace />
                            )
                        }
                    />
                    <Route
                        path="/stripe/success"
                        element={<div>clap clap komponent tutaj końcowy z podsumowaniem zamówienia proszę</div>}
                    />
                    <Route
                        path="/stripe/cancel"
                        element={<div>boohoo stripe failed or u did something wrong component here</div>}
                    />
                    <Route
                        path="*"
                        element={<Navigate to={user ? '/' : '/login'} replace />}
                    />
                </Routes>
            </Router>
        </AuthProvider>
    );
}