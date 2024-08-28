import {en, fakerKO as faker} from "@faker-js/faker";
import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'zzansuni'
});

const contentList = [
    "도전을 완료했습니다! 정말 도전적이었지만 보람 있었어요.",
    "이 챌린지를 통해 많은 것을 배웠습니다. 어려웠지만 재미있었습니다.",
    "성공적으로 챌린지를 완료했습니다. 다음 챌린지가 기다려집니다!",
    "기한 내에 도전을 완료했어요. 매우 만족스럽습니다!",
    "처음에는 힘들었지만, 이제는 끝내서 기쁩니다.",
    "도전 과제가 정말 좋았습니다. 다음에 또 도전해보겠습니다.",
    "이 챌린지는 제게 큰 도전이었지만, 완료한 후 보람이 있었습니다.",
    "다음에는 더 많은 도전 과제를 시도해보고 싶습니다. 매우 유익했어요.",
    "이번 챌린지는 어려웠지만, 목표를 달성하고 나니 기분이 좋습니다.",
    "도전이 끝나서 기쁩니다. 좋은 경험이었습니다!",
    "처음에는 어려웠지만 점점 쉬워졌어요. 좋은 경험이었습니다.",
    "도전의 난이도가 적당했습니다. 다시 시도할 의향이 있습니다.",
    "과정이 힘들었지만 목표를 달성하고 나니 만족스럽습니다.",
    "도전이 힘들었지만, 결과가 좋네요. 다음에는 더 도전적인 목표를 세워볼게요.",
    "챌린지 과제가 유익하고 도전적이었습니다. 다음 도전도 기대됩니다.",
    "이 도전을 성공적으로 마쳤습니다. 제 자신에게 칭찬합니다!",
    "도전 성공! 스스로에게도 자랑스럽습니다.",
    "도전을 마쳤습니다. 나 자신을 칭찬해요!",
    "이런 도전을 통해 더 나은 자신이 될 수 있었습니다.",
    "성공적으로 도전을 끝냈어요. 다음에도 잘할 수 있을 거라 믿어요."
];

// 테이블 데이터 생성 및 삽입
const insertData = () => {
    let entities = [];
    for (let i = 0; i < 1000; i++) {

        const createdAt = new Date();
        const lastModifiedAt = createdAt
        const userChallengeId = Math.floor(Math.random() * 5066813) + 1; //1~5066813
        const content = contentList[Math.floor(Math.random() * contentList.length)];
        const imageUrl = faker.image.url();
        const status = 'WAITING'
        entities.push([createdAt, lastModifiedAt, userChallengeId, content, imageUrl, status]);
    }

    const sql = 'INSERT IGNORE INTO challenge_verification (created_at, last_modified_at, user_challenge_id, content, image_url, status) VALUES ?';
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
