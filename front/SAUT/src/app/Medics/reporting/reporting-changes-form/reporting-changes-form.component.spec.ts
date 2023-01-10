import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportingChangesFormComponent } from './reporting-changes-form.component';

describe('ReportingChangesFormComponent', () => {
  let component: ReportingChangesFormComponent;
  let fixture: ComponentFixture<ReportingChangesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportingChangesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportingChangesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
