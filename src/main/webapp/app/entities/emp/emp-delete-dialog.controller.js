(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('EmpDeleteController',EmpDeleteController);

    EmpDeleteController.$inject = ['$uibModalInstance', 'entity', 'Emp'];

    function EmpDeleteController($uibModalInstance, entity, Emp) {
        var vm = this;

        vm.emp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Emp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
