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

    pesquisaMunicipios(idUf: number, nome: string, page: number): Observable<any>{
        let params = new HttpParams();
        params = params.append('ufId', idUf.toString());
        params = params.append('nome', nome);
        params = params.append('page', page.toString());
        params = params.set('size', '20');
        return this.httpClient.get<any>('pacientes/api/pesquisa/municipios',{
            params: params,
        }).pipe((res) => {
          return res;
        });
    }

}
