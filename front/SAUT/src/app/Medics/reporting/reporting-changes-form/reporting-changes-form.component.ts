import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { Client } from 'src/app/_models/Client';
//import {ClientsService} from '../../_services/clients.service'
import { DatePipe } from '@angular/common';
import { Order, BodyChange } from 'src/app/_models/Order';
import { ReportingService } from '../../_services';

@Component({
  selector: 'app-reporting-changes-form',
  templateUrl: './reporting-changes-form.component.html',
  styleUrls: ['./reporting-changes-form.component.css']
})
export class ReportingChangesFormComponent implements OnInit {
  
  @Input('order') order: Order;
  @Input('change') change: BodyChange;
  @Output('update') update: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output('delete') delete: EventEmitter<number> = new EventEmitter<number>();

  UserForm: FormGroup;   
  formMsg: string;
  formError: boolean;

  isEdit: boolean = false;
  isFormEdited: boolean = false;  
  
  updateMsg: string = "";
  deleteMsg: string = "";
  isUpdateError: boolean = false;
  isDeleteError: boolean = false;

  validation_messages = {    
    'change': [
      { type: 'required', message: 'Требуется название' },      
    ]
  };
  constructor(private fb: FormBuilder, private reportingService: ReportingService) { }

  ngOnInit() {  
    this.createForms();
    
    if(this.change.id < 0)
      this.isEdit = true;
  }

    /*FORM's GETTERS*/

    get formControlPassport() {
      return this.UserForm.get('passport');
    }

  /*FORM's GETTERS*/
  
  
    createForms() {
      this.UserForm = this.fb.group({        
        change : [this.change.change, Validators.required],
        description : [this.change.description],
        symptoms: [this.change.symptoms],
        actions: [this.change.actions,],
      })
    }

    cancelEdit(){
      if(this.change.id < 0)
        this.delete.emit(this.change.id);
      this.isEdit=false; 
      this.isFormEdited=false;      
      this.createForms();
    }

    updateChange(value)
    {
      console.log(value)
      value["id"] = this.change.id;
      this.reportingService.updateChange(value, this.order.id).subscribe(
        (result:BodyChange) =>{
          console.log(result);
          this.change = result;
          this.isEdit = false;
          this.isFormEdited = false;
          // this.update.emit(true);
          this.isUpdateError = false;
          this.updateMsg = "Запись сохранена"
          
          setTimeout(() => {
            this.updateMsg = "";
            this.isUpdateError = false;
          }, 5000);            
        },
        error => {
          this.isUpdateError = true;
          this.updateMsg = "Ошибка сохранения"
          
          setTimeout(() => {
            this.updateMsg = "";
            this.isUpdateError = false;
          }, 5000);            
        }
      )
    }

    createChange(value)
    {
      console.log(value)
      //value["id"] = this.change.id;
      this.reportingService.updateChange(value, this.order.id).subscribe(
        (result:BodyChange) =>{
          console.log(result);
          this.change = result;
          this.isEdit = false;
          this.isFormEdited = false;
          // this.update.emit(true);
          
          this.isUpdateError = false;
          this.updateMsg = "Запись сохранена"
          
          setTimeout(() => {
            this.updateMsg = "";
            this.isUpdateError = false;
          }, 5000);            
        },
        error => {
          this.isUpdateError = true;
          this.updateMsg = "Ошибка сохранения"
          
          setTimeout(() => {
            this.updateMsg = "";
            this.isUpdateError = false;
          }, 5000);            
        }
      )
    }
    
    deleteChange()
    {
      this.reportingService.deleteChange(this.change.id).subscribe(
        res => {
          console.log(res)
          //this.update.emit(true);
          this.delete.emit(this.change.id);
        },
        error => {
          this.isDeleteError = true;
          this.deleteMsg = "Не удалось удалить запись"
          
          setTimeout(() => {
            this.deleteMsg = "";
            this.isDeleteError = false;
          }, 5000);            
        }
      )
    }
}
