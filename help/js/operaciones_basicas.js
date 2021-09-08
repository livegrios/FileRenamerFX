function realizarOperacion(oper)
{
    // Leemos los valores de las cajas de texto:
    var n1 = parseFloat($('#txtN1').val());
    var n2 = parseFloat($('#txtN2').val());
    
    // Realizamos la operacion correspondiente:
    switch(oper)
    {
        case 1:
            sumar(n1, n2);
            break;        
        case 2 :
            restar(n1, n2);
            break;
        case 3 :
            multiplicar(n1, n2);
            break;
        case 4 :
            dividir(n1, n2);
            break;
    }
}
function sumar(n1, n2)
{    
    $.ajax( {
                type :  "GET",
                url  :  "api/ca/sumar",
                data :  {
                            x : n1,
                            y : n2
                        }
            })
     .done( function(data){
                // Mostramos el resultado 
                // de la operación:
                $('#txtResultado').val(data.result);
            });
}


