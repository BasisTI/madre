import { DarAltaAoPacienteService } from '../../services/dar-alta-ao-paciente.service';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { ConvenioDeSaude } from './../../models/convenio-de-saude';
import { Especialidade } from './../../models/especialidade';
import { UnidadeFuncional } from './../../models/unidade-funcional';
import { Component, OnInit } from '@angular/core';
import { Leito } from '@internacao/models/leito';

@Component({
    selector: 'app-listar-dar-alta-pacientes',
    templateUrl: './listar-dar-alta-pacientes.component.html',
    styleUrls: ['./listar-dar-alta-pacientes.component.css'],
})
export class ListarDarAltaPacientesComponent implements OnInit {
    id: string = '';
    dataDaInternacao: string = '';
    dataDaAlta: string = '';
    leitosId: string = '';
    especialidadeId: string = '';
    convenidoSaudeId: string = '';

    listaInternacoes: DarAltaAoPaciente[];
    listaLeitos: Leito[] = [];
    listaUnidadeFuncionais: UnidadeFuncional[] = [];
    listaEspecialidades: Especialidade[] = [];
    listaConveniosSaude: ConvenioDeSaude[] = [];

    seachUrl: string = 'internacao/api/_search/lista-internacoes';

    constructor(private altaPacienteService: DarAltaAoPacienteService) {}

    ngOnInit(): void {
        this.listarInternacoes();
    }

    listarInternacoes() {
        this.altaPacienteService
            .getListaInternacoes(
                this.id,
                this.dataDaInternacao,
                this.dataDaAlta,
                this.leitosId,
                this.especialidadeId,
                this.convenidoSaudeId,
            )
            .subscribe((response) => {
                this.listaInternacoes = response;
            });
    }
}
