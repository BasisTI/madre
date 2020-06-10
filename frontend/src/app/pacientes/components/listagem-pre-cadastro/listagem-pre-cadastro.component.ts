import { PreCadastroService } from './../pre-cadastro/pre-cadastro.service';
import { Component, OnInit } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { PreCadastroModel } from '../../models/pre-cadastro-model';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
    selector: 'app-listagem-pre-cadastro',
    templateUrl: './listagem-pre-cadastro.component.html',
    styleUrls: ['./listagem-pre-cadastro.component.css'],
})
export class ListagemPreCadastroComponent implements OnInit {
    preCadastros: PreCadastroModel[] = [];

    preCadstroPaciente: PreCadastroModel[];

    searchUrl = 'pacientes/api/pre-cadastro-pacientes';

    constructor(
        private breadcrumbService: BreadcrumbService,
        private service: PreCadastroService,
    ) {}
    nome = '';

    pesquisar() {
        this.service.buscarPreCadastroPaciente(this.nome).subscribe((resp) => {
            this.preCadastros = resp;
        });
        console.log(this.nome);
    }

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            {
                label: 'Pre-Cadastro de Paciente',
                routerLink: 'lista-pre-cadastro-paciente',
            },
        ]);
    }
    consultar(): void {
        this.service.buscaListaDePacientes();
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
