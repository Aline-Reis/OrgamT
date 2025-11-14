// /js/ui-buttons.js
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.back-compact,[data-action="back"]').forEach(btn => {
        btn.addEventListener('click', (e) => {
            if (document.referrer && new URL(document.referrer).origin === location.origin) {
                e.preventDefault();
                history.back();
            } else {
                const fallback = btn.getAttribute('data-fallback') || '/meu-dia';
                btn.setAttribute('href', fallback);
            }
        });
    });
});
