import { Observable } from 'rxjs';
import { Triagem } from './../../../models/triagem';
import { TriagemService } from '../triagem.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OnInit, OnDestroy, Component, Input, Optional } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CLASSIFICACAO_COLORS } from 'src/app/pacientes/models/radioButton/classificacao-colors';

@Component({
    selector: 'app-formulario-triagem',
    templateUrl: './formulario-triagem.component.html',
    styleUrls: ['./formulario-triagem.component.css'],
})
export class FormularioTriagemComponent implements OnInit, OnDestroy {
    @Input() formsTriagem: FormGroup;
    opcaoClassificacao = CLASSIFICACAO_COLORS;
    selectedValue: string;
    formTriagem = this.fb.group({
        classificacaoDeRisco: [''],
        paciente: ['', Validators.required],
        pressaoArterial: [''],
        frequenciaCardiaca: [''],
        temperatura: [''],
        peso: [''],
        sinaisSintomas: [''],
        dataHoraDoAtendimento: [new Date()],
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
    ngOnDestroy(): void {
        // throw new Error('Method not implemented.');
    }

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
        const tri = this.formTriagem.value;
        const triagem: Triagem = {
            classificacaoDeRisco: tri.classificacaoDeRisco,
            pressaoArterial: tri.pressaoArterial,
            frequenciaCardiaca: tri.frequenciaCardiaca,
            temperatura: tri.temperatura,
            peso: tri.peso,
            sinaisSintomas: tri.sinaisSintomas,
            dataHoraDoAtendimento: tri.dataHoraDoAtendimento,
            descricaoQueixa: tri.descricaoQueixa,
            vitimaDeAcidente: tri.vitimaDeAcidente,
            removidoDeAmbulancia: tri.removidoDeAmbulancia,
            observacao: tri.observacao,
        };

        this.triagemService.cadastrarTriagem(triagem).subscribe();
    }
}
