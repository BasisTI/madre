package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.Paciente;
import br.com.basis.madre.service.dto.PacienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {CartaoSUSMapper.class, ResponsavelMapper.class, DocumentoMapper.class, CertidaoMapper.class, OcupacaoMapper.class, ReligiaoMapper.class, MunicipioMapper.class, EtniaMapper.class, GenitoresMapper.class, NacionalidadeMapper.class, RacaMapper.class, EstadoCivilMapper.class})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {

    @Mapping(source = "responsavel.id", target = "responsavelId")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "certidao.id", target = "certidaoId")
    @Mapping(source = "ocupacao.id", target = "ocupacaoId")
    @Mapping(source = "religiao.id", target = "religiaoId")
    @Mapping(source = "naturalidade.id", target = "naturalidadeId")
    @Mapping(source = "etnia.id", target = "etniaId")
    @Mapping(source = "nacionalidade.id", target = "nacionalidadeId")
    @Mapping(source = "raca.id", target = "racaId")
    @Mapping(source = "estadoCivil.id", target = "estadoCivilId")
    @Mapping(source = "prontuario", target = "prontuario")
    PacienteDTO toDto(Paciente paciente);

    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "removeEndereco", ignore = true)
    @Mapping(source = "responsavelId", target = "responsavel")
    @Mapping(source = "documentoId", target = "documento")
    @Mapping(source = "certidaoId", target = "certidao")
    @Mapping(source = "ocupacaoId", target = "ocupacao")
    @Mapping(source = "religiaoId", target = "religiao")
    @Mapping(source = "naturalidadeId", target = "naturalidade")
    @Mapping(source = "etniaId", target = "etnia")
    @Mapping(source = "nacionalidadeId", target = "nacionalidade")
    @Mapping(source = "racaId", target = "raca")
    @Mapping(source = "estadoCivilId", target = "estadoCivil")
    @Mapping(source = "prontuario", target = "prontuario")
    Paciente toEntity(PacienteDTO pacienteDTO);

    default Paciente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
