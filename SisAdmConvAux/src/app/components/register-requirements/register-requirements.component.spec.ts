import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterRequirementsComponent } from "./register-requirements.component";

describe("RegisterRequirementsComponent", () => {
  let component: RegisterRequirementsComponent;
  let fixture: ComponentFixture<RegisterRequirementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterRequirementsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterRequirementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
