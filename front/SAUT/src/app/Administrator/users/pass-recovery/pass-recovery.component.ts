import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../_services';
import { MatDialog, MatDialogRef } from '@angular/material';
import { RecoverDialogComponent } from '../recover-dialog/recover-dialog.component';

@Component({
  selector: 'app-pass-recovery',
  templateUrl: './pass-recovery.component.html',
  styleUrls: ['./pass-recovery.component.css']
})
export class PassRecoveryComponent implements OnInit {

  loading: boolean = true;
  events = undefined;

  constructor(private dialog: MatDialog, private adminService:AdminService) { }

  ngOnInit() {
    this.refreshList();
  }

  refreshList()
  {
    this.loading = true

    this.adminService.getToRecover().subscribe(
      (data:any) =>
      {
        this.events = data;
        this.loading = false
      },
      error => {this.loading = false; console.log(error)}
    )
  }

  openDialog(event)
  {
    const dialogRef = this.dialog.open
      (
        RecoverDialogComponent,{
          hasBackdrop:true,
          width:'30%', 
          // height:'27.78%',
          data:event          
        },
      );
  }
}
