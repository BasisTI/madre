package br.com.basis.madre.cadastros.repository;

import br.com.basis.madre.cadastros.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by roman on 7/8/17.
 */
public interface UploadedFilesRepository extends JpaRepository<UploadFile, Long> {
}
