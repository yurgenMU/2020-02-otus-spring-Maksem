document.addEventListener("DOMContentLoaded", function () {

    (function () {
        var rootNodeElements = document.getElementsByClassName("library-vue-root");
        if (rootNodeElements.length === 0) {
            return;
        }
        if (rootNodeElements.length > 1) {
            console.error("received more than one root node element");
            return;
        }
        function initializeRoot(element) {
            return new Vue({
                el: element,
                data: function () {
                    return {
                        selectedGenres: "",
                        selectedAuthor: ""
                    }
                }
            })
        }

        initializeRoot(rootNodeElements[0]);
    }());
});
