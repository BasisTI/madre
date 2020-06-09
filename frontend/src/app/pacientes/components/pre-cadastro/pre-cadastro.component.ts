import { PreCadastroModel } from '../../models/pre-cadastro-model';
import { Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { PreCadastroService } from './pre-cadastro.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-pre-cadastro',
    templateUrl: './pre-cadastro.component.html',
    styleUrls: ['./pre-cadastro.component.css'],
})
export class PreCadastroComponent implements OnInit, OnDestroy {
    localizacao = CALENDAR_LOCALE;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private router: Router,
        private preCadastroService: PreCadastroService,
    ) {}

    preCadastro = this.fb.group({
        nome: ['', Validators.required],
        nomeSocial: ['', Validators.required],
        nomeDaMae: ['', Validators.required],
        dataDeNascimento: ['', Validators.required],
        cartaoSus: [''],
        status: [''],
    });

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Pré-Cadastro de Paciente', routerLink: 'pacientes/pre-cadastro-paciente' },
        ]);
    }

    preCadastrar(form: FormBuilder) {
        const pre = this.preCadastro.value;
        const preCadastroPaciente: PreCadastroModel = {
            nome: pre.nome,
            nomeSocial: pre.nomeSocial,
            nomeDaMae: pre.nomeDaMae,
            dataDeNascimento: pre.dataDeNascimento,
            cartaoSus: pre.cartaoSus,
            ativo: pre.ativo,
        };
        this.preCadastroService.preCadastrarPaciente(preCadastroPaciente).subscribe();
        console.log(preCadastroPaciente);
    }
    ngOnDestroy() {
        this.breadcrumbService.reset();
    }
}
