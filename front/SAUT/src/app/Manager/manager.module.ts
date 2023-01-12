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
import {DragDropModule} from '@angular/cdk/drag-drop'
import { MatExpansionModule } from '@angular/material/expansion';
import { DatePipe } from '@angular/common';
import { MatPaginatorModule } from '@angular/material';

import {SharedModule} from '../common/shared.module';

import { ClientsComponent } from './clients/clients-main/clients.component';
import { ClientsDialogComponent } from './clients/clients-dialog/clients-dialog.component';

import { ContractsComponent } from './contracts/contracts.component';
import { ServicesComponent } from './services/services.component';

import { ClientsService, OrderService, StockService } from './_services/index';
import { AdvancedSearchDialogComponent } from './clients/advanced-search-dialog/advanced-search-dialog.component';
import { NewRequestComponent } from './request/new-request/new-request.component';
import { RequestListComponent } from './request/request-list/request-list.component';
import { RequestContainerComponent } from './request/request-container/request-container.component';
import { ClientEditorComponent } from './clients/client-editor/client-editor.component';
import { OrderConfirmationComponent } from './request/order-confirmation/order-confirmation.component'

import { DndDirective } from './_directives/dnd.directive';
import { StockMainComponent } from './stock/stock-main/stock-main.component';
import { AnimationDirective } from './_directives/animation.directive';
 
const routes: Routes = [
    {   path: 'manager', redirectTo: '/manager/clients', pathMatch: 'full'},
    {   path: 'clients',    component: ClientsComponent             },
    {   path: 'contracts',  component: ContractsComponent           },
    {   path: 'requests',   component: RequestContainerComponent    },
    {   path: 'services',   component: ServicesComponent            },
    {   path: 'stock',      component: StockMainComponent           }
];
 
@NgModule({
  declarations: [
    AnimationDirective,
    ClientsComponent, 
    ClientsDialogComponent,
    ContractsComponent,
    ServicesComponent,
    AdvancedSearchDialogComponent,
    NewRequestComponent,
    RequestListComponent,
    RequestContainerComponent,
    ClientEditorComponent,
    OrderConfirmationComponent,
    DndDirective,
    StockMainComponent
  ],
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
    DragDropModule,
    MatExpansionModule
  ],
  providers: [
    DatePipe,
    ClientsService,
    OrderService,
    StockService,    
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' }
  ],
  entryComponents: [ClientsDialogComponent, AdvancedSearchDialogComponent]
})
export class ManagerModule { }