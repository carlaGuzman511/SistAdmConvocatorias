import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { NavbarKnowledgeComponent } from "./navbar-knowledge.component";

describe("NavbarKnowledgeComponent", () => {
  let component: NavbarKnowledgeComponent;
  let fixture: ComponentFixture<NavbarKnowledgeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarKnowledgeComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarKnowledgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
