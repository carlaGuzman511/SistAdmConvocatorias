import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { AdminNoEnableComponent } from "./admin-no-enable.component";

describe("AdminNoEnableComponent", () => {
  let component: AdminNoEnableComponent;
  let fixture: ComponentFixture<AdminNoEnableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AdminNoEnableComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminNoEnableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
