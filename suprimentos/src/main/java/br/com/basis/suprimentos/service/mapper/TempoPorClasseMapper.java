package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.TempoPorClasse;
import br.com.basis.suprimentos.service.dto.TempoPorClasseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TempoPorClasseMapper extends EntityMapper<TempoPorClasseDTO, TempoPorClasse> {

    TempoPorClasseDTO toDto(TempoPorClasse tempoPorClasse);

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
