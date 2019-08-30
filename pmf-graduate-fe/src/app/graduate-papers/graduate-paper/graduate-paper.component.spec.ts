/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { GraduatePaperComponent } from "./graduate-paper.component";

describe("GraduatePaperComponent", () => {
  let component: GraduatePaperComponent;
  let fixture: ComponentFixture<GraduatePaperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [GraduatePaperComponent]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GraduatePaperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
