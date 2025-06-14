import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { TeachingLabelComponent } from "./teaching-label.component";

describe("TeachingLabelComponent", () => {
  let component: TeachingLabelComponent;
  let fixture: ComponentFixture<TeachingLabelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TeachingLabelComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeachingLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
