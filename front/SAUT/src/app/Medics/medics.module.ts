import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon'
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import {MatDividerModule} from '@angular/material/divider';
import {MatMenuModule} from '@angular/material/menu';
import {MatTabsModule} from '@angular/material/tabs';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatPaginatorModule } from '@angular/material';

import { DatePipe } from '@angular/common';

import {ReportingService, SupportService} from './_services/index'

import { SharedModule } from '../common/shared.module';

import { SupportComponent } from './support/support/support.component';
import { ReportingComponent } from './reporting/reporting-main/reporting.component';
import { SupportDialogComponent } from './support/support-dialog/support-dialog.component';
import { ReportingClientsComponent } from './reporting/reporting-clients/reporting-clients.component';
import { ReportingImplantsComponent } from './reporting/reporting-implants/reporting-implants.component';
import { ReportingBodyChangesComponent } from './reporting/reporting-body-changes/reporting-body-changes.component'

 
const routes: Routes = [
    {   path: 'medic', redirectTo: '/medic/support', pathMatch: 'full'},
    {   path: 'support',   component: SupportComponent},
    {   path: 'reporting',   component: ReportingComponent   },    
];
 
@NgModule({
  declarations: [
    
  SupportComponent,
    
  ReportingComponent,
    
  SupportDialogComponent,
    
  ReportingClientsComponent,      
    
  ReportingImplantsComponent,
    
  ReportingBodyChangesComponent],
  imports: [
    SharedModule,
    MatPaginatorModule,
    MatButtonModule,
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,        
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatDialogModule,
    MatSelectModule,
    MatDividerModule,
    MatMenuModule,
    MatTabsModule,
    MatAutocompleteModule,
    MatExpansionModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' },
    SupportService,
    ReportingService,    
    DatePipe    
  ],
  entryComponents: [SupportDialogComponent]
})
export class MedicsModule { }