import { Certidao } from './models/certidao';
import { UF } from './../../models/dropdowns/types/uf';
import { OrgaoEmissor } from './../../models/dropdowns/types/orgao-emissor';
import { CartaoSUS } from './models/cartaoSUS';
import { DadosPessoaisComponent } from './dados-pessoais/dados-pessoais.component';
import { Paciente } from './models/paciente';
import { FormulaCadastroService } from './formula-cadastro.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { FormBuilder, Validators, FormGroup, AbstractControl, FormArray } from '@angular/forms';
import { Responsavel } from './models/responsavel';
import { Telefone } from './models/telefone';
import { Endereco } from './models/endereco';
import { Documento } from './models/documento';

@Component({
    selector: 'app-formulario-cadastro',
    templateUrl: './formulario-cadastro.component.html',
    styleUrls: ['./formulario-cadastro.component.scss'],
})
export class FormularioCadastroComponent implements OnInit, OnDestroy {
    dadosPessoais = this.fb.group({
        nome: [null, Validators.required],
        nomeSocial: [null],
        sexo: [null, Validators.required],
        raca: [null, Validators.required],
        etnia: [null, Validators.required],
        estadoCivil: [null, Validators.required],
        prontuarioDaMae: [null],
        nomeDaMae: [null, Validators.required],
        nomeDoPai: [null, Validators.required],
        dataDeNascimento: [null, Validators.required],
        horaDoNascimento: [null],
        nacionalidade: [null, Validators.required],
        naturalidade: [null],
        grauDeInstrucao: [null, Validators.required],
        ocupacao: [null],
        religiao: [null],
        email: [null, Validators.maxLength(254)],
        observacao: [null],
    });

    telefones = this.fb.array([]);

    enderecos = this.fb.array([]);

    responsavel = this.fb.group(
        {
            nomeDoResponsavel: [null, [this.customRequired]],
            grauDeParentesco: [null, [this.customRequired]],
            ddd: [null, [this.customRequired]],
            telefone: [null, [this.customRequired]],
            observacao: [null, [this.customRequired]],
        },
        { updateOn: 'blur', validators: this.validateGroup },
    );

    certidao = this.fb.group({
        registroDeNascimento: [null],
        tipoCertidao: [null],
        nomeDoCartorio: [null],
        livro: [null],
        folhas: [null],
        termo: [null],
        dataDeEmissao: [null],
        numeroDaDN: [null],
    });

    documentos = this.fb.group(
        {
            numeroIdentidade: [null, [this.customRequired1]],
            orgaoEmissor: [null, [this.customRequired1]],
            uf: [null, [this.customRequired1]],
            dataDeEmissao: [null, [this.customRequired1]],
            cpf: [null],
            pisPasep: [null],
            cnh: [null],
            validadeCNH: [null, this.customRequiredCNH1],
        },
        { updateOn: 'blur', validators: [this.validateGroup1, this.validateGroupCNH1] },
    );

