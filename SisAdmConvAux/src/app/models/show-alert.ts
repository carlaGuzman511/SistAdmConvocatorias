import Swal from "sweetalert2";
import { TypeAlertEnum } from "./type-alert-enum";

export class ShowAlert {
  public message = "";
  public type: TypeAlertEnum = TypeAlertEnum.error;

  public showMessageSuccess(message: string) {
    this.message = message;
    this.type = TypeAlertEnum.success;
    Swal.fire({
      position: "center",
      icon: this.type,
      title: message,
      showConfirmButton: false,
      timer: 2000,
    });
  }

  public showMessageInfo(message: string) {
    this.message = message;
    this.type = TypeAlertEnum.info;
    Swal.fire({
      position: "center",
      icon: this.type,
      title: message,
      showConfirmButton: false,
      timer: 5000,
    });
  }

  public showMessageWarning(message: string) {
    this.message = message;
    this.type = TypeAlertEnum.warning;
    Swal.fire({
      position: "center",
      icon: this.type,
      title: message,
      showConfirmButton: false,
      timer: 5000,
    });
  }

  public showMessageError(message: string) {
    this.message = message;
    this.type = TypeAlertEnum.error;
    Swal.fire({
      position: "center",
      icon: this.type,
      title: message,
      showConfirmButton: false,
      timer: 2000,
    });
  }
}
