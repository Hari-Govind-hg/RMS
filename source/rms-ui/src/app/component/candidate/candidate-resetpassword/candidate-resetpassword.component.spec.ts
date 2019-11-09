import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateResetpasswordComponent } from './candidate-resetpassword.component';

describe('CandidateResetpasswordComponent', () => {
  let component: CandidateResetpasswordComponent;
  let fixture: ComponentFixture<CandidateResetpasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateResetpasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateResetpasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
