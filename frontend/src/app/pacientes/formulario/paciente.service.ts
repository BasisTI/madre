import { Injectable } from "@angular/core";
import { CrudService } from "@nuvem/primeng-components";
import { HttpClient, HttpParams } from "@angular/common/http";

import { Paciente } from "./paciente.model";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class PacienteService implements CrudService<number, Paciente> {

    uriServico: string = '/pacientes/api/pacientes';
    uri: string = `${this.uriServico}/lista-de-pacientes-elastic`;
    uriProntuario: string = `${this.uriServico}/prontuario`;

    constructor(private http: HttpClient) {
    }

    save(paciente: Paciente): Observable<Paciente> {
        let salvar: any = {
            ...paciente,
            ocupacaoId: paciente.ocupacaoId?.id,
            religiaoId: paciente.religiaoId?.id,
            naturalidadeId: paciente.naturalidadeId?.id,
            etniaId: paciente.etniaId?.id,
            nacionalidadeId: paciente.nacionalidadeId?.id,
            racaId: paciente.racaId?.id,
            estadoCivilId: paciente.estadoCivilId?.id,
            cartaoSUS: {
                ...paciente.cartaoSUS,
                justificativaId: paciente.cartaoSUS.justificativaId?.id,
                motivoDoCadastroId: paciente.cartaoSUS.motivoDoCadastroId?.id,
            },
            responsavel: {
                ...paciente.responsavel,
                grauDeParentescoId: paciente.responsavel.grauDeParentescoId?.id,
            },
            documento: {
                ...paciente.documento,
                orgaoEmissorId: paciente.documento.orgaoEmissorId?.id,
                ufId: paciente.documento.ufId?.id
            },
        }
        salvar.enderecos.forEach((endereco) => {
            endereco.municipioId = endereco.municipioId.id;
            endereco.bairro = endereco.bairro;
            endereco.cep = endereco.cep;
            endereco.logradouro = endereco.logradouro;
            endereco.numero =  endereco.numero;
            endereco.complemento = endereco.complemento;
            endereco.bairro = endereco.bairro;
            endereco.correspondencia = endereco.correspondencia;
            endereco.tipoDoEndereco = endereco.tipoDoEndereco;
          });
        return this.http.post<Paciente>(this.uriServico, salvar);
    }

    edit(entity: Paciente): Observable<Paciente> {
        return this.http.put<Paciente>(this.uriServico, entity);
    }

    find(id: number): Observable<Paciente> {
        return this.http.get<Paciente>(`${this.uriServico}/${id}`);
    }

    delete(id: number): Observable<Paciente> {
        return this.http.delete<Paciente>(`${this.uriServico}/${id}`);
    }

    findAll(entity: Paciente): Observable<Paciente>{
        const params = new HttpParams().set('nome', entity.nome).set('prontuario', entity.prontuario);
        return this.http.get<Paciente>(`${this.uri}`, { params });
    }

}
