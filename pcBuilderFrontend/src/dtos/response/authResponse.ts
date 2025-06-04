import type {user} from "./user.ts";

export interface authResponse {
    token: string;
    user: user;
}