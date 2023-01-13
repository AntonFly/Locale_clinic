import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/_models/Order';
import { OrderService } from '../../_services'
import {PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-request-list',
  templateUrl: './request-list.component.html',
  styleUrls: ['./request-list.component.css']
})
export class RequestListComponent implements OnInit {

  searchField:string = "";

  orders: Order[];
  currentItems: Order[];
  shownItems: Order[];

  clientError: boolean;

  isOrderOpened: boolean;
  orderOpened: Order;

  /* pagination */
  length = 50;
  pageSize = 10;
  pageIndex = 0;
  pageSizeOptions = [5, 10, 25];
  
  hidePageSize = false;
  showPageSizeOptions = true;
  showFirstLastButtons = true;
  disabled = false;

  pageEvent: PageEvent;


  constructor(private orderService:OrderService) { }

  ngOnInit() {
    this.updateOrders()
  }

  updateOrders(){
    this.orderService.getAllOrders().subscribe(
      result =>{
        this.orders = result;
        this.clientError = false;
        
        this.currentItems = result;   
        this.length = this.currentItems.length;   
        this.pageIndex = 0;     
        this.show();
      },
      error =>{
        this.clientError = true;
      });
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log(this.pageSize +" "+this.pageIndex);
    this.show();
  }

  show() {
    this.shownItems = this.currentItems.slice(this.pageIndex * this.pageSize, this.pageIndex * this.pageSize + this.pageSize);
    console.log(this.shownItems)
  }

  getFIO(client:any){    
    return client.surname+' '+client.name.substring(0,1)+'. '+client.patronymic.substring(0,1)+'.'
  }

  openOrder(queueItem)
  {
    this.isOrderOpened = true;
    this.orderOpened = queueItem;
  }

  closeOrder()
  {
    this.isOrderOpened = false;
    this.orderOpened = undefined;
  }


  orderSearch(){
    this.currentItems = undefined;
    let filterValue = this.searchField
    
    if(filterValue == "" || filterValue == undefined || filterValue == null)
    {
      this.currentItems = this.orders;
      this.shownItems = this.orders;
      this.length = this.currentItems.length;
      return;
    }

    filterValue = filterValue.toLowerCase();

    let tmp = this.orders.filter(function(el) {
      
      return  el.client.person.surname.toLowerCase().includes(filterValue) ||
              el.client.person.name.toLowerCase().includes(filterValue) ||
              el.client.person.patronymic.toLowerCase().includes(filterValue) ||
              String(el.client.person.passports[0].passport).toLowerCase().includes(filterValue) ||
              el.client.person.dateOfBirth.toLowerCase().includes(filterValue) ||
              el.client.email.toLowerCase().includes(filterValue) ||
              el.specialization.name.toLowerCase().includes(filterValue) ||
              String(el.id).includes(filterValue)
    });
        
    this.currentItems = tmp;
    this.length = this.currentItems.length;
    this.show();
  }
}
