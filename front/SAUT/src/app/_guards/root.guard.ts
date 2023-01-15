import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs';
import { retry, takeWhile } from 'rxjs/operators';
import { AuthenticationService } from '../_services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RootGuard implements CanActivate
{
    root_paths = {
        ROLE_MEDIC: "/medic/",
        ROLE_ENGINEER: "/engineer/",
        ROLE_ADMIN: "/admin/",
        ROLE_MANAGER: "/manager/",
        ROLE_SCIENTIST: "/scientists/"
    }

    constructor(private authService: AuthenticationService, private router: Router) { }

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return new Promise(
            (resolve) => 
            {                
                var user = this.authService.token;
                if(!user)
                    return resolve(this.router.parseUrl("/login"));            
                        
                resolve (this.router.parseUrl(this.root_paths[user.roles[0]]));                      
        }
    )      
  }
  
}
