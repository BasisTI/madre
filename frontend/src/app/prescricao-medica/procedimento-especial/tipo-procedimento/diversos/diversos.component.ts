import { FormGroup } from '@angular/forms';
import { DiversosService } from './diversos.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-diversos',
    templateUrl: './diversos.component.html',
})
export class DiversosComponent implements OnInit {
    @Input() itemPrescricaoProcedimento: FormGroup;

    listaEspeciaisDiversos: any;

    constructor(private diversosService: DiversosService) {}

    ngOnInit() {
        this.carregarListaEspeciaisDiversos();
    }

    carregarListaEspeciaisDiversos() {
        return this.diversosService
            .listarEspeciaisDiversos()
            .subscribe((listaEspeciaisDiversos) => {
                this.listaEspeciaisDiversos = listaEspeciaisDiversos.map((item) => {
                    return { label: item.descricao, value: item };
                });
            });
    }
}
