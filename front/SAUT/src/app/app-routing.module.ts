import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotFoundComponent } from './common/not-found/not-found.component';
import { HomeComponent } from './common/home/home.component';
import { LoginComponent } from './common/login/login.component'
import { RoleGuard } from './_guards/role.guard';

const routes: Routes = [
  // { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'admin', loadChildren: () => import('./Administrator/administrator.module').then(m => m.AdministratorModule), canActivate: [RoleGuard] },
  { path: 'manager', loadChildren: () => import('./Manager/manager.module').then(m => m.ManagerModule), canActivate: [RoleGuard]},
  { path: 'engineer', loadChildren: () => import('./Engineer/engineers.module').then(m => m.EngineersModule), canActivate: [RoleGuard]},
  { path: 'scientist', loadChildren: () => import('./Scientist/scientists.module').then(m => m.ScientistsModule), canActivate: [RoleGuard]},
  { path: 'medic', loadChildren: () => import('./Medics/medics.module').then(m => m.MedicsModule), canActivate: [RoleGuard]},
  { path: '**', component: NotFoundComponent }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
