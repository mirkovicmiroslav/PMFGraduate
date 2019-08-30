/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { GraduatePaperService } from './graduatePaper.service';

describe('Service: GraduatePaper', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GraduatePaperService]
    });
  });

  it('should ...', inject([GraduatePaperService], (service: GraduatePaperService) => {
    expect(service).toBeTruthy();
  }));
});
