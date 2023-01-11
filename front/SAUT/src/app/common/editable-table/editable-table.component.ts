import { trigger } from '@angular/animations';
import { AfterViewInit, Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
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
  @Output('changed') changed: EventEmitter<any> = new EventEmitter();

  displayedColumns: string[];
  dataSource =  new MatTableDataSource<RowClass>();
  columnsSchema: any; 
  valid: any = {};
  lastId: number = 1;

  constructor(
    //private global_utilities: AppUtilityService
  ) {     
  }
  
  ngOnInit(): void {
    this.columnsSchema = this.schema;
    this.displayedColumns = this.columnsSchema.map((col) => col.key);    
    
    var newData = [];
    if(this.data)
      this.data.forEach((el, ind)=> {
        newData.push(new RowClass(el, ind + 1));
        this.lastId = ind + 1;
      })
    this.dataSource.data = newData;  
    console.log("DATA")   
    console.log(this.dataSource.data)
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
          for(var j = 0; j < this.columnsSchema.length; j++)
          {
            var key = this.columnsSchema[j].key;
            data[i].data[key] = row.data[key];
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
    
    var newData = new RowClass(data, ++this.lastId);
    var oldData = this.dataSource.data.slice();
    oldData.push(newData);    
    this.dataSource.data = oldData;
    this.changed.emit(this.clearData());    
  }

  removeRow(id: number) {
    console.log('delete')
    console.log(id)
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
    // if (this.valid[id]) {
    //   return Object.values(this.valid[id]).some((item) => item === false);
    // }
    return false;
  }
}