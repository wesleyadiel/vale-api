## 📘 Sobre a API

Esta API tem como objetivo fornecer um sistema de **gestão de vales corporativos**, permitindo que empresas criem, distribuam e monitorem os benefícios concedidos aos seus funcionários.

Além da funcionalidade principal, este projeto também serve como um **laboratório de aprendizado e evolução contínua em Java**, onde são aplicadas boas práticas de desenvolvimento backend e exploradas tecnologias modernas da linguagem.

### ⚙️ Tecnologias e práticas aplicadas:

- ✅ **Spring Boot** para construção de APIs RESTful
- ✅ **Spring Security** com autenticação via **JWT**
- ✅ Integração com **AWS DynamoDB** como banco de dados NoSQL
- ✅ Uso do **AWS Secrets Manager** para gerenciamento seguro de segredos (ex: chave JWT)
- ✅ Estrutura de código baseada em **boas práticas de arquitetura**
- ✅ Padronização de **branches Git** para fluxo de ambientes (dev, homolog, prod)

### 🚧 Este repositório está em constante evolução

Este projeto será utilizado para estudar e implementar:

- Testes automatizados com **JUnit** e **Mockito**
- Integrações com serviços externos (ex: envio de e-mail via fila) 
- Monitoramento com **Spring Actuator** 
- Deploy automatizado com **GitHub Actions**
- Boas práticas de documentação, versionamento e CI/CD 

---

Este é um ambiente ideal para praticar, experimentar e aplicar soluções reais utilizando o ecossistema Java e tecnologias associadas.

# 🧭 Fluxo Git e Padrão de Branches

Este projeto segue uma estrutura de branches baseada em ambientes e funcionalidades, com o objetivo de manter o controle e a organização do código-fonte em todas as etapas do desenvolvimento, homologação e produção.

---

## 🚀 Branches principais por ambiente

| Branch      | Descrição                               |
|-------------|-----------------------------------------|
| `main`      | Ambiente de produção. Contém código estável. |
| `dev`       | Ambiente de desenvolvimento ativo. Features são integradas aqui. |

---

## 🧱 Tipos de branches e convenções

| Tipo         | Prefixo         | Exemplo                            | Criada a partir de |
|--------------|-----------------|-------------------------------------|--------------------|
| Funcionalidade | `feature/`    | `feature/adicionar-login`          | `dev`              |
| Correção de bug | `bugfix/`    | `bugfix/corrigir-cadastro`         | `dev`              |
| Correção urgente | `hotfix/`   | `hotfix/fix-token-prod`            | `main`             |
| Refatoração    | `refactor/`   | `refactor/melhorar-jwt-provider`   | `dev`              |
| Tarefa de build/config | `chore/` | `chore/atualizar-dependencias`   | `dev`              |
| Documentação   | `docs/`       | `docs/atualizar-readme`            | `dev`              |
| Release        | `release/`    | `release/v1.2.0`                    | `dev`              |

---

## 🔁 Fluxo Git padrão

```text
feature/*     →      dev      →      homolog      →      main
(novas tarefas)       (integração)       (testes QA)        (produção)
