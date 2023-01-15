import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import { Order } from 'src/app/_models/Order';

@Injectable()
export class SupportService {

  baseUrl:string;

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.baseUrl = baseUrl;
  }


  getSupportBySpecializId(orderId: string) {
    const body = '?orderId=' + orderId;
    return this.http.get<Order[]>(this.baseUrl+"medic/get_script_by_order/"+body);
  }

  getClient(pass: number) {
    let body = "?passport=" + pass;
    return this.http.get<Client>(this.baseUrl+"medic/get_client_by_passport/" + body);
  }

  getOrdersByPassport(id: number) {
    let body = "?passport="+id;
    return this.http.get<Order[]>(this.baseUrl+"medic/get_orders_by_client_passport/"+body);
  }
}
