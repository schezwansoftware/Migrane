(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('HarshDeleteController',HarshDeleteController);

    HarshDeleteController.$inject = ['$uibModalInstance', 'entity', 'Harsh'];

    function HarshDeleteController($uibModalInstance, entity, Harsh) {
        var vm = this;

        vm.harsh = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Harsh.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
