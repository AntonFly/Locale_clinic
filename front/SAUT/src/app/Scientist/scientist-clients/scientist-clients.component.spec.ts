import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientistClientsComponent } from './scientist-clients.component';

describe('ScientistClientsComponent', () => {
  let component: ScientistClientsComponent;
  let fixture: ComponentFixture<ScientistClientsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientistClientsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientistClientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
