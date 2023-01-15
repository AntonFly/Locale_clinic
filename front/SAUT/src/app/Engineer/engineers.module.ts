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
import { EngineerMainComponent } from './engineer-main/engineer-main.component';
import { AddGenomeComponent } from './add-genome/add-genome.component';
import { EngineerService } from './_services';
 
const routes: Routes = [
    {   path: '', redirectTo: '/engineer/main', pathMatch: 'full'},
    {   path: 'main',   component: EngineerMainComponent},
    // {   path: 'reporting',   component: ReportingComponent   },
    // {   path: 'requests',   component: RequestContainerComponent   },
    // {   path: 'services',   component: ServicesComponent   },
];
 
@NgModule({
  declarations: [
      EngineerMainComponent,
      AddGenomeComponent],
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
    EngineerService,
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' }
  ],
  entryComponents: []
})
export class EngineersModule { }