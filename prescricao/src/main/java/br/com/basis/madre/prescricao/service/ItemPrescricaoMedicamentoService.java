package br.com.basis.madre.prescricao.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.basis.madre.prescricao.domain.ItemPrescricaoMedicamento;
import br.com.basis.madre.prescricao.repository.ItemPrescricaoMedicamentoRepository;
import br.com.basis.madre.prescricao.service.dto.ItemPrescricaoMedicamentoDTO;
import br.com.basis.madre.prescricao.service.mapper.ItemPrescricaoMedicamentoMapper;

@Service
@Transactional
public class ItemPrescricaoMedicamentoService {
	
	private final ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository;
	
	private final ItemPrescricaoMedicamentoMapper itemPrescricaoMedicamentoMapper;
	
	public ItemPrescricaoMedicamentoService(ItemPrescricaoMedicamentoRepository itemPrescricaoMedicamentoRepository, ItemPrescricaoMedicamentoMapper itemPrescricaoMedicamentoMapper) {
        this.itemPrescricaoMedicamentoRepository = itemPrescricaoMedicamentoRepository;
        this.itemPrescricaoMedicamentoMapper = itemPrescricaoMedicamentoMapper;
    }
	
	@Transactional(readOnly = true)
    public Page<ItemPrescricaoMedicamentoDTO> findAll(Pageable pageable) {
        return itemPrescricaoMedicamentoRepository.findAll(pageable)
            .map(itemPrescricaoMedicamentoMapper::toDto);
    }
	
	 public ItemPrescricaoMedicamentoDTO save(ItemPrescricaoMedicamentoDTO itemPrescricaoMedicamentoDTO) {
	        ItemPrescricaoMedicamento itemPrescricaoMedicamento = itemPrescricaoMedicamentoMapper.toEntity(itemPrescricaoMedicamentoDTO);
	        itemPrescricaoMedicamento = itemPrescricaoMedicamentoRepository.save(itemPrescricaoMedicamento);
	        ItemPrescricaoMedicamentoDTO result = itemPrescricaoMedicamentoMapper.toDto(itemPrescricaoMedicamento);
	        itemPrescricaoMedicamentoRepository.save(itemPrescricaoMedicamento);
	        return result;
	    }

}
