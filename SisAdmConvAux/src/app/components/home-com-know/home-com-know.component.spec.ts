import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { HomeComKnowComponent } from "./home-com-know.component";

describe("HomeComKnowComponent", () => {
  let component: HomeComKnowComponent;
  let fixture: ComponentFixture<HomeComKnowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComKnowComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComKnowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
