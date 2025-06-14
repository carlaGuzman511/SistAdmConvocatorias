import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ShowAnnouncementLaboratoryComponent } from "./show-announcement-laboratory.component";

describe("ShowAnnouncementLaboratoryComponent", () => {
  let component: ShowAnnouncementLaboratoryComponent;
  let fixture: ComponentFixture<ShowAnnouncementLaboratoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ShowAnnouncementLaboratoryComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAnnouncementLaboratoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
