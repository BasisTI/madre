import { Certidao } from './models/certidao';
import { UF } from './../../models/dropdowns/types/uf';
import { OrgaoEmissor } from './../../models/dropdowns/types/orgao-emissor';
import { CartaoSUS } from './models/cartaoSUS';
import { DadosPessoaisComponent } from './dados-pessoais/dados-pessoais.component';
import { Paciente } from './models/paciente';
import { FormulaCadastroService } from './formula-cadastro.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { BreadcrumbService } from 'src/app/breadcrumb/breadcrumb.service';
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
    formularioDeCadastro = this.fb.group({});
    dadosPessoais = this.fb.group({
        nome: ['', Validators.required],
        nomeSocial: [''],
        sexo: [null, Validators.required],
        raca: [null, Validators.required],
        etnia: [null, Validators.required],
        estadoCivil: [null, Validators.required],
        prontuarioDaMae: [''],
        nomeDaMae: ['', Validators.required],
        nomeDoPai: ['', Validators.required],
        dataDeNascimento: ['', Validators.required],
        horaDoNascimento: [''],
        nacionalidade: ['', Validators.required],
        naturalidade: ['', Validators.required],
        grauDeInstrucao: ['', Validators.required],
        ocupacao: [null],
        religiao: [null],
        email: ['', Validators.maxLength(254)],
    });

    telefones = this.fb.array([]);

    enderecos = this.fb.array([]);

    responsavel = this.fb.group(
        {
            nomeDoResponsavel: ['', [this.customRequired]],
            grauDeParentesco: [null, [this.customRequired]],
            ddd: ['', [this.customRequired]],
            telefone: ['', [this.customRequired]],
            observacao: ['', [this.customRequired]],
        },
        { updateOn: 'blur', validators: this.validateGroup },
    );

    certidao = this.fb.group({
        registroDeNascimento: [''],
        tipoCertidao: [''],
        nomeDoCartorio: [''],
        livro: [''],
        folhas: [''],
        termo: [''],
        dataDeEmissao: [''],
        numeroDaDN: [''],
    });

    documentos = this.fb.group(
        {
            numeroIdentidade: ['', [this.customRequired1]],
            orgaoEmissor: ['', [this.customRequired1]],
            uf: ['', [this.customRequired1]],
            dataDeEmissao: ['', [this.customRequired1]],
            cpf: [''],
            pisPasep: [''],
            cnh: [''],
            validadeCNH: ['', this.customRequiredCNH1],
        },
        { updateOn: 'blur', validators: [this.validateGroup1, this.validateGroupCNH1] },
    );

    cartaoSUS = this.fb.group({
        numero: ['', [Validators.required, this.validarNumero]],
        justificativa: [''],
        motivoCadastro: [''],
        docReferencia: [''],
        cartaoNacional: ['', [this.validarNumero]],
        dataDeEntrada: [''],
        dataDeNaturalizacao: [''],
        portaria: [''],
    });

    observacao: [''];

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

    valid(): boolean {
        return (
            this.dadosPessoais.valid &&
            this.telefones.valid &&
            this.enderecos.valid &&
            this.responsavel.valid &&
            this.documentos.valid &&
            this.cartaoSUS.valid &&
            this.certidao.valid
        );
    }

    cadastrar() {
        let dp = this.dadosPessoais.value;
        let telefonesCadastro = this.telefones.value;
        let telefonesCad: Telefone[] = [];
        telefonesCadastro.forEach((element) => {
            telefonesCad.push(new Telefone());
        });
        let enderecoCadastro = this.enderecos.value;
        let enderecosCad: Endereco[] = [];
        enderecoCadastro.forEach((element) => {
            enderecosCad.push(new Endereco());
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
            documento: {
                numeroDaIdentidade: doc.numeroIdentidade,
                data: doc.dataDeEmissao,
                cpf: doc.cpf,
                pisPasep: doc.pisPasep,
                cnh: doc.cnh,
                validadeDaCnh: doc.validadeCNH,
                orgaoEmissorId: doc.orgaoEmissor ? doc.orgaoEmissor.id : null,
                ufId: doc.uf ? doc.uf.id : null,
            },
            responsavel: {
                nomeDoResponsavel: resp.nomeDoResponsavel,
                grauDeParentescoId: resp.grauDeParentesco ? resp.grauDeParentesco.id : null,
                observacao: resp.observacao,
                telefone: {
                    ddd: resp.ddd,
                    numero: resp.telefone,
                },
            },
            certidao: {
                registroDeNascimento: cert.registroDeNascimento,
                tipoDaCertidao: cert.tipoCertidao,
                nomeDoCartorio: cert.nomeDoCartorio,
                livro: cert.livro,
                folhas: cert.folhas,
                termo: cert.termo,
                dataDeEmissao: cert.dataDeEmissao,
                numeroDaDeclaracaoDeNascimento: cert.numeroDaDN,
            },
            cartaoSUS: {
                numero: sus.numero,
                justificativaId: sus.justificativa ? sus.justificativa.id : null,
                motivoDoCadastroId: sus.motivoCadastro ? sus.motivoCadastro.id : null,
                documentoDeReferencia: sus.docReferencia,
                cartaoNacionalSaudeMae: sus.cartaoNacional,
                dataDeEntradaNoBrasil: sus.dataDeEntrada,
                dataDeNaturalizacao: sus.dataDeNaturalizacao,
                portaria: sus.portaria,
            },
            enderecos: enderecosCad,
            telefones: telefonesCad,
        };
        // new Paciente(
        //     null,
        //     dp.nome,
        //     dp.nomeSocial,
        //     dp.dataDeNascimento,
        //     dp.horaDoNascimento,
        //     dp.email,
        //     null,
        //     dp.grauDeInstrucao,
        //     dp.sexo,
        //     telefonesc,
        //     enderecos,
        //     sus,
        //     null,
        //     null, //Certidao
        //     dp.ocupacao.id,
        //     dp.religiao.id,
        //     dp.naturalidade.id,
        //     dp.etnia.id,
        //     null,
        //     dp.nacionalidade.id,
        //     dp.raca.id,
        //     dp.estadoCivil.id,
        // );
        if (resp.nomeDoResponsavel) {
            paciente.responsavel = new Responsavel(
                null,
                resp.nomeDoResponsavel,
                new Telefone(null, resp.ddd, resp.telefone),
                resp.grauDeParentesco.id,
                resp.observacao,
            );
        }

        if (doc.cpf) {
            paciente.documento = new Documento(
                null,
                doc.numeroIdentidade,
                doc.dataDeEmissao,
                doc.cpf,
                doc.pisPasep,
                doc.cnh,
                doc.validadeCNH,
                null,
                doc.orgaoEmissor,
                doc.uf,
            );
        }

        if (cert.registroDeNascimento) {
            paciente.certidao = new Certidao(
                null,
                cert.registroDeNascimento,
                cert.tipoCertidao,
                cert.nomeDoCartorio,
                cert.livro,
                cert.folhas,
                cert.termo,
                cert.dataDeEmissao,
                cert.numeroDaDN,
            );
        }

        console.log(paciente);
        //if (this.formularioDeCadastro.valid) {
        this.formularioCadastroService.cadastrarPaciente(paciente).subscribe();
        //}
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
}
