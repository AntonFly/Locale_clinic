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

  // constructor(http: HttpClient, @Inject('BASE_URL') baseUrl: string, private dialog: MatDialog) {
  //   http.get<QueueItem[]>(baseUrl + 'download').subscribe(result => {
  //     this.queueItems = result;
  //     console.log(this.queueItems);      
  //   }, error => console.error(error));
  // }

  constructor(private dialog: MatDialog, private clientService: ClientsService) { }

  ngOnInit() {
    this.clientItems = this.clientService.getAll();
  }
  
  openClientDialog(){
    this.ClientsDialogRef = this.dialog.open(ClientsDialogComponent,{
      hasBackdrop:true
    });

    this.ClientsDialogRef
      .afterClosed()
      .pipe()
      .subscribe(closed => {this.clientItems = this.clientService.getAll();})

  }

  openAdvancedDialog(){
    this.AdvancedDialogRef = this.dialog.open(AdvancedSearchDialogComponent,{
      hasBackdrop:true
    });
  }

}
