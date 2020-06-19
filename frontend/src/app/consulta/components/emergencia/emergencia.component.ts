import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';

@Component({
    selector: 'app-emergencia',
    templateUrl: './emergencia.component.html',
    styleUrls: ['./emergencia.component.css'],
})
export class EmergenciaComponent implements OnInit, OnDestroy {
    emergencia = this.fb.group({
        numeroConsulta: [''],
        dataDaConsulta: [''],
        grade: [''],
        prontuario: [''],
        nome: [''],
        especialidade: [''],
        profissional: [''],
        convenio: [''],
        codigoCentral: [''],
        observacao: [''],
        justificativa: [''],
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
                label: 'Emergência',
                routerLink: 'emergencia',
            },
        ]);
    }
    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
