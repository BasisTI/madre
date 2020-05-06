import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { PrescricaoMedicaDietaService } from './prescricao-medica-dieta.service';
import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
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

    tiposItens = [];

    tiposAprazamentos: [];

    itensDieta: any[] = [];


    profileForm = this.fb.group({
        tipoItem: ['', Validators.required],
        quantidade: [''],
        tipoUnidade: [''],
        frequencia: [''],
        tipoAprazamento: [''],
        numeroVezes: ['']
    });


    constructor(
        private breadcrumbService: BreadcrumbService,
        private prescricaoMedicaDietaService: PrescricaoMedicaDietaService,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private route: ActivatedRoute,
        private fb: FormBuilder
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
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {
                this.paciente = paciente;

            });
    }


    carregarTipoItem() {
        return this.prescricaoMedicaDietaService.listarTiposItens()
            .subscribe(tiposItens => {
                this.tiposItens = tiposItens.map(item => {
                    return { label: item.descricao, value: item };
                });

            });
    }

    carregarTipoAprazamento() {
        return this.prescricaoMedicaDietaService.listarTiposAprazamentos()
            .subscribe(tiposAprazamentos => {
                this.tiposAprazamentos = tiposAprazamentos.map(tipo => {
                    return { label: tipo.descricao, value: tipo };
                });
            });
    }

    incluirItem() {
        if (this.profileForm.valid) {

            this.itensDieta.push(this.profileForm.value);
            this.profileForm.reset();
        }

    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
