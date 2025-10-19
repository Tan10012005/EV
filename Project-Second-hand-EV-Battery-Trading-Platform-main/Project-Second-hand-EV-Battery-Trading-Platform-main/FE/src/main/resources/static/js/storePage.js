window.addEventListener("DOMContentLoaded", () => {
    const productLists = document.querySelectorAll(".product-list");
    const tabs = document.querySelectorAll(".tabs__item");
    console.log(tabs, productLists);

    tabs.forEach((tab, index) => {
        tab.addEventListener("click", () => {
            productLists.forEach((pl) => pl.classList.remove("active"));
            tabs.forEach((t) => t.classList.remove("tabs__item--active"));
            tab.classList.add("tabs__item--active");
            productLists[index].classList.add("active");
        });
    });
});
