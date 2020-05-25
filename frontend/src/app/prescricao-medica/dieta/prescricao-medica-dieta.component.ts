import { ItemPrescricaoDieta } from './models/itemPrescricaoDieta';
import { TipoAprazamento } from './../medicamento/models/tipoAprazamento';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { PrescricaoMedicaDietaService } from './prescricao-medica-dieta.service';
import { BreadcrumbService } from '@nuvem/primeng-components';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-prescricao-medica-dieta',
    templateUrl: './prescricao-medica-dieta.component.html',
    styleUrls: ['./prescricao-medica-dieta.component.css']
})

export class PrescricaoMedicaDietaComponent implements OnInit, OnDestroy {

    paciente = {};

    dietas: any[];

    searchUrl = 'prescricao/api/prescricao-dieta';

    tiposItens = [];

    tiposAprazamentos = [];

    listaUnidadeDieta = [];

    itensDieta: ItemPrescricaoDieta[] = [];


    prescricaoDieta = this.fb.group({
        idPaciente: [null],
        bombaInfusao: [null],
        observacao: [null]
    });

    itemPrescricaoDieta = this.fb.group({
        tipoItemDietaId: [null, Validators.required],
        quantidade: [null],
        frequencia: [null],
        tipoUnidadeDietaId: [null],
        tipoAprazamentoId: [null],
        numeroVezes: [null]
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

        // this.listarDieta(codigoDietaPaciente);

        this.carregarTipoItem();
        this.carregarTipoAprazamento();
        this.carregarTipoUnidade();

    }

    // listarDieta(id: number) {
    //     this.prescricaoMedicaDietaService.listarDieta(id).subscribe(dietas => {
    //         this.dietas = dietas;
    //     });
    // }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {

                this.paciente = paciente.nome;
                this.prescricaoDieta.patchValue({ idPaciente: paciente.id });

                console.log(paciente);

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

    carregarTipoUnidade() {
        return this.prescricaoMedicaDietaService.listarTipoUnidade()
            .subscribe(tiposDeUnidade => {
                this.listaUnidadeDieta = tiposDeUnidade.map(tipo => {
                    return { label: tipo.descricao, value: tipo };
                });
            });
    }

    incluirItem() {
        if (this.itemPrescricaoDieta.valid) {

            this.itensDieta.push(this.itemPrescricaoDieta.value);
            this.itemPrescricaoDieta.reset();
        }

    }

    prescrever() {


        const prescricao = this.prescricaoDieta.value;

        const prescricaoDieta = Object.assign({}, prescricao, {
            itemPrescricaoDietaDTO: this.itensDieta
        });


        prescricaoDieta.itemPrescricaoDietaDTO = prescricaoDieta.itemPrescricaoDietaDTO.map(item => {
            for (let propriedade in item) {
                if (item[propriedade]?.id) {
                    item[propriedade] = item[propriedade].id;
                }
            }

            return item;
        });

        this.prescricaoMedicaDietaService.adicionar(prescricaoDieta).subscribe();
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
