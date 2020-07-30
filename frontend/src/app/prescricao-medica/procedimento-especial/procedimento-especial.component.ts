import { ProcedimentoEspecialService } from './procedimento-especial.service';
import { ItemPrescricaoProcedimento } from './models/item-prescricao-procedimento';
import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { FormBuilder, Validators } from '@angular/forms';
import { TIPO_PROCEDIMENTO_ESPECIAL } from './models/tipo-procedimento-especial';
import { ActivatedRoute, Router } from '@angular/router';
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

    prescricaoProcedimento = this.fb.group({
        idPaciente: [null],
        nome: [null],
        tipo: "PROCEDIMENTO",
        dataPrescricao: [new Date()],
        observacao: [null]
    });

    itemPrescricaoProcedimento = this.fb.group({
        quantidadeOrteseProtese: [null],
        tipoProcedimentoEspecial: [null],
        informacoes: [null],
        justificativa: [null],
        duracaoSolicitada: [null],
        tipoProcedimentoId: [null],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private fb: FormBuilder,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private procedimentoEspecialService: ProcedimentoEspecialService,
        private router: Router
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

                this.paciente = paciente;
                this.prescricaoProcedimento.patchValue({ idPaciente: paciente.id, nome: paciente.nome });

            });
    }

    incluirItem() {
        if (this.itemPrescricaoProcedimento.valid) {

            this.itensPrescricaoProcedimento.push(this.itemPrescricaoProcedimento.value);
            this.itemPrescricaoProcedimento.reset();
        }

    }

    prescrever() {

        const prescricao = this.prescricaoProcedimento.value;

        const prescricaoProcedimentoObject = Object.assign({}, prescricao, {
            itemPrescricaoProcedimentoDTO: this.itensPrescricaoProcedimento
        });

        this.procedimentoEspecialService.prescreverProcedimento(prescricaoProcedimentoObject).subscribe(
            (resposta) => {
                this.router.navigate(['/prescricao-medica/lista/', prescricaoProcedimentoObject.idPaciente]);
                return resposta;

            },
            (erro) => {
                return erro;
            },
        );

    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
