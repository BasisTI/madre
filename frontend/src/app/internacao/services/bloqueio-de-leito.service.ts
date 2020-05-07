import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { api } from '@internacao/api';
import { EntityService } from '@shared/entity.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class BloqueioDeLeitoService implements EntityService {
    private readonly resource = `${api}`;

    constructor(private client: HttpClient) {}

    getResource<T>(params?: HttpParams): Observable<T> {
        if (params) {
            return this.client.get<T>(this.resource, { params });
        }

        return this.client.get<T>(this.resource);
    }

    getApi(): string {
        return this.resource;
    }
}
