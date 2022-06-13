import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'SAUT';

  getUserRole(){    
    
    var juser = localStorage.getItem("currentUser");
    if(juser){
      var User = JSON.parse(juser);
      
      return User.role;
    }
    else { return undefined; }
  }
}
