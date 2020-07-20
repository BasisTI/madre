import { TipoProcedimento } from './../../models/tipo-procedimento';
import { FormGroup } from '@angular/forms';
import { CirurgiasLeitoService } from './cirurgias-leito.service';
import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';

@Component({
    selector: 'app-cirurgias-leito',
    templateUrl: './cirurgias-leito.component.html'
})
export class CirurgiasLeitoComponent implements OnInit, AfterViewInit {

    @Input() itemPrescricaoProcedimento: FormGroup;

    public listaCirurgiasLeito: any;

    constructor(private cirurgiasLeitoService: CirurgiasLeitoService) { }

    ngOnInit() {
        this.carregarListaEspeciaisDiversos();
    }


    carregarListaEspeciaisDiversos() {
        return this.cirurgiasLeitoService.listarCirurgiasLeito()
            .subscribe(listaCirurgiasLeito => {
                console.log(listaCirurgiasLeito);

                this.listaCirurgiasLeito = listaCirurgiasLeito.map(item => {
                    return { label: item.descricao, value: item }
                });

            });
    }
    ngAfterViewInit() {

    }

}
