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
import {NewUserComponent} from './users/new-user/new-user.component';
import { PassRecoveryComponent } from './users/pass-recovery/pass-recovery.component'
import { AdminService } from './_services/admin.service'
import { DatePipe } from '@angular/common';
import {SharedModule} from '../common/shared.module';
import { RecoverDialogComponent } from './users/recover-dialog/recover-dialog.component'

import { AnimationDirective } from './_directives/animation.directive'

  
const routes: Routes = [
    {   path: 'admin', redirectTo: '/admin/addUser', pathMatch: 'full'},
    {   path: 'addUser',   component: NewUserComponent   },
    {   path: 'recoverPass',   component: PassRecoveryComponent },    
];
 
@NgModule({
  declarations: [NewUserComponent, PassRecoveryComponent, RecoverDialogComponent, AnimationDirective],
  imports: [
    MatButtonModule,
    CommonModule,
    SharedModule,
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
    MatDividerModule
  ],
  providers: [
    AdminService,
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' },
    DatePipe    
  ],
  entryComponents: [RecoverDialogComponent]
})
export class AdministratorModule { }