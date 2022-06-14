import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';


@Injectable()
export class AdminService {
  baseUrl: string;
  constructor(private http: HttpClient, private router: Router, @Inject('BASE_URL') baseUrl: string) { this.baseUrl = baseUrl; }

  AddUser(user: any) {    
    
    let personData = {"passport":user.passport, "name":user.name, "surname":user.surname, "patronymic":user.patronymic, "dateOfBirth": user.dateOfBirth };
    let body = {"person":personData, "role":user.role, "email":user.email, "password":user.password};
    
    let headers = new HttpHeaders({
        'Content-Type': 'application/json'});
    let options = { headers: headers };
    
      console.log("user:"+JSON.stringify(body));
    return this.http.post<any>(this.baseUrl + `admin/create_user`,  JSON.stringify(body), options);    
  }
  
}
