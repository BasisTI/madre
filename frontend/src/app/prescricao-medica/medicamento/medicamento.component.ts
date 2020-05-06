import { PrescricaoMedicaService } from './../prescricao-medica.service';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-medicamento',
    templateUrl: './medicamento.component.html',
    styleUrls: ['./medicamento.component.css']
})
export class MedicamentoComponent implements OnInit, OnDestroy {

    paciente = { nome: '' };

    prescricaoMedicamento = this.fb.group({
        medicamento: ['', Validators.required],
        dose: ['', Validators.required],
        unidadeDose: [''],
        frequencia: [''],
        todasVias: [''],
        bombaInfusao: [''],
        unidadeInfusao: [''],
        velocidadeInfusao: [''],
        tempoInfusao: [''],
        unidadeTempo: [''],
        inicioAdministracao: [''],
        condicaoNecessaria: [''],
        viasAdministracao: [''],
        diluente: [''],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private route: ActivatedRoute,
        private prescricaoMedicaService: PrescricaoMedicaService,
        private fb: FormBuilder
    ) { }


    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Medicamento' }
        ]);

        const codigoPaciente = this.route.snapshot.params['id'];

    }

    carregarPaciente(id: number) {
        this.prescricaoMedicaService.buscarIdPaciente(id)
            .subscribe(paciente => {
                this.paciente = paciente;
            });
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
