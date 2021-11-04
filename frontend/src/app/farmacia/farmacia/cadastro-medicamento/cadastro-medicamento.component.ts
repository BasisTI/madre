import { Unidade } from './../dispensacao/unidade';
import { FormBuilder, Validators } from '@angular/forms';
import { TipoMedicamento } from './tipoMedicamento';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit } from '@angular/core';
import { Apresentacao } from './apresentacao';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Medicamentos } from '../medicamentos/Medicamento';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';

@Component({
    selector: 'app-cadastro-medicamento',
    templateUrl: './cadastro-medicamento.component.html',
    styleUrls: ['./cadastro-medicamento.component.css'],
})
export class CadastroMedicamentoComponent implements OnInit {
    medicamento: Medicamentos = new Medicamentos();
    form = this.fb.group({
        id: [''],
        nome: ['', Validators.required],
        descricao: ['', Validators.required],
        concentracao: ['', Validators.required],
        unidade: ['', Validators.required],
        apresentacao: ['', Validators.required],
        ativo: ['', Validators.required],
    });

    results = new Array<TipoMedicamento>();
    unidade = new Array<Unidade>();
    apresentacao = new Array<Apresentacao>();
    pageNotificationService: any;
    medicamentoId: number;

    searchTipoMedicamento(event) {
        if (event) {
            this.service
                .getResultTipoMedicamento(event.query)
                .subscribe((data: Array<TipoMedicamento>) => {
                    this.results = data;
                });
        }
    }
    searchUnidade(event) {
        if (event) {
            this.service.getResultUnidade(event.query).subscribe((data: Array<Unidade>) => {
                this.unidade = data;
            });
        }
    }
    searchApresentacao(event) {
        if (event) {
            this.service
                .getResultApresentacao(event.query)
                .subscribe((data: Array<Apresentacao>) => {
                    this.apresentacao = data;
                });
        }
    }
    handleDropdown(event) {}

    constructor(
        private service: FarmaciaService,
        private fb: FormBuilder,
        private breadcrumbService: BreadcrumbService,
        private router: Router,
        private route: ActivatedRoute,
        private confirmationService: ConfirmationService,
    ) {}

    ngOnInit(): void {
        this.route.params.subscribe((params) => {
            if (params['id']) {
                this.service.find(params['id']).subscribe((medicamento) => {
                    this.form.patchValue(medicamento);
                    this.medicamento = medicamento;
                });
            }
        });

        this.medicamentoId = this.route.snapshot.params['id'];

        this.searchApresentacao(event);
        this.searchUnidade(event);
        this.searchTipoMedicamento(event);
    }

    submit() {
        this.medicamento = this.form.value;
        if (this.form.value.id != 0) {
            this.service.editar(this.medicamento).subscribe();
        } else {
            this.service.cadastrar(this.medicamento).subscribe();
        }
    }

    carregarMedicamento(id: number) {
        this.service.find(id).subscribe((medicamento) => {});
    }
}
