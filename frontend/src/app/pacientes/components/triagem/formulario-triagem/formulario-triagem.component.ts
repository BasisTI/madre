import { PacienteResumo } from './../../../models/paciente-resumo';
import { ActivatedRoute } from '@angular/router';
import { TriagemModel } from '../../../models/triagem-model';
import { TriagemService } from '../triagem.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { OnInit, OnDestroy, Component, Input } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl } from '@angular/forms';
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
    triagem: TriagemModel;
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
    toasty: any;
    errorHandler: any;

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private triagemService: TriagemService,
        private route: ActivatedRoute,
    ) {}

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem', routerLink: 'pacientes/triagem' },
            { label: 'Formulário' },
        ]);

        const triagemId = this.route.snapshot.params['id'];

        if (triagemId) {
            this.carregarTriagem(triagemId);
        }
    }
    get editando() {
        return Boolean(this.triagem.id);
    }

    //     this.dataHora();
    // }

    // dataHora() {
    //     const dataHora = `${new Date().getDay()}/${new Date().getMonth()}/${new Date().getFullYear()},
    //     ${new Date().getHours()}:${new Date().getUTCMinutes()}`;

    cadastrar(form: FormBuilder) {
        const tri = this.formTriagem.value;
        const triagem: TriagemModel = {
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

    carregarTriagem(id: number) {
        this.triagemService.buscarTriagemId(id).subscribe((triagem) => {
            console.log(triagem);

            this.formTriagem.patchValue(triagem);
            this.formTriagem.patchValue({ paciente: triagem.paciente.nome });
        });
    }
    // salvar(form: FormControl) {
    //     if (this.editando) {
    //       this.atualizarTriagem(form);
    //     } else {
    //       this.adicionarTriagem(form);
    //     }
    // }
    // adicionarTriagem(form: FormControl) {
    //     this.triagemService.adicionar(this.triagem)
    //       .then(() => {
    //         this.toasty.success('Lançamento adicionado com sucesso!');

    //         });
    //     }

    //         atualizarLancamento(form: FormControl) {
    //             this.triagemService.atualizar(this.triagem)
    //             .then(triagem => {
    //                 this.triagem = TriagemNova;
    //             });
    //         }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }
}
