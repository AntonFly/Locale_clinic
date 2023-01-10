import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Order } from 'src/app/_models/Order';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrls: ['./order-confirmation.component.css']
})
export class OrderConfirmationComponent implements OnInit {
  @Input('order') client: Order;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();
  constructor() { }

  ngOnInit() {
  }
  
  closeClick(event) { this.close.emit(true); }
}
