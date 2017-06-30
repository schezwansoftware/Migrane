(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('ArController', ArController);

    ArController.$inject = ['Ar'];

    function ArController(Ar) {

        var vm = this;

        vm.ars = [];

        loadAll();

        function loadAll() {
            Ar.query(function(result) {
                vm.ars = result;
                vm.searchQuery = null;
            });
        }
    }
})();
