import type {userResponse} from "./userResponse.ts";

export interface authResponse {
    token: string;
    user: userResponse;
}