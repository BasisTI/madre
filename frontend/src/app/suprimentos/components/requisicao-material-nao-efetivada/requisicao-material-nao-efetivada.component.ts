import { Component, OnInit } from '@angular/core';
import { DatatableClickEvent } from '@nuvem/primeng-components';
import { RequisicaoMaterialService } from '@suprimentos/services/requisicao-material.service';

@Component({
    selector: 'app-requisicao-material-nao-efetivada',
    templateUrl: './requisicao-material-nao-efetivada.component.html',
})
export class RequisicaoMaterialNaoEfetivadaComponent implements OnInit {
    public url: string;

    constructor(
        private requisicaoMaterialService: RequisicaoMaterialService,
    ) {}

    ngOnInit(): void {
        this.url = `${this.requisicaoMaterialService.getResource()}/nao-efetivadas`;
    }

    public onDatatableButtonClick(evento: DatatableClickEvent): void {
        this.requisicaoMaterialService.efetivarRequisicao(evento.selection?.id).subscribe(
            (resposta) => {
                console.log(resposta);
            },
            (erro) => {
                console.error(erro);
            },
        );
    }
}
