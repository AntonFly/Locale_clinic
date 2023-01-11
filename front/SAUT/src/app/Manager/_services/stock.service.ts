import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
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
    

}