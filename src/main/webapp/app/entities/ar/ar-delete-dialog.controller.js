(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('ArDeleteController',ArDeleteController);

    ArDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ar'];

    function ArDeleteController($uibModalInstance, entity, Ar) {
        var vm = this;

        vm.ar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
