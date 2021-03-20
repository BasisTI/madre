import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

import { CpfCnpjValidator } from '../../shared/cpf-cnpj.validator';
import { Paciente } from "./paciente.model";
import { PacienteService } from "./paciente.service";
import { PacienteValidators } from "./paciente.validators";

@Component({
    selector: 'paciente-form',
    templateUrl: './paciente-form.component.html',
})
export class PacienteFormComponent {

    constructor(private fb: FormBuilder, public pacienteService: PacienteService) {
    }

    paciente: FormGroup = this.fb.group({
        id: [null],
        nome: [null, Validators.required],
        nomeSocial: [null],
        dataDeNascimento: [null, Validators.required],
        horaDeNascimento: [null],
        email: [null, [Validators.maxLength(254), Validators.email]],
        observacao: [null],
        ocupacaoId: [null],
        religiaoId: [null],
        etniaId: [null, Validators.required],
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
            numero: ['', [Validators.required, PacienteValidators.validarNumero]],
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
                nomeDoResponsavel: [null, [PacienteValidators.nomeDoResponsavelAndObservacaoRequired]],
                telefones: this.fb.array([]),
                observacao: [null, [PacienteValidators.nomeDoResponsavelAndObservacaoRequired]],
                grauDeParentescoId: [null, [PacienteValidators.nomeDoResponsavelAndObservacaoRequired]],
            },
            { updateOn: 'blur', validators: PacienteValidators.validateResponsavel },
        ),
        documento: this.fb.group(
            {
                id: [null],
                numeroDaIdentidade: [null, [PacienteValidators.numeroDaIdentidadeRequired]],
                data: [null, [PacienteValidators.numeroDaIdentidadeRequired]],
                cpf: [null, [CpfCnpjValidator]],
                pisPasep: [null],
                cnh: [null],
                validadeDaCnh: [null, PacienteValidators.cnhRequired],
                orgaoEmissorId: [null, [PacienteValidators.numeroDaIdentidadeRequired]],
                ufId: [null, [PacienteValidators.numeroDaIdentidadeRequired]],
            },
            { updateOn: 'blur', validators: [PacienteValidators.validateDocumento, PacienteValidators.validateDocumentoCNH] },
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
            numeroDaDeclaracaoDeNascimento: [null],
        }),

    });

    public savePaciente(paciente){
        console.log("Apertou botão salvar");
    }

}
