import {Navigate, useLocation} from 'react-router-dom';
import { useState } from 'react';
import type { orderResponse } from '../dtos/response/orderResponse';
import { createCheckoutSession } from '../services/stripeService';
import type { paymentRequest } from '../dtos/request/paymentRequest';

export default function OrderSummary() {
    const location = useLocation();
    const order = location.state?.order as orderResponse;
    const [error, setError] = useState<string | null>(null);
    const [isProcessing, setIsProcessing] = useState(false);

    if (!order) {
        return <Navigate to="/checkout" replace />;
    }

    const handlePayment = async () => {
        setError(null);
        setIsProcessing(true);

        try {
            const paymentRequest: paymentRequest = {
                orderId: order.orderID,
                currency: "usd"
            };

            const response = await createCheckoutSession(paymentRequest);
            window.location.href = response.sessionUrl;
        } catch (err) {
            setError('Wystąpił błąd podczas inicjowania płatności. Spróbuj ponownie.');
            console.error(err);
        } finally {
            setIsProcessing(false);
        }
    };

    return (
        <div className="container py-4">
            <h1 className="display-6 fw-bold mb-4">Podsumowanie zamówienia</h1>

            <div className="row">
                <div className="col-md-8">
                    <div className="card mb-4">
                        <div className="card-body">
                            <h5 className="card-title mb-4">Szczegóły zamówienia</h5>
                            <p><strong>Numer zamówienia:</strong> {order.orderID}</p>
                            <p><strong>Data zamówienia:</strong> {new Date(order.orderDate).toLocaleDateString()}</p>
                            <p><strong>Odbiorca:</strong> {order.recipient.fullName}</p>
                            <p><strong>Email:</strong> {order.recipient.email}</p>
                            <p><strong>Adres:</strong> {order.recipient.zipCode} {order.recipient.city}</p>
                            <p><strong>Telefon:</strong> {order.recipient.phoneNumber}</p>
                        </div>
                    </div>

                    <div className="card mb-4">
                        <div className="card-body">
                            <h5 className="card-title mb-4">Produkty</h5>
                            {order.items.map(item => (
                                <div key={item.productID} className="d-flex justify-content-between mb-2">
                                    <div>
                                        <span>{item.productName} (x{item.quantity})</span>
                                        {item.imageUrl && (
                                            <img src={item.imageUrl} alt={item.productName} className="img-thumbnail ms-2" style={{ width: '50px' }} />
                                        )}
                                    </div>
                                    <span>{item.subtotal.toFixed(2)} zł</span>
                                </div>
                            ))}
                            <hr />
                            <div className="d-flex justify-content-between fw-bold">
                                <span>Razem:</span>
                                <span>{order.totalPrice.toFixed(2)} zł</span>
                            </div>
                        </div>
                    </div>

                    {error && (
                        <div className="alert alert-danger" role="alert">
                            {error}
                        </div>
                    )}

                    <div className="d-flex justify-content-end">
                        <button
                            className="btn btn-primary"
                            onClick={handlePayment}
                            disabled={isProcessing}
                        >
                            {isProcessing ? 'Przetwarzanie...' : 'Zapłać'}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}