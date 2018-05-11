sudo docker-compose -f ~/madre/madre_codigo_fonte/cadastros-basicos/src/main/docker/postgresql.yml up -d

sudo docker-compose -f ~/madre/madre_codigo_fonte/cadastros-basicos/src/main/docker/elasticsearch.yml up -d

sudo docker-compose -f ~/madre/madre_codigo_fonte/cadastros-basicos/src/main/docker/jhipster-registry.yml up -d

echo -e "\n\n[ Start Concluído ]\n\n"
