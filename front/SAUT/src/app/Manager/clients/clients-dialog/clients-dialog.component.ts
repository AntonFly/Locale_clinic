import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ClientsService} from '../../_services/clients.service'
import {Client} from '../../../_models/Client'

@Component({
  selector: 'app-clients-dialog',
  templateUrl: './clients-dialog.component.html',
  styleUrls: ['./clients-dialog.component.css']
})
export class ClientsDialogComponent implements OnInit {

  ClientForm: FormGroup;

  validation_messages = {
    'FIO': [
      { type: 'required', message: 'Требуется Ф.И.О.' }
    ],

    'Email': [
      { type: 'required', message: 'Требуется Email' },
      { type: 'pattern', message: 'Формат: __@__.__' }
    ],

    'Passport': [
      { type: 'required', message: 'Требуются данные паспорта' }
    ],
    
    'eventType': [
      { type: 'required', message: 'Пожалуйста, кажите тип события' },
    ],
    'Date': [
      { type: 'required', message: 'Пожалуйста, укажите дату рождения' },
    ]    
  };

  constructor( private dialogRef: MatDialogRef<ClientsDialogComponent>,  private fb: FormBuilder, private clientService: ClientsService) { }

  ngOnInit() {
    this.createForms();    
  }

  
  get formControlFIO() {
    return this.ClientForm.get('FIO');
  }

  get formControlEmail() {
    return this.ClientForm.get('Email');
  }

  get formControlPassport() {
    return this.ClientForm.get('Passport');
  }

  get formControlEvent() {
    return this.ClientForm.get('eventType');
  }

  get formControlDate() {
    return this.ClientForm.get('date');
  }

  createForms() {
    this.ClientForm = this.fb.group({
      FIO: ['', Validators.required],
      Email : new FormControl('', Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ])),
      Passport: ['', Validators.required],
      Date: ['', Validators.required],
      Note:['', Validators.maxLength]
      // eventType: new FormControl('', Validators.required)
    })
  }

  onSubmitClient(event: any) {
    console.log(event);
    this.clientService.createClient(event);
    
    // this.uploadCorpService.postData(event)
    //   .subscribe(
    //     (data: any) => {
    //       console.log(JSON.stringify(data));
    //       this.delete.emit(this.fileObj);
    //     },
    //     error => console.log(error)
    //   );
  }

}
