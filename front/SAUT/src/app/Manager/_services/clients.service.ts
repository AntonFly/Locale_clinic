import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import { Client, Person } from '../../_models/Client';
import {map} from 'rxjs/operators';
import {FnParam} from '@angular/compiler/src/output/output_ast';
import { Mod } from 'src/app/_models/Order';

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

    updateClient(client: any, clientId: number){
        let personData = {"passport":client.passport, "name":client.name, "surname":client.surname, "patronymic":client.patronymic, "dateOfBirth": client.dateOfBirth };
        let clientData = {"person":personData, "email":client.email, "comment":client.comment};        

        return this.http.post(this.baseUrl + `manager/update_client/?clientId=`+clientId, clientData);
    }

    addPreviousModification(clientId, modIds:number[]){
        return this.http.post(this.baseUrl+"manager/add_previous_modification", {"clientId":clientId, "modIds":modIds})
    }

    getClient(pass: number) {
        let body = "?passport=" + pass;
        return this.http.get<Client>(this.baseUrl+"manager/get_client_by_passport/" + body);                
    }

    getModifications(){
        return this.http.get<Mod[]>(this.baseUrl+"manager/get_mods");
    }
}