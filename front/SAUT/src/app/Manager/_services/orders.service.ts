import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import { Order, Mod, Spec } from '../../_models/Order';


@Injectable()
export class OrderService {
    baseUrl: string;

    constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
        this.baseUrl = baseUrl;                 
     }

    getAllOrders() {
        return this.http.get<Order[]>(this.baseUrl+"manager/get_orders");        
    }

    getSpecializations(){
        return this.http.get<Spec[]>(this.baseUrl+"manager/get_specs");
    }

    getModifications(){
        return this.http.get<Mod[]>(this.baseUrl+"manager/get_mods");
    }

    getModsBySpec(spec:string){
        let body = "?specId=" + spec;
        return this.http.get<Mod[]>(this.baseUrl+"manager/get_mods_by_spec/"+body);
    }

    createOrder(request: any, mods: string[]) {        
        let body = {"passport":request.passport, "specName":request.Specialization, "comment":request.comment, "modNames":mods};
    
        let headers = new HttpHeaders({
        'Content-Type': 'application/json'});
        let options = { headers: headers };
        
        console.log(JSON.stringify(body));
        return this.http.post<any>(this.baseUrl + `manager/create_order`,  JSON.stringify(body), options);            
    }
}