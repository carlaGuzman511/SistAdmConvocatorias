import { Component, OnInit } from "@angular/core";
import { Announcement } from "src/app/models/announcement.model";
import { ApiService } from "src/app/api-service/api.service";
import { Router, ActivatedRoute } from "@angular/router";
import { UserService } from "src/app/api-service/user.service";
import { HttpErrorResponse } from "@angular/common/http";
import { Requirement } from "src/app/models/requirement.model";
import { Request } from "../../models/request.model";
import { RequestDetail } from "src/app/models/request-detail.model";
import { DocumentDetail } from "src/app/models/document-detail.model";
import { Shape } from "src/app/models/shape.model";
import { DetailShape } from "src/app/models/detail-shape.model";
import { Deadline } from "src/app/models/deadline.model";
import { Merit } from "../../models/merit.model";
import { DetailMerit } from "src/app/models/detail-merit.model";
import { SubDetailMerit } from "src/app/models/subdetail-merit.model";
import { SubSubDetailMerit } from "src/app/models/sub-subdetail-merit.model";
import { PointsSubSubDetailMerit } from "src/app/models/points-sub-subdetail-merit.model";
import { Knowledge } from "src/app/models/knowledge.model";
import { KnowledgeEvaluation } from "src/app/models/knowledge-evaluation.model";
import { Schedule } from "src/app/models/schedule.model";
import { Authority } from "src/app/models/authority.model";
import { Document } from "../../models/document.model";
import { Observable, of } from "rxjs";
import { Event } from "../../models/event.model";
import { Courts1 } from "../../models/courts.model";
import { Location } from "@angular/common";
import { ShowAlertMessage } from "src/app/showAlertMessage";

@Component({
  selector: "app-show-announcement",
  templateUrl: "./show-announcement.component.html",
  styleUrls: ["./show-announcement.component.css"],
})
/**
  This class contains the component show teaching announcement
  @class ShowAnnouncementComponent
*/
export class ShowAnnouncementComponent implements OnInit {
  private idAnnouncement = 1;
  private showMessage = new ShowAlertMessage();
  private state = false;
  private announcement: Announcement;
  private requirement$: Observable<Requirement[]>;
  private requirements: Requirement[];
  private request: Request;
  private requestDetail$: Observable<RequestDetail[]>;
  private requestDetails: RequestDetail[];
  private document: Document;
  private documentDetail$: Observable<DocumentDetail[]>;
  private documentDetails: DocumentDetail[];
  private shape: Shape;
  private detailShape$: Observable<DetailShape[]>;
  private detailShapes: DetailShape[];
  private deadline: any;
  private merit: Merit;
  private detailMerit$: Observable<DetailMerit[]>;
  private detailMerits: DetailMerit[];
  private courts: Courts1;

  private subDetailMerit$: Observable<SubDetailMerit[][]>;
  private subDetailMerits: SubDetailMerit[][];
  private subDetails: SubDetailMerit[];

  private subSubDetailMerit$: Observable<SubSubDetailMerit[][]>;

  private subSubDetailMerits: SubSubDetailMerit[][];
  private subSubDetails: SubSubDetailMerit[];

  private pointsSubSubDetailMerit$: Observable<PointsSubSubDetailMerit[][]>;
  private pointsSubSubDetailMerits: PointsSubSubDetailMerit[][];

  private knowledge: Knowledge;
  private knowledgeEvaluation$: Observable<KnowledgeEvaluation[]>;
  private knowledgeEvaluations: KnowledgeEvaluation[];
  private schedule: Schedule;
  private event$: Observable<Event[]>;
  private events: Event[];
  private authorities: Authority[];

  private announcementPublication: string;
  private documentsDelivery: string;

