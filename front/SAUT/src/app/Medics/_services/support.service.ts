import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import { Request } from '../../_models/Request';
import {map} from 'rxjs/operators';
import {FnParam} from '@angular/compiler/src/output/output_ast';
import { Order } from 'src/app/_models/Order';

@Injectable()
export class SupportService {
    zatychka: Order[] = [{clientId:1,FIO:'Anton Avramenko', SpezId:1, SpezName:'Мастер-Пилот'},{clientId:1,FIO:'Anton Avramenko', SpezId:2, SpezName:'Пилот'}];
    curId = 0;
    constructor(private http: HttpClient) {
     }

     getClientById(id:string) : Order[]{
    //    this.zatychka.push({clientId:1,FIO:'Anton Avramenko', SpezId:1, SpezName:'Мастер-Пилот'});
        return [{clientId:Number(id),FIO:'Anton Avramenko', SpezId:1, SpezName:'Мастер-Пилот'},{clientId:Number(id),FIO:'Anton Avramenko', SpezId:2, SpezName:'Пилот'}];//this.zatychka;
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