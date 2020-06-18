import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { BreadcrumbService } from '@nuvem/primeng-components';

@Component({
    selector: 'app-emergencia',
    templateUrl: './emergencia.component.html',
    styleUrls: ['./emergencia.component.css'],
})
export class EmergenciaComponent implements OnInit, OnDestroy {
    emergencia = this.fb.group({
        numeroConsulta: [''],
        data: [''],
        nome: [''],
        consultaAnterior: [''],
        prontuario: [''],
        codigoPaciente: [''],
        situacaoAtendimento: [''],
        grade: [''],
        especialidade: [''],
        retornoPaciente: [''],
        pagador: [''],
        autorizacao: [''],
        condicaoAtendimento: [''],
        retornoPagador: [''],
        movimentacao: [''],
        operacao: [''],
        responsavel: [''],
        motivo: [''],
    });

    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Emergência',
                routerLink: 'emergencia',
            },
        ]);
    }
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
