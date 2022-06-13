import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {AuthenticationService} from '../../_services'
import { User } from '../../_models/User'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  fileDetailsForm: FormGroup;   

    eventTypes = [
    "Администратор",
    "Менеджер",
    "Медик"
  ]

  validation_messages = {    
    'eventType': [
      { type: 'required', message: 'Выберете роль' },
    ],
    
  };

  constructor(private authenticationService: AuthenticationService, private fb: FormBuilder) {}

  ngOnInit() {
    this.createForms();
  }

  get formControlEvent() {
    return this.fileDetailsForm.get('eventType');
  }

  createForms() {
    this.fileDetailsForm = this.fb.group({      
      eventType: new FormControl(this.eventTypes[this.eventTypes[0]], Validators.required)
    })
  }
  
  onSubmitFileDetails(event: any) {
    console.log(event);
    
    this.authenticationService.login(
      {
        id: 1,
        email: "divand@divand.divand",
        name: "Ivan",
        surname: "Davydov",
        patronymic: "Denisovich",
        role: event.eventType,
        password: "xd313"
      });      
  }

  logout($event){
    this.authenticationService.logout();
  }

}
