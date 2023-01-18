import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import { Client, Modification } from 'src/app/_models/Client';
import { Order } from 'src/app/_models/Order';
import { ScientistService } from '../_services';


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

  scenarioMsg:string = ""
  isScenarioError: boolean = false;
  saved = false;
  constructor(private serv: ScientistService) { }

  ngOnInit() {
    this.modsToEdit = this.order.modifications;
    if(this.order.scenario)
    {
      this.isEditor = false;
      this.curScenario = this.order.scenario;
      this.sortCurScenario();
    }
    else {
      this.serv.registerScenario(this.order.specialization.id, this.order.id, this.order.modifications.map(el => el.id)).subscribe(
        (res : any) => {
          console.log("AAAA");
          console.log(res);
          this.order.scenario = res;
        },
        error => {
          console.log(error);
        }

      )
    }
    this.mapScenario()

    //this.order.modifications.map(el => {return el.name});
  }

  getFIO(){
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }
  closeClick() { this.close.emit(true); }

  drop(event: CdkDragDrop<Modification>) {
    moveItemInArray(this.modsToEdit, event.previousIndex, event.currentIndex);
    if(event.currentIndex == 0)
      if(this.modsToEdit[event.currentIndex + 1].priority < this.modsToEdit[event.currentIndex].priority)
      {
        this.modsToEdit[event.currentIndex].priority = this.modsToEdit[event.currentIndex + 1].priority == 0 ?
        0 : this.modsToEdit[event.currentIndex + 1].priority - 1;
      }

    if(event.currentIndex == this.modsToEdit.length - 1)
      if(this.modsToEdit[event.currentIndex - 1].priority > this.modsToEdit[event.currentIndex].priority)
      {
        this.modsToEdit[event.currentIndex].priority = this.modsToEdit[event.currentIndex - 1].priority == 100?
        100 : this.modsToEdit[event.currentIndex - 1].priority + 1;
      }

    if( event.currentIndex != this.modsToEdit.length - 1 && event.currentIndex != 0)
    {
      var left = (this.modsToEdit[event.currentIndex - 1].priority + this.modsToEdit[event.currentIndex + 1].priority) % 2
      this.modsToEdit[event.currentIndex].priority = left == 0 ? (this.modsToEdit[event.currentIndex - 1].priority + this.modsToEdit[event.currentIndex + 1].priority) / 2
      : this.modsToEdit[event.currentIndex - 1].priority + 1
    }

    console.log(this.modsToEdit)
  }

  mapScenario()
  {
    console.log(this.modsToEdit)
    if(this.order.scenario)
    {
      this.modsToEdit.forEach(el => {
        var a = this.curScenario.modificationScenarios.find(function(item, index, arr){
          return item.modification.id == el.id;
        })

        if(a)
          el.priority = a.priority;
      })
    }
    else{
      this.modsToEdit.forEach((el, ind)=>{
        el.priority = ind +1;
      })
    }

    this.modsToEdit.sort(function(a,b){
      if(a.priority < b.priority){
          return -1;
      } else if(a.priority == b.priority){
          return 0;
      } else { // a > b
          return 1;
      }
    });
    console.log(this.modsToEdit)
  }

  priorityChanged(mod){
    console.log(mod);
    var ind = this.modsToEdit.findIndex(function(item, index, array){
      return item.id == mod.id;
    })

    var newInd = this.modsToEdit.findIndex(
      function(item, index, array){
        if(index != ind)
          return mod.priority < item.priority;
        else return false;
    });

    this.modsToEdit[ind].priority = mod.priority;
    newInd = newInd - 1;
    newInd = newInd < 0? this.modsToEdit.length - 1: newInd;
    moveItemInArray(this.modsToEdit, ind, newInd);
    console.log(this.modsToEdit)
  }

  sortCurScenario(){
    this.curScenario.modificationScenarios.sort(function(a,b){
      if(a.priority < b.priority){
          return -1;
      } else if(a.priority == b.priority){
          return 0;
      } else { // a > b
          return 1;
      }
    });
  }

  saveRes(){
    //id scen = this.order.scenario
    //[{id_mod, prior}]
    console.log(this.modsToEdit)
    var mods = this.modsToEdit.map(el => { return {"id":el.id, "priority":el.priority}})
    this.serv.updateScenario(this.order.scenario.id, mods).subscribe(
      res => {
        console.log(res)
        this.isEditor = false;
        this.curScenario = res;
        this.sortCurScenario();
        this.mapScenario();
        this.scenarioMsg = "Успешно сохранено";
        this.saved = true;

        setTimeout(() => {
          this.scenarioMsg = "";
          this.isScenarioError = false;
        }, 5000);
      },
      error => {
        console.log(error)
        this.isScenarioError = true;
        this.scenarioMsg = "Произошла ошибка"

        setTimeout(() => {
          this.scenarioMsg = "";
          this.isScenarioError = false;
        }, 5000);
      }
    );
  }

}
