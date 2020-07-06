import {Component, OnInit} from '@angular/core';
import {AlmoxarifadoService} from '@suprimentos/almoxarifado/almoxarifado.service';
import {ClassificacaoMaterialService} from '@suprimentos/classificacao-material/classificacao-material.service';
import {Almoxarifado} from '@suprimentos/almoxarifado/almoxarifado';
import {ClassificacaoMaterial} from '@suprimentos/classificacao-material/classificacao-material';
import {AbstractControl, FormBuilder, Validators} from '@angular/forms';
import {TransferenciaAutomaticaService} from '@suprimentos/transferencia-automatica/transferencia-automatica.service';
import {TransferenciaAutomaticaDTO} from '@suprimentos/transferencia-automatica/transferencia-automatica';
import {Router} from '@angular/router';
import {ItemTransferencia} from '@suprimentos/item-transferencia/item-transferencia';
import {MaterialService} from '@suprimentos/material/material.service';
import {Material} from '@suprimentos/material/material';

@Component({
    selector: 'app-transferencia-automatica-form',
    templateUrl: './transferencia-automatica-form.component.html',
    styles: [],
})
export class TransferenciaAutomaticaFormComponent implements OnInit {
    public almoxarifados: Almoxarifado[];
    public classificacoes: ClassificacaoMaterial[];
    public materiais: Material[];
    public itens: any[] = [];

    public transferenciaForm = this.fb.group({
        origemId: [null, Validators.required],
        destinoId: [null, Validators.required],
        classificacaoMaterialId: [null, Validators.required]
    });

    public itemForm = this.fb.group({
        quantidadeEnviada: [null, [Validators.required, Validators.min(1)]],
        material: [null, Validators.required]
    });

    constructor(
        private router: Router,
        private fb: FormBuilder,
        private transferenciaAutomaticaService: TransferenciaAutomaticaService,
        private almoxarifadoService: AlmoxarifadoService,
        private materialService: MaterialService,
        private classificacaoMaterialService: ClassificacaoMaterialService) {
    }

    public getAlmoxarifadoPorDescricao(descricao: string): void {
        this.almoxarifadoService.getAlmoxarifadosPorDescricao(descricao).subscribe(almoxarifados => {
            this.almoxarifados = almoxarifados;
        });
    }

    public getClassificacoesPorDescricao(descricao: string): void {
        this.classificacaoMaterialService.getClassificacoesPorDescricao(descricao).subscribe(classificacoes => {
            this.classificacoes = classificacoes;
        });
    }

    public getMateriaisPorNome(nome: string): void {
        this.materialService.obterMateriaisPeloNome(nome).subscribe(materiais => this.materiais = materiais);
    }

    public adicionarItem() {
        const item = this.itemForm.value;
        console.log('Novo item: ', item);
        this.itens.push(item);
        console.log('Itens: ', this.itens);
    }

    public salvar(): void {
        const form = this.normalizeForm(this.transferenciaForm);
        form.itens = this.itens.map((item) => {
            return {quantidadeEnviada: item.quantidadeEnviada, materialId: item.material.id };
        });

        console.log(form);

        this.transferenciaAutomaticaService.criarNovaTransferencia(form).subscribe(
            resposta => {
                this.router.navigate(['/suprimentos/transferencias-automaticas']);
            },
            erro => {
                console.error(erro);
            }
        );
    }

    public normalizeForm(form: AbstractControl): TransferenciaAutomaticaDTO {
        const value = form.value;

        for (const prop in value) {
            if (value[prop]?.id) {
                value[prop] = value[prop].id;
            }
        }

        return {
            origemId: value['origemId'],
            destinoId: value['destinoId'],
            informacaoTransferencia: {
                classificacaoMaterialId: value['classificacaoMaterialId']
            }
        };
    }

    ngOnInit(): void {
    }

}
