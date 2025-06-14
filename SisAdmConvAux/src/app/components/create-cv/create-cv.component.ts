import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, FormArray } from '@angular/forms';
import { ShowAlertMessage } from 'src/app/showAlertMessage';
import { ApiService } from 'src/app/api-service/api.service';

@Component({
  selector: 'app-create-cv',
  templateUrl: './create-cv.component.html',
  styleUrls: ['./create-cv.component.css']
})
export class CreateCvComponent implements OnInit {
  public form: FormGroup;
  public meritTable: Array<any> = [];
  public merits: Array<any> = [];
  public details: Array<any> = [];
  public subdetails: Array<any> = [];
  public pointDetails: Array<any> = [];
  public showAlertMessage = new ShowAlertMessage();
  constructor(private fb: FormBuilder, private apiService: ApiService) { }

  ngOnInit() {
    this.getMeritRatingTable();
    this.form = this.fb.group({
        name: new FormControl('', [
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.required,
        Validators.pattern('^[a-zA-Z\ áéíóúÁÉÍÓÚñÑ\s]*$')
        ]),
        surname: new FormControl('', [
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.required,
        Validators.pattern('^[a-zA-Z\ áéíóúÁÉÍÓÚñÑ\s]*$')
        ]),
        ci: new FormControl('', [
        Validators.minLength(7),
        Validators.maxLength(8),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
        address: new FormControl('', [
        Validators.minLength(10),
        Validators.maxLength(255),
        Validators.required
        ]),
        phone: new FormControl('', [
        Validators.minLength(7),
        Validators.maxLength(13),
        Validators.required,
        Validators.pattern('^[0-9]*$')
        ]),
        email: new FormControl('', [
        Validators.minLength(6),
        Validators.maxLength(70),
        Validators.required,
        Validators.email
        ]),
        birthdate: new FormControl('', Validators.required),
        requests: this.fb.array([this.initX()])
    });
  }

  public initX(): any {
    return this.fb.group({
      requisito: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(2000)]],
      note: this.fb.array([this.initNote()])
    });
  }
  public initNote(): any {
    return this.fb.group({
      //  ---------------------forms fields on y level ------------------------
      note: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(150)]]
      // ----------------------------------------------------------------------
    });
  }
  public removeNote(index: number, i: number): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = (<FormArray> this.form.controls['requests']).at(index).get('note') as FormArray;
    control.removeAt(i);
  }
  public addX(): void {
    // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['requests'];
    control.push(this.initX());
  }

  public removeX(index: number): void {
     // tslint:disable-next-line:no-angle-bracket-type-assertion
    const control = <FormArray> this.form.controls['requests'];
    control.removeAt(index);
  }

  public getMeritRatingTable(): void {
    this.apiService.getAll('/merit/announcement/1').subscribe(
      (response: any) => {
        this.meritTable = response;
        this.getMerits(this.meritTable[4].idmerit);
      });
  }
  public getMerits(idmerit: number): void {
    let endpoint = '/detailmerit/merit/';
    endpoint = endpoint.concat(idmerit.toString());
    this.apiService.getAll(endpoint).subscribe(
      (response: any) => {
        this.merits = response;
        // tslint:disable-next-line:forin
        for (const i in  this.merits) {
          this.getDetails(this.merits[i].iddetailmerit);
        }
      });
  }
  public getDetails(idmerit: number): void {
    let endpoint = '/subdetailmerit/detailmerit/';
    endpoint = endpoint.concat(idmerit.toString());
    this.apiService.getAll(endpoint).subscribe(
      (response: any) => {
        this.details.push(response);
        // tslint:disable-next-line:forin
        for (const x in this.details) {
          // tslint:disable-next-line:forin
          for (const y in this.details[x]) {
            this.getSubDetails(this.details[x][y].idsubdetailmerit);
          }
        }
      });
  }
  public getSubDetails(idmerit: number): void {
    let endpoint = '/subsubdetailmerit/subdetailmerit/';
    endpoint = endpoint.concat(idmerit.toString());
    this.apiService.getAll(endpoint).subscribe(
      (response: Array<any>) => {
        // tslint:disable-next-line:forin
        this.subdetails.push(response);
      });
  }
  public filter(): void {
    for (const i in this.subdetails) {
      if (this.subdetails[i].length > 0) {
        this.pointDetails.push(this.subdetails[i]);
      }
    }
  }

  public save(): void {
    console.log(this.form.controls.requests.value);
  }
}
