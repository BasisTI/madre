import { Unidade } from './../dispensacao/unidade';
import { FormBuilder, Validators } from '@angular/forms';
import { TipoMedicamento } from './tipoMedicamento';
import { FarmaciaService } from './../farmacia.service';
import { Component, OnInit } from '@angular/core';
import { Apresentacao } from './apresentacao';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Medicamentos } from '../medicamentos/Medicamento';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicamentoComponent } from '@prescricao-medica/medicamento/medicamento.component';
import { ConfirmationService } from 'primeng';

@Component({
    selector: 'app-cadastro-medicamento',
    templateUrl: './cadastro-medicamento.component.html',
    styleUrls: ['./cadastro-medicamento.component.css'],
})
export class CadastroMedicamentoComponent implements OnInit {
    medicamento: Medicamentos = new Medicamentos(); 
    form = this.fb.group({
        medicamento: ['', Validators.required],
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

    submit() {
        console.log(this.form.value);
        this.medicamento = this.form.value;
        this.service.cadastrar(this.medicamento).subscribe();
                
    }

    searchTipoMedicamento(event) {
        this.service
            .getResultTipoMedicamento(event.query)
            .subscribe((data: Array<TipoMedicamento>) => {
                this.results = data;
            });
    }
    searchUnidade(event) {
        this.service.getResultUnidade(event.query).subscribe((data: Array<Unidade>) => {
            this.unidade = data;
        });
    }
    searchApresentacao(event) {
        this.service.getResultApresentacao(event.query).subscribe((data: Array<Apresentacao>) => {
            this.apresentacao = data;
        });
    }
    handleDropdown(event) {}

    constructor(
        private service: FarmaciaService, 
        private fb: FormBuilder,
        private breadcrumbService: BreadcrumbService,
        private router: Router,
        private route: ActivatedRoute,
        private confirmationService: ConfirmationService) {}

    ngOnInit(): void {
        
        this.route.params.subscribe(params => {
            if (params['id']) {
                this.service.find(params['id']).subscribe(medicamento => this.medicamento = medicamento);
                this.medicamento.id = params['id'];
            }
        })

        this.medicamentoId = this.route.snapshot.params['id'];

        if(this.medicamentoId) {
            this.carregarMedicamento(this.medicamentoId);
        }
        
        this.breadcrumbService.setItems([
            { label: 'Medicamentos', routerLink: 'medicamentos' },
            { label: 'Cadastrar Medicamentos', routerLink: 'cadastrar-medicamento'},
        ]);
        
        this.searchApresentacao(event);
        this.searchUnidade(event);
        this.searchTipoMedicamento(event);
        
    }

    carregarMedicamento(id: number) {
        this.service.find(id).subscribe((medicamento) => {
            this.form.patchValue(medicamento);
            this.form.patchValue({ medicamento: medicamento.nome });
        });
    }

}
