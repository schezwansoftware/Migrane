(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('ArDialogController', ArDialogController);

    ArDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ar'];

    function ArDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Ar) {
        var vm = this;

        vm.ar = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ar.id !== null) {
                Ar.update(vm.ar, onSaveSuccess, onSaveError);
            } else {
                Ar.save(vm.ar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gApp:arUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
