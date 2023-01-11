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
  selector: 'app-reporting-body-changes',
  templateUrl: './reporting-body-changes.component.html',
  styleUrls: ['./reporting-body-changes.component.css']
})
export class ReportingBodyChangesComponent implements OnInit {
  @Input('client') client: Client;
  @Input('order') order: Order;    

  UserForm: FormGroup;   
  formMsg: string;
  formError: boolean;

  currentChanges: BodyChange[];

  constructor(private report: ReportingService) { }

  ngOnInit() {
    this.currentChanges = this.order.bodyChanges;
  }

  latestId:number = -1;
  addChange(){
    
    this.currentChanges.push({id:this.latestId--, change:"",description:"", symptoms:"", actions:""})
  }

  updateList(){
    this.report.getBodyChanges(this.order.id).subscribe(
      (res : BodyChange[]) => 
      {
        this.currentChanges = res;
      },
      err => {

      }
    )
  }

  deleteItem(id){
    
    this.currentChanges.splice(this.currentChanges.findIndex(e => e.id === id),1);
  }

}
