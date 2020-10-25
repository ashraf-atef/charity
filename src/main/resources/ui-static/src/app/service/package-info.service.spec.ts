import { TestBed } from '@angular/core/testing';

import { PackageInfoService } from './package-info.service';

describe('PackageInfoService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PackageInfoService = TestBed.get(PackageInfoService);
    expect(service).toBeTruthy();
  });
});
