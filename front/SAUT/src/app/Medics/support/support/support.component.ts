import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {SupportService} from '../../_services/support.service'
import { Order } from '../../../_models/Order'
import {Observable} from 'rxjs';
import { Client } from 'src/app/_models/Client';
import { ClientsService } from 'src/app/Manager/_services';
import { SupportDialogComponent } from '../support-dialog/support-dialog.component';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  ScenarioDialogRef: MatDialogRef<SupportDialogComponent>;

  panelOpenState = false;

  isClientError: boolean = false;
  clientErrorMsg: string = "";
  currentClient: Client;

  currentOrders: Order[];
  selectedOrder: Order;

  validation_messages = {
    'passport': [
      { type: 'required', message: 'Требуется паспорт клиента' }
    ]
  }

  passport = new FormControl('', Validators.required);  

  constructor(private dialog: MatDialog,
              private supportService:SupportService, 
              private clientService:ClientsService) { }

  ngOnInit() {
    
  }

  get formControlPassport() {
    return this.passport;
  }

  generateScneario(){
    this.supportService.getSupportBySpecializId(this.selectedOrder.specialization.name).subscribe(
      result => {
        this.ScenarioDialogRef = this.dialog.open(SupportDialogComponent,{
          hasBackdrop:true,
          data:{
            order: this.selectedOrder,
            scenario: result
          }
        });
      },
      error => {
        this.ScenarioDialogRef = this.dialog.open(SupportDialogComponent,{
          hasBackdrop:true,
          data:{
            order: this.selectedOrder,            
            error: error.error
          }
        });
      });    
    
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
    this.clientService.getClient(parseInt(id)).subscribe(
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
    this.supportService.getOrdersByPassport(id).subscribe(
      result => {
        this.currentOrders = result;
      },
      error => {

      })
  }

}
