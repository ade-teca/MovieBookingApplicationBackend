# Movie Booking Application 🎬

Este é um sistema de reserva de ingressos para cinema desenvolvido com **Spring Boot 3**, focado em uma arquitetura limpa e lógica de negócio robusta.

## 🚀 O que já foi implementado?

O projeto já conta com o fluxo completo de reserva, desde a gestão de filmes até a confirmação de pagamento:

* **Gestão de Filmes e Cinemas:** CRUD completo com filtros de busca por localização e título.
* **Gestão de Sessões (Shows):** Lógica para vincular filmes a cinemas com horários e preços específicos.
* **Sistema de Reserva (Booking):**
    * Fluxo de estados: `PENDING` -> `CONFIRMED` ou `CANCELLED`.
    * **Validação de Assentos:** O sistema impede a reserva de assentos já ocupados para a mesma sessão.
    * **Regra de Cancelamento:** Implementada lógica que impede cancelamentos com menos de 2 horas de antecedência do filme.
* **Arquitetura:** Uso de DTOs (Data Transfer Objects) para segurança dos dados e ModelMapper para conversão de entidades.

## 🛠️ Tecnologias Utilizadas

* Java 17+
* Spring Boot 3
* Spring Data JPA
* ModelMapper
* Lombok
* MySQL/PostgreSQL (H2 para desenvolvimento)

## 🏗️ Próximos Passos (Roadmap)

- [ ] Implementar Segurança com Spring Security e **JWT**.
- [ ] Configuração de perfis de banco de dados (`application.yml`).
- [ ] Tratamento Global de Exceções.
- [ ] Testes Unitários e de Integração (JUnit/Mockito).

## 📝 Como executar (Em breve)
*Instruções de configuração do banco de dados serão adicionadas após a finalização do application.yml.*

