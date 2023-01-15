import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGenomeComponent } from './add-genome.component';

describe('AddGenomeComponent', () => {
  let component: AddGenomeComponent;
  let fixture: ComponentFixture<AddGenomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddGenomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddGenomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
