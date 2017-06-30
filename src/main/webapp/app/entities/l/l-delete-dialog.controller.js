(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('LDeleteController',LDeleteController);

    LDeleteController.$inject = ['$uibModalInstance', 'entity', 'L'];

    function LDeleteController($uibModalInstance, entity, L) {
        var vm = this;

        vm.l = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            L.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
