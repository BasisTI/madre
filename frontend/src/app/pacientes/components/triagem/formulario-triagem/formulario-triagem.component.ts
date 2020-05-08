import { Observable } from 'rxjs';
import { Triagem } from './../../../models/triagem';
import { values } from 'micro-dash';
import { TriagemService } from '../triagem.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OnInit, OnDestroy, Component, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-de-risco';

@Component({
    selector: 'app-formulario-triagem',
    templateUrl: './formulario-triagem.component.html',
    styleUrls: ['./formulario-triagem.component.css'],
})
export class FormularioTriagemComponent implements OnInit, OnDestroy {
    triagens: any[];

    searchUrl = 'pacientes/api/triagens/paciente/{id}';

    formTriagem = this.fb.group({
        classificacaoDeRisco: CLASSIFICACAO_RISCO,
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
        // tslint:disable-next-line: comment-format
        //let
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

        console.log(this.formTriagem.value.classificacaoDeRisco);

        this.triagemService.cadastrarTriagem(triagem).subscribe();
    }
    listarTriagens(id: number) {
        this.triagemService.listarTriagem(id).subscribe((triagens) => {
            this.triagens = triagens;
        });
    }
}
