import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Almoxarifado } from '@suprimentos/models/almoxarifado';
import { ClassificacaoMaterial } from '@suprimentos/models/classificacao-material';
import { Material } from '@suprimentos/models/material';
import { TransferenciaAutomaticaDTO } from '@suprimentos/models/transferencia-automatica';
import { AlmoxarifadoService } from '@suprimentos/services/almoxarifado.service';
import { ClassificacaoMaterialService } from '@suprimentos/services/classificacao-material.service';
import { MaterialService } from '@suprimentos/services/material.service';
import { TransferenciaAutomaticaService } from '@suprimentos/services/transferencia-automatica.service';

@Component({
    selector: 'app-transferencia-automatica-form',
    templateUrl: './transferencia-automatica-form.component.html',
})
export class TransferenciaAutomaticaFormComponent {
    public almoxarifados: Almoxarifado[];
    public classificacoes: ClassificacaoMaterial[];
    public materiais: Material[];
    public itens: any[] = [];

    public transferenciaForm = this.fb.group({
        origemId: [null, Validators.required],
        destinoId: [null, Validators.required],
        classificacaoMaterialId: [null, Validators.required],
    });

    public itemForm = this.fb.group({
        quantidadeEnviada: [null, [Validators.required, Validators.min(1)]],
        material: [null, Validators.required],
    });

    constructor(
        private router: Router,
        private fb: FormBuilder,
        private transferenciaAutomaticaService: TransferenciaAutomaticaService,
        private almoxarifadoService: AlmoxarifadoService,
        private materialService: MaterialService,
        private classificacaoMaterialService: ClassificacaoMaterialService,
    ) {}
    ngOnInit(): void {
        throw new Error('Method not implemented.');
    }

    public getAlmoxarifadoPorDescricao(descricao: string): void {
        this.almoxarifadoService
            .getAlmoxarifadosPorDescricao(descricao)
            .subscribe((almoxarifados) => {
                this.almoxarifados = almoxarifados;
            });
    }

    public getClassificacoesPorDescricao(descricao: string): void {
        this.classificacaoMaterialService
            .getClassificacoesPorDescricao(descricao)
            .subscribe((classificacoes) => {
                this.classificacoes = classificacoes;
            });
    }

    public getMateriaisPorNome(nome: string): void {
        this.materialService
            .obterMateriaisPeloNome(nome)
            .subscribe((materiais) => (this.materiais = materiais));
    }

    public adicionarItem() {
        const item = this.itemForm.value;
        this.itens.push(item);
    }

    public salvar(): void {
        const form = this.normalizeForm(this.transferenciaForm);
        form.itens = this.itens.map((item) => {
            return { quantidadeEnviada: item.quantidadeEnviada, materialId: item.material.id };
        });

        console.log(form);

        this.transferenciaAutomaticaService.criarNovaTransferencia(form).subscribe(
            (resposta) => {
                this.router.navigate(['/suprimentos/transferencias-automaticas']);
            },
            (erro) => {
                console.error(erro);
            },
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
                classificacaoMaterialId: value['classificacaoMaterialId'],
            },
        };
    }
}
