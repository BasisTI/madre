package br.com.basis.madre.madreexames.service.dto;

import java.util.Set;

public class ExameCompletoDTO extends ExameDTO {

    private Set<SinonimoDTO> sinonimos;

    public Set<SinonimoDTO> getSinonimos() {
        return sinonimos;
    }

    public void setSinonimos(Set<SinonimoDTO> sinonimos) {
        this.sinonimos = sinonimos;
    }
}
