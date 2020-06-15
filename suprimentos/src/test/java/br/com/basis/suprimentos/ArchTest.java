package br.com.basis.suprimentos;

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
            .importPackages("br.com.basis.suprimentos");

        noClasses()
            .that()
            .resideInAnyPackage("br.com.basis.suprimentos.service..")
            .or()
            .resideInAnyPackage("br.com.basis.suprimentos.repository..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..br.com.basis.suprimentos.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
