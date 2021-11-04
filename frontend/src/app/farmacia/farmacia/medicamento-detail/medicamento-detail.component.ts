import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FarmaciaService } from '../farmacia.service';
import { Medicamentos } from '../medicamentos/Medicamento';

@Component({
    selector: 'app-medicamento-detail',
    templateUrl: './medicamento-detail.component.html',
})
export class MedicamentoDetailComponent implements OnInit {
    public medicamento: Medicamentos = new Medicamentos();

    constructor(private service: FarmaciaService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
    }

    load(id) {
        this.service.find(id).subscribe((medicamento) => {
            this.medicamento = medicamento;
        });
    }
}
