import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { AdminEnableNoEnableComponent } from "./admin-enable-no-enable.component";

describe("AdminEnableNoEnableComponent", () => {
  let component: AdminEnableNoEnableComponent;
  let fixture: ComponentFixture<AdminEnableNoEnableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AdminEnableNoEnableComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminEnableNoEnableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
