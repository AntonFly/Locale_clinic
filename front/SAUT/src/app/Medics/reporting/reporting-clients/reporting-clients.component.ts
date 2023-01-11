import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Subject, Subscription } from 'rxjs';
import { Client } from 'src/app/_models/Client';
//import {ClientsService} from '../../_services/clients.service'
import { DatePipe } from '@angular/common';
import { Order } from 'src/app/_models/Order';
import { ReportingService } from '../../_services';

@Component({
  selector: 'app-reporting-clients',
  templateUrl: './reporting-clients.component.html',
  styleUrls: ['./reporting-clients.component.css']
})
export class ReportingClientsComponent implements OnInit {

  @Input('client') client: Client;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();

  currentOrders: Order[];
  selectedOrder: Order;

  isOrderError: boolean = false;
  isOrderLoading: boolean = true;

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

  page : string = "client";

  constructor(
    private fb: FormBuilder, 
    // private clientService: ClientsService,
    private reportService: ReportingService,
    datepipe: DatePipe
    ) { 
      this.myDatepipe = datepipe;       
    }

  ngOnInit() {
    this.isOrderLoading = true;    
    this.reportService.getOrdersByPassport(this.client.person.passports[0].passport).subscribe(
      res =>{
        this.isOrderLoading = false;
        this.currentOrders = res;
      },
      error => {
        this.isOrderLoading = false;
        this.isOrderError = true;
      }
    )
  }

  
  getFIO(){        
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }  
  closeClick(event) { 
    if(this.page == "client")
      this.close.emit(true); 
    else this.page = "client";
  }

  openChanges(){}
  openImplants(){}


  previousChanged(data)
  {
    console.log(data);
  }  

  /* ORDERS TABLE */
  selectRow(order: Order){
    console.log(order);
    if(this.selectedOrder == order)
      this.selectedOrder = undefined
    else
      this.selectedOrder = order;
  }

}
