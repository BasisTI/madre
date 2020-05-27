import { ItemPrescricaoProcedimento } from './models/item-prescricao-procedimento';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { FormBuilder, Validators } from '@angular/forms';
import { TIPO_PROCEDIMENTO_ESPECIAL } from './models/tipo-procedimento-especial';
import { ActivatedRoute } from '@angular/router';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-procedimento-especial',
    templateUrl: './procedimento-especial.component.html',
    styleUrls: ['./procedimento-especial.component.css']
})
export class ProcedimentoEspecialComponent implements OnInit, OnDestroy {

    paciente: {};

    tipoProcedimento = TIPO_PROCEDIMENTO_ESPECIAL;

    itensPrescricaoProcedimento: ItemPrescricaoProcedimento[] = [];

    especiaisDiversosForm = this.fb.group({
        descricao: [null, Validators.required],
    });

    cirurgiasLeitoForm = this.fb.group({
        descricao: [null],
    });

    orteseProteseForm = this.fb.group({
        decricao: [null],
        quantidadeOrteseProtese: [null],
    });

    prescricaoProcedimento = this.fb.group({
        idPaciente: [null],
        observacao: [null]
    });

    itemPrescricaoProcedimento = this.fb.group({
        tipoProcedimento: [null],
        informacoes: [null],
        justificativa: [null],
        duracaoSolicitada: [null],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private prescricaoMedicaService: PrescricaoMedicaService
    ) { }

    ngOnInit() {

        const codigoPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente) {
            this.carregarPaciente(codigoPaciente);
        }

        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Procedimento Especial' }
        ]);
    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {

                this.paciente = paciente.nome;
                this.prescricaoProcedimento.patchValue({ idPaciente: paciente.id });

            });
    }

    incluirItem() {
        const teste = this.itemPrescricaoProcedimento.value;
        const objeto = Object.assign({}, teste, {
            especiaisDiversosId: this.especiaisDiversosForm.value.descricao,
            cirurgiasLeitoId: this.cirurgiasLeitoForm.value.descricao,
            orteseProteseId: this.orteseProteseForm.value.decricao
        });

        if (this.itemPrescricaoProcedimento.valid) {

            this.itensPrescricaoProcedimento.push(objeto);
            console.log(objeto);

            this.especiaisDiversosForm.reset();
            this.cirurgiasLeitoForm.reset();
            this.orteseProteseForm.reset();
            this.itemPrescricaoProcedimento.reset();
        }

    }

    // prescrever() {


    //     const prescricao = this.prescricaoProcedimento.value;

    //     const prescricaoMedicamento = Object.assign({}, prescricao, {
    //         itemPrescricaoMedicamentos: this.itensPrescricaoMedicamento
    //     });


    //     prescricaoMedicamento.itemPrescricaoMedicamentos = prescricaoMedicamento.itemPrescricaoMedicamentos.map(item => {
    //         for (let propriedade in item) {
    //             if (item[propriedade]?.id) {
    //                 item[propriedade] = item[propriedade].id;
    //             }
    //         }

    //         return item;
    //     });


    //     this.medicamentoService.prescreverMedicamento(prescricaoMedicamento).subscribe();
    // }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
