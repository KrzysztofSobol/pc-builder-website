import ProductList from "./ProductList.tsx";

export default function MainPage() {
    return (
        <div className="min-vh-100 bg-light">
            <div className="container py-5">
                <h1 className="display-5 fw-bold">Witamy w sklepie komputerowym!</h1>
                <p className="lead">Tutaj możesz przeglądać produkty, tworzyć zamówienia i więcej.</p>
                <ProductList />
            </div>
        </div>
    );
}