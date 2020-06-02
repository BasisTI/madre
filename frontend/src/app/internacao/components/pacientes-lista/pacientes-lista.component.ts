import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { api } from '@internacao/api';

@Component({
    selector: 'app-pacientes-lista',
    templateUrl: './pacientes-lista.component.html',
})
export class PacientesListaComponent {
    public readonly uri = `${api}/pacientes`;

    constructor(private router: Router) {}

    onButtonClick(evento: DatatableClickEvent): void {
        if (!evento.selection) {
            return;
        }

        if (evento.button === 'create') {
            this.router.navigate(['internacao/solicitacao-de-internacao', evento.selection.id]);
        }
    }
}
