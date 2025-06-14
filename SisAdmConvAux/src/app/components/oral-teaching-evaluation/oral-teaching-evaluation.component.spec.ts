import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { OralTeachingEvaluationComponent } from "./oral-teaching-evaluation.component";

describe("OralTeachingEvaluationComponent", () => {
  let component: OralTeachingEvaluationComponent;
  let fixture: ComponentFixture<OralTeachingEvaluationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OralTeachingEvaluationComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OralTeachingEvaluationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
