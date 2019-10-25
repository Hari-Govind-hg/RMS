import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateRegisterDetailsComponent } from './candidate-register-details.component';

describe('CandidateRegisterDetailsComponent', () => {
  let component: CandidateRegisterDetailsComponent;
  let fixture: ComponentFixture<CandidateRegisterDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateRegisterDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateRegisterDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
