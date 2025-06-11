import type {recipient} from "../both/recipient.ts";
import type {orderItemResponse} from "./orderItemResponse.ts";

export interface orderResponse {
    orderID: number;
    userID: string;
    username: string;
    recipient: recipient;
    orderDate: string;
    items: orderItemResponse[];
    totalPrice: number;
}