import { Naturalidade } from '../../models/dropdowns/types/naturalidade';
import { FiltroMunicipioModel } from '../../models/municipio.filtro.model';
import { Pagination } from '../../../shared/pagination';
import { Injectable } from '@angular/core';
import { CrudServiceNuvem } from '@nuvem/primeng-components';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class NaturalidadeService extends CrudServiceNuvem<number, Naturalidade> {
    constructor(private httpClient: HttpClient) {
        super('pacientes/api/municipios', httpClient);
    }

    getListaDeNaturalidades(idUf: number, nome: string): Observable<Naturalidade[]> {
        let params = new HttpParams();
        params = params.append('idUf', idUf.toString());
        params = params.append('nome', nome);
        params = params.set('size', '20');
        return this.httpClient.get<Naturalidade[]>('pacientes/api/municipios/naturalidade',{
            params: params,
        });
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
