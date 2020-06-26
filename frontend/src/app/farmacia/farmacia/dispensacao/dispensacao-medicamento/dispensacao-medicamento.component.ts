import { Medicamento } from './../../medicamentos/Medicamento';
import { DatatableClickEvent } from '@nuvem/primeng-components';
import { Prescricao } from './../prescricao';
import { FarmaciaService } from './../../farmacia.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { DispensacaoMedicamento } from './dispensacaoMedicamento';
import { timingSafeEqual } from 'crypto';
import { isEmpty } from 'rxjs/operators';

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
    arrayDispensacaoMedicamento = new Array<DispensacaoMedicamento>();
    id;
    dispensado;
    boolean;

    medicamento: Medicamento;
    medicamentos: Medicamento[] = [];

    dispensar(event: DatatableClickEvent) {
        console.log(this.prescricao);
        console.log('xx', this.prescricao.medicamentosDispensados);
        this.setDispensacaoDeMedicamento(event);
        this.getMedicamentoPorId();
        this.for();

        console.log(this.boolean);
        if (this.boolean) {
            this.service
                .DispensarMedicamento(this.dipensacaoMedicamentos)
                .subscribe(
                    (resut) => (
                        this.dispensacoesMedicamento.push(resut),
                        console.log('metodo1', this.prescricao.medicamentosDispensados)
                    ),
                );

            if (this.prescricao.medicamentosDispensados === null) {
                this.prescricao.medicamentosDispensados = this.medicamentos;
                this.prescricao.medicamentosDispensados.push(this.medicamento);
            } else if (this.prescricao.medicamentosDispensados.length === 0) {
                this.prescricao.medicamentosDispensados[0] = this.medicamento;
            } else {
                for (let i = 0; i <= this.prescricao.medicamentosDispensados.length; i++) {
                    console.log('for1');
                    if (this.prescricao.medicamentosDispensados[i] === null) {
                        console.log('for2');
                        this.prescricao.medicamentosDispensados[i] = this.medicamento;
                        break;
                    }
                    console.log('vama ver', this.prescricao);
                }
            }
        }
    }

    ngOnInit(): void {
        const codigoPaciente = this.route.snapshot.params['id'];
        this.buscaPrescricaoPorId(codigoPaciente);
    }

    for() {
        if (this.prescricao.medicamentosDispensados === null) {
            this.prescricao.medicamentosDispensados = this.medicamentos;
            this.boolean = true;
        } else if (this.prescricao.medicamentosDispensados.length === 0) {
            this.boolean = true;
        } else {
            for (let i = 0; i <= this.prescricao.medicamentosDispensados.length; i++) {
                console.log('for4');
                if (this.prescricao.medicamentosDispensados[i] === this.medicamento) {
                    this.boolean = false;
                    console.log('for');
                    break;
                } else {
                    this.boolean = true;
                }
            }
        }
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

    getMedicamentoPorId() {
        this.service.getMedicamentoPorId(this.id).subscribe((resut) => (this.medicamento = resut));
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
// console.log(this.id);
// console.log(this.prescricao);
// console.log(this.dipensacaoMedicamento.value);

// if ((this.prescricao.medicamentosDispensados.length = 0)) {
//     this.prescricao.medicamentosDispensados[i] = this.medicamento;
//     break;
// } else
