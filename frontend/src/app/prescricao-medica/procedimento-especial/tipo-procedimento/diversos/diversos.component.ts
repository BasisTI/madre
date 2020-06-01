import { TipoProcedimento } from './../../models/tipo-procedimento';
import { FormGroup } from '@angular/forms';
import { DiversosService } from './diversos.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-diversos',
    templateUrl: './diversos.component.html'
})
export class DiversosComponent implements OnInit {

    @Input() itemPrescricaoProcedimento: FormGroup;

    listaEspeciaisDiversos = TipoProcedimento[''];

    constructor(private diversosService: DiversosService) { }

    ngOnInit() {

        this.carregarListaEspeciaisDiversos();

    }

    carregarListaEspeciaisDiversos() {
        return this.diversosService.listarEspeciaisDiversos()
            .subscribe(listaEspeciaisDiversos => {
                console.log(listaEspeciaisDiversos);

                this.listaEspeciaisDiversos = listaEspeciaisDiversos.map(item => {
                    return { label: item.descricao, value: item };
                });

            });
    }

}
