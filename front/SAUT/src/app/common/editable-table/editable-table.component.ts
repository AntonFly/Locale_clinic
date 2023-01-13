/* auto complete feature works like hell nah, picks first auto field from schema and checks auto data obvs
next it changes whole row based on autocomplete pick */
import { trigger } from '@angular/animations';
import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild, OnChanges } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { stringify } from 'querystring';
import { Observable, Subscription } from 'rxjs';
//import { AppUtilityService } from 'src/app/app-utility.service';

class RowClass {
  isEdit: boolean;
  data: any;  
  isSelected: boolean;
  id: number;

  constructor(data, id)
  {
    this.id = id;
    this.data = data;
    this.isEdit = false;
    this.isSelected = false;
  }
}

@Component({
  selector: 'app-editable-table',
  templateUrl: './editable-table.component.html',
  styleUrls: ['./editable-table.component.css']
})
export class EditableTableComponent implements OnInit {
  @Input('schema') schema: any;
  @Input('data') data: any;
  @Input('deletable') deletable: boolean = false;
  @Input('autocomplete') autocomplete: any;
  @Output('changed') changed: EventEmitter<any> = new EventEmitter();

  schemeError: string = "";

  /* auto */
  autoColKey: string = undefined;
  // filteredOptions: Observable<String[]>;
  options: String[] = [];

  displayedColumns: string[];
  dataSource =  new MatTableDataSource<RowClass>();
  columnsSchema: any; 
  valid: any = {};
  lastId: number = 1;
  
  highlights_names=[];
  highlights = {};

  constructor(
    //private global_utilities: AppUtilityService
  ) {     
  }
  
  ngOnInit(): void {    
    this.verifySchema();
    this.init();

    // this.filteredOptions = this.formControlSpec.valueChanges.pipe(
    //   startWith(''),
    //   map(value => this._filter(value || '')),
    // );
  }

  ngOnChanges() {
    this.init();  
  }   

  init(){
    this.columnsSchema = this.schema;
    this.displayedColumns = this.columnsSchema.map((col) => col.key);    

    this.schema.forEach( el => {
      if(el.highlight)
      {
        this.highlights_names.push(el.highlight);
        this.highlights[el.highlight] = el[el.highlight];
      }

      if(el.autoComplete && !this.autoColKey)
        this.autoColKey = el.key;
    })

    if(this.autocomplete && this.autoColKey)
      this.autocomplete.forEach(el => {
        this.options.push(el[this.autoColKey]);
    })
    
    var newData = [];
    if(this.data)
      this.data.forEach((el, ind)=> 
      {        
        this.highlights_names.forEach(name => {
          el[name] = this.highlights[name];
        })
        
        var row = new RowClass(el, ind + 1);
        if(this.autocomplete && this.autoColKey)
        {
          row["filteredOptions"] = [];          
        }

        newData.push(row);
        this.lastId = ind + 1;
      })
    
    this.dataSource.data = newData;      
  }

  verifySchema()
  {
    var isAuto: Boolean = false;
    this.schema.forEach(el => 
      {
        if(el.autoComplete)
        {
          if(!isAuto)
            isAuto = true;
          else this.schemeError = "Несколько полей с автодополнением"
        }
      })
  }

  updateAuto(event, id)
  {
    var data = this.dataSource.data;
    var index = data.findIndex(e => e.id == id)    
    
    data[index]["filteredOptions"] = this._filter(this.dataSource.data[index].data[this.autoColKey]);
    this.dataSource.data = data;  
    
  }

  private _filter(value: string): String[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }

  clearData()
  {
    var data = this.dataSource.data;
    var out = [];

    data.forEach(el => {
      out.push(el.data);
    });
    
    return out;
  }

  editRow(row) {    
    if (row.id !== 0) {
      var data = this.dataSource.data;
      for (var i = 0; i < data.length; i++ )
      {
        if(data[i].id === row.id)
        {
          if(this.autocomplete && this.autoColKey)
          {
            var index = this.autocomplete.findIndex(e => e[this.autoColKey] == row.data[this.autoColKey]);
            data[i].data = this.autocomplete[index];
          }
          else
          {
            for(var j = 0; j < this.columnsSchema.length; j++)
            {
              var key = this.columnsSchema[j].key;
              data[i].data[key] = row.data[key];
            }
          }
          data[i].isEdit = false;          
        }
      }      
      this.dataSource.data = data;
      this.changed.emit(this.clearData());
    }
  }

  addRow() {
    
    var data = {};
    for(var j = 0; j < this.columnsSchema.length; j++)
    {
      var key = this.columnsSchema[j].key;
      switch(this.columnsSchema[j].type)
      {
        case 'text':
          data[key] = '';
          break;
        case 'number':
          data[key] = 0;
          break;
        case 'date':
          data[key] = new Date();
          break;
      }      
    }

    this.highlights_names.forEach(name => {
      data[name] = this.highlights[name];
    })
    
    var newData = new RowClass(data, ++this.lastId);
    var oldData = this.dataSource.data.slice();
    oldData.push(newData);    
    this.dataSource.data = oldData;
    this.changed.emit(this.clearData());    
  }

  removeRow(id: number) {    
    var data = this.dataSource.data;
    data.splice(data.findIndex(e => e.id == id),1);
    this.dataSource.data = data;
    this.changed.emit(this.clearData());
    // this.userService.deleteUser(id).subscribe(() => {
    //   this.dataSource.data = this.dataSource.data.filter(
    //     (u: User) => u.id !== id
    //   );
    // });
  }

  removeSelectedRows() {
    // const users = this.dataSource.data.filter((u: User) => u.isSelected);
    // this.dialog
    //   .open(ConfirmDialogComponent)
    //   .afterClosed()
    //   .subscribe((confirm) => {
    //     if (confirm) {
    //       this.userService.deleteUsers(users).subscribe(() => {
    //         this.dataSource.data = this.dataSource.data.filter(
    //           (u: User) => !u.isSelected
    //         );
    //       });
    //     }
    //   });
  }

  inputHandler(e: any, id: number, key: string) {
    // if (!this.valid[id]) {
    //   this.valid[id] = {};
    // }
    // this.valid[id][key] = e.target.validity.valid;
  }

  disableSubmit(id: number) {
    if(this.autocomplete && this.autoColKey)
    {
      var data = this.dataSource.data;
      var el = data[data.findIndex(e => e.id == id)];
      return !this.options.includes(el.data[this.autoColKey]);
    }
    // if (this.valid[id]) {
    //   return Object.values(this.valid[id]).some((item) => item === false);
    // }
    return false;
  }
}