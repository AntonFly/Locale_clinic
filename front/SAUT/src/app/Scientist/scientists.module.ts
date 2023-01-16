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
import { ScientistClientsComponent } from './scientist-clients/scientist-clients.component';
import { ScientistClientsInfoComponent } from './scientist-clients-info/scientist-clients-info.component';
import { ScenarioMainComponent } from './scenario-main/scenario-main.component';


 
const routes: Routes = [
  {   path: '', redirectTo: '/scientist/scenario', pathMatch: 'full'},
  {   path: 'scenario',   component: ScenarioMainComponent},
    // {   path: 'reporting',   component: ReportingComponent   },
    // {   path: 'requests',   component: RequestContainerComponent   },
    // {   path: 'services',   component: ServicesComponent   },
];
 
@NgModule({
  declarations: [
   ScientistClientsComponent,
   ScientistClientsInfoComponent,
   ScenarioMainComponent],
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
    MatAutocompleteModule,
    MatExpansionModule
  ],
  providers: [    
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' }
  ],
  entryComponents: []
})
export class ScientistsModule { }