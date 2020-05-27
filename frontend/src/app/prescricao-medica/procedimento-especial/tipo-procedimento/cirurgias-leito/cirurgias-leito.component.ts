import { FormGroup } from '@angular/forms';
import { CirurgiasLeitoService } from './cirurgias-leito.service';
import { CirurgiasLeito } from './../../models/cirurgias-leito';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-cirurgias-leito',
    templateUrl: './cirurgias-leito.component.html'
})
export class CirurgiasLeitoComponent implements OnInit {

    @Input() cirurgiasLeitoForm: FormGroup;

    listaCirurgiasLeito = CirurgiasLeito[''];

    constructor(private cirurgiasLeitoService: CirurgiasLeitoService) { }

    ngOnInit() {
        this.carregarListaEspeciaisDiversos();
    }

    carregarListaEspeciaisDiversos() {
        return this.cirurgiasLeitoService.listarCirurgiasLeito()
            .subscribe(listaCirurgiasLeito => {
                this.listaCirurgiasLeito = listaCirurgiasLeito.map(item => {
                    return { label: item.descricao, value: item };
                });

            });
    }

}
