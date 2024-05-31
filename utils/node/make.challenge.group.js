// INSERT INTO challenge_group (cumulative_count, created_at, last_modified_at, content, title, category)
// VALUES (100, '2023-05-31 10:00:00', '2023-05-31 12:00:00', 'This is a sample content', 'Sample Title', 'HEALTH');
import { fakerKO as faker } from "@faker-js/faker";

for(let i=0; i<20; i++) {
    const category = ['HEALTH', 'ECHO', 'SHARE', 'VOLUNTEER'];
    const selectedCategory = category[Math.floor(Math.random() * category.length)];
    const content = faker.lorem.text().substring(0,254);
    const title = faker.lorem.words(3);

    const sql = `(100, '2023-05-31 10:00:00', '2023-05-31 12:00:00', '${content}', '${title}', '${selectedCategory}'),`;
    console.log(sql);
}