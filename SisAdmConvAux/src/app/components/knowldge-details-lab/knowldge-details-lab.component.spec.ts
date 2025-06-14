import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { KnowldgeDetailsLabComponent } from "./knowldge-details-lab.component";

describe("KnowldgeDetailsLabComponent", () => {
  let component: KnowldgeDetailsLabComponent;
  let fixture: ComponentFixture<KnowldgeDetailsLabComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [KnowldgeDetailsLabComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KnowldgeDetailsLabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
