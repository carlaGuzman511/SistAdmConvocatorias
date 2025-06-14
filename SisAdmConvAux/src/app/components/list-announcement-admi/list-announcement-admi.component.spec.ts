import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ListAnnouncementAdmiComponent } from "./list-announcement-admi.component";

describe("ListAnnouncementComponent", () => {
  let component: ListAnnouncementAdmiComponent;
  let fixture: ComponentFixture<ListAnnouncementAdmiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ListAnnouncementAdmiComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListAnnouncementAdmiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
