(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('EmpController', EmpController);

    EmpController.$inject = ['Emp'];

    function EmpController(Emp) {

        var vm = this;

        vm.emps = [];

        loadAll();

        function loadAll() {
            Emp.query(function(result) {
                vm.emps = result;
                vm.searchQuery = null;
            });
        }
    }
})();
