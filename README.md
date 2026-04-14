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
## Autenticação de usuário

  <div align="center">
   <p>Quando autenticar o usuário registrado pelo "/auth/login" será retornado um token, copie e clique em "Authorize":</p>
    
  <img width="50%" alt="image" src="https://github.com/user-attachments/assets/66159a7d-ecee-4db6-bc28-91fda2b19953" />

  Em seguida cole seu token aqui:
  
  <img width=50% alt="image" src="https://github.com/user-attachments/assets/15605e33-1f6a-41a5-a019-c1a5537a40f6" />
</div>

 

