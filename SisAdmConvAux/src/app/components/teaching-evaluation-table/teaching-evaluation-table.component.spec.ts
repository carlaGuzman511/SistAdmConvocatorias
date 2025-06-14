import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { TeachingEvaluationTableComponent } from "./teaching-evaluation-table.component";

describe("TeachingEvaluationTableComponent", () => {
  let component: TeachingEvaluationTableComponent;
  let fixture: ComponentFixture<TeachingEvaluationTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TeachingEvaluationTableComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeachingEvaluationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
