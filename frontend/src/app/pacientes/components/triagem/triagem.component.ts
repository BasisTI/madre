import { DatatableClickEvent } from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { TriagemService } from './triagem.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';

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
    styleUrls: ['triagem.scss'],
})
export class TriagemComponent implements OnInit, OnDestroy {
    constructor(
        private breadcrumbService: BreadcrumbService,
        private triagemService: TriagemService,
        private router: Router,
    ) {}

    triagens: any;

    searchUrl = 'pacientes/api/triagens/listagem';

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem', routerLink: 'triagem' },
        ]);

        this.listarTriagens();
    }

    listarTriagemId(id: number) {
        this.triagemService.buscarTriagemId(id).subscribe((triagem) => {
            this.triagens = triagem;
        });
    }

    listarTriagens() {
        this.triagemService.listarTriagem().subscribe((triagens) => {
            this.triagens = triagens.content;
            console.log(triagens.content);
        });
    }
    getMensagem(tipo: String): string {
        let descricao = 'Não informado';
        switch (tipo) {
            case 'EMERGENCIA': {
                descricao = 'Emergência';
                break;
            }
            case 'MUITO_URGENTE': {
                descricao = 'Muito urgente';
                break;
            }
            case 'URGENTE': {
                descricao = 'Urgente';
                break;
            }

            case 'POUCO_URGENTE': {
                descricao = 'Pouco urgente';
                break;
            }
            case 'NAO': {
                descricao = 'Não urgente';
                break;
            }
        }
        return descricao;
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
