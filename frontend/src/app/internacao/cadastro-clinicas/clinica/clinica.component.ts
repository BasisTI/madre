import { CadastroClinica } from './cadastro-clinica.service';

import { Clinica } from '@internacao/formulario-unidades/models/dropwdowns/Clinica';
import { FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-clinica',
    templateUrl: './clinica.component.html',
    styleUrls: ['./clinica.component.css'],
})
export class ClinicaComponent implements OnInit {
    clinica = this.fb.group({
        descricao: [null, Validators.required],
        capacidadeReferencial: [null, Validators.required],
        numeroSUS: ['', [this.validarNumero]],
        idadeMinimaInternacao: [null, Validators.required],
        idadeMaximaInternacao: [null, Validators.required],
        idadeMinimaAmbulatorio: [null, Validators.required],
        idadeMaximaAmbulatorio: [null, Validators.required],
    });

    constructor(private fb: FormBuilder, private clinicaService: CadastroClinica) {}

    ngOnInit(): void {}

    validarNumero(control: AbstractControl) {
        let cns = control.value;
        cns = cns.replace(/\D/g, '');

        if (cns.length !== 15) {
            return { customCns: true };
        }

        const soma =
            cns
                .split('')
                .reduce(
                    (somaParcial: number, atual: string, posicao: number) =>
                        somaParcial + parseInt(atual, 10) * (15 - posicao),
                    0,
                ) % 11;

        return soma % 11 === 0 ? null : { customCns: true };
    }

    valid(): boolean {
        return this.clinica.valid;
    }

    cadastrar() {
        let cadastrarClinica = this.clinica.value;

        let clinica: Clinica = {
            descricao: cadastrarClinica.descricao,
            capacidadeReferencial: cadastrarClinica.capacidadeReferencial,
            numeroSUS: cadastrarClinica.numeroSUS,
            idadeMinimaInternacao: cadastrarClinica.idadeMinimaInternacao,
            idadeMaximaInternacao: cadastrarClinica.idadeMaximaInternacao,
            idadeMinimaAmbulatorio: cadastrarClinica.idadeMinimaAmbulatorio,
            idadeMaximaAmbulatorio: cadastrarClinica.idadeMaximaAmbulatorio,
        };

        console.log(clinica);
        this.clinicaService.cadastrarClinica(clinica).subscribe();
    }
}
