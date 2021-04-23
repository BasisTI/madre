package br.com.basis.madre.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ClassificacaoDeRisco {
    NAO_URGENTE, POUCO_URGENTE, URGENTE, MUITO_URGENTE, EMERGENCIA;

    private static Map<ClassificacaoDeRisco, Integer> niveisEmergencia = new HashMap(){{
       put(ClassificacaoDeRisco.EMERGENCIA, 10);
       put(ClassificacaoDeRisco.MUITO_URGENTE, 20);
       put(ClassificacaoDeRisco.URGENTE, 30);
       put(ClassificacaoDeRisco.POUCO_URGENTE, 40);
       put(ClassificacaoDeRisco.NAO_URGENTE, 50);
    }};

    public static Map<ClassificacaoDeRisco, Integer> getNiveisEmergencia(){
        return niveisEmergencia;
    }
}
