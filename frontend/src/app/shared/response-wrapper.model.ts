import { Headers } from '@angular/http';

export class ResponseWrapper {
  array: any;
  constructor(
    public headers: Headers,
    public json: any,
    public status: number
  ) {}
}
