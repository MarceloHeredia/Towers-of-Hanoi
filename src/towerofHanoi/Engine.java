package towerofHanoi;

//imports
import structures.StackSimpleLinked;
import java.lang.Math;

/**
 * @author marcelo.heredia
 * @version 1.0
 * @since 	1.0
 * 
 * This class is the game engine
 * <p>
 * 	Camada inferior do jogo onde ficam os m�todos
 * 	que representam regras e jogadas da Torre de Hanoi.
 * </p>
 */
public class Engine
{
	/**
	 * Representa o pino da esquerda (inicial)
	 */
	private StackSimpleLinked<Integer> torre1;
	/**
	 * Representa o pino do meio.
	 */
	private StackSimpleLinked<Integer> torre2;
	/**
	 * Representa o pino da direita (final).
	 */
	private StackSimpleLinked<Integer> torre3;
	/**
	 * Lista contendo as jogadas feitas pelo usu�rio.
	 * Feita para usar o m�todo undoPlay.
	 */
	private StackSimpleLinked<String> jogadas;
	/**
	 * Vari�vel de controle que define o fim do jogo.
	 */
	private boolean fim;
	/**
	 * Armazena a dificuldade do jogo para testar checar se o jogo acabou.
	 */
	private int dificuldade;
	/**
	 * Armazena a quantidade de movimentos usados at� agora.
	 */
	private int movimentos;
	
	/**
	 * Instancia o Sistema do jogo.
	 * Define a dificuldade desta partida.
	 * Chama o M�todo iniciar.
	 * @param dificuldade define o n�vel do jogo de 3 a 7
	 * @throws FimDeJogoException
	 * @throws GameException
	 */
	public Engine (int dificuldade) throws GameException
	{
		this.dificuldade = dificuldade;
		iniciar(dificuldade);
	}
	
	/**
	 * Reinicia o jogo.
	 * @throws GameException 
	 */
	public void reiniciar () throws GameException
	{
		this.iniciar(dificuldade);
	}

	/**
	 * M�todo usado para fazer um movimento(jogada).
	 * @param atual localiza��o atual do disco
	 * @param destino localiza��o para onde quer mover o disco
	 * @return (true) se a jogada for v�lida.
	 * @throws GameException 
	 * @throws FimDeJogoException
	 */
	public boolean mover (String atual, String destino) throws GameException, FimDeJogoException
	{
		if (fim)
		{
			this.fimDeJogo();
		}
		StackSimpleLinked<Integer> loc_at, loc_dest;
		
		switch (atual) 
		{
			case "A":
				loc_at = torre1;
				break;
			case "B":
				loc_at = torre2;
				break;
			case "C":
				loc_at = torre3;
				break;
			default:
				throw new GameException("Ponto de partida invalido");
		}
		switch (destino)
		{
			case "A":
				loc_dest = torre1;
				break;
			case "B":
				loc_dest = torre2;
				break;
			case "C":
				loc_dest = torre3;
				break;
			default:
				throw new GameException("Ponto destino invalido");
		}
		
		if (loc_at == loc_dest)
		{
			throw new GameException("Movimento invalido");
		}
		if	(loc_at.size() == 0)
		{
			throw new GameException("Nao tem nenhum disco no seu ponto de partida");
		}
		else
		{
			if (loc_dest.isEmpty())
			{
				loc_dest.push(loc_at.pop());
				movimentos ++;
				jogadas.push(atual+'-'+destino);
				return true;
			}
			else
			{
				if (loc_at.peek() < loc_dest.peek())
				{
					loc_dest.push(loc_at.pop());
					movimentos++;
					jogadas.push(atual+'-'+destino);
					if (torre3.size()==dificuldade)
					{
						this.fimDeJogo();
					}
					return true;
				}
			}
		}
		throw new GameException("Nao pode colocar um disco maior acima de um menor");
	}
	
