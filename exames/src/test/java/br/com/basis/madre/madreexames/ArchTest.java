package br.com.basis.madre.madreexames;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.com.basis.madre.madreexames");

        noClasses()
            .that()
                .resideInAnyPackage("br.com.basis.madre.madreexames.service..")
            .or()
                .resideInAnyPackage("br.com.basis.madre.madreexames.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..br.com.basis.madre.madreexames.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
