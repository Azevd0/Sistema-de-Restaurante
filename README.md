# Sistema-de-Restaurante
Sistema de restaurante em desenvolvimento para gerenciamento de pedidos e funcionários, permitindo cadastro de entidades, listagem de cardápio, registro de pagamento e histórico financeiro da empresa. Nesta versão implementei Redis Cache e configurei a serialização de objetos Java para Json.
Aqui está uma breve instrução de ativação da API:

## 🚀 Executando a API via Docker
Para iniciar a API do GerenciamentoPedidos e todas as suas dependências (como banco de dados e Redis) de forma automatizada, utilize o Docker Compose.

Na raiz do projeto, onde o arquivo docker-compose.yml está localizado, execute o seguinte comando:

````bash
docker compose --profile test up -d
````

## 🛑 Como parar a aplicação
Quando terminar seus testes, você pode derrubar os contêineres e liberar os recursos da máquina rodando:

```bash
docker compose --profile test down
```
