import { Component, OnInit } from '@angular/core';
import { UnidadeFuncional } from '@internacao/models/unidade-funcional';
import { SituacaoAtivo } from '../../models/dropdowns/situacao.dropdown';
import { Sala } from '../../models/subjects/sala';
import { SalasService } from '../../services/sala.service';
import { UnidadeFuncionalService } from '../../services/unidade-funcional.service';

@Component({
    selector: 'app-lista-salas',
    templateUrl: './lista-salas.component.html',
    styleUrls: ['./lista-salas.component.css'],
})
export class ListaSalasComponent implements OnInit {
    id: string = '';
    nome: string = '';
    locacao: string = '';
    ativo: string = '';
    unidadeExecutoraId: string = '';

    salas: Sala[] = [];
    unidades: UnidadeFuncional[] = [];

    situacaoSala = SituacaoAtivo;

    searchUrl: string = 'madreexames/api';

    constructor(
        private unidadeFuncionalService: UnidadeFuncionalService,
        private salaService: SalasService,
    ) {}

    ngOnInit(): void {
        this.listarSalas();
        this.listarUnidades();
    }

    listarSalas() {
        this.salaService
            .getSalasFiltradas(
                this.id,
                this.nome,
                this.locacao,
                this.ativo,
                this.unidadeExecutoraId,
            )
            .subscribe((response) => {
                this.salas = response;
            });
    }

    listarUnidades() {
        this.unidadeFuncionalService.getUnidades().subscribe((response) => {
            this.unidades = response;
        });
    }

    limparFiltros() {
        this.nome = '';
        this.locacao = '';
        this.ativo = '';
        this.unidadeExecutoraId = '';
        this.id = '';
    }
}
