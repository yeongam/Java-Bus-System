// ===========================
// 버스 시간표 - 프론트엔드 스크립트
// ===========================

/**
 * 노선 검색 (클라이언트 필터링)
 */
function search() {
    const keyword = document.getElementById('searchInput')?.value.trim().toLowerCase();
    const cards   = document.querySelectorAll('.route-card');
    let visible   = 0;

    cards.forEach(card => {
        const text = card.textContent.toLowerCase();
        const show = !keyword || text.includes(keyword);
        card.style.display = show ? '' : 'none';
        if (show) visible++;
    });

    const noResult = document.getElementById('noResultMsg');
    if (noResult) noResult.style.display = visible === 0 ? '' : 'none';
}

/**
 * 엔터키로 검색 실행, 빈 칸이면 전체 복원
 */
document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('searchInput');
    if (input) {
        input.addEventListener('keydown', e => { if (e.key === 'Enter') search(); });
        input.addEventListener('input',   () => { if (!input.value) search(); });
    }
});
