import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Token } from './_models/User';
import { AuthenticationService } from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'SAUT';
  role : string = undefined;

  constructor (public router: Router, private authenticationService: AuthenticationService){
    authenticationService.token$.subscribe(
      token =>
      {
        if(token)        
          this.role = token.roles[0];
        else this.role = undefined;
      }
    )
  }

  getUserRole()
  {    
    var juser = localStorage.getItem("currentUser");
    if(juser){
      var User = JSON.parse(juser);
      
      return User.role;
    }
    else { return undefined; }
  }
}
