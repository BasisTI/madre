import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { api } from 'src/app/seguranca/api';
import { GrupoFuncional } from '../models/grupo-funcional-model';

@Injectable({
    providedIn: 'root',
})
export class GrupoFuncionalService {
    private readonly resource = `${api}/grupo-funcionals`;

    constructor(private client: HttpClient) {}

    public getGrupoFuncional(): Observable<Array<GrupoFuncional>> {
        return this.client.get<Array<GrupoFuncional>>(`${this.resource}`);
    }
}
