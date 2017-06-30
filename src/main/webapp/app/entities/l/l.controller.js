(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('LController', LController);

    LController.$inject = ['L'];

    function LController(L) {

        var vm = this;

        vm.ls = [];

        loadAll();

        function loadAll() {
            L.query(function(result) {
                vm.ls = result;
                vm.searchQuery = null;
            });
        }
    }
})();
