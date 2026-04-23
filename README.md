# Order Factory
[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.3.3-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)](https://redis.io/)
[![Docker](https://img.shields.io/badge/Docker-Container-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Docker Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://docs.docker.com/compose/)
[![GraalVM](https://img.shields.io/badge/GraalVM-Native%20Image-black?style=for-the-badge&logo=graalvm&logoColor=white)](https://www.graalvm.org/)

Sistema de restaurante desenvolvido para gerenciamento de pedidos e registro de usuários(atendentes), permitindo cadastro de entidades, listagem de cardápio, lançamento de pedidos e registro de pagamento. O sistema também conta com histórico de lançamentos, restrição de acesso para administradores e relatório financeiro. Para escalabilidade e performance, a API tem serviços de armazenamento de dados em cache e em banco de dados relacional, ambos executados em ambiente de contêiner, tornando seu uso ágil e seguro.

## Configuração do Ambiente Local

Para rodar este projeto na sua máquina, você precisará configurar as credenciais do banco de dados e garantir que sua IDE consiga ler essas variáveis. Siga o passo a passo abaixo:

### 1. Configurando as Variáveis de Ambiente (.env)
Este projeto utiliza variáveis de ambiente para proteger dados sensíveis. 

1. Na raiz do projeto, localize o arquivo `.env.example`.
2. Faça uma cópia deste arquivo e renomeie a cópia para **`.env`**.
3. Abra o seu novo arquivo `.env` e preencha com as senhas e usuários que você deseja utilizar no seu banco local:
   ```env
   DB_USER=seu_usuario_aqui
   DB_PASSWORD=sua_senha_aqui
   DB_NAME=orderfactory
   ```
4. Para que sua IDE saiba ler o .env, atualize suas configurações de run para que possa receber bem o arquivo.
* Intellij: instale o plugin EnvFile, vá nas configurações de run -> Edit -> marque Enable Envfile -> selecione seu arquivo.env
* VS Code: baixe a extensão Spring Boot Extension Pack, ele lida automaticamente com o .env
* Eclipse: clique com o botão direito no projeto -> Run As -> Run Configurations....
Selecione sua aplicação em Spring Boot App ou Java Application. Vá na aba Environment.
Aqui, você terá que clicar em Add... e colocar cada variável manualmente (DB_USER, DB_PASSWORD, etc.).


## Executando a API via Docker
Para iniciar OrderFactory, utilize o Docker Compose para executar os perfis de teste ou produção.
### Spring profile:
no application.properties, altere o spring.profiles.active= para o perfil desejado (test/prod).

Na raiz do projeto, onde o arquivo docker-compose.yml está localizado, execute o seguinte comando, para iniciar o contêiner de testes:

````bash
docker compose --profile test up -d
````

Para executar o contêiner de produção, primeiro você deve executar o build da imagem na raíz do projeto:

````bash
docker compose --profile prod build
````
O processo é demorado e custa muita memória, pois o projeto usa compilação aot (ahead-of-time). Se o build quebrar é por falta de memória, não da máquina, mas a memória disponível para o docker. Caso aconteça, pesquise %USERPROFILE% na barra de pesquisa do windows e crie um arquivo de texto e renomeie para .wslconfig (certifique-se de remover o .txt, caso contrário ele não vai funcionar. No arquivo cole isso e execute o build novamente:

```
[wsl2]
memory=8GB 

processors=4

swap=4GB
```
Quando o build estiver completo execute:
```bash
docker compose --profile prod up -d
```

## 🛑 Como parar a aplicação
Quando terminar seus testes, você pode derrubar os contêineres e liberar os recursos da máquina rodando:

```bash
docker compose --profile test down
```
ou para o perfil de desenvolvimento...
````bash
docker compose --profile prod down
````

## Acessando documentação (Swagger)
Com a aplicação em runtime, acesse a URL da documentação dos testes:

```bash
http://localhost:9090/swagger-ui/index.html
```
e para a documentação de produção...

```bash
http://localhost:9091/swagger-ui/index.html
```

## Autenticação de usuário

  <div align="center">
   <p>Quando autenticar o usuário registrado pelo "/auth/login" será retornado um token, copie e clique em "Authorize":</p>
    
  <img width="50%" alt="image" src="https://github.com/user-attachments/assets/66159a7d-ecee-4db6-bc28-91fda2b19953" />

  Em seguida cole seu token aqui:
  
  <img width=50% alt="image" src="https://github.com/user-attachments/assets/15605e33-1f6a-41a5-a019-c1a5537a40f6" />
</div>

## Cartões
Para registro de pagamento na modalidade de cartão, há cartões fictícios instanciados em memória, mas APENAS PARA O PERFIL DE TESTE. Confira as senhas de cada um:

Id: 1 | Senha: @#1234

Id: 2 | Senha: @#9876

Id: 3 | Senha: @#5544

Id: 4 | Senha: @#102030

Id: 5 | Senha: @#8899

Id: 6 | Senha: @#1122

Id: 7 | Senha: @#3344

Id: 8 | Senha: @#5566

Id: 9 | Senha: @#7788

Id: 10 | Senha: @#9966

 

