import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportingBodyChangesComponent } from './reporting-body-changes.component';

describe('ReportingBodyChangesComponent', () => {
  let component: ReportingBodyChangesComponent;
  let fixture: ComponentFixture<ReportingBodyChangesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportingBodyChangesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportingBodyChangesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
