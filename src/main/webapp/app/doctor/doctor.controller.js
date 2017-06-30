(function() {
    'use strict';

    angular
        .module('gApp')
        .controller('DoctorController', DoctorController);

    DoctorController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function DoctorController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
