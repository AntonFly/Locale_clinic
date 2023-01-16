import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { Validators, FormControl } from '@angular/forms';
import { Order } from '../../_models/Order';
import { Client } from 'src/app/_models/Client';
import { ClientsService } from 'src/app/Manager/_services';
import {SupportService} from '../_services/support.service';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportEngComponent implements OnInit {


  panelOpenState = false;

  isClientError = false;
  clientErrorMsg = '';
  currentClient: Client;

  currentOrders: Order[];
  selectedOrder: Order;

  validation_messages = {
    'passport': [
      { type: 'required', message: 'Требуется паспорт клиента' }
    ]
  };

  passport = new FormControl('', Validators.required);

  constructor(private dialog: MatDialog,
              private clientService: ClientsService,
              private supportService: SupportService) { }

  ngOnInit() {

  }

  get formControlPassport() {
    return this.passport;
  }


  getFIO(client: any) {
    return client.surname + ' ' + client.name.substring(0, 1) + '. ' + client.patronymic.substring(0, 1) + '.';
  }

  selectRow(order: Order) {
    console.log(order);
    if (this.selectedOrder === order) {
      this.selectedOrder = undefined;
    } else {
      this.selectedOrder = order;
    }
  }

  clientExists(id: any) {
    console.log(id);
    if (id === '' || isNaN(id) || id === undefined) {
      this.isClientError = false;
      this.clientErrorMsg = '';
      this.currentClient = undefined;
      return;
    }

    this.selectedOrder = undefined;
    // tslint:disable-next-line:radix
    this.supportService.getClient(parseInt(id)).subscribe(
      result => {
        this.isClientError = false;
        this.clientErrorMsg = '';
        this.currentClient = result;
        // tslint:disable-next-line:radix
        this.getOrdersById(parseInt(id));
      },
      error => {
        this.isClientError = true;

        if (error.error.status === 'NOT_FOUND') {
          this.clientErrorMsg = 'Такого клиента не существует';
        } else { this.clientErrorMsg = 'Не удалось найти клиента'; }

        this.currentClient = undefined;
        this.currentOrders = [];
      });
  }

  getOrdersById(id: number) {
    this.supportService.getOrdersByPassport(id).subscribe(
      result => {
        this.currentOrders = result;
      },
      error => {

      });
  }

}
