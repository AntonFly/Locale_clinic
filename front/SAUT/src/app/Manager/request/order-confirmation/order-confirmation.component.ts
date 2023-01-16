import { Component, EventEmitter, Input, OnInit, Output, ElementRef, ViewChild} from '@angular/core';
import { Client } from 'src/app/_models/Client';
import { Order } from 'src/app/_models/Order';
import { OrderService } from '../../_services';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrls: ['./order-confirmation.component.css']
})
export class OrderConfirmationComponent implements OnInit {
  @ViewChild('fileDropRef', null) fileInput: ElementRef;
  @Input('order') order: Order;
  @Output('close') close: EventEmitter<boolean> = new EventEmitter();
  
  client: Client;

  currentFile: File = undefined;
  isChosen: boolean = false;

  isUploadError: boolean = false;
  uploadMsg: string = "test";
  isDownloadError: boolean = false;
  downloadMsg: string = "test";

  riskOfferMsg = "";
  riskOfferError = false;

  newFileName:string = "";
  fileDate:string = "";
  
  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.client = this.order.client;    
    this.setNewName();
  }

  setNewName(){
    if(this.order.confirmation)
    {
      var a = this.order.confirmation;
      this.newFileName = a.substring(0, a.indexOf('_'))+a.substring(a.indexOf('.'));
      this.fileDate = a.substring(this.getPosition(a, '_', 2)+1, a.indexOf('.'));      
    }
  }

  getPosition(string, subString, index) {
    return string.split(subString, index).join(subString).length;
  }

  getFIO(){    
    return this.client.person.surname+' '+this.client.person.name.substring(0,1)+'. '+this.client.person.patronymic.substring(0,1)+'.'
  }

  closeClick() { this.close.emit(true); }

  uploadFiles(file){
    console.log(file);
    this.currentFile = file["0"];
    this.isChosen = true;

    this.fileInput.nativeElement.value = '';
  }

  confirmFile(){
    this.orderService.uploadConfirmation(this.currentFile, this.order.id).subscribe(
      (res : any) =>{
        console.log(res);
        this.order.confirmation = res.fileName;
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
    this.orderService.downloadConfirmation('manager/download_confirmation/?file='+this.order.confirmation, this.newFileName).subscribe(
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

  downloadRisks(){
    this.orderService.downloadConfirmation('manager/get_risks/'+this.order.id, "risks_"+this.order.id+".pdf").subscribe(
      res =>{
        console.log(res);
      },
      error => {
        this.riskOfferMsg = "Не удалось скачать риски";
        this.riskOfferError = true;

        setTimeout(() => {
          this.riskOfferMsg = "";
          this.riskOfferError = false;
        }, 5000);            
      }
    )
  }

  downloadOffer(){
    this.orderService.downloadConfirmation('manager/get_commercial/'+this.order.id, "commercial_"+this.order.id+".pdf").subscribe(
      res =>{
        console.log(res);
      },
      error => {
        console.log(error)
        this.riskOfferMsg = "Не удалось скачать предложение";
        this.riskOfferError = true;

        setTimeout(() => {
          this.riskOfferMsg = "";
          this.riskOfferError = false;
        }, 5000);            
      }
    )
  }

  downloadConfirmation(){}

  changeFile(){
    this.order.confirmation = undefined;
  }
}
