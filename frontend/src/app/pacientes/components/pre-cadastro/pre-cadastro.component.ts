import { Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { FormGroup } from '@angular/forms';

@Component({
    selector: 'app-pre-cadastro',
    templateUrl: './pre-cadastro.component.html',
    styleUrls: ['./pre-cadastro.component.css'],
})
export class PreCadastroComponent implements OnInit, OnDestroy {
    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}
    preCadastro = this.fb.group({
        nomeDoPaciente: ['', Validators.required],
        nomeSocial: ['', Validators.required],
        nomeDaMae: ['', Validators.required],
        dataDeNascimento: ['', Validators.required],
        cartaoSus: [''],
        status: [''],
    });
    @Input() formularioTriagem: FormGroup;
    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `1900:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

    ngOnInit(): void {}

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
