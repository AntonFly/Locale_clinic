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
    this.reportingService.updateimplants(this.client.id, this.currentImplants).subscribe(
      res => {
        console.log(res)
      },
      error => {
        console.log(error)
      }
    ); 
  }

  previousChanged(event)
  {
    console.log(event)
    this.currentImplants = event;    
  }  

}
