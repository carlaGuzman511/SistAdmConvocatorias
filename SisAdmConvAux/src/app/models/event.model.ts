import { Schedule } from "./schedule.model";

export interface Event {
  idevent: number;
  name: string;
  dateEvent: string;
  place: string;
  timeEvent: string;
  schedule: Schedule;
}
