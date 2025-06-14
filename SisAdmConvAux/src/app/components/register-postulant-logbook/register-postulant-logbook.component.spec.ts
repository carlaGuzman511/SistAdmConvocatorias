import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RegisterPostulantLogbookComponent } from "./register-postulant-logbook.component";

describe("RegisterPostulantLogbookComponent", () => {
  let component: RegisterPostulantLogbookComponent;
  let fixture: ComponentFixture<RegisterPostulantLogbookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RegisterPostulantLogbookComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterPostulantLogbookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
