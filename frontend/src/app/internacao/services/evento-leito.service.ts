import { EventoCalendario } from '@internacao/models/evento-calendario';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class EventoLeitoService {
    private readonly _resource$ = `${api}`;

    constructor(private client: HttpClient) {}

    public obterEventosCalendario(): Observable<Array<EventoCalendario>> {
        return this.client.get<Array<EventoCalendario>>(`${this._resource$}/eventos-calendario`);
    }
}
