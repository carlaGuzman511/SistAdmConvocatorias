import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { LaboratoryEvaluationTableComponent } from "./laboratory-evaluation-table.component";

describe("LaboratoryEvaluationTableComponent", () => {
  let component: LaboratoryEvaluationTableComponent;
  let fixture: ComponentFixture<LaboratoryEvaluationTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LaboratoryEvaluationTableComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LaboratoryEvaluationTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
