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
  hide: boolean = true;
  UserForm: FormGroup;

  formError: boolean = true;
  formMsg: string = 'JIASAS';

    eventTypes = [
    "Администратор",
    "Менеджер",
    "Медик"
  ]

  validation_messages = {    
    'eventType': [
      { type: 'required', message: 'Выберете роль' },
    ],
    'Email':[
      {type: 'required', message:''}
    ],
    'Pass':[
      {type: 'required', message:''}
    ]    
  };

  constructor(private authenticationService: AuthenticationService, private fb: FormBuilder) {}

  ngOnInit() {
    this.createForms();
  }

  get formControlEmail() {
    return this.UserForm.get('Email');
  }
  get formControlPass() {
    return this.UserForm.get('Pass');
  }

  get formControlEvent() {
    return this.fileDetailsForm.get('eventType');
  }

  createForms() {
    this.fileDetailsForm = this.fb.group({      
      eventType: new FormControl(this.eventTypes[this.eventTypes[0]], Validators.required)
    })

    this.UserForm = this.fb.group({
      Email: new FormControl('', Validators.required),
      Pass: new FormControl('', Validators.required),
    });
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

  onSubmitClient(data){
    console.log(data);
  }

}
