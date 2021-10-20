import { Component, OnInit } from '@angular/core';
import { GradeDeAgendamentoExame } from '../../models/subjects/grades-de-agendamento';
import { ExamModel } from '../../models/subjects/exames-model';
import { Responsavel } from '../../models/subjects/responsavel-model';
import { Sala } from '../../models/subjects/sala';
import { UnidadeFuncional } from '../../models/subjects/unidade-model';
import { ExamesService } from '../../services/exames.service';
import { GradeDeAgendamentoService } from '../../services/grade-de-agendamento.service';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { ListaServidor } from 'src/app/seguranca/models/dropdowns/lista-servidor';
import { ServidorService } from 'src/app/seguranca/services/servidor.service';

@Component({
    selector: 'app-lista-grade-de-exame',
    templateUrl: './lista-grade-de-exame.component.html',
    styleUrls: ['./lista-grade-de-exame.component.css'],
})
export class ListaGradeDeExameComponent implements OnInit {
    id: string = '';
    unidadeExecutoraId: string = '';
    ativo: string = '';
    duracao: string = '';
    salaId: string = '';
    exameId: string = '';
    responsavelId: string = '';
    results = [];

    listaGrades: GradeDeAgendamentoExame[];
    listaUnidadesExecutoras: UnidadeFuncional[] = [];
    listaServidores: ListaServidor[] = [];
    listaSalas: Sala[] = [];
    listaExames: ExamModel[] = [];
    listaResponsaveis: Responsavel[] = [];

    situacaoGrade = SituacaoAtivo;

    searchUrl: string = 'madreexames/api/_search/grades-de-agendamento';

    constructor(
        private gradeAgendamentoService: GradeDeAgendamentoService,
        private unidadeFuncionalService: UnidadeFuncionalService,
        private servidorService: ServidorService,
        private exameService: ExamesService,
    ) {}

    ngOnInit(): void {
        this.listarUnidades();
        this.listarServidores();
        this.listarSalas();
        this.listarExames();
        this.listarGrades();
    }

    limparFiltros() {
        this.id = '';
        this.unidadeExecutoraId = '';
        this.ativo = '';
        this.duracao = '';
        this.salaId = '';
        this.exameId = '';
        this.responsavelId = '';
    }

    listarGrades() {
        this.gradeAgendamentoService
            .getGradesDeAgendamento(
                this.id,
                this.unidadeExecutoraId,
                this.ativo,
                this.duracao,
                this.responsavelId,
                this.exameId,
                this.salaId,
            )
            .subscribe((response) => {
                this.listaGrades = response;
            });
    }

    listarExames() {
        this.exameService.getExames().subscribe((response) => {
            this.listaExames = response;
        });
    }

    listarSalas() {
        this.gradeAgendamentoService.getSalas().subscribe((response) => {
            this.listaSalas = response;
        });
    }

    listarServidores() {
        this.servidorService.getServidor().subscribe((response) => {
            this.listaServidores = response;
        });
    }

    listarUnidades() {
        this.unidadeFuncionalService.getUnidades().subscribe((response) => {
            this.listaUnidadesExecutoras = response;
        });
    }
}
