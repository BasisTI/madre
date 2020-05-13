import { DatatableClickEvent } from '@nuvem/primeng-components';
import { Router, ActivatedRoute } from '@angular/router';
import { TriagemService } from './triagem.service';
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
    constructor(
        private breadcrumbService: BreadcrumbService,
        private triagemService: TriagemService,
        private router: Router,
        private route: ActivatedRoute,
    ) {}

    triagens: any[];

    triagem: any;

    searchUrl = 'pacientes/api/triagens/pacientes';

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Emergencia', routerLink: 'emergencia' },
        ]);

        const idTriagem = this.route.snapshot.params['id'];

        if (idTriagem) {
            this.listarTriagemId(idTriagem);
        }

        this.listarTriagens();
    }

    listarTriagemId(id: number) {
        this.triagemService.buscarTriagemId(id).subscribe((triagem) => {
            this.triagem = triagem;
        });
    }

    listarTriagens() {
        this.triagemService.listarTriagem().subscribe((triagens) => {
            this.triagens = triagens.content;
            console.log(triagens);

            // this.triagemService
            //     .recuperar()
            //     .subscribe((atualizaTriagem) => (this.triagemService = atualizaTriagem));
        });
    }

    btnClick(event: DatatableClickEvent) {
        console.log(event);

        if (!event.selection) {
            return;
        }
        switch (event.button) {
            case 'edit':
                console.log('clicado');

                this.router.navigate(['/triagem/edit', event.selection.id]);
                console.log(event.selection);
                break;
        }
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
