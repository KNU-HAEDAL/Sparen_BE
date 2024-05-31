import { fakerKO as faker } from "@faker-js/faker";

for (let i = 0; i < 50; i++) {
    const difficulty = faker.number.int({ min: 1, max: 5 });
    const onceExp = faker.number.int({ min: 1, max: 30 });
    const requiredCount = faker.number.int({ min: 5, max: 50 });
    const successExp = faker.number.int({ min: 10, max: 100 });
    const challengeGroupId = faker.number.int({ min: 1, max: 20 });
    const createdAt = faker.date.past().toISOString().slice(0, 19).replace('T', ' ');
    const lastModifiedAt = faker.date.recent().toISOString().slice(0, 19).replace('T', ' ');
    const dayTypes = ['DAY', 'MONTH', 'WEEK'];
    const dayType = dayTypes[Math.floor(Math.random() * dayTypes.length)];

    const sql = `(${difficulty}, ${onceExp}, ${requiredCount}, ${successExp}, ${challengeGroupId}, '${createdAt}', '${lastModifiedAt}', '${dayType}'),`;
    console.log(sql);
}
