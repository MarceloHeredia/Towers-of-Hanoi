package towerofHanoi;


import java.util.InputMismatchException;
//imports
import java.util.Scanner;
import java.lang.StringBuilder;


/**
 * @author marcelo.heredia
 * @version 1.1
 * @since 	1.0
 * 
 * Essa classe e o executavel do jogo no modo CONSOLE.
 * <p>
 * 	Camada onde se instancia o sistema Engine para executar o jogo
 * </p>
 */

 
public class TowerOfHanoi
{
	/**
	 * Variaveis que serao utilizadas em todos metodos da classe.
	 */
	private static Scanner read = new Scanner(System.in);
	private static StringBuilder telaNovaPartida;
	private static StringBuilder telaInicio;
	private static StringBuilder telaAuto;
	private static StringBuilder telaOpcoesJogada;
	private static StringBuilder estadoTorres;
	
	private static Engine eng;
	private static StringBuilder estadoTorre1;
	private static StringBuilder estadoTorre2;
	private static StringBuilder estadoTorre3;
    
    
	/**
	 * Metodo main
	 * Inicia o menu do jogo.
	 * @param args
	 */
    public static void main(String[]args)
    {
        preencheTextosJogo();

        menuInicial();

        read.close();
    }

    /**
     * Menu inicial do jogo
     * @return falso.
     */
    public static boolean menuInicial()
    {
        String op;
        boolean jogando = true;
        boolean opInvalida = false;
        while(jogando)
        {
            estadoTorre1 = new StringBuilder();
            estadoTorre2 = new StringBuilder();
            estadoTorre3 = new StringBuilder();

            do
            {
                //seleciona opcoes
                //repete sempre que o usuario digitar uma opcao invalida.
                System.out.println(telaInicio.toString());
                op = read.next();
                switch(op)
                {
                    case "iniciar":
                        iniciar();
                        opInvalida = false;
                        break;
                    case "auto":
                        auto();
                        opInvalida = false;
                        break;
                    case "sair":
                        jogando = false;
                        opInvalida = false;
                        break;
                    default:
                        System.out.println("\nOpcao invalida!\n");
                        opInvalida = true;
                        break;
                }
            }
            while(opInvalida);
        }
        return jogando;
    }

    /**
     * Inicia uma partida.
     */
    private static void iniciar()
    {
        int discos = 0;
        boolean continuar = true;
        String [] tokens = new String[3];
        boolean opInvalida = false;
        

        do
        {
            try
            {
            	System.out.println(telaNovaPartida.toString());
                discos = read.nextInt();
                eng = new Engine(discos);
                read.nextLine();
                opInvalida = false;
                System.out.println(estadoJogo(discos));
            }
            catch(GameException ex)
            {
                opInvalida = true;
                System.out.println(ex.toString());
            }
            catch(InputMismatchException ex2)
            {
                opInvalida = true;
                System.out.println("A quantidade de discos deve ser um numero inteiro.");
                read.nextLine();
            }
        }
        while(opInvalida);
        
        while(continuar)
        {
            System.out.println(telaOpcoesJogada.toString());
            tokens = read.nextLine().split(" ");

            try
            {
                continuar = execComando(tokens);
            }
            catch(GameException ex)
            {
                System.out.println(ex.toString());
            }
            catch(FimDeJogoException end)
            {
            	System.out.println(estadoJogo(discos));
                System.out.println("Parabens!, jogo completo.");
                System.out.println("Voltando ao menu principal em: ");
                try
                {
                    for (int i=9; i>=0; i--)
                    {
                        System.out.print(i);
                        Thread.sleep(1000);
                        System.out.print("\b");
                    }
                }
                catch(InterruptedException e1)
                {
                    break;
                }
                continuar = false;
            }
            if (continuar)
            {
                System.out.println(estadoJogo(discos));
            }
        }
        
        return;
        
    }

    /**
     * Executa o comando na Engine
     * @param tokens - Array de string contendo o comando a ser executado.
     * @throws GameException
     * @returns true se a jogada for executada e false se for sair do jogo 
     * (se a jogada n√£o for executada com sucesso retornar√° uma exce√ß√£o).
     */
	private static boolean execComando(String[] tokens) throws GameException
	{
		switch (tokens[0])
		{
		
			case "mover":
				if (tokens.length<3) 
					throw new GameException("Deve definir ponto de partida e destino da jogada.");
				return eng.mover(tokens[1].toUpperCase(), tokens[2].toUpperCase());
			case "desfazer":
				return eng.desfazer();
			case "reiniciar":
				eng.reiniciar();
				return true;
			case "sair":
				return false;
		}
		return false;
	}

