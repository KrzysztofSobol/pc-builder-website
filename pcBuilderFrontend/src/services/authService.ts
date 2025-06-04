import type {loginRequest} from "../dtos/request/loginRequest.ts";
import type {authResponse} from "../dtos/response/authResponse.ts";
import axiosInstance from "../axiosConfig.ts";
import type {registerRequest} from "../dtos/request/registerRequest.ts";
import type {user} from "../dtos/response/user.ts";

export const login = async (request: loginRequest): Promise<authResponse> => {
    try {
        const response = await axiosInstance.post<authResponse>('/auth/login', request);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const register = async (request: registerRequest): Promise<authResponse> => {
    try {
        const response = await axiosInstance.post<authResponse>('/auth/register', request);
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
};

export const authMe = async (): Promise<user> => {
    try{
        const response = await axiosInstance.get<user>('/auth/me');
        return response.data;
    } catch (error: any) {
        console.log("error :P");
        throw error;
    }
}

