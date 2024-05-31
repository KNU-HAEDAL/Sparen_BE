/*
 어드민 1명, 매니저 1명, 유저 18명 INSERT
 */
INSERT INTO users (exp, created_at, last_modified_at, auth_token, email, nickname, password, profile_image_url, provider, role)
VALUES
    (0, '2023-05-31 10:00:00', '2023-05-31 12:00:00', 'NAVER_HP6Yyh7SIkk8QS1VFtEylxrxo23UxNTBHcAjfTw-BHM', null, 'bayy', null, null, 'NAVER', 'ADMIN'),
    (0, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test1@a.c', 'testUser1', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'MANAGER'),
    (49, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test0@a.c', '이원', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (94, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test1@a.c', '재광', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (87, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test2@a.c', '조한', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (87, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test3@a.c', '태혁', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (47, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test4@a.c', '태윤', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (43, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test5@a.c', '예승', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (83, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test6@a.c', '효신', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (52, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test7@a.c', '준범', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (68, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test8@a.c', '소담', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (40, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test9@a.c', '준섭', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (32, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test10@a.c', '강은', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (17, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test11@a.c', '의빈', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (3, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test12@a.c', '승지', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (66, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test13@a.c', '휘서', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (60, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test14@a.c', '영후', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (25, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test15@a.c', '형우', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (38, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test16@a.c', '희범', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),
    (31, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test17@a.c', '효범', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER');

