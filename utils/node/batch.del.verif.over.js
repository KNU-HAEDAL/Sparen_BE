import mysql from 'mysql2';

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'admin',
    password: 'your_password',
    database: 'zzansuni'
});

connection.connect();

const batchSize = 1000; // 한번에 처리할 레코드 수
let startId = 0; // 시작 ID
const endId = 5066814; // 종료 ID

/**
 * 챌린지 require_count보다 더 많은 인증을 받은 경우의 정합성을 위해 삭제 처리
 */
const runBatchInsert = (startId) => {
    return new Promise((resolve, reject) => {
        const sql = `
            DELETE cv
            FROM challenge_verification cv
            JOIN (
                SELECT cv.id
                FROM challenge_verification cv
                JOIN user_challenge uc ON cv.user_challenge_id = uc.id
                JOIN challenge c ON uc.challenge_id = c.id
                WHERE uc.id > ? AND uc.id <= ?
                GROUP BY uc.id, c.required_count
                HAVING COUNT(cv.id) > c.required_count
            ) AS to_delete ON cv.id = to_delete.id;

        `;

        connection.query(sql, [startId, startId + batchSize], (err, results) => {
            if (err) {
                reject(err);
            } else {
                console.log(`Processed records from ${startId} to ${startId + batchSize}`);
                resolve(results);
            }
        });
    });
};

// 배치 처리 실행
const processBatches = async () => {
    try {
        while (startId < endId) {
            await runBatchInsert(startId);
            startId += batchSize;
        }
        console.log('All batches processed successfully.');
    } catch (err) {
        console.error('Error processing batch:', err);
    } finally {
        connection.end();
    }
};

processBatches();
