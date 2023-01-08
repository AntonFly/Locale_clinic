import { Component, OnInit } from '@angular/core';
import { Token } from 'src/app/_models/User';
import {AuthenticationService} from '../../_services';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isExpanded = false;
  token : Token = undefined;

  constructor(private authenticationService: AuthenticationService) { 
    authenticationService.token$.subscribe(
      token =>
      {
        this.token = token;
      }
    )
  }

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
