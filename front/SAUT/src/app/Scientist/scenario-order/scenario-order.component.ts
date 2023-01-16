import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import { Client } from 'src/app/_models/Client';
import { Order } from 'src/app/_models/Order';

@Component({
  selector: 'app-scenario-order',
  templateUrl: './scenario-order.component.html',
  styleUrls: ['./scenario-order.component.css']
})
export class ScenarioOrderComponent implements OnInit {  
  @Input('client') client: Client;
  @Input('order') order: Order;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();

  isEditor: boolean = true;
  modsToEdit = [];
  curScenario;

  constructor() { }

  ngOnInit() {
    if(this.order.scenario)
    {
      this.isEditor = false;
      this.curScenario = this.order.scenario.modificationScenarios;
      console.log(this.curScenario)
    }
    
    this.modsToEdit = this.order.modifications.map(el => {return el.name});    
  }

  getFIO(){        
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }
  closeClick() { this.close.emit(true); }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.modsToEdit, event.previousIndex, event.currentIndex);
    console.log(this.modsToEdit)
  }

}
