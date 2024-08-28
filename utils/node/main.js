import {GoogleGenerativeAI} from "@google/generative-ai";

const GEMINI_API_KEY = '';
const jwt = '';

const genAI = new GoogleGenerativeAI(GEMINI_API_KEY);




function getPrompt(str, challenge) {
    return `
        
        I want to make a challenge API request ${str}
        Challenge for people to improve their lifestyle.
        
        ${challenge}
       
       
        category is [ HEALTH, ECHO, SHARE, VOLUNTEER, ETC ]
        dayType is [ DAY, MONTH, WEEK ]
        
        Using this JSON Schema:
        Resp = {
          "title": "string",
          "content": "string",
          "guide": "string",
          "category": "string",
          "challenges": [
            {
              "startDate": "2024-08-17",
              "endDate": "2024-08-17",
              "dayType": "DAY",
              "requiredCount": 0,
              "onceExp": 0,
              "successExp": 0,
              "difficulty": 0
            }
          ]
        }
        Return a 'Resp' object for the challenge.
        챌린지는 적당히 만들어줘, 경험치는 1~100 사이로, 난이도는 1~5 사이로, 시작일과 종료일은 오늘로부터 적당히 맞춰서 1년이나 한달
        챌린지리스트는 4개정도 만들어줘
        `;
}

const challengePromptEx = [
    '1.체중 관리 및 피트니스\n    - 체중 감량, 근육 강화, 유연성 향상 등의 목표를 중심으로 한 챌린지.', '' +
    ' 2. 정신 건강 및 웰빙\n        - 스트레스 관리, 명상, 정신적 안정을 위한 활동을 주제로 한 챌린지.\n\n  ',
    '3. 영양 및 식습관 개선\n            - 건강한 식단, 영양 섭취, 클린 이팅(clean eating)을 목표로 하는 챌린지.\n\n',
    '  4. 수면 개선\n            - 수면의 질 향상, 규칙적인 수면 패턴을 형성하는 것을 목표로 한 챌린지.\n        \n   ',
    '5. 습관 형성\n            - 새로운 건강한 습관을 만들고 지속시키는 것을 중심으로 한 챌린지.\n        \n      ',
    '  6.생활 습관 개선\n        -  일상 속에서 작은 생활 습관을 변화시켜 더 건강한 라이프스타일을 만드는 챌린지.\n        \n   ' ,
    '7. 신체 활동 증가\n            - 일상에서의 운동량을 늘리는 것을 목표로 한 챌린지.\n        \n      ' +
    '  8. 디지털 디톡스\n            - 스마트폰, 컴퓨터 사용을 줄이고 디지털 기기에서 벗어나 더 건강한 생활을 유도하는 챌린지.\n  ',
    '9. 자기 관리 및 셀프케어\n            - 전반적인 자기 관리(스킨케어, 피로 회복 등)를 중심으로 한 챌린지.\n',
    '10. 마음 챙김 및 긍정적 사고\n            - 마음 챙김, 긍정적 사고, 감사의 실천을 중심으로 한 챌린지'
]

async function generate(req) {


    const model = genAI.getGenerativeModel(
        {
            model: "gemini-1.5-flash",
            generationConfig: {
                responseMimeType: "application/json",
            }
        }
    );

    for (let i = 0; i < 100; i++) {

        const challenge = challengePromptEx[Math.floor(Math.random() * challengePromptEx.length)];
        const prompt = getPrompt(req,challenge);
        const result = await model.generateContent(prompt);
        const txt = result.response.text();
        const json = JSON.parse(txt);
        console.log(json);

        const res = await fetch('https://api.reditus.site/api/admin/challengeGroups', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwt}`,
            },
            body: JSON.stringify(json)
        })
        const resJson = await res.json();
        console.log(resJson);
    }
}

generate('to create a challenge for dummy data for KOREAN language');