(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('LDialogController', LDialogController);

    LDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'L'];

    function LDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, L) {
        var vm = this;

        vm.l = entity;
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
            if (vm.l.id !== null) {
                L.update(vm.l, onSaveSuccess, onSaveError);
            } else {
                L.save(vm.l, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gApp:lUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
