import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientistClientsInfoComponent } from './scientist-clients-info.component';

describe('ScientistClientsInfoComponent', () => {
  let component: ScientistClientsInfoComponent;
  let fixture: ComponentFixture<ScientistClientsInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientistClientsInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientistClientsInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
