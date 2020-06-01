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
        chosenAuthor: {
            type: String,
            default: '{}'
        },
        allAuthors: String
    },

    mounted: function () {
        var author = JSON.parse(this.chosenAuthor);
        this.selectedAuthor = author;
        if (author.name) {
            this.searchTerm = author.name;
        }
        this.setAuthorToRoot();
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
            if (this.selectedAuthor.name) {
                this.searchTerm = this.selectedAuthor.name;
            }
            this.showPopup = true;
        },

        closePopup: function () {
            this.showPopup = false;
        }

    }
});