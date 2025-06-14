import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { PostulantsListComponent } from "./components/postulants-list/postulants-list.component";
import { EnabledPostulantsComponent } from "./components/enabled-postulants/enabled-postulants.component";
import { RotuloComponent } from "./components/rotulo/rotulo.component";
import { EnabledAndNoEnabledPostulantsComponent } from "./components/enabled-and-no-enabled-postulants/enabled-and-no-enabled-postulants.component";
import { NoEnabledPostulantsComponent } from "./components/no-enabled-postulants/no-enabled-postulants.component";
import { TeachingLabelComponent } from "./components/teaching-label/teaching-label.component";
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
import { AdminGuard } from "./guard/admin.guard";
import { SecretaryGuard } from "./guard/secretary.guard";
import { CommeritsGuard } from "./guard/commerits.guard";
import { ComknowGuard } from "./guard/comknow.guard";
import { KnowldgeResultsComponent } from "./components/knowldge-results/knowldge-results.component";
import { KnowldgeDetailsLabComponent } from "./components/knowldge-details-lab/knowldge-details-lab.component";
import { KnowldgeDetailsDocComponent } from "./components/knowldge-details-doc/knowldge-details-doc.component";
import { ListAnnouncementAdmiComponent } from "./components/list-announcement-admi/list-announcement-admi.component";
import { AdminEnableNoEnableComponent } from "./components/admin-enable-no-enable/admin-enable-no-enable.component";
import { MeritCalificationComponent } from "./components/merit-calification/merit-calification.component";
import { ReportFinalResultsViewComponent } from "./components/report-final-results-view/report-final-results-view.component";
import { CourtsComponent } from "./components/courts/courts.component";
import { AdminNoEnableComponent } from "./components/admin-no-enable/admin-no-enable.component";
import { RegisterCommmissionComponent } from "./components/register-commmission/register-commmission.component";

const routes: Routes = [
  {
    path: "",
    redirectTo: "/",
    pathMatch: "full",
  },
  {
    path: "",
    component: HomeComponent,
    children: [
      {
        path: "rotulo/:id",
        component: RotuloComponent,
      },
      {
        path: "rotulo-auxiliaturas/:id",
        component: TeachingLabelComponent,
      },
      {
        path: "iniciar-sesion",
        component: LoginComponent,
      },
      {
        path: "generarCurriculumVitae",
        component: CreateCvComponent,
      },

      {
        path: "mostrar-convocatoria-docencia/:id",
        component: ShowAnnouncementComponent,
      },
      {
        path: "mostrar-convocatoria-laboratorio/:id",
        component: ShowAnnouncementLaboratoryComponent,
      },
      {
        path: "",
        component: InitialPageComponent,
      },
      {
        path: "listarConvocatorias",
        component: ListAnnouncementComponent,
      },
    ],
  },
  {
    path: "merito",
    component: HomeComMeritsComponent,
    canActivate: [CommeritsGuard],
    children: [
      {
        path: "postulantes",
        component: PostulantsListComponent,
      },
      {
        path: "verificarRequisitos/:id",
        component: VerifyRequestComponent,
      },
      {
        path: "habilitados",
        component: EnabledPostulantsComponent,
      },
      {
        path: "no-habilitados",
        component: NoEnabledPostulantsComponent,
      },
      {
        path: "habilitados-no-habilitados",
        component: EnabledAndNoEnabledPostulantsComponent,
      },
      {
        path: "evaluar-meritos/:id",
        component: MeritCalificationComponent,
      },
      {
        path: "",
        component: PostulantsListComponent,
      },
    ],
  },
  {
    path: "conocimiento",
    component: HomeComKnowComponent,
    canActivate: [ComknowGuard],
    children: [
      {
        path: "docencia-escrito",
        component: EvaluationComponent,
      },
      {
        path: "laboratorio-escrito",
        component: WrittenLaboratoryEvaluationComponent,
      },
      {
        path: "docencia-oral",
        component: OralTeachingEvaluationComponent,
      },
      {
        path: "resultadosDeConocimientos",
        component: KnowldgeResultsComponent,
      },
      {
        path: "detallesConocimientoLaboratorio/:id",
        component: KnowldgeDetailsLabComponent,
      },
      {
        path: "detallesConocimientoDocencia/:id",
        component: KnowldgeDetailsDocComponent,
      },
      {
        path: "",
        component: KnowldgeResultsComponent,
      },
    ],
  },
  {
    path: "admin",
    component: HomeAdminComponent,
    canActivate: [AdminGuard],
    children: [
      {
        path: "listarConvocatoriasAdmin",
        component: ListAnnouncementAdmiComponent,
      },
      {
        path: "registrar-miembro-comision/:id",
        component: RegisterCommissionMemberComponent,
      },
      {
        path: "generarConvocatoria",
        component: GenerateAnnouncementComponent,
      },
      {
        path: "adm-habilitados-no-habilitados",
        component: AdminEnableNoEnableComponent,
      },
      {
        path: "admin-no-habilitados",
        component: AdminNoEnableComponent,
      },
      {
        path: "registrar-comision",
        component: RegisterCommmissionComponent,
      },
      {
        path: "resultadosFinales",
        component: FinalResultsComponent,
      },
      {
        path: "tribunales/:id",
        component: CourtsComponent,
      },
      {
        path: "reporteFinal",
        component: ReportFinalResultsComponent,
      },
      {
        path: "detalles-reporte/:id",
        component: ReportFinalResultsViewComponent,
      },
      {
        path: "",
        component: ListAnnouncementAdmiComponent,
      },
      {
        path: "registrarRequisitos/:id",
        component: RegisterRequisitosComponent,
      },
      {
        path: "registrarRequerimientosDeLaboratorio/:id",
        component: RegisterRequirementsComponent,
      },
      {
        path: "registrarRequerimientosDeDocencia/:id",
        component: RegisterTeachingRequirementsComponent,
      },
      {
        path: "registrarTablaCalificacionMeritos/:id",
        component: RegisterMeritRatingTableComponent,
      },
      {
        path: "documentosAPresentar/:id",
        component: DocsToPresentComponent,
      },
      {
        path: "deLaForma/:id",
        component: ShapeComponent,
      },
      {
        path: "fechaLimite/:id",
        component: DeadlineComponent,
      },
      {
        path: "autoridadesQueFirman/:id",
        component: AuthoritiesComponent,
      },
      {
        path: "registrarFechas/:id",
        component: ScheduleComponent,
      },
      {
        path: "docencia-tabla/:id",
        component: TeachingEvaluationTableComponent,
      },
      {
        path: "laboratorio-tabla/:id",
        component: LaboratoryEvaluationTableComponent,
      },
      {
        path: "resultadosDeConocimientos",
        component: KnowldgeResultsComponent,
      },
      {
        path: "detallesConocimientoLaboratorio/:id",
        component: KnowldgeDetailsLabComponent,
      },
      {
        path: "detallesConocimientoDocencia/:id",
        component: KnowldgeDetailsDocComponent,
      },
    ],
  },
  {
    path: "secretario",
    component: HomeSecretaryComponent,
    canActivate: [SecretaryGuard],
    children: [
      {
        path: "registerPostulantOnLogbook/:id",
        component: RegisterPostulantLogbookComponent,
      },
      {
        path: "buscarPostulante",
        component: SearchPostulantComponent,
      },
      {
        path: "",
        component: SearchPostulantComponent,
      },
    ],
  },
  {
    path: "merits",
    component: RegisterMeritRatingTableComponent,
  },
  {
    path: "**",
    pathMatch: "full",
    redirectTo: "/",
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
export const routingComponents = [RotuloComponent];