	/**
	 * M�todo usado para desfazer um movimento(jogada).
	 * @return (true) se tiver desfeito, (false) se n�o puder desfazer.
	 * @throws GameException
	 * @throws FimDeJogoException
	 */
	public boolean desfazer() throws GameException, FimDeJogoException
	{
		if (fim)
		{
			this.fimDeJogo();
		}
		StackSimpleLinked<Integer> loc_at, loc_dest;
		if(jogadas.size()>0)
		{
			String [] jogada = jogadas.pop().split("-");
			switch (jogada[1])
			{
			case "A":
				loc_at = torre1;
				break;
			case "B":
				loc_at = torre2;
				break;
			case "C":
				loc_at = torre3;
				break;
			default:
				jogadas.push(jogada[0]+'-'+jogada[1]);
				throw new GameException("Falha ao desfazer jogada");
			}
			switch (jogada[0])
			{
				case "A":
					loc_dest = torre1;
					break;
				case "B":
					loc_dest = torre2;
					break;
				case "C":
					loc_dest = torre3;
					break;
				default:
					jogadas.push(jogada[0]+'-'+jogada[1]);
					throw new GameException("Falha ao desfazer jogada");
			}
			loc_dest.push(loc_at.pop());
			movimentos--;
			return true;
		}
		throw new GameException("Falha ao desfazer jogada");
	}
	
	/**
	 * Informa o m�nimo de movimentos necess�rios para terminar o jogo
	 * @return Quantidade m�nima de movimentos necessarias.
	 */
	public int minMovimentos ()
	{
		return (int)Math.pow(2, dificuldade)-1;
	}

	/**
	 * Retorna a quantidade de movimentos executados.
	 * @return movimentos
	 */
	public int getMovimentos()
	{
		return movimentos;
	}
	
	/**
	 * Retorna o estado da torre 1
	 * @return torre1.toString()
	 */
	public String getTorre1()
	{
		return torre1.reversetoString().replaceAll("[^1-7]", "");
	}
	
	/**
	 * Retorna o estado da torre 2
	 * @return torre1.toString
	 */
	public String getTorre2()
	{
		return torre2.reversetoString().replaceAll("[^1-7]", "");
	}
	
	/**
	 * Retorna o estado da torre 3
	 * @return torre3.toString()
	 */
	public String getTorre3()
	{
		return torre3.reversetoString().replaceAll("[^1-7]", "");
	}
	
	
	/**
	 * Inicia um novo jogo.
	 * M�todo chamado pela Engine e pelo m�todo reiniciar.
	 * @param dificuldade
	 */
	private void iniciar(int dificuldade) throws GameException
	{
		torre1 = new StackSimpleLinked<Integer>();
		torre2 = new StackSimpleLinked<Integer>();
		torre3 = new StackSimpleLinked<Integer>();
		jogadas = new StackSimpleLinked<String>();
		fim = false;
		movimentos = 0;
		switch (dificuldade)
		{
			case 3:
				torre1.push(3);
				torre1.push(2);
				torre1.push(1);
				break;
			case 4:
				torre1.push(4);
				torre1.push(3);
				torre1.push(2);
				torre1.push(1);
				break;
			case 5:
				torre1.push(5);
				torre1.push(4);
				torre1.push(3);
				torre1.push(2);
				torre1.push(1);
				break;
			case 6:
				torre1.push(6);
				torre1.push(5);
				torre1.push(4);
				torre1.push(3);
				torre1.push(2);
				torre1.push(1);
				break;
			case 7:
				torre1.push(7);
				torre1.push(6);
				torre1.push(5);
				torre1.push(4);
				torre1.push(3);
				torre1.push(2);
				torre1.push(1);
				break;
			default:
				throw new GameException("Deve iniciar o jogo com 3 a 7 discos");
		}
	}
	
	/**
	 * Declara fim de jogo.
	 * @throws FimDeJogoException
	 */
	private void fimDeJogo() throws FimDeJogoException
	{
		fim = true;
		throw new FimDeJogoException();
	}
}
