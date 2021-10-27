import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Internacao } from '@internacao/models/internacao';
import { Observable } from 'rxjs';
import { SolicitacaoDeInternacaoDTO } from '@internacao/models/dtos/solicitacao-de-internacao.dto';
import { api } from '@internacao/api';

@Injectable({
    providedIn: 'root',
})
export class InternacaoDePacienteService {
    private readonly resource = `${api}/internacoes`;

    constructor(private client: HttpClient) {}

    internarPaciente(
        internacao: Internacao,
        solicitacao: SolicitacaoDeInternacaoDTO,
    ): Observable<Internacao> {
        const dto = {
            pacienteId: internacao.pacienteId,
            procedimentoId: solicitacao.procedimentoId,
            prioridade: solicitacao.prioridade,
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

        return this.client.post<Internacao>(this.resource, dto);
    }
    listarGradeTransferiPaciente(): Observable<Array<Internacao>> {
        return this.client.get<Array<Internacao>>(`${this.resource}`)
    }
}
