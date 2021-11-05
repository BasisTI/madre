import { FormGroup } from '@angular/forms';
import { TipoProcedimento } from '../../models/tipo-procedimento';
import { OrtesesProteseService } from './ortese-protese.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-ortese-protese',
    templateUrl: './ortese-protese.component.html',
})
export class OrteseProteseComponent implements OnInit {
    @Input() itemPrescricaoProcedimento: FormGroup;

    listaOrtesesProteses = TipoProcedimento[''];

    constructor(private orteseProteseService: OrtesesProteseService) {}

    ngOnInit() {
        this.carregarListaEspeciaisDiversos();
    }

    carregarListaEspeciaisDiversos() {
        return this.orteseProteseService
            .listarOrtesesproteses()
            .subscribe((listaOrtesesProteses) => {
                this.listaOrtesesProteses = listaOrtesesProteses.map((item) => {
                    return { label: item.descricao, value: item };
                });
            });
    }
}
