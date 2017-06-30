(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('EmpDialogController', EmpDialogController);

    EmpDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Emp'];

    function EmpDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Emp) {
        var vm = this;

        vm.emp = entity;
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
            if (vm.emp.id !== null) {
                Emp.update(vm.emp, onSaveSuccess, onSaveError);
            } else {
                Emp.save(vm.emp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gApp:empUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
