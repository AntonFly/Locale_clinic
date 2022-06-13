import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {SupportService} from '../_services/support.service'
import { Order } from '../../_models/Order'
import {Observable} from 'rxjs';

@Component({
  selector: 'app-support',
  templateUrl: './support.component.html',
  styleUrls: ['./support.component.css']
})
export class SupportComponent implements OnInit {

  validation_messages = {
    'Id': [
      { type: 'required', message: 'Требуется номер клиента' }
    ]
  }

  Id = new FormControl('', Validators.required);
  orders: Order[] = [];

  constructor(private supportService:SupportService) { }

  ngOnInit() {
    //this.Id.valueChanges.subscribe(value=>{ this.orders = this.supportService.getClientById(value)});
  }

  get formControlId() {
    return this.Id;
  }

}
