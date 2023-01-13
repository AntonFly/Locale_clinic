import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { ChangeDetectorRef, AfterViewInit } from '@angular/core'
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

  tableMsg: string;
  tableError: boolean;


  myDatepipe: DatePipe;
  
  user_data;
  auto_data;

  tableValid: boolean = false;
  
  tableChosen;
  
  columns_schema = [
    {
      key: 'name',
      type: 'text',
      label: 'Модификация',
      autoComplete: true
    },    
    {
      key: 'risk',
      type: 'text',
      label: 'Риск',
      notEditable: true
    },  
    {
      key: 'price',
      type: 'text',
      label: 'Цена                                ',
      notEditable: true
    },
    {
      key: 'currency',
      type: 'text',
      label: 'Валюта',
      notEditable: true
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
    private cdr: ChangeDetectorRef,
    private fb: FormBuilder, 
    private clientService: ClientsService,
    datepipe: DatePipe
    ) { this.myDatepipe = datepipe;}

  ngOnInit() {
    this.user_data = this.client.modifications;
    this.getAllMods();
    this.createForms();    
    this.formMsg = "";
    this.formError = false;    
  }

  getAllMods(){
    this.clientService.getModifications().subscribe(
      res => {
        this.auto_data = res;
        // console.log(res)
        // this.user_data = res;
      },
      err => {
        console.log(err)
      }
    )
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

    this.cdr.detectChanges();
  }

  getFIO(){        
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }
  closeClick(event) { this.close.emit(true); }


  onSubmitClient(value)
  {    
    value.dateOfBirth = this.myDatepipe.transform(this.ClientForm.value.dateOfBirth, 'yyyy-MM-dd');
    
    this.clientService.updateClient(value, this.client.id).subscribe(
      res => {
          console.log(res);
        this.formError = false;
        this.formMsg = "Изменения сохранены";
        setTimeout(() => {
          this.formMsg = "";
          this.formError = false;
        }, 5000);            
      },
      error => {
        console.log(error)
        this.formError = true;
        this.formMsg = "Не удалось сохранить изменения";
        setTimeout(() => {
          this.formMsg = "";
          this.formError = false;
        }, 5000);            
      }
    )    
  }

  updateTable(){
    if(!this.tableValid)
      return;
      
      var ids = this.tableChosen.map(obj =>{
        return [obj.id]
      }).flat();

      this.clientService.addPreviousModification(this.client.id, ids).subscribe(
        res => {
            console.log(res);
          this.tableError = false;
          this.tableMsg = "Изменения сохранены";
          setTimeout(() => {
            this.tableMsg = "";
            this.tableError = false;
          }, 5000);            
        },
        error => {
          console.log(error)
          this.tableError = true;
          this.tableMsg = "Не удалось сохранить изменения";
          setTimeout(() => {
            this.tableMsg = "";
            this.tableError = false;
          }, 5000);            
        }
      )
    
  }

  previousChanged(data)
  {
    this.tableValid = true;
    
    data.forEach(el => {
      if(!el.id || !el.name)
        this.tableValid = false;
    })

    this.tableChosen = data;
  }
}

