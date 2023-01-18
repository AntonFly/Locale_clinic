import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
// import {EngineerService} from '../_services/engineer.service'
import { ScientistService } from '../_services';
import { Order } from '../../_models/Order'
import {Observable} from 'rxjs';
import { Client } from 'src/app/_models/Client';
import { ClientsService } from 'src/app/Manager/_services';

@Component({
  selector: 'app-scenario-main',
  templateUrl: './scenario-main.component.html',
  styleUrls: ['./scenario-main.component.css']
})
export class ScenarioMainComponent implements OnInit {

  panelOpenState = false;

  isClientError: boolean = false;
  clientErrorMsg: string = "";
  currentClient: Client;

  currentOrders: Order[];
  selectedOrder: Order;

  isGenomePage: boolean = false;

  validation_messages = {
    'passport': [
      { type: 'required', message: 'Требуется паспорт клиента' }
    ]
  }

  curId;
  passport = new FormControl('', Validators.required);  

  constructor(
              private engService: ScientistService, 
              ) { }

  ngOnInit() {
    
  }

  get formControlPassport() {
    return this.passport;
  }

  getFIO(client:any){    
    return client.surname+' '+client.name.substring(0,1)+'. '+client.patronymic.substring(0,1)+'.'
  }

  selectRow(order: Order){
    console.log(order);
    if(this.selectedOrder == order)
      this.selectedOrder = undefined
    else
      this.selectedOrder = order;
  }

  clientExists(id: any) {
    console.log(id)
    if(id=="" || id===NaN || id === undefined)
    {
      this.isClientError = false;
      this.clientErrorMsg = "";
      this.currentClient = undefined;
      return;
    }

    this.selectedOrder=undefined;
    this.engService.getClient(parseInt(id)).subscribe(
      result => {                              
        this.isClientError = false;
        this.clientErrorMsg = "";
        this.currentClient = result;
        this.getOrdersById(parseInt(id));
      },
      error => {                
        this.isClientError = true;
        
        if(error.error.status == "NOT_FOUND")
          this.clientErrorMsg = "Такого клиента не существует";
        else this.clientErrorMsg = "Не удалось найти клиента";

        this.currentClient = undefined;
        this.currentOrders = [];
      });        
  }

  getOrdersById(id: number){
    this.engService.getOrdersByPassport(id).subscribe(
      result => {
        this.curId = id;
        this.currentOrders = result;
        console.log(result)
      },
      error => {

      })
  }

  selectOrder(order){    
    if(order.confirmation)
    {
      this.isGenomePage=!this.isGenomePage; 
      this.selectedOrder=order;
    }
  }

  closePage(){
    this.isGenomePage=!this.isGenomePage
    this.getOrdersById(this.curId);
  }

}



