import { fakerKO as faker } from "@faker-js/faker";

for (let i = 0; i < 50; i++) {
    const createdAt = faker.date.past().toISOString().slice(0, 19).replace('T', ' ');
    const lastModifiedAt = faker.date.recent().toISOString().slice(0, 19).replace('T', ' ');
    const userChallengeId = faker.number.int({ min: 1, max: 50 });
    const content = faker.lorem.sentence();
    const imageUrl = faker.image.url();
    const statuses = ['APPROVED', 'REJECTED', 'WAITING'];
    const status = statuses[Math.floor(Math.random() * statuses.length)];

    const sql = `('${createdAt}', '${lastModifiedAt}', ${userChallengeId}, '${content}', '${imageUrl}', '${status}'),`;
    console.log(sql);
}