    cartaoSUS = this.fb.group({
        numero: ['', [Validators.required, this.validarNumero]],
        justificativa: [null],
        motivoCadastro: [null],
        docReferencia: [null],
        cartaoNacional: ['', [this.validarNumero]],
        dataDeEntrada: [null],
        dataDeNaturalizacao: [null],
        portaria: [null],
    });

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private formularioCadastroService: FormulaCadastroService,
    ) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Cadastro de Paciente' },
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

    customRequired(control: AbstractControl): { [key: string]: boolean } | null {
        if (
            control.parent &&
            (control.parent.get('nomeDoResponsavel').value ||
                control.parent.get('observacao').value) &&
            !control.value
        ) {
            return { required: true };
        }

        return null;
    }

    validateGroup(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('nomeDoResponsavel').value || group.get('observacao').value) {
            group.markAsDirty();
            return { required: true };
        }

        return null;
    }

    customRequired1(control: AbstractControl): { [key: string]: boolean } | null {
        if (control.parent && control.parent.get('numeroIdentidade').value && !control.value) {
            return { required: true };
        } else {
            return null;
        }
    }

    validateGroup1(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('numeroIdentidade').value) {
            group.markAsDirty();
            return { required: true };
        } else {
            return null;
        }
    }

    validateGroupCNH1(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('cnh').value) {
            group.markAsDirty();
            return { required: true };
        }

        return null;
    }

    customRequiredCNH1(control: AbstractControl): { [key: string]: boolean } | null {
        if (control.parent && control.parent.get('cnh').value && !control.value) {
            return { required: true };
        }

        return null;
    }

    /**
     * fé em deus
     */
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
        return this.dadosPessoais.valid && this.cartaoSUS.valid;
    }

    cadastrar() {
        let dp = this.dadosPessoais.value;
        let telefonesCadastro = this.telefones.value;
        let telefonesCad: Telefone[] = [];
        telefonesCadastro.forEach((element) => {
            telefonesCad.push(
                new Telefone(
                    null,
                    element.ddd,
                    element.numero,
                    element.tipo ? element.tipo : null,
                    element.observacao ? element.observacao : null,
                    element.pacienteId,
                ),
            );
        });
        let enderecoCadastro = this.enderecos.value;
        let enderecosCad: Endereco[] = [];
        enderecoCadastro.forEach((element) => {
            enderecosCad.push(
                new Endereco(
                    null,
                    element.cep,
                    element.logradouro,
                    element.numero,
                    element.complemento ? element.complemento : null,
                    element.bairro,
                    element.correspondencia ? element.correspondencia : null,
                    element.tipoDoEndereco ? element.tipoDoEndereco : null,
                    element.municioId ? element.municioId : null,
                    element.pacienteId ? element.pacienteId : null,
                ),
            );
        });
        let sus = this.cartaoSUS.value;
        let resp = this.responsavel.value;
        let doc = this.documentos.value;
        let cert = this.certidao.value;

        let paciente: Paciente = {
            nome: dp.nome,
            nomeSocial: dp.nomeSocail,
            dataDeNascimento: dp.dataDeNascimento,
            email: dp.email,
            horaDeNascimento: dp.horaDoNascimento,
            ocupacaoId: dp.ocupacao ? dp.ocupacao.id : null,
            religiaoId: dp.religiao ? dp.religiao.id : null,
            etniaId: dp.etnia ? dp.religiao.id : null,
            naturalidadeId: dp.naturalidade ? dp.naturalidade.id : null,
            nacionalidadeId: dp.nacionalidade ? dp.nacionalidade.id : null,
            racaId: dp.raca ? dp.raca.id : null,
            estadoCivilId: dp.estadoCivil ? dp.estadoCivil.id : null,
            sexo: dp.sexo,
            grauDeInstrucao: dp.grauDeInstrucao,
            observacao: dp.observacao,
            enderecos: enderecosCad,
            telefones: telefonesCad,
        };

        if (dp.prontuarioDaMae) {
            paciente.genitores = {
                prontuarioDaMae: dp.prontuarioDaMae,
                nomeDaMae: dp.nomeDaMae,
                nomeDoPai: dp.nomeDoPai,
            };
        }

        if (resp.nomeDoResponsavel) {
            paciente.responsavel = {
                nomeDoResponsavel: resp.nomeDoResponsavel,
                grauDeParentescoId: resp.grauDeParentesco ? resp.grauDeParentesco.id : null,
                observacao: resp.observacao,
                telefones: [
                    {
                        ddd: resp.ddd,
                        numero: resp.telefone,
                    },
                ],
            };
        }

        if (doc.cpf) {
            paciente.documento = {
                numeroDaIdentidade: doc.numeroIdentidade,
                data: doc.dataDeEmissao,
                cpf: doc.cpf,
                pisPasep: doc.pisPasep,
                cnh: doc.cnh,
                validadeDaCnh: doc.validadeCNH,
                orgaoEmissorId: doc.orgaoEmissor ? doc.orgaoEmissor.id : null,
                ufId: doc.uf ? doc.uf.id : null,
            };
        }

        if (cert.registroDeNascimento) {
            paciente.certidao = {
                registroDeNascimento: cert.registroDeNascimento,
                tipoDaCertidao: cert.tipoCertidao,
                nomeDoCartorio: cert.nomeDoCartorio,
                livro: cert.livro,
                folhas: cert.folhas,
                termo: cert.termo,
                dataDeEmissao: cert.dataDeEmissao,
                numeroDaDeclaracaoDeNascimento: cert.numeroDaDN,
            };
        }

        if (sus.numero) {
            paciente.cartaoSUS = {
                numero: sus.numero,
                justificativaId: sus.justificativa ? sus.justificativa.id : null,
                motivoDoCadastroId: sus.motivoCadastro ? sus.motivoCadastro.id : null,
                documentoDeReferencia: sus.docReferencia,
                cartaoNacionalSaudeMae: sus.cartaoNacional,
                dataDeEntradaNoBrasil: sus.dataDeEntrada,
                dataDeNaturalizacao: sus.dataDeNaturalizacao,
                portaria: sus.portaria,
            };
        }

        console.log(paciente);
        //if (this.formularioDeCadastro.valid) {
        this.formularioCadastroService.cadastrarPaciente(paciente).subscribe();
        //}
    }
}
