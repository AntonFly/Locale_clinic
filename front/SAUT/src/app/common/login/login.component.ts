import {Component, Inject, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {AuthenticationService} from '../../_services'
import { Router } from '@angular/router';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RecoverDialogComponent} from '../../Administrator/users/recover-dialog/recover-dialog.component';

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
    'username':[
      {type: 'required', message:'Укажите email'}
    ],
    'password':[
      {type: 'required', message:'Укажите пароль'}
    ]
  };

  retried: number = 0;

  constructor(
    private authenticationService: AuthenticationService,
    private fb: FormBuilder,
    public router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.createForms();
  }

  get formControlEmail() {
    return this.UserForm.get('username');
  }
  get formControlPass() {
    return this.UserForm.get('password');
  }

  get formControlEvent() {
    return this.fileDetailsForm.get('eventType');
  }

  createForms() {
    this.fileDetailsForm = this.fb.group({
      eventType: new FormControl(this.eventTypes[this.eventTypes[0]], Validators.required)
    });

    this.UserForm = this.fb.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
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

  openDialog() {
    const dialogRef = this.dialog.open
      (
        ResetPWDDialogComponent, {
          hasBackdrop: true,
          width: '30%',
          // height:'27.78%',
          data: this.formControlEmail
        },
      );

  }

  onEmailChange() { this.retried = 0; }

}


  @Component({
    selector: 'app-reset-pwd-dialog',
    templateUrl: 'reset-pwd-dialog.html',
    styleUrls: ['./login.component.css']
  })
  export class ResetPWDDialogComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<ResetPWDDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private authenticationService: AuthenticationService) {}
  send: boolean = null;
  formMsg: String = '';

  ngOnInit() {
      console.log(this.data.value);
    }

    resetPwd() {
      this.authenticationService.resetPWD(this.data.value).subscribe(
          res => {
            console.log(res);
            this.send = true;
            this.formMsg = 'Запрос на сброс пароля успешно отправлен, ' +
              'после сброса пароля на указанную почту прийдет новый пароль.';
            setTimeout
            (
              () => this.dialogRef.close(),
              3000
            );
          },
          error => {
            console.log(error);
            this.send = false;
            this.formMsg = 'При сбросе пароля произошла ошибка!';

            setTimeout
            (
              () => this.dialogRef.close(),
              3000
            );
          }

        );
    }
}
