import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Inject } from '@angular/core';
import { AdminService } from '../../_services';

@Component({
  selector: 'app-recover-dialog',
  templateUrl: './recover-dialog.component.html',
  styleUrls: ['./recover-dialog.component.css']
})
export class RecoverDialogComponent implements OnInit {

  loading: boolean = false;
  loadingComplete: boolean = false;
  link: string;
  resultMsg: string;

  constructor(
    private service: AdminService,
    public dialogRef: MatDialogRef<RecoverDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
    ) { }

  ngOnInit() {
    console.log(this.data);
    this.link = "https://outlook.office.com/owa/?path=/mail/action/compose&to="+this.data.email+"&subject=Восстановление%20пароля&body="+"Здравствуйте, "+this.data.name+"!"
  }

  closeDialog() {
    this.dialogRef.close();
  }

  generate()
  {
    this.dialogRef.updateSize('30%', '27.78%');
    this.loading = true;
    this.service.generatePass(this.data).subscribe(
      result => {
        this.loading = false;
        this.loadingComplete = true;
        this.resultMsg = "Пароль успешно сгенерирован";
      },
      error => {
        this.loading = false;
        this.loadingComplete = true;
        this.resultMsg = "Произошла ошибка, повторите попытку позже";
      }
    )
    {

    }
  }
}
