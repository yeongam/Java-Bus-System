// ===========================
// 버스 시간표 - 프론트엔드 스크립트
// TODO: 기능 요구사항에 맞게 구현 필요
// ===========================

/**
 * 노선 검색
 * TODO: 서버 API 연동 또는 클라이언트 필터링 구현
 */
function search() {
    const keyword = document.getElementById('searchInput')?.value.trim();
    if (!keyword) return;

    // TODO: fetch(`/api/routes?q=${encodeURIComponent(keyword)}`)
    //   .then(res => res.json())
    //   .then(data => renderRoutes(data));

    console.log('검색어:', keyword); // 임시
}

/**
 * 엔터키로 검색 실행
 */
document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('searchInput');
    if (input) {
        input.addEventListener('keydown', (e) => {
            if (e.key === 'Enter') search();
        });
    }
});
