import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterTeachingRequirementsComponent } from "./register-teaching-requirements.component";

describe("RegisterTeachingRequirementsComponent", () => {
  let component: RegisterTeachingRequirementsComponent;
  let fixture: ComponentFixture<RegisterTeachingRequirementsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterTeachingRequirementsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterTeachingRequirementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
