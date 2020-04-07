import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';

@Component({
    selector: 'app-formulario-cadastro',
    templateUrl: './formulario-cadastro.component.html',
})
export class FormularioCadastroComponent implements OnInit, OnDestroy {
    constructor(private breadCrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        console.log('aqui');

        this.breadCrumbService.setItems([
            { label: 'Cadastro de Paciente', routerLink: 'pacientes' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadCrumbService.reset();
    }
}
