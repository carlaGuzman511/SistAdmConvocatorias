import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { HomeComMeritsComponent } from "./home-com-merits.component";

describe("HomeComMeritsComponent", () => {
  let component: HomeComMeritsComponent;
  let fixture: ComponentFixture<HomeComMeritsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComMeritsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComMeritsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
