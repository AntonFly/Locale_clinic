import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of } from "rxjs";
import { Router } from '@angular/router';


@Injectable()
export class AdminService {
  baseUrl: string;
  constructor(private http: HttpClient, private router: Router, @Inject('BASE_URL') baseUrl: string) { this.baseUrl = baseUrl; }

  addUser(user: any) 
  {
    
    let personData = {"passport":user.passport, "name":user.name, "surname":user.surname, "patronymic":user.patronymic, "dateOfBirth": user.dateOfBirth };
    let body = {"person":personData, "role":user.role, "email":user.email, "password":user.password};
    
    let headers = new HttpHeaders({
        'Content-Type': 'application/json'});
    let options = { headers: headers };
    
      console.log("user:"+JSON.stringify(body));
    return this.http.post<any>(this.baseUrl + `admin/create_user`,  JSON.stringify(body), options);    
  }

  getToRecover():Observable<any>
  {
    var data = [
      {email: "myEmail@mail.ru", name:"Иван", surname:"Давыдов", patronymic:"Денисыч", dateOfBirth:"12.12.1999", request_date:"13.12.2022"},
      {email: "ффф@mail.ru", name:"Антон", surname:"Авраменко", patronymic:"Денисыч", dateOfBirth:"12.04.1999", request_date:"13.12.2022"},
    ]
    return of(data);
  }
  
  generatePass(data):Observable<any>
  {
    var resp = {status: "success"};
    
    return of(resp);
  }
}
