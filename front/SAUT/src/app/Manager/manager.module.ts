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


import { ClientsComponent } from './clients/clients-main/clients.component';
import { ClientsDialogComponent } from './clients/clients-dialog/clients-dialog.component';

import { ContractsComponent } from './contracts/contracts.component';
import { ServicesComponent } from './services/services.component';

import { ManagerGuard } from '../_guards/manager.guard'
import { ClientsService, RequestService } from './_services/index';
import { AdvancedSearchDialogComponent } from './clients/advanced-search-dialog/advanced-search-dialog.component';
import { NewRequestComponent } from './request/new-request/new-request.component';
import { RequestListComponent } from './request/request-list/request-list.component';
import { RequestContainerComponent } from './request/request-container/request-container.component'
 
const routes: Routes = [
    {   path: 'clients',   component: ClientsComponent, canActivate:[ManagerGuard]   },
    {   path: 'contracts',   component: ContractsComponent   },
    {   path: 'requests',   component: RequestContainerComponent   },
    {   path: 'services',   component: ServicesComponent   },
];
 
@NgModule({
  declarations: [
    ClientsComponent, 
    ClientsDialogComponent,
    ContractsComponent,
    ServicesComponent,
    AdvancedSearchDialogComponent,
    NewRequestComponent,
    RequestListComponent,
    RequestContainerComponent
  ],
  imports: [
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
    MatAutocompleteModule
  ],
  providers: [
    ClientsService,
    RequestService,
    ManagerGuard,
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' }
  ],
  entryComponents: [ClientsDialogComponent, AdvancedSearchDialogComponent]
})
export class ManagerModule { }