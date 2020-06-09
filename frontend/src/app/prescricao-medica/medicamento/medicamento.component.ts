import { Medicamento } from './../../farmacia/farmacia/medicamentos/Medicamento';
import { TIPO_UNIDADE_TEMPO } from './models/unidadeTempo';

import { ItemPrescricaoMedicamento } from './models/itemPrescricaoMedicamento';
import { MedicamentoService } from './medicamento.service';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-medicamento',
    templateUrl: './medicamento.component.html',
    styleUrls: ['./medicamento.component.css']
})
export class MedicamentoComponent implements OnInit, OnDestroy {

    paciente: {};
    medicamentos = [];

    listaMedicamentos = [];

    listaUnidadeDose = [];

    listaViasAdministracao = [];

    listaTipoAprazamento = [];

    listaDiluente = [];

    listaUnidadeTempo = TIPO_UNIDADE_TEMPO;

    listaUnidadeInfusao = [];

    itensPrescricaoMedicamento: ItemPrescricaoMedicamento[] = [];

    prescricaoMedicamento = this.fb.group({
        idPaciente: [null],
        observacao: [null]
    });

    itemPrescricaoMedicamento = this.fb.group({
        idMedicamento: [null, Validators.required],
        dose: [null, Validators.required],
        unidadeDoseId: [null, Validators.required],
        viasAdministracaoId: [null, Validators.required],
        frequencia: [null],
        todasVias: [null],
        volume: [null],
        bombaInfusao: [null],
        unidadeInfusaoId: [null],
        velocidadeInfusao: [null],
        tipoAprazamentoId: [null, Validators.required],
        tempoInfusao: [null],
        unidadeTempo: [null],
        inicioAdministracao: [null],
        condicaoNecessaria: [null],
        diluenteId: [null],
        observacaoCondicao: [null]
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
        this.carregarDiluente();
        this.carregarUnidadeInfusao();

    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {

                this.paciente = paciente.nome;
                this.prescricaoMedicamento.patchValue({ idPaciente: paciente.id });
                console.log(paciente);

            });
    }

    carregarMedicamentos(evento?) {
    

        return this.medicamentoService.listarMedicamentos((evento?.query)?evento.query:'')
            .subscribe(medicamentos => {
                this.medicamentos = medicamentos.content;

            });
    }

    carregarListaMedicamentos() {
        return this.medicamentoService.listarListaMedicamentos()
            .subscribe(listaMedicamentos => {
                console.log(listaMedicamentos.content);
                
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

    carregarDiluente() {
        return this.medicamentoService.listarDiluentes()
            .subscribe(diluentes => {

                this.listaDiluente = diluentes.map(diluente => {
                    return { label: diluente.descricao, value: diluente };
                });
            });
    }

    carregarUnidadeInfusao() {
        return this.medicamentoService.listarUnidadeInfusao()
            .subscribe(unidades => {

                this.listaUnidadeInfusao = unidades.map(unidade => {
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
            itemPrescricaoMedicamentos: this.itensPrescricaoMedicamento
        });


        prescricaoMedicamento.itemPrescricaoMedicamentos = prescricaoMedicamento.itemPrescricaoMedicamentos.map(item => {
            for (let propriedade in item) {
                if (item[propriedade]?.id) {
                    item[propriedade] = item[propriedade].id;
                }
            }

            return item;
        });


        this.medicamentoService.prescreverMedicamento(prescricaoMedicamento).subscribe();
        this.itensPrescricaoMedicamento = [];
        console.log(prescricaoMedicamento);
        
    }


    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
