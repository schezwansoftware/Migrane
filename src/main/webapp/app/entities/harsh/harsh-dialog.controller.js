(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('HarshDialogController', HarshDialogController);

    HarshDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Harsh'];

    function HarshDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Harsh) {
        var vm = this;

        vm.harsh = entity;
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
            if (vm.harsh.id !== null) {
                Harsh.update(vm.harsh, onSaveSuccess, onSaveError);
            } else {
                Harsh.save(vm.harsh, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('gApp:harshUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
