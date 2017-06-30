(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('RitDialogController', RitDialogController);

    RitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Rit'];

    function RitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Rit) {
        var vm = this;

        vm.rit = entity;
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
            if (vm.rit.id !== null) {
                Rit.update(vm.rit, onSaveSuccess, onSaveError);
            } else {
                Rit.save(vm.rit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gApp:ritUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
