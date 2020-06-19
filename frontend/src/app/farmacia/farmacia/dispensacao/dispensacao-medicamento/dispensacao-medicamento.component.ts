import { DatatableClickEvent } from '@nuvem/primeng-components';
import { Prescricao } from './../prescricao';
import { FarmaciaService } from './../../farmacia.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-dispensacao-medicamento',
    templateUrl: './dispensacao-medicamento.component.html',
    styleUrls: ['./dispensacao-medicamento.component.css'],
})
export class DispensacaoMedicamentoComponent implements OnInit {
    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private service: FarmaciaService,
    ) {}
    codigoPaciente = {};
    prescricao: Prescricao = {
        medicamentos: [],
    };

    form = this.fb.group({
        id: [''],
        nome: [''],
        dataInicio: [''],
    });
    dipensacao = this.fb.group({
        idPrescricao: [''],
    });
    dipensacaoMedicamento = this.fb.group({
        dispensado: [''],
        idMedicamento: [''],
    });
    id;
    dispensado: boolean;

    dispensar(event: DatatableClickEvent) {
        this.id = event.selection.id;
        console.log(this.id);
        console.log(this.dispensado);
        console.log(this.prescricao);

        this.service.DispensarMedicamento(this.prescricao, this.id, this.dispensado).subscribe();
    }

    ngOnInit(): void {
        const codigoPaciente = this.route.snapshot.params['id'];
        this.buscaPrescricaoPorId(codigoPaciente);
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
