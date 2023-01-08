import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router, CanActivateChild } from '@angular/router';
import { Observable } from 'rxjs';
import { retry, takeWhile } from 'rxjs/operators';
import { AuthenticationService } from '../_services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate//, CanActivateChild
{
  constructor(private authService: AuthenticationService, private router: Router) { }

//   canActivateChild (
//     next: ActivatedRouteSnapshot,
//     state: RouterStateSnapshot
//   ): boolean | UrlTree | Promise<boolean | UrlTree>
//   {    
    // return new Promise((resolve) => {
    //   this.authService.user$.pipe(
    //     takeWhile(user => user === null),
    //   ).subscribe(
    //     {
    //       complete: () =>
    //       {
    //         var user = this.authService.user;

    //         if (!user.isValid())
    //           return resolve(this.router.parseUrl("/autherr"));

    //         if (user.IsAdmin)
    //           resolve(true);

    //         var redirect = "";
    //         switch (user.Process)
    //         {
    //           case "Security":
    //             redirect = "/upload/security";
    //             break;
    //           case "Corp":
    //             redirect = "/upload/corp";
    //             break;
    //           default:
    //             resolve(this.router.parseUrl("/autherr"));
    //         }

    //         if (state.url != redirect)
    //           resolve (this.router.parseUrl(redirect));
    //         else resolve(true);
    //       }
    //     }
    //   )
    // })    
//   }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return new Promise(
        (resolve) => 
        {
          // this.authService.token$.pipe(
          //   takeWhile(user => user === null),
          // ).subscribe(
          //   {
          //     complete: () =>
          //     {
            // var storUser = localStorage.getItem("currentUser");
            // var user;
                // console.log("GUUUUARD");

            var user = this.authService.token;
            if(!user)
                return resolve(this.router.parseUrl("/login"));            
            
            var redirect = "";
            switch (user.roles[0])
            {
              case "ROLE_MEDIC":
                redirect = "medic";
                break;
              case "ROLE_ENGINEER":
                redirect = "engineer";
                break;
            case "ROLE_ADMIN":
                redirect = "admin";
                break;
            case "ROLE_MANAGER":
                redirect = "manager";
                break;
            case "ROLE_SCIENTIST":
                redirect = "scientist";
                break;
              default:
                resolve(this.router.parseUrl("/autherr"));
            }

            if (!state.url.includes(redirect))
              resolve (this.router.parseUrl("/roleErr"));
            else resolve(true);
          // }})
        }
    )      
  }
  
}
