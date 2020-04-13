package br.com.basis.madre.service.mapper;


import br.com.basis.madre.domain.*;
import br.com.basis.madre.service.dto.PacienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {CartaoSUSMapper.class, ResponsavelMapper.class, DocumentoMapper.class, CertidaoMapper.class, OcupacaoMapper.class, ReligiaoMapper.class, EtniaMapper.class, GenitoresMapper.class, NacionalidadeMapper.class, Municipio.class, RacaMapper.class, EstadoCivilMapper.class})
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {

    @Mapping(source = "cartaoSUS.id", target = "cartaoSUSId")
    @Mapping(source = "responsavel.id", target = "responsavelId")
    @Mapping(source = "documento.id", target = "documentoId")
    @Mapping(source = "certidao.id", target = "certidaoId")
    @Mapping(source = "ocupacao.id", target = "ocupacaoId")
    @Mapping(source = "religiao.id", target = "religiaoId")
    @Mapping(source = "etnia.id", target = "etniaId")
    @Mapping(source = "genitores.id", target = "genitoresId")
    @Mapping(source = "nacionalidade.id", target = "nacionalidadeId")
    @Mapping(source = "raca.id", target = "racaId")
    @Mapping(source = "estadoCivil.id", target = "estadoCivilId")
    @Mapping(source = "naturalidade.id", target = "municipioId")
    PacienteDTO toDto(Paciente paciente);

    @Mapping(source = "cartaoSUSId", target = "cartaoSUS")
    @Mapping(target = "telefones", ignore = true)
    @Mapping(target = "removeTelefone", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "removeEndereco", ignore = true)
    @Mapping(source = "responsavelId", target = "responsavel")
    @Mapping(source = "documentoId", target = "documento")
    @Mapping(source = "certidaoId", target = "certidao")
    @Mapping(source = "ocupacaoId", target = "ocupacao")
    @Mapping(source = "religiaoId", target = "religiao")
    @Mapping(source = "etniaId", target = "etnia")
    @Mapping(source = "genitoresId", target = "genitores")
    @Mapping(source = "nacionalidadeId", target = "nacionalidade")
    @Mapping(source = "naturalidadeId", target = "naturalidade")
    @Mapping(source = "racaId", target = "raca")
    @Mapping(source = "estadoCivilId", target = "estadoCivil")
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
