package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Internacao;
import br.com.basis.madre.service.dto.InternacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EspecialidadeMapper.class, CRMMapper.class,
    HospitalMapper.class, OrigemDaInternacaoMapper.class, ConvenioDeSaudeMapper.class,
    PlanoDeSaudeMapper.class, ProcedimentoMapper.class, ProcedenciaMapper.class,
    ModalidadeAssistencialMapper.class, LocalDeAtendimentoMapper.class})
public interface InternacaoMapper extends EntityMapper<InternacaoDTO, Internacao> {

    @Mapping(source = "especialidade.id", target = "especialidadeId")
    @Mapping(source = "crm.id", target = "crmId")
    @Mapping(source = "hospitalDeOrigem.id", target = "hospitalDeOrigemId")
    @Mapping(source = "origem.id", target = "origemId")
    @Mapping(source = "convenio.id", target = "convenioId")
    @Mapping(source = "plano.id", target = "planoId")
    @Mapping(source = "procedimento.id", target = "procedimentoId")
    @Mapping(source = "procedencia.id", target = "procedenciaId")
    @Mapping(source = "modalidadeAssistencial.id", target = "modalidadeAssistencialId")
    @Mapping(source = "localDeAtendimento.id", target = "localDeAtendimentoId")
    InternacaoDTO toDto(Internacao internacao);

    @Mapping(source = "especialidadeId", target = "especialidade")
    @Mapping(source = "crmId", target = "crm")
    @Mapping(source = "hospitalDeOrigemId", target = "hospitalDeOrigem")
    @Mapping(source = "origemId", target = "origem")
    @Mapping(source = "convenioId", target = "convenio")
    @Mapping(source = "planoId", target = "plano")
    @Mapping(source = "procedimentoId", target = "procedimento")
    @Mapping(source = "procedenciaId", target = "procedencia")
    @Mapping(source = "modalidadeAssistencialId", target = "modalidadeAssistencial")
    @Mapping(source = "localDeAtendimentoId", target = "localDeAtendimento")
    Internacao toEntity(InternacaoDTO internacaoDTO);

    default Internacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        Internacao internacao = new Internacao();
        internacao.setId(id);
        return internacao;
    }

}
