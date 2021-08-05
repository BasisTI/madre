import { DatatableComponent } from '@nuvem/primeng-components';
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { DatatableClickEvent } from '@nuvem/primeng-components';

import { CpfCnpjValidator } from '../../shared/cpf-cnpj.validator';
import { Paciente } from "./paciente.model";
import { PacienteService } from "./paciente.service";
import { PacienteValidators } from "./paciente.validators";
import { MenuItem } from 'primeng/api';
import {TabMenuModule} from 'primeng/tabmenu';


@Component({
    selector: 'paciente-form',
    templateUrl: './paciente-form.component.html',
    styleUrls: ['./paciente-form.component.css']
})
export class PacienteFormComponent  implements OnInit{

    paciente: FormGroup;
    items: MenuItem[];
    activeItem: MenuItem;

    constructor(private fb: FormBuilder, private service: PacienteService) {
    }

    @ViewChild('datatable', { static: true }) datatable: DatatableComponent;

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
            ufId: [null],
            naturalidadeId: [null],
            nacionalidadeId: [null, Validators.required],
            racaId: [null, Validators.required],
            estadoCivilId: [null, Validators.required],
            grauDeInstrucao: [null, Validators.required],
            sexo: [null, Validators.required],
            telefones: [[]],
            enderecos: [[]],
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
                    telefones: [[]],
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
        this.items = [
            {label: 'Dados Pessoais'},
            {label: 'Contatos do Paciente'},
            {label: 'Endereço'},
            {label: 'Responsável'},
            {label: 'Cartão SUS'}
        ];
        this.activeItem = this.items[0];
    }

    imprimirRelatorioPaciente(id){
        this.service.geraRelatorioPaciente(id);
    }

}