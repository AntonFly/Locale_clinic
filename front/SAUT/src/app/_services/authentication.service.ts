import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { User, Token } from '../_models/User';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable()
export class AuthenticationService {
  private _token: BehaviorSubject<Token> = new BehaviorSubject<Token>(null);
  public readonly token$: Observable<Token> = this._token.asObservable();
  baseUrl: string;
  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject('BASE_URL') baseUrl: string
    ) {
    this.renewToken();
    this.baseUrl = baseUrl;
  }
  renewToken() {
    let token = localStorage.getItem('token');
    if(token)
      this._token.next(JSON.parse(atob(token)));
    
  }
  login(user) {// : User) {

    // console.log('Logged in with '+user);
    // localStorage.setItem('currentUser', JSON.stringify(user) );
    // let headers = new HttpHeaders({
    // 'Content-Type': 'application/x-www-form-urlencoded'});
    // let options = { headers: headers };

    // var body = 'login=' + username + '&password=' + password;

    return this.http.post(this.baseUrl + 'auth/sign_in', user)// .subscribe(res=>{console.log(res)})
      .pipe( map(
        (token: any) => {
          this._token.next(token);
          localStorage.setItem('token', btoa(JSON.stringify(token)));
          return token.roles[0];
        }
      ));
  }

  logout() {
    // remove user from local storage to log user out
    this.router.navigate(['login']);
    this._token.next(undefined);
    localStorage.removeItem('currentUser');
    console.log('Logged out');
  }

  get token(): Token { return this._token.value; }

  get id(){ return this._token.value.id;}

  resetPWD(email: any) {

    return this.http.post(this.baseUrl + 'user/create_pwd_drop_request', { 'email' : email } );

  }
}
