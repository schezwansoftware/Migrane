(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('RitDeleteController',RitDeleteController);

    RitDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rit'];

    function RitDeleteController($uibModalInstance, entity, Rit) {
        var vm = this;

        vm.rit = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Rit.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
