import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { ApiService } from "./api-service/api.service";
import { Ng2SearchPipeModule } from "ng2-search-filter";
import { PostulantsListComponent } from "./components/postulants-list/postulants-list.component";
import { EnabledPostulantsComponent } from "./components/enabled-postulants/enabled-postulants.component";
import { RotuloComponent } from "./components/rotulo/rotulo.component";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { NgMultiSelectDropDownModule } from "ng-multiselect-dropdown";
import { TeachingLabelComponent } from "./components/teaching-label/teaching-label.component";
import { EnabledAndNoEnabledPostulantsComponent } from "./components/enabled-and-no-enabled-postulants/enabled-and-no-enabled-postulants.component";
import { NoEnabledPostulantsComponent } from "./components/no-enabled-postulants/no-enabled-postulants.component";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { RegisterPostulantLogbookComponent } from "./components/register-postulant-logbook/register-postulant-logbook.component";
import { SearchPostulantComponent } from "./components/search-postulant/search-postulant.component";
import { FinalResultsComponent } from "./components/final-results/final-results.component";
import { RegisterRequisitosComponent } from "./components/register-requisitos/register-requisitos.component";
import { RegisterRequirementsComponent } from "./components/register-requirements/register-requirements.component";
import { RegisterMeritRatingTableComponent } from "./components/register-merit-rating-table/register-merit-rating-table.component";
import { RegisterTeachingRequirementsComponent } from "./components/register-teaching-requirements/register-teaching-requirements.component";
import { TeachingEvaluationTableComponent } from "./components/teaching-evaluation-table/teaching-evaluation-table.component";
import { LaboratoryEvaluationTableComponent } from "./components/laboratory-evaluation-table/laboratory-evaluation-table.component";
import { LoginComponent } from "./components/login/login.component";
import { EvaluationComponent } from "./components/evaluation/evaluation.component";
import { WrittenLaboratoryEvaluationComponent } from "./components/written-laboratory-evaluation/written-laboratory-evaluation.component";
import { OralTeachingEvaluationComponent } from "./components/oral-teaching-evaluation/oral-teaching-evaluation.component";
import { RegisterCommissionMemberComponent } from "./components/register-commission-member/register-commission-member.component";
import { ShowAnnouncementComponent } from "./components/show-announcement/show-announcement.component";
import { ShowAnnouncementLaboratoryComponent } from "./components/show-announcement-laboratory/show-announcement-laboratory.component";
import { ListAnnouncementComponent } from "./components/list-announcement/list-announcement.component";
import { ReportFinalResultsComponent } from "./components/report-final-results/report-final-results.component";
import { VerifyRequestComponent } from "./components/verify-request/verify-request.component";
import { GenerateAnnouncementComponent } from "./components/generate-announcement/generate-announcement.component";
import { CollapseModule } from "ngx-bootstrap/collapse";
import { CreateCvComponent } from "./components/create-cv/create-cv.component";
import { DocsToPresentComponent } from "./components/docs-to-present/docs-to-present.component";
import { ShapeComponent } from "./components/shape/shape.component";
import { DeadlineComponent } from "./components/deadline/deadline.component";
import { AuthoritiesComponent } from "./components/authorities/authorities.component";
import { ScheduleComponent } from "./components/schedule/schedule.component";
import { HomeComMeritsComponent } from "./components/home-com-merits/home-com-merits.component";
import { HomeAdminComponent } from "./components/home-admin/home-admin.component";
import { HomeSecretaryComponent } from "./components/home-secretary/home-secretary.component";
import { HomeComKnowComponent } from "./components/home-com-know/home-com-know.component";
import { HomeComponent } from "./components/home/home.component";
import { InitialPageComponent } from "./components/initial-page/initial-page.component";
import { NavbarMeritsComponent } from "./components/navbar-merits/navbar-merits.component";
import { NavbarKnowledgeComponent } from "./components/navbar-knowledge/navbar-knowledge.component";
import { BsDropdownModule } from "ngx-bootstrap/dropdown";
import { NavbarSecretaryComponent } from "./components/navbar-secretary/navbar-secretary.component";
import { NavbarComponent } from "./components/navbar/navbar.component";
import { ListAnnouncementAdmiComponent } from "./components/list-announcement-admi/list-announcement-admi.component";
import { KnowldgeResultsComponent } from "./components/knowldge-results/knowldge-results.component";
import { KnowldgeDetailsLabComponent } from "./components/knowldge-details-lab/knowldge-details-lab.component";
import { KnowldgeDetailsDocComponent } from "./components/knowldge-details-doc/knowldge-details-doc.component";
import { AdminEnableNoEnableComponent } from "./components/admin-enable-no-enable/admin-enable-no-enable.component";
import { MeritCalificationComponent } from "./components/merit-calification/merit-calification.component";
import { ReportFinalResultsViewComponent } from "./components/report-final-results-view/report-final-results-view.component";
import { CourtsComponent } from "./components/courts/courts.component";
import { AdminNoEnableComponent } from "./components/admin-no-enable/admin-no-enable.component";
import { RegisterCommmissionComponent } from "./components/register-commmission/register-commmission.component";
import { TypeaheadModule } from "ngx-bootstrap/typeahead";

@NgModule({
  declarations: [
    AppComponent,
    PostulantsListComponent,
    EnabledPostulantsComponent,
    RotuloComponent,
    EnabledAndNoEnabledPostulantsComponent,
    TeachingLabelComponent,
    RegisterPostulantLogbookComponent,
    NoEnabledPostulantsComponent,
    SearchPostulantComponent,
    FinalResultsComponent,
    RegisterRequisitosComponent,
    RegisterRequirementsComponent,
    RegisterMeritRatingTableComponent,
    RegisterTeachingRequirementsComponent,
    TeachingEvaluationTableComponent,
    LaboratoryEvaluationTableComponent,
    LoginComponent,
    EvaluationComponent,
    WrittenLaboratoryEvaluationComponent,
    OralTeachingEvaluationComponent,
    RegisterCommissionMemberComponent,
    ShowAnnouncementComponent,
    ShowAnnouncementLaboratoryComponent,
    ListAnnouncementComponent,
    ReportFinalResultsComponent,
    VerifyRequestComponent,
    GenerateAnnouncementComponent,
    CreateCvComponent,
    DocsToPresentComponent,
    ShapeComponent,
    DeadlineComponent,
    AuthoritiesComponent,
    ScheduleComponent,
    SearchPostulantComponent,
    HomeComMeritsComponent,
    HomeAdminComponent,
    HomeSecretaryComponent,
    HomeComKnowComponent,
    HomeComponent,
    InitialPageComponent,
    NavbarMeritsComponent,
    NavbarKnowledgeComponent,
    NavbarSecretaryComponent,
    NavbarComponent,
    ListAnnouncementAdmiComponent,
    KnowldgeResultsComponent,
    KnowldgeDetailsLabComponent,
    KnowldgeDetailsDocComponent,
    AdminEnableNoEnableComponent,
    MeritCalificationComponent,
    ReportFinalResultsViewComponent,
    CourtsComponent,
    AdminNoEnableComponent,
    RegisterCommmissionComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    Ng2SearchPipeModule,
    ReactiveFormsModule,
    FormsModule,
    NgMultiSelectDropDownModule.forRoot(),
    BsDatepickerModule.forRoot(),
    BrowserAnimationsModule,
    CollapseModule.forRoot(),
    BsDropdownModule.forRoot(),
    TypeaheadModule.forRoot(),
  ],
  providers: [HttpClientModule, ApiService],
  bootstrap: [AppComponent],
})
export class AppModule {}
