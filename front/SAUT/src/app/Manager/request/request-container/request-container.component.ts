import { Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core'
import { MatTabChangeEvent } from '@angular/material';
import { RequestListComponent } from '../request-list/request-list.component';

@Component({
  selector: 'app-request-container',
  templateUrl: './request-container.component.html',
  styleUrls: ['./request-container.component.css']
})
export class RequestContainerComponent implements OnInit {

  @ViewChild(RequestListComponent, {static: true}) private reqList: RequestListComponent;

  constructor() { }

  ngOnInit() {
  }

  onTabChanged(event: MatTabChangeEvent)
  {
    if(event.index == 1)
    {
      this.reqList.updateOrders();
    }    
  }

}
