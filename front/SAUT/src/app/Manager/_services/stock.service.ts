import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { forkJoin } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Client } from '../../_models/Client';
import { Order, Mod, Spec } from '../../_models/Order';


@Injectable()
export class StockService {
    baseUrl: string;

    constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
        this.baseUrl = baseUrl;                 
    }

    getAllStock(){
        return this.http.get(this.baseUrl+'stock/get_all_items')
    }

    submitStock(stock : any[]){
        var res = [];
        stock.forEach(el => {
            console.log("AAA")
            console.log(el);
            if(el.stockId)
            {
                res.push(this.updateStock(el));
            }
            else {
                res.push(this.createStock(el));
            }
        })

        return forkJoin(res);
    }

    createStock(stock){
        return this.http.post(this.baseUrl+'stock/create_item', stock);
    }
    
    updateStock(stock){
        return this.http.put(this.baseUrl+'stock/update_item', stock);
    }

}