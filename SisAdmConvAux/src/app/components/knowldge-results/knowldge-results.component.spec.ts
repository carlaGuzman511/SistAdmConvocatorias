import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { KnowldgeResultsComponent } from "./knowldge-results.component";

describe("KnowldgeResultsComponent", () => {
  let component: KnowldgeResultsComponent;
  let fixture: ComponentFixture<KnowldgeResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [KnowldgeResultsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowldgeResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
