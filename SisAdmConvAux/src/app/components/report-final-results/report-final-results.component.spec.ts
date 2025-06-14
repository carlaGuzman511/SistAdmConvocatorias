import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ReportFinalResultsComponent } from "./report-final-results.component";

describe("ReportFinalResultsComponent", () => {
  let component: ReportFinalResultsComponent;
  let fixture: ComponentFixture<ReportFinalResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ReportFinalResultsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportFinalResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
