Vue.component("EditAuthorComponent", {
    template: "#edit-author-template",

    data: function () {
        return {
            selectedAuthor: {},
            searchTerm: "",
            availableAuthors: [],
            showPopup: false,
        }
    },

    props: {
        chosenAuthor: String,
        allAuthors: String
    },

    mounted: function () {
        var author = JSON.parse(this.chosenAuthor);
        this.selectedAuthor = author;
        this.searchTerm = author.name;
        this.setAuthorToRoot();
    },

    directives: {
        'click-outside': {
            priority: 700,
            bind(el, binding, vNode) {
                if (typeof binding.value !== 'function') {
                    var compName = vNode.context.name;
                    var warn = '[Vue-click-outside:] provided expression ' + binding.expression + ' is not a function, but has to be';
                    if (compName) {
                        warn += 'Found in component ' + compName;
                    }
                    console.warn(warn);
                }
                var bubble = binding.modifiers.bubble;
                var handler = function (e) {
                    if (bubble || (!el.contains(e.target) && el !== e.target)) {
                        binding.value(e);
                    }
                };
                el.__vueClickOutside__ = handler;
                document.addEventListener('click', handler);
            },

            unbind() {
                document.removeEventListener('click', el.__vueClickOutside__);
                el.__vueClickOutside__ = null;
            },

            stopProp(event) {
                event.stopPropagation()
            }
        }
    },


    computed: {

        wrappedAuthors: function () {
            return JSON.parse(this.allAuthors);
        },

        filteredAuthors: function () {
            var term = this.searchTerm.toLowerCase();
            if (!term || term.length === 0) {
                return this.wrappedAuthors;
            }
            return this.wrappedAuthors.filter(author =>
                author.name.toLowerCase().includes(term)
            );

        },

        // wrappedGenres: function () {
        //     return JSON.parse(this.allGenres).map(function (service) {
        //         return {selected: false, origin: service};
        //     });
        // },
    },

    methods: {
        chooseAuthor: function (author) {
            this.selectedAuthor = author;
            this.setAuthorToRoot();
            this.closePopup();
            this.searchTerm = this.selectedAuthor.name;
        },

        setAuthorToRoot: function () {
            this.$root.selectedAuthor = this.selectedAuthor.id + "";
        },

        startEditing: function () {
            this.searchTerm = this.selectedAuthor.name;
            this.showPopup = true;
        },

        closePopup: function () {
            this.showPopup = false;
        }

    }
});