import { Motivo } from './Motivo';
import { Estorno } from './estorno/estorno';
import { FormBuilder } from '@angular/forms';
import { DispensacaoMedicamento } from './../dispensacao/dispensacao-medicamento/dispensacaoMedicamento';
import { DatatableClickEvent } from '@nuvem/primeng-components';
import { Prescricao } from './../../../internacao/formulario-unidades/models/Prescricao';
import { Router } from '@angular/router';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-estorno-medicamento',
    templateUrl: './estorno-medicamento.component.html',
    styleUrls: ['./estorno-medicamento.component.css'],
})
export class EstornoMedicamentoComponent implements OnInit {
    data: Date;
    nome = '';
    // input = `${this.data.getFullYear()}-${this.data.getMonth()}-${this.data.getDay()}`;
    dataInicio = '';
    dataFim = '';
    local = '';
    url = 'farmacia/api/dispensacao-medicamentos';
    estornoMedicamento = this.fb.group({
        id: [''],
        dispensacaoMedicamentosId: [''],
        estornado: [''],
        motivoId: [''],
    });
    estorno: Estorno;
    id: number;
    motivo: Array<Motivo>;

    constructor(
        private service: FarmaciaService,
        private router: Router,
        private fb: FormBuilder,
    ) {}

    ngOnInit(): void {
        // this.service.getDispensacaoMedicamento().subscribe((dispensacaoMedicamento) => {
        //     this.dispensacaoMedicamento = dispensacaoMedicamento.content;
        //     console.log(this.dispensacaoMedicamento);
        // });
    }

    estornar(event: DatatableClickEvent) {
        this.setEstorno(event);
        console.log(this.estorno);
        this.service.estornar(this.estorno).subscribe();
    }

    setEstorno(event) {
        this.id = event.selection.id;

        this.estornoMedicamento.patchValue({
            id: null,
            dispensacaoMedicamentosId: this.id,
            estornado: true,
        });
        this.estorno = this.estornoMedicamento.value;
        console.log(this.id);
    }
    searchMotivo(event) {
        this.service.getResultMotivo(event.query).subscribe((data: Array<Motivo>) => {
            this.motivo = data;
        });
    }
}

// estornarMedicamento(event: DatatableClickEvent) {
//     this.router.navigate(['/estorno', event.selection.id]);
// }
