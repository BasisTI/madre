import { ListarDarAltaAoPacienteService } from './../../services/listar-dar-alta-ao-paciente.service';
import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { ConvenioDeSaudeService } from '@internacao/services/convenio-de-saude.service';
import { EspecialidadeService } from '@internacao/services/especialidade.service';
import { UnidadeFuncionalService } from 'src/app/exames/services/unidade-funcional.service';
import { LeitoService } from '@internacao/services/leito.service';
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
    unidadeFuncionalId: string = '';
    especialidadeId: string = '';
    convenidoSaudeId: string = '';
    ativo: string = '';
    tipoDeAlta: string = '';

    listaInternacoes: DarAltaAoPaciente[];
    listaLeitos: Leito[] = [];
    listaUnidadeFuncionais: UnidadeFuncional[] = [];
    listaEspecialidades: Especialidade[] = [];
    listaConveniosSaude: ConvenioDeSaude[] = [];

    seachUrl: string = 'internacao/api/_search/internacoes';

    constructor(
        private listarAltaPacienteService: ListarDarAltaAoPacienteService,
        private leitoService: LeitoService,
        private unidadeFuncionalService: UnidadeFuncionalService,
        private especialidadeService: EspecialidadeService,
        private convenioDeSaudeService: ConvenioDeSaudeService,
    ) {}

    ngOnInit(): void {
        this.listarUnidades();

        this.listarLeitos();

        this.listarEspecialidades();

        this.listarConvenioDeSaude();

        this.listarInternacoes();
    }

    listarUnidades() {
        this.unidadeFuncionalService.getUnidades().subscribe((response) => {
            this.listaUnidadeFuncionais = response;
        });
    }

    listarLeitos() {
        this.leitoService.obterLeitosBloqueados().subscribe((response) => {
            this.listaLeitos = response;
        });
    }

    listarEspecialidades() {
        this.especialidadeService.getEspecialidades().subscribe((response) => {
            this.listaEspecialidades = response;
        });
    }

    listarConvenioDeSaude() {
        this.convenioDeSaudeService.getConveniosDeSaude().subscribe((response) => {
            this.listaConveniosSaude = response;
        });
    }

    listarInternacoes() {
        this.listarAltaPacienteService
            .getListaInternacoes(
                this.id,
                this.dataDaInternacao,
                this.dataDaAlta,
                this.leitosId,
                this.unidadeFuncionalId,
                this.especialidadeId,
                this.convenidoSaudeId,
                this.ativo,
                this.tipoDeAlta
            )
            .subscribe((response) => {
                this.listaInternacoes = response;
            });
    }
}
