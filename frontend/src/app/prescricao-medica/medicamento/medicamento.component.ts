import { TipoAprazamento } from './models/tipoAprazamento';
import { UnidadeDose } from './models/unidadeDose';
import { ItemPrescricaoMedicamento } from './models/itemPrescricaoMedicamento';
import { MedicamentoService } from './medicamento.service';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-medicamento',
    templateUrl: './medicamento.component.html',
    styleUrls: ['./medicamento.component.css']
})
export class MedicamentoComponent implements OnInit, OnDestroy {

    paciente = { nome: '' };

    medicamentos = [];

    listaMedicamentos = [];

    listaUnidadeDose = [];

    listaViasAdministracao = [];

    listaTipoAprazamento = [];

    itensPrescricaoMedicamento: ItemPrescricaoMedicamento[] = [];

    itemPrescricaoMedicamento = this.fb.group({
        idMedicamento: ['', Validators.required],
        idListaMedicamentos: [''],
        dose: ['', Validators.required],
        unidadeDose: [''],
        frequencia: [''],
        todasVias: [''],
        volume: [''],
        bombaInfusao: [''],
        unidadeInfusao: [''],
        velocidadeInfusao: [''],
        tipoAprazamento:[''],
        tempoInfusao: [''],
        unidadeTempo: [''],
        inicioAdministracao: [''],
        condicaoNecessaria: [''],
        viasAdministracao: [''],
        diluente: [''],
        observacao: ['']
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private medicamentoService: MedicamentoService,
        private fb: FormBuilder
    ) { }


    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Medicamento' }
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

    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {
                this.paciente = paciente;
            });
    }

    carregarMedicamentos() {
        return this.medicamentoService.listarMedicamentos()
            .subscribe(medicamentos => {
                this.medicamentos = medicamentos.content.map(medicamento => {
                    return { label: medicamento.name, value: medicamento };
                });

            });
    }

    carregarListaMedicamentos() {
        return this.medicamentoService.listarListaMedicamentos()
            .subscribe(listaMedicamentos => {
                this.listaMedicamentos = listaMedicamentos.content.map(listaMedicamento => {
                    return { label: listaMedicamento.nome, value: listaMedicamento };
                });

            });
    }

    carregarListaUnidadeDose() {
        return this.medicamentoService.listarUnidadeDose()
            .subscribe(listaUnidadeDose => {
                this.listaUnidadeDose = listaUnidadeDose.map(unidadeDose => {
                    return { label: unidadeDose.descricao, value: unidadeDose };
                });

            });
    }

    carregarViaAdministracao() {
        return this.medicamentoService.listarViaAdministracao()
            .subscribe(lista => {

                this.listaViasAdministracao = lista.map(via => {
                    return { label: via.descricao, value: via };
                });

            });
    }

    carregarTipoAprazamento() {
        return this.medicamentoService.listarTiposAprazamentos()
            .subscribe(tiposAprazamentos => {
                this.listaTipoAprazamento = tiposAprazamentos.map(tipo => {
                    return { label: tipo.descricao, value: tipo };
                });
            });
    }



    incluirItem() {
        if (this.itemPrescricaoMedicamento.valid) {

            this.itensPrescricaoMedicamento.push(this.itemPrescricaoMedicamento.value);
            console.log(this.itemPrescricaoMedicamento.value);

            this.itemPrescricaoMedicamento.reset();
        }

    }

    salvar(prescricaoMedicamento: FormBuilder) {
        console.log(prescricaoMedicamento);


    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
