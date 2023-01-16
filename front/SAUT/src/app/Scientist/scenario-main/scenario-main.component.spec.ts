import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScenarioMainComponent } from './scenario-main.component';

describe('ScenarioMainComponent', () => {
  let component: ScenarioMainComponent;
  let fixture: ComponentFixture<ScenarioMainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScenarioMainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScenarioMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
