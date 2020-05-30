Vue.component("EditGenresComponent", {
    template: "#edit-genres-template",

    data: function () {
        return {
            selectedGenres: [],
            unselectedGenres: [],
            allGenres: []
        }
    },

    props: {
        chosenGenres: String,
        allGenres: String
    },

    mounted: function () {
        var that = this;
        var groupedGenres = this.groupingBy(this.wrappedGenres, genre =>
            JSON.parse(that.chosenGenres).some(chosenGenre =>
                chosenGenre.id === genre.origin.id
            ) ? 'chosen' : 'available'
        );

        this.unselectedGenres = groupedGenres.available || [];
        this.selectedGenres = groupedGenres.chosen || [];
        this.setGenresToRoot();
    },

    computed: {

        wrappedGenres: function () {
            return JSON.parse(this.allGenres).map(function (service) {
                return {selected: false, origin: service};
            });
        },
    },

    methods: {
        addToSelected: function () {
            var splitItems = this.groupingBy(this.unselectedGenres, el =>
                el.selected ? 'selected' : 'not selected');

            var selectedItems = splitItems['selected'] || [];
            this.unselectedGenres = splitItems['not selected'] || [];
            this.moveElements(selectedItems, this.selectedGenres);
            this.setGenresToRoot();
        },

        removeFromSelected: function () {
            var splitItems = this.groupingBy(this.selectedGenres, el =>
                el.selected ? 'selected' : 'not selected');

            var selectedItems = splitItems['selected'] || [];
            this.selectedGenres = splitItems['not selected'] || [];
            this.moveElements(selectedItems, this.unselectedGenres);
            this.setGenresToRoot();
        },

        moveElements: function (source, target) {
            for (var i = 0; i < source.length; i++) {
                var element = source[i];
                if (element.selected) {
                    source.splice(i, 1);
                    element.selected = false;
                    target.push(element);
                    i--;
                }
            }
            this.sort(target);
        },


        unselectAll: function () {
            this.unselectedGenres.map(service =>
                service.selected = false
            );
            this.selectedGenres.map(service =>
                service.selected = false
            )
        },

        sort: function (target) {
            target.sort((first, second) =>
                first.name - second.name
            );
        },

        groupingBy: function (array, keySupplier) {
            var result = {};
            for (var i = 0; i < array.length; i++) {
                var el = array[i];
                var key = keySupplier(el);
                if (!result[key]) {
                    result[key] = [el];
                    continue;
                }
                result[key].push(el);
            }
            return result;
        },

        setGenresToRoot: function () {
            this.$root.selectedGenres = this.selectedGenres.map(genre => genre.origin.id).join(",");
        }
    }
});