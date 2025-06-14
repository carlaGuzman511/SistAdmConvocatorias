import { TestBed, async, inject } from "@angular/core/testing";

import { CommeritsGuard } from "./commerits.guard";

describe("CommeritsGuard", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommeritsGuard],
    });
  });

  it("should ...", inject([CommeritsGuard], (guard: CommeritsGuard) => {
    expect(guard).toBeTruthy();
  }));
});
