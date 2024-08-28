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
const endId = 1472103; // 종료 ID

/**
 * 챌린지 require_count보다 더 많은 인증을 받은 경우의 정합성을 위해 삭제 처리
 */
const runBatchInsert = (startId) => {
    return new Promise((resolve, reject) => {
        const sql = `
            UPDATE users u
                JOIN (
                SELECT
                user_id,
                SUM(total_exp) AS sum_exp
                FROM
                challenge_group_user_exp
                WHERE user_id >= ? AND user_id < ?
                GROUP BY
                user_id
                ) AS exp_sum
            ON u.id = exp_sum.user_id
                SET u.exp = exp_sum.sum_exp;
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
