import { ptBR } from '@shared/calendar.pt-br.locale';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { BreadcrumbService } from '@breadcrumb/breadcrumb.service';

@Component({
    selector: 'app-internacao-de-paciente',
    templateUrl: './internacao-de-paciente.component.html',
    styleUrls: ['./internacao-de-paciente.component.scss'],
})
export class InternacaoDePacienteComponent implements OnInit, OnDestroy {
    public pCalendarConfig = {
        localidade: ptBR,
        dataMinima: new Date(),
        anosDisponiveis: '1900:2100',
        formatoDeData: 'dd/mm/yy',
    };

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

    public formGroup = this.fb.group({
        // prontuario: this.fb.control({ value: '', disabled: true }, Validators.required),
        // nomeDoPaciente: this.fb.control({ value: '', disabled: true }, Validators.required),
        especialidade: ['', Validators.required],
        planoDeSaude: ['', Validators.required],
        convenioDeSaude: ['', Validators.required],
        caraterDaInternacao: ['', Validators.required],
        origemDaInternacao: ['', Validators.required],
        hospitalDeOrigem: ['', Validators.required],
        procedencia: ['', Validators.required],
        localDeAtendimento: ['', Validators.required],
        modalidadeAssistencial: ['', Validators.required],
        dataDaInternacao: ['', Validators.required],
        diferencaDeClasse: [false, Validators.required],
        solicitarProntuario: [false, Validators.required],
        justificativa: ['', Validators.required],
        crm: ['', Validators.required],
    });

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Internação',
            },
            {
                label: 'Internar Paciente',
                routerLink: 'internacao-de-paciente',
            },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    internarPaciente() {
        console.log(this.formGroup.value);
    }
}
