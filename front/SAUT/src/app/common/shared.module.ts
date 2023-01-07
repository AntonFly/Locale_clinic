import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { environment } from '@env/environment';
import { LoaderComponent } from './loader/loader.component';

@NgModule({
  declarations: [
    LoaderComponent
  ],
  imports: [

  ],
  exports: [
    LoaderComponent
  ]  
})
export class SharedModule { }
