import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { DatePipe } from '@angular/common';
import { ReportingService } from '../../_services';

import { Client, Implant } from 'src/app/_models/Client';
import { copyArrayItem } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-reporting-implants',
  templateUrl: './reporting-implants.component.html',
  styleUrls: ['./reporting-implants.component.css']
})
export class ReportingImplantsComponent implements OnInit {

  @Input('client') client: Client;
  myDatepipe: DatePipe;

  currentImplants: Implant[];
  user_data;

  isDisabled: boolean = true;
  isUpdateError: boolean = false;
  updateMsg: string = "";
 
  columns_schema = [
    {
      key: 'name',
      type: 'text',
      label: 'Наименование'
    },
    {
      key: 'number',
      type: 'number',
      label: 'Серийный номер'
    },
    {
      key: 'description',
      type: 'text',
      label: 'Описание'
    },
    {
      key: 'implantation_date',
      type: 'date',
      label: 'Дата установки'
    },  
    {
      key: 'isEdit',
      type: 'isEdit',
      label: '',
    },
  ];

  constructor
  (
    datepipe: DatePipe,
    private reportingService: ReportingService
  )
  { this.myDatepipe = datepipe; }

  ngOnInit() {
    console.log(this.client)
    this.user_data = this.client.implants;
  }

  saveImplants()
  {
    this.reportingService.updateImplants(this.client.id, this.currentImplants).subscribe(
      res => {        
        this.isDisabled = true;
        this.isUpdateError = false;
        this.updateMsg = "Изменения сохранены";
        setTimeout(() => {
          this.updateMsg = "";
          this.isUpdateError = false;
        }, 5000);            
      },
      error => {
        this.isUpdateError = true;
        this.updateMsg = "Не удалось сохранить изменения";
        setTimeout(() => {
          this.updateMsg = "";
          this.isUpdateError = false;
        }, 5000);            
      }
    ); 
  }

  previousChanged(event)
  {
    this.isDisabled = false;    
    this.currentImplants = event;    
  }  

}
