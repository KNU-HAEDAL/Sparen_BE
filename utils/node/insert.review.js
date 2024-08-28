import {fakerKO as faker} from "@faker-js/faker";
import mysql from 'mysql2';


/**
 * for i in {1..30}; do node insert.z.review.js; done
 */
// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'zzansuni'
});

const contentExamples = [
    "정말 유익한 자료였습니다. 감사합니다!",
    "해당 기능이 매우 유용하네요.",
    "더 나은 성능을 기대합니다.",
    "사용하기 간편하고 직관적입니다.",
    "기능이 잘 동작하지 않는 것 같습니다.",
    "이해하기 쉬운 설명 감사합니다.",
    "다른 사용자와의 협업이 수월해졌습니다.",
    "문서가 잘 정리되어 있어 좋습니다.",
    "이 부분이 좀 더 개선되면 좋겠습니다.",
    "설정이 복잡하지만 괜찮습니다.",
    "모든 기능이 기대 이상으로 작동합니다.",
    "버그 수정이 빨리 이루어지길 바랍니다.",
    "정확한 분석과 결과를 제공해줍니다.",
    "사용자 인터페이스가 직관적입니다.",
    "고객 지원이 매우 빠릅니다.",
    "이 기능 덕분에 많은 도움이 되었습니다.",
    "디자인이 더 깔끔했으면 좋겠습니다.",
    "기능이 다양하고 유용합니다.",
    "사용하기 쉽지만 몇 가지 개선이 필요합니다.",
    "문서화가 부족해서 이해하기 어려웠습니다.",
    "이해하기 쉬운 예제가 필요합니다.",
    "반복적인 작업이 줄어들어 좋습니다.",
    "기능이 정상적으로 작동하지 않습니다.",
    "데이터 처리 속도가 매우 빠릅니다.",
    "업데이트 후 성능이 향상되었습니다.",
    "가이드라인이 명확하지 않습니다.",
    "사용자 맞춤 설정이 가능하면 좋겠습니다.",
    "인터페이스가 너무 복잡합니다.",
    "좀 더 유연한 설정 옵션이 필요합니다.",
    "기능이 매우 유용하지만 조금 불편합니다.",
    "이 부분은 개선이 필요해 보입니다.",
    "사용자 경험이 매우 긍정적입니다.",
    "리뷰를 통해 개선점을 찾을 수 있었습니다.",
    "문서 내용이 유용했지만 보완이 필요합니다.",
    "정확한 피드백과 개선이 필요합니다.",
    "소프트웨어의 안정성이 뛰어납니다.",
    "몇 가지 기능이 불안정해 보입니다.",
    "전체적으로 만족스러운 결과를 얻었습니다.",
    "기능이 원활하게 작동하지 않습니다.",
    "디자인이 좀 더 현대적이면 좋겠습니다.",
    "사용자 맞춤형 옵션이 추가되면 좋겠습니다.",
    "속도와 성능 모두 만족스럽습니다.",
    "업데이트가 자주 이루어지길 바랍니다.",
    "기능이 불완전해 보입니다.",
    "고객 서비스가 훌륭합니다.",
    "기능이 잘못 작동합니다.",
    "소프트웨어의 안정성이 좋습니다.",
    "기능이 기대 이상으로 잘 동작합니다.",
    "매우 유용한 기능입니다.",
    "문서와 예제가 더 많으면 좋겠습니다.",
    "이해하기 쉬운 설명과 예제가 필요합니다.",
    "업데이트 후 문제가 해결되었습니다.",
    "기능 개선이 필요합니다.",
    "사용이 간편하고 만족스럽습니다.",
    "전체적인 경험이 긍정적입니다.",
    "개선이 필요한 부분이 몇 가지 있습니다."
];


// 테이블 데이터 생성 및 삽입
const insertData = () => {
    let entities = [];
    for (let i = 0; i < 1000; i++) {
        const rating = faker.number.int({ min: 1, max: 5 });
        const createdAt = new Date();
        const lastModifiedAt = createdAt;
        const userChallengeId = Math.floor(Math.random() * 5066813) + 1; //1~5066813
        const content = contentExamples[Math.floor(Math.random() * contentExamples.length)];

        entities.push([rating, createdAt, 0, lastModifiedAt, userChallengeId, content]); // challenge_group_id는 update 필요
    }

    const sql = 'INSERT IGNORE INTO challenge_review (rating, created_at, challenge_group_id, last_modified_at, user_challenge_id, content) VALUES ?';
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
