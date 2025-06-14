import { TestBed } from "@angular/core/testing";
import { ApiService } from "./api.service";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("ApiService", () => {
  beforeEach(() =>
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService],
    })
  );

  it("should be created", () => {
    const service: ApiService = TestBed.get(ApiService);
    expect(service).toBeTruthy();
  });

  it("should send the information to the API, post", () => {
    const service = TestBed.get(ApiService);
    expect(
      service.post("login", {
        carnetIdentidad: "12345666",
        contraseña: "123admi",
      })
    ).toBeTruthy();
  });

  it("should get an object from the database, getById ", () => {
    const service = TestBed.get(ApiService);
    expect(service.getById("candidatoPostulante", "1")).toBeTruthy();
  });

  it("should get all the objects from a table, getAll", () => {
    const service = TestBed.get(ApiService);
    expect(service.getAll("candidatoPostulante")).toBeTruthy();
  });

  it("should update an object, update", () => {
    const service = TestBed.get(ApiService);
    expect(
      service.update("candidatoPostulante", "1", {
        nombre: "Carl",
        apellido: "Smith",
        direccion: "Calle Calama entre Lanza y Antezana #1232",
        telefono: 4563453,
        email: "carlsmith@gmail.com",
        cod_item: "00001",
        nombre_auxiliatura: "introduccion a la programacion",
      })
    ).toBeTruthy();
  });
});
