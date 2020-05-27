import { FormGroup } from '@angular/forms';
import { OrtesesProteses } from './../../models/orteses-protese';
import { OrtesesProteseService } from './ortese-protese.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-ortese-protese',
    templateUrl: './ortese-protese.component.html'
})
export class OrteseProteseComponent implements OnInit {

    @Input() orteseProteseForm: FormGroup;

    listaOrtesesProteses = OrtesesProteses[''];

    constructor(private orteseProteseService: OrtesesProteseService) { }

    ngOnInit() {
        this.carregarListaEspeciaisDiversos();

    }

    carregarListaEspeciaisDiversos() {
        return this.orteseProteseService.listarOrtesesproteses()
            .subscribe(listaOrtesesProteses => {
                console.log(listaOrtesesProteses);

                this.listaOrtesesProteses = listaOrtesesProteses.map(item => {
                    return { label: item.decricao, value: item };
                });

            });
    }

}
