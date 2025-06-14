import { TestBed, async, inject } from "@angular/core/testing";

import { SecretaryGuard } from "./secretary.guard";

describe("SecretaryGuard", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SecretaryGuard],
    });
  });

  it("should ...", inject([SecretaryGuard], (guard: SecretaryGuard) => {
    expect(guard).toBeTruthy();
  }));
});
