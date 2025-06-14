import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterRequisitosComponent } from "./register-requisitos.component";

describe("RegisterRequisitosComponent", () => {
  let component: RegisterRequisitosComponent;
  let fixture: ComponentFixture<RegisterRequisitosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterRequisitosComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterRequisitosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
