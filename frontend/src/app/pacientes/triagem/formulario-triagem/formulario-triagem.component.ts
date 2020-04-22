import { BreadcrumbService } from './../../../breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ptBR } from 'src/app/shared/calendar.pt-br.locale';
import { CalendarModule } from 'primeng/calendar';
import { now } from 'moment';

@Component({
    selector: 'app-formulario-triagem',
    templateUrl: './formulario-triagem.component.html',
    styleUrls: ['./formulario-triagem.component.css'],
})
export class FormularioTriagemComponent implements OnInit, OnDestroy {
    triagemComponent: FormGroup = this.fb.group({
        nomeDoPaciente: ['', Validators.required],
        pressaoArterial: [''],
        frequenciaCardiaca: [''],
        temperatura: [''],
        peso: [''],
        sinaisSintomas: [''],
        horaDoAtendimento: [''],
        dataDoAtendimento: [''],
        idade: [''],
        descricaoQueixa: ['', Validators.required],
        vitimaDeAcidente: [''],
        removidoDeAmbulancia: [''],
        observacao: [''],
    });

    @Input() formularioTriagem: FormGroup;
    localizacao = ptBR;
    dataLimite = new Date();
    anosDisponiveis = `1900:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    idade = '';
    uf = '';

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem', routerLink: 'triagem' },
            { label: 'Formulário' },
        ]);

        this.dataHora();
    }

    dataHora() {
        var data = new Date().toLocaleString();
        console.log('Data e hora: ' + data);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
