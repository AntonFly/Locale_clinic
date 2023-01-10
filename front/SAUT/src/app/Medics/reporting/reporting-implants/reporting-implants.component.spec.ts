import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportingImplantsComponent } from './reporting-implants.component';

describe('ReportingImplantsComponent', () => {
  let component: ReportingImplantsComponent;
  let fixture: ComponentFixture<ReportingImplantsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportingImplantsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportingImplantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
