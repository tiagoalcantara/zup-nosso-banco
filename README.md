# 🏦 Nosso banco digital

Essa aplicação, desenvolvida com <strong>Spring</strong>, é parte do processo seletivo para o <strong>Bootcamp da Zup</strong>.

## Como rodar?

Levando em conta que você tenha o Java 11 e o Maven configurados, o processo é simples. Basta clonar esse repositório:

```
git clone https://github.com/tiagoalcantara/zup-nosso-banco.git
```

### Alguns detalhes importantes
A fins de demonstração, não omiti informação sensível do ambiente de desenvolvimento:

- A aplicação está configurada para um banco <strong>Postgres</strong>. Usei essa [imagem do Docker](https://hub.docker.com/_/postgres).
  - Nome do banco: <strong>nosso_banco_digital</strong>
  - Porta: <strong>5432</strong>
  - Usuário: <strong>postgres</strong>
  - Senha: docker
- A aplicação está rodando por padrão na porta <strong>4000</strong>.
- Os e-mails enviados estão caindo no meu [Mailtrap](https://mailtrap.io/), necessário mudar as configurações no `application.properties` para conseguir visualizar.

### Executando

```
# Para instalar as dependências
mvn install

# Para executar o projeto
mvn spring-boot:run
```
## Rotas

Usei o [Insomnia](https://insomnia.rest/download/) para testar as rotas. Um arquivo com minha workspace pode ser baixado [aqui](https://drive.google.com/file/d/1YvMm9mnpoZMl5es61ml6Y4_v5bF3315y/view?usp=sharing).

### Referência rápida

<table>
  <tr>
    <th>Rota</th>
    <th>Método</th>
    <th>Descrição</th>
  <tr>
  <tr>
    <td>/proposal</td>
    <td>GET</td>
    <td>Retorna uma lista com todas as propostas do banco.</td>
  </tr>
  <tr>
    <td>/proposal/:id</td>
    <td>GET</td>
    <td>Retorna uma proposta com determinado id.</td>
  </tr>
  <tr>
    <td>/proposal/step-one</td>
    <td>POST</td>
    <td>Recebe no corpo as informações pessoais 'firstName', 'lastName', 'email', 'identificationDocument', 'dateOfBirth' e 'cpf' e cria a proposta no banco.</td>
  </tr>
  <tr>
    <td>/proposal/step-two?id=:id</td>
    <td>POST</td>
    <td>Recebe na url o campo 'id' da proposta em andamento. Recebe no corpo as informações de endereço 'cep', 'street', 'complement', 'neighborhood', 'city' e 'province' e atualiza a proposta com os dados de endereço.</td>
  </tr>
  <tr>
    <td>/proposal/step-three?id=:id</td>
    <td>POST</td>
    <td>Recebe na url o campo 'id' da proposta em andamento. Recebe no corpo os arquivos Multipart 'documentFront' e 'documentBack', salva os arquivos e atualiza a proposta com a url das imagens.</td>
  </tr>
  <tr>
    <td>/proposal/step-four?id=:id</td>
    <td>POST</td>
    <td>Recebe na url o campo 'id' da proposta em andamento. Recebe no corpo um booleano 'accepted' e dispara um e-mail de acordo.</td>
  </tr>
  <tr>
    <td>/account</td>
    <td>POST</td>
    <td>Recebe no corpo o campo 'proposalId' com o id de uma proposta, caso apta, gera uma nova conta associada com aquela proposta e dispara um e-mail com os dados.</td>
  </tr>
</table>

### Opiniões

Aqui vou listar alguns pontos que me deixaram satisfeitos com a aplicação, e coisas que eu gostaria de melhorar caso houvesse mais tempo.

#### Pontos positivos
- Consegui aplicar DDD e principios SOLID para facilitar a manutenção do código.
- O upload de imagens está sendo feito no disco, mas foi pensado para facilitar sua substituição por um serviço como o Amazon S3 em produção.
- Similar ao upload, o envio de e-mails foi feito com o Mailtrap e pensado para ser facilmente substituido por um serviço como o Amazon SES em produção.
- Boas práticas na nomeclatura de variáveis e funções.

### Possíveis melhorias
- Gostaria de ter escrito testes para os serviços, mas dado o prazo não o fiz.
- Os e-mails poderiam ter um template em vez de texto puro.
- Acho que alguns controllers poderiam ser divididos em outros para reduzir sua responsabilidade.
- Tratamento de erros poderia ser feito de forma menos genérica e com menos código repetido.
- Melhores configurações de segurança e autenticação.
- Implementar um cache para economizar buscas no banco de dados.
- E claro, ter finalizado o restante dos requisitos.
