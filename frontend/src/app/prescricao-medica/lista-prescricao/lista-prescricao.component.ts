import { ActivatedRoute } from '@angular/router';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ListaPrescricaoService } from './lista-prescricao.service';

@Component({
    selector: 'app-lista-prescricao',
    templateUrl: './lista-prescricao.component.html',
    styleUrls: ['./lista-prescricao.component.css']
})
export class ListaPrescricaoComponent implements OnInit, OnDestroy {

    cols: any[];
    listaPrescricoes = []

    constructor(
        private breadcrumbService: BreadcrumbService,
        private listaPrescricaoService: ListaPrescricaoService,
        private route: ActivatedRoute,
    ) { }

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Prescrições' }
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

        if (codigoPaciente) {
            this.carregarListaPrescricoes(codigoPaciente);

        }

        this.cols = [
            { field: 'observacao', header: 'Nome' },
            { field: 'frequencia', header: 'Frequência' },
            { field: 'tipoAprazamento', header: 'Aprazamento' },
            { field: 'dataPrescricao', header: 'Data' }
        ];
    }

    carregarListaPrescricoes(id: number) {

        return this.listaPrescricaoService.listarPrescricoes(id).subscribe((resposta) => {
            console.log(resposta);

        });
    }



    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }


}
