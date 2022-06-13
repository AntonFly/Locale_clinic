import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../_services';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isExpanded = false;
  
  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit() {
  }  

  collapse() {
    this.isExpanded = false;
  }

  toggle() {
    this.isExpanded = !this.isExpanded;
  }

  getUser(){

    var juser = localStorage.getItem("currentUser");
    if(juser){
      var User = JSON.parse(juser);
      
      return User.name+'/'+User.role;
    }
    else { return undefined; }
    
  }

  quit(){
    this.authenticationService.logout();
  }
}
