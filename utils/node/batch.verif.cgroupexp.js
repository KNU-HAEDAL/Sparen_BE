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
const endId = 6099783; // 종료 ID

/**
 * 챌린지 require_count보다 더 많은 인증을 받은 경우의 정합성을 위해 삭제 처리
 */
const runBatchInsert = (startId) => {
    return new Promise((resolve, reject) => {
        const sql = `
            UPDATE challenge_group_user_exp cgue
                JOIN (
                SELECT
                uc.user_id,
                c.challenge_group_id,
                SUM(c.once_exp) AS total_exp
                FROM challenge_verification cv
                JOIN user_challenge uc ON cv.user_challenge_id = uc.id
                JOIN challenge c ON uc.challenge_id = c.id
                WHERE cv.id > ? AND cv.id <= ?
                GROUP BY uc.user_id, c.challenge_group_id
                ) AS stats
            ON cgue.user_id = stats.user_id AND cgue.challenge_group_id = stats.challenge_group_id
                SET cgue.total_exp = cgue.total_exp + stats.total_exp;
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
