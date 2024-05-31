import { fakerKO as faker } from "@faker-js/faker";
for(let i = 0; i < 20; i++) {
    const name = faker.person.firstName();
    const randomExp = Math.floor(Math.random() * 100);
    const sql = `(${randomExp}, '2023-05-31 10:00:00', '2023-05-31 12:00:00', null, 'test${i}@a.c', '${name}', '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK', null, null, 'USER'),`;
    console.log(sql);
}