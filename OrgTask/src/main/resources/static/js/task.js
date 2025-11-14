document.addEventListener('DOMContentLoaded', () => {
    const back = document.querySelector('.back-compact');
    if (!back) return;

    back.addEventListener('click', (e) => {
        if (document.referrer && new URL(document.referrer).origin === location.origin) {
            e.preventDefault();
            history.back();
        } else {
            back.setAttribute('href', '/meu-dia'); // fallback
        }
    });
});