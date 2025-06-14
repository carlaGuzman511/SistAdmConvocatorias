import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ShowAnnouncementComponent } from "./show-announcement.component";

describe("ShowAnnouncementComponent", () => {
  let component: ShowAnnouncementComponent;
  let fixture: ComponentFixture<ShowAnnouncementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ShowAnnouncementComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAnnouncementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
