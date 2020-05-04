import { CLASSIFICACAO_RISCO } from './../../../models/radioButton/classificacao-de-risco';
import { TriagemService } from './../triagem.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OnInit, OnDestroy, Component, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
    selector: 'app-formulario-triagem',
    templateUrl: './formulario-triagem.component.html',
    styleUrls: ['./formulario-triagem.component.css'],
})
export class FormularioTriagemComponent implements OnInit, OnDestroy {
    formTriagem = this.fb.group({
        classificacaoDeRisco: [CLASSIFICACAO_RISCO],
        // tslint:disable-next-line: comment-format
        //CLASSIFICACAO_RISCO
        // paciente: ['', Validators.required],
        pressaoArterial: [''],
        frequenciaCardiaca: [''],
        temperatura: [''],
        peso: [''],
        sinaisSintomas: [''],
        dataHoraDoAtendimento: [
            //             `${new Date().getDay()}/${new Date().getMonth()}/${new Date().getFullYear()} -
            // ${new Date().getHours()}:${new Date().getUTCMinutes()}`,
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
        console.log(this.formTriagem.value.classificacaoDeRisco);

        this.triagemService.cadastrarTriagem(this.formTriagem.value).subscribe();
    }
    alterar(form: FormBuilder) {
        console.log(this.formTriagem.value.classificacaoDeRisco);

        this.triagemService.alterarTriagem(this.formTriagem.value).subscribe();
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
