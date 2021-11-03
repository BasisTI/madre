import { ItemPrescricaoDieta } from './models/itemPrescricaoDieta';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { PrescricaoMedicaDietaService } from './prescricao-medica-dieta.service';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';

import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-prescricao-medica-dieta',
    templateUrl: './prescricao-medica-dieta.component.html',
    styleUrls: ['./prescricao-medica-dieta.component.css'],
})
export class PrescricaoMedicaDietaComponent implements OnInit, OnDestroy {
    public paciente = {};
    public dietas: any[];
    public searchUrl = 'prescricao/api/prescricao-dieta';
    public tiposItens = [];
    public tiposAprazamentos = [];
    public listaUnidadeDieta = [];
    public itensDieta: ItemPrescricaoDieta[] = [];
    public calendarLocale = CALENDAR_LOCALE;

    prescricaoDieta = this.fb.group({
        idPaciente: [null],
        nome: [null],
        tipo: 'DIETA',
        dataPrescricao: [new Date()],
        bombaInfusao: [null],
        observacao: [null],
    });

    itemPrescricaoDieta = this.fb.group({
        tipoItemDieta: [null, Validators.required],
        quantidade: [null],
        frequencia: [null],
        tipoUnidadeDieta: [null],
        tipoAprazamento: [null],
        numeroVezes: [null],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private prescricaoMedicaDietaService: PrescricaoMedicaDietaService,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private router: Router,
    ) {}

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Dieta' },
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente) {
            this.carregarPaciente(codigoPaciente);
        }

        this.carregarTipoItem();
        this.carregarTipoAprazamento();
        this.carregarTipoUnidade();
    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id).subscribe((paciente) => {
            this.paciente = paciente;
            this.prescricaoDieta.patchValue({ idPaciente: paciente.id, nome: paciente.nome });

            console.log(paciente);
        });
    }

    carregarTipoItem() {
        return this.prescricaoMedicaDietaService.listarTiposItens().subscribe((tiposItens) => {
            this.tiposItens = tiposItens.map((item) => {
                return { label: item.descricao, value: item };
            });
        });
    }

    carregarTipoAprazamento() {
        return this.prescricaoMedicaDietaService
            .listarTiposAprazamentos()
            .subscribe((tiposAprazamentos) => {
                this.tiposAprazamentos = tiposAprazamentos.map((tipo) => {
                    return { label: tipo.descricao, value: tipo };
                });
            });
    }

    carregarTipoUnidade() {
        return this.prescricaoMedicaDietaService.listarTipoUnidade().subscribe((tiposDeUnidade) => {
            this.listaUnidadeDieta = tiposDeUnidade.map((tipo) => {
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
            itens: this.itensDieta,
        });

        this.prescricaoMedicaDietaService.adicionar(prescricaoDieta).subscribe(
            (resposta) => {
                this.router.navigate(['/prescricao-medica/lista/', prescricaoDieta.idPaciente]);
                console.log(resposta);
            },
            (erro) => {
                console.error(erro);
            },
        );
        this.itensDieta = [];
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }
}
