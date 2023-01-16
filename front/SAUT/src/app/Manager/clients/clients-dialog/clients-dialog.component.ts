import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ClientsService} from '../../_services/clients.service'
import { DatePipe } from '@angular/common';
import {Client} from '../../../_models/Client'

@Component({
  selector: 'app-clients-dialog',
  templateUrl: './clients-dialog.component.html',
  styleUrls: ['./clients-dialog.component.css']
})
export class ClientsDialogComponent implements OnInit {

  ClientForm: FormGroup;
  formMsg: string;
  formError: boolean = null;
  sendSuccessfully = false;

  validation_messages = {
    'name': [
      { type: 'required', message: 'Требуется имя' }
    ],
    'surname': [
      { type: 'required', message: 'Требуется фамилия' }
    ],
    'patronymic': [
      { type: 'required', message: 'Требуется отчество' }
    ],
    'email': [
      { type: 'required', message: 'Требуется Email' },
      { type: 'pattern', message: 'Формат: __@__.__' }
    ],

    'passport': [
      { type: 'required', message: 'Требуются данные паспорта' }
    ],

    'dateOfBirth': [
      { type: 'required', message: 'Пожалуйста, укажите дату рождения' },
    ]
  };

  myDatepipe: DatePipe;

  constructor( private dialogRef: MatDialogRef<ClientsDialogComponent>,
    private fb: FormBuilder,
    private clientService: ClientsService,
    datepipe: DatePipe) {
      this.myDatepipe = datepipe;
    }

  ngOnInit() {
    this.createForms();
    this.formMsg = "";
    this.formError = false;
  }


  get formControlName() {
    return this.ClientForm.get('name');
  }

  get formControlSurname() {
    return this.ClientForm.get('surname');
  }

  get formControlPatronymic() {
    return this.ClientForm.get('patronymic');
  }
  get formControlEmail() {
    return this.ClientForm.get('email');
  }

  get formControlPassport() {
    return this.ClientForm.get('passport');
  }

  get formControlDate() {
    return this.ClientForm.get('dateOfBirth');
  }

  createForms() {
    this.ClientForm = this.fb.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      patronymic: ['', Validators.required],
      email : new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ])),
      passport: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      comment:['', Validators.maxLength]
    })
  }

  onSubmitClient(event: any) {
    event.dateOfBirth = this.myDatepipe.transform(this.ClientForm.value.dateOfBirth, 'yyyy-MM-dd');
    console.log(event);
    this.clientService.createClient(event).subscribe(
      (data: any) => {
        console.log(data);
        this.formMsg = "Пользователь успешно добавлен";
        this.formError = false;
        this.sendSuccessfully = true;

        setTimeout( () => {
            this.formMsg = "";
            this.dialogRef.close();
          }
          , 3000
        );

      },

      error => {
        console.log(error);
        this.formError = true;
          if(error.error.status === "CONFLICT")
            this.formMsg = error.error.message;
          else
          this.formMsg = "Не удалось добавить пользователя";

          setTimeout(() => {
            this.formMsg = "";
            this.formError = false;
          }, 5000);
      }
    );

  }

}
