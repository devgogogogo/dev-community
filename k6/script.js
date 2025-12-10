import http from 'k6/http';
import { check, sleep, group } from 'k6';
import { Trend } from 'k6/metrics';

// 그룹별 latency 기록용 커스텀 metric 생성
export const ALL_DURATION = new Trend('ALL_duration');
export const PAGE_DURATION = new Trend('PAGE_duration');

export const options = {
    vus: 500,             // 가상의 유저(Virtual Users) 500명 동시 실행
    duration: '20s',    // 테스트를 20초 동안 지속
};

const BASE_URL = 'http://app:8080/api/v1/posts';

export default function () {

    group('전체 조회', () => {
        const start = Date.now();
        const res = http.get(`${BASE_URL}/all`);
        const end = Date.now();

        ALL_DURATION.add(end - start); // <-- 측정 기록

        check(res, {
            'status is 200': (r) => r.status === 200,
            'not empty': (r) => r.body.length > 2,
        });

        sleep(1);
    });


    group('페이징 조회', () => {
        const start = Date.now();
        const res = http.get(`${BASE_URL}?page=0&size=15`);
        const end = Date.now();

        PAGE_DURATION.add(end - start); // <-- 측정 기록

        check(res, {
            'status is 200': (r) => r.status === 200,
            'has content': (r) => r.body.includes('"content"'),
        });

        sleep(1);
    });
}
