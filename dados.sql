INSERT INTO usuario(data_criacao, data_ultimo_acesso, flag_ativo, login, email, nome, senha)
values ('2021-12-20 18:47:52', '2021-12-21 18:47:52', true, 'ester','ester@ucsal.edu.br', 'Ester', '$2a$10$Le.iEd4ISlBGn65MJuSeMuLfwlcES6zMfHKBnm07xWKfTAv1S/sw6'),
       ('2021-12-20 18:47:52', '2021-12-21 18:47:52', true, 'yla','yla@ucsal.edu.br', 'Yla Buri', '$2a$10$76rLhrHoVlJNxT9lvmV3LuqBwVYrAtJ3akT1ahV3viS7cSo42Tpq.'),
       ('2021-12-20 18:47:52', '2021-12-21 18:47:52', true, 'joaldo','joaldo@ucsal.edu.br', 'Joaldo Tavares', '$2a$10$76rLhrHoVlJNxT9lvmV3LuqBwVYrAtJ3akT1ahV3viS7cSo42Tpq.'),
       ('2023-04-06 18:23:17', '2023-04-06 18:23:17', true, 'danilo','danilo@email.com', 'Danilo', '$2a$10$RJDFjMmilJEuV1.8jCyR0OZamgC3C4hRnHHG397p.p.PwnNblYZFG');

SELECT * FROM USUARIO;

INSERT INTO perfil(nome)
values('Admin'),
('Aluno'),
('Professor');

SELECT * FROM TAREFA;

INSERT INTO tarefa(descricao, status, visibilidade, titulo, usuario_id)
values
       ('Digite numeros separados por espaço que a soma seja 10', 0, 0, 'Até 10', 3),
       ('Mostre a divisão entre dois numeros', 0, 0, 'Divisão', 3),
       ('Mostre o inverso da palavra', 0, 0, 'Inverso', 3);

INSERT INTO caso_teste(comparacao, entrada, flag_exibir, nome_teste, saida, tarefa_id)
values
       (1, '1 2 3 4', true, 'Até 10', '10', 1),
       (2, '5 5', false, 'Até 10', '10', 1),
       (2, '7 1 1 1', true, 'Até 10', '10', 1),
       (1, '4 2', true, 'Teste 1', '2', 2),
       (1, '10 0', true, 'Divisão por 0', 'undefined', 2),
       (1, '8 2', true, 'Teste 2', '4', 2),
       (1, 'oi', true, 'Teste 1', 'io', 3),
       (1, 'casa', true, 'Teste 2', 'asac', 3),
       (1, 'papel', false, 'Teste 3', 'lepap', 3),
       (1, 'tomada', false, 'Teste 4', 'adamot', 3);
       
SELECT * FROM USUARIO_PERFIL;

INSERT INTO USUARIO_PERFIL(USUARIO_ID, PERFIL_ID)
values (1, 1),(2, 2),(3, 3), (4, 2);

INSERT INTO PROVA(DATA_INICIAL, DATA_FINAL, NOME, USUARIO_ID)
values ('2022-11-14 07:00:00', '2022-11-29 23:59:59', 'Prova final', 3),
       ('2022-11-01 07:00:00', '2022-12-12 23:59:59', 'Prova final 2', 3);

INSERT INTO ITEM_PROVA(ORDEM, PROVA_ID, TAREFA_ID)
values (1, 1, 1),
        (2, 1, 2),
       (1, 2, 2),
       (2, 2, 3);
       
-- Leonam 
INSERT INTO tarefa(descricao, status, visibilidade, titulo, usuario_id)
values
       ('Calcule a área do círculo: area = pi . raio^2', 0, 0, 'área do Círculo', 3),
       ('Calcule o produto entre estes dois valore inteiros', 0, 0, 'Produto Simples', 3),
       ('Calcule a média do aluno. Nota A tem peso 3.0 e a nota B tem peso 7.0', 0, 0, 'Média 1', 3),
       ('Calcule a média do aluno. Nota A tem peso 2, nota B tem peso 3 e nota C tem peso 5.0', 0, 0, 'Média 2', 3),
       ('Calcule e mostre a diferença do produto de A e B pelo produto de C e D segundo a fórmula: DIFERENCA = (A * B - C * D).', 0, 0, 'Diferença', 3);

SELECT * FROM caso_teste;

INSERT INTO caso_teste(comparacao, entrada, flag_exibir, nome_teste, saida, tarefa_id)
values
(1, '2.00', true, 'Teste 1', 'A=12.5664', 4),
       (1, '100.64', true, 'Teste 2', 'A=31819.3103', 4),
       (1, '150.00', true, 'Teste 3', 'A=70685.7750', 4),
       (1, '3 9', true, 'Teste 1', 'PROD=27', 5),
       (1, '-30 10', true, 'Teste 2', 'PROD=-300', 5),
       (1, '0 9', true, 'Teste 3', 'PROD=0', 5),
       (1, '5.0 7.1', true, 'Teste 1', 'MEDIA=6.43182', 6),
       (1, '0.0 7.1', true, 'Teste 2', 'MEDIA=4.84091', 6),
       (1, '10.0 10.0', true, 'Teste 3', 'MEDIA=10.00000', 6),
       (1, '5.0 6.0 7.0', true, 'Teste 1', 'MEDIA=6.3', 7),
       (1, '5.0 10.0 10.0', true, 'Teste 2', 'MEDIA=9.0', 7),
       (1, '10.0 10.0 5.0', true, 'Teste 3', 'MEDIA=7.5', 7),
       (1, '5 6 7 8', true, 'Teste 1', 'DIFERENCA=-26', 8),
       (1, '0 0 7 8', true, 'Teste 2', 'DIFERENCA=-56', 8),
       (1, '5 6 -7 8', true, 'Teste 3', 'DIFERENCA=86', 8);
       
SELECT * FROM TAREFA;
