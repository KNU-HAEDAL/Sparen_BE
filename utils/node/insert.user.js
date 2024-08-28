import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'zzansuni'
});

// users 테이블 데이터 생성 및 삽입
const insertUsers = () => {
    let users = [];
    for (let i = 0; i < 10_0000; i++) {
        const exp = 0;
        const createdAt = faker.date.recent();
        const lastModified = new Date();
        const authToken = null;
        const nowTime = new Date();
        const email = `${nowTime.getMilliseconds()}${i}${faker.internet.email()}`;
        const name = faker.person.firstName();
        const password = '$2a$10$h1VlKrjjHSnuRoeCnl1reOh.oaAw6EqxrSMB0FVClpOb1S2D.K.ZK';
        const profileImage = faker.image.url();
        const provider = null;
        const role = 'USER';
        users.push([exp, createdAt, lastModified, authToken, email, name, password, profileImage, provider, role]);
    }

    const sql = 'INSERT INTO users (exp, created_at, last_modified_at, auth_token, email, nickname, password, profile_image_url,provider, role) VALUES ?';
    connection.query(sql, [users], (err, results) => {
        if (err)
            console.log(err);
        else
            console.log('Inserted ' + results.affectedRows + ' rows into users table.');
    });
};


// 각 테이블에 데이터 삽입
connection.connect((err) => {
    if (err) console.log(err)
    console.log('Connected to MySQL database.');

    // 데이터 삽입 함수 호출
    insertUsers();
    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
