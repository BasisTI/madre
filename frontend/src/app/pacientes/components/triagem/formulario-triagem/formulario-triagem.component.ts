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
        nomeDoPaciente: ['', Validators.required],
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

    @Input() formularioTriagem: FormGroup;
    localizacao = ptBR;
    dataLimite = new Date();
    anosDisponiveis = `2000:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

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

        this.triagemService.cadastrarTriagem(this.formTriagem.value);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
