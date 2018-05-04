import { Injectable } from '@angular/core';
import { HttpRequest, HttpClient } from '@angular/common/http';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';

@Injectable()
export class UploadService {

  constructor(private http: HttpService) { }

  resources = {
    upload: environment.apiUrl + '/upload',
    getFile: environment.apiUrl + '/getFile',
    getFileInfo: environment.apiUrl + '/getFile/info'
  }

  uploadFile(file: File) {
    const headers: any = {
      'Content-Type': 'multipart/form-data',
    }

    let body = new FormData();

    body.append('file', file)

    return this.http.post(this.resources.upload, body).map(response => {
      return response;
    });
  }

  getFile(id: number) {
    return this.http.get(this.resources.getFile, {
      params: {
        id: id
      }
    }).map(response => {
      return response;
    })
  }

  getFileInfo(id: number) {
    return this.http.get(this.resources.getFileInfo, {
      params: {
        id: id
      }
    }).map(response => {
      return response.json();
    })
  }

}
