import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from '../../../breadcrumb/breadcrumb.service';

@Component({
    selector: 'app-triagem',
    templateUrl: './triagem.component.html',
    styles: [
        `
            .ui-widget.read-only:disabled {
                opacity: 1;
                background-color: #dddddd;
            }

            div {
                margin: 3px;
            }
        `,
    ],
})
export class TriagemComponent implements OnInit, OnDestroy {
    constructor(private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
