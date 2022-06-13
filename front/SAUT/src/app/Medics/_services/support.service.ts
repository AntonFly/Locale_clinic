import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import { Order } from 'src/app/_models/Order';

@Injectable()
export class SupportService {
    
    constructor(private http: HttpClient) {
     }

     getClientById(id:string){
    
    }

    getSupportBySpecializId(){

    }

    createRequest(request: Request) {
        // let headers = new HttpHeaders({
        //   'Content-Type': 'application/x-www-form-urlencoded'});
        // let options = { headers: headers };
        // var body = "name=" + user.name + "&surname=" + user.surName + "&login=" + user.login + "&password=" + user.password;
        console.log('request.service.ts\createRequest')
        // var tmp = client;
        // tmp.Id = this.curId++;
        // return this.http.post(`http://localhost:8080/MultHubnew_war_exploded/resources/user/signUp`,  body, options);
        // this.zatychka.push(client);
      }
}