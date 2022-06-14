import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/_models/Order';
import { OrderService } from '../../_services'

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {

  orders: Order[];
  clientError: boolean;

  constructor(private orderService:OrderService) { }

  ngOnInit() {
    this.updateOrders()
  }

  updateOrders(){
    this.orderService.getAllOrders().subscribe(
      result =>{
        this.orders = result;
        this.clientError = false;
        console.log(result);
      },
      error =>{
        this.clientError = true;
      });
  }

  getFIO(client:any){    
    return client.surname+' '+client.name.substring(0,1)+'. '+client.patronymic.substring(0,1)+'.'
  }

}
