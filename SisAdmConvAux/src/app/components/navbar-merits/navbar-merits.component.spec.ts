import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { Router } from "@angular/router";
import { MenuButton } from "../../models/menu-button.model";
import { NavbarMeritsComponent } from "./navbar-merits.component";

describe("navbarMeritsComponent", () => {
  let navbarComponent: NavbarMeritsComponent;
  let fixture: ComponentFixture<NavbarMeritsComponent>;
  let router;
  let navigateSpy;
  const btn: MenuButton = {
    route: "",
    name: "Salir",
    icon: "fa fa-home",
  };
  const btns: MenuButton[] = [];
  btns[0] = btn;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarMeritsComponent],
      imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule,
      ],
      providers: [],
    })
      .compileComponents()
      .then(() => {
        fixture = TestBed.createComponent(NavbarMeritsComponent);
        navbarComponent = fixture.componentInstance;

        router = TestBed.get(Router);
        navigateSpy = spyOn(router, "navigate");
      });
  }));

  it("#constructor, should create a header component", () => {
    expect(navbarComponent).toBeTruthy();
  });
});
