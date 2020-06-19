package br.com.basis.madre.farmacia.domain.evento;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
public class PrescricaoMedicamento {

    private Long id;

    private List<ItemPrescricaoMedicamentos> itemPrescricaoMedicamentos;
}
