import type { product } from "../dtos/response/product.ts";
import axiosInstance from "../axiosConfig.ts";
import type {pagedResponse} from "../dtos/response/pagedResponse.ts";

export const createProduct = async (product: product): Promise<product> => {
    try {
        const response = await axiosInstance.post<product>('/products', product);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const getProduct = async (id: number): Promise<product> => {
    try {
        const response = await axiosInstance.get<product>(`/products/${id}`);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const getProducts = async (): Promise<product[]> => {
    try {
        const response = await axiosInstance.get<product[]>('/products');
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const getPagedProducts = async (page: number = 0, size: number = 10): Promise<pagedResponse<product>> => {
    try {
        const response = await axiosInstance.get<pagedResponse<product>>('/products/paged', {
            params: { page, size }
        });
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};


export const updateProduct = async (id: number, product: product): Promise<product> => {
    try {
        const response = await axiosInstance.put<product>(`/products/${id}`, product);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const patchProduct = async (id: number, product: Partial<product>): Promise<product> => {
    try {
        const response = await axiosInstance.patch<product>(`/products/${id}`, product);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const deleteProduct = async (id: number): Promise<void> => {
    try {
        await axiosInstance.delete(`/products/${id}`);
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};