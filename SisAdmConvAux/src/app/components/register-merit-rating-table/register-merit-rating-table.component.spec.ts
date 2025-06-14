import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterMeritRatingTableComponent } from "./register-merit-rating-table.component";

describe("RegisterMeritRatingTableComponent", () => {
  let component: RegisterMeritRatingTableComponent;
  let fixture: ComponentFixture<RegisterMeritRatingTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterMeritRatingTableComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterMeritRatingTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