	/**
	 * Retorna o estado atual do jogo em String para ser imprimido no console
	 * @param discos
	 * @return estado atual do jogo
	 */
	private static String estadoJogo(int discos)
	{
		String pinos = "|||||||".substring(0,discos);
		String ocupados = pinos.substring(0,discos-eng.getTorre1().length());
		estadoTorre1 = new StringBuilder(eng.getTorre1()+ocupados);
		
		ocupados = pinos.substring(0,discos-eng.getTorre2().length());
		estadoTorre2 = new StringBuilder(eng.getTorre2()+ocupados);
		
		ocupados = pinos.substring(0,discos-eng.getTorre3().length());
		estadoTorre3 = new StringBuilder(eng.getTorre3()+ocupados);
		
		
		estadoTorres = new StringBuilder();
		estadoTorres.append("\n\n\n\n");
		if (discos==7)
			estadoTorres.append("  "+estadoTorre1.charAt(6)+"       "+estadoTorre2.charAt(6)+"       "+estadoTorre3.charAt(6)+"  \n");
		if (discos>=6)
			estadoTorres.append("  "+estadoTorre1.charAt(5)+"       "+estadoTorre2.charAt(5)+"       "+estadoTorre3.charAt(5)+"  \n");
		if (discos>=5)
			estadoTorres.append("  "+estadoTorre1.charAt(4)+"       "+estadoTorre2.charAt(4)+"       "+estadoTorre3.charAt(4)+"  \n");
		if (discos>=4)
			estadoTorres.append("  "+estadoTorre1.charAt(3)+"       "+estadoTorre2.charAt(3)+"       "+estadoTorre3.charAt(3)+"  \n");
		estadoTorres.append("  "+estadoTorre1.charAt(2)+"       "+estadoTorre2.charAt(2)+"       "+estadoTorre3.charAt(2)+"  \n");
		estadoTorres.append("  "+estadoTorre1.charAt(1)+"       "+estadoTorre2.charAt(1)+"       "+estadoTorre3.charAt(1)+"  \n");
		estadoTorres.append("  "+estadoTorre1.charAt(0)+"       "+estadoTorre2.charAt(0)+"       "+estadoTorre3.charAt(0)+"  \n");
		estadoTorres.append("=====================\n");
		estadoTorres.append("  A       B       C  \n");
		estadoTorres.append("\n\n\n Jogadas executadas ate agora: "+eng.getMovimentos());
		estadoTorres.append("\n Minimo de movimentos nesse nivel: "+eng.minMovimentos()+"\n");
		
		return estadoTorres.toString();	
	}

    /**
	 * Inicializa os textos que nÔøΩo receberÔøΩo mudanÔøΩas ao longo do jogo
	 */
	private static void preencheTextosJogo()
	{
		//inicia tela inicial
		telaInicio = new StringBuilder();
		telaInicio.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		telaInicio.append("====================\n");
		telaInicio.append("===TORRE DE HANOI===\n");
		telaInicio.append("====================\n");
		telaInicio.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		telaInicio.append("Digite uma opcao:\n");
		telaInicio.append("NOVO JOGO: iniciar\n");
		telaInicio.append("VER SOLUCAO: auto\n");
		telaInicio.append("SAIR DO JOGO: sair\n\n");
		
		//inicia tela de Novo Jogo
		telaNovaPartida = new StringBuilder();
		telaNovaPartida.append("\n\nDigite a quantidade de discos (3-7):\n\n");
		
		//inicia tela de Auto Jogo
		telaAuto = new StringBuilder();
		telaAuto.append("\n\nDigite a quantidade de discos (3-7):\n\n");
		
		//inicia tela de Opcoes de Jogada
		telaOpcoesJogada = new StringBuilder();
		telaOpcoesJogada.append("\n\n\n");
		telaOpcoesJogada.append("Opcoes de Jogada\n");
		telaOpcoesJogada.append("Fazer movimento: mover localizacao destino\n");
		telaOpcoesJogada.append("Desfazer movimento: desfazer\n");
		telaOpcoesJogada.append("Comecar uma nova partida: reiniciar\n");
		telaOpcoesJogada.append("Voltar ao menu principal: sair");
		
		
	}

	/**
	 * Resolve o jogo automaticamente.
	 */
	private static void auto()
	{
		int discos = 0;
		boolean opInvalida = false;
		
		
		do
        {
            try
            {
            	System.out.println(telaAuto.toString());
                discos = read.nextInt();
        		read.nextLine();
                eng = new Engine(discos);
                opInvalida = false;
                System.out.println(estadoJogo(discos));
            }
            catch(GameException ex)
            {
                opInvalida = true;
                System.out.println(ex.toString());
            }
            catch(InputMismatchException ex2)
            {
                opInvalida = true;
                System.out.println("A quantidade de discos deve ser um numero inteiro.");
        		read.nextLine();
            }
        }
        while(opInvalida);
		
		
		try
		{
			resolve(discos, discos, "A", "B", "C");
		}
		catch (FimDeJogoException e)
		{
			try
			{
				Thread.sleep(1000);
				System.out.println(estadoJogo(discos));
				System.out.println("Jogo resolvido!");
				System.out.println("Para sair digite qualquer coisa");
				read.next();
				return;
			}
            catch(InterruptedException e1)
            {
                System.out.println("Algo deu muito errado!");
            }
		} 
		catch (GameException e)
		{
			System.out.println(e.toString());
		}
	}
	
	/**
	 * AdaptaÁ„o de uma soluÁ„o recursiva que encontrei na internet para resolver o jogo automaticamente.
	 * @param dificuldade -> dificuldade do jogo
	 * @param n -> auxiliar para a recurs„o
	 * @param inicio -> ponto de partida do movimento
	 * @param auxiliar -> auxiliar que recebe a torre que n„o est· sendo movida
	 * @param fim -> ponto de destino do movimento
	 * @throws FimDeJogoException
	 * @throws GameException
	 */
	private static void resolve(int dificuldade, int n, String inicio, String auxiliar, String fim) throws FimDeJogoException, GameException 
	{
		try
		{
			if (n==1) 
			{
				eng.mover(inicio, fim);
				Thread.sleep(1000);
				System.out.println(estadoJogo(dificuldade));
				return;
			}
			else 
			{
				resolve(dificuldade, n-1, inicio, fim, auxiliar);
				eng.mover(inicio, fim);
				Thread.sleep(1000);
				System.out.println(estadoJogo(dificuldade));
				resolve(dificuldade, n-1, auxiliar, inicio, fim);
			}
		}
		catch(InterruptedException ex)
		{
			System.out.println("Algo deu muito errado!");
		}
	}
}