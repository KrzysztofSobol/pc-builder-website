import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ShoppingCart, Trash2 } from 'lucide-react';
import type { product } from "../dtos/both/product.ts";

interface CartItem extends product {
    quantity: number;
}

export default function Cart() {
    const navigate = useNavigate();
    const [cart, setCart] = useState<CartItem[]>(() => {
        const savedCart = localStorage.getItem('cart');
        return savedCart ? JSON.parse(savedCart) : [];
    });

    useEffect(() => {
        localStorage.setItem('cart', JSON.stringify(cart));
    }, [cart]);

    const removeFromCart = (productId: number) => {
        setCart(prev => prev.filter(item => item.productID !== productId));
    };

    const updateQuantity = (productId: number, newQuantity: number) => {
        if (newQuantity < 1) return;
        setCart(prev =>
            prev.map(item =>
                item.productID === productId
                    ? { ...item, quantity: Math.min(newQuantity, item.stock) }
                    : item
            )
        );
    };

    const clearCart = () => {
        setCart([]);
    };

    const totalPrice = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div className="container py-4">
            <h1 className="display-6 fw-bold mb-4">Koszyk</h1>
            {cart.length === 0 ? (
                <div className="text-center py-5">
                    <ShoppingCart className="text-muted mb-3" size={48} />
                    <p className="text-muted">Twój koszyk jest pusty</p>
                </div>
            ) : (
                <>
                    <div className="mb-4">
                        {cart.map(item => (
                            <div key={item.productID} className="card mb-3">
                                <div className="card-body d-flex align-items-center">
                                    <img
                                        src={item.imageUrl || 'https://placehold.co/100x100'}
                                        alt={item.name}
                                        className="me-3"
                                        style={{width: '100px', height: '100px', objectFit: 'cover'}}
                                    />
                                    <div className="flex-grow-1">
                                        <h5 className="mb-1">{item.name}</h5>
                                        <p className="text-muted mb-2">{item.price.toFixed(2)} zł/szt.</p>
                                        <div className="d-flex align-items-center">
                                            <button
                                                className="btn btn-outline-secondary btn-sm me-2"
                                                onClick={() => updateQuantity(item.productID, item.quantity - 1)}
                                                disabled={item.quantity <= 1}
                                            >
                                                -
                                            </button>
                                            <span className="mx-2">{item.quantity}</span>
                                            <button
                                                className="btn btn-outline-secondary btn-sm me-2"
                                                onClick={() => updateQuantity(item.productID, item.quantity + 1)}
                                                disabled={item.quantity >= item.stock}
                                            >
                                                +
                                            </button>
                                            <button
                                                className="btn btn-outline-danger btn-sm"
                                                onClick={() => removeFromCart(item.productID)}
                                            >
                                                <Trash2 size={16}/>
                                            </button>
                                        </div>
                                    </div>
                                    <div className="text-end">
                                        <p className="fw-bold mb-0">{(item.price * item.quantity).toFixed(2)} zł</p>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                    <div className="d-flex justify-content-between align-items-center w-100">
                        <button className="btn btn-outline-danger" onClick={clearCart}>
                            Wyczyść koszyk
                        </button>

                        <div className="ms-auto text-end">
                            <h4>Razem: {totalPrice.toFixed(2)} zł</h4>
                            <button
                                className="btn btn-primary"
                                onClick={() => navigate('/checkout')}
                            >
                                Zamów
                            </button>
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}