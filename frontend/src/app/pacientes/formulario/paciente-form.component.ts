import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

import { CpfCnpjValidator } from '../../shared/cpf-cnpj.validator';
import { Paciente } from "./paciente.model";
import { PacienteService } from "./paciente.service";
import { PacienteValidators } from "./paciente.validators";

@Component({
    selector: 'paciente-form',
    templateUrl: './paciente-form.component.html',
})
export class PacienteFormComponent  implements OnInit{

    paciente: FormGroup;

    constructor(private fb: FormBuilder) {
    }
    ngOnInit(): void {
        this.paciente = this.fb.group({
            id: [null],
            nome: [null, Validators.required],
            nomeSocial: [null],
            dataDeNascimento: [null, Validators.required],
            horaDeNascimento: [null],
            email: [null, Validators.email],
            observacao: [null],
            ocupacaoId: [null],
            religiaoId: [null],
            etniaId: [null],
            uf: [null],
            naturalidadeId: [null],
            nacionalidadeId: [null, Validators.required],
            racaId: [null, Validators.required],
            estadoCivilId: [null, Validators.required],
            grauDeInstrucao: [null, Validators.required],
            sexo: [null, Validators.required],
            telefones: this.fb.array([]),
            enderecos: this.fb.array([]),
            genitores: this.fb.group({
                id: [null],
                prontuarioDaMae: [null],
                nomeDaMae: [null, Validators.required],
                nomeDoPai: [null, Validators.required],
            }),
            cartaoSUS: this.fb.group({
                id: [null],
                numero: ['',[Validators.required, PacienteValidators.validarNumero]],
                documentoDeReferencia: [null],
                cartaoNacionalSaudeMae: ['', PacienteValidators.validarNumero],
                dataDeEntradaNoBrasil: [null],
                dataDeNaturalizacao: [null],
                portaria: [null],
                justificativaId: [null],
                motivoDoCadastroId: [null],
            }),
            responsavel: this.fb.group(
                {
                    id: [null],
                    nomeDoResponsavel: [null],
                    telefones: this.fb.array([]),
                    observacao: [null],
                    grauDeParentescoId: [null],
                },
                { updateOn: 'blur'},
            ),
            documento: this.fb.group(
                {
                    id: [null],
                    numeroDaIdentidade: [null],
                    data: [null],
                    cpf: [null],
                    pisPasep: [null],
                    cnh: [null],
                    validadeDaCnh: [null,],
                    orgaoEmissorId: [null],
                    ufId: [null],
                },
                { updateOn: 'blur'},
            ),
            certidao: this.fb.group({
                id: [null],
                registroDeNascimento: [null],
                tipoDaCertidao: [null],
                nomeDoCartorio: [null],
                livro: [null],
                folhas: [null],
                termo: [null],
                dataDeEmissao: [null],
                numeroDaDeclaracaoDeNascimento: [null]
            })
        });
    }
}
