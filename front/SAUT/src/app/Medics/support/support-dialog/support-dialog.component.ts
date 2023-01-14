import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA  } from '@angular/material';

@Component({
  selector: 'app-support-dialog',
  templateUrl: './support-dialog.component.html',
  styleUrls: ['./support-dialog.component.css']
})
export class SupportDialogComponent implements OnInit {

  isError: boolean = false;
  errorMsg: string = "";

  steps: string[];
  orderedSteps: any[] = [];

  modsNum: number;

  constructor(  private dialogRef: MatDialogRef<SupportDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data    
  ) { 
    
    if(data.error)
    {
      this.isError = true;
      this.errorMsg =  "Во время генерации произошла ошибка";
    }
    else if(data.scenario === undefined || data.scenario.length == 0)
    {
      this.isError = true;
      this.errorMsg =  "Не удалось получить данные по модификациям";
    }
    else{
      this.prepareData();
    }
      
  }

  prepareData(){
    console.log(this.data.scenario.length)
    console.log(this.data);
    this.steps = this.data.scenario[0].scenarios.split('\n');
    if(this.steps[this.steps.length] == undefined)
      this.steps = this.steps.slice(0, this.steps.length - 1)

    // this.steps.push("test1");
    // this.steps.push("test2");
    // this.steps.push("test3");
    // this.steps.push("test4");
    // this.steps.push("test5");

    //help 
    let spec = this.data.order.specialization.name;
    let mods = this.data.order.modifications;  
    this.modsNum = this.data.order.modifications.length;
    let stepsNum = this.steps.length;
    let unorderedSteps = []

    for (var i = 0; i < this.modsNum ; i++) { 
      let stepsPerMod = spec.length > mods[i].name.length ? 
        spec.length % mods[i].name.length : 
        mods[i].name.length % spec.length;
      
      for (var j = 0; j < stepsPerMod; j++) { 
        var price = String(mods[i].price);
        var tmp = parseInt(price.charAt(j % price.length));

        if(tmp == 0)
          tmp = mods[i].price * (j + this.modsNum  ) / stepsPerMod + 1;          
          let index = ( this.data.order.id * tmp * mods[i].price - tmp ) % stepsNum;
          index = Math.round(index);          
          unorderedSteps.push({mod: mods[i], name: this.steps[ index ]})      
      }      
    }

    let num = unorderedSteps.length
    for (var i = 0; i < num; i++) { 
      var price = String(mods[ i % this.modsNum  ].price);
      var tmp = (parseInt(price.charAt(i % price.length))+i)*i;      
      this.orderedSteps.push( unorderedSteps.splice(tmp%unorderedSteps.length,1)[0]);
    }
    
  }

  ngOnInit() {
  }

}
