import java.io.File
//Declaração das variáveis que serão utilizadas pelas funções listadas acima:
//Quando testamos o programa, ele crashou devido ao tamanho dos arrays. Então dobramos o seu tamanho para não ter esse problema novamente.
val reservasNomeDoCliente = arrayOfNulls<String>(200)
val reservasIdadeDoCliente = arrayOfNulls<Int>(200)
val reservasNomeDoVoo = arrayOfNulls<String>(200)
val reservasDiaDoVoo = arrayOfNulls<Int>(200)
val reservasNumCartaoCidadao = arrayOfNulls<String>(200)
val vooDestinoDoVoo = arrayOfNulls<String>(20)
val vooPartidaDoVoo = arrayOfNulls<String>(20)
val vooNomeDoVoo = arrayOfNulls<String>(20)
val vooDiaDoVoo = arrayOfNulls<Int>(20)
//Aplicação da função lerFicheiro:
val voos = lerFicheiro("voos.txt", vooNomeDoVoo, vooPartidaDoVoo, vooDestinoDoVoo, vooDiaDoVoo)
var reservas = lerFicheiro("reservas.txt", reservasNumCartaoCidadao, reservasNomeDoCliente, reservasNomeDoVoo, reservasDiaDoVoo)
//Função criada para não existir duplicação de código ao ler os dois ficheiros.
fun lerFicheiro(ficheiro: String, parte1doArray: Array<String?>, parte2doArray: Array<String?>, parte3doArray: Array<String?>, parte4doArray: Array<Int?>): Int{
    val lines = File(ficheiro).readLines()
    var posDoArray = 0
    for (line in lines) {
        val parts = line.split(':')
        parte1doArray[posDoArray] = parts[0]
        parte2doArray[posDoArray] = parts[1]
        parte3doArray[posDoArray] = parts[2]
        parte4doArray[posDoArray] = parts[3].toInt()
        posDoArray++
    }
    if (ficheiro=="voos.txt"){
        println("Ficheiro de voos lido com sucesso")
        println()
    }
    //Precisaremos do valor de linhas lidas mais tarde, então:
    return posDoArray

}

