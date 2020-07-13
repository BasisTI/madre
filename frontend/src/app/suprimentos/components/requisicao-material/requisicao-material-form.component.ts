import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Almoxarifado } from '@suprimentos/models/almoxarifado';
import { CentroAtividade } from '@suprimentos/models/centro-atividade';
import { GrupoMaterial } from '@suprimentos/models/grupo-material';
import { Material } from '@suprimentos/models/material';
import { AlmoxarifadoService } from '@suprimentos/services/almoxarifado.service';
import { CentroAtividadeService } from '@suprimentos/services/centro-atividade.service';
import { GrupoMaterialService } from '@suprimentos/services/grupo-material.service';
import { MaterialService } from '@suprimentos/services/material.service';
import { RequisicaoMaterialService } from './../../services/requisicao-material.service';

@Component({
    selector: 'app-requisicao-material-form',
    templateUrl: './requisicao-material-form.component.html',
})
export class RequisicaoMaterialFormComponent {
    public itens: any[] = [];
    public centros: CentroAtividade[];
    public grupos: GrupoMaterial[];
    public almoxarifados: Almoxarifado[];
    public materiais: Material[];

    public requisicaoForm = this.fb.group({
        caRequisitanteId: [null, Validators.required],
        caAplicacaoId: [null, Validators.required],
        almoxarifadoId: [null, Validators.required],
        grupoMaterialId: [null, Validators.required],
    });

    public itemForm = this.fb.group({
        material: [null, Validators.required],
        quantidade: [null, [Validators.required, Validators.min(0)]],
    });

    constructor(
        private fb: FormBuilder,
        private router: Router,
        private materialService: MaterialService,
        private almoxarifadoService: AlmoxarifadoService,
        private grupoMaterialService: GrupoMaterialService,
        private centroAtividadeService: CentroAtividadeService,
        private requisicaoMaterialService: RequisicaoMaterialService,
    ) {}

    public obterMateriaisPorNome(nome: string): void {
        this.materialService.obterMateriaisPeloNome(nome).subscribe((materiais) => {
            this.materiais = materiais;
        });
    }

    public obterAlmoxarifadoPorDescricao(descricao: string): void {
        this.almoxarifadoService
            .getAlmoxarifadosPorDescricao(descricao)
            .subscribe((almoxarifados) => {
                this.almoxarifados = almoxarifados;
            });
    }

    public obterGruposPorDescricao(descricao: string): void {
        this.grupoMaterialService.getGruposPorDescricao(descricao).subscribe((grupos) => {
            this.grupos = grupos;
        });
    }

    public obterCentrosPorDescricao(descricao: string): void {
        this.centroAtividadeService.getCentrosPorDescricao(descricao).subscribe((centros) => {
            this.centros = centros;
        });
    }

    public salvar() {
        const form = this.normalizeForm(this.requisicaoForm);

        form.itens = this.itens.map((item) => {
            return {
                materialId: item.material.id,
                quantidade: item.quantidade,
            };
        });

        this.requisicaoMaterialService.gerarRequisicaoMaterial(form).subscribe((resposta) => {
            this.router.navigate(['/suprimentos/requisicoes-materiais']);
        });
    }

    public adicionarItem() {
        this.itens.push(this.itemForm.value);
    }

    public normalizeForm(form: AbstractControl): any {
        let value = form.value;

        for (const prop in value) {
            if (value[prop]?.id) {
                value[prop] = value[prop].id;
            }
        }

        return value;
    }
}
