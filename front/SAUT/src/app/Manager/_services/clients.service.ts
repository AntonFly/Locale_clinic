import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import { Client, Person } from '../../_models/Client';
import {map} from 'rxjs/operators';
import {FnParam} from '@angular/compiler/src/output/output_ast';

@Injectable()
export class ClientsService {    
    baseUrl: string;

    constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
        this.baseUrl = baseUrl;         
     }

    getAll() {
        return this.http.get<Client[]>(this.baseUrl+"manager/get_clients");        
    }

    createClient(client: any) {
        let personData = {"passport":client.passport, "name":client.name, "surname":client.surname, "patronymic":client.patronymic, "dateOfBirth": client.dateOfBirth };
        let body = {"person":personData, "email":client.email, "comment":client.comment};
    
        let headers = new HttpHeaders({
        'Content-Type': 'application/json'});
        let options = { headers: headers };
        
        console.log(JSON.stringify(body));
        return this.http.post<any>(this.baseUrl + `manager/create_client`,  JSON.stringify(body), options);            
    }

    getClient(pass: number) {
        let body = "?passport=" + pass;
        return this.http.get<Client>(this.baseUrl+"manager/get_client_by_passport/" + body);                
    }
}