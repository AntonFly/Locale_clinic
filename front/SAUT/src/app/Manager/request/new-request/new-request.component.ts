import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {RequestService } from '../../_services/request.service'
import {map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-new-request',
  templateUrl: './new-request.component.html',
  styleUrls: ['./new-request.component.css']
})
export class NewRequestComponent implements OnInit {
  
  RequestForm: FormGroup;   
  
  optionsSpec: string[];
  filteredOptionsSpec: Observable<string[]>;

  constructor( private requestService: RequestService,  private fb: FormBuilder) { }

  ngOnInit() {
    this.biuldForm();
    this.optionsSpec = this.requestService.getSpecializations();
    this.filteredOptionsSpec = this.formControlSpec.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  validation_messages = {
    'Id': [
      { type: 'required', message: 'Требуется номер клиента' }
    ],

    'Specialization': [
      { type: 'required', message: 'Требуется спецификация' }
    ],
    'Modification': [
      { type: 'required', message: 'Требуется модификация' }
    ]
  }

  biuldForm(){
    this.RequestForm = this.fb.group({
      Id: ['', Validators.required],
      Specialization: ['', Validators.required],      
      Modification: ['', Validators.required],      
    })
  }
  get formControlId() {
    return this.RequestForm.get('Id');
  }

  get formControlSpec() {
    return this.RequestForm.get('Specialization');
  }

  get formControlMod() {
    return this.RequestForm.get('Modification');
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.optionsSpec.filter(option => option.toLowerCase().includes(filterValue));
  }

  onSubmitRequest(event: any){
      console.log('Request submit '+event)
  }

}
