(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('HarshController', HarshController);

    HarshController.$inject = ['Harsh'];

    function HarshController(Harsh) {

        var vm = this;

        vm.harshes = [];

        loadAll();

        function loadAll() {
            Harsh.query(function(result) {
                vm.harshes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
