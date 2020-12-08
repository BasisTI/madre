# madre

![Version: 1.0.4](https://img.shields.io/badge/Version-1.0.4-informational?style=flat-square) ![Type: application](https://img.shields.io/badge/Type-application-informational?style=flat-square) ![AppVersion: 1.0.0](https://img.shields.io/badge/AppVersion-1.0.0-informational?style=flat-square)

madre

## Maintainers

| Name | Email | Url |
| ---- | ------ | --- |
| Basis | basis@basis.com.br |  |

## Source Code

* <https://github.com/BasisTI/madre/>

## Requirements

| Repository | Name | Version |
|------------|------|---------|
| http://element.basis.com.br/repository/commons-helm/ | common | 2.0.2 |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| comum.configmap.spring.data.jest.uri | string | `"http://elasticsearch-client.elasticsearch:9200"` |  |
| comum.configmap.spring.elasticsearch.rest.uris | string | `"http://elasticsearch-client.elasticsearch:9200"` |  |
| comum.configmap.spring.kafka.bootstrap-servers | string | `"confluent-cp-kafka.confluent:9092"` |  |
| comum.configmap.spring.profiles.active | string | `"prod,swagger"` |  |
| comum.imagePullSecrets[0].name | string | `"basis-registry"` |  |
| farmacia.configmap.spring.datasource.password | string | `"farmacia"` |  |
| farmacia.configmap.spring.datasource.url | string | `"jdbc:postgresql://pgsql-madre/madre"` |  |
| farmacia.configmap.spring.datasource.username | string | `"farmacia"` |  |
| farmacia.image.repository | string | `"basis-registry.basis.com.br/madre/madrefarmacia"` |  |
| farmacia.image.tag | string | `"latest"` |  |
| farmacia.name | string | `"farmacia"` |  |
| farmacia.ports.port | int | `8081` |  |
| gateway.configmap.spring.profiles.active | string | `"prod,swagger"` |  |
| gateway.image.repository | string | `"basis-registry.basis.com.br/madre/madregateway"` |  |
| gateway.image.tag | string | `"latest"` |  |
| gateway.imagePullSecrets[0].name | string | `"basis-registry"` |  |
| gateway.name | string | `"gateway"` |  |
| gateway.ports.port | int | `8080` |  |
| internacao.configmap.spring.datasource.password | string | `"internacao"` |  |
| internacao.configmap.spring.datasource.url | string | `"jdbc:postgresql://pgsql-madre/madre"` |  |
| internacao.configmap.spring.datasource.username | string | `"internacao"` |  |
| internacao.image.repository | string | `"basis-registry.basis.com.br/madre/madreinternacao"` |  |
| internacao.image.tag | string | `"latest"` |  |
| internacao.name | string | `"internacao"` |  |
| internacao.ports.port | int | `8081` |  |
| madrecliente.image.repository | string | `"basis-registry.basis.com.br/madre/cliente"` |  |
| madrecliente.image.tag | string | `"latest"` |  |
| madrecliente.ingress.annotations."cert-manager.io/cluster-issuer" | string | `"letsencrypt-prod"` |  |
| madrecliente.ingress.annotations."kubernetes.io/tls-acme" | string | `"true"` |  |
| madrecliente.ingress.tls[0].hosts[0] | string | `"madre.tst.basis.com.br"` |  |
| madrecliente.ingress.tls[0].secretName | string | `"madretst-cert"` |  |
| madrecliente.ingress.url | string | `"madre.tst.basis.com.br"` |  |
| madrecliente.name | string | `"cliente"` |  |
| madrecliente.ports.port | int | `80` |  |
| madrecliente.proxy.locations[0] | string | `"pacientes"` |  |
| madrecliente.proxy.locations[1] | string | `"internacao"` |  |
| madrecliente.proxy.locations[2] | string | `"prescricao"` |  |
| madrecliente.proxy.locations[3] | string | `"farmacia"` |  |
| madrecliente.proxy.locations[4] | string | `"suprimentos"` |  |
| madrecliente.proxy.locations[5] | string | `"consulta"` |  |
| madreconsulta.configmap.spring.datasource.password | string | `"consulta"` |  |
| madreconsulta.configmap.spring.datasource.url | string | `"jdbc:postgresql://pgsql-madre/madre"` |  |
| madreconsulta.configmap.spring.datasource.username | string | `"consulta"` |  |
| madreconsulta.image.repository | string | `"basis-registry.basis.com.br/madre/madreconsulta"` |  |
| madreconsulta.image.tag | string | `"latest"` |  |
| madreconsulta.name | string | `"madreconsulta"` |  |
| madreconsulta.ports.port | int | `8081` |  |
| madresuprimentos.configmap.spring.datasource.password | string | `"suprimentos"` |  |
| madresuprimentos.configmap.spring.datasource.url | string | `"jdbc:postgresql://pgsql-madre/madre"` |  |
| madresuprimentos.configmap.spring.datasource.username | string | `"suprimentos"` |  |
| madresuprimentos.image.repository | string | `"basis-registry.basis.com.br/madre/madresuprimentos"` |  |
| madresuprimentos.image.tag | string | `"latest"` |  |
| madresuprimentos.jdk | int | `11` |  |
| madresuprimentos.name | string | `"madresuprimentos"` |  |
| madresuprimentos.ports.port | int | `8081` |  |
| pacientes.configmap.spring.datasource.password | string | `"pacientes"` |  |
| pacientes.configmap.spring.datasource.url | string | `"jdbc:postgresql://pgsql-madre/madre"` |  |
| pacientes.configmap.spring.datasource.username | string | `"pacientes"` |  |
| pacientes.image.repository | string | `"basis-registry.basis.com.br/madre/madrepacientes"` |  |
| pacientes.image.tag | string | `"latest"` |  |
| pacientes.name | string | `"pacientes"` |  |
| pacientes.ports.port | int | `8081` |  |
| postgresql.fullnameOverride | string | `"pgsql-madre"` |  |
| postgresql.install | bool | `true` |  |
| postgresql.persistence.size | string | `"10Gi"` |  |
| postgresql.persistence.storageClass | string | `"nfs-kube-des"` |  |
| postgresql.postgresqlDatabase | string | `"madre"` |  |
| postgresql.postgresqlPassword | string | `"madre"` |  |
| postgresql.postgresqlPostgresPassword | string | `"basis123"` |  |
| postgresql.postgresqlUsername | string | `"madre"` |  |
| prescricao.configmap.spring.datasource.password | string | `"prescricao"` |  |
| prescricao.configmap.spring.datasource.url | string | `"jdbc:postgresql://pgsql-madre/madre"` |  |
| prescricao.configmap.spring.datasource.username | string | `"prescricao"` |  |
| prescricao.image.repository | string | `"basis-registry.basis.com.br/madre/madreprescricao"` |  |
| prescricao.image.tag | string | `"latest"` |  |
| prescricao.name | string | `"prescricao"` |  |
| prescricao.ports.port | int | `8081` |  |

----------------------------------------------
Autogenerated from chart metadata using [helm-docs v1.4.0](https://github.com/norwoodj/helm-docs/releases/v1.4.0)
