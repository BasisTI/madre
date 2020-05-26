import { TIPO_PROCEDIMENTO_ESPECIAL } from './models/tipoProcedimentoEspecial';
import { ActivatedRoute } from '@angular/router';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-procedimento-especial',
    templateUrl: './procedimento-especial.component.html',
    styleUrls: ['./procedimento-especial.component.css']
})
export class ProcedimentoEspecialComponent implements OnInit, OnDestroy {

    tipoProcedimento = TIPO_PROCEDIMENTO_ESPECIAL;

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {

        const codigoPaciente = this.route.snapshot.params['id'];


        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Procedimento Especial' }
        ]);
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
