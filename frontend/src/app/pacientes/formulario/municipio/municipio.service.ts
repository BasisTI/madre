import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Municipio } from '../../models/dropdowns/types/municipio';
import { MunicipioUF } from '../../models/dropdowns/types/municipio-uf';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class MunicipioService extends CrudServiceNuvem<number, Municipio> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/municipios?sort=nome', httpClient);
    }

    getListaDeMunicipios(): Observable<Municipio[]> {
        return this.httpClient.get<Municipio[]>('pacientes/api/municipios');
    }

    getMunicipioById(id: number){
        return this.http.get(`pacientes/api/municipios/${id}`);
    }

    pesquisaMunicipios(ufId: number, nome: string): Observable<Municipio[]> {
        let params = new HttpParams();
        params = params.append('ufId', ufId.toString());
        params = params.append('nome', nome);
        params = params.append('size', '1000');
        return this.httpClient.get<Municipio[]>('pacientes/api/pesquisa/municipios',{
            params: params,
        });
    }

}
