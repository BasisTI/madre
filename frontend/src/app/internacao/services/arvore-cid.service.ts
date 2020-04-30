import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class ArvoreCidService implements EntityService {
    private readonly resource = `${api}/cids/arvore`;

    getResource<T>(params?: HttpParams): Observable<T> {
        throw new Error('Method not implemented.');
    }
}
