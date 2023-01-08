import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../_services';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    var authToken = undefined;
    
    if(this.authenticationService.token)
    {
        authToken = this.authenticationService.token.token;
    
        const authReq = req.clone({
            headers: req.headers.set('Authorization', authToken)
        });
    
        return next.handle(authReq);
    }
    else return next.handle(req);
  }
}
