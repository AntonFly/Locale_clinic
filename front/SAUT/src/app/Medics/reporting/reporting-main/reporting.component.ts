import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {Client} from '../../../_models/Client'
import {PageEvent} from '@angular/material/paginator';
import { ReportingService } from '../../_services';

@Component({
  selector: 'app-reporting',
  templateUrl: './reporting.component.html',
  styleUrls: ['./reporting.component.css']
})
export class ReportingComponent implements OnInit {
  
  allClientItems: Client[];  
  filteredItems: Client[];
  currentItems: Client[];
  shownItems: Client[];

  clientError: boolean;
  searchField = new FormControl('');
  
  /* EDITOR */
  showingEditor: boolean = false;
  editedClient: Client;

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

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    console.log(this.pageSize +" "+this.pageIndex);
    this.show();
  }
  /* pagination */

  constructor(private reportingService: ReportingService) 
  {
    this.updateUsers();    
  }

  ngOnInit() {  
    this.clientError = false;  
  }
  
  show() {
    this.shownItems = this.currentItems.slice(this.pageIndex * this.pageSize, this.pageIndex * this.pageSize + this.pageSize);
    console.log(this.shownItems)
  }


  updateUsers(){
    this.reportingService.getAllClients().subscribe(
      result =>{        
        this.allClientItems = result;
        this.currentItems = result;        
        this.length = this.currentItems.length;   
        this.pageIndex = 0;     
        this.show();
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

  clientSearch(){
    this.currentItems = undefined;
    let filterValue = this.searchField.value
    
    if(filterValue == "" || filterValue == undefined || filterValue == null)
    {
      this.currentItems = this.allClientItems
      return;
    }
    filterValue = filterValue.toLowerCase();

    let tmp = this.allClientItems.filter(function(el) {
      
      return  el.person.surname.toLowerCase().includes(filterValue) ||
              el.person.name.toLowerCase().includes(filterValue) ||
              el.person.patronymic.toLowerCase().includes(filterValue) ||
              String(el.person.id).toLowerCase().includes(filterValue) ||
              el.person.dateOfBirth.toLowerCase().includes(filterValue) ||
              el.email.toLowerCase().includes(filterValue)
    });
    
    this.filteredItems = tmp;
    this.currentItems = tmp;
    this.length = this.currentItems.length;
    this.show();
  }
  searchReset(){
    this.searchField.reset();    
    this.searchField.clearValidators();
    this.searchField.markAsPristine();
    this.searchField.markAsUntouched();
    this.searchField.updateValueAndValidity();
    this.currentItems = this.allClientItems;
  }

  openEditor(data){
    console.log(data);
    this.showingEditor = true;
    this.editedClient = data;
  }

  closeEditor()
  {
    this.showingEditor = false;
    this.editedClient = undefined;
  }

}
