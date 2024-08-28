import { fakerKO as faker } from "@faker-js/faker";
import mysql from 'mysql2';


/**
 * NSERT IGNORE INTO challenge_group_user_exp (total_exp, challenge_group_id, user_id)
 *  SELECT 0 AS total_exp, c.challenge_group_id, uc.user_id
 *  FROM user_challenge uc JOIN challenge c
 *  ON uc.challenge_id = c.id;
 * 삽입 이후 해당 INSERT 문을 이용하여 데이터를 삽입할 수 있음
 */
// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'zzansuni'
});


// 테이블 데이터 생성 및 삽입
const insertData = () => {
    let entities = [];
    for (let i = 0; i < 1000; i++) {

        const challengeId = Math.floor(Math.random() * 807) + 1;
        const createdAt = new Date();
        const lastModified = createdAt;
        const userId = Math.floor(Math.random() * 1472103) + 1;
        const status = 'PROCEEDING';
        entities.push([challengeId, createdAt, lastModified, userId, status]);
    }

    const sql = 'INSERT IGNORE INTO user_challenge (challenge_id, created_at, last_modified_at, user_id, status) VALUES ?';
    connection.query(sql, [entities], (err, results) => {
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
    for(let i = 0; i < 100; i++){
        insertData();
    }

    // 다른 테이블 데이터 삽입 함수들도 추가할 수 있음

    // 연결 종료
    connection.end();
});
