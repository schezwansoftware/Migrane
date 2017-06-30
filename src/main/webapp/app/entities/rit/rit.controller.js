(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('RitController', RitController);

    RitController.$inject = ['Rit'];

    function RitController(Rit) {

        var vm = this;

        vm.rits = [];

        loadAll();

        function loadAll() {
            Rit.query(function(result) {
                vm.rits = result;
                vm.searchQuery = null;
            });
        }
    }
})();