//Função da opção 1:
fun listarPassageirosDoVoo () {
    println("Insira o voo que deseja a listagem:")
    val userVooDesejado = readLine()!!.toString()
    var posProcuraVoo = 0
    while (posProcuraVoo < voos) {
        if (userVooDesejado == vooNomeDoVoo[posProcuraVoo]) {
            break
        }
        posProcuraVoo++
    }
    //Se o voo não existir, mensagem de erro:
    if (posProcuraVoo == voos) {
        println("O voo introduzido não existe.")
        return
    }
    //Se o nome do voo introduzido existir, este é o próximo passo:
    println("Insira o dia que o voo deve partir:")
    val userDiaDesejado = readLine()!!.toInt()
    posProcuraVoo = 0
    while (posProcuraVoo < voos) {
        //Aqui, a função compara o voo encontrado com o dia que ocorrerá para que o programa não se perca na contagem:
        if (userVooDesejado == vooNomeDoVoo[posProcuraVoo] && userDiaDesejado == vooDiaDoVoo[posProcuraVoo]) {
            break
        }
        posProcuraVoo++
    }
    //Se o dia do voo não coincidir com o voo introduzido, mensagem de erro:
    if (posProcuraVoo == voos) {
        println("O voo escolhido não ocorre nesse dia.")
        return
    }
    //Else, lista os passageiros:
    println("Lista de passageiros do voo $userVooDesejado no dia $userDiaDesejado, de ${vooPartidaDoVoo[posProcuraVoo]} a " +
            "${vooDestinoDoVoo[posProcuraVoo]}:")
    for (posProcuraReservas in 0..(reservas - 1)) {
        if (reservasNomeDoVoo[posProcuraReservas] == userVooDesejado && reservasDiaDoVoo[posProcuraReservas] == userDiaDesejado ) {
                print("${reservasNomeDoCliente[posProcuraReservas]!!.toUpperCase()}\n") //.toUpperCase() foi usado para embelezar o output.

            }
        }
    }


    //Função para a opção 2:
    fun numeroPassageirosPeriodoDia() {
        println("Insira o dia em que deseja iniciar a contagem:")
        val userDiaContagemInicio = readLine()!!.toInt()
        if (userDiaContagemInicio < 1 || userDiaContagemInicio > 31) {
            println("Por favor, insira um dia válido.")
            return
        }
        println("Insira o dia em que deseja finalizar a contagem:")
        val userDiaContagemFim = readLine()!!.toInt()
        if (userDiaContagemFim < 1 || userDiaContagemFim > 31 || userDiaContagemFim < userDiaContagemInicio) {
            println("O período de dias inserido não é válido.")
            return
        }
        //Ao testarmos o código, notamos que funcionava, mas os dias estavam todos embaralhados. Para que o output fosse organizado, este loop imprime o dia em que estamos primeiro:
        for (contadorDias in userDiaContagemInicio..userDiaContagemFim) {
            //Para o caso de não existirem voos num dia, essa variável evitará que a linha não tenha nada escrito nela.
            var naoTemVoo = true
            print("Dia $contadorDias: ")
            //Loop pelos voos.
            for (posVoo in 0..(voos - 1)) {
                //Checamos se o voo da posição que estamos está entre esse período de dias:
                if (vooDiaDoVoo[posVoo]!! == contadorDias) {
                    naoTemVoo = false
                    //Está, então agora fazemos um loop pelas reservas e contamos cada uma que tem o nome e dia do voo da posição que estamos.
                    var totalPassageirosNoVoo = 0
                    for (posReserva in 0..(reservas - 1)) {
                        //Checamos se as informações coincidem:
                        if (reservasNomeDoVoo[posReserva] == vooNomeDoVoo[posVoo] && reservasDiaDoVoo[posReserva] == vooDiaDoVoo[posVoo]) {
                            //Sim, então é mais um passageiro para aquele voo.
                            totalPassageirosNoVoo++
                        }
                    }
                    //Aqui, totalPassageirosNoVoo já é equivalente a quantidade de pessoas no voo atual, então podemos listar as informações:
                    print("Voo ${vooNomeDoVoo[posVoo]} - $totalPassageirosNoVoo passageiros.  ")
                }
            }
            if (naoTemVoo) {
                print("Não ocorrem voos neste dia.")
            }
            println("")
        }
    }

    //Função para a opção 3:
    fun escritaEmFicheiro() {
        println("Obrigado por fazer sua reserva. Aguarde enquanto processamos seu pedido.")
        val writer = File("reservas.txt").bufferedWriter()

        for (posicao in 0..(reservas - 1)) {
            writer.write("${reservasNumCartaoCidadao[posicao]}:${reservasNomeDoCliente[posicao]}:${reservasNomeDoVoo[posicao]}:${reservasDiaDoVoo[posicao]}")
            if (posicao < (reservas - 1)) {
                writer.newLine()
            }
        }

        writer.close()
        println("Pronto! Obrigado por escolher Ja Fui! Tenha um bom voo.")
        }

    //Funções para a opção 4:
//Esta função cria um array (nomesUnicosVoos) que contém cada nome do voo uma só vez. Será usada para facilitar o funcionamento da função seguinte.
    fun separadorNomesVoo(): Array<String?> {
        val nomesUnicosVoos = arrayOfNulls<String>(20)
        //Quantidade de nomes únicos:
        var nomesUnicosVoosCount = 0
        //Fazer um loop pelos nomes dos voos:
        for (nomeDoVoo in vooNomeDoVoo) {
            //Precisamos dessa variável para comparar o nome do voo atual com os que já temos no array:
            var testeNomesUnicos = 0
            //Fazemos um loop por todos os nomes que já temos no nosso array. Se o nome do voo atual já estiver nele, o loop acaba e esse nome não é lido novamente.
            while (testeNomesUnicos < nomesUnicosVoosCount) {
                if (nomeDoVoo == nomesUnicosVoos[testeNomesUnicos]) {
                    break
                }
                testeNomesUnicos++
            }
            //Depois, quando o último nome único for registrado, temos o array concluído e retornamos seu valor atual.
            if (testeNomesUnicos == nomesUnicosVoosCount) {
                nomesUnicosVoos[nomesUnicosVoosCount] = nomeDoVoo
                nomesUnicosVoosCount++
            }
        }
        return nomesUnicosVoos
    }

    fun analiseEconomica() {
        val nomesUnicosVoos = separadorNomesVoo()
        for (nomeVoo in nomesUnicosVoos) {
            if (nomeVoo == null) {
                break
            }
            var numPassageiros = 0
            print("$nomeVoo : ")
            for (reservasNomeAnalise in reservasNomeDoVoo) {
                if (reservasNomeAnalise == nomeVoo) {
                    print("#")
                    numPassageiros++
                    if (numPassageiros >= 30) {
                        break
                    }
                }
            }
            println("")
        }
    }

    //Funções para a opção 5:
