import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';

@Component({
    selector: 'app-listar-consultas',
    templateUrl: './listar-consultas.component.html',
})
export class ListarConsultasComponent implements OnInit, OnDestroy {
    listaConsultas = this.fb.group({
        prontuario: [''],
        codigo: [''],
        nome: [''],
        consulta: [''],
        codigoCentral: [''],
        consultaAnterior: [''],
        condicaoAtendimento: [''],
        especialidade: [''],
        grade: [''],
        dataInicial: [''],
        dataFinal: [''],
        historico: [''],
        consultas: [''],
        dataHora: [''],
        unidadeFuncional: [''],
        equipe: [''],
        profissional: [''],
        situacaoAtendimento: [''],
        excedente: [''],
    });

    @Input() formularioTriagem: FormGroup;
    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2010:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Pequisar Consultas',
                routerLink: 'listar-consultas',
            },
        ]);
    }
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
