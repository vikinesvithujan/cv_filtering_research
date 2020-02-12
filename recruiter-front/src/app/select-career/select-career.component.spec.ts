import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectCareerComponent } from './select-career.component';

describe('SelectCareerComponent', () => {
  let component: SelectCareerComponent;
  let fixture: ComponentFixture<SelectCareerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectCareerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectCareerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
