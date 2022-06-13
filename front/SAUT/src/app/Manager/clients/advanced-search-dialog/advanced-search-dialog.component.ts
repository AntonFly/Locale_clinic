import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material';

@Component({
  selector: 'app-advanced-search-dialog',
  templateUrl: './advanced-search-dialog.component.html',
  styleUrls: ['./advanced-search-dialog.component.css']
})
export class AdvancedSearchDialogComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<AdvancedSearchDialogComponent>) { }

  ngOnInit() {
  }

}
