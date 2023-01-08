import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {AuthenticationService} from '../../_services'
import { Router } from '@angular/router';
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

  formError: boolean = false;
  formMsg: string = '';

    eventTypes = [
    "Администратор",
    "Менеджер",
    "Медик",
    "Инженер",
    "Ученый"
  ];
  
  defaultRoutes = {
    "ROLE_ADMIN": "admin/addUser",
    "ROLE_MANAGER": "manager/clients",
    "ROLE_MEDIC": "medic/support",
    "ROLE_ENGINEER": "engineer/",
    "ROLE_SCIENTIST": "scientist/"
    };

  validation_messages = {    
    'eventType': [
      { type: 'required', message: 'Выберете роль' },
    ],
    'username':[
      {type: 'required', message:''}
    ],
    'password':[
      {type: 'required', message:''}
    ]    
  };

  retried: number = 0;

  constructor(
    private authenticationService: AuthenticationService, 
    private fb: FormBuilder,
    public router: Router) {}

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
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
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
    this.router.navigateByUrl(this.defaultRoutes[event.eventType]);
  }

  logout($event){
    this.authenticationService.logout();
  }

  onSubmitClient(data){
    console.log(data);
    this.authenticationService.login(data).subscribe(
      res => {
        console.log(res);
        this.router.navigateByUrl(this.defaultRoutes[res]);
      },
      error => {
        console.log(error)
        this.retried++;
        this.formMsg = "Неверные данные";
        this.formError = true;                

        setTimeout
        (
            () => this.formMsg = "",
            5000
        );
      }
      
    );
  }

}
