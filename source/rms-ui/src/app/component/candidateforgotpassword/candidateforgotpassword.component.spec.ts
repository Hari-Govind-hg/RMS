import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateforgotpasswordComponent } from './candidateforgotpassword.component';

describe('CandidateforgotpasswordComponent', () => {
  let component: CandidateforgotpasswordComponent;
  let fixture: ComponentFixture<CandidateforgotpasswordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CandidateforgotpasswordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidateforgotpasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
