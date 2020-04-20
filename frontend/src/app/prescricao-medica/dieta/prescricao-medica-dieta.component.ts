import { PrescricaoMedicaDietaService } from './prescricao-medica-dieta.service';
import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-prescricao-medica-dieta',
    templateUrl: './prescricao-medica-dieta.component.html',
    styleUrls: ['./prescricao-medica-dieta.component.css']
})

export class PrescricaoMedicaDietaComponent implements OnInit, OnDestroy {

    paciente = { nome: '' };


    dietas: any[];

    searchUrl = 'prescricao/api/prescricao-dieta';

    tipoItem = [];

    tipoAprazamento: [];


    constructor(
        private breadcrumbService: BreadcrumbService,
        private prescricaoMedicaDietaService: PrescricaoMedicaDietaService,
        private route: ActivatedRoute
    ) { }


    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Dieta', }
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

        const codigoDietaPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente) {
            this.carregarPaciente(codigoPaciente);
        }

        this.listarDieta(codigoDietaPaciente);

        this.carregarTipoItem();
        this.carregarTipoAprazamento();

    }

    listarDieta(id: number) {
        this.prescricaoMedicaDietaService.listarDieta(id).subscribe(dietas => {
            this.dietas = dietas;
        });
    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaDietaService.buscarId(id)
            .subscribe(paciente => {
                this.paciente = paciente;
            });
    }



    carregarTipoItem() {
        return this.prescricaoMedicaDietaService.listarTiposItens()
        .subscribe(tipoItem => {
            this.tipoItem = tipoItem.map(item => {
                return {label: item.descricao, value: item.id};
            });
        });
    }

    carregarTipoAprazamento() {
        return this.prescricaoMedicaDietaService.listarTiposAprazamentos()
        .subscribe(tipoAprazamento => {
            this.tipoAprazamento = tipoAprazamento.map(item => {
                return {label: item.descricao, value: item.id};
            });
        });
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
