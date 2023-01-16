import { Component, Input, Output, OnInit, OnDestroy } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

import { Client } from 'src/app/_models/Client';

import { DatePipe } from '@angular/common';
import { Order } from 'src/app/_models/Order';


@Component({
  selector: 'app-clients-info',
  templateUrl: './clients-info.component.html',
  styleUrls: ['./clients-info.component.css']
})
export class ClientsInfoComponent implements OnInit {

  @Input('client') client: Client;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();

  currentOrders: Order[];
  selectedOrder: Order;

  isOrderError: boolean = false;
  isOrderLoading: boolean = true;

  myDatepipe: DatePipe;
  
  
  page : string = "client";

  constructor(
    private fb: FormBuilder, 
    // private clientService: ClientsService,
    // private reportService: ReportingService,
    datepipe: DatePipe
    ) { 
      this.myDatepipe = datepipe;       
    }

  ngOnInit() {
    this.isOrderLoading = true;    
    // this.reportService.getOrdersByPassport(this.client.person.passports[0].passport).subscribe(
    //   res =>{
    //     this.isOrderLoading = false;
    //     this.currentOrders = res;
    //   },
    //   error => {
    //     this.isOrderLoading = false;
    //     this.isOrderError = true;
    //   }
    // )
  }

  
  getFIO(){        
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }  
  closeClick() { 
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