import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import {map} from 'rxjs/operators';
import {FnParam} from '@angular/compiler/src/output/output_ast';

@Injectable()
export class ClientsService {
    zatychka: Client[];
    curId = 0;
    constructor(private http: HttpClient) {
        this.zatychka = [{Id:this.curId++,FIO:"Вася петькин",Date:"12.12.1999",Email:"me@nowhere.wow", Note:"Crazy dude", Password:"PSWD313"},
        {Id:this.curId++,FIO:"Петя Васькин",Date:"12.10.1990",Email:"хм@yandex.lmao", Note:"Even crazierCrazy dude", Password:"PSWD313"}]
     }

    getAll() {
        //return this.http.get<Client[]>(`/users`);
        return this.zatychka
    }

    createClient(client: Client) {
        // let headers = new HttpHeaders({
        //   'Content-Type': 'application/x-www-form-urlencoded'});
        // let options = { headers: headers };
        // var body = "name=" + user.name + "&surname=" + user.surName + "&login=" + user.login + "&password=" + user.password;
    
        var tmp = client;
        tmp.Id = this.curId++;
        // return this.http.post(`http://localhost:8080/MultHubnew_war_exploded/resources/user/signUp`,  body, options);
        this.zatychka.push(client);
      }
}