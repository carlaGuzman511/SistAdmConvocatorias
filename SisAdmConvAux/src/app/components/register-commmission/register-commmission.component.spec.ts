import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterCommmissionComponent } from "./register-commmission.component";

describe("RegisterCommmissionComponent", () => {
  let component: RegisterCommmissionComponent;
  let fixture: ComponentFixture<RegisterCommmissionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterCommmissionComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterCommmissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
