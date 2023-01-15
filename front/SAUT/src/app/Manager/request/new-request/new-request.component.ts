import { MatDialog, MatDialogRef } from '@angular/material';
import {ClientsDialogComponent} from '../../clients/clients-dialog/clients-dialog.component'
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

import { OrderService } from '../../_services/orders.service'
import { ClientsService } from '../../_services/clients.service'

import { Client } from '../../../_models/Client'
import { Order, Mod, Spec } from '../../../_models/Order';

@Component({
  selector: 'app-new-request',
  templateUrl: './new-request.component.html',
  styleUrls: ['./new-request.component.css']
})
export class NewRequestComponent implements OnInit {
  
  ClientsDialogRef: MatDialogRef<ClientsDialogComponent>;

  RequestForm: FormGroup;

  panelOpenState = false;

  toSelect = [];
  selected = [];
  
  currentClient: Client;
  isClientError: boolean = false;
  clientErrorMsg: string = "";

  specList: Spec[];
  isSpecError: boolean = false;
  specErrorMsg: string = "";
  
  currentMods: Mod[];
  isModsError: boolean = false;
  modsErrorMsg: string = "";

  isFormError: boolean = false;
  formMsg: string = "";

  optionsSpec: String[] = [];
  filteredOptionsSpec: Observable<String[]>;
  nameToId= {};

  constructor(  
                private dialog: MatDialog,
                private orderService: OrderService, 
                private clientService: ClientsService,
                private fb: FormBuilder) { }

  ngOnInit() {
    this.selected = [];
    this.optionsSpec = [];
    this.nameToId= {};      

    this.biuldForm();
    this.updateSpecs();

    this.filteredOptionsSpec = this.formControlSpec.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  validation_messages = {
    'passport': [
      { type: 'required', message: 'Требуется номер клиента' }
    ],

    'Specialization': [
      { type: 'required', message: 'Требуется специализация' }
    ],
    'Modification': [
      { type: 'required', message: 'Требуется модификация' }
    ]
  }

  biuldForm(){
    this.RequestForm = this.fb.group({
      passport: ['', Validators.required],
      Specialization: ['', Validators.required],
      comment:['', Validators.maxLength]            
    })
  }
  get formControlPassport() {
    return this.RequestForm.get('passport');
  }

  get formControlSpec() {
    return this.RequestForm.get('Specialization');
  }

  get formControlMod() {
    return this.RequestForm.get('Modification');
  }

  private _filter(value: string): String[] {
    const filterValue = value.toLowerCase();

    return this.optionsSpec.filter(option => option.toLowerCase().includes(filterValue));
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
    }
  }  

  getFIO(client:any){    
    return client.surname+' '+client.name.substring(0,1)+'. '+client.patronymic.substring(0,1)+'.'
  }

  checkErrors(){
    return this.isClientError || this.isModsError || this.isSpecError || this.selected.length===0;
  }

  clearForm(f:any){    
    Object.keys(f.controls).forEach(key => {
      f.controls[key].reset();
      f.controls[key].clearValidators();
      f.controls[key].markAsPristine();
      f.controls[key].markAsUntouched();
      f.controls[key].updateValueAndValidity();                    
    })  
    f.reset();
    f.clearValidators();
    f.markAsPristine();
    f.markAsUntouched();
    f.updateValueAndValidity();                  
    this.selected = [];
    this.toSelect = [];
    this.currentClient = undefined;
  }

  openClientDialog(){
    this.ClientsDialogRef = this.dialog.open(ClientsDialogComponent,{
      hasBackdrop:true
    });

    this.ClientsDialogRef
      .afterClosed()
      .pipe()
      .subscribe(closed => {/*this.updateUsers()*/})

  }

  //~~~~~~~~~~~~~~~~REQUESTS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
  onSubmitRequest(event: any){      
      var modIds = this.selected.map(el => { return this.modsToId[el]});

      this.orderService.createOrder(this.currentClient.id,this.nameToId[event.Specialization],event.comment,modIds).subscribe(
        result => {          
          this.formMsg = "Заявка успешно добавлена";
          this.isFormError = false;

          setTimeout( () =>
            this.formMsg = ""
          , 5000);
          this.clearForm(this.RequestForm);
          

        },
        error => {          
          this.isFormError = true;
          if(error.error.status === "CONFLICT")
            this.formMsg = error.error.message;
          else
          this.formMsg = "Не удалось создать заявку";

          setTimeout(() => {
            this.formMsg = "";
            this.isFormError = false;
          }, 5000);            
        }

      );
  }

  updateSpecs(){
    this.orderService.getSpecializations().subscribe(
      result => {        
        result.forEach(item => {
          this.optionsSpec.push(item.name);
          this.nameToId[item.name] = item.id;
        });
        this.specList = result;
        this.isSpecError = false;        
        this.specErrorMsg = "";
      },
      error => {        
        // this.isSpecError = true;
        // this.
        console.log("Не удалось получить список специализаций");
      });
  }

  clientExists(id: any) {
    if(id=="" || id===NaN || id === undefined)
    {
      this.isClientError = false;
      this.clientErrorMsg = "";
      this.currentClient = undefined;
      return;
    }

    this.clientService.getClient(parseInt(id)).subscribe(
      result => {                              
        this.isClientError = false;
        this.clientErrorMsg = "";
        this.currentClient = result;
      },
      error => {                
        this.isClientError = true;
        
        if(error.error.status == "NOT_FOUND")
          this.clientErrorMsg = "Такого клиента не существует";
        else this.clientErrorMsg = "Не удалось найти клиента";

        this.currentClient = undefined;
      });        
  }

  modsToId = {};
  getModsBySpec(){
    let spec = this.formControlSpec.value;    
    if( this.optionsSpec.indexOf(spec) > -1)
    {    
      this.isSpecError = false;
      this.specErrorMsg = "";

      this.orderService.getModsBySpec(this.nameToId[spec]).subscribe(
        result => {                                          
          this.toSelect = [];
          this.selected = [];

          this.currentMods = result;
          result.forEach(item => {            
            this.toSelect.push(item.name);
            this.modsToId[item.name] = item.id;
          });
          this.isModsError = false;
          this.modsErrorMsg = "";
        },
        error => {                  
          console.log(error);                  
          this.toSelect = [];
          this.selected = [];
          this.currentMods = [];

          this.isModsError = true;
          this.modsErrorMsg = "Не удалось получить список модификаций"
        });            
    }
    else 
    {
      this.isSpecError = true;
      this.specErrorMsg = "Такой специализации не существует";
    }
  }
  checkPatch(opt){    
    this.RequestForm.patchValue({Specialization: opt})
    this.getModsBySpec();
  }

}
