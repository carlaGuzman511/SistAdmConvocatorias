import { TestBed, async, inject } from "@angular/core/testing";

import { ComknowGuard } from "./comknow.guard";

describe("ComknowGuard", () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ComknowGuard],
    });
  });

  it("should ...", inject([ComknowGuard], (guard: ComknowGuard) => {
    expect(guard).toBeTruthy();
  }));
});
