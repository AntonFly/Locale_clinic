import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { environment } from '@env/environment';


/* MATERIAL */
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon'
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import {MatDividerModule} from '@angular/material/divider';
import {MatMenuModule} from '@angular/material/menu';

/*SERVICES*/
import {AuthenticationService} from './_services'

/* COMPONENTS */
import { AppComponent } from './app.component';
import { NotFoundComponent } from './common/not-found/not-found.component';
import { HeaderComponent } from './common/header/header.component';
import { HomeComponent } from './common/home/home.component';
import { LoginComponent } from './common/login/login.component'

/* MODULES */
import { AppRoutingModule } from './app-routing.module';
import {ManagerModule} from './Manager/manager.module';
import {AdministratorModule} from './Administrator/administrator.module';
import {MedicsModule} from './Medics/medics.module';
import { ScientistsModule } from './Scientist/scientists.module';
import { EngineersModule } from './Engineer/engineers.module';

/* GUARDS */
import {RoleGuard} from './_guards/role.guard'
import { RootGuard } from './_guards/root.guard';

/* INTERCEPTORS */
import { AuthInterceptor } from './_interceptors/authInterceptor';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    HeaderComponent,
    HomeComponent,
    LoginComponent
  ],
  imports: [
    ManagerModule,
    AdministratorModule,
    MedicsModule,
    ScientistsModule,
    EngineersModule,
    BrowserModule,
    AppRoutingModule,
    // BrowserModule.withServerTransition({ appId: 'ng-cli-universal' }),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,    
    BrowserAnimationsModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatDialogModule,
    MatSelectModule,
    MatDividerModule,
    MatMenuModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    AuthenticationService,
    { provide: MAT_DATE_LOCALE, useValue: 'ru-Ru' },
    { provide: 'BASE_URL', useValue: environment.apiRoot },
    RoleGuard,
    RootGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
