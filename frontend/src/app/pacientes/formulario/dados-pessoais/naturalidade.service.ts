import { Naturalidade } from '../../models/dropdowns/types/naturalidade';
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
        params = params.set('size', '50');
        return this.httpClient.get<Naturalidade[]>('pacientes/api/municipios/naturalidade',{
            params: params,
        });
    }

    pesquisaMunicipios(idUf: number, nome: string): Observable<Naturalidade[]> {
            let params = new HttpParams();
            params = params.append('ufId', idUf.toString());
            params = params.append('nome', nome);
            params = params.set('size', '50');
            return this.httpClient.get<Naturalidade[]>('pacientes/api/pesquisa/municipios',{
                params: params,
            });
        }

}
