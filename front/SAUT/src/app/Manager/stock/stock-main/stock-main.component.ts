import { Component, OnInit } from '@angular/core';
import { Stock } from 'src/app/_models/Stock';
import { StockService } from '../../_services';

@Component({
  selector: 'app-stock-main',
  templateUrl: './stock-main.component.html',
  styleUrls: ['./stock-main.component.css']
})
export class StockMainComponent implements OnInit {

  columns_schema = [
    {
      key: 'name',
      type: 'text',
      label: 'Наименование'
    },
    {
      key: 'description',
      type: 'text',
      label: 'Описание'
    },
    {
      key: 'vendor_id',
      type: 'number',
      label: 'Артикул'
    },
    {
      key: 'amount',
      type: 'number',
      label: 'Количество',
      highlight: 'amount_highlight',
      amount_highlight: function(){return this.amount < this.minAmount}
    },    
    {
      key: 'minAmount',
      type: 'number',
      label: 'Минимально кол-во'
    },  
    {
      key: 'isEdit',
      type: 'isEdit',
      label: '',
    },
  ];
  deletable = function(row) {
    if(row.stockId)
      return false;
    else return true;
  }

  isGlobalError: boolean = false;

  currentStock:Stock[] = undefined;
  changedStock: any[] = undefined;

  currentUserId: number;

  updateMsg: string = "";
  isUpdateError: boolean = false;

  constructor(private stockService: StockService) { }

  ngOnInit() {
    this.currentUserId = JSON.parse(localStorage.getItem("token")).id;    
    this.refreshList();
  }

  refreshList(){
    this.stockService.getAllStock().subscribe(
      (res: Stock[]) => {
        res.forEach(el => {
          el["id"] = el.stockId.id;
          el["vendor_id"] = el.stockId.vendor;
          el["user_id"] = this.currentUserId;
          // el["highlight"] = function(){return this.amount < this.minAmount}
        })
        this.currentStock = res
        
        console.log(this.currentStock);
      },
      error => {
        this.isUpdateError = true;
        this.updateMsg = "Не удалось получить список";        
        setTimeout(() => {
          this.updateMsg = "";
          this.isUpdateError = false;
        }, 5000);            
      }
    )
  }

  submitChanges(){
    this.changedStock.forEach(el => {      
      el["user_id"] = this.currentUserId;
    });

    this.stockService.submitStock(this.changedStock).subscribe(
      res =>{
        console.log(res)
        this.refreshList();
        this.isUpdateError = false;
        this.updateMsg = "Успешно сохранено";
        setTimeout(() => {
          this.updateMsg = "";
          this.isUpdateError = false;
        }, 5000);  
      },
      err => {
        console.log(err)
        this.isUpdateError = true;
        this.updateMsg = err.error.message;
        setTimeout(() => {
          this.updateMsg = "";
          this.isUpdateError = false;
        }, 5000);  
      })
  }

  previousChanged(event)
  {
    this.changedStock = event;
    console.log(event);
  }

}
