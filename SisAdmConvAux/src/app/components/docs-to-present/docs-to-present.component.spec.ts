import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { DocsToPresentComponent } from "./docs-to-present.component";

describe("DocsToPresentComponent", () => {
  let component: DocsToPresentComponent;
  let fixture: ComponentFixture<DocsToPresentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DocsToPresentComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DocsToPresentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
