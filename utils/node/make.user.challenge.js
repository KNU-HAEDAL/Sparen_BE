import { fakerKO as faker } from "@faker-js/faker";

for (let i = 0; i < 50; i++) {
    const challengeId = faker.number.int({ min: 1, max: 40 });
    const userId = faker.number.int({ min: 1, max: 20 });
    const createdAt = faker.date.past().toISOString().slice(0, 19).replace('T', ' ');
    const lastModifiedAt = faker.date.recent().toISOString().slice(0, 19).replace('T', ' ');
    const statuses = ['PROCEEDING', 'SUCCESS', 'FAIL'];
    const status = statuses[Math.floor(Math.random() * statuses.length)];

    const sql = `(${challengeId}, '${createdAt}', '${lastModifiedAt}', ${userId}, '${status}'),`;
    console.log(sql);
}
