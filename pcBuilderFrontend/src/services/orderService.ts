import type { orderRequest } from "../dtos/request/orderRequest.ts";
import type {orderResponse} from "../dtos/response/orderResponse.ts";
import axiosInstance from "../axiosConfig.ts";

export const createOrder = async (order: orderRequest): Promise<orderResponse> => {
    try {
        const response = await axiosInstance.post<orderResponse>('/orders', order);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const getOrder = async (id: number): Promise<orderResponse> => {
    try {
        const response = await axiosInstance.get<orderResponse>(`/orders/${id}`);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const updateOrder = async (id: number, order: orderRequest): Promise<orderResponse> => {
    try {
        const response = await axiosInstance.put<orderResponse>(`/orders/${id}`, order);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const patchOrder = async (id: number, order: Partial<orderRequest>): Promise<orderResponse> => {
    try {
        const response = await axiosInstance.patch<orderResponse>(`/orders/${id}`, order);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const deleteOrder = async (id: number): Promise<void> => {
    try {
        await axiosInstance.delete(`/orders/${id}`);
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};