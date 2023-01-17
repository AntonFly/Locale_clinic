import { Component, OnInit, Input, OnChanges, EventEmitter, Output } from '@angular/core';
import { Modification } from 'src/app/_models/Client';

@Component({
  selector: 'app-priority-box',
  templateUrl: './priority-box.component.html',
  styleUrls: ['./priority-box.component.css']
})
export class PriorityBoxComponent implements OnInit {
  @Input('mod') modification: Modification;
  @Input('index') index: number;
  @Input('prevIndex') prevIndex: number;
  @Output('changeP') change: EventEmitter<any> = new EventEmitter();

  mod: Modification;
  prevPrior: number;
  changed: boolean = false;

  constructor() { }

  ngOnChanges() {
    this.init();
    // console.log("change")
  }   

  ngOnInit() {
    this.init();
  }

  init(){
    this.mod = this.modification;

    // if(!this.mod.priority || !this.changed)
    ///////////////////////////////////////////////aaaaaaaaaaaaaaaaaaaaaaaaaaaa
      // this.mod.priority = this.index;
    
    // if(this.prevIndex > this.mod.priority)
    // {
    //   this.mod.priority = this.prevIndex + 1;
    //   this.change.emit(this.mod);
    // }

    this.prevPrior = this.mod.priority;
  }

  input(){
    if(this.mod.priority < 1 || this.mod.priority > 100)
      this.mod.priority = this.prevPrior;
    else {
      this.prevPrior = this.mod.priority;
      this.changed = true;
      this.change.emit(this.mod);
    }
  }
}
