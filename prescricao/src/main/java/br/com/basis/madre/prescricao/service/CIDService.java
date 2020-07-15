package br.com.basis.madre.prescricao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.basis.madre.prescricao.domain.CID;

@FeignClient("INTERNACAO")

@Service
public interface CIDService {

	@GetMapping("/api/cids/{id}")
	CID findOne(@PathVariable("id") Long id);

}
