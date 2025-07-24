# Fórum Alura Clone

Projeto de backend desenvolvido com Java, Spring Boot e PostgreSQL, inspirado no fórum da Alura. O objetivo é permitir que usuários autenticados criem tópicos relacionados a cursos, promovendo interação e aprendizado entre a comunidade.

---

## 🚀 Funcionalidades

- ✅ Cadastro de usuários
- 🔐 Autenticação com JWT (Bearer Token)
- 🗨️ Criação de tópicos com título e mensagem
- 📦 Associação de tópicos a usuários e cursos
- ✏️ Edição e exclusão de tópicos pelo autor
- 🔍 Listagem de todos os tópicos
- 🛡️ Proteção de endpoints por autenticação

---

## 📚 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Maven
- Docker & Docker Compose
- Insomnia (para testes de API)

---

## 🧪 Testes com Insomnia

Use o Insomnia ou Postman para testar os seguintes endpoints:

| Método | Endpoint                          | Autenticação      | Descrição                       |
|--------|-----------------------------------|-------------------|---------------------------------|
| `POST` | `/auth/login`                     | ❌ Não precisa     | Retorna token de acesso         |
| `GET`  | `/api/topicos`                    | ✅ Bearer Token    | Lista todos os tópicos          |
| `POST` | `/api/topicos`                    | ✅ Bearer Token    | Cria novo tópico                |
| `PUT`  | `/api/topicos/{id}`               | ✅ Somente autor   | Atualiza tópico                 |
| `DELETE` | `/api/topicos/{id}`             | ✅ Somente autor   | Remove tópico                   |

---

## 🧩 Regras de Negócio

- Apenas usuários **cadastrados e logados** podem criar tópicos
- Cada tópico pertence a **um curso** e a **um usuário**
- Só o **autor** pode editar ou excluir seus tópicos

---

## 🐳 Como Rodar com Docker

```bash
# Construir e subir os containers
docker-compose up --build
