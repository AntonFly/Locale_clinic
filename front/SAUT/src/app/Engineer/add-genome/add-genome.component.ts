import { Component, OnInit, Input, Output, EventEmitter, ElementRef, ViewChild } from '@angular/core';
import { Client } from 'src/app/_models/Client';
import { Order } from 'src/app/_models/Order';
import { EngineerService } from '../_services';

@Component({
  selector: 'app-add-genome',
  templateUrl: './add-genome.component.html',
  styleUrls: ['./add-genome.component.css']
})
export class AddGenomeComponent implements OnInit {
  @ViewChild('fileDropRef', null) fileInput: ElementRef;
  @Input('client') client: Client;
  @Input('order') order: Order;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();
  
  currentFile: File = undefined;
  isChosen: boolean = false;

  isUploadError: boolean = false;
  uploadMsg: string = "test";
  isDownloadError: boolean = false;
  downloadMsg: string = "test";

  newFileName:string = "";
  fileDate:string = "";

  constructor(private engService: EngineerService) { }

  ngOnInit() {    
    this.setNewName();
  }

  setNewName(){
    if(this.order.genome)
    {
      var a = this.order.genome;
      this.newFileName = a.substring(0, a.indexOf('_'))+a.substring(a.indexOf('.'));
      this.fileDate = a.substring(this.getPosition(a, '_', 2)+1, a.indexOf('.'));      
    }
  }

  getPosition(string, subString, index) {
    return string.split(subString, index).join(subString).length;
  }

  getFIO(){        
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.';
  }
  closeClick() { this.close.emit(true); }

  uploadFiles(file){
    console.log(file);
    this.currentFile = file["0"];
    this.isChosen = true;

    this.fileInput.nativeElement.value = '';
  }

  confirmFile(){
    this.engService.uploadGenome(this.currentFile, this.order.id).subscribe(
      (res : any) =>{
        console.log(res);
        this.order.genome = res.fileName;
        this.setNewName();
      },
      error => {
        this.uploadMsg = "Не удалось загрузить файл";
        this.isUploadError = true;

        setTimeout(() => {
          this.uploadMsg = "";
          this.isUploadError = false;
        }, 5000);            
      }
    )
  }

  cancelFile(){
    this.isChosen = false;
    this.currentFile = undefined;
  }

  downloadFile(){
    this.engService.downloadGenome(this.order.genome, this.newFileName).subscribe(
      res =>{
        console.log(res);
      },
      error => {
        this.downloadMsg = "Не удалось скачать файл";
        this.isDownloadError = true;

        setTimeout(() => {
          this.downloadMsg = "";
          this.isDownloadError = false;
        }, 5000);            
      }
    )
  }

  changeFile(){
    this.order.genome = undefined;
  }

}