//Essa função serve para contar a quantidade de passageiros no voo, para evitar duplicação de código mais tarde:
    fun contadoNumeroDePassageirosNoVoo(vooNomeDoVoo: String?, vooDiaDoVoo: Int): Int {
        var lotacao = 0
        for (reservaPos in 0..(reservas - 1)) {
            if (reservasDiaDoVoo[reservaPos] == vooDiaDoVoo && reservasNomeDoVoo[reservaPos] == vooNomeDoVoo) {
                lotacao++
            }
        }
        return lotacao
    }

    //Esta função é o mecanismo de sugestão para o case do voo desejado não estar disponível:
    fun sugerirVoos(userReservaPartida: String, userReservaDestino: String, userReservaDia: Int) {
        var naoTemSugestao = true
        for (posReservaVoos in 0..(voos - 1)) {
            if (userReservaPartida == vooPartidaDoVoo[posReservaVoos] && userReservaDestino == vooDestinoDoVoo[posReservaVoos] && vooDiaDoVoo[posReservaVoos]!! >= userReservaDia - 4 && vooDiaDoVoo[posReservaVoos]!! <= userReservaDia + 4) {
                naoTemSugestao = false
                println("${vooNomeDoVoo[posReservaVoos]} : ${vooPartidaDoVoo[posReservaVoos]} - ${vooDestinoDoVoo[posReservaVoos]}, DIA ${vooDiaDoVoo[posReservaVoos]} - Lotação: ${contadoNumeroDePassageirosNoVoo(vooNomeDoVoo[posReservaVoos], vooDiaDoVoo[posReservaVoos]!!)}/20")
            }
        }
        if (naoTemSugestao) {
            println("Não estão disponíveis outros voos com as especificações indicadas nos em dias próximos a este. Nos desculpamos pela inconvenicência.")
        }
        return
    }

    fun reservaDeUmVoo() {
        //Primeiramente, checamos se o número máximo de reservas já foi efetuado:
        if (reservas >= 200) {
            // Se sim:
            println("O número máximo de reservas foi alcançado.")
            return
        }
        //Se não, continuamos o programa.
        //Pedir o local de origem e partida ao utlizador:
        println("Insira o local de partida do voo que deseja:")
        val userReservaPartida = readLine()!!
        println("Insira o destino do voo que deseja:")
        val userReservaDestino = readLine()!!
        //Loop para checar se existem voos com o local e destino introduzidos:
        var posReservaVoos = 0
        while (posReservaVoos < voos) {
            if (userReservaPartida == vooPartidaDoVoo[posReservaVoos] && userReservaDestino == vooDestinoDoVoo[posReservaVoos]) {
                break
            }
            posReservaVoos++
        }
        if (posReservaVoos == voos) {
            println("Não existem voos que se encaixem nas condições desejadas.")
            return
        }
        //Existe pelo menos um voo com esta origem e este destino, então pergunta-se o dia que o usuário deseja voar:
        println("Insira o dia que deseja embarcar:")
        val userReservaDia = readLine()!!.toInt()
        if (userReservaDia > 31 || userReservaDia < 1) {
            println("O dia inserido não é válido. Tente novamente.")
            return
        }
        //Para não criarmos mais variáveis desnecessárias, reiniciamos o valor do nosso "count":
        posReservaVoos = 0
        //Este boolean resolverá o problema caso não existam voos que se encaixem no inserido pelo usuário:
        var naoTemVoo = true
        //Loop para ver se existe um voo com esta origem, este destino, e no dia inserido pelo usuário:
        while (posReservaVoos < voos) {
            if (userReservaPartida == vooPartidaDoVoo[posReservaVoos] && userReservaDestino == vooDestinoDoVoo[posReservaVoos] && userReservaDia == vooDiaDoVoo[posReservaVoos]) {
                naoTemVoo = false
                println("${vooNomeDoVoo[posReservaVoos]} : ${vooPartidaDoVoo[posReservaVoos]} - ${vooDestinoDoVoo[posReservaVoos]}, DIA ${vooDiaDoVoo[posReservaVoos]} - Lotação: ${contadoNumeroDePassageirosNoVoo(vooNomeDoVoo[posReservaVoos], vooDiaDoVoo[posReservaVoos]!!)}/20")
            }
            posReservaVoos++
        }
        if (naoTemVoo) {
            println("Infelizmente, não existem voos de $userReservaPartida a $userReservaDestino no dia $userReservaDia. ")
            //Porém, se não existirem opções para mostrar:
            sugerirVoos(userReservaPartida, userReservaDestino, userReservaDia)
            return
        }
        //Aqui, o utilizador insere o voo que deseja.
        println("Insira o voo que deseja:")
        val userVooDesejado = readLine()
        var posNomeVoos = 0
        while (posNomeVoos < voos) {
            if (userVooDesejado == vooNomeDoVoo[posNomeVoos] && userReservaPartida == vooPartidaDoVoo[posNomeVoos] && userReservaDestino == vooDestinoDoVoo[posNomeVoos] && userReservaDia == vooDiaDoVoo[posNomeVoos]) {
                break
            }
            posNomeVoos++
        }
        //Se o nome do voo introduzido não condizer com os mostrados, uma mensagem de erro é mostrada:
        if (posNomeVoos == voos) {
            println("O número do voo inserido é inválido.")
            return
        }
        //Se o voo estiver cheio, sugerimos voos.
        if (contadoNumeroDePassageirosNoVoo(userVooDesejado, userReservaDia) >= 20) {
            println("O voo tem lotação máxima. Por favor, selecione outro voo.")
            sugerirVoos(userReservaPartida, userReservaDestino, userReservaDia)
            return
        }
        //Para finalizar a operação, os outros dados do utilizador são pedidos:
        println("Insira seu nome:")
        val userNome = readLine()
        println("Insira o seu número do Cartão de Cidadão:")
        val userCC = readLine()
        println("Insira a sua Idade:")
        val userAge = readLine()!!.toInt()
        //Para garantir que o utilizador não cometeu nenhum erro, o programa mostra os dados por ele introduzidos:
        println("Por favor, confirme seus dados:\n" +
                "$userVooDesejado : $userReservaPartida - $userReservaDestino DIA $userReservaDia\n" +
                "Cliente : $userNome\n" +
                "Número do Cartão de Cidadão : $userCC\n" +
                "Idade : $userAge\n")
        //Se tudo estiver OK, o utilizador deve introduzir S. Se não, N.
        println("Confirmar? Escreva S ou N.")
        val userAnswer = readLine()!!
        //Por fim, com tudo checado, os dados do utilizador são adicionados aos arrays relacionados à reserva. Para que a operação seja concluída, existe uma mensagem de instrução.
        if (userAnswer == "S") {
            reservasNomeDoCliente[reservas] = userNome
            reservasNomeDoVoo[reservas] = userVooDesejado
            reservasDiaDoVoo[reservas] = userReservaDia
            reservasNumCartaoCidadao[reservas] = userCC
            reservasIdadeDoCliente[reservas] = userAge
            reservas++
            println("Reserva feita! Use a opção 3 do menu para finalizar sua reserva.")
        } else {
            println("A reserva não foi concluída. Retornando ao menu.")
            return
        }
    }

    //Início do programa com a função main:
    fun main(args: Array<String>) {
        //Menu inicial do programa, num ciclo do..while para que o programa só feche quando o utilizador quiser que ele feche.
        do {
            println("Companhia de Aviação \"Ja Fui\" \n " +
                    "Programa de Reservas \n" +
                    "0 - Sair do programa \n" +
                    "1 - Listar passageiros de um voo \n" +
                    "2 - Número de passageiros de um periodo de dias \n" +
                    "3 - Escrita em ficheiro \n" +
                    "4 - Análise económica \n" +
                    "5 - Reserva simples de um voo \n")
            val comandoMainMenu = readLine()!!.toInt()
            when (comandoMainMenu) {
                1 -> listarPassageirosDoVoo()
                2 -> numeroPassageirosPeriodoDia()
                3 -> escritaEmFicheiro()
                4 -> analiseEconomica()
                5 -> reservaDeUmVoo()
                0 -> println("") //O programa termina quando esta for a opção selecionada. Foi colocada por último por conveniência.
                else -> println("Por favor, insira uma opção válida.")
            }
        } while (comandoMainMenu != 0)
    }
//Fim do programa! :D