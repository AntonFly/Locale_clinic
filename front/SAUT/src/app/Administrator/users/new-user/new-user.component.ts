import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {AdminService} from '../../_services/admin.service'

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent implements OnInit {

  UserForm: FormGroup;   

  RolesToBd ={
    "Администратор":"ROLE_ADMIN",
    "Менеджер":"ROLE_MANAGER",
    "Медик":"ROLE_MEDIC",
    "Ученый":"ROLE_SCIENTIST",
    "Инженер":"ROLE_ENGINEER"
  }

  Roles = [
    "Администратор",
    "Менеджер",
    "Медик",
    "Ученый",
    "Инженер"
  ]

  validation_messages = {
    'Email': [
      { type: 'required', message: 'Требуется Email' },
      { type: 'pattern', message: 'Формат: __@__.__' }
    ],

    'Name': [
      { type: 'required', message: 'Требуется Имя' }
    ],

    'SurName': [
      { type: 'required', message: 'Требуется Фамилия' }
    ],
    
    'Patron': [
      { type: 'required', message: 'Пожалуйста, укажите отчество' },
    ],
    'Role': [
      { type: 'required', message: 'Пожалуйста, укажите роль' },
    ],
    'Password': [
      { type: 'required', message: 'Требуется пароль' },      
    ],
    'Passport': [
      { type: 'required', message: 'Требуется паспорт' },      
    ]
  };

  constructor(private fb: FormBuilder, private adminService:AdminService) { }

  ngOnInit() {
    this.createForms();
  }

    /*FORM's GETTERS*/
    get formControlEmail() {
      return this.UserForm.get('email');
    }
  
    get formControlName() {
      return this.UserForm.get('name');
    }
  
    get formControlSurName() {
      return this.UserForm.get('surname');
    }
  
    get formControlPatron() {
      return this.UserForm.get('patronymic');
    }
    
    get formControlRole() {
      return this.UserForm.get('role');
    }

    get formControlPassword() {
      return this.UserForm.get('password');
    }

    get formControlPassport() {
      return this.UserForm.get('password');
    }
  /*FORM's GETTERS*/
  
  
    createForms() {
      this.UserForm = this.fb.group({
        email : new FormControl('', Validators.compose([
          Validators.required,
          Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
        ])),
        name : ['', Validators.required],
        surname : ['', Validators.required],
        patronymic : ['', Validators.required],
        role : new FormControl(this.Roles[0], Validators.required),
        password : ['', Validators.required],
        passport : ['', Validators.required]  
      })
    }
  
    
  
    onSubmitClient(event: any) {      
      event.role = this.RolesToBd[event.role];
      console.log(event);
      this.adminService.AddUser(event).subscribe(
        (data: any) => {
          console.log(JSON.stringify(data));
          // this.delete.emit(this.fileObj);
        },
        error => console.log(error)
      );

      this.UserForm.reset();
      this.UserForm.clearValidators();
      
      
    }

}
