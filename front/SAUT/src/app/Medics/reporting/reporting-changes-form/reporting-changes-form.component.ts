import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { Client } from 'src/app/_models/Client';
//import {ClientsService} from '../../_services/clients.service'
import { DatePipe } from '@angular/common';
import { Order, BodyChange } from 'src/app/_models/Order';

@Component({
  selector: 'app-reporting-changes-form',
  templateUrl: './reporting-changes-form.component.html',
  styleUrls: ['./reporting-changes-form.component.css']
})
export class ReportingChangesFormComponent implements OnInit {
  
  @Input('change') change: BodyChange;
  @Output('update') update: EventEmitter<BodyChange> = new EventEmitter<BodyChange>();
  @Output('delete') delete: EventEmitter<boolean> = new EventEmitter<boolean>();

  UserForm: FormGroup;   
  formMsg: string;
  formError: boolean;

  validation_messages = {    
    'Passport': [
      { type: 'required', message: 'Требуется название' },      
    ]
  };
  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.createForms();
  }

    /*FORM's GETTERS*/

    get formControlPassport() {
      return this.UserForm.get('passport');
    }

  /*FORM's GETTERS*/
  
  
    createForms() {
      this.UserForm = this.fb.group({        
        passport : ['', Validators.required],
        comment : ['', Validators.required],
        symptoms: ['', Validators.required],
        actions: ['', Validators.required],
      })
    }

}
