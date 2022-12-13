# Calculadora-web-socket
Calculadora-web-socket é um projeto implementado na disciplina Sistemas Distribuídos.
Trata-se, portanto, de uma calculadora que utiliza Webservice e Socket, onde é possível realizar as quatro operações básicas: soma, subtração, multiplicação e divisão 
e operações avançadas de potenciação, raiz quadrada e porcentagem.

## Estrutura dos arquivos
  .
  ├───src
  │   ├───main
  │   │   ├───java
  │   │   │   │   AdvancedCalculator.java
  │   │   │   │   AdvancedServer.java
  │   │   │   │   AdvancedServerConnection.java
  │   │   │   │   Api.java
  │   │   │   │   BasicCalculator.java
  │   │   │   │   BasicServer.java
  │   │   │   │   BasicServerConnection.java
  │   │   │   │   Calculate.java
  │   │   │   │   Utils.java
  │   │   │   │
  │   │   │   └───org
  │   │   │       └───json
  │   │   │
  │   │   └───resources
  │   │       ├───public
  │   │       │   ├───css
  │   │       │   │       styles.css
  │   │       │   │
  │   │       │   └───js
  │   │       │       │   app.js
  │   │       │       │
  │   │       │       └───vendor
  │   │       │               intercooler-0.9.6.min.js
  │   │       │               jquery-1.12.4.min.js
  │   │       │
  │   │       └───velocity
  │   │               editTodo.vm
  │   │               index.vm
  │   │               todoList.vm


##  Instruções de Uso
1. Baixar o projeto
2. Abrir o projeto em uma IDE
3. Acessar os arquivos \*.java do diretório *./src*
4. Execultar os servidores **BasicServer.java** e **AdvancedServer.java**. Em seguida deve-se execultar o cliente **Api.java**.

### Layout
As operações devem ser fornecidas por meio de um simulador de APIs (por exemplo, Postman).

### Protocolo
O protocolo para utilização na rota é o método **GET**.

1. Campo de envio das expressões:  **Body**
2. Formato: JSON

### Rota
O nome da rota deve ser:
~~~
  localhost:8081/calculadora
~~~
### Padrão para as expressões
~~~
exemplo 1:
{
  "expressao": "2+5"
}
~~~
~~~
exemplo 2:
{
  "expressao": "raiz(16)"
}
~~~

~~~
exemplo 3:
{
  "expressao": "2*10%"
}
~~~

~~~
exemplo 4: expressões agrupadas
{
  "expressao": "(2+5)*3/10+raiz(64)*15%"
}
~~~
### Resultado
Após o envio, o resultado  é disponibilizado na tela em número de formato decimal (float/double).
~~~
==========RESULTADO==============
3.3
~~~

O campo *Expressão* permite qualquer operação dentre as quatro básicas (soma, subtração, multiplicação, divisão) e
operações com potência, raiz quadrada e percentual.

## Entradas permitidas

| Tipo |         Descrição         |Restrição |
| :---         |:-------------------------:| :---: |
| Números      | Integer, Float ou Double. | Para Floats e Double, o separador decimal deve ser o ponto. Ex.: 20.5, 10.87, 10587.12    |
| Operadores   |        + - * / ^ %        |     |



O campo *Expressão* permite operações simples e compostas:
* Operações simples. Exemplos:
  * Soma
    ~~~
    Ex.: 10+20
    ~~~
  * Subtração
    ~~~
    Ex.: 10-20
    ~~~
  * Multiplicação
    ~~~
    Ex.: 10*20
    ~~~
  * Divisão
    ~~~
    Ex.: 10/20
    ~~~
  * Potenciação
      ~~~
      Ex.: 2^3
      ~~~
  * Raiz quadrada
      ~~~
      Ex.: raiz(16)
      ~~~
  * Porcentagem
      ~~~
      Ex.: 2*10%
      ~~~
  
* Operações compostas. Exemplos:
    ~~~
    Ex.: 10+20*(2-5)/(4*8+2)*10% + raiz(45)
    ~~~
