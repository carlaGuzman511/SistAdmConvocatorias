import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { MeritCalificationComponent } from "./merit-calification.component";

describe("MeritCalificationComponent", () => {
  let component: MeritCalificationComponent;
  let fixture: ComponentFixture<MeritCalificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MeritCalificationComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MeritCalificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
