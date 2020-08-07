import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient } from '@angular/common/http';
import { OrgaoEmissor } from '../models/dropdowns/types/orgao-emissor';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class OrgaoEmissorService extends CrudServiceNuvem<number, OrgaoEmissor> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/orgao-emissors?sort=valor', httpClient);
    }

    getListaDeOrgaosEmissores(): Observable<OrgaoEmissor[]> {
        return this.httpClient.get<OrgaoEmissor[]>('pacientes/api/orgao-emissors');
    }
}
