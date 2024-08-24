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
let startId = 200_000; // 시작 ID
const endId = 2_000_000; // 종료 ID

const runBatchInsert = (startId) => {
    return new Promise((resolve, reject) => {
        const sql = `
        UPDATE challenge_review cr JOIN user_challenge uc 
        ON cr.user_challenge_id = uc.id JOIN challenge c ON uc.challenge_id = c.id 
            SET cr.challenge_group_id = c.challenge_group_id WHERE cr.id > ? and cr.id<= ? ;
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
