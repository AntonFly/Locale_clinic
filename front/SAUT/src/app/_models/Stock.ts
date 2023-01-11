import { Client, User } from "./Client";

export interface Stock{
    stockId: StockId;
    vendorId: number;
    name: string;
    amount: number;
    description: string;
    minAmount: number;
    lastUpdateTime: string;
    user: User
}

export interface StockId{
    id: number;
    vendor: number;
}