import { DatatableClickEvent } from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { Paciente } from './../../models/paciente';
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
    leitoId: string = '';
    ativo: string = '';
    especialidadeId: string = '';
    convenioId: string = '';
    pacienteId: string = '';
    prontuario: string = '';

    listaInternacoes: DarAltaAoPaciente[];
    listaLeitos: Leito[] = [];
    listaUnidadeFuncionais: UnidadeFuncional[] = [];
    listaEspecialidades: Especialidade[] = [];
    listaConveniosSaude: ConvenioDeSaude[] = [];
    listaPacientes: Paciente[] = [];

    seachUrl: string = 'internacao/api/_search/lista-internacoes';

    darAltaModal: DarAltaAoPaciente;

    constructor(private altaPacienteService: DarAltaAoPacienteService, private router: Router) {}

    ngOnInit(): void {
        this.listarInternacoes();
    }

    listarInternacoes() {
        this.altaPacienteService
            .getListaInternacoes(
                this.id,
                this.dataDaInternacao,
                this.dataDaAlta,
                this.leitoId,
                this.ativo,
                this.especialidadeId,
                this.convenioId,
                this.pacienteId,
                this.prontuario,
            )
            .subscribe((response) => {
                this.listaInternacoes = response;
            });
    }

    modalClick(event: DatatableClickEvent) {
        switch (event.button) {
            case 'acao': {
                this.router.navigate(['internacao/formulario-dar-alta-ao-paciente']);
                break;
            }
            case 'view': {
                this.darAltaModal = event.selection;
                break;
            }
            default: {
                break;
            }
        }
    }
}
