import { Prescricao } from './../../dispensacao/prescricao';
import { DatatableClickEvent } from '@nuvem/primeng-components';
import { DispensacaoMedicamento } from './../../dispensacao/dispensacao-medicamento/dispensacaoMedicamento';
import { FarmaciaService } from './../../farmacia.service';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-estorno',
    templateUrl: './estorno.component.html',
    styleUrls: ['./estorno.component.css'],
})
export class EstornoComponent implements OnInit {
    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private service: FarmaciaService,
    ) {}
    codigoPaciente = {};
    prescricao: Prescricao = {
        medicamentos: [],
        medicamentosDispensados: [],
    };

    form = this.fb.group({
        id: [''],
        nome: [''],
        dataInicio: [''],
    });

    resultDispensacoesMedicamentos: DispensacaoMedicamento[];

    dipensacaoMedicamento = this.fb.group({
        dispensacaoId: [''],
        dispensado: [''],
        medicamentosId: [''],
    });

    dipensacaoMedicamentos: DispensacaoMedicamento;
    dispensacoesMedicamento = [];

    id;

    dispensar(event: DatatableClickEvent) {
        this.setDispensacaoDeMedicamento(event);

        this.service.DispensarMedicamento(this.dipensacaoMedicamentos).subscribe();
    }

    ngOnInit(): void {
        const codigoPaciente = this.route.snapshot.params['id'];
        this.buscaPrescricaoPorId(codigoPaciente);
    }

    setDispensacaoDeMedicamento(event) {
        this.id = event.selection.id;

        this.dipensacaoMedicamento.patchValue({
            id: null,
            dispensacaoId: this.prescricao.idDispensacao,
            medicamentosId: this.id,
        });
        this.dipensacaoMedicamentos = this.dipensacaoMedicamento.value;
        console.log('set');
    }

    buscaPrescricaoPorId(id: number) {
        this.service.getPrescricaoPorId(id).subscribe((res) => (this.prescricao = res));

        this.service.getPrescricaoPorId(id).subscribe((prescricao) => {
            this.prescricao = prescricao;
            this.form.patchValue({
                id: prescricao.id,
                nome: prescricao.nome,
                dataInicio: prescricao.dataInicio,
            });
        });
    }
}
