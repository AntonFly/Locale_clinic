import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerMainComponent } from './engineer-main.component';

describe('EngineerMainComponent', () => {
  let component: EngineerMainComponent;
  let fixture: ComponentFixture<EngineerMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EngineerMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EngineerMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
