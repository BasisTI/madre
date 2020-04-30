import { ICID } from './../../solicitacao-de-internacao/cid.service';
import { CLASSIFICACAO_RISCO } from './../../../models/radioButton/classificacao-de-risco';
import { values } from 'micro-dash';
import { types } from 'util';
import { ClassificacaoDeRiscoService } from './classificacao-de-risco/classificacao-de-risco.service';
import { TriagemService } from './../triagem.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OnInit, OnDestroy, Component, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ptBR } from 'src/app/shared/calendar.pt-br.locale';

@Component({
    selector: 'app-formulario-triagem',
    templateUrl: './formulario-triagem.component.html',
    styleUrls: ['./formulario-triagem.component.css'],
})
export class FormularioTriagemComponent implements OnInit, OnDestroy {
    formTriagem = this.fb.group({
        classificacaoDeRisco: ['EMERGENCIA'],
        // tslint:disable-next-line: comment-format
        //CLASSIFICACAO_RISCO
        // paciente: ['id', Validators.required],
        pressaoArterial: [''],
        frequenciaCardiaca: [''],
        temperatura: [''],
        peso: [''],
        sinaisSintomas: [''],
        dataHoraDoAtendimento: [
            `${new Date().getDay()}/${new Date().getMonth()}/${new Date().getFullYear()} -
${new Date().getHours()}:${new Date().getUTCMinutes()}`,
        ],
        idade: [''],
        descricaoQueixa: ['', Validators.required],
        vitimaDeAcidente: [''],
        removidoDeAmbulancia: [''],
        observacao: [''],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private triagemService: TriagemService,
    ) {}

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem', routerLink: 'triagem' },
            { label: 'Formulário' },
        ]);

        //     this.dataHora();
        // }

        // dataHora() {
        //     const dataHora = `${new Date().getDay()}/${new Date().getMonth()}/${new Date().getFullYear()},
        //     ${new Date().getHours()}:${new Date().getUTCMinutes()}`;
    }

    cadastrar(form: FormBuilder) {
        console.log(form);

        this.triagemService.cadastrarTriagem(this.formTriagem.value).subscribe();
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
