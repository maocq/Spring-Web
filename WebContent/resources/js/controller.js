angular.module("MyApp", [])
    .controller("IndexController", function($scope, $http){
        $scope.usuarios = [];
        $scope.newUsuario = {};

        $http.get("/spring-web/hql")
            .success(function(data){
                $scope.usuarios = data;
            })
            .error(function(err){
                console.log(err);
            });

        $scope.addUsuario = function(){
            $http.post("/spring-web/usuario", {
              name: $scope.newUsuario.name,
              email: $scope.newUsuario.email
            })
            .success(function(data){
                if (data.hasOwnProperty('error')) {
                    var errorString = '';
                    for (var k in data.errores){
                        errorString += k + ': ' + data.errores[k] +'\n';
                    }
                    alert(errorString);
                    return;
                }

                $scope.usuarios.push(data);
                $scope.newUsuario = {};
            })
            .error(function(err){
                alert("Error");
            });
          }
    });