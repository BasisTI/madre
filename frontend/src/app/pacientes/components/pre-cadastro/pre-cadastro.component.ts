import { Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-pre-cadastro',
    templateUrl: './pre-cadastro.component.html',
    styleUrls: ['./pre-cadastro.component.css'],
})
export class PreCadastroComponent implements OnInit, OnDestroy {
    preCadastro: any;

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {
        this.preCadastro = this.fb.group({
            nomeDoPaciente: ['', Validators.required],
            nomeSocial: [null],
            nomeDaMae: [null, Validators.required],
            dataDeNascimento: [null, Validators.required],
            cartaoSus: [''],
            status: [''],
        });
    }

    ngOnInit(): void {}

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
    preCadastrar() {
        const preCadastro = this.preCadastro.value;
    }
}
