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
    let body = {"personData":personData, "role":user.role, "email":user.email, "password":user.password};
    
    let headers = new HttpHeaders({
        'Content-Type': 'application/json'});
      let options = { headers: headers };
    
  
      return this.http.post<any>(this.baseUrl + `admin/create_user`,  JSON.stringify(body), options);
    // let headers = new HttpHeaders({
    // 'Content-Type': 'application/x-www-form-urlencoded'});
    // let options = { headers: headers };

    // var body = 'login=' + username + '&password=' + password;

    // return this.http.post<any>(`http://localhost:8080/MultHubnew_war_exploded/resources/user/signIn`, body/*{ username: username, password: password }*/, options)
    //   .pipe(map(user => {
    //     console.log(user);
    //     if (user && user.login) {
    //       localStorage.setItem('currentUser', JSON.stringify(user));
    //     }
    //     return user;
    //   }));
  }

  logout() {
    // remove user from local storage to log user out
    this.router.navigate(['login']);
    localStorage.removeItem('currentUser');    
    console.log('Logged out');
  }
}
