const mysql = require('mysql');

// MySQL 연결 설정
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'admin',
    password: 'your_password',
    database: 'zzansuni'
});

connection.connect();

const batchSize = 1000; // 한번에 처리할 레코드 수
let startId = 330000; // 시작 ID
const endId = 1472104; // 종료 ID

const runBatchInsert = (startId) => {
    return new Promise((resolve, reject) => {
        const sql = `
      INSERT IGNORE INTO challenge_group_user_exp (total_exp, challenge_group_id, user_id)
      SELECT 0 AS total_exp, c.challenge_group_id, uc.user_id 
      FROM user_challenge uc 
      INNER JOIN challenge c ON uc.challenge_id = c.id 
      WHERE uc.user_id >= ? AND uc.user_id < ?
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
