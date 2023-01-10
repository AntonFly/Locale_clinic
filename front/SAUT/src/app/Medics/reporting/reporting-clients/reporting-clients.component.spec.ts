import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportingClientsComponent } from './reporting-clients.component';

describe('ReportingClientsComponent', () => {
  let component: ReportingClientsComponent;
  let fixture: ComponentFixture<ReportingClientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportingClientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportingClientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
