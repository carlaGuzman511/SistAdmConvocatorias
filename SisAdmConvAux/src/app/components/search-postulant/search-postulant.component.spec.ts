import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { SearchPostulantComponent } from "./search-postulant.component";

describe("SearchPostulantComponent", () => {
  let component: SearchPostulantComponent;
  let fixture: ComponentFixture<SearchPostulantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SearchPostulantComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchPostulantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
