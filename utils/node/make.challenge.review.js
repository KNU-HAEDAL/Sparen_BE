import { fakerKO as faker } from "@faker-js/faker";

for (let i = 0; i < 50; i++) {
    const rating = faker.number.int({ min: 1, max: 5 });
    const createdAt = faker.date.past().toISOString().slice(0, 19).replace('T', ' ');
    const lastModifiedAt = faker.date.recent().toISOString().slice(0, 19).replace('T', ' ');
    const userChallengeId = faker.number.int({ min: 1, max: 50 });
    const content = faker.lorem.sentence();

    const sql = `(${rating}, '${createdAt}', '${lastModifiedAt}', ${userChallengeId}, '${content}'),`;
    console.log(sql);
}
