import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PriorityBoxComponent } from './priority-box.component';

describe('PriorityBoxComponent', () => {
  let component: PriorityBoxComponent;
  let fixture: ComponentFixture<PriorityBoxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PriorityBoxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PriorityBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
