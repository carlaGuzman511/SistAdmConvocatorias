import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { RotuloComponent } from "./rotulo.component";
import { RouterTestingModule } from "@angular/router/testing";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ShowAlertMessage } from "src/app/showAlertMessage";

class MockApiService {
  postResponse = true;
  post() {
    return this.postResponse;
  }

  getAll() {
    return [
      {
        id_postulation: "1",
        announcement: "Convocatoria 1",
        auxiliature_name: "Administrador de Laboratorio de Cómputo",
        aux_codItem: "LCO-ADM",
      },
      {
        id_postulation: "2",
        announcement: "Convocatoria 2",
        auxiliature_name: "Administrador de Laboratorio de Desarrollo",
        aux_codItem: "LDS-ADM",
      },
      {
        id_postulation: "3",
        announcement: "Convocatoria 3",
        auxiliature_name:
          "Administrador de Laboratorio de Mantenimiento de Software",
        aux_codItem: "LM-ADM-SW",
      },
      {
        id_postulation: "4",
        announcement: "Convocatoria 4",
        auxiliature_name:
          "Administrador de Laboratorio de Mantenimiento de Hardware",
        aux_codItem: "LM-ADM-HW",
      },
    ];
  }
}
describe("RotuloComponent", () => {
  let component: RotuloComponent;
  let fixture: ComponentFixture<RotuloComponent>;
  let spy: any;
  let service: MockApiService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RotuloComponent],
      imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule,
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RotuloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = new MockApiService();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("#labelForm, should be a invalid postulation", () => {
    expect(component.labelForm.valid).toBeFalsy();
  });

  it("#labelForm, should be a valid postulation", () => {
    component.labelForm.setValue({
      name: "Gilda",
      lastname: "Silvestre López",
      address: "Villa Satélite",
      phone: "79744255",
      email: "gildasilvestrelop09@gmail.com",
      cod_item: "LM – ADM –SW",
      aux_name: "Administrador de Laboratorio de Mantenimiento de Software",
    });
    expect(component.labelForm.valid).toBeTruthy();
  });

  it("should print the label", () => {
    component.printLabel();
    expect(component).toBeTruthy();
  });

  it("should save data", () => {
    //service.postResponse = true;
    expect(component.saveData()).toBeTruthy();
  });

  it("should save the postulation", () => {
    spy = spyOn(service, "post");
    component.savePostulation();
    expect(spy).toHaveBeenCalled();
  });
});
