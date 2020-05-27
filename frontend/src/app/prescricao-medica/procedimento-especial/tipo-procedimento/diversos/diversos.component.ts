import { FormGroup } from '@angular/forms';
import { EspeciaisDiversos } from './../../models/especiais-diversos';
import { DiversosService } from './diversos.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-diversos',
    templateUrl: './diversos.component.html'
})
export class DiversosComponent implements OnInit {

    @Input() especiaisDiversosForm: FormGroup;

    listaEspeciaisDiversos = EspeciaisDiversos[''];

    constructor(private diversosService: DiversosService) { }

    ngOnInit() {

        this.carregarListaEspeciaisDiversos();

    }

    carregarListaEspeciaisDiversos() {
        return this.diversosService.listarEspeciaisDiversos()
            .subscribe(listaEspeciaisDiversos => {
                this.listaEspeciaisDiversos = listaEspeciaisDiversos.map(item => {
                    return { label: item.descricao, value: item };
                });

            });
    }

}
