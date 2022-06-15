import { MatDialog, MatDialogRef } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import {ClientsDialogComponent} from '../clients-dialog/clients-dialog.component'
import { AdvancedSearchDialogComponent } from '../advanced-search-dialog/advanced-search-dialog.component'
import {ClientsService} from '../../_services/clients.service'
import {Client} from '../../../_models/Client'

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  ClientsDialogRef: MatDialogRef<ClientsDialogComponent>;
  AdvancedDialogRef: MatDialogRef<AdvancedSearchDialogComponent>;
  allClientItems: Client[];
  filteredClientItems: Client[];
  clientItems: Client[];
  clientError: boolean;
  searchField = new FormControl('');  

  constructor(private dialog: MatDialog, private clientService: ClientsService) {this.updateUsers() }

  ngOnInit() {  
    this.clientError = false;  
  }
  
  openClientDialog(){
    this.ClientsDialogRef = this.dialog.open(ClientsDialogComponent,{
      hasBackdrop:true
    });

    this.ClientsDialogRef
      .afterClosed()
      .pipe()
      .subscribe(closed => {this.updateUsers()})

  }

  openAdvancedDialog(){
    this.AdvancedDialogRef = this.dialog.open(AdvancedSearchDialogComponent,{
      hasBackdrop:true
    });
  }

  updateUsers(){
    this.clientService.getAll().subscribe(
      result =>{
        this.allClientItems = result;
        this.clientItems = result;
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
    this.clientItems = undefined;
    let filterValue = this.searchField.value
    
    if(filterValue == "" || filterValue == undefined || filterValue == null)
    {
      this.clientItems = this.allClientItems
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
    
    this.clientItems = tmp;
  }
  searchReset(){
    this.searchField.reset();    
    this.searchField.clearValidators();
    this.searchField.markAsPristine();
    this.searchField.markAsUntouched();
    this.searchField.updateValueAndValidity();
    this.clientItems = this.allClientItems;
  }

}