  constructor(
    private apiService: ApiService,
    private router: Router,
    private userService: UserService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit() {
    this.idAnnouncement = +this.route.snapshot.paramMap.get("id");
    this.searchAnnouncement();
  }
  /**
   This function searches the announcement by academic unit, area and management
    @method searchAnnouncement()
  */
  private searchAnnouncement(): void {
    this.apiService.getById("announcement", this.idAnnouncement).subscribe(
      (response: Announcement) => {
        this.announcement = response;
        this.searchRequirements();
        this.searchRequest();
        this.searchDocument();
        this.searchDeadline();
        this.searchShape();
        this.searchShedule();
        this.searchMerit();
        this.searchKnowledge();
        this.searchAuthorities();
        this.searchCourts();
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert("La convocatoria no fue encontrada");
      }
    );
  }
  /**
   This function searches the announcement requirements
   @method searchRequirements()
  */
  private searchRequirements(): void {
    this.requirement$ = this.apiService.getById(
      "requirement/announcement",
      this.announcement.idannouncement
    );
    this.requirement$.subscribe(
      (response: Requirement[]) => {
        this.requirements = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron requerimientos para la convocatoria"
        );
      }
    );
  }
  /**
   This function searches the announcement requests
   @method searchRequest()
  */
  private searchRequest(): void {
    this.apiService
      .getById("request/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Request[]) => {
          this.request = response[0];
          this.searchRequestDetails();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro un requisito para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement requests details
   @method searchRequestDetails()
  */
  private searchRequestDetails(): void {
    this.requestDetail$ = this.apiService.getById(
      "requestdetail/request",
      this.request.idrequest
    );
    this.requestDetail$.subscribe(
      (response: RequestDetail[]) => {
        this.requestDetails = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron los detalles de un requisito"
        );
      }
    );
  }
  /**
   This function searches the announcement documents
   @method searchDocument()
  */
  private searchDocument(): void {
    this.apiService
      .getById("document/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Document[]) => {
          this.document = response[0];
          this.searchDocumentDetails();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro un documento para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement documents details
   @method searchDocumentDetails()
  */
  private searchDocumentDetails(): void {
    this.documentDetail$ = this.apiService.getById(
      "documentdetail/document",
      this.document.iddocument
    );
    this.documentDetail$.subscribe(
      (response: DocumentDetail[]) => {
        this.documentDetails = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron los detalles de un documento"
        );
      }
    );
  }
  /**
   This function searches the announcement deadline
   @method searchDeadline()
  */
  private searchDeadline(): void {
    this.apiService
      .getById("deadline/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Deadline[]) => {
          this.deadline = response[0];
          console.log(this.deadline);
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro una fecha limite para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement shapes
   @method searchShape()
  */
  private searchShape(): void {
    this.apiService
      .getById("shape/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Shape[]) => {
          this.shape = response[0];
          this.searchDetailShapes();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro una forma para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement shapes details
   @method searchDetailShapes()
  */
  private searchDetailShapes(): void {
    this.detailShape$ = this.apiService.getById(
      "detailshape/shape",
      this.shape.idshape
    );
    this.detailShape$.subscribe(
      (response: DetailShape[]) => {
        this.detailShapes = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron detalles de la forma"
        );
      }
    );
  }
  /**
   This function searches the announcement's schedule
    @method searchShedule()
  */
  private searchShedule(): void {
    this.apiService
      .getById("schedule/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Schedule[]) => {
          this.schedule = response[0];
          this.searchEvents();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro un cronograma para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement's events
    @method searchEvents()
  */
  private searchEvents(): void {
    this.event$ = this.apiService.getById(
      "event/schedule",
      this.schedule.idschedule
    );
    this.event$.subscribe(
      (response: Event[]) => {
        this.events = response;
        console.log(this.events);
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron eventos para el cronograma"
        );
      }
    );
  }
  /**
   This function searches the announcement's merit
    @method searchMerit()
  */
  private searchMerit(): void {
    this.apiService
      .getById("merit/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Merit[]) => {
          this.merit = response[0];
          this.searchDetailMerits();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro un merito para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement's merits details
    @method searchDetailMerits()
  */
  private searchDetailMerits(): void {
    this.detailMerit$ = this.apiService.getById(
      "detailmerit/merit",
      this.merit.idmerit
    );
    this.detailMerit$.subscribe(
      (response: DetailMerit[]) => {
        this.detailMerits = response;
        this.searchSubDetailMerits();
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron detalles para un merito"
        );
      }
    );
  }
  /**
   This function searches the announcement's merits subdetails
    @method searchSubDetailMerits()
  */
  private searchSubDetailMerits(): void {
    const subDetailMerits = [];
    const answerSubDetailLists = [];
    for (const detailMerit of this.detailMerits) {
      this.apiService
        .getById("subdetailmerit/detailmerit", detailMerit.iddetailmerit)
        .subscribe(
          (response: SubDetailMerit[]) => {
            subDetailMerits.push(response);
            for (const subdetail of response) {
              answerSubDetailLists.push(subdetail);
              // this.searchSubSubDetailMerits(subdetail);
              this.searchSubSubDetailMerits();
            }
          },
          (error: HttpErrorResponse) => {
            this.showMessage.showErrorAlert(
              "No se encontraron subdetalles para un detalle de merito"
            );
          }
        );
    }

    this.subDetails = answerSubDetailLists;
    this.subDetailMerits = subDetailMerits;
    this.subDetailMerit$ = of(this.subDetailMerits);
  }
  /**
   This function searches the announcement's merits subsubdetails
    @method searchSubSubDetailMerits()
  */
  private searchSubSubDetailMerits(): void {
    const subSubDetailMerits = [];
    const answerSubSubDetailLists = [];
    for (const subDetails of this.subDetailMerits) {
      for (const subDetail of subDetails) {
        this.apiService
          .getById(
            "subsubdetailmerit/subdetailmerit",
            subDetail.idsubdetailmerit
          )
          .subscribe(
            (response: SubSubDetailMerit[]) => {
              if (response.length > 0) {
                subSubDetailMerits.push(response);
                //this.subSubDetailMerits.push(response);
                for (const subSubdetail of response) {
                  answerSubSubDetailLists.push(subSubdetail);
                  this.searchPointsSubSubDetailMerits();
                }
              }
            },
            (error: HttpErrorResponse) => {
              this.showMessage.showErrorAlert(
                "No se encontraron subdetalles para un detalle de merito"
              );
            }
          );
      }
    }
    this.subSubDetails = answerSubSubDetailLists;
    this.subSubDetailMerits = subSubDetailMerits;
    this.subSubDetailMerit$ = of(this.subSubDetailMerits);
  }
  /**
   This function searches the announcement's merits points subsubdetails
    @method searchPointsSubSubDetailMerits()
  */
  private searchPointsSubSubDetailMerits(): void {
    const pointsSubSubDetailMerits = [];
    const answerPointsSubSubDetailLists = [];

    for (const subSubDetails of this.subSubDetailMerits) {
      for (const subSubDetail of subSubDetails) {
        this.apiService
          .getById(
            "pointssubsubdetailmerit/subsubdetailmerit",
            subSubDetail.idsubsubdetailmerit
          )
          .subscribe(
            (response: PointsSubSubDetailMerit[]) => {
              if (response.length > 0) {
                pointsSubSubDetailMerits.push(response);
                for (const subSubdetail of response) {
                  answerPointsSubSubDetailLists.push(subSubdetail);
                }
              }
            },
            (error: HttpErrorResponse) => {
              this.showMessage.showErrorAlert(
                "No se encontraron subdetalles para un detalle de merito"
              );
            }
          );
      }
    }
    this.pointsSubSubDetailMerits = pointsSubSubDetailMerits;
    this.pointsSubSubDetailMerit$ = of(this.pointsSubSubDetailMerits);
  }
  /**
   This function searches the announcement's knowledge
    @method searchKnowledge()
  */
  private searchKnowledge(): void {
    this.apiService
      .getById("knowledge/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Knowledge[]) => {
          this.knowledge = response[0];
          this.searchKnowledgeEvaluation();
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontro un conocimiento para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement's knowledge evaluations
    @method searchKnowledgeEvaluation()
  */
  private searchKnowledgeEvaluation(): void {
    this.knowledgeEvaluation$ = this.apiService.getById(
      "knowledgeevaluation/knowledge",
      this.knowledge.idknowledge
    );
    this.knowledgeEvaluation$.subscribe(
      (response: KnowledgeEvaluation[]) => {
        this.knowledgeEvaluations = response;
      },
      (error: HttpErrorResponse) => {
        this.showMessage.showErrorAlert(
          "No se encontraron las evaluaciones conocimiento para la convocatoria"
        );
      }
    );
  }
  /**
   This function searches the announcement's authorities
    @method searchAuthorities()
  */
  private searchAuthorities(): void {
    this.apiService
      .getById("authority/announcement", this.announcement.idannouncement)
      .subscribe(
        (response: Authority[]) => {
          this.authorities = response;
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontraron las autoridades para la convocatoria"
          );
        }
      );
  }
  /**
   This function searches the announcement's courts
    @method searchCourts()
  */
  private searchCourts(): void {
    this.apiService
      .getById("courts/description", this.announcement.idannouncement)
      .subscribe(
        (response: Courts1) => {
          this.courts = response;
          console.log(this.courts);
        },
        (error: HttpErrorResponse) => {
          this.showMessage.showErrorAlert(
            "No se encontraron los tribunales para la convocatoria"
          );
        }
      );
  }
  /**
   This function generates the document note
    @method generateDocumentNote()
  */
  private generateDocumentNote(): string {
    const noteDocument = "NOTA: " + this.document.name;
    return noteDocument;
  }
  /**
   This function generates the schedule note
    @method generateScheduleNote()
  */
  private generateScheduleNote(): string {
    const noteSchedule = "NOTA: " + this.schedule.note;
    return noteSchedule;
  }
  /**
   This function displays the rotulo component
    @method apply()
  */
  private apply(): void {
    console.log("id", this.idAnnouncement);
    const pack = this.announcement.pack;
    if (this.state) {
      if (pack) {
        this.router.navigate(["/rotulo-auxiliaturas", this.idAnnouncement]);
      } else {
        this.router.navigate(["/rotulo", this.idAnnouncement]);
      }
    } else {
      this.showMessage.showInfoAlert(
        "La fecha limite para la presentacion de documentos fue: " +
          this.documentsDelivery
      );
    }
  }
  /**
   This function displays the announcement lists component
    @method goBack()
  */
  private goBack(): void {
    //this.router.navigate(['/listarConvocatorias']);
    this.location.back();
  }
  /**
    This function validates the dates for revision
    @method datesValidator()
  */
  private datesValidator(): string {
    let enable = "VENCIDO";
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    let announcementPublication = new Date();
    let documentsDelivery = new Date();
    for (const event of this.events) {
      if (event.name === "Publicacion de la convocatoria") {
        this.announcementPublication = event.dateEvent;
        announcementPublication = new Date(this.announcementPublication);
        announcementPublication.setHours(0, 0, 0, 0);
        announcementPublication.setTime(
          announcementPublication.getTime() + 1 * 24 * 60 * 60 * 1000
        );
      } else {
        if (event.name === "Presentacion de documentos") {
          this.documentsDelivery = event.dateEvent;
          documentsDelivery = new Date(this.documentsDelivery);
          documentsDelivery.setHours(0, 0, 0, 0);
        }
      }
    }
    if (today >= announcementPublication) {
      if (today <= documentsDelivery) {
        enable = "VIGENTE";
        this.state = true;
      }
    }
    return enable;
  }
  /**
    This function shows the annnouncement's publication date
    @method publicationDate()
  */
  private publicationDate(): string {
    return this.events[0].dateEvent;
  }
}
