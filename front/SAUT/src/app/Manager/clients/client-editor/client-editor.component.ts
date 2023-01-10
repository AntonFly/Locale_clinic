import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { Client } from 'src/app/_models/Client';
import {ClientsService} from '../../_services/clients.service'
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-client-editor',
  templateUrl: './client-editor.component.html',
  styleUrls: ['./client-editor.component.css']
})
export class ClientEditorComponent implements OnInit {
  @Input('client') client: Client;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();

  ClientForm: FormGroup;
  formMsg: string;
  formError: boolean;

  myDatepipe: DatePipe;
  
  user_data = [
    {
      mod: 'John Smith',
      comment: 'Advisor',
      date: '1984-05-05',    
    },
    {
      mod: 'Muhi Masri',
      comment: 'Developer',
      date: '1992-02-02',    
    },
    { mod: 'Peter Adams', comment: 'HR', date: '2000-01-01' },
    {
      mod: 'HugeA$$',
      comment: 'Marketing',
      date: '1977-03-03',    
    },
  ];
  
  columns_schema = [
    {
      key: 'mod',
      type: 'text',
      label: 'Модификация'
    },
    {
      key: 'date',
      type: 'date',
      label: 'Дата'
    },
    {
      key: 'comment',
      type: 'text',
      label: 'Комментарий'
    },  
    {
      key: 'isEdit',
      type: 'isEdit',
      label: '',
    },
  ];

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

  constructor(
    private fb: FormBuilder, 
    private clientService: ClientsService,
    datepipe: DatePipe
    ) { this.myDatepipe = datepipe; console.log("CLINET_EDITOR"); console.log(this.user_data)}

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
    var pattern = /(\d{2})\.(\d{2})\.(\d{4})/;
    var date = new Date(this.client.person.dateOfBirth.replace(pattern, '$3-$2-$1'));
    this.ClientForm = this.fb.group({
      name: [this.client.person.name, Validators.required],
      surname: [this.client.person.surname, Validators.required],
      patronymic: [this.client.person.patronymic, Validators.required],
      email : new FormControl(this.client.email, Validators.compose([
        Validators.required,
        Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$')
      ])),
      passport: [this.client.person.passports[0].passport, Validators.required],
      dateOfBirth: [date, Validators.required],
      comment:[this.client.comment, Validators.maxLength]      
    })
  }

  getFIO(){        
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }
  closeClick(event) { this.close.emit(true); }


  onSubmitClient(value)
  {
    value.dateOfBirth = this.myDatepipe.transform(this.ClientForm.value.dateOfBirth, 'yyyy-MM-dd');
    
    console.log(value);
  }

  previousChanged(data)
  {
    console.log(data);
  }  
}

