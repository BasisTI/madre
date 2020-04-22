import { BreadcrumbService } from '../../../../breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

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

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem', routerLink: 'triagem' },
            { label: 'Formulário' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }
}
