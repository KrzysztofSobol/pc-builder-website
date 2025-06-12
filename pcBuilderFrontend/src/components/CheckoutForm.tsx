import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { orderRequest } from '../dtos/request/orderRequest';
import type { recipient } from '../dtos/both/recipient';
import type { shipmentMethod } from '../enums/shipmentMethod';
import { createOrder } from '../services/orderService';
import { useAuth } from '../AuthContext';

interface CartItem {
    productID: number;
    quantity: number;
    price: number;
    name: string;
}

export default function CheckoutForm() {
    const navigate = useNavigate();
    const { getUserId } = useAuth();
    const [cart] = useState<CartItem[]>(() => {
        const savedCart = localStorage.getItem('cart');
        return savedCart ? JSON.parse(savedCart) : [];
    });

    const [recipient, setRecipient] = useState<recipient>({
        fullName: '',
        email: '',
        zipCode: '',
        city: '',
        phoneNumber: ''
    });

    const [shipmentMethod, setShipmentMethod] = useState<shipmentMethod>('DEPARTMENT');
    const [error, setError] = useState<string | null>(null);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        if (name in recipient) {
            setRecipient(prev => ({ ...prev, [name]: value }));
        } else if (name === 'shipmentMethod') {
            setShipmentMethod(value as shipmentMethod);
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setIsSubmitting(true);

        const userId = getUserId();
        if (!userId) {
            setError('Brak zalogowanego użytkownika.');
            setIsSubmitting(false);
            return;
        }

        try {
            const order: orderRequest = {
                userID: userId,
                recipient,
                shipmentMethod,
                products: cart.map(item => ({
                    productID: item.productID,
                    quantity: item.quantity
                }))
            };

            const orderResponse = await createOrder(order);
            localStorage.removeItem('cart');
            navigate('/order-summary', { state: { order: orderResponse } });
        } catch (err) {
            setError('Wystąpił błąd podczas składania zamówienia. Spróbuj ponownie.');
            console.error(err);
        } finally {
            setIsSubmitting(false);
        }
    };

    const totalPrice = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div className="container py-4">
            <h1 className="display-6 fw-bold mb-4">Dane do zamówienia</h1>

            <div className="row">
                <div className="col-md-8">
                    <form onSubmit={handleSubmit}>
                        <div className="card mb-4">
                            <div className="card-body">
                                <h5 className="card-title mb-4">Dane odbiorcy</h5>
                                <div className="mb-3">
                                    <label htmlFor="fullName" className="form-label">Imię i nazwisko</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="fullName"
                                        name="fullName"
                                        value={recipient.fullName}
                                        onChange={handleInputChange}
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
                                        value={recipient.email}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="zipCode" className="form-label">Kod pocztowy</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="zipCode"
                                        name="zipCode"
                                        value={recipient.zipCode}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="city" className="form-label">Miasto</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="city"
                                        name="city"
                                        value={recipient.city}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="phoneNumber" className="form-label">Numer telefonu</label>
                                    <input
                                        type="tel"
                                        className="form-control"
                                        id="phoneNumber"
                                        name="phoneNumber"
                                        value={recipient.phoneNumber}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                            </div>
                        </div>

                        <div className="card mb-4">
                            <div className="card-body">
                                <h5 className="card-title mb-4">Metoda wysyłki</h5>
                                <select
                                    className="form-select"
                                    name="shipmentMethod"
                                    value={shipmentMethod}
                                    onChange={handleInputChange}
                                >
                                    <option value="DEPARTMENT">Odbiór w sklepie</option>
                                    <option value="INPOST">Paczkomat</option>
                                    <option value="COURIER">Kurier</option>
                                </select>
                            </div>
                        </div>

                        {error && (
                            <div className="alert alert-danger" role="alert">
                                {error}
                            </div>
                        )}

                        <div className="d-flex justify-content-end">
                            <button
                                type="submit"
                                className="btn btn-primary"
                                disabled={isSubmitting || cart.length === 0}
                            >
                                {isSubmitting ? 'Przetwarzanie...' : 'Zamów'}
                            </button>
                        </div>
                    </form>
                </div>

                <div className="col-md-4">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title mb-4">Podsumowanie zamówienia</h5>
                            {cart.map(item => (
                                <div key={item.productID} className="d-flex justify-content-between mb-2">
                                    <span>{item.name} (x{item.quantity})</span>
                                    <span>{(item.price * item.quantity).toFixed(2)} zł</span>
                                </div>
                            ))}
                            <hr />
                            <div className="d-flex justify-content-between fw-bold">
                                <span>Razem:</span>
                                <span>{totalPrice.toFixed(2)} zł</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}