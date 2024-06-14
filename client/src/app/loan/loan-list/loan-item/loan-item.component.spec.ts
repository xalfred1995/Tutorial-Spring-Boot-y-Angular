import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanItemComponent } from './loan-item.component';

describe('LoanItemComponent', () => {
  let component: LoanItemComponent;
  let fixture: ComponentFixture<LoanItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoanItemComponent]
    });
    fixture = TestBed.createComponent(LoanItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
