import { InternacaoDePacienteService } from '@internacao/services/internacao-de-paciente.service';
import { Component, OnInit } from '@angular/core';
import { Internacao } from '@internacao/models/internacao';
import { Router } from '@angular/router';
import { Medicamentos } from 'src/app/farmacia/farmacia/medicamentos/Medicamento';

@Component({
    selector: 'app-formulario-transferir-paciente',
    templateUrl: './formulario-transferir-paciente.component.html',
    styleUrls: ['./formulario-transferir-paciente.component.css'],
})
export class FormularioTransferirPacienteComponent implements OnInit {
    internacoes: Internacao[];

    constructor(
        private internacaoDePacienteService: InternacaoDePacienteService,
        private router: Router,
    ) {}

    listarTransferiPaciente() {
        this.internacaoDePacienteService.listarGradeTransferiPaciente().subscribe((response) => {
            this.internacoes = response;
        });
    }

    abrirEditar(tipoMedicamentoSelecionada: Medicamentos) {
        this.router.navigate(['/medicamentos', tipoMedicamentoSelecionada.id, 'edit']);
    }

    ngOnInit(): void {
        this.listarTransferiPaciente();
    }
}
