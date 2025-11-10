// login.
document.addEventListener("DOMContentLoaded", () => {
    const back = document.getElementById("backBtn");
    if (back) {
        back.addEventListener("click", (e) => {
            // se vier de uma página anterior do mesmo domínio, volta no histórico
            if (document.referrer && new URL(document.referrer).origin === location.origin) {
                e.preventDefault();
                history.back();
            }
        });
    }
});
