import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { GenerateAnnouncementComponent } from "./generate-announcement.component";

describe("GenerateAnnouncementComponent", () => {
  let component: GenerateAnnouncementComponent;
  let fixture: ComponentFixture<GenerateAnnouncementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GenerateAnnouncementComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerateAnnouncementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
