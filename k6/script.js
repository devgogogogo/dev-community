import http from 'k6/http';
import { check, sleep, group } from 'k6';

export const options = {
    vus: 5,
    duration: '10s',
};

// 반드시 서비스 이름(app) 사용해야 함!
const BASE_URL = 'http://app:8080/api/v1/posts';

export default function () {

    group('Sanity Check: /all', () => {
        const res = http.get(`${BASE_URL}/all`);

        // 응답 상태와 HTML 여부 검사 로그 출력
        console.log('STATUS =', res.status);
        console.log('BODY START =', res.body.substring(0, 200));

        let jsonBody;

        try {
            jsonBody = res.json(); // JSON으로 파싱 시도
        } catch (e) {
            console.error('JSON PARSE ERROR:', e.message);
            // HTML이 오면 여기서 걸림 → 테스트 종료
            return;
        }

        check(res, {
            'status is 200': (r) => r.status === 200,
            'not empty list': () =>
                Array.isArray(jsonBody) && jsonBody.length > 0,
        });

        sleep(1);
    });
}
