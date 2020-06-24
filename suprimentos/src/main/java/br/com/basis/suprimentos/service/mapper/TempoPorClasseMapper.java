package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.TempoPorClasse;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AlmoxarifadoMapper.class})
public interface TempoPorClasseMapper extends EntityMapper<TempoPorClasseDTO, TempoPorClasse> {
    @Mapping(source = "almoxarifado.id", target = "almoxarifadoId")
    TempoPorClasseDTO toDto(TempoPorClasse tempoPorClasse);

    @Mapping(source = "almoxarifadoId", target = "almoxarifado")
    TempoPorClasse toEntity(TempoPorClasseDTO tempoPorClasseDTO);

    default TempoPorClasse fromId(Long id) {
        if (id == null) {
            return null;
        }
        TempoPorClasse tempoPorClasse = new TempoPorClasse();
        tempoPorClasse.setId(id);
        return tempoPorClasse;
    }
}
