import { MatDialog, MatDialogRef } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
// import {ClientsDialogComponent} from '../clients-dialog/clients-dialog.component'
// import { AdvancedSearchDialogComponent } from '../advanced-search-dialog/advanced-search-dialog.component'
import {PageEvent} from '@angular/material/paginator';

import { EngineerService } from '../_services';
import {Client} from '../../_models/Client'


@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  // ClientsDialogRef: MatDialogRef<ClientsDialogComponent>;
  // AdvancedDialogRef: MatDialogRef<AdvancedSearchDialogComponent>;

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

  constructor(private dialog: MatDialog, private clientService: EngineerService) 
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

  // openClientDialog(){
  //   this.ClientsDialogRef = this.dialog.open(ClientsDialogComponent,{
  //     hasBackdrop:true
  //   });

  //   this.ClientsDialogRef
  //     .afterClosed()
  //     .pipe()
  //     .subscribe(closed => {this.updateUsers()})

  // }

  // openAdvancedDialog(){
  //   this.AdvancedDialogRef = this.dialog.open(AdvancedSearchDialogComponent,{
  //     hasBackdrop:true
  //   });
  // }

  updateUsers(){
    this.clientService.getAll().subscribe(
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
      this.shownItems = this.allClientItems;
      this.currentItems = this.allClientItems;
      this.length = this.currentItems.length;
      return;
    }
    filterValue = filterValue.toLowerCase();

    let tmp = this.allClientItems.filter(function(el) {
      
      return  el.person.surname.toLowerCase().includes(filterValue) ||
              el.person.name.toLowerCase().includes(filterValue) ||
              el.person.patronymic.toLowerCase().includes(filterValue) ||
              String(el.person.passports[0].passport).toLowerCase().includes(filterValue) ||
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
    this.length = this.currentItems.length;
    this.show();
  }

  openEditor(data){
    console.log(data);
    this.showingEditor = true;
    this.editedClient = data;
  }

  closeEditor()
  {
    this.updateUsers();
    this.showingEditor = false;
    this.editedClient = undefined;
  }

}


