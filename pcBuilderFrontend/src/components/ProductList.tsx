import { useState, useEffect } from 'react';
import { ShoppingCart, ChevronLeft, ChevronRight } from 'lucide-react';
import { getPagedProducts } from "../services/productService.ts";
import type { product } from "../dtos/response/product.ts";
import type { pagedResponse } from "../dtos/response/pagedResponse.ts";

interface CartItem extends product {
    quantity: number;
}

export default function ProductList() {
    const [products, setProducts] = useState<product[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [pageSize] = useState<number>(8);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [totalElements, setTotalElements] = useState<number>(0);
    const [cart, setCart] = useState<CartItem[]>(() => {
        const savedCart = localStorage.getItem('cart');
        return savedCart ? JSON.parse(savedCart) : [];
    });

    useEffect(() => {
        localStorage.setItem('cart', JSON.stringify(cart));
    }, [cart]);

    useEffect(() => {
        fetchProducts(currentPage);
    }, [currentPage]);

    const fetchProducts = async (page: number): Promise<void> => {
        if (pageCache.has(page)) {
            const cachedData = pageCache.get(page)!;
            setProducts(cachedData.content);
            setTotalPages(cachedData.totalPages);
            setTotalElements(cachedData.totalElements);
            return;
        }

        setLoading(true);
        try {
            const response = await getPagedProducts(page, pageSize);
            setProducts(response.content);
            setTotalPages(response.totalPages);
            setTotalElements(response.totalElements);
            setPageCache(prev => new Map(prev).set(page, response));
        } catch (error) {
            console.error('Error fetching products:', error);
        } finally {
            setLoading(false);
        }
    };

    // Cache
    const [pageCache, setPageCache] = useState<Map<number, pagedResponse<product>>>(new Map());

    const addToCart = (product: product): void => {
        setCart(prev => {
            const existingItem = prev.find(item => item.productID === product.productID);
            if (existingItem) {
                if (existingItem.quantity >= product.stock) {
                    return prev;
                }
                return prev.map(item =>
                    item.productID === product.productID
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            }
            if (product.stock > 0) {
                return [...prev, { ...product, quantity: 1 }];
            }
            return prev;
        });
    };

    const getCartItemCount = (productId: number): number => {
        const item = cart.find(item => item.productID === productId);
        return item ? item.quantity : 0;
    };

    const truncateDescription = (text: string, maxLength: number = 120): string => {
        if (text.length <= maxLength

        ) return text;
        return text.substring(0, maxLength) + '...';
    };

    const handleImageError = (e: React.SyntheticEvent<HTMLImageElement>): void => {
        e.currentTarget.src = 'https://placehold.co/300x200';
    };

    const goToPage = (page: number): void => {
        if (page >= 0 && page < totalPages) {
            setCurrentPage(page);
        }
    };

    return (
        <div className="container py-4">
            <div className="mb-4">
                <h1 className="display-6 fw-bold text-dark">Nasze Produkty</h1>
                <p className="text-muted">
                    Wyświetlamy {products.length} z {totalElements} produktów
                </p>
            </div>

            {loading && (
                <div className="d-flex justify-content-center py-5">
                    <div className="spinner-border text-primary" role="status">
                        <span className="visually-hidden">Ładowanie...</span>
                    </div>
                </div>
            )}

            {!loading && (
                <div className="row g-4 mb-4">
                    {products.map((product) => (
                        <div key={product.productID} className="col-12 col-sm-6 col-lg-4 col-xl-3">
                            <div className="card h-100 shadow-sm">
                                <div className="position-relative" style={{ height: '200px', overflow: 'hidden' }}>
                                    <img
                                        src={product.imageUrl || 'https://placehold.co/300x200'}
                                        alt={product.name}
                                        className="card-img-top w-100 h-100"
                                        style={{ objectFit: 'cover' }}
                                        onError={handleImageError}
                                    />
                                </div>
                                <div className="card-body d-flex flex-column">
                                    <h5 className="card-title fw-semibold text-truncate" title={product.name}>
                                        {product.name}
                                    </h5>
                                    <p className="card-text text-muted small flex-grow-1">
                                        {truncateDescription(product.description)}
                                    </p>
                                    <div className="d-flex justify-content-between align-items-center mb-3">
                                        <span className="h4 text-primary fw-bold mb-0">
                                            ${product.price.toFixed(2)}
                                        </span>
                                        <span className={`badge ${
                                            product.stock > 10
                                                ? 'bg-success'
                                                : product.stock > 0
                                                    ? 'bg-warning text-dark'
                                                    : 'bg-danger'
                                        }`}>
                                            {product.stock > 0 ? `${product.stock} szt.` : 'Brak'}
                                        </span>
                                    </div>
                                    <button
                                        onClick={() => addToCart(product)}
                                        disabled={product.stock === 0 || getCartItemCount(product.productID) >= product.stock}
                                        className={`btn w-100 d-flex align-items-center justify-content-center ${
                                            product.stock === 0 || getCartItemCount(product.productID) >= product.stock
                                                ? 'btn-outline-secondary disabled'
                                                : 'btn-primary'
                                        }`}
                                    >
                                        <ShoppingCart className="me-2" size={16} />
                                        {getCartItemCount(product.productID) > 0
                                            ? `Dodaj więcej (${getCartItemCount(product.productID)})`
                                            : product.stock === 0
                                                ? 'Niedostępny'
                                                : 'Dodaj do koszyka'
                                        }
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {!loading && totalPages > 1 && (
                <nav aria-label="Nawigacja produktów">
                    <ul className="pagination justify-content-center">
                        <li className={`page-item ${currentPage === 0 ? 'disabled' : ''}`}>
                            <button
                                className="page-link"
                                onClick={() => goToPage(currentPage - 1)}
                                disabled={currentPage === 0}
                                aria-label="Poprzednia strona"
                            >
                                <ChevronLeft size={16} />
                            </button>
                        </li>
                        {Array.from({ length: totalPages }, (_, i) => (
                            <li key={i} className={`page-item ${i === currentPage ? 'active' : ''}`}>
                                <button
                                    className="page-link"
                                    onClick={() => goToPage(i)}
                                >
                                    {i + 1}
                                </button>
                            </li>
                        ))}
                        <li className={`page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}`}>
                            <button
                                className="page-link"
                                onClick={() => goToPage(currentPage + 1)}
                                disabled={currentPage === totalPages - 1}
                                aria-label="Następna strona"
                            >
                                <ChevronRight size={16} />
                            </button>
                        </li>
                    </ul>
                </nav>
            )}
        </div>
    );
}