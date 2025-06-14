// This class make possible the connection with the API
import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class ApiService {
  constructor(private http: HttpClient) {}
  // This method is in charge for saving an object in the database
  post(dir: string, model: object): Observable<any> {
    return this.http.post<any>(`${environment.api_url}${dir}`, model);
  }

  //This method gets all the objects stored in a table from the database
  getAll(dir: string, model?: object): Observable<any> {
    return this.http.get<any>(`${environment.api_url}${dir}`, model);
  }

  // This method get an object from the database by an id
  getById(dir: string, id: number): Observable<any> {
    return this.http.get<any>(`${environment.api_url}/${dir}/${id}`);
  }

  // This method update an object
  update(dir: string, id: string, model: object): Observable<any> {
    return this.http.put<any>(`${environment.api_url}/${dir}/${id}`, model);
  }

  updateList(dir: string, model: object): Observable<any> {
    return this.http.put<any>(`${environment.api_url}/${dir}`, model);
  }

  sendEmail(dir: string, model: object): Observable<any> {
    return this.http.post<any>(dir, model);
  }
}
