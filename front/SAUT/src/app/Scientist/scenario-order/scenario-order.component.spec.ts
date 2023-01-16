import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScenarioOrderComponent } from './scenario-order.component';

describe('ScenarioOrderComponent', () => {
  let component: ScenarioOrderComponent;
  let fixture: ComponentFixture<ScenarioOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScenarioOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScenarioOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
