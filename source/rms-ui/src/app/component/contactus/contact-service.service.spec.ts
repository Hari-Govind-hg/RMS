import { TestBed } from '@angular/core/testing';
import { contactServiceService } from './contact-service.service';



describe('ContactServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: contactServiceService = TestBed.get(contactServiceService);
    expect(service).toBeTruthy();
  });
});
