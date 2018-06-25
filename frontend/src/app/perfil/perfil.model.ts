import { BaseEntity } from '../shared';


export class Perfil implements BaseEntity {

  constructor(
    public id?: number,
    public nomePerfil?: string,
    public dsPerfil?: string,
    // Campos da tabela Permissões
    
    //-------Unidade de Saude-------
    public pesquisarUS?:  boolean,
    public incluirUS?:    boolean,
    public alterarUS?:    boolean,
    public excluirUS?:    boolean,
    public visualizarUS?: boolean,
    //-------Usuario-------
    public pesquisarU?:  boolean,
    public incluirU?:    boolean,
    public alterarU?:    boolean,
    public excluirU?:    boolean,
    public visualizarU?: boolean,
    //-------Perfis e Permissoes-------
    public pesquisarPP?:  boolean,
    public incluirPP?:    boolean,
    public alterarPP?:    boolean,
    public excluirPP?:    boolean,
    public visualizarPP?: boolean,
    //-------Pre Cadastro-------
    public pesquisarPC?:  boolean,
    public incluirPC?:    boolean,
    public alterarPC?:    boolean,
    public excluirPC?:    boolean,
    public visualizarPC?: boolean,
    //-------Triagem-------
    public pesquisarT?:  boolean,
    public incluirT?:    boolean,
    public alterarT?:    boolean,
    public excluirT?:    boolean,
    public visualizarT?: boolean,
    //-------Paciente-------
    public pesquisarP?:  boolean,
    public incluirP?:    boolean,
    public alterarP?:    boolean,
    public excluirP?:    boolean,
    public visualizarP?: boolean,

  ) { }
}

