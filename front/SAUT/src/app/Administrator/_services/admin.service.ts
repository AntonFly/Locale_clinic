import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';


@Injectable()
export class AdminService {
  baseUrl: string;
  constructor(private http: HttpClient, private router: Router, @Inject('BASE_URL') baseUrl: string) { this.baseUrl = baseUrl; }

  addUser(user: any) {
    const personData = {'passport': user.passport, 'name': user.name, 'surname': user.surname,
      'patronymic': user.patronymic, 'dateOfBirth': user.dateOfBirth };
    const body = {'person': personData, 'role': user.role, 'email': user.email, 'password': user.password};

    const headers = new HttpHeaders({
        'Content-Type': 'application/json'});
    const options = { headers: headers };

      console.log('user:' + JSON.stringify(body));
    return this.http.post<any>(this.baseUrl + `admin/create_user`,  JSON.stringify(body), options);
  }

  getToRecover(): Observable<any> {
    return  this.http.get<any>(this.baseUrl + `admin/get_all_drop_requests`);

  }

  generatePass(data): Observable<any> {
    return this.http.put<any>(this.baseUrl + `admin/satisfy_pwd_drop_request`,  {'id' : 1});
  }
}
