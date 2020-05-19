import { Observable } from 'rxjs';
import { Internacao } from '@internacao/models/internacao';
import { HttpClient } from '@angular/common/http';
import { api } from '@internacao/api';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class InternacaoDePacienteService {
    private readonly resource = `${api}/internacoes`;

    constructor(private client: HttpClient) {}

    internarPaciente(internacao: Internacao): Observable<Internacao> {
        const internacaoDTO = {
            procedimentoId: 1,
            prioridade: 'ELETIVA',
            justificativa: internacao.justificativa,
            dataDaInternacao: internacao.dataDaInternacao,
            diferencaDeClasse: internacao.diferencaDeClasse,
            solicitarProntuario: internacao.solicitarProntuario,
            especialidadeId: internacao.especialidade.id,
            crmId: internacao.crm.id,
            hospitalDeOrigemId: internacao.hospitalDeOrigem.id,
            origemId: internacao.origemDaInternacao.id,
            convenioId: internacao.convenioDeSaude.id,
            planoId: internacao.planoDeSaude.id,
            procedenciaId: internacao.procedencia.id,
            modalidadeAssistencialId: internacao.modalidadeAssistencial.id,
            localDeAtendimentoId: internacao.localDeAtendimento.id,
            caraterDaInternacaoId: internacao.caraterDaInternacao.id,
            leitoId: internacao.leito.id,
        };

        return this.client.post<Internacao>(this.resource, internacaoDTO);
    }
}
