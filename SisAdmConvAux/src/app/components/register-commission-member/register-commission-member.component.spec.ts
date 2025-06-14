import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterCommissionMemberComponent } from "./register-commission-member.component";

describe("RegisterCommissionMemberComponent", () => {
  let component: RegisterCommissionMemberComponent;
  let fixture: ComponentFixture<RegisterCommissionMemberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterCommissionMemberComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterCommissionMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
