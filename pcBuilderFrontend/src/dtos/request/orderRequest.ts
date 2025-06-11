import type {recipient} from "../both/recipient.ts";
import type {shipmentMethod} from "../../enums/shipmentMethod.ts";
import type {orderItemRequest} from "./orderItemRequest.ts";

export interface orderRequest {
    userID: string;
    recipient: recipient;
    shipmentMethod: shipmentMethod;
    products: orderItemRequest[];
}