import { TestBed } from '@angular/core/testing';
import { contactReplyService } from './contact-reply.service';




describe('ContactReplyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: contactReplyService = TestBed.get(contactReplyService);
    expect(service).toBeTruthy();
  });
});
