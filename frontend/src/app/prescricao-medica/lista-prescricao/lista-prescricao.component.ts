import { ActivatedRoute } from '@angular/router';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ListaPrescricaoService } from './lista-prescricao.service';
import { TreeNode } from 'primeng/api';
@Component({
    selector: 'app-lista-prescricao',
    templateUrl: './lista-prescricao.component.html',
    styleUrls: ['./lista-prescricao.component.css']
})
export class ListaPrescricaoComponent implements OnInit, OnDestroy {

    prescricoes: TreeNode[];

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

    }

    carregarListaPrescricoes(id: number) {

        return this.listaPrescricaoService.listarPrescricoes(id).subscribe((resposta) => {

            this.prescricoes = resposta.map(e => {
                let node = {
                    data: {
                        name: e.tipo,
                        dataPrescricao: e.dataPrescricao
                    },
                    children: []
                }
                if (e.tipo === 'MEDICAMENTO') {
                    node.children = e.itens.map(item => {
                        let node = {
                            data: {
                                name: item.medicamento.nome,
                                descricao: `${item.diluente.descricao}; ${item.tipoAprazamento.descricao}`,
                            }
                        }
                        return node;

                    })
                } else if (e.tipo === 'DIETA') {
                    node.children = e.itens.map(item => {
                        let node = {
                            data: {
                                name: item.tipoItemDieta.descricao,
                                descricao: `${item.quantidade} ${item.tipoUnidadeDieta.sigla}`,
                            }
                        }
                        return node;

                    })
                } else if (e.tipo === 'DIAGNOSTICO') {
                    node.children = e.itens.map(item => {
                        let node = {
                            data: {
                                name: item.cid.codigo,
                                descricao: item.cid.descricao,
                            }
                        }
                        return node;
                    })
                    return node;
                }
            });

        });
    }


    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }


}
