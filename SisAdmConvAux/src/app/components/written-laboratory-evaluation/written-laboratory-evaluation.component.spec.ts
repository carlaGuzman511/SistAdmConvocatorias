import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { WrittenLaboratoryEvaluationComponent } from "./written-laboratory-evaluation.component";

describe("WrittenLaboratoryEvaluationComponent", () => {
  let component: WrittenLaboratoryEvaluationComponent;
  let fixture: ComponentFixture<WrittenLaboratoryEvaluationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [WrittenLaboratoryEvaluationComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WrittenLaboratoryEvaluationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
