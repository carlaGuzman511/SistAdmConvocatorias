import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { KnowldgeDetailsDocComponent } from "./knowldge-details-doc.component";

describe("KnowldgeDetailsDocComponent", () => {
  let component: KnowldgeDetailsDocComponent;
  let fixture: ComponentFixture<KnowldgeDetailsDocComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [KnowldgeDetailsDocComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowldgeDetailsDocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
