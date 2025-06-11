import axiosInstance from "../axiosConfig.ts";
import type {paymentRequest} from "../dtos/request/paymentRequest.ts";
import type {paymentResponse} from "../dtos/response/paymentResponse.ts";

export const createCheckoutSession = async (paymentRequest: paymentRequest): Promise<paymentResponse> => {
    try {
        const response = await axiosInstance.post<paymentResponse>('/create-checkout-session', paymentRequest);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};