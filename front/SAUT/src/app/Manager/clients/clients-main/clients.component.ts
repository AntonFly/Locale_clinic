import { MatDialog, MatDialogRef } from '@angular/material';
import { Component, OnInit } from '@angular/core';
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
  clientItems: Client[];
  clientError: boolean;

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

}
