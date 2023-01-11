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
      key: 'vendor',
      type: 'text',
      label: 'Вендор'
    },
    {
      key: 'amount',
      type: 'number',
      label: 'Количество'
    },
    {
      key: 'description',
      type: 'text',
      label: 'Описание'
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

  isGlobalError: boolean = false;

  currentStock:Stock[] = undefined;

  constructor(private stockService: StockService) { }

  ngOnInit() {
    this.stockService.getAllStock().subscribe(
      (res: Stock[]) => {
        this.currentStock = res
        console.log(res);
      },
      error => {this.isGlobalError = true;}
    )
  }

  previousChanged(event)
  {
    console.log(event);
  }

}
