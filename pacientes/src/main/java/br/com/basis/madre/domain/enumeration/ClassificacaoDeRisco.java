package br.com.basis.madre.domain.enumeration;

public enum ClassificacaoDeRisco {
    NAO_URGENTE{
        @Override
        public Integer nivelEmergencia() {
            return 50;
        }
    }, POUCO_URGENTE{
        @Override
        public Integer nivelEmergencia() {
            return 40;
        }
    }, URGENTE{
        @Override
        public Integer nivelEmergencia() {
            return 30;
        }
    }, MUITO_URGENTE{
        @Override
        public Integer nivelEmergencia() {
            return 20;
        }
    }, EMERGENCIA{
        @Override
        public Integer nivelEmergencia() {
            return 10;
        }
    };

    public abstract Integer nivelEmergencia();
}
