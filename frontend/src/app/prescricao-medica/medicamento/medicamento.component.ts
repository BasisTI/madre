import { TIPO_UNIDADE_TEMPO } from './models/unidadeTempo';

import { ItemPrescricaoMedicamento } from './models/itemPrescricaoMedicamento';
import { MedicamentoService } from './medicamento.service';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-medicamento',
    templateUrl: './medicamento.component.html',
    styleUrls: ['./medicamento.component.css'],
})
export class MedicamentoComponent implements OnInit, OnDestroy {
    public paciente: {};
    public medicamentos = [];
    public listaMedicamentos = [];
    public listaUnidadeDose = [];
    public listaViasAdministracao = [];
    public listaTipoAprazamento = [];
    public listaDiluente = [];
    public listaUnidadeTempo = TIPO_UNIDADE_TEMPO;
    public listaUnidadeInfusao = [];
    public itensPrescricaoMedicamento: ItemPrescricaoMedicamento[] = [];
    public calendarLocale = CALENDAR_LOCALE;

    prescricaoMedicamento = this.fb.group({
        idPaciente: [null],
        nome: [null],
        dataPrescricao: [new Date()],
        tipo: 'MEDICAMENTO',
        observacao: [null],
    });

    itemPrescricaoMedicamento = this.fb.group({
        idMedicamento: [null, Validators.required],
        dose: [null, Validators.required],
        unidadeDose: [null, Validators.required],
        viasAdministracao: [null, Validators.required],
        frequencia: [null],
        todasVias: [null],
        volume: [null],
        bombaInfusao: [null],
        unidadeInfusao: [null],
        velocidadeInfusao: [null],
        tipoAprazamento: [null, Validators.required],
        tempoInfusao: [null],
        unidadeTempo: [null],
        inicioAdministracao: [null],
        condicaoNecessaria: [null],
        diluente: [null],
        observacaoCondicao: [null],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private medicamentoService: MedicamentoService,
        private fb: FormBuilder,
        private router: Router,
    ) {}

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Medicamento' },
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente) {
            this.carregarPaciente(codigoPaciente);
        }

        this.carregarMedicamentos();
        this.carregarListaMedicamentos();
        this.carregarListaUnidadeDose();
        this.carregarViaAdministracao();
        this.carregarTipoAprazamento();
        this.carregarDiluente();
        this.carregarUnidadeInfusao();
    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id).subscribe((paciente) => {
            this.paciente = paciente;
            this.prescricaoMedicamento.patchValue({ idPaciente: paciente.id, nome: paciente.nome });
        });
    }

    carregarMedicamentos(evento?) {
        return this.medicamentoService
            .listarMedicamentos(evento?.query ? evento.query : '')
            .subscribe((medicamentos) => {
                this.medicamentos = medicamentos.content;
            });
    }

    carregarListaMedicamentos() {
        return this.medicamentoService.listarListaMedicamentos().subscribe((listaMedicamentos) => {
            this.listaMedicamentos = listaMedicamentos.content.map((listaMedicamento) => {
                return { label: listaMedicamento.nome, value: listaMedicamento };
            });
        });
    }

    carregarListaUnidadeDose() {
        return this.medicamentoService.listarUnidadeDose().subscribe((listaUnidadeDose) => {
            this.listaUnidadeDose = listaUnidadeDose.map((unidadeDose) => {
                return { label: unidadeDose.descricao, value: unidadeDose };
            });
        });
    }

    carregarViaAdministracao() {
        return this.medicamentoService.listarViaAdministracao().subscribe((lista) => {
            this.listaViasAdministracao = lista.map((via) => {
                return { label: via.descricao, value: via };
            });
        });
    }

    carregarTipoAprazamento() {
        return this.medicamentoService.listarTiposAprazamentos().subscribe((tiposAprazamentos) => {
            this.listaTipoAprazamento = tiposAprazamentos.map((tipo) => {
                return { label: tipo.descricao, value: tipo };
            });
        });
    }

    carregarDiluente() {
        return this.medicamentoService.listarDiluentes().subscribe((diluentes) => {
            this.listaDiluente = diluentes.map((diluente) => {
                return { label: diluente.descricao, value: diluente };
            });
        });
    }

    carregarUnidadeInfusao() {
        return this.medicamentoService.listarUnidadeInfusao().subscribe((unidades) => {
            this.listaUnidadeInfusao = unidades.map((unidade) => {
                return { label: unidade.descricao, value: unidade };
            });
        });
    }

    incluirItem() {
        if (this.itemPrescricaoMedicamento.valid) {
            this.itensPrescricaoMedicamento.push(this.itemPrescricaoMedicamento.value);
            this.itemPrescricaoMedicamento.reset();
        }
    }

    prescrever() {
        const prescricao = this.prescricaoMedicamento.value;

        const prescricaoMedicamento = Object.assign({}, prescricao, {
            itens: this.itensPrescricaoMedicamento,
        });

        prescricaoMedicamento.itens = prescricaoMedicamento.itens.map((item) => {
            if (item.idMedicamento?.id) {
                item.idMedicamento = item.idMedicamento.id;
            }
            return item;
        });

        if (this.itensPrescricaoMedicamento.length == 0) {
            this.prescricaoMedicamento.invalid;
        }

        this.medicamentoService.prescreverMedicamento(prescricaoMedicamento).subscribe(
            (resposta) => {
                this.router.navigate([
                    '/prescricao-medica/lista/',
                    prescricaoMedicamento.idPaciente,
                ]);
                console.log(resposta);
            },
            (erro) => {
                console.error(erro);
            },
        );
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }
}
